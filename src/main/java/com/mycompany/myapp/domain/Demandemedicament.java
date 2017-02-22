package com.mycompany.myapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Demandemedicament.
 */

@Document(collection = "demandemedicament")
public class Demandemedicament implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("date")
    private LocalDate date;

    @Field("time")
    private ZonedDateTime time;

    @Field("type")
    private String type;

    @Field("quantity")
    private Integer quantity;

    @Field("etat")
    private String etat;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Demandemedicament date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public Demandemedicament time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public Demandemedicament type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Demandemedicament quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getEtat() {
        return etat;
    }

    public Demandemedicament etat(String etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Demandemedicament demandemedicament = (Demandemedicament) o;
        if (demandemedicament.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, demandemedicament.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Demandemedicament{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", time='" + time + "'" +
            ", type='" + type + "'" +
            ", quantity='" + quantity + "'" +
            ", etat='" + etat + "'" +
            '}';
    }
}
