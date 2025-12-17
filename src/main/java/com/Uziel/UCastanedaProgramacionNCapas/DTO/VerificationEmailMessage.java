package com.Uziel.UCastanedaProgramacionNCapas.DTO;

import java.io.Serializable;

public class VerificationEmailMessage implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private String email;
    private String nombre;
    private String token;
    
    public VerificationEmailMessage(){
    }
    
    public VerificationEmailMessage(String email, String nombre, String token){
        this.email = email;
        this.nombre = nombre;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
