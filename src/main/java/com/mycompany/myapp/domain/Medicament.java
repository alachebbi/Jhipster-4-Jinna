package com.mycompany.myapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Medicament.
 */

@Document(collection = "medicament")
public class Medicament implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("nom")
    private String nom;

    @Field("type")
    private String type;

    @Field("quantity")
    private Integer quantity;

    @Field("ref")
    private String ref;

    @Field("datevalidite")
    private LocalDate datevalidite;

    @Field("dateproduction")
    private LocalDate dateproduction;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Medicament nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public Medicament type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Medicament quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getRef() {
        return ref;
    }

    public Medicament ref(String ref) {
        this.ref = ref;
        return this;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public LocalDate getDatevalidite() {
        return datevalidite;
    }

    public Medicament datevalidite(LocalDate datevalidite) {
        this.datevalidite = datevalidite;
        return this;
    }

    public void setDatevalidite(LocalDate datevalidite) {
        this.datevalidite = datevalidite;
    }

    public LocalDate getDateproduction() {
        return dateproduction;
    }

    public Medicament dateproduction(LocalDate dateproduction) {
        this.dateproduction = dateproduction;
        return this;
    }

    public void setDateproduction(LocalDate dateproduction) {
        this.dateproduction = dateproduction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Medicament medicament = (Medicament) o;
        if (medicament.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, medicament.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Medicament{" +
            "id=" + id +
            ", nom='" + nom + "'" +
            ", type='" + type + "'" +
            ", quantity='" + quantity + "'" +
            ", ref='" + ref + "'" +
            ", datevalidite='" + datevalidite + "'" +
            ", dateproduction='" + dateproduction + "'" +
            '}';
    }
}
