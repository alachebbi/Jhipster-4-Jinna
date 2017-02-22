import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Materiel } from './materiel.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class MaterielService {

    private resourceUrl = 'api/materiels';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(materiel: Materiel): Observable<Materiel> {
        let copy: Materiel = Object.assign({}, materiel);
        copy.dateproduction = this.dateUtils
            .convertLocalDateToServer(materiel.dateproduction);
        copy.datevalidite = this.dateUtils
            .convertLocalDateToServer(materiel.datevalidite);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(materiel: Materiel): Observable<Materiel> {
        let copy: Materiel = Object.assign({}, materiel);
        copy.dateproduction = this.dateUtils
            .convertLocalDateToServer(materiel.dateproduction);
        copy.datevalidite = this.dateUtils
            .convertLocalDateToServer(materiel.datevalidite);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Materiel> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            let jsonResponse = res.json();
            jsonResponse.dateproduction = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.dateproduction);
            jsonResponse.datevalidite = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.datevalidite);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }


    private convertResponse(res: any): any {
        let jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].dateproduction = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].dateproduction);
            jsonResponse[i].datevalidite = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].datevalidite);
        }
        res._body = jsonResponse;
        return res;
    }

    private createRequestOption(req?: any): BaseRequestOptions {
        let options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            let params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }
}
