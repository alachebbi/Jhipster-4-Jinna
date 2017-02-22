import { Injectable, Component } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Reclamation } from './reclamation.model';
import { ReclamationService } from './reclamation.service';
@Injectable()
export class ReclamationPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private reclamationService: ReclamationService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.reclamationService.find(id).subscribe(reclamation => {
                this.reclamationModalRef(component, reclamation);
            });
        } else {
            return this.reclamationModalRef(component, new Reclamation());
        }
    }

    reclamationModalRef(component: Component, reclamation: Reclamation): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.reclamation = reclamation;
        modalRef.result.then(result => {
            console.log(`Closed with: ${result}`);
            this.isOpen = false;
        }, (reason) => {
            console.log(`Dismissed ${reason}`);
            this.isOpen = false;
        });
        return modalRef;
    }
}
