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
import { MedicamentDetailComponent } from '../../../../../../main/webapp/app/entities/medicament/medicament-detail.component';
import { MedicamentService } from '../../../../../../main/webapp/app/entities/medicament/medicament.service';
import { Medicament } from '../../../../../../main/webapp/app/entities/medicament/medicament.model';

describe('Component Tests', () => {

    describe('Medicament Management Detail Component', () => {
        let comp: MedicamentDetailComponent;
        let fixture: ComponentFixture<MedicamentDetailComponent>;
        let service: MedicamentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [MedicamentDetailComponent],
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
                    MedicamentService
                ]
            }).overrideComponent(MedicamentDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MedicamentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MedicamentService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new Medicament('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.medicament).toEqual(jasmine.objectContaining({id:'aaa'}));
            });
        });
    });

});
