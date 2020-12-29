import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { Table, TableModule } from 'primeng/table';
import { of, BehaviorSubject } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute, Router } from '@angular/router';
import { EmployeeComponent } from 'app/entities/employee/employee.component';
import { EmployeeService } from 'app/entities/employee/employee.service';
import { Employee } from 'app/shared/model/employee.model';
import { ConfirmationService } from 'primeng/api';

import { JhiEventManager } from 'ng-jhipster';

describe('Component Tests', () => {
  describe('Employee Management Component', () => {
    let comp: EmployeeComponent;
    let fixture: ComponentFixture<EmployeeComponent>;
    let service: EmployeeService;
    let mockConfirmationService: any;

    let activatedRoute: ActivatedRoute;
    let mockEventManager: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule, TableModule],
        declarations: [EmployeeComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: of(),
          },
        ],
      })
        .overrideTemplate(EmployeeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmployeeComponent);
      comp = fixture.componentInstance;
      comp.employeeTable = fixture.debugElement.children[0].componentInstance;
      service = TestBed.inject(EmployeeService);
      mockConfirmationService = fixture.debugElement.injector.get(ConfirmationService);
      activatedRoute = fixture.debugElement.injector.get(ActivatedRoute);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
    });

    it('Should call load all on init', fakeAsync(() => {
      // GIVEN
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [{ username: "'123'" }],
          })
        )
      );

      // WHEN
      fixture.detectChanges();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.employees?.[0]).toEqual(jasmine.objectContaining({ username: "'123'" }));
    }));

    it('should load a page', fakeAsync(() => {
      // GIVEN
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [{ username: "'123'" }],
          })
        )
      );

      // WHEN
      fixture.detectChanges();
      tick(100);
      (activatedRoute.queryParams as BehaviorSubject<any>).next({ first: 3 });

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.employees?.[0]).toEqual(jasmine.objectContaining({ username: "'123'" }));
    }));

    it('should call delete service using confirmDialog', fakeAsync(() => {
      // GIVEN
      spyOn(service, 'delete').and.returnValue(of({}));

      // WHEN
      comp.delete('123');

      // THEN
      expect(mockConfirmationService.confirmSpy).toHaveBeenCalled();
      expect(service.delete).toHaveBeenCalledWith('123');
      expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
    }));
  });
});
