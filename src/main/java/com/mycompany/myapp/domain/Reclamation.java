package com.mycompany.myapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Reclamation.
 */

@Document(collection = "reclamation")
public class Reclamation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("titre")
    private String titre;

    @Field("objet")
    private String objet;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public Reclamation titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getObjet() {
        return objet;
    }

    public Reclamation objet(String objet) {
        this.objet = objet;
        return this;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reclamation reclamation = (Reclamation) o;
        if (reclamation.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, reclamation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Reclamation{" +
            "id=" + id +
            ", titre='" + titre + "'" +
            ", objet='" + objet + "'" +
            '}';
    }
}
