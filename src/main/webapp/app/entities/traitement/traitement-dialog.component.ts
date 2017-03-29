import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import {Medicament} from "../medicament/medicament.model";
import {MedicamentService} from "../medicament/medicament.service";

import { Traitement } from './traitement.model';
import { TraitementPopupService } from './traitement-popup.service';
import { TraitementService } from './traitement.service';
@Component({
    selector: 'jhi-traitement-dialog',
    templateUrl: './traitement-dialog.component.html'
})
export class TraitementDialogComponent implements OnInit {

    traitement: Traitement;
    authorities: any[];
    medicaments:Medicament[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private medicamentService: MedicamentService,
        private traitementService: TraitementService,
        private eventManager: EventManager,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['traitement']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.loadAllmaladies();
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
    }

    save () {
        this.isSaving = true;
        if (this.traitement.id !== undefined) {
            this.traitementService.update(this.traitement)
                .subscribe((res: Traitement) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.traitementService.create(this.traitement)
                .subscribe((res: Traitement) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Traitement) {
        this.eventManager.broadcast({ name: 'traitementListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
        this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }
    loadAllmaladies() {
        this.medicamentService.query().subscribe(
            (res: Response) => {
                this.medicaments = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-traitement-popup',
    template: ''
})
export class TraitementPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private traitementPopupService: TraitementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.traitementPopupService
                    .open(TraitementDialogComponent, params['id']);
            } else {
                this.modalRef = this.traitementPopupService
                    .open(TraitementDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
