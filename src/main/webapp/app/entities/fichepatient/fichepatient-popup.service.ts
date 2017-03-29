import { Injectable, Component } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Fichepatient } from './fichepatient.model';
import { FichepatientService } from './fichepatient.service';
@Injectable()
export class FichepatientPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private fichepatientService: FichepatientService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.fichepatientService.find(id).subscribe(fichepatient => {
                if (fichepatient.datedenaissance) {
                    fichepatient.datedenaissance = {
                        year: fichepatient.datedenaissance.getFullYear(),
                        month: fichepatient.datedenaissance.getMonth() + 1,
                        day: fichepatient.datedenaissance.getDate()
                    };
                }
                this.fichepatientModalRef(component, fichepatient);
            });
        } else {
            return this.fichepatientModalRef(component, new Fichepatient());
        }
    }

    fichepatientModalRef(component: Component, fichepatient: Fichepatient): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.fichepatient = fichepatient;
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
