package com.Uziel.UCastanedaProgramacionNCapas.JPA;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

public class Result {

    public boolean correct;
    public String errorMessage;
    public Object object;
    public List<Object> objects;
    public Exception ex;
    
    @JsonIgnore //Anotación utlizada para que no se muestre en la vista este dato al usuario
    public int status;
}