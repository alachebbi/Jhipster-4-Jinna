import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AvancementSharedModule } from '../../shared';

import {
    MaterielService,
    MaterielPopupService,
    MaterielComponent,
    MaterielDetailComponent,
    MaterielDialogComponent,
    MaterielPopupComponent,
    MaterielDeletePopupComponent,
    MaterielDeleteDialogComponent,
    materielRoute,
    materielPopupRoute,
    MaterielResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...materielRoute,
    ...materielPopupRoute,
];

@NgModule({
    imports: [
        AvancementSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MaterielComponent,
        MaterielDetailComponent,
        MaterielDialogComponent,
        MaterielDeleteDialogComponent,
        MaterielPopupComponent,
        MaterielDeletePopupComponent,
    ],
    entryComponents: [
        MaterielComponent,
        MaterielDialogComponent,
        MaterielPopupComponent,
        MaterielDeleteDialogComponent,
        MaterielDeletePopupComponent,
    ],
    providers: [
        MaterielService,
        MaterielPopupService,
        MaterielResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AvancementMaterielModule {}
