import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { CertificateTypeComponent } from './certificate-type.component';
import { CertificateTypeDetailComponent } from './certificate-type-detail.component';
import { CertificateTypeUpdateComponent } from './certificate-type-update.component';

@NgModule({
  imports: [SharedModule, RouterModule],
  declarations: [CertificateTypeComponent, CertificateTypeDetailComponent, CertificateTypeUpdateComponent],
  exports: [CertificateTypeDetailComponent, CertificateTypeUpdateComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class CertificateTypeModule {}
