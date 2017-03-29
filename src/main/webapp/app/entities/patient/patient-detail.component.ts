import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService, DataUtils } from 'ng-jhipster';
import { Patient } from './patient.model';
import { PatientService } from './patient.service';

@Component({
    selector: 'jhi-patient-detail',
    templateUrl: './patient-detail.component.html'
})
export class PatientDetailComponent implements OnInit, OnDestroy {

    patient: Patient;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private dataUtils: DataUtils,
        private patientService: PatientService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['patient']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.patientService.find(id).subscribe(patient => {
            this.patient = patient;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
