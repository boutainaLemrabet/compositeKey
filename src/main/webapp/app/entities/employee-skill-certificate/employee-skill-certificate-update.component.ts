import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable, of, Subject } from 'rxjs';
import { lazyLoadEventToServerQueryParams } from '../../core/request/request-util';
import { LazyLoadEvent } from 'primeng/api';
import { IEmployeeSkillCertificate } from '../../shared/model/employee-skill-certificate.model';
import { EmployeeSkillCertificateService } from './employee-skill-certificate.service';
import { MessageService } from 'primeng/api';
import { ICertificateType } from '../../shared/model/certificate-type.model';
import { CertificateTypeService } from '../certificate-type/certificate-type.service';
import { IEmployeeSkill } from '../../shared/model/employee-skill.model';
import { EmployeeSkillService } from '../employee-skill/employee-skill.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from '../employee/employee.service';
import { switchMap, takeUntil, filter, map, catchError } from 'rxjs/operators';

@Component({
  selector: 'jhi-employee-skill-certificate-update',
  templateUrl: './employee-skill-certificate-update.component.html',
})
export class EmployeeSkillCertificateUpdateComponent implements OnInit, OnDestroy {
  edit = false;
  isSaving = false;
  typeOptions?: ICertificateType[] = undefined;
  skillOptions?: IEmployeeSkill[] = undefined;
  employeeOptions?: IEmployee[] = undefined;

  onDestroySubject = new Subject<void>();

  editForm = this.fb.group({
    grade: [null, [Validators.required]],
    date: [null, [Validators.required]],
    type: this.fb.group({
      id: [null, [Validators.required]],
    }),
    skill: this.fb.group({
      name: [null, [Validators.required]],
      employee: this.fb.group({
        username: [null, [Validators.required]],
      }),
    }),
  });

  constructor(
    protected messageService: MessageService,
    protected employeeSkillCertificateService: EmployeeSkillCertificateService,
    protected certificateTypeService: CertificateTypeService,
    protected employeeSkillService: EmployeeSkillService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.employeeForm
      .get('username')!
      .valueChanges.pipe(
        takeUntil(this.onDestroySubject),
        filter(() => !this.edit), // because we always subscribe even if not edit
        switchMap(username => {
          if (!username) {
            return of(undefined);
          }
          return this.employeeSkillService.query({ 'employee.username.equals': username }).pipe(
            map((res: HttpResponse<IEmployeeSkill[]>) => res.body!),
            catchError(err => {
              this.onError(err.message);
              return of(undefined);
            })
          );
        })
      )
      .subscribe((skillOptions?: IEmployeeSkill[]) => {
        this.skillOptions = skillOptions;
        this.skillForm.get('name')!.reset();
      });
    this.activatedRoute.data.subscribe(({ employeeSkillCertificate }) => {
      this.updateForm(employeeSkillCertificate);
    });
  }

  get typeForm(): FormGroup {
    return this.editForm.get('type')! as FormGroup;
  }

  get skillForm(): FormGroup {
    return this.editForm.get('skill')! as FormGroup;
  }

  get employeeForm(): FormGroup {
    return this.skillForm.get('employee')! as FormGroup;
  }

  ngOnDestroy(): void {
    this.onDestroySubject.next(undefined);
    this.onDestroySubject.complete();
  }

  onTypeLazyLoadEvent(event: LazyLoadEvent): void {
    this.certificateTypeService.query(lazyLoadEventToServerQueryParams(event, 'globalFilter')).subscribe(
      (res: HttpResponse<ICertificateType[]>) => (this.typeOptions = res.body!),
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  onSkillLazyLoadEvent(event: LazyLoadEvent): void {
    this.employeeSkillService
      .query({
        ...lazyLoadEventToServerQueryParams(event, 'globalFilter'),
        'employee.username.equals': this.employeeForm.value.username,
      })
      .subscribe(
        (res: HttpResponse<IEmployeeSkill[]>) => (this.skillOptions = res.body!),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  onEmployeeLazyLoadEvent(event: LazyLoadEvent): void {
    this.employeeService.query(lazyLoadEventToServerQueryParams(event, 'globalFilter')).subscribe(
      (res: HttpResponse<IEmployee[]>) => (this.employeeOptions = res.body!),
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  updateForm(employeeSkillCertificate: IEmployeeSkillCertificate | null): void {
    if (employeeSkillCertificate) {
      this.edit = true;
      this.editForm.reset({ ...employeeSkillCertificate });
      this.typeOptions = [employeeSkillCertificate.type!];
      this.employeeOptions = [employeeSkillCertificate.skill!.employee!];
      this.skillOptions = [employeeSkillCertificate.skill!];
    } else {
      this.edit = false;
      this.editForm.reset({
        date: new Date(),
      });
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employeeSkillCertificate = this.editForm.value;
    if (this.edit) {
      this.subscribeToSaveResponse(this.employeeSkillCertificateService.update(employeeSkillCertificate));
    } else {
      this.subscribeToSaveResponse(this.employeeSkillCertificateService.create(employeeSkillCertificate));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployeeSkillCertificate>>): void {
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

  protected onError(errorMessage: string): void {
    this.messageService.add({ severity: 'error', summary: errorMessage });
  }
}
