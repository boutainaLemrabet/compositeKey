import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { TaskComponent } from './task.component';
import { TaskDetailComponent } from './task-detail.component';
import { TaskUpdateComponent } from './task-update.component';

@NgModule({
  imports: [SharedModule, RouterModule],
  declarations: [TaskComponent, TaskDetailComponent, TaskUpdateComponent],
  exports: [TaskDetailComponent, TaskUpdateComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class TaskModule {}
