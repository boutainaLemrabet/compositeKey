import { IEmployeeSkillCertificate } from 'app/shared/model/employee-skill-certificate.model';
import { ITask } from 'app/shared/model/task.model';
import { IEmployee } from 'app/shared/model/employee.model';

export interface IEmployeeSkill {
  name?: string;
  level?: number;
  employeeSkillCertificates?: IEmployeeSkillCertificate[];
  tasks?: ITask[];
  employee?: IEmployee;
  teacher?: IEmployee;
}

export class EmployeeSkill implements IEmployeeSkill {
  constructor(
    public name?: string,
    public level?: number,
    public employeeSkillCertificates?: IEmployeeSkillCertificate[],
    public tasks?: ITask[],
    public employee?: IEmployee,
    public teacher?: IEmployee
  ) {}
}
