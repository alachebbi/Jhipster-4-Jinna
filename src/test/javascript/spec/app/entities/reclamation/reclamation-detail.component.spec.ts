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
import { ReclamationDetailComponent } from '../../../../../../main/webapp/app/entities/reclamation/reclamation-detail.component';
import { ReclamationService } from '../../../../../../main/webapp/app/entities/reclamation/reclamation.service';
import { Reclamation } from '../../../../../../main/webapp/app/entities/reclamation/reclamation.model';

describe('Component Tests', () => {

    describe('Reclamation Management Detail Component', () => {
        let comp: ReclamationDetailComponent;
        let fixture: ComponentFixture<ReclamationDetailComponent>;
        let service: ReclamationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [ReclamationDetailComponent],
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
                    ReclamationService
                ]
            }).overrideComponent(ReclamationDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReclamationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReclamationService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new Reclamation('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.reclamation).toEqual(jasmine.objectContaining({id:'aaa'}));
            });
        });
    });

});
