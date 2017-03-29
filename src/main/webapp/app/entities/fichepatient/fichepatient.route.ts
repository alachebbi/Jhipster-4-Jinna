import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { FichepatientComponent } from './fichepatient.component';
import { FichepatientDetailComponent } from './fichepatient-detail.component';
import { FichepatientPopupComponent } from './fichepatient-dialog.component';
import { FichepatientDeletePopupComponent } from './fichepatient-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class FichepatientResolvePagingParams implements Resolve<any> {

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

export const fichepatientRoute: Routes = [
  {
    path: 'fichepatient',
    component: FichepatientComponent,
    resolve: {
      'pagingParams': FichepatientResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.fichepatient.home.title'
    }
  }, {
    path: 'fichepatient/:id',
    component: FichepatientDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.fichepatient.home.title'
    }
  }
];

export const fichepatientPopupRoute: Routes = [
  {
    path: 'fichepatient-new',
    component: FichepatientPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.fichepatient.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'fichepatient/:id/edit',
    component: FichepatientPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.fichepatient.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'fichepatient/:id/delete',
    component: FichepatientDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.fichepatient.home.title'
    },
    outlet: 'popup'
  }
];
