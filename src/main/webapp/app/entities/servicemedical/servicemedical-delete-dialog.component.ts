import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Servicemedical } from './servicemedical.model';
import { ServicemedicalPopupService } from './servicemedical-popup.service';
import { ServicemedicalService } from './servicemedical.service';

@Component({
    selector: 'jhi-servicemedical-delete-dialog',
    templateUrl: './servicemedical-delete-dialog.component.html'
})
export class ServicemedicalDeleteDialogComponent {

    servicemedical: Servicemedical;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private servicemedicalService: ServicemedicalService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['servicemedical']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
    }

    confirmDelete (id: number) {
        this.servicemedicalService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'servicemedicalListModification',
                content: 'Deleted an servicemedical'
            });
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-servicemedical-delete-popup',
    template: ''
})
export class ServicemedicalDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private servicemedicalPopupService: ServicemedicalPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.servicemedicalPopupService
                .open(ServicemedicalDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
