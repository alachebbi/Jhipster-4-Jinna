import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { DemandemedicamentComponent } from './demandemedicament.component';
import { DemandemedicamentDetailComponent } from './demandemedicament-detail.component';
import { DemandemedicamentPopupComponent } from './demandemedicament-dialog.component';
import { DemandemedicamentDeletePopupComponent } from './demandemedicament-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class DemandemedicamentResolvePagingParams implements Resolve<any> {

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

export const demandemedicamentRoute: Routes = [
  {
    path: 'demandemedicament',
    component: DemandemedicamentComponent,
    resolve: {
      'pagingParams': DemandemedicamentResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.demandemedicament.home.title'
    }
  }, {
    path: 'demandemedicament/:id',
    component: DemandemedicamentDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.demandemedicament.home.title'
    }
  }
];

export const demandemedicamentPopupRoute: Routes = [
  {
    path: 'demandemedicament-new',
    component: DemandemedicamentPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.demandemedicament.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'demandemedicament/:id/edit',
    component: DemandemedicamentPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.demandemedicament.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'demandemedicament/:id/delete',
    component: DemandemedicamentDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.demandemedicament.home.title'
    },
    outlet: 'popup'
  }
];
