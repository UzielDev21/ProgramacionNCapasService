package com.Uziel.UCastanedaProgramacionNCapas.JPA;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "PASSWORDTOKEN")
public class PasswordTokenJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtoken", nullable = false)
    private int idToken;

    @Column(name = "token", unique = true, nullable = false)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuario", nullable = false)
    @JsonBackReference
    public UsuarioJPA usuarioJPA;

    @Column(name = "expiration", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiration;

    @Column(name = "isused")
    private int isUsed;

    
//------------------------------------------------------------------SETTERS Y GETTERS------------------------------------------------------------------//
    public PasswordTokenJPA() {
    }

    public PasswordTokenJPA(String token, Date expiration, int isUsed) {
        this.token = token;
        this.expiration = expiration;
        this.isUsed = isUsed;
    }

    public int getIdToken() {
        return idToken;
    }

    public void setIdToken(int idToken) {
        this.idToken = idToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UsuarioJPA getUsuarioJPA() {
        return usuarioJPA;
    }

    public void setUsuarioJPA(UsuarioJPA usuarioJPA) {
        this.usuarioJPA = usuarioJPA;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }

}
