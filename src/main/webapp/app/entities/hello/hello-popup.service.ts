import { Injectable, Component } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Hello } from './hello.model';
import { HelloService } from './hello.service';
@Injectable()
export class HelloPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private helloService: HelloService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.helloService.find(id).subscribe(hello => {
                this.helloModalRef(component, hello);
            });
        } else {
            return this.helloModalRef(component, new Hello());
        }
    }

    helloModalRef(component: Component, hello: Hello): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.hello = hello;
        modalRef.result.then(result => {
            console.log(`Closed with: ${result}`);
            this.isOpen = false;
        }, (reason) => {
            console.log(`Dismissed ${reason}`);
            this.isOpen = false;
        });
        return modalRef;
    }
}
