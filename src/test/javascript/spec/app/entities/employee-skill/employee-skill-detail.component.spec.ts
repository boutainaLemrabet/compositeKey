import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EmployeeSkillDetailComponent } from 'app/entities/employee-skill/employee-skill-detail.component';

describe('Component Tests', () => {
  describe('EmployeeSkill Management Detail Component', () => {
    let comp: EmployeeSkillDetailComponent;
    let fixture: ComponentFixture<EmployeeSkillDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [EmployeeSkillDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ employeeSkill: { name: "'123'", employee: { username: "'123'" } } }) },
          },
        ],
      })
        .overrideTemplate(EmployeeSkillDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmployeeSkillDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load employeeSkill on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.employeeSkill).toEqual(jasmine.objectContaining({ name: "'123'", employee: { username: "'123'" } }));
      });
    });
  });
});
