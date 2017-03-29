package com.mycompany.myapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Hello.
 */

@Document(collection = "hello")
public class Hello implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("medicamentid")
    private String medicamentid;

    @Field("maladie")
    private String maladie;

    @Field("symptomes")
    private String symptomes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Hello name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMedicamentid() {
        return medicamentid;
    }

    public Hello medicamentid(String medicamentid) {
        this.medicamentid = medicamentid;
        return this;
    }

    public void setMedicamentid(String medicamentid) {
        this.medicamentid = medicamentid;
    }

    public String getMaladie() {
        return maladie;
    }

    public Hello maladie(String maladie) {
        this.maladie = maladie;
        return this;
    }

    public void setMaladie(String maladie) {
        this.maladie = maladie;
    }

    public String getSymptomes() {
        return symptomes;
    }

    public Hello symptomes(String symptomes) {
        this.symptomes = symptomes;
        return this;
    }

    public void setSymptomes(String symptomes) {
        this.symptomes = symptomes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Hello hello = (Hello) o;
        if (hello.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, hello.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Hello{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", medicamentid='" + medicamentid + "'" +
            ", maladie='" + maladie + "'" +
            ", symptomes='" + symptomes + "'" +
            '}';
    }
}
