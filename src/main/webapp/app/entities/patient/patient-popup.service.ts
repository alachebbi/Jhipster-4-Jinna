import { Injectable, Component } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Patient } from './patient.model';
import { PatientService } from './patient.service';
@Injectable()
export class PatientPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private patientService: PatientService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.patientService.find(id).subscribe(patient => {
                if (patient.datedenaissance) {
                    patient.datedenaissance = {
                        year: patient.datedenaissance.getFullYear(),
                        month: patient.datedenaissance.getMonth() + 1,
                        day: patient.datedenaissance.getDate()
                    };
                }
                this.patientModalRef(component, patient);
            });
        } else {
            return this.patientModalRef(component, new Patient());
        }
    }

    patientModalRef(component: Component, patient: Patient): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.patient = patient;
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
