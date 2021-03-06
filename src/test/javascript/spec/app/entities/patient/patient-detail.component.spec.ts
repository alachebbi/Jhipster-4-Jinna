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
import { PatientDetailComponent } from '../../../../../../main/webapp/app/entities/patient/patient-detail.component';
import { PatientService } from '../../../../../../main/webapp/app/entities/patient/patient.service';
import { Patient } from '../../../../../../main/webapp/app/entities/patient/patient.model';

describe('Component Tests', () => {

    describe('Patient Management Detail Component', () => {
        let comp: PatientDetailComponent;
        let fixture: ComponentFixture<PatientDetailComponent>;
        let service: PatientService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [PatientDetailComponent],
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
                    PatientService
                ]
            }).overrideComponent(PatientDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PatientDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PatientService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new Patient('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.patient).toEqual(jasmine.objectContaining({id:'aaa'}));
            });
        });
    });

});
