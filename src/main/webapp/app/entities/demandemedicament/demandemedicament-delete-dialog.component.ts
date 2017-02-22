import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Demandemedicament } from './demandemedicament.model';
import { DemandemedicamentPopupService } from './demandemedicament-popup.service';
import { DemandemedicamentService } from './demandemedicament.service';

@Component({
    selector: 'jhi-demandemedicament-delete-dialog',
    templateUrl: './demandemedicament-delete-dialog.component.html'
})
export class DemandemedicamentDeleteDialogComponent {

    demandemedicament: Demandemedicament;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private demandemedicamentService: DemandemedicamentService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['demandemedicament']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
    }

    confirmDelete (id: number) {
        this.demandemedicamentService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'demandemedicamentListModification',
                content: 'Deleted an demandemedicament'
            });
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-demandemedicament-delete-popup',
    template: ''
})
export class DemandemedicamentDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private demandemedicamentPopupService: DemandemedicamentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.demandemedicamentPopupService
                .open(DemandemedicamentDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
