package com.Uziel.UCastanedaProgramacionNCapas.JPA;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "USUARIO")
public class UsuarioJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario", nullable = false)
    private int IdUsuario;

    @Column(name = "username")
    private String userName;

    @Column(name = "nombre")
    private String Nombre;

    @Column(name = "apellidopaterno")
    private String ApellidoPaterno;

    @Column(name = "apellidomaterno")
    private String ApellidoMaterno;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "fechanacimiento")
    private Date FechaNacimiento;

    @Column(name = "sexo")
    private String Sexo;

    @Column(name = "telefono")
    private String Telefono;

    @Column(name = "celular")
    private String Celular;

    @Column(name = "curp")
    private String Curp;

    @Column(name = "imagenfile")
    @Lob
    private String Imagen;

    @Column(name = "status", nullable = false)
    private int Status;

    @Column(name = "isverified", nullable = false)
    private int IsVerified;

    @ManyToOne
    @JoinColumn(name = "idrol")
    public RolJPA RolJPA;
    
    @OneToOne(mappedBy = "usuarioJPA", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    public PasswordTokenJPA token;

    @OneToMany(mappedBy = "UsuarioJPA", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    public List<DireccionJPA> DireccionesJPA = new ArrayList<>();

    @PrePersist
    private void prePersist() {
        Status = 1;
        IsVerified = 0;
    }

//------------------------------------------------------------------SETTERS Y GETTERS------------------------------------------------------------------//
    public UsuarioJPA() {
    }

    public UsuarioJPA(int IdUsuario, String userName, String Nombre, String ApellidoPaterno, String ApellidoMaterno, String email,
            String password, Date FechaNacimiento, String Sexo, String Telefono, String Celular, String Curp, String Imagen, int Status, int IsVerified) {
        this.IdUsuario = IdUsuario;
        this.userName = userName;
        this.Nombre = Nombre;
        this.ApellidoPaterno = ApellidoPaterno;
        this.ApellidoMaterno = ApellidoMaterno;
        this.email = email;
        this.password = password;
        this.FechaNacimiento = FechaNacimiento;
        this.Sexo = Sexo;
        this.Telefono = Telefono;
        this.Celular = Celular;
        this.Curp = Curp;
        this.Imagen = Imagen;
        this.Status = Status;
        this.IsVerified = IsVerified;
    }

    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setApellidoPaterno(String ApellidoPaterno) {
        this.ApellidoPaterno = ApellidoPaterno;
    }

    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }

    public void setApellidoMaterno(String ApellidoMaterno) {
        this.ApellidoMaterno = ApellidoMaterno;
    }

    public String getApellidoMaterno() {
        return ApellidoMaterno;
    }

    public void setEmail(String Email) {
        this.email = Email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setFechaNacimiento(Date FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }

    public Date getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setSexo(String Sexo) {
        this.Sexo = Sexo;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setCelular(String Celular) {
        this.Celular = Celular;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCurp(String Curp) {
        this.Curp = Curp;
    }

    public String getCurp() {
        return Curp;
    }

    public void setImagen(String Imagen) {
        this.Imagen = Imagen;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public int getStatus() {
        return Status;
    }

    public void setIsVerified(int IsVerified) {
        this.IsVerified = IsVerified;
    }
    
    public int getIsVerified(){
        return IsVerified;
    }

    @JsonIgnore
    public List<DireccionJPA> getDireccionesJPA() {
        return DireccionesJPA;
    }

    public void setDireccionesJPA(List<DireccionJPA> DireccionesJPA) {
        this.DireccionesJPA = DireccionesJPA;
    }

}
