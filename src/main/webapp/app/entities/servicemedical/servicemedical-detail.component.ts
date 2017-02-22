import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService ,AlertService} from 'ng-jhipster';
import { Servicemedical } from './servicemedical.model';
import { ServicemedicalService } from './servicemedical.service';
import {Medecin} from "../medecin/medecin.model";
import {MedecinService} from "../medecin/medecin.service";
import {Response} from "@angular/http";


@Component({
    selector: 'jhi-servicemedical-detail',
    templateUrl: './servicemedical-detail.component.html'
})
export class ServicemedicalDetailComponent implements OnInit, OnDestroy {

    servicemedical: Servicemedical;
    private subscription: any;
    medecins: Medecin[];

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private medecinService : MedecinService,
        private alertService: AlertService,
        private servicemedicalService: ServicemedicalService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['servicemedical']);
    }

    ngOnInit() {

        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
            this.loadAll(params['id']);
        });
    }

    load (id) {
        this.servicemedicalService.find(id).subscribe(servicemedical => {
            this.servicemedical = servicemedical;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
    loadAll(id) {
        this.medecinService.query().subscribe(
            (res: Response) => {
                this.medecins = res.json().filter((medecin =>medecin.serviceid===id));
            },
            (res: Response) => this.onError(res.json())
        );

    }
    private onError (error) {
        this.alertService.error(error.message, null, null);
    }


}
