jest.mock('@angular/router');

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WithIdStringUpdateComponent } from 'app/entities/with-id-string/with-id-string-update.component';
import { WithIdStringService } from 'app/entities/with-id-string/with-id-string.service';

describe('Component Tests', () => {
  describe('WithIdString Management Update Component', () => {
    let comp: WithIdStringUpdateComponent;
    let fixture: ComponentFixture<WithIdStringUpdateComponent>;
    let service: WithIdStringService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [WithIdStringUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(WithIdStringUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WithIdStringUpdateComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(WithIdStringService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = { id: "'123'" };
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
