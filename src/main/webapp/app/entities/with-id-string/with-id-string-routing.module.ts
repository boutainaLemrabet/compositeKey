import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { Authority } from '../../core/user/authority.model';
import { UserRouteAccessService } from '../../core/auth/user-route-access.service';
import { WithIdStringComponent } from './with-id-string.component';
import { WithIdStringDetailComponent } from './with-id-string-detail.component';
import { WithIdStringUpdateComponent } from './with-id-string-update.component';
import { WithIdStringRoutingResolveService } from './with-id-string-routing-resolve.service';
import { WithIdStringModule } from './with-id-string.module';

const withIdStringRoute: Routes = [
  {
    path: '',
    component: WithIdStringComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'compositekeyApp.withIdString.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WithIdStringDetailComponent,
    resolve: {
      withIdString: WithIdStringRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'compositekeyApp.withIdString.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WithIdStringUpdateComponent,
    resolve: {
      withIdString: WithIdStringRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'compositekeyApp.withIdString.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WithIdStringUpdateComponent,
    resolve: {
      withIdString: WithIdStringRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'compositekeyApp.withIdString.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(withIdStringRoute), WithIdStringModule],
  exports: [WithIdStringModule],
})
export class WithIdStringRoutingModule {}
