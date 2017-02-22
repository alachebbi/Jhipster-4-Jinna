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
import { DemandemedicamentDetailComponent } from '../../../../../../main/webapp/app/entities/demandemedicament/demandemedicament-detail.component';
import { DemandemedicamentService } from '../../../../../../main/webapp/app/entities/demandemedicament/demandemedicament.service';
import { Demandemedicament } from '../../../../../../main/webapp/app/entities/demandemedicament/demandemedicament.model';

describe('Component Tests', () => {

    describe('Demandemedicament Management Detail Component', () => {
        let comp: DemandemedicamentDetailComponent;
        let fixture: ComponentFixture<DemandemedicamentDetailComponent>;
        let service: DemandemedicamentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [DemandemedicamentDetailComponent],
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
                    DemandemedicamentService
                ]
            }).overrideComponent(DemandemedicamentDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DemandemedicamentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DemandemedicamentService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new Demandemedicament('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.demandemedicament).toEqual(jasmine.objectContaining({id:'aaa'}));
            });
        });
    });

});
