import { Injectable, Component } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Infirmier } from './infirmier.model';
import { InfirmierService } from './infirmier.service';
@Injectable()
export class InfirmierPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private infirmierService: InfirmierService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.infirmierService.find(id).subscribe(infirmier => {
                this.infirmierModalRef(component, infirmier);
            });
        } else {
            return this.infirmierModalRef(component, new Infirmier());
        }
    }

    infirmierModalRef(component: Component, infirmier: Infirmier): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.infirmier = infirmier;
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
