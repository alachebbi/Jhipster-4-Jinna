import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AvancementSharedModule } from '../../shared';

import {
    InfirmierService,
    InfirmierPopupService,
    InfirmierComponent,
    InfirmierDetailComponent,
    InfirmierDialogComponent,
    InfirmierPopupComponent,
    InfirmierDeletePopupComponent,
    InfirmierDeleteDialogComponent,
    infirmierRoute,
    infirmierPopupRoute,
    InfirmierResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...infirmierRoute,
    ...infirmierPopupRoute,
];

@NgModule({
    imports: [
        AvancementSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        InfirmierComponent,
        InfirmierDetailComponent,
        InfirmierDialogComponent,
        InfirmierDeleteDialogComponent,
        InfirmierPopupComponent,
        InfirmierDeletePopupComponent,
    ],
    entryComponents: [
        InfirmierComponent,
        InfirmierDialogComponent,
        InfirmierPopupComponent,
        InfirmierDeleteDialogComponent,
        InfirmierDeletePopupComponent,
    ],
    providers: [
        InfirmierService,
        InfirmierPopupService,
        InfirmierResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AvancementInfirmierModule {}
