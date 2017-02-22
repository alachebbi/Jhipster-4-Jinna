import { Injectable, Component } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Demandemedicament } from './demandemedicament.model';
import { DemandemedicamentService } from './demandemedicament.service';
@Injectable()
export class DemandemedicamentPopupService {
    private isOpen = false;
    constructor (
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private demandemedicamentService: DemandemedicamentService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.demandemedicamentService.find(id).subscribe(demandemedicament => {
                if (demandemedicament.date) {
                    demandemedicament.date = {
                        year: demandemedicament.date.getFullYear(),
                        month: demandemedicament.date.getMonth() + 1,
                        day: demandemedicament.date.getDate()
                    };
                }
                demandemedicament.time = this.datePipe.transform(demandemedicament.time, 'yyyy-MM-ddThh:mm');
                this.demandemedicamentModalRef(component, demandemedicament);
            });
        } else {
            return this.demandemedicamentModalRef(component, new Demandemedicament());
        }
    }

    demandemedicamentModalRef(component: Component, demandemedicament: Demandemedicament): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.demandemedicament = demandemedicament;
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
