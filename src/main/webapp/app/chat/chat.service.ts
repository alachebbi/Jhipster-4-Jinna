
import { Injectable } from '@angular/core';
import {Message} from "./message.model";

@Injectable()
export class ChatService {

    private messages: Message[] = [];


    add(msg:Message){
        this.messages.push(msg) ;
    }


    get():Message[]{
        return this.messages ;
    }

}
