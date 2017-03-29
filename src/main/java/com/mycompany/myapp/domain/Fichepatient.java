package com.mycompany.myapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Fichepatient.
 */

@Document(collection = "fichepatient")
public class Fichepatient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("nom")
    private String nom;

    @Field("prenom")
    private String prenom;

    @Field("medid")
    private String medid;

    @Field("datedenaissance")
    private LocalDate datedenaissance;

    @Field("antecedents")
    private String antecedents;

    @Field("resultatdudernierexamen")
    private String resultatdudernierexamen;

    @Field("notesprofitionnelparamedicaux")
    private String notesprofitionnelparamedicaux;

    @Field("compterendu")
    private byte[] compterendu;

    @Field("compterendu_content_type")
    private String compterenduContentType;

    @Field("recommandations")
    private String recommandations;

    @Field("perscriptionmedicamenteuses")
    private String perscriptionmedicamenteuses;

    @Field("groupesanguin")
    private String groupesanguin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Fichepatient nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Fichepatient prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMedid() {
        return medid;
    }

    public Fichepatient medid(String medid) {
        this.medid = medid;
        return this;
    }

    public void setMedid(String medid) {
        this.medid = medid;
    }

    public LocalDate getDatedenaissance() {
        return datedenaissance;
    }

    public Fichepatient datedenaissance(LocalDate datedenaissance) {
        this.datedenaissance = datedenaissance;
        return this;
    }

    public void setDatedenaissance(LocalDate datedenaissance) {
        this.datedenaissance = datedenaissance;
    }

    public String getAntecedents() {
        return antecedents;
    }

    public Fichepatient antecedents(String antecedents) {
        this.antecedents = antecedents;
        return this;
    }

    public void setAntecedents(String antecedents) {
        this.antecedents = antecedents;
    }

    public String getResultatdudernierexamen() {
        return resultatdudernierexamen;
    }

    public Fichepatient resultatdudernierexamen(String resultatdudernierexamen) {
        this.resultatdudernierexamen = resultatdudernierexamen;
        return this;
    }

    public void setResultatdudernierexamen(String resultatdudernierexamen) {
        this.resultatdudernierexamen = resultatdudernierexamen;
    }

    public String getNotesprofitionnelparamedicaux() {
        return notesprofitionnelparamedicaux;
    }

    public Fichepatient notesprofitionnelparamedicaux(String notesprofitionnelparamedicaux) {
        this.notesprofitionnelparamedicaux = notesprofitionnelparamedicaux;
        return this;
    }

    public void setNotesprofitionnelparamedicaux(String notesprofitionnelparamedicaux) {
        this.notesprofitionnelparamedicaux = notesprofitionnelparamedicaux;
    }

    public byte[] getCompterendu() {
        return compterendu;
    }

    public Fichepatient compterendu(byte[] compterendu) {
        this.compterendu = compterendu;
        return this;
    }

    public void setCompterendu(byte[] compterendu) {
        this.compterendu = compterendu;
    }

    public String getCompterenduContentType() {
        return compterenduContentType;
    }

    public Fichepatient compterenduContentType(String compterenduContentType) {
        this.compterenduContentType = compterenduContentType;
        return this;
    }

    public void setCompterenduContentType(String compterenduContentType) {
        this.compterenduContentType = compterenduContentType;
    }

    public String getRecommandations() {
        return recommandations;
    }

    public Fichepatient recommandations(String recommandations) {
        this.recommandations = recommandations;
        return this;
    }

    public void setRecommandations(String recommandations) {
        this.recommandations = recommandations;
    }

    public String getPerscriptionmedicamenteuses() {
        return perscriptionmedicamenteuses;
    }

    public Fichepatient perscriptionmedicamenteuses(String perscriptionmedicamenteuses) {
        this.perscriptionmedicamenteuses = perscriptionmedicamenteuses;
        return this;
    }

    public void setPerscriptionmedicamenteuses(String perscriptionmedicamenteuses) {
        this.perscriptionmedicamenteuses = perscriptionmedicamenteuses;
    }

    public String getGroupesanguin() {
        return groupesanguin;
    }

    public Fichepatient groupesanguin(String groupesanguin) {
        this.groupesanguin = groupesanguin;
        return this;
    }

    public void setGroupesanguin(String groupesanguin) {
        this.groupesanguin = groupesanguin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Fichepatient fichepatient = (Fichepatient) o;
        if (fichepatient.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, fichepatient.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Fichepatient{" +
            "id=" + id +
            ", nom='" + nom + "'" +
            ", prenom='" + prenom + "'" +
            ", medid='" + medid + "'" +
            ", datedenaissance='" + datedenaissance + "'" +
            ", antecedents='" + antecedents + "'" +
            ", resultatdudernierexamen='" + resultatdudernierexamen + "'" +
            ", notesprofitionnelparamedicaux='" + notesprofitionnelparamedicaux + "'" +
            ", compterendu='" + compterendu + "'" +
            ", compterenduContentType='" + compterenduContentType + "'" +
            ", recommandations='" + recommandations + "'" +
            ", perscriptionmedicamenteuses='" + perscriptionmedicamenteuses + "'" +
            ", groupesanguin='" + groupesanguin + "'" +
            '}';
    }
}
