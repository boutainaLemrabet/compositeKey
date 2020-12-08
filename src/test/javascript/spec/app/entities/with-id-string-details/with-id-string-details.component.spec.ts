import { ComponentFixture, TestBed, fakeAsync } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { compositekeyAppTestModule } from '../../../test.module';
import { WithIdStringDetailsComponent } from 'app/entities/with-id-string-details/with-id-string-details.component';
import { WithIdStringDetailsService } from 'app/entities/with-id-string-details/with-id-string-details.service';
import { WithIdStringDetails } from 'app/shared/model/with-id-string-details.model';
import { ConfirmationService } from 'primeng/api';
import { JhiEventManager } from 'ng-jhipster';

describe('Component Tests', () => {
  describe('WithIdStringDetails Management Component', () => {
    let comp: WithIdStringDetailsComponent;
    let fixture: ComponentFixture<WithIdStringDetailsComponent>;
    let service: WithIdStringDetailsService;
    let mockConfirmationService: any;
    let mockEventManager: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [WithIdStringDetailsComponent],
      })
        .overrideTemplate(WithIdStringDetailsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WithIdStringDetailsComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(WithIdStringDetailsService);
      mockConfirmationService = fixture.debugElement.injector.get(ConfirmationService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
    });

    it('Should call load all on init', fakeAsync(() => {
      // GIVEN
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [{ withIdStringId: "'123'" }],
          })
        )
      );

      // WHEN
      fixture.detectChanges();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.withIdStringDetails?.[0]).toEqual(jasmine.objectContaining({ withIdStringId: "'123'" }));
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
