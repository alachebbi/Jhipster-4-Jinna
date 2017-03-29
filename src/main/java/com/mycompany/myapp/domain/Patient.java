package com.mycompany.myapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Patient.
 */

@Document(collection = "patient")
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("nom")
    private String nom;

    @Field("prenom")
    private String prenom;

    @Field("cin")
    private Long cin;

    @Field("datedenaissance")
    private LocalDate datedenaissance;

    @Field("numsecsociale")
    private Long numsecsociale;

    @Field("dossier")
    private byte[] dossier;

    @Field("dossier_content_type")
    private String dossierContentType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Patient nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Patient prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Long getCin() {
        return cin;
    }

    public Patient cin(Long cin) {
        this.cin = cin;
        return this;
    }

    public void setCin(Long cin) {
        this.cin = cin;
    }

    public LocalDate getDatedenaissance() {
        return datedenaissance;
    }

    public Patient datedenaissance(LocalDate datedenaissance) {
        this.datedenaissance = datedenaissance;
        return this;
    }

    public void setDatedenaissance(LocalDate datedenaissance) {
        this.datedenaissance = datedenaissance;
    }

    public Long getNumsecsociale() {
        return numsecsociale;
    }

    public Patient numsecsociale(Long numsecsociale) {
        this.numsecsociale = numsecsociale;
        return this;
    }

    public void setNumsecsociale(Long numsecsociale) {
        this.numsecsociale = numsecsociale;
    }

    public byte[] getDossier() {
        return dossier;
    }

    public Patient dossier(byte[] dossier) {
        this.dossier = dossier;
        return this;
    }

    public void setDossier(byte[] dossier) {
        this.dossier = dossier;
    }

    public String getDossierContentType() {
        return dossierContentType;
    }

    public Patient dossierContentType(String dossierContentType) {
        this.dossierContentType = dossierContentType;
        return this;
    }

    public void setDossierContentType(String dossierContentType) {
        this.dossierContentType = dossierContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Patient patient = (Patient) o;
        if (patient.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, patient.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Patient{" +
            "id=" + id +
            ", nom='" + nom + "'" +
            ", prenom='" + prenom + "'" +
            ", cin='" + cin + "'" +
            ", datedenaissance='" + datedenaissance + "'" +
            ", numsecsociale='" + numsecsociale + "'" +
            ", dossier='" + dossier + "'" +
            ", dossierContentType='" + dossierContentType + "'" +
            '}';
    }
}
