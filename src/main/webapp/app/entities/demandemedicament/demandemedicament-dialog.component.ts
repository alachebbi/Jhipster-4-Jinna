import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Demandemedicament } from './demandemedicament.model';
import { DemandemedicamentPopupService } from './demandemedicament-popup.service';
import { DemandemedicamentService } from './demandemedicament.service';
@Component({
    selector: 'jhi-demandemedicament-dialog',
    templateUrl: './demandemedicament-dialog.component.html'
})
export class DemandemedicamentDialogComponent implements OnInit {

    demandemedicament: Demandemedicament;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private demandemedicamentService: DemandemedicamentService,
        private eventManager: EventManager,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['demandemedicament']);
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
        if (this.demandemedicament.id !== undefined) {
            this.demandemedicamentService.update(this.demandemedicament)
                .subscribe((res: Demandemedicament) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.demandemedicamentService.create(this.demandemedicament)
                .subscribe((res: Demandemedicament) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Demandemedicament) {
        this.eventManager.broadcast({ name: 'demandemedicamentListModification', content: 'OK'});
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
    selector: 'jhi-demandemedicament-popup',
    template: ''
})
export class DemandemedicamentPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private demandemedicamentPopupService: DemandemedicamentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.demandemedicamentPopupService
                    .open(DemandemedicamentDialogComponent, params['id']);
            } else {
                this.modalRef = this.demandemedicamentPopupService
                    .open(DemandemedicamentDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
