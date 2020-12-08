import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEmployeeSkill } from '../../shared/model/employee-skill.model';
import { EmployeeSkillService } from './employee-skill.service';

@Injectable({ providedIn: 'root' })
export class EmployeeSkillRoutingResolveService implements Resolve<IEmployeeSkill | null> {
  constructor(private service: EmployeeSkillService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmployeeSkill | null> | Observable<never> {
    const name = route.params['name'] ? route.params['name'] : null;
    const employeeUsername = route.params['employeeUsername'] ? route.params['employeeUsername'] : null;
    if (name && employeeUsername) {
      return this.service.find(name, employeeUsername).pipe(
        mergeMap((employeeSkill: HttpResponse<IEmployeeSkill>) => {
          if (employeeSkill.body) {
            return of(employeeSkill.body);
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
