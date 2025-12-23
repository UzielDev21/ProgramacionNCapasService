package com.Uziel.UCastanedaProgramacionNCapas.JPA;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "PASSWORDTOKEN")
public class PasswordTokenJPA {

    private static final int EXPIRATION = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtoken", nullable = false)
    private int idToken;

    @Column(name = "token", unique = true, nullable = false)
    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idusuario", nullable = false)
    @JsonBackReference
    public UsuarioJPA usuarioJPA;

    @Column(name = "expirydate")
    private Date expiryDate;

//------------------------------------------------------------------SETTERS Y GETTERS------------------------------------------------------------------//
    public PasswordTokenJPA() {
    }

    public PasswordTokenJPA(String token, Date expiryDate) {
        this.token = token;
        this.expiryDate = expiryDate;
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

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

}
