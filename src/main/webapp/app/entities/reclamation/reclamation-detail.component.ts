import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Reclamation } from './reclamation.model';
import { ReclamationService } from './reclamation.service';

@Component({
    selector: 'jhi-reclamation-detail',
    templateUrl: './reclamation-detail.component.html'
})
export class ReclamationDetailComponent implements OnInit, OnDestroy {

    reclamation: Reclamation;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private reclamationService: ReclamationService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['reclamation']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.reclamationService.find(id).subscribe(reclamation => {
            this.reclamation = reclamation;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
