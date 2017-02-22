import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Medicament } from './medicament.model';
import { MedicamentService } from './medicament.service';

@Component({
    selector: 'jhi-medicament-detail',
    templateUrl: './medicament-detail.component.html'
})
export class MedicamentDetailComponent implements OnInit, OnDestroy {

    medicament: Medicament;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private medicamentService: MedicamentService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['medicament']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.medicamentService.find(id).subscribe(medicament => {
            this.medicament = medicament;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
