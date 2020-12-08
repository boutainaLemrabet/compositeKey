import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';
import { MessageService } from 'primeng/api';
import { IWithIdStringDetails } from '../../shared/model/with-id-string-details.model';
import { WithIdStringDetailsService } from './with-id-string-details.service';
import { computeFilterMatchMode, lazyLoadEventToServerQueryParams } from '../../core/request/request-util';
import { ConfirmationService, LazyLoadEvent } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';

import { IWithIdString } from '../../shared/model/with-id-string.model';
import { WithIdStringService } from '../../entities/with-id-string/with-id-string.service';
import { Table } from 'primeng/table';

@Component({
  selector: 'jhi-with-id-string-details',
  templateUrl: './with-id-string-details.component.html',
})
export class WithIdStringDetailsComponent implements OnInit, OnDestroy {
  withIdStringDetails?: IWithIdStringDetails[];
  eventSubscriber?: Subscription;
  withIdStringOptions: IWithIdString[] | null = null;

  private filtersDetails: { [_: string]: { matchMode?: string; flatten?: (_: string[]) => string; unflatten?: (_: string) => any } } = {
    withIdStringId: { matchMode: 'in' },
  };

  @ViewChild('withIdStringDetailsTable', { static: true })
  withIdStringDetailsTable!: Table;

  constructor(
    protected withIdStringDetailsService: WithIdStringDetailsService,
    protected withIdStringService: WithIdStringService,
    protected messageService: MessageService,
    protected eventManager: JhiEventManager,
    protected confirmationService: ConfirmationService,
    protected translateService: TranslateService
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
    this.withIdStringDetailsService
      .query()
      .pipe(
        filter((res: HttpResponse<IWithIdStringDetails[]>) => res.ok),
        map((res: HttpResponse<IWithIdStringDetails[]>) => res.body!)
      )
      .subscribe(
        (withIdStringDetails: IWithIdStringDetails[]) => {
          this.withIdStringDetails = withIdStringDetails;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  filter(value: any, field: string): void {
    this.withIdStringDetailsTable.filter(value, field, computeFilterMatchMode(this.filtersDetails[field]));
  }

  delete(withIdStringId: string): void {
    this.confirmationService.confirm({
      header: this.translateService.instant('entity.delete.title'),
      message: this.translateService.instant('primengtestApp.withIdStringDetails.delete.question', { id: withIdStringId }),
      accept: () => {
        this.withIdStringDetailsService.delete(withIdStringId).subscribe(() => {
          this.eventManager.broadcast({
            name: 'withIdStringDetailsListModification',
            content: 'Deleted an withIdStringDetails',
          });
        });
      },
    });
  }

  onWithIdStringLazyLoadEvent(event: LazyLoadEvent): void {
    this.withIdStringService
      .query(lazyLoadEventToServerQueryParams(event, 'id.contains'))
      .subscribe(res => (this.withIdStringOptions = res.body));
  }

  trackId(index: number, item: IWithIdStringDetails): string {
    return item.withIdStringId!;
  }

  protected onError(errorMessage: string): void {
    this.messageService.add({ severity: 'error', summary: errorMessage });
  }
}
