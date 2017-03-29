import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService, DataUtils,AlertService} from 'ng-jhipster';
import { Fichemedicale } from './fichemedicale.model';
import { FichemedicaleService } from './fichemedicale.service';
import { Medecin } from '../medecin/medecin.model';
import { MedecinService } from '../medecin/medecin.service';
import {Response} from "@angular/http";

@Component({
    selector: 'jhi-fichemedicale-detail',
    templateUrl: './fichemedicale-detail.component.html'
})
export class FichemedicaleDetailComponent implements OnInit, OnDestroy {

    fichemedicale: Fichemedicale;
    medecin: Medecin;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private dataUtils: DataUtils,
        private medecinService: MedecinService,
        private alertService: AlertService,
        private fichemedicaleService: FichemedicaleService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['fichemedicale']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
            this.loadAllmed(params['medid']);
        });
    }

    load (id) {
        this.fichemedicaleService.find(id).subscribe(fichemedicale => {
            this.fichemedicale = fichemedicale;
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
    loadAllmed(medid) {
        this.fichemedicaleService.query().subscribe(
            (res: Response) => {
                this.medecin = res.json().filter((medecin =>medecin.id===medid));
            },
            (res: Response) => this.onError(res.json())
        );

    }





    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
