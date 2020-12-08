import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { lazyLoadEventToServerQueryParams } from '../../core/request/request-util';
import { LazyLoadEvent } from 'primeng/api';
import { IEmployeeSkillCertificate } from '../../shared/model/employee-skill-certificate.model';
import { EmployeeSkillCertificateService } from './employee-skill-certificate.service';
import { MessageService } from 'primeng/api';
import { DataUtils } from '../../core/util/data-util.service';
import { ICertificateType } from '../../shared/model/certificate-type.model';
import { CertificateTypeService } from '../certificate-type/certificate-type.service';
import { IEmployeeSkill } from '../../shared/model/employee-skill.model';
import { EmployeeSkillService } from '../employee-skill/employee-skill.service';

@Component({
  selector: 'jhi-employee-skill-certificate-update',
  templateUrl: './employee-skill-certificate-update.component.html',
})
export class EmployeeSkillCertificateUpdateComponent implements OnInit {
  edit = false;
  isSaving = false;
  typeOptions: ICertificateType[] | null = null;
  skillOptions: IEmployeeSkill[] | null = null;
  skillFilterValue?: any;

  editForm = this.fb.group({
    grade: [null, [Validators.required]],
    date: [null, [Validators.required]],
    type: [null, Validators.required],
    skill: [null, Validators.required],
  });

  constructor(
    protected messageService: MessageService,
    protected employeeSkillCertificateService: EmployeeSkillCertificateService,
    protected certificateTypeService: CertificateTypeService,
    protected employeeSkillService: EmployeeSkillService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ employeeSkillCertificate }) => {
      this.updateForm(employeeSkillCertificate);
    });
  }

  onTypeLazyLoadEvent(event: LazyLoadEvent): void {
    this.certificateTypeService.query(lazyLoadEventToServerQueryParams(event, 'id.contains')).subscribe(
      (res: HttpResponse<ICertificateType[]>) => (this.typeOptions = res.body),
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  onSkillLazyLoadEvent(event: LazyLoadEvent): void {
    this.employeeSkillService.query(lazyLoadEventToServerQueryParams(event, 'id.contains')).subscribe(
      (res: HttpResponse<IEmployeeSkill[]>) => (this.skillOptions = res.body),
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  updateForm(employeeSkillCertificate: IEmployeeSkillCertificate | null): void {
    if (employeeSkillCertificate) {
      this.edit = true;
      this.editForm.reset({ ...employeeSkillCertificate });
      this.skillFilterValue = employeeSkillCertificate.skill!.name!;
      this.skillFilterValue = employeeSkillCertificate.skill!.employee!.username!;
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

  trackById(index: number, item: ICertificateType): number {
    return item.id!;
  }

  trackByEmployeeSkillId(index: number, item: IEmployeeSkill): string {
    return `${item.name!},${item.employee!.username!}`;
  }

  protected onError(errorMessage: string): void {
    this.messageService.add({ severity: 'error', summary: errorMessage });
  }
}
