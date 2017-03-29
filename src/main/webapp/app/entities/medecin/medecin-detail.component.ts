import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService, DataUtils } from 'ng-jhipster';
import { Medecin } from './medecin.model';
import { MedecinService } from './medecin.service';

@Component({
    selector: 'jhi-medecin-detail',
    templateUrl: './medecin-detail.component.html'
})
export class MedecinDetailComponent implements OnInit, OnDestroy {

    medecin: Medecin;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private dataUtils: DataUtils,
        private medecinService: MedecinService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['medecin']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
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

}
