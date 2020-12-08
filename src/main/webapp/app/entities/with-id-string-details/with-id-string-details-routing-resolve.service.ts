import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IWithIdStringDetails } from '../../shared/model/with-id-string-details.model';
import { WithIdStringDetailsService } from './with-id-string-details.service';

@Injectable({ providedIn: 'root' })
export class WithIdStringDetailsRoutingResolveService implements Resolve<IWithIdStringDetails | null> {
  constructor(private service: WithIdStringDetailsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWithIdStringDetails | null> | Observable<never> {
    const withIdStringId = route.params['withIdStringId'] ? route.params['withIdStringId'] : null;
    if (withIdStringId) {
      return this.service.find(withIdStringId).pipe(
        mergeMap((withIdStringDetails: HttpResponse<IWithIdStringDetails>) => {
          if (withIdStringDetails.body) {
            return of(withIdStringDetails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
