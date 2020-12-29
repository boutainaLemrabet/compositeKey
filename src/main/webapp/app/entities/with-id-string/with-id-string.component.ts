import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';
import { MessageService } from 'primeng/api';
import { IWithIdString } from '../../shared/model/with-id-string.model';
import { WithIdStringService } from './with-id-string.service';
import { ConfirmationService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';

import { Table } from 'primeng/table';

@Component({
  selector: 'jhi-with-id-string',
  templateUrl: './with-id-string.component.html',
})
export class WithIdStringComponent implements OnInit, OnDestroy {
  withIdStrings?: IWithIdString[];
  eventSubscriber?: Subscription;

  private filtersDetails: { [_: string]: { matchMode?: string; flatten?: (_: string[]) => string; unflatten?: (_: string) => any } } = {
    id: { matchMode: 'contains' },
  };

  @ViewChild('withIdStringTable', { static: true })
  withIdStringTable!: Table;

  constructor(
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
    this.withIdStringService
      .query()
      .pipe(
        filter((res: HttpResponse<IWithIdString[]>) => res.ok),
        map((res: HttpResponse<IWithIdString[]>) => res.body!)
      )
      .subscribe(
        (withIdStrings: IWithIdString[]) => {
          this.withIdStrings = withIdStrings;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  filter(value: any, field: string): void {
    this.withIdStringTable.filter(value, field, this.filtersDetails[field].matchMode);
  }

  delete(id: string): void {
    this.confirmationService.confirm({
      header: this.translateService.instant('entity.delete.title'),
      message: this.translateService.instant('compositekeyApp.withIdString.delete.question', { id }),
      accept: () => {
        this.withIdStringService.delete(id).subscribe(() => {
          this.eventManager.broadcast({
            name: 'withIdStringListModification',
            content: 'Deleted an withIdString',
          });
        });
      },
    });
  }

  trackId(index: number, item: IWithIdString): string {
    return item.id!;
  }

  protected onError(errorMessage: string): void {
    this.messageService.add({ severity: 'error', summary: errorMessage });
  }
}
