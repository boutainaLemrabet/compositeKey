import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { of, BehaviorSubject } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute, Router } from '@angular/router';
import { compositekeyAppTestModule } from '../../../test.module';
import { EmployeeSkillCertificateComponent } from 'app/entities/employee-skill-certificate/employee-skill-certificate.component';
import { EmployeeSkillCertificateService } from 'app/entities/employee-skill-certificate/employee-skill-certificate.service';
import { EmployeeSkillCertificate } from 'app/shared/model/employee-skill-certificate.model';
import { ConfirmationService } from 'primeng/api';

import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MockTable } from '../../../helpers/mock-table';
import { JhiEventManager } from 'ng-jhipster';

describe('Component Tests', () => {
  describe('EmployeeSkillCertificate Management Component', () => {
    let comp: EmployeeSkillCertificateComponent;
    let fixture: ComponentFixture<EmployeeSkillCertificateComponent>;
    let service: EmployeeSkillCertificateService;
    let mockConfirmationService: any;

    let activatedRoute: MockActivatedRoute;
    let mockEventManager: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EmployeeSkillCertificateComponent],
      })
        .overrideTemplate(EmployeeSkillCertificateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmployeeSkillCertificateComponent);
      comp = fixture.componentInstance;
      comp.employeeSkillCertificateTable = new MockTable() as any;
      service = TestBed.inject(EmployeeSkillCertificateService);
      mockConfirmationService = fixture.debugElement.injector.get(ConfirmationService);
      activatedRoute = fixture.debugElement.injector.get(ActivatedRoute);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
    });

    it('Should call load all on init', fakeAsync(() => {
      // GIVEN
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [{ type: { id: 123 }, skill: { name: "'123'", employee: { username: "'123'" } } }],
          })
        )
      );

      // WHEN
      fixture.detectChanges();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.employeeSkillCertificates?.[0]).toEqual(
        jasmine.objectContaining({ type: { id: 123 }, skill: { name: "'123'", employee: { username: "'123'" } } })
      );
    }));

    it('should load a page', fakeAsync(() => {
      // GIVEN
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [{ type: { id: 123 }, skill: { name: "'123'", employee: { username: "'123'" } } }],
          })
        )
      );

      // WHEN
      fixture.detectChanges();
      tick(100);
      (activatedRoute.queryParams as BehaviorSubject<any>).next({ first: 3 });

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.employeeSkillCertificates?.[0]).toEqual(
        jasmine.objectContaining({ type: { id: 123 }, skill: { name: "'123'", employee: { username: "'123'" } } })
      );
    }));

    it('should call delete service using confirmDialog', fakeAsync(() => {
      // GIVEN
      spyOn(service, 'delete').and.returnValue(of({}));

      // WHEN
      comp.delete(123, '123', '123');

      // THEN
      expect(mockConfirmationService.confirmSpy).toHaveBeenCalled();
      expect(service.delete).toHaveBeenCalledWith(123, '123', '123');
      expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
    }));
  });
});
