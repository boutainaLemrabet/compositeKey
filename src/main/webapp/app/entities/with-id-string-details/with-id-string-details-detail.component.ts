import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWithIdStringDetails } from '../../shared/model/with-id-string-details.model';

@Component({
  selector: 'jhi-with-id-string-details-detail',
  templateUrl: './with-id-string-details-detail.component.html',
})
export class WithIdStringDetailsDetailComponent implements OnInit {
  withIdStringDetails: IWithIdStringDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ withIdStringDetails }) => {
      this.withIdStringDetails = withIdStringDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
