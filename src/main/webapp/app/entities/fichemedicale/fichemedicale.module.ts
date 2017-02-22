import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AvancementSharedModule } from '../../shared';

import {
    FichemedicaleService,
    FichemedicalePopupService,
    FichemedicaleComponent,
    FichemedicaleDetailComponent,
    FichemedicaleDialogComponent,
    FichemedicalePopupComponent,
    FichemedicaleDeletePopupComponent,
    FichemedicaleDeleteDialogComponent,
    fichemedicaleRoute,
    fichemedicalePopupRoute,
    FichemedicaleResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...fichemedicaleRoute,
    ...fichemedicalePopupRoute,
];

@NgModule({
    imports: [
        AvancementSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FichemedicaleComponent,
        FichemedicaleDetailComponent,
        FichemedicaleDialogComponent,
        FichemedicaleDeleteDialogComponent,
        FichemedicalePopupComponent,
        FichemedicaleDeletePopupComponent,
    ],
    entryComponents: [
        FichemedicaleComponent,
        FichemedicaleDialogComponent,
        FichemedicalePopupComponent,
        FichemedicaleDeleteDialogComponent,
        FichemedicaleDeletePopupComponent,
    ],
    providers: [
        FichemedicaleService,
        FichemedicalePopupService,
        FichemedicaleResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AvancementFichemedicaleModule {}
