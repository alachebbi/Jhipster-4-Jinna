import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Infirmier } from './infirmier.model';
import { InfirmierPopupService } from './infirmier-popup.service';
import { InfirmierService } from './infirmier.service';

@Component({
    selector: 'jhi-infirmier-delete-dialog',
    templateUrl: './infirmier-delete-dialog.component.html'
})
export class InfirmierDeleteDialogComponent {

    infirmier: Infirmier;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private infirmierService: InfirmierService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['infirmier']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
    }

    confirmDelete (id: number) {
        this.infirmierService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'infirmierListModification',
                content: 'Deleted an infirmier'
            });
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-infirmier-delete-popup',
    template: ''
})
export class InfirmierDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private infirmierPopupService: InfirmierPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.infirmierPopupService
                .open(InfirmierDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
