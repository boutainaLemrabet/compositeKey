import { ComponentFixture, TestBed, fakeAsync } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { compositekeyAppTestModule } from '../../../test.module';
import { CertificateTypeComponent } from 'app/entities/certificate-type/certificate-type.component';
import { CertificateTypeService } from 'app/entities/certificate-type/certificate-type.service';
import { CertificateType } from 'app/shared/model/certificate-type.model';
import { ConfirmationService } from 'primeng/api';
import { JhiEventManager } from 'ng-jhipster';

describe('Component Tests', () => {
  describe('CertificateType Management Component', () => {
    let comp: CertificateTypeComponent;
    let fixture: ComponentFixture<CertificateTypeComponent>;
    let service: CertificateTypeService;
    let mockConfirmationService: any;
    let mockEventManager: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CertificateTypeComponent],
      })
        .overrideTemplate(CertificateTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CertificateTypeComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(CertificateTypeService);
      mockConfirmationService = fixture.debugElement.injector.get(ConfirmationService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
    });

    it('Should call load all on init', fakeAsync(() => {
      // GIVEN
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [{ id: 123 }],
          })
        )
      );

      // WHEN
      fixture.detectChanges();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.certificateTypes?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    }));

    it('should call delete service using confirmDialog', fakeAsync(() => {
      // GIVEN
      spyOn(service, 'delete').and.returnValue(of({}));

      // WHEN
      comp.delete(123);

      // THEN
      expect(mockConfirmationService.confirmSpy).toHaveBeenCalled();
      expect(service.delete).toHaveBeenCalledWith(123);
      expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
    }));
  });
});
