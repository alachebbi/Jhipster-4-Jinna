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
import { MaterielDetailComponent } from '../../../../../../main/webapp/app/entities/materiel/materiel-detail.component';
import { MaterielService } from '../../../../../../main/webapp/app/entities/materiel/materiel.service';
import { Materiel } from '../../../../../../main/webapp/app/entities/materiel/materiel.model';

describe('Component Tests', () => {

    describe('Materiel Management Detail Component', () => {
        let comp: MaterielDetailComponent;
        let fixture: ComponentFixture<MaterielDetailComponent>;
        let service: MaterielService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [MaterielDetailComponent],
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
                    MaterielService
                ]
            }).overrideComponent(MaterielDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MaterielDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MaterielService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new Materiel('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.materiel).toEqual(jasmine.objectContaining({id:'aaa'}));
            });
        });
    });

});
