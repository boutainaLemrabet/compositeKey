import { Component, OnInit, Optional } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { lazyLoadEventToServerQueryParams } from 'app/shared/util/request-util';
import { LazyLoadEvent } from 'primeng/api';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from './employee.service';
import { MessageService } from 'primeng/api';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-employee-update',
  templateUrl: './employee-update.component.html',
})
export class EmployeeUpdateComponent implements OnInit {
  edit = false;
  isSaving = false;
  Options: I[] | null = null;
  FilterValue?: any;

  editForm = this.fb.group({
    username: [null, [Validators.required]],
    fullname: [null, [Validators.required]],
    manager: [],
  });

  constructor(
    protected messageService: MessageService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ employee }) => {
      this.updateForm(employee);
    });
  }

  onLazyLoadEvent(event: LazyLoadEvent): void {
    this.Service.query(lazyLoadEventToServerQueryParams(event, '.contains')).subscribe(
      (res: HttpResponse<I[]>) => (this.Options = res.body),
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  updateForm(employee: IEmployee | null): void {
    if (employee) {
      this.edit = true;
      this.editForm.reset({ ...employee });
      this.FilterValue = employee.managerUsername;
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
    const employee = this.editForm.value;
    if (this.edit) {
      this.subscribeToSaveResponse(this.employeeService.update(employee));
    } else {
      this.subscribeToSaveResponse(this.employeeService.create(employee));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployee>>): void {
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

  trackByUsername(index: number, item: IEmployee): string {
    return item.username!;
  }
}
