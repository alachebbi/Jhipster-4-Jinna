import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Hello } from './hello.model';
import { HelloService } from './hello.service';

@Component({
    selector: 'jhi-hello-detail',
    templateUrl: './hello-detail.component.html'
})
export class HelloDetailComponent implements OnInit, OnDestroy {

    hello: Hello;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private helloService: HelloService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['hello']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.helloService.find(id).subscribe(hello => {
            this.hello = hello;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
