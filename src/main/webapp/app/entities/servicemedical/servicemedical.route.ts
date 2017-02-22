import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ServicemedicalComponent } from './servicemedical.component';
import { ServicemedicalDetailComponent } from './servicemedical-detail.component';
import { ServicemedicalPopupComponent } from './servicemedical-dialog.component';
import { ServicemedicalDeletePopupComponent } from './servicemedical-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ServicemedicalResolvePagingParams implements Resolve<any> {

  constructor(private paginationUtil: PaginationUtil) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
      let page = route.queryParams['page'] ? route.queryParams['page'] : '1';
      let sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
      return {
          page: this.paginationUtil.parsePage(page),
          predicate: this.paginationUtil.parsePredicate(sort),
          ascending: this.paginationUtil.parseAscending(sort)
    };
  }
}

export const servicemedicalRoute: Routes = [
  {
    path: 'servicemedical',
    component: ServicemedicalComponent,
    resolve: {
      'pagingParams': ServicemedicalResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.servicemedical.home.title'
    }
  }, {
    path: 'servicemedical/:id',
    component: ServicemedicalDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.servicemedical.home.title'
    }
  }
];

export const servicemedicalPopupRoute: Routes = [
  {
    path: 'servicemedical-new',
    component: ServicemedicalPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.servicemedical.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'servicemedical/:id/edit',
    component: ServicemedicalPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.servicemedical.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'servicemedical/:id/delete',
    component: ServicemedicalDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.servicemedical.home.title'
    },
    outlet: 'popup'
  }
];
