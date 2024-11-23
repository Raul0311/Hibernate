/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.interfazhibernate;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author raulr
 */
@Entity
@Table(name="Supermercados")
public class Supermercado {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="id")
    int id;
    @Column(name="cod_super")
    int cod_super;
    @Column(name="nombre")
    String nombre;
    @Column(name="direccion")
    String direccion;
    @Column(name="email")
    String email;
    @Column(name="telefono")
    String telefono;
    @Column(name="cod_postal")
    String cod_postal;
    
    @OneToMany(mappedBy = "supermercado", cascade = CascadeType.ALL)
    private List<Trabajador> trabajadores;

    public Supermercado() {
        this.cod_super = 0;
        this.nombre = "nombre";
        this.direccion = "direccion";
        this.email = "email";
        this.telefono = "telefono";
        this.cod_postal = "cod_postal";
    }
    
    public int getCod_super() {
        return cod_super;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCod_postal() {
        return cod_postal;
    }
    
    public List<Trabajador> getTrabajadores() {
        return trabajadores;
    }
    
    public void setCod_super(int cod_super) {
        this.cod_super = cod_super;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCod_postal(String cod_postal) {
        this.cod_postal = cod_postal;
    }
    
    public void setTrabajadores(List<Trabajador> trabajadores) {
        this.trabajadores = trabajadores;
    }
    
    @Override
    public String toString(){
        return this.nombre;
    }
}
