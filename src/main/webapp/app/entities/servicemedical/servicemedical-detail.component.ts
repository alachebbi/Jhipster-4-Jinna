import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService ,AlertService} from 'ng-jhipster';
import { Servicemedical } from './servicemedical.model';
import { ServicemedicalService } from './servicemedical.service';
import {Doctor} from "../doctor/doctor.model";
import {DoctorService} from "../doctor/doctor.service";
import {Response} from "@angular/http";


@Component({
    selector: 'jhi-servicemedical-detail',
    templateUrl: './servicemedical-detail.component.html'
})
export class ServicemedicalDetailComponent implements OnInit, OnDestroy {

    servicemedical: Servicemedical;
    private subscription: any;
    doctors: Doctor[];
    name:string;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private doctorService : DoctorService,
        private alertService: AlertService,
        private servicemedicalService: ServicemedicalService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['servicemedical']);
    }

    ngOnInit() {

        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);


        });
    }

    load (id) {
        this.servicemedicalService.find(id).subscribe(servicemedical => {
            this.servicemedical = servicemedical;
            this.name=this.servicemedical.nom;
            this.loadAll(this.name);


        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
    loadAll(nom) {
        this.doctorService.query().subscribe(
            (res: Response) => {
                this.doctors = res.json().filter(doctor =>doctor.servicemedi==nom);
            },
            (res: Response) => this.onError(res.json())
        );

    }
    private onError (error) {
        this.alertService.error(error.message, null, null);
    }


}
