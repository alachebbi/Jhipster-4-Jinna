import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AvancementSharedModule } from '../../shared';

import {
    HelloService,
    HelloPopupService,
    HelloComponent,
    HelloDetailComponent,
    HelloDialogComponent,
    HelloPopupComponent,
    HelloDeletePopupComponent,
    HelloDeleteDialogComponent,
    helloRoute,
    helloPopupRoute,
    HelloResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...helloRoute,
    ...helloPopupRoute,
];

@NgModule({
    imports: [
        AvancementSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        HelloComponent,
        HelloDetailComponent,
        HelloDialogComponent,
        HelloDeleteDialogComponent,
        HelloPopupComponent,
        HelloDeletePopupComponent,
    ],
    entryComponents: [
        HelloComponent,
        HelloDialogComponent,
        HelloPopupComponent,
        HelloDeleteDialogComponent,
        HelloDeletePopupComponent,
    ],
    providers: [
        HelloService,
        HelloPopupService,
        HelloResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AvancementHelloModule {}
