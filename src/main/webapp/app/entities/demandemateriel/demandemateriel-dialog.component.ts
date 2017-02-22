import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Demandemateriel } from './demandemateriel.model';
import { DemandematerielPopupService } from './demandemateriel-popup.service';
import { DemandematerielService } from './demandemateriel.service';
@Component({
    selector: 'jhi-demandemateriel-dialog',
    templateUrl: './demandemateriel-dialog.component.html'
})
export class DemandematerielDialogComponent implements OnInit {

    demandemateriel: Demandemateriel;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private demandematerielService: DemandematerielService,
        private eventManager: EventManager,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['demandemateriel']);
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
        if (this.demandemateriel.id !== undefined) {
            this.demandematerielService.update(this.demandemateriel)
                .subscribe((res: Demandemateriel) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.demandematerielService.create(this.demandemateriel)
                .subscribe((res: Demandemateriel) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Demandemateriel) {
        this.eventManager.broadcast({ name: 'demandematerielListModification', content: 'OK'});
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
    selector: 'jhi-demandemateriel-popup',
    template: ''
})
export class DemandematerielPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private demandematerielPopupService: DemandematerielPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.demandematerielPopupService
                    .open(DemandematerielDialogComponent, params['id']);
            } else {
                this.modalRef = this.demandematerielPopupService
                    .open(DemandematerielDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
