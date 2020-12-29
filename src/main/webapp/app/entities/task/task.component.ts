import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { MessageService } from 'primeng/api';
import { ITask } from '../../shared/model/task.model';
import { TaskType, TASK_TYPE_ARRAY } from '../../shared/model/enumerations/task-type.model';
import { TaskService } from './task.service';
import { lazyLoadEventToServerQueryParams } from '../../core/request/request-util';
import { ConfirmationService, LazyLoadEvent } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';

import { IUser } from '../../core/user/user.model';
import { UserService } from '../../core/user/user.service';
import { Table } from 'primeng/table';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'jhi-task',
  templateUrl: './task.component.html',
})
export class TaskComponent implements OnInit, OnDestroy {
  tasks?: ITask[];
  eventSubscriber?: Subscription;
  typeOptions = TASK_TYPE_ARRAY.map((s: TaskType) => ({ label: s.toString(), value: s }));
  userOptions: IUser[] | null = null;

  private filtersDetails: { [_: string]: { matchMode?: string; flatten?: (_: string[]) => string; unflatten?: (_: string) => any } } = {
    id: { matchMode: 'equals', unflatten: (x: string) => +x },
    name: { matchMode: 'contains' },
    type: { matchMode: 'in' },
    endDate: { matchMode: 'between', flatten: a => a.filter((x: string) => x).join(','), unflatten: (a: string) => a.split(',') },
    createdAt: { matchMode: 'between', flatten: a => a.filter((x: string) => x).join(','), unflatten: (a: string) => a.split(',') },
    modifiedAt: { matchMode: 'between', flatten: a => a.filter((x: string) => x).join(','), unflatten: (a: string) => a.split(',') },
    done: { matchMode: 'equals', unflatten: (x: string) => x === 'true' },
    ['user.id']: {
      matchMode: 'in',
      flatten: a => a.filter((x: string) => `${x}`).join(','),
      unflatten: (a: string) => a.split(',').map(x => +x),
    },
  };

  @ViewChild('taskTable', { static: true })
  taskTable!: Table;

  constructor(
    protected taskService: TaskService,
    protected userService: UserService,
    protected messageService: MessageService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected confirmationService: ConfirmationService,
    protected translateService: TranslateService,
    protected datePipe: DatePipe
  ) {}

  ngOnInit(): void {
    this.loadAll();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  loadAll(): void {
    this.taskService
      .query()
      .pipe(
        filter((res: HttpResponse<ITask[]>) => res.ok),
        map((res: HttpResponse<ITask[]>) => res.body!)
      )
      .subscribe(
        (tasks: ITask[]) => {
          this.tasks = tasks;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  filter(value: any, field: string): void {
    this.taskTable.filter(value, field, this.filtersDetails[field].matchMode);
  }

  delete(id: number): void {
    this.confirmationService.confirm({
      header: this.translateService.instant('entity.delete.title'),
      message: this.translateService.instant('compositekeyApp.task.delete.question', { id }),
      accept: () => {
        this.taskService.delete(id).subscribe(() => {
          this.eventManager.broadcast({
            name: 'taskListModification',
            content: 'Deleted an task',
          });
        });
      },
    });
  }

  onUserLazyLoadEvent(event: LazyLoadEvent): void {
    this.userService.query(lazyLoadEventToServerQueryParams(event, 'globalFilter')).subscribe(res => (this.userOptions = res.body));
  }

  trackId(index: number, item: ITask): number {
    return item.id!;
  }

  byteSize(field: string): string {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType = '', field: string): void {
    return this.dataUtils.openFile(contentType, field);
  }

  protected onError(errorMessage: string): void {
    this.messageService.add({ severity: 'error', summary: errorMessage });
  }
}
