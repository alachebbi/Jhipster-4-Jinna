import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Materiel } from './materiel.model';
import { MaterielPopupService } from './materiel-popup.service';
import { MaterielService } from './materiel.service';
@Component({
    selector: 'jhi-materiel-dialog',
    templateUrl: './materiel-dialog.component.html'
})
export class MaterielDialogComponent implements OnInit {

    materiel: Materiel;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private materielService: MaterielService,
        private eventManager: EventManager,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['materiel']);
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
        if (this.materiel.id !== undefined) {
            this.materielService.update(this.materiel)
                .subscribe((res: Materiel) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.materielService.create(this.materiel)
                .subscribe((res: Materiel) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Materiel) {
        this.eventManager.broadcast({ name: 'materielListModification', content: 'OK'});
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
    selector: 'jhi-materiel-popup',
    template: ''
})
export class MaterielPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private materielPopupService: MaterielPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.materielPopupService
                    .open(MaterielDialogComponent, params['id']);
            } else {
                this.modalRef = this.materielPopupService
                    .open(MaterielDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
