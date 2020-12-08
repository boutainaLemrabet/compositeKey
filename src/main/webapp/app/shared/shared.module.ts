import { NgModule } from '@angular/core';
import { SharedLibsModule } from './shared-libs.module';
import { FindLanguageFromKeyPipe } from './find-language-from-key.pipe';
import { TranslateDirective } from './translate.directive';
import { HasAnyAuthorityDirective } from './has-any-authority.directive';
import { PrimeNGCommonModule } from 'app/shared/primeng-common.module';
import { SortDirective } from 'app/shared/sort/sort.directive';
@NgModule({
  imports: [SharedLibsModule],
  declarations: [FindLanguageFromKeyPipe, TranslateDirective, HasAnyAuthorityDirective, SortDirective],
  exports: [SharedLibsModule, FindLanguageFromKeyPipe, TranslateDirective, HasAnyAuthorityDirective, PrimeNGCommonModule, SortDirective],
})
export class SharedModule {}
