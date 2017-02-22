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
import { DemandematerielDetailComponent } from '../../../../../../main/webapp/app/entities/demandemateriel/demandemateriel-detail.component';
import { DemandematerielService } from '../../../../../../main/webapp/app/entities/demandemateriel/demandemateriel.service';
import { Demandemateriel } from '../../../../../../main/webapp/app/entities/demandemateriel/demandemateriel.model';

describe('Component Tests', () => {

    describe('Demandemateriel Management Detail Component', () => {
        let comp: DemandematerielDetailComponent;
        let fixture: ComponentFixture<DemandematerielDetailComponent>;
        let service: DemandematerielService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [DemandematerielDetailComponent],
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
                    DemandematerielService
                ]
            }).overrideComponent(DemandematerielDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DemandematerielDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DemandematerielService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new Demandemateriel('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.demandemateriel).toEqual(jasmine.objectContaining({id:'aaa'}));
            });
        });
    });

});
