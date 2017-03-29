import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';


import {Medicament} from "../medicament/medicament.model";
import {MedicamentService} from "../medicament/medicament.service";

import { Hello } from './hello.model';
import { HelloPopupService } from './hello-popup.service';
import { HelloService } from './hello.service';
@Component({
    selector: 'jhi-hello-dialog',
    templateUrl: './hello-dialog.component.html'
})
export class HelloDialogComponent implements OnInit {

    hello: Hello;
    authorities: any[];
    isSaving: boolean;
    medicaments:Medicament[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private medicamentService: MedicamentService,
        private alertService: AlertService,
        private helloService: HelloService,
        private eventManager: EventManager,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['hello']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.loadAllserv();

        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
    }

    save () {
        this.isSaving = true;
        if (this.hello.id !== undefined) {
            this.helloService.update(this.hello)
                .subscribe((res: Hello) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.helloService.create(this.hello)
                .subscribe((res: Hello) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Hello) {
        this.eventManager.broadcast({ name: 'helloListModification', content: 'OK'});
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

    loadAllserv() {
        this.medicamentService.query().subscribe(
            (res: Response) => {
                this.medicaments = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
}

@Component({
    selector: 'jhi-hello-popup',
    template: ''
})
export class HelloPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private helloPopupService: HelloPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.helloPopupService
                    .open(HelloDialogComponent, params['id']);
            } else {
                this.modalRef = this.helloPopupService
                    .open(HelloDialogComponent);
            }

        });
    }
    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }


}
