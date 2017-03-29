import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService, DataUtils } from 'ng-jhipster';
import { Doctor } from './doctor.model';
import { DoctorService } from './doctor.service';

@Component({
    selector: 'jhi-doctor-detail',
    templateUrl: './doctor-detail.component.html'
})
export class DoctorDetailComponent implements OnInit, OnDestroy {

    doctor: Doctor;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private dataUtils: DataUtils,
        private doctorService: DoctorService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['doctor']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);

        });
    }

    load (id) {
        this.doctorService.find(id).subscribe(doctor => {
            this.doctor = doctor;
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
