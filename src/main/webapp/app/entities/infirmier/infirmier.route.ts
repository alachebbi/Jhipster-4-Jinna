import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { InfirmierComponent } from './infirmier.component';
import { InfirmierDetailComponent } from './infirmier-detail.component';
import { InfirmierPopupComponent } from './infirmier-dialog.component';
import { InfirmierDeletePopupComponent } from './infirmier-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class InfirmierResolvePagingParams implements Resolve<any> {

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

export const infirmierRoute: Routes = [
  {
    path: 'infirmier',
    component: InfirmierComponent,
    resolve: {
      'pagingParams': InfirmierResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.infirmier.home.title'
    }
  }, {
    path: 'infirmier/:id',
    component: InfirmierDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.infirmier.home.title'
    }
  }
];

export const infirmierPopupRoute: Routes = [
  {
    path: 'infirmier-new',
    component: InfirmierPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.infirmier.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'infirmier/:id/edit',
    component: InfirmierPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.infirmier.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'infirmier/:id/delete',
    component: InfirmierDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.infirmier.home.title'
    },
    outlet: 'popup'
  }
];
