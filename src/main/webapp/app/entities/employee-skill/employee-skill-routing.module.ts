import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { Authority } from 'app/core/user/authority.model';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EmployeeSkillComponent } from './employee-skill.component';
import { EmployeeSkillDetailComponent } from './employee-skill-detail.component';
import { EmployeeSkillUpdateComponent } from './employee-skill-update.component';
import { EmployeeSkillRoutingResolveService } from './employee-skill-routing-resolve.service';
import { EmployeeSkillModule } from './employee-skill.module';

const employeeSkillRoute: Routes = [
  {
    path: '',
    component: EmployeeSkillComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: '',
      pageTitle: 'compositekeyApp.employeeSkill.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'view',
    component: EmployeeSkillDetailComponent,
    resolve: {
      employeeSkill: EmployeeSkillRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'compositekeyApp.employeeSkill.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmployeeSkillUpdateComponent,
    resolve: {
      employeeSkill: EmployeeSkillRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'compositekeyApp.employeeSkill.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'edit',
    component: EmployeeSkillUpdateComponent,
    resolve: {
      employeeSkill: EmployeeSkillRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'compositekeyApp.employeeSkill.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(employeeSkillRoute), EmployeeSkillModule],
  exports: [EmployeeSkillModule],
})
export class EmployeeSkillRoutingModule {}
