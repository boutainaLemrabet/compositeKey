import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest } from 'rxjs';
import { Subscription } from 'rxjs';
import { filter, tap, switchMap } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';
import { MessageService } from 'primeng/api';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from './employee.service';

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
import { Table } from 'primeng/table';

@Component({
  selector: 'jhi-employee',
  templateUrl: './employee.component.html',
})
export class EmployeeComponent implements OnInit, OnDestroy {
  employees?: IEmployee[];
  eventSubscriber?: Subscription;
  Options: I[] | null = null;

  totalItems?: number;
  itemsPerPage!: number;
  loading!: boolean;

  private filtersDetails: { [_: string]: { matchMode?: string; flatten?: (_: any) => string; unflatten?: (_: string) => any } } = {
    managerUsername: { matchMode: 'in' },
  };

  @ViewChild('employeeTable', { static: true })
  employeeTable!: Table;

  constructor(
    protected employeeService: EmployeeService,
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
    this.registerChangeInEmployees();
    this.activatedRoute.queryParams
      .pipe(
        tap(queryParams => fillTableFromQueryParams(this.employeeTable, queryParams, this.filtersDetails)),
        tap(() => (this.loading = true)),
        switchMap(() => this.employeeService.query(lazyLoadEventToServerQueryParams(this.employeeTable.createLazyLoadMetadata()))),
        filter((res: HttpResponse<IEmployee[]>) => res.ok)
      )
      .subscribe(
        (res: HttpResponse<IEmployee[]>) => {
          this.paginateEmployees(res.body!, res.headers);
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
    this.router.navigate(['/employee'], { queryParams });
  }

  filter(value: any, field: string): void {
    this.employeeTable.filter(value, field, computeFilterMatchMode(this.filtersDetails[field]));
  }

  delete(username: undefined): void {
    this.confirmationService.confirm({
      header: this.translateService.instant('entity.delete.title'),
      message: this.translateService.instant('primengtestApp.employee.delete.question', { id: username }),
      accept: () => {
        this.employeeService.delete(username).subscribe(() => {
          this.eventManager.broadcast({
            name: 'employeeListModification',
            content: 'Deleted an employee',
          });
        });
      },
    });
  }

  onLazyLoadEvent(event: LazyLoadEvent): void {
    this.employeeService.query(lazyLoadEventToServerQueryParams(event, '.contains')).subscribe(res => (this.Options = res.body));
  }

  trackId(index: number, item: IEmployee): string {
    return item.username!;
  }

  protected paginateEmployees(data: IEmployee[], headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.employees = data;
  }

  protected onError(errorMessage: string): void {
    this.messageService.add({ severity: 'error', summary: errorMessage });
  }
}
