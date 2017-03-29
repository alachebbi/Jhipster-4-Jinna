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
import { TraitementDetailComponent } from '../../../../../../main/webapp/app/entities/traitement/traitement-detail.component';
import { TraitementService } from '../../../../../../main/webapp/app/entities/traitement/traitement.service';
import { Traitement } from '../../../../../../main/webapp/app/entities/traitement/traitement.model';

describe('Component Tests', () => {

    describe('Traitement Management Detail Component', () => {
        let comp: TraitementDetailComponent;
        let fixture: ComponentFixture<TraitementDetailComponent>;
        let service: TraitementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [TraitementDetailComponent],
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
                    TraitementService
                ]
            }).overrideComponent(TraitementDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TraitementDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TraitementService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new Traitement('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.traitement).toEqual(jasmine.objectContaining({id:'aaa'}));
            });
        });
    });

});
