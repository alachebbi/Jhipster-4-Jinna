import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AvancementSharedModule } from '../shared';

import { CHART_ROUTE, ChartComponent } from './';

require('chart.js');
import { ChartsModule } from 'ng2-charts/ng2-charts';

@NgModule({
    imports: [
        AvancementSharedModule,
        RouterModule.forRoot([ CHART_ROUTE ], { useHash: true }),
        ChartsModule
    ],
    declarations: [
        ChartComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AvancementChartModule {}
