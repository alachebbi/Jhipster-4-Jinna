import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Demandemateriel } from './demandemateriel.model';
import { DemandematerielPopupService } from './demandemateriel-popup.service';
import { DemandematerielService } from './demandemateriel.service';

@Component({
    selector: 'jhi-demandemateriel-delete-dialog',
    templateUrl: './demandemateriel-delete-dialog.component.html'
})
export class DemandematerielDeleteDialogComponent {

    demandemateriel: Demandemateriel;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private demandematerielService: DemandematerielService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['demandemateriel']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
    }

    confirmDelete (id: number) {
        this.demandematerielService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'demandematerielListModification',
                content: 'Deleted an demandemateriel'
            });
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-demandemateriel-delete-popup',
    template: ''
})
export class DemandematerielDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private demandematerielPopupService: DemandematerielPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.demandematerielPopupService
                .open(DemandematerielDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
