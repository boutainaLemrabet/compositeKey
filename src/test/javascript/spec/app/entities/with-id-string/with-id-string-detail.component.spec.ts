import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WithIdStringDetailComponent } from 'app/entities/with-id-string/with-id-string-detail.component';

describe('Component Tests', () => {
  describe('WithIdString Management Detail Component', () => {
    let comp: WithIdStringDetailComponent;
    let fixture: ComponentFixture<WithIdStringDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [WithIdStringDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ withIdString: { id: "'123'" } }) },
          },
        ],
      })
        .overrideTemplate(WithIdStringDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WithIdStringDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load withIdString on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.withIdString).toEqual(jasmine.objectContaining({ id: "'123'" }));
      });
    });
  });
});
