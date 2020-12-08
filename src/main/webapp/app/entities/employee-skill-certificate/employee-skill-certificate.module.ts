import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { EmployeeSkillCertificateComponent } from './employee-skill-certificate.component';
import { EmployeeSkillCertificateDetailComponent } from './employee-skill-certificate-detail.component';
import { EmployeeSkillCertificateUpdateComponent } from './employee-skill-certificate-update.component';

@NgModule({
  imports: [SharedModule, RouterModule],
  declarations: [EmployeeSkillCertificateComponent, EmployeeSkillCertificateDetailComponent, EmployeeSkillCertificateUpdateComponent],
  exports: [EmployeeSkillCertificateDetailComponent, EmployeeSkillCertificateUpdateComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class EmployeeSkillCertificateModule {}
