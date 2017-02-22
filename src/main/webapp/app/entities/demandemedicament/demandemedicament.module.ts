import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AvancementSharedModule } from '../../shared';

import {
    DemandemedicamentService,
    DemandemedicamentPopupService,
    DemandemedicamentComponent,
    DemandemedicamentDetailComponent,
    DemandemedicamentDialogComponent,
    DemandemedicamentPopupComponent,
    DemandemedicamentDeletePopupComponent,
    DemandemedicamentDeleteDialogComponent,
    demandemedicamentRoute,
    demandemedicamentPopupRoute,
    DemandemedicamentResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...demandemedicamentRoute,
    ...demandemedicamentPopupRoute,
];

@NgModule({
    imports: [
        AvancementSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DemandemedicamentComponent,
        DemandemedicamentDetailComponent,
        DemandemedicamentDialogComponent,
        DemandemedicamentDeleteDialogComponent,
        DemandemedicamentPopupComponent,
        DemandemedicamentDeletePopupComponent,
    ],
    entryComponents: [
        DemandemedicamentComponent,
        DemandemedicamentDialogComponent,
        DemandemedicamentPopupComponent,
        DemandemedicamentDeleteDialogComponent,
        DemandemedicamentDeletePopupComponent,
    ],
    providers: [
        DemandemedicamentService,
        DemandemedicamentPopupService,
        DemandemedicamentResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AvancementDemandemedicamentModule {}
