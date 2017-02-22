import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Medicament } from './medicament.model';
import { MedicamentPopupService } from './medicament-popup.service';
import { MedicamentService } from './medicament.service';
@Component({
    selector: 'jhi-medicament-dialog',
    templateUrl: './medicament-dialog.component.html'
})
export class MedicamentDialogComponent implements OnInit {

    medicament: Medicament;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private medicamentService: MedicamentService,
        private eventManager: EventManager,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['medicament']);
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
        if (this.medicament.id !== undefined) {
            this.medicamentService.update(this.medicament)
                .subscribe((res: Medicament) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.medicamentService.create(this.medicament)
                .subscribe((res: Medicament) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Medicament) {
        this.eventManager.broadcast({ name: 'medicamentListModification', content: 'OK'});
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
    selector: 'jhi-medicament-popup',
    template: ''
})
export class MedicamentPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private medicamentPopupService: MedicamentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.medicamentPopupService
                    .open(MedicamentDialogComponent, params['id']);
            } else {
                this.modalRef = this.medicamentPopupService
                    .open(MedicamentDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
