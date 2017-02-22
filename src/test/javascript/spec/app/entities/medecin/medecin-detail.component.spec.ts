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
import { MedecinDetailComponent } from '../../../../../../main/webapp/app/entities/medecin/medecin-detail.component';
import { MedecinService } from '../../../../../../main/webapp/app/entities/medecin/medecin.service';
import { Medecin } from '../../../../../../main/webapp/app/entities/medecin/medecin.model';

describe('Component Tests', () => {

    describe('Medecin Management Detail Component', () => {
        let comp: MedecinDetailComponent;
        let fixture: ComponentFixture<MedecinDetailComponent>;
        let service: MedecinService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [MedecinDetailComponent],
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
                    MedecinService
                ]
            }).overrideComponent(MedecinDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MedecinDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MedecinService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new Medecin('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.medecin).toEqual(jasmine.objectContaining({id:'aaa'}));
            });
        });
    });

});
