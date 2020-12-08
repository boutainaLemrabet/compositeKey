import { ComponentFixture, TestBed, fakeAsync } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { compositekeyAppTestModule } from '../../../test.module';
import { WithIdStringComponent } from 'app/entities/with-id-string/with-id-string.component';
import { WithIdStringService } from 'app/entities/with-id-string/with-id-string.service';
import { WithIdString } from 'app/shared/model/with-id-string.model';
import { ConfirmationService } from 'primeng/api';
import { JhiEventManager } from 'ng-jhipster';

describe('Component Tests', () => {
  describe('WithIdString Management Component', () => {
    let comp: WithIdStringComponent;
    let fixture: ComponentFixture<WithIdStringComponent>;
    let service: WithIdStringService;
    let mockConfirmationService: any;
    let mockEventManager: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [WithIdStringComponent],
      })
        .overrideTemplate(WithIdStringComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WithIdStringComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(WithIdStringService);
      mockConfirmationService = fixture.debugElement.injector.get(ConfirmationService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
    });

    it('Should call load all on init', fakeAsync(() => {
      // GIVEN
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [{ id: "'123'" }],
          })
        )
      );

      // WHEN
      fixture.detectChanges();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.withIdStrings?.[0]).toEqual(jasmine.objectContaining({ id: "'123'" }));
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
