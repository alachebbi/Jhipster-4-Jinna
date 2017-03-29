import { Injectable, Component } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Traitement } from './traitement.model';
import { TraitementService } from './traitement.service';
@Injectable()
export class TraitementPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private traitementService: TraitementService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.traitementService.find(id).subscribe(traitement => {
                this.traitementModalRef(component, traitement);
            });
        } else {
            return this.traitementModalRef(component, new Traitement());
        }
    }

    traitementModalRef(component: Component, traitement: Traitement): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.traitement = traitement;
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
