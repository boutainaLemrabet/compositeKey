import { Component, OnInit, Optional } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { lazyLoadEventToServerQueryParams } from 'app/shared/util/request-util';
import { LazyLoadEvent } from 'primeng/api';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IEmployeeSkill } from 'app/shared/model/employee-skill.model';
import { EmployeeSkillService } from './employee-skill.service';
import { MessageService } from 'primeng/api';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ITask } from 'app/shared/model/task.model';
import { TaskService } from 'app/entities/task/task.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';

@Component({
  selector: 'jhi-employee-skill-update',
  templateUrl: './employee-skill-update.component.html',
})
export class EmployeeSkillUpdateComponent implements OnInit {
  edit = false;
  isSaving = false;
  Options: I[] | null = null;
  Options: I[] | null = null;
  FilterValue?: any;
  Options: I[] | null = null;
  FilterValue?: any;

  editForm = this.fb.group({
    name: [null, [Validators.required]],
    level: [null, [Validators.required]],
    tasks: [],
    employee: [null, Validators.required],
    teacher: [null, Validators.required],
  });

  constructor(
    protected messageService: MessageService,
    protected employeeSkillService: EmployeeSkillService,
    protected taskService: TaskService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ employeeSkill }) => {
      this.updateForm(employeeSkill);
    });
  }

  onLazyLoadEvent(event: LazyLoadEvent): void {
    this.Service.query(lazyLoadEventToServerQueryParams(event, '.contains')).subscribe(
      (res: HttpResponse<I[]>) => (this.Options = res.body),
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  onLazyLoadEvent(event: LazyLoadEvent): void {
    this.Service.query(lazyLoadEventToServerQueryParams(event, '.contains')).subscribe(
      (res: HttpResponse<I[]>) => (this.Options = res.body),
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  onLazyLoadEvent(event: LazyLoadEvent): void {
    this.Service.query(lazyLoadEventToServerQueryParams(event, '.contains')).subscribe(
      (res: HttpResponse<I[]>) => (this.Options = res.body),
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  updateForm(employeeSkill: IEmployeeSkill | null): void {
    if (employeeSkill) {
      this.edit = true;
      this.editForm.reset({ ...employeeSkill });
      this.FilterValue = employeeSkill.employeeUsername;
      this.FilterValue = employeeSkill.teacherUsername;
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
    const employeeSkill = this.editForm.value;
    if (this.edit) {
      this.subscribeToSaveResponse(this.employeeSkillService.update(employeeSkill));
    } else {
      this.subscribeToSaveResponse(this.employeeSkillService.create(employeeSkill));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployeeSkill>>): void {
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

  trackById(index: number, item: ITask): number {
    return item.id!;
  }

  trackByUsername(index: number, item: IEmployee): string {
    return item.username!;
  }
}
