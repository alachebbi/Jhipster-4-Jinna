export class Medicament {
    constructor(
        public id?: string,
        public nom?: string,
        public type?: string,
        public quantity?: number,
        public ref?: string,
        public datevalidite?: any,
        public dateproduction?: any,
    ) { }
}
