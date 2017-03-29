export class Fichepatient {
    constructor(
        public id?: string,
        public nom?: string,
        public prenom?: string,
        public medid?: string,
        public datedenaissance?: any,
        public antecedents?: string,
        public resultatdudernierexamen?: string,
        public notesprofitionnelparamedicaux?: string,
        public compterendu?: any,
        public recommandations?: string,
        public perscriptionmedicamenteuses?: string,
        public groupesanguin?: string,
    ) { }
}
