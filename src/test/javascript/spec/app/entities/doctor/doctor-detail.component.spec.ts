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
import { DoctorDetailComponent } from '../../../../../../main/webapp/app/entities/doctor/doctor-detail.component';
import { DoctorService } from '../../../../../../main/webapp/app/entities/doctor/doctor.service';
import { Doctor } from '../../../../../../main/webapp/app/entities/doctor/doctor.model';

describe('Component Tests', () => {

    describe('Doctor Management Detail Component', () => {
        let comp: DoctorDetailComponent;
        let fixture: ComponentFixture<DoctorDetailComponent>;
        let service: DoctorService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [DoctorDetailComponent],
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
                    DoctorService
                ]
            }).overrideComponent(DoctorDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DoctorDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DoctorService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new Doctor('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.doctor).toEqual(jasmine.objectContaining({id:'aaa'}));
            });
        });
    });

});
