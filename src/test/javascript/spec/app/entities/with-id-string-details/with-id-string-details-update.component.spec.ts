jest.mock('@angular/router');

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { MessageService } from 'primeng/api';
import { of } from 'rxjs';

import { WithIdStringDetailsUpdateComponent } from 'app/entities/with-id-string-details/with-id-string-details-update.component';
import { WithIdStringDetailsService } from 'app/entities/with-id-string-details/with-id-string-details.service';

describe('Component Tests', () => {
  describe('WithIdStringDetails Management Update Component', () => {
    let comp: WithIdStringDetailsUpdateComponent;
    let fixture: ComponentFixture<WithIdStringDetailsUpdateComponent>;
    let service: WithIdStringDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [WithIdStringDetailsUpdateComponent],
        providers: [FormBuilder, ActivatedRoute, MessageService],
      })
        .overrideTemplate(WithIdStringDetailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WithIdStringDetailsUpdateComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(WithIdStringDetailsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = { withIdStringId: "'123'" };
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
