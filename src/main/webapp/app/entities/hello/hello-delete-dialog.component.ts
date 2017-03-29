import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Hello } from './hello.model';
import { HelloPopupService } from './hello-popup.service';
import { HelloService } from './hello.service';

@Component({
    selector: 'jhi-hello-delete-dialog',
    templateUrl: './hello-delete-dialog.component.html'
})
export class HelloDeleteDialogComponent {

    hello: Hello;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private helloService: HelloService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager,
        private router: Router
    ) {
        this.jhiLanguageService.setLocations(['hello']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
        this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
    }

    confirmDelete (id: number) {
        this.helloService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'helloListModification',
                content: 'Deleted an hello'
            });
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-hello-delete-popup',
    template: ''
})
export class HelloDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private helloPopupService: HelloPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.helloPopupService
                .open(HelloDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
