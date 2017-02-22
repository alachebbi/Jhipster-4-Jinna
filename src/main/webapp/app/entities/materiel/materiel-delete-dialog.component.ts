import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Materiel } from './materiel.model';
import { MaterielPopupService } from './materiel-popup.service';
import { MaterielService } from './materiel.service';

@Component({
    selector: 'jhi-materiel-delete-dialog',
    templateUrl: './materiel-delete-dialog.component.html'
})
export class MaterielDeleteDialogComponent {

    materiel: Materiel;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private materielService: MaterielService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['materiel']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
    }

    confirmDelete (id: number) {
        this.materielService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'materielListModification',
                content: 'Deleted an materiel'
            });
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-materiel-delete-popup',
    template: ''
})
export class MaterielDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private materielPopupService: MaterielPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.materielPopupService
                .open(MaterielDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
