import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Medicament } from './medicament.model';
import { MedicamentPopupService } from './medicament-popup.service';
import { MedicamentService } from './medicament.service';

@Component({
    selector: 'jhi-medicament-delete-dialog',
    templateUrl: './medicament-delete-dialog.component.html'
})
export class MedicamentDeleteDialogComponent {

    medicament: Medicament;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private medicamentService: MedicamentService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['medicament']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
    }

    confirmDelete (id: number) {
        this.medicamentService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'medicamentListModification',
                content: 'Deleted an medicament'
            });
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-medicament-delete-popup',
    template: ''
})
export class MedicamentDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private medicamentPopupService: MedicamentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.medicamentPopupService
                .open(MedicamentDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
