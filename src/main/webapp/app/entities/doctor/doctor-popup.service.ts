import { Injectable, Component } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Doctor } from './doctor.model';
import { DoctorService } from './doctor.service';
@Injectable()
export class DoctorPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private doctorService: DoctorService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.doctorService.find(id).subscribe(doctor => {
                if (doctor.datenaissance) {
                    doctor.datenaissance = {
                        year: doctor.datenaissance.getFullYear(),
                        month: doctor.datenaissance.getMonth() + 1,
                        day: doctor.datenaissance.getDate()
                    };
                }
                if (doctor.daten) {
                    doctor.daten = {
                        year: doctor.daten.getFullYear(),
                        month: doctor.daten.getMonth() + 1,
                        day: doctor.daten.getDate()
                    };
                }
                this.doctorModalRef(component, doctor);
            });
        } else {
            return this.doctorModalRef(component, new Doctor());
        }
    }

    doctorModalRef(component: Component, doctor: Doctor): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.doctor = doctor;
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
