package com.mycompany.myapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Traitement.
 */

@Document(collection = "traitement")
public class Traitement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("maladie")
    private String maladie;

    @Field("symptomes")
    private String symptomes;

    @Field("medicamentid")
    private String medicamentid;

    @Field("duree")
    private String duree;

    @Field("fois")
    private String fois;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaladie() {
        return maladie;
    }

    public Traitement maladie(String maladie) {
        this.maladie = maladie;
        return this;
    }

    public void setMaladie(String maladie) {
        this.maladie = maladie;
    }

    public String getSymptomes() {
        return symptomes;
    }

    public Traitement symptomes(String symptomes) {
        this.symptomes = symptomes;
        return this;
    }

    public void setSymptomes(String symptomes) {
        this.symptomes = symptomes;
    }

    public String getMedicamentid() {
        return medicamentid;
    }

    public Traitement medicamentid(String medicamentid) {
        this.medicamentid = medicamentid;
        return this;
    }

    public void setMedicamentid(String medicamentid) {
        this.medicamentid = medicamentid;
    }

    public String getDuree() {
        return duree;
    }

    public Traitement duree(String duree) {
        this.duree = duree;
        return this;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getFois() {
        return fois;
    }

    public Traitement fois(String fois) {
        this.fois = fois;
        return this;
    }

    public void setFois(String fois) {
        this.fois = fois;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Traitement traitement = (Traitement) o;
        if (traitement.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, traitement.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Traitement{" +
            "id=" + id +
            ", maladie='" + maladie + "'" +
            ", symptomes='" + symptomes + "'" +
            ", medicamentid='" + medicamentid + "'" +
            ", duree='" + duree + "'" +
            ", fois='" + fois + "'" +
            '}';
    }
}
