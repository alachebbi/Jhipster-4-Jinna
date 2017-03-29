import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Traitement } from './traitement.model';
import { TraitementPopupService } from './traitement-popup.service';
import { TraitementService } from './traitement.service';

@Component({
    selector: 'jhi-traitement-delete-dialog',
    templateUrl: './traitement-delete-dialog.component.html'
})
export class TraitementDeleteDialogComponent {

    traitement: Traitement;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private traitementService: TraitementService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['traitement']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
    }

    confirmDelete (id: number) {
        this.traitementService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'traitementListModification',
                content: 'Deleted an traitement'
            });
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-traitement-delete-popup',
    template: ''
})
export class TraitementDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private traitementPopupService: TraitementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.traitementPopupService
                .open(TraitementDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
