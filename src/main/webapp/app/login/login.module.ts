import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedLibsModule } from 'app/shared/shared-libs.module';
import { LOGIN_ROUTE } from './login.route';
import { LoginComponent } from './login.component';

@NgModule({
  imports: [SharedLibsModule, RouterModule.forChild([LOGIN_ROUTE])],
  declarations: [LoginComponent],
})
export class LoginModule {}
