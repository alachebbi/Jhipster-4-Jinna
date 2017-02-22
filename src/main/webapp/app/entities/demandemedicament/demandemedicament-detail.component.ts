import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Demandemedicament } from './demandemedicament.model';
import { DemandemedicamentService } from './demandemedicament.service';

@Component({
    selector: 'jhi-demandemedicament-detail',
    templateUrl: './demandemedicament-detail.component.html'
})
export class DemandemedicamentDetailComponent implements OnInit, OnDestroy {

    demandemedicament: Demandemedicament;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private demandemedicamentService: DemandemedicamentService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['demandemedicament']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.demandemedicamentService.find(id).subscribe(demandemedicament => {
            this.demandemedicament = demandemedicament;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
