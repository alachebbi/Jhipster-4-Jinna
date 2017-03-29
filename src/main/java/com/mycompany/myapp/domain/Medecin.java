package com.mycompany.myapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Medecin.
 */

@Document(collection = "medecin")
public class Medecin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("nom")
    private String nom;

    @Field("prenom")
    private String prenom;

    @Field("datenaissance")
    private LocalDate datenaissance;

    @Field("serviceid")
    private String serviceid;

    @Field("specialite")
    private String specialite;

    @Field("photo")
    private byte[] photo;

    @Field("photo_content_type")
    private String photoContentType;

    @Field("login")
    private String login;

    @Field("full_name")
    private String fullName;

    @Field("motdepasse")
    private String motdepasse;

    @Field("numero")
    private String numero;

    @Field("numr")
    private String numr;

    @Field("numer")
    private String numer;

    @Field("az")
    private String az;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Medecin nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Medecin prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDatenaissance() {
        return datenaissance;
    }

    public Medecin datenaissance(LocalDate datenaissance) {
        this.datenaissance = datenaissance;
        return this;
    }

    public void setDatenaissance(LocalDate datenaissance) {
        this.datenaissance = datenaissance;
    }

    public String getServiceid() {
        return serviceid;
    }

    public Medecin serviceid(String serviceid) {
        this.serviceid = serviceid;
        return this;
    }

    public void setServiceid(String serviceid) {
        this.serviceid = serviceid;
    }

    public String getSpecialite() {
        return specialite;
    }

    public Medecin specialite(String specialite) {
        this.specialite = specialite;
        return this;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Medecin photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public Medecin photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getLogin() {
        return login;
    }

    public Medecin login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFullName() {
        return fullName;
    }

    public Medecin fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public Medecin motdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
        return this;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    public String getNumero() {
        return numero;
    }

    public Medecin numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNumr() {
        return numr;
    }

    public Medecin numr(String numr) {
        this.numr = numr;
        return this;
    }

    public void setNumr(String numr) {
        this.numr = numr;
    }

    public String getNumer() {
        return numer;
    }

    public Medecin numer(String numer) {
        this.numer = numer;
        return this;
    }

    public void setNumer(String numer) {
        this.numer = numer;
    }

    public String getAz() {
        return az;
    }

    public Medecin az(String az) {
        this.az = az;
        return this;
    }

    public void setAz(String az) {
        this.az = az;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Medecin medecin = (Medecin) o;
        if (medecin.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, medecin.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Medecin{" +
            "id=" + id +
            ", nom='" + nom + "'" +
            ", prenom='" + prenom + "'" +
            ", datenaissance='" + datenaissance + "'" +
            ", serviceid='" + serviceid + "'" +
            ", specialite='" + specialite + "'" +
            ", photo='" + photo + "'" +
            ", photoContentType='" + photoContentType + "'" +
            ", login='" + login + "'" +
            ", fullName='" + fullName + "'" +
            ", motdepasse='" + motdepasse + "'" +
            ", numero='" + numero + "'" +
            ", numr='" + numr + "'" +
            ", numer='" + numer + "'" +
            ", az='" + az + "'" +
            '}';
    }
}
