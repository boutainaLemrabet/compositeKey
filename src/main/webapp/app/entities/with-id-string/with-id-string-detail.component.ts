import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWithIdString } from '../../shared/model/with-id-string.model';

@Component({
  selector: 'jhi-with-id-string-detail',
  templateUrl: './with-id-string-detail.component.html',
})
export class WithIdStringDetailComponent implements OnInit {
  withIdString: IWithIdString | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ withIdString }) => {
      this.withIdString = withIdString;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
