import { Injectable, Component } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Materiel } from './materiel.model';
import { MaterielService } from './materiel.service';
@Injectable()
export class MaterielPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private materielService: MaterielService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.materielService.find(id).subscribe(materiel => {
                if (materiel.dateproduction) {
                    materiel.dateproduction = {
                        year: materiel.dateproduction.getFullYear(),
                        month: materiel.dateproduction.getMonth() + 1,
                        day: materiel.dateproduction.getDate()
                    };
                }
                if (materiel.datevalidite) {
                    materiel.datevalidite = {
                        year: materiel.datevalidite.getFullYear(),
                        month: materiel.datevalidite.getMonth() + 1,
                        day: materiel.datevalidite.getDate()
                    };
                }
                this.materielModalRef(component, materiel);
            });
        } else {
            return this.materielModalRef(component, new Materiel());
        }
    }

    materielModalRef(component: Component, materiel: Materiel): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.materiel = materiel;
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
