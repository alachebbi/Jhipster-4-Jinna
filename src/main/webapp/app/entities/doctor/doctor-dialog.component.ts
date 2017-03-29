import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService, DataUtils } from 'ng-jhipster';
import {Servicemedical} from "../servicemedical/servicemedical.model";
import {ServicemedicalService} from "../servicemedical/servicemedical.service";
import { Doctor } from './doctor.model';
import { DoctorPopupService } from './doctor-popup.service';
import { DoctorService } from './doctor.service';
@Component({
    selector: 'jhi-doctor-dialog',
    templateUrl: './doctor-dialog.component.html'
})
export class DoctorDialogComponent implements OnInit {

    doctor: Doctor;
    servicemedicals:Servicemedical[];
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private servicemedicalService: ServicemedicalService,
        private jhiLanguageService: JhiLanguageService,
        private dataUtils: DataUtils,
        private alertService: AlertService,
        private doctorService: DoctorService,
        private eventManager: EventManager,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['doctor']);
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

    setFileData($event, doctor, field, isImage) {
        if ($event.target.files && $event.target.files[0]) {
            let $file = $event.target.files[0];
            if (isImage && !/^image\//.test($file.type)) {
                return;
            }
            this.dataUtils.toBase64($file, (base64Data) => {
                doctor[field] = base64Data;
                doctor[`${field}ContentType`] = $file.type;
            });
        }
    }
    loadAllserv() {
        this.servicemedicalService.query().subscribe(
            (res: Response) => {
                this.servicemedicals = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
    }

    save () {
        this.isSaving = true;
        if (this.doctor.id !== undefined) {
            this.doctorService.update(this.doctor)
                .subscribe((res: Doctor) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.doctorService.create(this.doctor)
                .subscribe((res: Doctor) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Doctor) {
        this.eventManager.broadcast({ name: 'doctorListModification', content: 'OK'});
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
    selector: 'jhi-doctor-popup',
    template: ''
})
export class DoctorPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private doctorPopupService: DoctorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.doctorPopupService
                    .open(DoctorDialogComponent, params['id']);
            } else {
                this.modalRef = this.doctorPopupService
                    .open(DoctorDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
