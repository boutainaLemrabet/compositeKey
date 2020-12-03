import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PasswordComponent } from './password.component';
import { Authority } from 'app/core/user/authority.model';

export const passwordRoute: Route = {
  path: 'password',
  component: PasswordComponent,
  data: {
    authorities: [Authority.USER],
    pageTitle: 'global.menu.account.password',
  },
  canActivate: [UserRouteAccessService],
};
