import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { MedecinComponent } from './medecin.component';
import { MedecinDetailComponent } from './medecin-detail.component';
import { MedecinPopupComponent } from './medecin-dialog.component';
import { MedecinDeletePopupComponent } from './medecin-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class MedecinResolvePagingParams implements Resolve<any> {

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

export const medecinRoute: Routes = [
  {
    path: 'medecin',
    component: MedecinComponent,
    resolve: {
      'pagingParams': MedecinResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.medecin.home.title'
    }
  }, {
    path: 'medecin/:id',
    component: MedecinDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.medecin.home.title'
    }
  }
];

export const medecinPopupRoute: Routes = [
  {
    path: 'medecin-new',
    component: MedecinPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.medecin.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'medecin/:id/edit',
    component: MedecinPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.medecin.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'medecin/:id/delete',
    component: MedecinDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.medecin.home.title'
    },
    outlet: 'popup'
  }
];
