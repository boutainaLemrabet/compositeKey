import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { Authority } from '../../core/user/authority.model';
import { UserRouteAccessService } from '../../core/auth/user-route-access.service';
import { WithIdStringDetailsComponent } from './with-id-string-details.component';
import { WithIdStringDetailsDetailComponent } from './with-id-string-details-detail.component';
import { WithIdStringDetailsUpdateComponent } from './with-id-string-details-update.component';
import { WithIdStringDetailsRoutingResolveService } from './with-id-string-details-routing-resolve.service';
import { WithIdStringDetailsModule } from './with-id-string-details.module';

const withIdStringDetailsRoute: Routes = [
  {
    path: '',
    component: WithIdStringDetailsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'compositekeyApp.withIdStringDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':withIdStringId/view',
    component: WithIdStringDetailsDetailComponent,
    resolve: {
      withIdStringDetails: WithIdStringDetailsRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'compositekeyApp.withIdStringDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WithIdStringDetailsUpdateComponent,
    resolve: {
      withIdStringDetails: WithIdStringDetailsRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'compositekeyApp.withIdStringDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':withIdStringId/edit',
    component: WithIdStringDetailsUpdateComponent,
    resolve: {
      withIdStringDetails: WithIdStringDetailsRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'compositekeyApp.withIdStringDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(withIdStringDetailsRoute), WithIdStringDetailsModule],
  exports: [WithIdStringDetailsModule],
})
export class WithIdStringDetailsRoutingModule {}
