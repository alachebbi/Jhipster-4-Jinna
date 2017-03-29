import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService, DataUtils } from 'ng-jhipster';

import { Fichepatient } from './fichepatient.model';
import { FichepatientPopupService } from './fichepatient-popup.service';
import { FichepatientService } from './fichepatient.service';
import { Medecin } from '../medecin/medecin.model';
import {MedecinService }from'../medecin/medecin.service';
@Component({
    selector: 'jhi-fichepatient-dialog',
    templateUrl: './fichepatient-dialog.component.html'
})
export class FichepatientDialogComponent implements OnInit {

    fichepatient: Fichepatient;
    medecins: Medecin[];
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private dataUtils: DataUtils,
        private alertService: AlertService,
        private medecinService: MedecinService,
        private fichepatientService: FichepatientService,
        private eventManager: EventManager,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['fichepatient']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.loadAllserv();
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData($event, fichepatient, field, isImage) {
        if ($event.target.files && $event.target.files[0]) {
            let $file = $event.target.files[0];
            if (isImage && !/^image\//.test($file.type)) {
                return;
            }
            this.dataUtils.toBase64($file, (base64Data) => {
                fichepatient[field] = base64Data;
                fichepatient[`${field}ContentType`] = $file.type;
            });
        }
    }
    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
    }
    loadAllserv() {
        this.medecinService.query().subscribe(
            (res: Response) => {
                this.medecins = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    save () {
        this.isSaving = true;
        if (this.fichepatient.id !== undefined) {
            this.fichepatientService.update(this.fichepatient)
                .subscribe((res: Fichepatient) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.fichepatientService.create(this.fichepatient)
                .subscribe((res: Fichepatient) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Fichepatient) {
        this.eventManager.broadcast({ name: 'fichepatientListModification', content: 'OK'});
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
    selector: 'jhi-fichepatient-popup',
    template: ''
})
export class FichepatientPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private fichepatientPopupService: FichepatientPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.fichepatientPopupService
                    .open(FichepatientDialogComponent, params['id']);
            } else {
                this.modalRef = this.fichepatientPopupService
                    .open(FichepatientDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
