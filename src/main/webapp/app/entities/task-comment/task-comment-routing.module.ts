import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { Authority } from '../../core/user/authority.model';
import { UserRouteAccessService } from '../../core/auth/user-route-access.service';
import { TaskCommentComponent } from './task-comment.component';
import { TaskCommentDetailComponent } from './task-comment-detail.component';
import { TaskCommentUpdateComponent } from './task-comment-update.component';
import { TaskCommentRoutingResolveService } from './task-comment-routing-resolve.service';
import { TaskCommentModule } from './task-comment.module';

const taskCommentRoute: Routes = [
  {
    path: '',
    component: TaskCommentComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'compositekeyApp.taskComment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TaskCommentDetailComponent,
    resolve: {
      taskComment: TaskCommentRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'compositekeyApp.taskComment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TaskCommentUpdateComponent,
    resolve: {
      taskComment: TaskCommentRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'compositekeyApp.taskComment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TaskCommentUpdateComponent,
    resolve: {
      taskComment: TaskCommentRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'compositekeyApp.taskComment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(taskCommentRoute), TaskCommentModule],
  exports: [TaskCommentModule],
})
export class TaskCommentRoutingModule {}
