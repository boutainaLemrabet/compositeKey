import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from './employee.service';

@Component({
  templateUrl: './employee-delete-dialog.component.html',
})
export class EmployeeDeleteDialogComponent {
  employee?: IEmployee;

  constructor(protected employeeService: EmployeeService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(username: string): void {
    this.employeeService.delete(username).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
