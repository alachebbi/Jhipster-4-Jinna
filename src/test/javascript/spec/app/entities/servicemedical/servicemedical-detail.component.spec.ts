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
import { ServicemedicalDetailComponent } from '../../../../../../main/webapp/app/entities/servicemedical/servicemedical-detail.component';
import { ServicemedicalService } from '../../../../../../main/webapp/app/entities/servicemedical/servicemedical.service';
import { Servicemedical } from '../../../../../../main/webapp/app/entities/servicemedical/servicemedical.model';

describe('Component Tests', () => {

    describe('Servicemedical Management Detail Component', () => {
        let comp: ServicemedicalDetailComponent;
        let fixture: ComponentFixture<ServicemedicalDetailComponent>;
        let service: ServicemedicalService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [ServicemedicalDetailComponent],
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
                    ServicemedicalService
                ]
            }).overrideComponent(ServicemedicalDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ServicemedicalDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ServicemedicalService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new Servicemedical('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.servicemedical).toEqual(jasmine.objectContaining({id:'aaa'}));
            });
        });
    });

});
