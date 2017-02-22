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
import { FichemedicaleDetailComponent } from '../../../../../../main/webapp/app/entities/fichemedicale/fichemedicale-detail.component';
import { FichemedicaleService } from '../../../../../../main/webapp/app/entities/fichemedicale/fichemedicale.service';
import { Fichemedicale } from '../../../../../../main/webapp/app/entities/fichemedicale/fichemedicale.model';

describe('Component Tests', () => {

    describe('Fichemedicale Management Detail Component', () => {
        let comp: FichemedicaleDetailComponent;
        let fixture: ComponentFixture<FichemedicaleDetailComponent>;
        let service: FichemedicaleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [FichemedicaleDetailComponent],
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
                    FichemedicaleService
                ]
            }).overrideComponent(FichemedicaleDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FichemedicaleDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FichemedicaleService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new Fichemedicale('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.fichemedicale).toEqual(jasmine.objectContaining({id:'aaa'}));
            });
        });
    });

});
