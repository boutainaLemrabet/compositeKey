jest.mock('@angular/router');

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TaskCommentUpdateComponent } from 'app/entities/task-comment/task-comment-update.component';
import { TaskCommentService } from 'app/entities/task-comment/task-comment.service';

describe('Component Tests', () => {
  describe('TaskComment Management Update Component', () => {
    let comp: TaskCommentUpdateComponent;
    let fixture: ComponentFixture<TaskCommentUpdateComponent>;
    let service: TaskCommentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TaskCommentUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(TaskCommentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaskCommentUpdateComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(TaskCommentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = { id: 123 };
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
