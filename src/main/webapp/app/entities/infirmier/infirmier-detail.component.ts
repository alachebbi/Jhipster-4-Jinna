import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Infirmier } from './infirmier.model';
import { InfirmierService } from './infirmier.service';

@Component({
    selector: 'jhi-infirmier-detail',
    templateUrl: './infirmier-detail.component.html'
})
export class InfirmierDetailComponent implements OnInit, OnDestroy {

    infirmier: Infirmier;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private infirmierService: InfirmierService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['infirmier']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.infirmierService.find(id).subscribe(infirmier => {
            this.infirmier = infirmier;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
