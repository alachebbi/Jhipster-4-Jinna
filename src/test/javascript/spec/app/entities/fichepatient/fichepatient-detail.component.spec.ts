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
import { FichepatientDetailComponent } from '../../../../../../main/webapp/app/entities/fichepatient/fichepatient-detail.component';
import { FichepatientService } from '../../../../../../main/webapp/app/entities/fichepatient/fichepatient.service';
import { Fichepatient } from '../../../../../../main/webapp/app/entities/fichepatient/fichepatient.model';

describe('Component Tests', () => {

    describe('Fichepatient Management Detail Component', () => {
        let comp: FichepatientDetailComponent;
        let fixture: ComponentFixture<FichepatientDetailComponent>;
        let service: FichepatientService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [FichepatientDetailComponent],
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
                    FichepatientService
                ]
            }).overrideComponent(FichepatientDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FichepatientDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FichepatientService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new Fichepatient('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.fichepatient).toEqual(jasmine.objectContaining({id:'aaa'}));
            });
        });
    });

});
