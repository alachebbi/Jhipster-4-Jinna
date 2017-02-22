import { Injectable, Component } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Medicament } from './medicament.model';
import { MedicamentService } from './medicament.service';
@Injectable()
export class MedicamentPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private medicamentService: MedicamentService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.medicamentService.find(id).subscribe(medicament => {
                if (medicament.datevalidite) {
                    medicament.datevalidite = {
                        year: medicament.datevalidite.getFullYear(),
                        month: medicament.datevalidite.getMonth() + 1,
                        day: medicament.datevalidite.getDate()
                    };
                }
                if (medicament.dateproduction) {
                    medicament.dateproduction = {
                        year: medicament.dateproduction.getFullYear(),
                        month: medicament.dateproduction.getMonth() + 1,
                        day: medicament.dateproduction.getDate()
                    };
                }
                this.medicamentModalRef(component, medicament);
            });
        } else {
            return this.medicamentModalRef(component, new Medicament());
        }
    }

    medicamentModalRef(component: Component, medicament: Medicament): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.medicament = medicament;
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
