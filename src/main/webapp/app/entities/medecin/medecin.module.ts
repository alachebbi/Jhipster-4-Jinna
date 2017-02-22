import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AvancementSharedModule } from '../../shared';

import {
    MedecinService,
    MedecinPopupService,
    MedecinComponent,
    MedecinDetailComponent,
    MedecinDialogComponent,
    MedecinPopupComponent,
    MedecinDeletePopupComponent,
    MedecinDeleteDialogComponent,
    medecinRoute,
    medecinPopupRoute,
    MedecinResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...medecinRoute,
    ...medecinPopupRoute,
];

@NgModule({
    imports: [
        AvancementSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MedecinComponent,
        MedecinDetailComponent,
        MedecinDialogComponent,
        MedecinDeleteDialogComponent,
        MedecinPopupComponent,
        MedecinDeletePopupComponent,
    ],
    entryComponents: [
        MedecinComponent,
        MedecinDialogComponent,
        MedecinPopupComponent,
        MedecinDeleteDialogComponent,
        MedecinDeletePopupComponent,
    ],
    providers: [
        MedecinService,
        MedecinPopupService,
        MedecinResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AvancementMedecinModule {}
