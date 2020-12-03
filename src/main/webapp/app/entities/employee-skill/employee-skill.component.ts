import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest } from 'rxjs';
import { Subscription } from 'rxjs';
import { filter, tap, switchMap } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';
import { MessageService } from 'primeng/api';
import { IEmployeeSkill } from 'app/shared/model/employee-skill.model';
import { EmployeeSkillService } from './employee-skill.service';

import { ITEMS_PER_PAGE } from 'app/core/config/pagination.constants';
import {
  computeFilterMatchMode,
  lazyLoadEventToServerQueryParams,
  lazyLoadEventToRouterQueryParams,
  fillTableFromQueryParams,
} from 'app/shared/util/request-util';
import { ConfirmationService, LazyLoadEvent } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { I } from 'app/shared/model/.model';
import { Service } from 'app/entities//.service';
import { I } from 'app/shared/model/.model';
import { Service } from 'app/entities//.service';
import { Table } from 'primeng/table';

@Component({
  selector: 'jhi-employee-skill',
  templateUrl: './employee-skill.component.html',
})
export class EmployeeSkillComponent implements OnInit, OnDestroy {
  employeeSkills?: IEmployeeSkill[];
  eventSubscriber?: Subscription;
  Options: I[] | null = null;
  Options: I[] | null = null;
  Options: I[] | null = null;

  totalItems?: number;
  itemsPerPage!: number;
  loading!: boolean;

  private filtersDetails: { [_: string]: { matchMode?: string; flatten?: (_: any) => string; unflatten?: (_: string) => any } } = {
    level: { matchMode: 'equals', unflatten: (x: string) => +x },
    taskId: { matchMode: 'in' },
    employeeUsername: { matchMode: 'in' },
    teacherUsername: { matchMode: 'in' },
  };

  @ViewChild('employeeSkillTable', { static: true })
  employeeSkillTable!: Table;

  constructor(
    protected employeeSkillService: EmployeeSkillService,
    protected taskService: Service,
    protected employeeService: Service,
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
    this.registerChangeInEmployeeSkills();
    this.activatedRoute.queryParams
      .pipe(
        tap(queryParams => fillTableFromQueryParams(this.employeeSkillTable, queryParams, this.filtersDetails)),
        tap(() => (this.loading = true)),
        switchMap(() =>
          this.employeeSkillService.query(lazyLoadEventToServerQueryParams(this.employeeSkillTable.createLazyLoadMetadata()))
        ),
        filter((res: HttpResponse<IEmployeeSkill[]>) => res.ok)
      )
      .subscribe(
        (res: HttpResponse<IEmployeeSkill[]>) => {
          this.paginateEmployeeSkills(res.body!, res.headers);
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
    this.router.navigate(['/employee-skill'], { queryParams });
  }

  filter(value: any, field: string): void {
    this.employeeSkillTable.filter(value, field, computeFilterMatchMode(this.filtersDetails[field]));
  }

  delete(name: undefined, employeeUsername: undefined): void {
    this.confirmationService.confirm({
      header: this.translateService.instant('entity.delete.title'),
      message: this.translateService.instant('primengtestApp.employeeSkill.delete.question', { id: name + ',' + employeeUsername }),
      accept: () => {
        this.employeeSkillService.delete(name, employeeUsername).subscribe(() => {
          this.eventManager.broadcast({
            name: 'employeeSkillListModification',
            content: 'Deleted an employeeSkill',
          });
        });
      },
    });
  }

  onLazyLoadEvent(event: LazyLoadEvent): void {
    this.taskService.query(lazyLoadEventToServerQueryParams(event, '.contains')).subscribe(res => (this.Options = res.body));
  }

  onLazyLoadEvent(event: LazyLoadEvent): void {
    this.employeeService.query(lazyLoadEventToServerQueryParams(event, '.contains')).subscribe(res => (this.Options = res.body));
  }

  onLazyLoadEvent(event: LazyLoadEvent): void {
    this.employeeService.query(lazyLoadEventToServerQueryParams(event, '.contains')).subscribe(res => (this.Options = res.body));
  }

  trackId(index: number, item: IEmployeeSkill): string {
    return `${item.name!},${item.employee!.username!}`;
  }

  protected paginateEmployeeSkills(data: IEmployeeSkill[], headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.employeeSkills = data;
  }

  protected onError(errorMessage: string): void {
    this.messageService.add({ severity: 'error', summary: errorMessage });
  }
}
