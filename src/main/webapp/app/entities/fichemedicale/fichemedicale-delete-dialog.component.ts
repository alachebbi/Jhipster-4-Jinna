import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Fichemedicale } from './fichemedicale.model';
import { FichemedicalePopupService } from './fichemedicale-popup.service';
import { FichemedicaleService } from './fichemedicale.service';

@Component({
    selector: 'jhi-fichemedicale-delete-dialog',
    templateUrl: './fichemedicale-delete-dialog.component.html'
})
export class FichemedicaleDeleteDialogComponent {

    fichemedicale: Fichemedicale;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private fichemedicaleService: FichemedicaleService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['fichemedicale']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
    }

    confirmDelete (id: number) {
        this.fichemedicaleService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'fichemedicaleListModification',
                content: 'Deleted an fichemedicale'
            });
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-fichemedicale-delete-popup',
    template: ''
})
export class FichemedicaleDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private fichemedicalePopupService: FichemedicalePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.fichemedicalePopupService
                .open(FichemedicaleDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
