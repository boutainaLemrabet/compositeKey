import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmployeeSkill } from 'app/shared/model/employee-skill.model';
import { EmployeeSkillService } from './employee-skill.service';

@Component({
  templateUrl: './employee-skill-delete-dialog.component.html',
})
export class EmployeeSkillDeleteDialogComponent {
  employeeSkill?: IEmployeeSkill;

  constructor(protected employeeSkillService: EmployeeSkillService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(name: string, employeeUsername: string): void {
    this.employeeSkillService.delete(name, employeeUsername).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
