import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { P3SharedLibsModule, P3SharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
    imports: [P3SharedLibsModule, P3SharedCommonModule],
    declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
    providers: [], // { provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }
    entryComponents: [JhiLoginModalComponent],
    exports: [P3SharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class P3SharedModule {}
