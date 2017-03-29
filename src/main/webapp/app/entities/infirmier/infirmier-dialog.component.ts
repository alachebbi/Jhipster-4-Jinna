import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Infirmier } from './infirmier.model';
import { InfirmierPopupService } from './infirmier-popup.service';
import { InfirmierService } from './infirmier.service';
@Component({
    selector: 'jhi-infirmier-dialog',
    templateUrl: './infirmier-dialog.component.html'
})
export class InfirmierDialogComponent implements OnInit {

    infirmier: Infirmier;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private infirmierService: InfirmierService,
        private eventManager: EventManager,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['infirmier']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
    }

    save () {
        this.isSaving = true;
        if (this.infirmier.id !== undefined) {
            this.infirmierService.update(this.infirmier)
                .subscribe((res: Infirmier) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.infirmierService.create(this.infirmier)
                .subscribe((res: Infirmier) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Infirmier) {
        this.eventManager.broadcast({ name: 'infirmierListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
        this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-infirmier-popup',
    template: ''
})
export class InfirmierPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private infirmierPopupService: InfirmierPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.infirmierPopupService
                    .open(InfirmierDialogComponent, params['id']);
            } else {
                this.modalRef = this.infirmierPopupService
                    .open(InfirmierDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
