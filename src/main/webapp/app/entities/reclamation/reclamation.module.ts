import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AvancementSharedModule } from '../../shared';

import {
    ReclamationService,
    ReclamationPopupService,
    ReclamationComponent,
    ReclamationDetailComponent,
    ReclamationDialogComponent,
    ReclamationPopupComponent,
    ReclamationDeletePopupComponent,
    ReclamationDeleteDialogComponent,
    reclamationRoute,
    reclamationPopupRoute,
    ReclamationResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...reclamationRoute,
    ...reclamationPopupRoute,
];

@NgModule({
    imports: [
        AvancementSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ReclamationComponent,
        ReclamationDetailComponent,
        ReclamationDialogComponent,
        ReclamationDeleteDialogComponent,
        ReclamationPopupComponent,
        ReclamationDeletePopupComponent,
    ],
    entryComponents: [
        ReclamationComponent,
        ReclamationDialogComponent,
        ReclamationPopupComponent,
        ReclamationDeleteDialogComponent,
        ReclamationDeletePopupComponent,
    ],
    providers: [
        ReclamationService,
        ReclamationPopupService,
        ReclamationResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AvancementReclamationModule {}
