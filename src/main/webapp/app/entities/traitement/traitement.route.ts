import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { TraitementComponent } from './traitement.component';
import { TraitementDetailComponent } from './traitement-detail.component';
import { TraitementPopupComponent } from './traitement-dialog.component';
import { TraitementDeletePopupComponent } from './traitement-delete-dialog.component';

import { Principal } from '../../shared';


export const traitementRoute: Routes = [
  {
    path: 'traitement',
    component: TraitementComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.traitement.home.title'
    }
  }, {
    path: 'traitement/:id',
    component: TraitementDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.traitement.home.title'
    }
  }
];

export const traitementPopupRoute: Routes = [
  {
    path: 'traitement-new',
    component: TraitementPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.traitement.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'traitement/:id/edit',
    component: TraitementPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.traitement.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'traitement/:id/delete',
    component: TraitementDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'avancementApp.traitement.home.title'
    },
    outlet: 'popup'
  }
];
