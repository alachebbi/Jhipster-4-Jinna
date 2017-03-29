import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { JhiLanguageService } from 'ng-jhipster';
import { MockLanguageService } from '../../../helpers/mock-language.service';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { HelloDetailComponent } from '../../../../../../main/webapp/app/entities/hello/hello-detail.component';
import { HelloService } from '../../../../../../main/webapp/app/entities/hello/hello.service';
import { Hello } from '../../../../../../main/webapp/app/entities/hello/hello.model';

describe('Component Tests', () => {

    describe('Hello Management Detail Component', () => {
        let comp: HelloDetailComponent;
        let fixture: ComponentFixture<HelloDetailComponent>;
        let service: HelloService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [HelloDetailComponent],
                providers: [
                    MockBackend,
                    BaseRequestOptions,
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    {
                        provide: Http,
                        useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                            return new Http(backendInstance, defaultOptions);
                        },
                        deps: [MockBackend, BaseRequestOptions]
                    },
                    {
                        provide: JhiLanguageService,
                        useClass: MockLanguageService
                    },
                    HelloService
                ]
            }).overrideComponent(HelloDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HelloDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HelloService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new Hello('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.hello).toEqual(jasmine.objectContaining({id:'aaa'}));
            });
        });
    });

});
