package com.mycompany.myapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Fichemedicale.
 */

@Document(collection = "fichemedicale")
public class Fichemedicale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("nom")
    private String nom;

    @Field("prenom")
    private String prenom;

    @Field("datenaissance")
    private LocalDate datenaissance;

    @Field("antecedents")
    private String antecedents;

    @Field("resultatdudernierexamen")
    private String resultatdudernierexamen;

    @Field("notesprofitionnelsparamedicaux")
    private String notesprofitionnelsparamedicaux;

    @Field("comptesrendus")
    private String comptesrendus;

    @Field("recommandations")
    private String recommandations;

    @Field("perscriptionmedicamenteuses")
    private String perscriptionmedicamenteuses;

    @Field("groupesanguin")
    private String groupesanguin;

    @Field("compte")
    private byte[] compte;

    @Field("compte_content_type")
    private String compteContentType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Fichemedicale nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Fichemedicale prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDatenaissance() {
        return datenaissance;
    }

    public Fichemedicale datenaissance(LocalDate datenaissance) {
        this.datenaissance = datenaissance;
        return this;
    }

    public void setDatenaissance(LocalDate datenaissance) {
        this.datenaissance = datenaissance;
    }

    public String getAntecedents() {
        return antecedents;
    }

    public Fichemedicale antecedents(String antecedents) {
        this.antecedents = antecedents;
        return this;
    }

    public void setAntecedents(String antecedents) {
        this.antecedents = antecedents;
    }

    public String getResultatdudernierexamen() {
        return resultatdudernierexamen;
    }

    public Fichemedicale resultatdudernierexamen(String resultatdudernierexamen) {
        this.resultatdudernierexamen = resultatdudernierexamen;
        return this;
    }

    public void setResultatdudernierexamen(String resultatdudernierexamen) {
        this.resultatdudernierexamen = resultatdudernierexamen;
    }

    public String getNotesprofitionnelsparamedicaux() {
        return notesprofitionnelsparamedicaux;
    }

    public Fichemedicale notesprofitionnelsparamedicaux(String notesprofitionnelsparamedicaux) {
        this.notesprofitionnelsparamedicaux = notesprofitionnelsparamedicaux;
        return this;
    }

    public void setNotesprofitionnelsparamedicaux(String notesprofitionnelsparamedicaux) {
        this.notesprofitionnelsparamedicaux = notesprofitionnelsparamedicaux;
    }

    public String getComptesrendus() {
        return comptesrendus;
    }

    public Fichemedicale comptesrendus(String comptesrendus) {
        this.comptesrendus = comptesrendus;
        return this;
    }

    public void setComptesrendus(String comptesrendus) {
        this.comptesrendus = comptesrendus;
    }

    public String getRecommandations() {
        return recommandations;
    }

    public Fichemedicale recommandations(String recommandations) {
        this.recommandations = recommandations;
        return this;
    }

    public void setRecommandations(String recommandations) {
        this.recommandations = recommandations;
    }

    public String getPerscriptionmedicamenteuses() {
        return perscriptionmedicamenteuses;
    }

    public Fichemedicale perscriptionmedicamenteuses(String perscriptionmedicamenteuses) {
        this.perscriptionmedicamenteuses = perscriptionmedicamenteuses;
        return this;
    }

    public void setPerscriptionmedicamenteuses(String perscriptionmedicamenteuses) {
        this.perscriptionmedicamenteuses = perscriptionmedicamenteuses;
    }

    public String getGroupesanguin() {
        return groupesanguin;
    }

    public Fichemedicale groupesanguin(String groupesanguin) {
        this.groupesanguin = groupesanguin;
        return this;
    }

    public void setGroupesanguin(String groupesanguin) {
        this.groupesanguin = groupesanguin;
    }

    public byte[] getCompte() {
        return compte;
    }

    public Fichemedicale compte(byte[] compte) {
        this.compte = compte;
        return this;
    }

    public void setCompte(byte[] compte) {
        this.compte = compte;
    }

    public String getCompteContentType() {
        return compteContentType;
    }

    public Fichemedicale compteContentType(String compteContentType) {
        this.compteContentType = compteContentType;
        return this;
    }

    public void setCompteContentType(String compteContentType) {
        this.compteContentType = compteContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Fichemedicale fichemedicale = (Fichemedicale) o;
        if (fichemedicale.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, fichemedicale.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Fichemedicale{" +
            "id=" + id +
            ", nom='" + nom + "'" +
            ", prenom='" + prenom + "'" +
            ", datenaissance='" + datenaissance + "'" +
            ", antecedents='" + antecedents + "'" +
            ", resultatdudernierexamen='" + resultatdudernierexamen + "'" +
            ", notesprofitionnelsparamedicaux='" + notesprofitionnelsparamedicaux + "'" +
            ", comptesrendus='" + comptesrendus + "'" +
            ", recommandations='" + recommandations + "'" +
            ", perscriptionmedicamenteuses='" + perscriptionmedicamenteuses + "'" +
            ", groupesanguin='" + groupesanguin + "'" +
            ", compte='" + compte + "'" +
            ", compteContentType='" + compteContentType + "'" +
            '}';
    }
}
