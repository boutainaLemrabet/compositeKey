import { ITask } from 'app/shared/model/task.model';

export interface ITaskComment {
  id?: number;
  value?: string;
  task?: ITask;
}

export class TaskComment implements ITaskComment {
  constructor(public id?: number, public value?: string, public task?: ITask) {}
}
