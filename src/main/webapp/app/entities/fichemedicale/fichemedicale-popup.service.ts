import { Injectable, Component } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Fichemedicale } from './fichemedicale.model';
import { FichemedicaleService } from './fichemedicale.service';
@Injectable()
export class FichemedicalePopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private fichemedicaleService: FichemedicaleService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.fichemedicaleService.find(id).subscribe(fichemedicale => {
                if (fichemedicale.datenaissance) {
                    fichemedicale.datenaissance = {
                        year: fichemedicale.datenaissance.getFullYear(),
                        month: fichemedicale.datenaissance.getMonth() + 1,
                        day: fichemedicale.datenaissance.getDate()
                    };
                }
                this.fichemedicaleModalRef(component, fichemedicale);
            });
        } else {
            return this.fichemedicaleModalRef(component, new Fichemedicale());
        }
    }

    fichemedicaleModalRef(component: Component, fichemedicale: Fichemedicale): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.fichemedicale = fichemedicale;
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
