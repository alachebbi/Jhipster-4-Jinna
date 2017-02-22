import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AvancementSharedModule } from '../../shared';

import {
    MedicamentService,
    MedicamentPopupService,
    MedicamentComponent,
    MedicamentDetailComponent,
    MedicamentDialogComponent,
    MedicamentPopupComponent,
    MedicamentDeletePopupComponent,
    MedicamentDeleteDialogComponent,
    medicamentRoute,
    medicamentPopupRoute,
    MedicamentResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...medicamentRoute,
    ...medicamentPopupRoute,
];

@NgModule({
    imports: [
        AvancementSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MedicamentComponent,
        MedicamentDetailComponent,
        MedicamentDialogComponent,
        MedicamentDeleteDialogComponent,
        MedicamentPopupComponent,
        MedicamentDeletePopupComponent,
    ],
    entryComponents: [
        MedicamentComponent,
        MedicamentDialogComponent,
        MedicamentPopupComponent,
        MedicamentDeleteDialogComponent,
        MedicamentDeletePopupComponent,
    ],
    providers: [
        MedicamentService,
        MedicamentPopupService,
        MedicamentResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AvancementMedicamentModule {}
