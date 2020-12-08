import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IWithIdString } from '../../shared/model/with-id-string.model';
import { WithIdStringService } from './with-id-string.service';
import { DataUtils } from '../../core/util/data-util.service';

@Component({
  selector: 'jhi-with-id-string-update',
  templateUrl: './with-id-string-update.component.html',
})
export class WithIdStringUpdateComponent implements OnInit {
  edit = false;
  isSaving = false;

  editForm = this.fb.group({
    id: [null, [Validators.required]],
  });

  constructor(protected withIdStringService: WithIdStringService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ withIdString }) => {
      this.updateForm(withIdString);
    });
  }

  updateForm(withIdString: IWithIdString | null): void {
    if (withIdString) {
      this.edit = true;
      this.editForm.reset({ ...withIdString });
    } else {
      this.edit = false;
      this.editForm.reset({});
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const withIdString = this.editForm.value;
    if (this.edit) {
      this.subscribeToSaveResponse(this.withIdStringService.update(withIdString));
    } else {
      this.subscribeToSaveResponse(this.withIdStringService.create(withIdString));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWithIdString>>): void {
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
}
