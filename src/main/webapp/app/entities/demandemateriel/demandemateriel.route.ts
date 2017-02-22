import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { DemandematerielComponent } from './demandemateriel.component';
import { DemandematerielDetailComponent } from './demandemateriel-detail.component';
import { DemandematerielPopupComponent } from './demandemateriel-dialog.component';
import { DemandematerielDeletePopupComponent } from './demandemateriel-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class DemandematerielResolvePagingParams implements Resolve<any> {

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

export const demandematerielRoute: Routes = [
  {
    path: 'demandemateriel',
    component: DemandematerielComponent,
    resolve: {
      'pagingParams': DemandematerielResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.demandemateriel.home.title'
    }
  }, {
    path: 'demandemateriel/:id',
    component: DemandematerielDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.demandemateriel.home.title'
    }
  }
];

export const demandematerielPopupRoute: Routes = [
  {
    path: 'demandemateriel-new',
    component: DemandematerielPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.demandemateriel.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'demandemateriel/:id/edit',
    component: DemandematerielPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.demandemateriel.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'demandemateriel/:id/delete',
    component: DemandematerielDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.demandemateriel.home.title'
    },
    outlet: 'popup'
  }
];
