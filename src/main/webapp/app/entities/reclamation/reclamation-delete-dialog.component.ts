import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Reclamation } from './reclamation.model';
import { ReclamationPopupService } from './reclamation-popup.service';
import { ReclamationService } from './reclamation.service';

@Component({
    selector: 'jhi-reclamation-delete-dialog',
    templateUrl: './reclamation-delete-dialog.component.html'
})
export class ReclamationDeleteDialogComponent {

    reclamation: Reclamation;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private reclamationService: ReclamationService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['reclamation']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
    }

    confirmDelete (id: number) {
        this.reclamationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'reclamationListModification',
                content: 'Deleted an reclamation'
            });
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-reclamation-delete-popup',
    template: ''
})
export class ReclamationDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private reclamationPopupService: ReclamationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.reclamationPopupService
                .open(ReclamationDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
