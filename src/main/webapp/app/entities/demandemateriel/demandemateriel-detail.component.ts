import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Demandemateriel } from './demandemateriel.model';
import { DemandematerielService } from './demandemateriel.service';

@Component({
    selector: 'jhi-demandemateriel-detail',
    templateUrl: './demandemateriel-detail.component.html'
})
export class DemandematerielDetailComponent implements OnInit, OnDestroy {

    demandemateriel: Demandemateriel;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private demandematerielService: DemandematerielService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['demandemateriel']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.demandematerielService.find(id).subscribe(demandemateriel => {
            this.demandemateriel = demandemateriel;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
