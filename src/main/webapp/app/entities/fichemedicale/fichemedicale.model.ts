export class Fichemedicale {
    constructor(
        public id?: string,
        public nom?: string,
        public prenom?: string,
        public datenaissance?: any,
        public antecedents?: string,
        public resultatdudernierexamen?: string,
        public notesprofitionnelsparamedicaux?: string,
        public comptesrendus?: string,
        public recommandations?: string,
        public perscriptionmedicamenteuses?: string,
        public groupesanguin?: string,
        public compte?: any,
    ) { }
}
