import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Reclamation } from './reclamation.model';
import { ReclamationPopupService } from './reclamation-popup.service';
import { ReclamationService } from './reclamation.service';
@Component({
    selector: 'jhi-reclamation-dialog',
    templateUrl: './reclamation-dialog.component.html'
})
export class ReclamationDialogComponent implements OnInit {

    reclamation: Reclamation;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private reclamationService: ReclamationService,
        private eventManager: EventManager,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['reclamation']);
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
        if (this.reclamation.id !== undefined) {
            this.reclamationService.update(this.reclamation)
                .subscribe((res: Reclamation) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.reclamationService.create(this.reclamation)
                .subscribe((res: Reclamation) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Reclamation) {
        this.eventManager.broadcast({ name: 'reclamationListModification', content: 'OK'});
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
    selector: 'jhi-reclamation-popup',
    template: ''
})
export class ReclamationPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private reclamationPopupService: ReclamationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.reclamationPopupService
                    .open(ReclamationDialogComponent, params['id']);
            } else {
                this.modalRef = this.reclamationPopupService
                    .open(ReclamationDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
