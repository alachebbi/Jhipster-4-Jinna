import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AvancementSharedModule } from '../../shared';

import {
    DemandematerielService,
    DemandematerielPopupService,
    DemandematerielComponent,
    DemandematerielDetailComponent,
    DemandematerielDialogComponent,
    DemandematerielPopupComponent,
    DemandematerielDeletePopupComponent,
    DemandematerielDeleteDialogComponent,
    demandematerielRoute,
    demandematerielPopupRoute,
    DemandematerielResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...demandematerielRoute,
    ...demandematerielPopupRoute,
];

@NgModule({
    imports: [
        AvancementSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DemandematerielComponent,
        DemandematerielDetailComponent,
        DemandematerielDialogComponent,
        DemandematerielDeleteDialogComponent,
        DemandematerielPopupComponent,
        DemandematerielDeletePopupComponent,
    ],
    entryComponents: [
        DemandematerielComponent,
        DemandematerielDialogComponent,
        DemandematerielPopupComponent,
        DemandematerielDeleteDialogComponent,
        DemandematerielDeletePopupComponent,
    ],
    providers: [
        DemandematerielService,
        DemandematerielPopupService,
        DemandematerielResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AvancementDemandematerielModule {}
