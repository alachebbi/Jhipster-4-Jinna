import { Injectable, Component } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Demandemateriel } from './demandemateriel.model';
import { DemandematerielService } from './demandemateriel.service';
@Injectable()
export class DemandematerielPopupService {
    private isOpen = false;
    constructor (
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private demandematerielService: DemandematerielService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.demandematerielService.find(id).subscribe(demandemateriel => {
                if (demandemateriel.date) {
                    demandemateriel.date = {
                        year: demandemateriel.date.getFullYear(),
                        month: demandemateriel.date.getMonth() + 1,
                        day: demandemateriel.date.getDate()
                    };
                }
                demandemateriel.time = this.datePipe.transform(demandemateriel.time, 'yyyy-MM-ddThh:mm');
                this.demandematerielModalRef(component, demandemateriel);
            });
        } else {
            return this.demandematerielModalRef(component, new Demandemateriel());
        }
    }

    demandematerielModalRef(component: Component, demandemateriel: Demandemateriel): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.demandemateriel = demandemateriel;
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
