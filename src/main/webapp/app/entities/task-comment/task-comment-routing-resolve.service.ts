import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITaskComment } from '../../shared/model/task-comment.model';
import { TaskCommentService } from './task-comment.service';

@Injectable({ providedIn: 'root' })
export class TaskCommentRoutingResolveService implements Resolve<ITaskComment | null> {
  constructor(private service: TaskCommentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITaskComment | null> | Observable<never> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((taskComment: HttpResponse<ITaskComment>) => {
          if (taskComment.body) {
            return of(taskComment.body);
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
