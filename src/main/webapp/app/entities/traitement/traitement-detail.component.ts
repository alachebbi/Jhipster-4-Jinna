import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Traitement } from './traitement.model';
import { TraitementService } from './traitement.service';

@Component({
    selector: 'jhi-traitement-detail',
    templateUrl: './traitement-detail.component.html'
})
export class TraitementDetailComponent implements OnInit, OnDestroy {

    traitement: Traitement;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private traitementService: TraitementService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['traitement']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.traitementService.find(id).subscribe(traitement => {
            this.traitement = traitement;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
