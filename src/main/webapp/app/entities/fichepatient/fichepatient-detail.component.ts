import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {JhiLanguageService, DataUtils, AlertService} from 'ng-jhipster';
import { Fichepatient } from './fichepatient.model';
import { FichepatientService } from './fichepatient.service';
import {Medecin} from '../medecin/medecin.model'
import {MedecinService} from '../medecin/medecin.service'
import {Response} from "@angular/http";

@Component({
    selector: 'jhi-fichepatient-detail',
    templateUrl: './fichepatient-detail.component.html'
})
export class FichepatientDetailComponent implements OnInit, OnDestroy {

    fichepatient: Fichepatient;
    medecin: Medecin;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private dataUtils: DataUtils,
        private alertService: AlertService,
        private medecinService: MedecinService,
        private fichepatientService: FichepatientService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['fichepatient']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
            this.loadAllmed(params['med']);
            this.loadAllser(params['medid']);
        });
    }

    load (id) {
        this.fichepatientService.find(id).subscribe(fichepatient => {
            this.fichepatient = fichepatient;
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
    loadAllser(medid) {
        this.fichepatientService.query().subscribe(
            (res: Response) => {
                this.medecin = res.json().filter((medecin =>medecin.id===medid));
            },
            (res: Response) => this.onError(res.json())
        );

    }
    loadAllmed(med) {
        this.fichepatientService.query().subscribe(
            (res: Response) => {
                this.medecin = res.json().filter((medecin =>medecin.id===med));
            },
            (res: Response) => this.onError(res.json())
        );

    }
    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
