import { Component, OnInit,OnDestroy } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager} from 'ng-jhipster';
import { Account, LoginModalService, Principal } from '../shared';
import { Message} from "./message.model";
import {Angular2AutoScroll} from "angular2-auto-scroll/lib/angular2-auto-scroll.directive";
import  { UserService} from  '../shared/user/user.service'
import  {Response} from '@angular/http'
import * as _ from  'lodash' ;
import  'rxjs';
import {ChatService} from "./chat.service";
var SockJS = require('sockjs-client');
var Stomp = require('stompjs');

@Component({
    selector: 'jhi-chat',
    templateUrl: './chat.component.html',
    styleUrls: [
        'chat.css'
    ]

})
export class ChatComponent implements OnInit ,OnDestroy{
    account: Account;
    modalRef: NgbModalRef;
    stompClient: any;
    stompTopicSubscription:any;
    stompConnectSubscription:any;
    stompDisconnectSubscription:any;
    messages:Message[] =[];
    receiverMessage:Message[] =[];

    private sendText:string ;
    sender:string ;
    header:any ;
    receiver:string = "clovis";
    users:any[] = [] ;


    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: EventManager,
        private userService:UserService,
        private chatService:ChatService
    ) {
    }

    private connect(){
        var socket = new SockJS('/chat');
        this.stompClient = Stomp.over(socket);
        this.stompClient.connect(this.header,(frame) => {

            // subscribe to the Topic Chanel
            this.stompTopicSubscription =  this.stompClient.subscribe('/topic/messages',
                message =>   {
                    let msg = JSON.parse(message.body);
                    if(msg.receiverName === this.receiver || msg.receiverName === this.sender){
                        this.receiverMessage.push(msg)
                    }
                    this.chatService.add(msg);
                },this.header);


        }) ;

    }


    onUserSelected(user){
        _.forEach(this.users, item =>{
            if(item == user){
                item.active = true ;
                this.receiver = item.login
            }else{
                item.active = false ;
            }
        });

        let msgs:Message[] = [] ;

        _.forEach(this.chatService.get(), msg =>{
            if(user.login === msg.receiverName && msg.receiverName === this.sender){
                msgs.push(msg) ;
            }
        });

        this.receiverMessage = msgs ;
    }


    sendMessage(){
        let  m = new Message();
        m.msg = this.sendText ;
        m.senderName = this.sender;
        m.receiverName = this.receiver  ;
        this.stompClient.send('/api/hello',  this.header,JSON.stringify(m));
        this.sendText = '';
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
            this.sender = account.login ;
            this.header = {'X-SENDER': this.sender};

        });
        this.userService.query()
            .map((res:Response) => res.json())
            .subscribe(users => {
                _.forEach(users , user =>{
                    if(user.login !==  this.sender){
                        this.users.push(user);
                    }});
                this.onUserSelected(this.users[0]);
                this.connect();
            });


        this.registerAuthenticationSuccess();
        this.messages =  [] ;
    }

    ngOnDestroy() {
        if(this.stompTopicSubscription!=null){
            this.stompTopicSubscription.unsubscribe();
        }
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }
}
