import { NgModule } from '@angular/core';

import { P3SharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [P3SharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [P3SharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class P3SharedCommonModule {}
