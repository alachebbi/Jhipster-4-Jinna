import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService, DataUtils ,AlertService} from 'ng-jhipster';
import { Medecin } from './medecin.model';
import { MedecinService } from './medecin.service';
import {Servicemedical} from "../servicemedical/servicemedical.model";
import {ServicemedicalService} from "../servicemedical/servicemedical.service";
import {Response} from "@angular/http";

@Component({
    selector: 'jhi-medecin-detail',
    templateUrl: './medecin-detail.component.html'
})
export class MedecinDetailComponent implements OnInit, OnDestroy {

    medecin: Medecin;
    private subscription: any;
    servicemedical:Servicemedical;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private dataUtils: DataUtils,
        private medecinService: MedecinService,
        private alertService: AlertService,
        private servicemedicalService: ServicemedicalService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['medecin']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
            this.loadAllser(params['serviceid']);
        });
    }

    load (id) {
        this.medecinService.find(id).subscribe(medecin => {
            this.medecin = medecin;
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
    loadAllser(serviceid) {
        this.medecinService.query().subscribe(
            (res: Response) => {
                this.servicemedical = res.json().filter((servicemedical =>servicemedical.id===serviceid));
            },
            (res: Response) => this.onError(res.json())
        );

    }
    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

}
