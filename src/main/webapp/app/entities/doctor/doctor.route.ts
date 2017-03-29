import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { DoctorComponent } from './doctor.component';
import { DoctorDetailComponent } from './doctor-detail.component';
import { DoctorPopupComponent } from './doctor-dialog.component';
import { DoctorDeletePopupComponent } from './doctor-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class DoctorResolvePagingParams implements Resolve<any> {

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

export const doctorRoute: Routes = [
  {
    path: 'doctor',
    component: DoctorComponent,
    resolve: {
      'pagingParams': DoctorResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.doctor.home.title'
    }
  }, {
    path: 'doctor/:id',
    component: DoctorDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.doctor.home.title'
    }
  }
];

export const doctorPopupRoute: Routes = [
  {
    path: 'doctor-new',
    component: DoctorPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.doctor.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'doctor/:id/edit',
    component: DoctorPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.doctor.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'doctor/:id/delete',
    component: DoctorDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.doctor.home.title'
    },
    outlet: 'popup'
  }
];
