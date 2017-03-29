import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { FichemedicaleComponent } from './fichemedicale.component';
import { FichemedicaleDetailComponent } from './fichemedicale-detail.component';
import { FichemedicalePopupComponent } from './fichemedicale-dialog.component';
import { FichemedicaleDeletePopupComponent } from './fichemedicale-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class FichemedicaleResolvePagingParams implements Resolve<any> {

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

export const fichemedicaleRoute: Routes = [
  {
    path: 'fichemedicale',
    component: FichemedicaleComponent,
    resolve: {
      'pagingParams': FichemedicaleResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.fichemedicale.home.title'
    }
  }, {
    path: 'fichemedicale/:id',
    component: FichemedicaleDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.fichemedicale.home.title'
    }
  }
];

export const fichemedicalePopupRoute: Routes = [
  {
    path: 'fichemedicale-new',
    component: FichemedicalePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.fichemedicale.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'fichemedicale/:id/edit',
    component: FichemedicalePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.fichemedicale.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'fichemedicale/:id/delete',
    component: FichemedicaleDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.fichemedicale.home.title'
    },
    outlet: 'popup'
  }
];
