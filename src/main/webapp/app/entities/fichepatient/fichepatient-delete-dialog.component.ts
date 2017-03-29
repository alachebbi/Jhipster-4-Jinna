import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Fichepatient } from './fichepatient.model';
import { FichepatientPopupService } from './fichepatient-popup.service';
import { FichepatientService } from './fichepatient.service';

@Component({
    selector: 'jhi-fichepatient-delete-dialog',
    templateUrl: './fichepatient-delete-dialog.component.html'
})
export class FichepatientDeleteDialogComponent {

    fichepatient: Fichepatient;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private fichepatientService: FichepatientService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['fichepatient']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
    }

    confirmDelete (id: number) {
        this.fichepatientService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'fichepatientListModification',
                content: 'Deleted an fichepatient'
            });
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-fichepatient-delete-popup',
    template: ''
})
export class FichepatientDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private fichepatientPopupService: FichepatientPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.fichepatientPopupService
                .open(FichepatientDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
