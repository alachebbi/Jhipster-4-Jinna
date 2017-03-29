import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AvancementSharedModule } from '../../shared';

import {
    TraitementService,
    TraitementPopupService,
    TraitementComponent,
    TraitementDetailComponent,
    TraitementDialogComponent,
    TraitementPopupComponent,
    TraitementDeletePopupComponent,
    TraitementDeleteDialogComponent,
    traitementRoute,
    traitementPopupRoute,
} from './';

let ENTITY_STATES = [
    ...traitementRoute,
    ...traitementPopupRoute,
];

@NgModule({
    imports: [
        AvancementSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TraitementComponent,
        TraitementDetailComponent,
        TraitementDialogComponent,
        TraitementDeleteDialogComponent,
        TraitementPopupComponent,
        TraitementDeletePopupComponent,
    ],
    entryComponents: [
        TraitementComponent,
        TraitementDialogComponent,
        TraitementPopupComponent,
        TraitementDeleteDialogComponent,
        TraitementDeletePopupComponent,
    ],
    providers: [
        TraitementService,
        TraitementPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AvancementTraitementModule {}
