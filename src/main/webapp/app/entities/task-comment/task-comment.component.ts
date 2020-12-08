import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, tap, switchMap } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';
import { MessageService } from 'primeng/api';
import { ITaskComment } from '../../shared/model/task-comment.model';
import { TaskCommentService } from './task-comment.service';

import { ITEMS_PER_PAGE } from '../../core/config/pagination.constants';
import {
  computeFilterMatchMode,
  lazyLoadEventToServerQueryParams,
  lazyLoadEventToRouterQueryParams,
  fillTableFromQueryParams,
} from '../../core/request/request-util';
import { ConfirmationService, LazyLoadEvent } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';

import { ITask } from '../../shared/model/task.model';
import { TaskService } from '../../entities/task/task.service';
import { Table } from 'primeng/table';

@Component({
  selector: 'jhi-task-comment',
  templateUrl: './task-comment.component.html',
})
export class TaskCommentComponent implements OnInit, OnDestroy {
  taskComments?: ITaskComment[];
  eventSubscriber?: Subscription;
  taskOptions: ITask[] | null = null;

  totalItems?: number;
  itemsPerPage!: number;
  loading!: boolean;

  private filtersDetails: { [_: string]: { matchMode?: string; flatten?: (_: string[]) => string; unflatten?: (_: string) => any } } = {
    id: { matchMode: 'equals', unflatten: (x: string) => +x },
    taskId: { matchMode: 'in' },
  };

  @ViewChild('taskCommentTable', { static: true })
  taskCommentTable!: Table;

  constructor(
    protected taskCommentService: TaskCommentService,
    protected taskService: TaskService,
    protected messageService: MessageService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected confirmationService: ConfirmationService,
    protected translateService: TranslateService
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.loading = true;
  }

  ngOnInit(): void {
    this.activatedRoute.queryParams
      .pipe(
        tap(queryParams => fillTableFromQueryParams(this.taskCommentTable, queryParams, this.filtersDetails)),
        tap(() => (this.loading = true)),
        switchMap(() => this.taskCommentService.query(lazyLoadEventToServerQueryParams(this.taskCommentTable.createLazyLoadMetadata()))),
        filter((res: HttpResponse<ITaskComment[]>) => res.ok)
      )
      .subscribe(
        (res: HttpResponse<ITaskComment[]>) => {
          this.paginateTaskComments(res.body!, res.headers);
          this.loading = false;
        },
        (res: HttpErrorResponse) => {
          this.onError(res.message);
          this.loading = false;
        }
      );
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  onLazyLoadEvent(event: LazyLoadEvent): void {
    const queryParams = lazyLoadEventToRouterQueryParams(event, this.filtersDetails);
    this.router.navigate(['/task-comment'], { queryParams });
  }

  filter(value: any, field: string): void {
    this.taskCommentTable.filter(value, field, computeFilterMatchMode(this.filtersDetails[field]));
  }

  delete(id: number): void {
    this.confirmationService.confirm({
      header: this.translateService.instant('entity.delete.title'),
      message: this.translateService.instant('primengtestApp.taskComment.delete.question', { id: id }),
      accept: () => {
        this.taskCommentService.delete(id).subscribe(() => {
          this.eventManager.broadcast({
            name: 'taskCommentListModification',
            content: 'Deleted an taskComment',
          });
        });
      },
    });
  }

  onTaskLazyLoadEvent(event: LazyLoadEvent): void {
    this.taskService.query(lazyLoadEventToServerQueryParams(event, 'id.contains')).subscribe(res => (this.taskOptions = res.body));
  }

  trackId(index: number, item: ITaskComment): number {
    return item.id!;
  }

  protected paginateTaskComments(data: ITaskComment[], headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.taskComments = data;
  }

  protected onError(errorMessage: string): void {
    this.messageService.add({ severity: 'error', summary: errorMessage });
  }
}
