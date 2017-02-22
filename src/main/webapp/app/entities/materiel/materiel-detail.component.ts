import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Materiel } from './materiel.model';
import { MaterielService } from './materiel.service';

@Component({
    selector: 'jhi-materiel-detail',
    templateUrl: './materiel-detail.component.html'
})
export class MaterielDetailComponent implements OnInit, OnDestroy {

    materiel: Materiel;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private materielService: MaterielService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['materiel']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.materielService.find(id).subscribe(materiel => {
            this.materiel = materiel;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
