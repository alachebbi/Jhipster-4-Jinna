import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AvancementSharedModule } from '../shared';
import {NgPipesModule} from 'ngx-pipes';
import { CHAT_ROUTE, ChatComponent ,Angular2AutoScroll } from './';
import {ChatService} from "./chat.service";


@NgModule({
    imports: [
        AvancementSharedModule,
        RouterModule.forRoot([ CHAT_ROUTE ], { useHash: true }),
        NgPipesModule
    ],
    declarations: [
        ChatComponent,Angular2AutoScroll
    ],
    entryComponents: [
    ],
    providers: [
        ChatService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AvancementChatModule {}
