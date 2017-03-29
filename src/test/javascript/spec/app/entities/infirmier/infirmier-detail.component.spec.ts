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
import { InfirmierDetailComponent } from '../../../../../../main/webapp/app/entities/infirmier/infirmier-detail.component';
import { InfirmierService } from '../../../../../../main/webapp/app/entities/infirmier/infirmier.service';
import { Infirmier } from '../../../../../../main/webapp/app/entities/infirmier/infirmier.model';

describe('Component Tests', () => {

    describe('Infirmier Management Detail Component', () => {
        let comp: InfirmierDetailComponent;
        let fixture: ComponentFixture<InfirmierDetailComponent>;
        let service: InfirmierService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [InfirmierDetailComponent],
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
                    InfirmierService
                ]
            }).overrideComponent(InfirmierDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InfirmierDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InfirmierService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new Infirmier('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.infirmier).toEqual(jasmine.objectContaining({id:'aaa'}));
            });
        });
    });

});
