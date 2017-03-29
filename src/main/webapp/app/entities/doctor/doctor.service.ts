import { Injectable } from '@angular/core';
import {Http, Response, URLSearchParams, BaseRequestOptions, ResponseContentType} from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Doctor } from './doctor.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class DoctorService {

    private resourceUrl = 'api/doctors';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(doctor: Doctor): Observable<Doctor> {
        let copy: Doctor = Object.assign({}, doctor);
        copy.datenaissance = this.dateUtils
            .convertLocalDateToServer(doctor.datenaissance);
        copy.daten = this.dateUtils
            .convertLocalDateToServer(doctor.daten);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(doctor: Doctor): Observable<Doctor> {
        let copy: Doctor = Object.assign({}, doctor);
        copy.datenaissance = this.dateUtils
            .convertLocalDateToServer(doctor.datenaissance);
        copy.daten = this.dateUtils
            .convertLocalDateToServer(doctor.daten);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }


    find(id: number): Observable<Doctor> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            let jsonResponse = res.json();
            jsonResponse.datenaissance = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.datenaissance);
            jsonResponse.daten = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.daten);
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

    getChartData(): Observable<any> {
        return this.http.get(this.resourceUrl+'/chartdata').map((res: Response) => {
            return res.json();
        });
    }

    private convertResponse(res: any): any {
        let jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].datenaissance = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].datenaissance);
            jsonResponse[i].daten = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].daten);
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
    printAll() : Observable<any>{
        return this.http.get(this.resourceUrl+'/all-doctor.pdf',{ responseType:
        ResponseContentType.Blob })
            .map(resp => {
                return new Blob([resp.blob()], { type: 'application/pdf' })});
    }
    printOne(id:string) : Observable<any>{
        return  this.http.get(this.resourceUrl+`/${id}/doctor.pdf`,{ responseType: ResponseContentType.Blob })
            .map(resp =>  {
                return new Blob([resp.blob()], { type: 'application/pdf' })});

    }
   /* printOne(id:string) : Observable<any>{
        return  this.http.get(this.resourceUrl+`/${id}/medecin.pdf`,{ responseType: ResponseContentType.Blob })
            .map(resp =>  {
                return new Blob([resp.blob()], { type: 'application/pdf' })});

    }*/

}
