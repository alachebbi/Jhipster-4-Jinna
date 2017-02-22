import { Injectable, Component } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Servicemedical } from './servicemedical.model';
import { ServicemedicalService } from './servicemedical.service';
@Injectable()
export class ServicemedicalPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private servicemedicalService: ServicemedicalService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.servicemedicalService.find(id).subscribe(servicemedical => {
                this.servicemedicalModalRef(component, servicemedical);
            });
        } else {
            return this.servicemedicalModalRef(component, new Servicemedical());
        }
    }

    servicemedicalModalRef(component: Component, servicemedical: Servicemedical): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.servicemedical = servicemedical;
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
