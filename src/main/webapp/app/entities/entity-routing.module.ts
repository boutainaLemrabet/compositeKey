import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'employee',
        loadChildren: () => import('./employee/employee-routing.module').then(m => m.EmployeeRoutingModule),
      },
      {
        path: 'employee-skill',
        loadChildren: () => import('./employee-skill/employee-skill-routing.module').then(m => m.EmployeeSkillRoutingModule),
      },
      {
        path: 'certificate-type',
        loadChildren: () => import('./certificate-type/certificate-type-routing.module').then(m => m.CertificateTypeRoutingModule),
      },
      {
        path: 'employee-skill-certificate',
        loadChildren: () =>
          import('./employee-skill-certificate/employee-skill-certificate-routing.module').then(
            m => m.EmployeeSkillCertificateRoutingModule
          ),
      },
      {
        path: 'task',
        loadChildren: () => import('./task/task-routing.module').then(m => m.TaskRoutingModule),
      },
      {
        path: 'task-comment',
        loadChildren: () => import('./task-comment/task-comment-routing.module').then(m => m.TaskCommentRoutingModule),
      },
      {
        path: 'with-id-string',
        loadChildren: () => import('./with-id-string/with-id-string-routing.module').then(m => m.WithIdStringRoutingModule),
      },
      {
        path: 'with-id-string-details',
        loadChildren: () =>
          import('./with-id-string-details/with-id-string-details-routing.module').then(m => m.WithIdStringDetailsRoutingModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
