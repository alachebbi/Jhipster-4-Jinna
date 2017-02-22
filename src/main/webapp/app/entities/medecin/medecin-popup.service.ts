import { Injectable, Component } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Medecin } from './medecin.model';
import { MedecinService } from './medecin.service';
@Injectable()
export class MedecinPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private medecinService: MedecinService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.medecinService.find(id).subscribe(medecin => {
                if (medecin.datenaissance) {
                    medecin.datenaissance = {
                        year: medecin.datenaissance.getFullYear(),
                        month: medecin.datenaissance.getMonth() + 1,
                        day: medecin.datenaissance.getDate()
                    };
                }
                this.medecinModalRef(component, medecin);
            });
        } else {
            return this.medecinModalRef(component, new Medecin());
        }
    }

    medecinModalRef(component: Component, medecin: Medecin): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.medecin = medecin;
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
