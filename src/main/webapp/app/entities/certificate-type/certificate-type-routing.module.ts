import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { Authority } from '../../core/user/authority.model';
import { UserRouteAccessService } from '../../core/auth/user-route-access.service';
import { CertificateTypeComponent } from './certificate-type.component';
import { CertificateTypeDetailComponent } from './certificate-type-detail.component';
import { CertificateTypeUpdateComponent } from './certificate-type-update.component';
import { CertificateTypeRoutingResolveService } from './certificate-type-routing-resolve.service';
import { CertificateTypeModule } from './certificate-type.module';

const certificateTypeRoute: Routes = [
  {
    path: '',
    component: CertificateTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'compositekeyApp.certificateType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CertificateTypeDetailComponent,
    resolve: {
      certificateType: CertificateTypeRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'compositekeyApp.certificateType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CertificateTypeUpdateComponent,
    resolve: {
      certificateType: CertificateTypeRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'compositekeyApp.certificateType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CertificateTypeUpdateComponent,
    resolve: {
      certificateType: CertificateTypeRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'compositekeyApp.certificateType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(certificateTypeRoute), CertificateTypeModule],
  exports: [CertificateTypeModule],
})
export class CertificateTypeRoutingModule {}
