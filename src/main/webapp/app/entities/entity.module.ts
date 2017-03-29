import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AvancementMedecinModule } from './medecin/medecin.module';
import { AvancementServicemedicalModule } from './servicemedical/servicemedical.module';
import { AvancementFichemedicaleModule } from './fichemedicale/fichemedicale.module';
import { AvancementReclamationModule } from './reclamation/reclamation.module';
import { AvancementPatientModule } from './patient/patient.module';
import { AvancementMaterielModule } from './materiel/materiel.module';
import { AvancementMedicamentModule } from './medicament/medicament.module';
import { AvancementDemandemedicamentModule } from './demandemedicament/demandemedicament.module';
import { AvancementDemandematerielModule } from './demandemateriel/demandemateriel.module';
import { AvancementHelloModule } from './hello/hello.module';

import { AvancementFichepatientModule } from './fichepatient/fichepatient.module';
import { AvancementDoctorModule } from './doctor/doctor.module';
import { AvancementTraitementModule } from './traitement/traitement.module';
import { AvancementInfirmierModule } from './infirmier/infirmier.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        AvancementMedecinModule,
        AvancementServicemedicalModule,
        AvancementFichemedicaleModule,
        AvancementReclamationModule,
        AvancementPatientModule,
        AvancementMaterielModule,
        AvancementMedicamentModule,
        AvancementDemandemedicamentModule,
        AvancementDemandematerielModule,
        AvancementHelloModule,
        AvancementFichepatientModule,
        AvancementDoctorModule,
        AvancementTraitementModule,
        AvancementInfirmierModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AvancementEntityModule {}
