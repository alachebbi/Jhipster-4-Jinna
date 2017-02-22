import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Fichemedicale } from './fichemedicale.model';
import { FichemedicaleService } from './fichemedicale.service';

@Component({
    selector: 'jhi-fichemedicale-detail',
    templateUrl: './fichemedicale-detail.component.html'
})
export class FichemedicaleDetailComponent implements OnInit, OnDestroy {

    fichemedicale: Fichemedicale;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private fichemedicaleService: FichemedicaleService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['fichemedicale']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.fichemedicaleService.find(id).subscribe(fichemedicale => {
            this.fichemedicale = fichemedicale;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
