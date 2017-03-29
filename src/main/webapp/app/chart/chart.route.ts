import { Route } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { ChartComponent } from './';

export const CHART_ROUTE: Route = {
  path: 'chart',
  component: ChartComponent,
  data: {
    authorities: ['ROLE_ADMIN'],
    pageTitle: 'Welcome, Medecin Chart !'
  },
  canActivate: [UserRouteAccessService]
};

