import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AvancementSharedModule } from '../../shared';

import {
    FichepatientService,
    FichepatientPopupService,
    FichepatientComponent,
    FichepatientDetailComponent,
    FichepatientDialogComponent,
    FichepatientPopupComponent,
    FichepatientDeletePopupComponent,
    FichepatientDeleteDialogComponent,
    fichepatientRoute,
    fichepatientPopupRoute,
    FichepatientResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...fichepatientRoute,
    ...fichepatientPopupRoute,
];

@NgModule({
    imports: [
        AvancementSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FichepatientComponent,
        FichepatientDetailComponent,
        FichepatientDialogComponent,
        FichepatientDeleteDialogComponent,
        FichepatientPopupComponent,
        FichepatientDeletePopupComponent,
    ],
    entryComponents: [
        FichepatientComponent,
        FichepatientDialogComponent,
        FichepatientPopupComponent,
        FichepatientDeleteDialogComponent,
        FichepatientDeletePopupComponent,
    ],
    providers: [
        FichepatientService,
        FichepatientPopupService,
        FichepatientResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AvancementFichepatientModule {}
