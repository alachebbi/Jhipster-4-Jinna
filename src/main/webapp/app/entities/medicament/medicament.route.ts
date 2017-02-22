import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { MedicamentComponent } from './medicament.component';
import { MedicamentDetailComponent } from './medicament-detail.component';
import { MedicamentPopupComponent } from './medicament-dialog.component';
import { MedicamentDeletePopupComponent } from './medicament-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class MedicamentResolvePagingParams implements Resolve<any> {

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

export const medicamentRoute: Routes = [
  {
    path: 'medicament',
    component: MedicamentComponent,
    resolve: {
      'pagingParams': MedicamentResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.medicament.home.title'
    }
  }, {
    path: 'medicament/:id',
    component: MedicamentDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.medicament.home.title'
    }
  }
];

export const medicamentPopupRoute: Routes = [
  {
    path: 'medicament-new',
    component: MedicamentPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.medicament.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'medicament/:id/edit',
    component: MedicamentPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.medicament.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'medicament/:id/delete',
    component: MedicamentDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.medicament.home.title'
    },
    outlet: 'popup'
  }
];
