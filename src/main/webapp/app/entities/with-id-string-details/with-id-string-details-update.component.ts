import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { lazyLoadEventToServerQueryParams } from '../../core/request/request-util';
import { LazyLoadEvent } from 'primeng/api';
import { IWithIdStringDetails } from '../../shared/model/with-id-string-details.model';
import { WithIdStringDetailsService } from './with-id-string-details.service';
import { MessageService } from 'primeng/api';
import { DataUtils } from '../../core/util/data-util.service';
import { IWithIdString } from '../../shared/model/with-id-string.model';
import { WithIdStringService } from '../with-id-string/with-id-string.service';

@Component({
  selector: 'jhi-with-id-string-details-update',
  templateUrl: './with-id-string-details-update.component.html',
})
export class WithIdStringDetailsUpdateComponent implements OnInit {
  isSaving = false;
  withIdStringOptions: IWithIdString[] | null = null;
  withIdStringFilterValue?: any;

  editForm = this.fb.group({
    withIdStringId: [],
    name: [],
    withIdString: [],
  });

  constructor(
    protected messageService: MessageService,
    protected withIdStringDetailsService: WithIdStringDetailsService,
    protected withIdStringService: WithIdStringService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ withIdStringDetails }) => {
      this.updateForm(withIdStringDetails);
    });
  }

  onWithIdStringLazyLoadEvent(event: LazyLoadEvent): void {
    this.withIdStringService.query(lazyLoadEventToServerQueryParams(event, 'id.contains')).subscribe(
      (res: HttpResponse<IWithIdString[]>) => (this.withIdStringOptions = res.body),
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  updateForm(withIdStringDetails: IWithIdStringDetails | null): void {
    if (withIdStringDetails) {
      this.editForm.reset({ ...withIdStringDetails });
    } else {
      this.editForm.reset({});
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const withIdStringDetails = this.editForm.value;
    if (withIdStringDetails.withIdStringId !== null) {
      this.subscribeToSaveResponse(this.withIdStringDetailsService.update(withIdStringDetails));
    } else {
      this.subscribeToSaveResponse(this.withIdStringDetailsService.create(withIdStringDetails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWithIdStringDetails>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IWithIdString): string {
    return item.id!;
  }

  protected onError(errorMessage: string): void {
    this.messageService.add({ severity: 'error', summary: errorMessage });
  }
}
