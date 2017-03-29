import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { HelloComponent } from './hello.component';
import { HelloDetailComponent } from './hello-detail.component';
import { HelloPopupComponent } from './hello-dialog.component';
import { HelloDeletePopupComponent } from './hello-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class HelloResolvePagingParams implements Resolve<any> {

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

export const helloRoute: Routes = [
  {
    path: 'hello',
    component: HelloComponent,
    resolve: {
      'pagingParams': HelloResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.hello.home.title'
    }
  }, {
    path: 'hello/:id',
    component: HelloDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.hello.home.title'
    }
  }
];

export const helloPopupRoute: Routes = [
  {
    path: 'hello-new',
    component: HelloPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.hello.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'hello/:id/edit',
    component: HelloPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.hello.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'hello/:id/delete',
    component: HelloDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.hello.home.title'
    },
    outlet: 'popup'
  }
];
