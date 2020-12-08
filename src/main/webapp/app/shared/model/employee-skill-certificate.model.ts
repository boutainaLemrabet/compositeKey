import { ICertificateType } from 'app/shared/model/certificate-type.model';
import { IEmployeeSkill } from 'app/shared/model/employee-skill.model';

export interface IEmployeeSkillCertificate {
  grade?: number;
  date?: Date;
  type?: ICertificateType;
  skill?: IEmployeeSkill;
}

export class EmployeeSkillCertificate implements IEmployeeSkillCertificate {
  constructor(public grade?: number, public date?: Date, public type?: ICertificateType, public skill?: IEmployeeSkill) {}
}
