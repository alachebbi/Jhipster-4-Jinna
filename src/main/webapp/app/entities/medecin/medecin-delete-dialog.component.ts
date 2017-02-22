import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Medecin } from './medecin.model';
import { MedecinPopupService } from './medecin-popup.service';
import { MedecinService } from './medecin.service';

@Component({
    selector: 'jhi-medecin-delete-dialog',
    templateUrl: './medecin-delete-dialog.component.html'
})
export class MedecinDeleteDialogComponent {

    medecin: Medecin;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private medecinService: MedecinService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['medecin']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
    }

    confirmDelete (id: number) {
        this.medecinService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'medecinListModification',
                content: 'Deleted an medecin'
            });
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-medecin-delete-popup',
    template: ''
})
export class MedecinDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private medecinPopupService: MedecinPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.medecinPopupService
                .open(MedecinDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
