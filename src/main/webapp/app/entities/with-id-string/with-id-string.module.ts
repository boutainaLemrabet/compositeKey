import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { WithIdStringComponent } from './with-id-string.component';
import { WithIdStringDetailComponent } from './with-id-string-detail.component';
import { WithIdStringUpdateComponent } from './with-id-string-update.component';

@NgModule({
  imports: [SharedModule, RouterModule],
  declarations: [WithIdStringComponent, WithIdStringDetailComponent, WithIdStringUpdateComponent],
  exports: [WithIdStringDetailComponent, WithIdStringUpdateComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class WithIdStringModule {}
