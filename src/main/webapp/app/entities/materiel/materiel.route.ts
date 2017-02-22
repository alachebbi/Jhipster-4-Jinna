import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { MaterielComponent } from './materiel.component';
import { MaterielDetailComponent } from './materiel-detail.component';
import { MaterielPopupComponent } from './materiel-dialog.component';
import { MaterielDeletePopupComponent } from './materiel-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class MaterielResolvePagingParams implements Resolve<any> {

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

export const materielRoute: Routes = [
  {
    path: 'materiel',
    component: MaterielComponent,
    resolve: {
      'pagingParams': MaterielResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.materiel.home.title'
    }
  }, {
    path: 'materiel/:id',
    component: MaterielDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.materiel.home.title'
    }
  }
];

export const materielPopupRoute: Routes = [
  {
    path: 'materiel-new',
    component: MaterielPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.materiel.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'materiel/:id/edit',
    component: MaterielPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.materiel.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'materiel/:id/delete',
    component: MaterielDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.materiel.home.title'
    },
    outlet: 'popup'
  }
];
