export class Materiel {
    constructor(
        public id?: string,
        public nom?: string,
        public type?: string,
        public quantity?: number,
        public ref?: string,
        public dateproduction?: any,
        public datevalidite?: any,
    ) { }
}
