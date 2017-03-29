export class Message {
    constructor(
        public senderName?: string,
        public receiverName?: string,
        public msg?: string,
        public createdDate?:Date,
    ) { }
}
