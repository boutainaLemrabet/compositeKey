import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { EmployeeSkillComponent } from './employee-skill.component';
import { EmployeeSkillDetailComponent } from './employee-skill-detail.component';
import { EmployeeSkillUpdateComponent } from './employee-skill-update.component';

@NgModule({
  imports: [SharedModule, RouterModule],
  declarations: [EmployeeSkillComponent, EmployeeSkillDetailComponent, EmployeeSkillUpdateComponent],
  exports: [EmployeeSkillDetailComponent, EmployeeSkillUpdateComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class EmployeeSkillModule {}
