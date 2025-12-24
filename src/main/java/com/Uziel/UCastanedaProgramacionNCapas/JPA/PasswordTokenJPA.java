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
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "PASSWORDTOKEN")
public class PasswordTokenJPA {

    private static final int EXPIRATION_MINUTES = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtoken", nullable = false)
    private Integer idToken;

    @Column(name = "token", unique = true, nullable = false)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuario", nullable = false)
    @JsonBackReference
    public UsuarioJPA usuarioJPA;

    @Column(name = "expirydate", nullable = false)
    private LocalDateTime expiryDate;

//------------------------------------------------------------------CONSTRUCTORES------------------------------------------------------------------//
    public PasswordTokenJPA() {
    }

    public PasswordTokenJPA(String token,UsuarioJPA usuarioJPA) {
        this.token = token;
        this.usuarioJPA = usuarioJPA;
        this.expiryDate = calculateExpiryDate(EXPIRATION_MINUTES);
    }

    public PasswordTokenJPA(String token,UsuarioJPA usuarioJPA, LocalDateTime expiryDate) {
        this.token = token;
        this.usuarioJPA = usuarioJPA;
        this.expiryDate = expiryDate;
    }
    
//------------------------------------------------------------------HELPERS------------------------------------------------------------------/    
        
    public static LocalDateTime calculateExpiryDate(int minutes){
        return LocalDateTime.now().plusMinutes(minutes);
    }
    
    public boolean isExpired(){
        return expiryDate.isBefore(LocalDateTime.now());
    }
    
//------------------------------------------------------------------SETTERS Y GETTERS------------------------------------------------------------------/
    public Integer getIdToken() {
        return idToken;
    }

    public void setIdToken(Integer idToken) {
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

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

}
