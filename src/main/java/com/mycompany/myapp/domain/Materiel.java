package com.mycompany.myapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Materiel.
 */

@Document(collection = "materiel")
public class Materiel implements Serializable {

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

    @Field("dateproduction")
    private LocalDate dateproduction;

    @Field("datevalidite")
    private LocalDate datevalidite;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Materiel nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public Materiel type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Materiel quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getRef() {
        return ref;
    }

    public Materiel ref(String ref) {
        this.ref = ref;
        return this;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public LocalDate getDateproduction() {
        return dateproduction;
    }

    public Materiel dateproduction(LocalDate dateproduction) {
        this.dateproduction = dateproduction;
        return this;
    }

    public void setDateproduction(LocalDate dateproduction) {
        this.dateproduction = dateproduction;
    }

    public LocalDate getDatevalidite() {
        return datevalidite;
    }

    public Materiel datevalidite(LocalDate datevalidite) {
        this.datevalidite = datevalidite;
        return this;
    }

    public void setDatevalidite(LocalDate datevalidite) {
        this.datevalidite = datevalidite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Materiel materiel = (Materiel) o;
        if (materiel.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, materiel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Materiel{" +
            "id=" + id +
            ", nom='" + nom + "'" +
            ", type='" + type + "'" +
            ", quantity='" + quantity + "'" +
            ", ref='" + ref + "'" +
            ", dateproduction='" + dateproduction + "'" +
            ", datevalidite='" + datevalidite + "'" +
            '}';
    }
}
