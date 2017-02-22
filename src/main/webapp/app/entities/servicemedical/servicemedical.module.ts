import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AvancementSharedModule } from '../../shared';

import {
    ServicemedicalService,
    ServicemedicalPopupService,
    ServicemedicalComponent,
    ServicemedicalDetailComponent,
    ServicemedicalDialogComponent,
    ServicemedicalPopupComponent,
    ServicemedicalDeletePopupComponent,
    ServicemedicalDeleteDialogComponent,
    servicemedicalRoute,
    servicemedicalPopupRoute,
    ServicemedicalResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...servicemedicalRoute,
    ...servicemedicalPopupRoute,
];

@NgModule({
    imports: [
        AvancementSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ServicemedicalComponent,
        ServicemedicalDetailComponent,
        ServicemedicalDialogComponent,
        ServicemedicalDeleteDialogComponent,
        ServicemedicalPopupComponent,
        ServicemedicalDeletePopupComponent,
    ],
    entryComponents: [
        ServicemedicalComponent,
        ServicemedicalDialogComponent,
        ServicemedicalPopupComponent,
        ServicemedicalDeleteDialogComponent,
        ServicemedicalDeletePopupComponent,
    ],
    providers: [
        ServicemedicalService,
        ServicemedicalPopupService,
        ServicemedicalResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AvancementServicemedicalModule {}
