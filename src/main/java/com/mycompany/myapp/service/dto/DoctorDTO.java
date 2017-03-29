package com.mycompany.myapp.service.dto;

import java.time.LocalDate;
import java.util.Set;
import com.mycompany.myapp.domain.Doctor;

public class DoctorDTO {

    private String id;

    private String motdepasse;

    private String login;

    private String servicemedi;

    private byte[] photo;

    private String photoContentType;

    private String specialite;

    private LocalDate datenaissance;

    private LocalDate daten;

    private String nometprenom;


    private Set<String> authorities;

    public DoctorDTO(String id, String motdepasse, String login, String servicemedi, String specialite, String photoContentType, String nometprenom, LocalDate datenaissance, LocalDate daten) {
        super();
        this.id = id;
        this.motdepasse = motdepasse;
        this.login = login;
        this.photo = photo;
        this.photoContentType = photoContentType;
        this.specialite = specialite;
        this.datenaissance = datenaissance;
        this.daten = daten;
        this.nometprenom = nometprenom;}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMotdepasse() {
        return motdepasse;
    }



    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    public String getLogin() {
        return login;
    }



    public void setLogin(String login) {
        this.login = login;
    }

    public String getServicemedi() {
        return servicemedi;
    }


    public void setServicemedi(String servicemedi) {
        this.servicemedi = servicemedi;
    }

    public byte[] getPhoto() {
        return photo;
    }




    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }



    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getSpecialite() {
        return specialite;
    }


    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getNometprenom() {
        return nometprenom;
    }



    public void setNometprenom(String nometprenom) {
        this.nometprenom = nometprenom;
    }

    public LocalDate getDatenaissance() {
        return datenaissance;
    }


    public void setDatenaissance(LocalDate datenaissance) {
        this.datenaissance = datenaissance;
    }

    public LocalDate getDaten() {
        return daten;
    }


    public void setDaten(LocalDate daten) {
        this.daten = daten;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }


    public DoctorDTO (Doctor m){
        this(m.getId(), m.getMotdepasse(), m.getLogin(), m.getServicemedi(),
            m.getSpecialite(),m.getPhotoContentType(),m.getNometprenom(),
            m.getDatenaissance(),m.getDaten());
    }

}

