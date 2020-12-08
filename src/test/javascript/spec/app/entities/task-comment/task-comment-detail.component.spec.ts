import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TaskCommentDetailComponent } from 'app/entities/task-comment/task-comment-detail.component';

describe('Component Tests', () => {
  describe('TaskComment Management Detail Component', () => {
    let comp: TaskCommentDetailComponent;
    let fixture: ComponentFixture<TaskCommentDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [TaskCommentDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ taskComment: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(TaskCommentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaskCommentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load taskComment on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taskComment).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
