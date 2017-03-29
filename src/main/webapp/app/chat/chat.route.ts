import { Route } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { ChatComponent } from './';

export const CHAT_ROUTE: Route = {
  path: 'chat',
  component: ChatComponent,
  data: {
    authorities: ['ROLE_USER','ROLE_ADMIN'],
    pageTitle: 'Welcome, Medecin Tchat !'
  },
  canActivate: [UserRouteAccessService]
};

