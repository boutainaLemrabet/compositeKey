jest.mock('@angular/router');

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { MessageService } from 'primeng/api';
import { of } from 'rxjs';
import { DatePipe } from '@angular/common';

import { EmployeeSkillCertificateUpdateComponent } from 'app/entities/employee-skill-certificate/employee-skill-certificate-update.component';
import { EmployeeSkillCertificateService } from 'app/entities/employee-skill-certificate/employee-skill-certificate.service';

describe('Component Tests', () => {
  describe('EmployeeSkillCertificate Management Update Component', () => {
    let comp: EmployeeSkillCertificateUpdateComponent;
    let fixture: ComponentFixture<EmployeeSkillCertificateUpdateComponent>;
    let service: EmployeeSkillCertificateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EmployeeSkillCertificateUpdateComponent],
        providers: [FormBuilder, ActivatedRoute, MessageService, DatePipe],
      })
        .overrideTemplate(EmployeeSkillCertificateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmployeeSkillCertificateUpdateComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(EmployeeSkillCertificateService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = { type: { id: 123 }, skill: { name: "'123'", employee: { username: "'123'" } } };
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(jasmine.objectContaining(entity));
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: null })));
        comp.updateForm(null);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(comp.editForm.value);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
