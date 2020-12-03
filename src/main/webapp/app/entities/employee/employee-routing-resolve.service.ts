import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from './employee.service';

@Injectable({ providedIn: 'root' })
export class EmployeeRoutingResolveService implements Resolve<IEmployee | null> {
  constructor(private service: EmployeeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmployee | null> | Observable<never> {
    const username = route.params['username'] ? route.params['username'] : null;
    if (username) {
      return this.service.find(username).pipe(
        mergeMap((employee: HttpResponse<IEmployee>) => {
          if (employee.body) {
            return of(employee.body);
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
