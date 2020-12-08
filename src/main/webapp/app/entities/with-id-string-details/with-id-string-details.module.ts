import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { WithIdStringDetailsComponent } from './with-id-string-details.component';
import { WithIdStringDetailsDetailComponent } from './with-id-string-details-detail.component';
import { WithIdStringDetailsUpdateComponent } from './with-id-string-details-update.component';

@NgModule({
  imports: [SharedModule, RouterModule],
  declarations: [WithIdStringDetailsComponent, WithIdStringDetailsDetailComponent, WithIdStringDetailsUpdateComponent],
  exports: [WithIdStringDetailsDetailComponent, WithIdStringDetailsUpdateComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class WithIdStringDetailsModule {}
