import { IUser } from 'app/core/user/user.model';
import { IEmployeeSkill } from 'app/shared/model/employee-skill.model';
import { TaskType } from 'app/shared/model/enumerations/task-type.model';

export interface ITask {
  id?: number;
  name?: string;
  type?: TaskType;
  endDate?: Date;
  createdAt?: Date;
  modifiedAt?: Date;
  done?: boolean;
  description?: string;
  attachmentContentType?: string;
  attachment?: string;
  pictureContentType?: string;
  picture?: string;
  user?: IUser;
  employeeSkills?: IEmployeeSkill[];
}

export class Task implements ITask {
  constructor(
    public id?: number,
    public name?: string,
    public type?: TaskType,
    public endDate?: Date,
    public createdAt?: Date,
    public modifiedAt?: Date,
    public done?: boolean,
    public description?: string,
    public attachmentContentType?: string,
    public attachment?: string,
    public pictureContentType?: string,
    public picture?: string,
    public user?: IUser,
    public employeeSkills?: IEmployeeSkill[]
  ) {}
}
