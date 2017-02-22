import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ReclamationComponent } from './reclamation.component';
import { ReclamationDetailComponent } from './reclamation-detail.component';
import { ReclamationPopupComponent } from './reclamation-dialog.component';
import { ReclamationDeletePopupComponent } from './reclamation-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ReclamationResolvePagingParams implements Resolve<any> {

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

export const reclamationRoute: Routes = [
  {
    path: 'reclamation',
    component: ReclamationComponent,
    resolve: {
      'pagingParams': ReclamationResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.reclamation.home.title'
    }
  }, {
    path: 'reclamation/:id',
    component: ReclamationDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.reclamation.home.title'
    }
  }
];

export const reclamationPopupRoute: Routes = [
  {
    path: 'reclamation-new',
    component: ReclamationPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.reclamation.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'reclamation/:id/edit',
    component: ReclamationPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.reclamation.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'reclamation/:id/delete',
    component: ReclamationDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.reclamation.home.title'
    },
    outlet: 'popup'
  }
];
