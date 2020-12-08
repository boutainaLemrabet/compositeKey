import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { TaskCommentComponent } from './task-comment.component';
import { TaskCommentDetailComponent } from './task-comment-detail.component';
import { TaskCommentUpdateComponent } from './task-comment-update.component';

@NgModule({
  imports: [SharedModule, RouterModule],
  declarations: [TaskCommentComponent, TaskCommentDetailComponent, TaskCommentUpdateComponent],
  exports: [TaskCommentDetailComponent, TaskCommentUpdateComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class TaskCommentModule {}
