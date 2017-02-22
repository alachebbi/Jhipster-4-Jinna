import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Fichemedicale } from './fichemedicale.model';
import { FichemedicalePopupService } from './fichemedicale-popup.service';
import { FichemedicaleService } from './fichemedicale.service';
@Component({
    selector: 'jhi-fichemedicale-dialog',
    templateUrl: './fichemedicale-dialog.component.html'
})
export class FichemedicaleDialogComponent implements OnInit {

    fichemedicale: Fichemedicale;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private fichemedicaleService: FichemedicaleService,
        private eventManager: EventManager,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['fichemedicale']);
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
        if (this.fichemedicale.id !== undefined) {
            this.fichemedicaleService.update(this.fichemedicale)
                .subscribe((res: Fichemedicale) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.fichemedicaleService.create(this.fichemedicale)
                .subscribe((res: Fichemedicale) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Fichemedicale) {
        this.eventManager.broadcast({ name: 'fichemedicaleListModification', content: 'OK'});
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
    selector: 'jhi-fichemedicale-popup',
    template: ''
})
export class FichemedicalePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private fichemedicalePopupService: FichemedicalePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.fichemedicalePopupService
                    .open(FichemedicaleDialogComponent, params['id']);
            } else {
                this.modalRef = this.fichemedicalePopupService
                    .open(FichemedicaleDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
