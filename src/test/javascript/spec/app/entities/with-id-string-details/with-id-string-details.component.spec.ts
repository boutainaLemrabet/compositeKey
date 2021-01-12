jest.mock('@ngx-translate/core');

import { ComponentFixture, TestBed, fakeAsync } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ConfirmationService, MessageService, Confirmation } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';

import { WithIdStringDetailsComponent } from 'app/entities/with-id-string-details/with-id-string-details.component';
import { WithIdStringDetailsService } from 'app/entities/with-id-string-details/with-id-string-details.service';

describe('Component Tests', () => {
  describe('WithIdStringDetails Management Component', () => {
    let comp: WithIdStringDetailsComponent;
    let fixture: ComponentFixture<WithIdStringDetailsComponent>;
    let service: WithIdStringDetailsService;
    let confirmationService: ConfirmationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [WithIdStringDetailsComponent],
        providers: [ConfirmationService, MessageService, TranslateService],
      })
        .overrideTemplate(WithIdStringDetailsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WithIdStringDetailsComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(WithIdStringDetailsService);
      confirmationService = fixture.debugElement.injector.get(ConfirmationService);
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
      spyOn(confirmationService, 'confirm').and.callFake((confirmation: Confirmation) => {
        if (confirmation.accept) {
          confirmation.accept();
        }
      });

      // WHEN
      comp.delete('123');

      // THEN
      expect(confirmationService.confirm).toHaveBeenCalled();
      expect(service.delete).toHaveBeenCalledWith('123');
    }));
  });
});
