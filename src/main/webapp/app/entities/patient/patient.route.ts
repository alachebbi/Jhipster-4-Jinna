import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PatientComponent } from './patient.component';
import { PatientDetailComponent } from './patient-detail.component';
import { PatientPopupComponent } from './patient-dialog.component';
import { PatientDeletePopupComponent } from './patient-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class PatientResolvePagingParams implements Resolve<any> {

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

export const patientRoute: Routes = [
  {
    path: 'patient',
    component: PatientComponent,
    resolve: {
      'pagingParams': PatientResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.patient.home.title'
    }
  }, {
    path: 'patient/:id',
    component: PatientDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.patient.home.title'
    }
  }
];

export const patientPopupRoute: Routes = [
  {
    path: 'patient-new',
    component: PatientPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.patient.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'patient/:id/edit',
    component: PatientPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.patient.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'patient/:id/delete',
    component: PatientDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.patient.home.title'
    },
    outlet: 'popup'
  }
];
