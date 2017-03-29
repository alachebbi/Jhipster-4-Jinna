import { Component ,OnInit, OnDestroy} from '@angular/core';
import { Response } from  '@angular/http';
import { DoctorService } from '../entities/doctor/doctor.service'
import  * as _ from 'lodash'
@Component({
    selector: 'bar-chart-demo',
    templateUrl: './chart.component.html',
    styleUrls: [
        'chart.css'
    ]
})
export class ChartComponent implements OnInit{
    public barChartOptions:any = {
        scaleShowVerticalLines: false,
        responsive: true
    };
    public barChartLabels:string[] = [];
    public barChartType:string = 'bar';
    public barChartLegend:boolean = true;

    public barChartData:any[] = [
        {data: [], label: 'Doctor CHART BY SPECIALITEES'},
    ];

    constructor(
        private doctorservice : DoctorService
    ) {
    }

    ngOnInit(){
        let titles = [] ;
        let values = [] ;
        this.doctorservice.getChartData()
            .subscribe(data => {
                _.forEach(data, item => {
                       titles.push(item.title) ;
                       values.push(item.value);
                });
                let clone = JSON.parse(JSON.stringify(this.barChartData));
                clone[0].data = values;
                this.barChartLabels = titles;
                this.barChartData = clone;
            })
    }

    // events
    public chartClicked(e:any):void {
        console.log(e);
    }

    public chartHovered(e:any):void {
        console.log(e);
    }

    public changeCharType(){
        if(this.barChartType == "bar"){
            this.barChartType = "line" ;
        }else{
            this.barChartType ="bar" ;
        }
    }
}
