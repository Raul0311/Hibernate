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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author raulr
 */
@Entity
@Table(name="Trabajadores")
public class Trabajador {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="id")
    int id;
    @Column(name="cod_trabajador")
    int cod_trabajador;
    @Column(name="dni")
    String dni;
    @Column(name="nombre")
    String nombre;
    @Column(name="telefono")
    String telefono;
    @Column(name="email")
    String email;
    @Column(name="direccion")
    String direccion;
    
    @ManyToOne
    @JoinColumn(name="id_supermercado")
    private Supermercado supermercado;
    
    @OneToMany(mappedBy = "trabajador", cascade = CascadeType.ALL)
    private List<Producto> productos;

    public Trabajador() {
        this.cod_trabajador = 0;
        this.dni = "dni";
        this.nombre = "nombre";
        this.telefono = "telefono";
        this.email = "email";
        this.direccion = "direccion";
    }

    public int getId() {
        return id;
    }
    
    public int getCod_trabajador() {
        return cod_trabajador;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getDireccion() {
        return direccion;
    }
    
    public Supermercado getSupermercado() {
        return supermercado;
    }
    
    public List<Producto> getProductos() {
        return productos;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public void setCod_trabajador(int cod_trabajador) {
        this.cod_trabajador = cod_trabajador;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public void setSupermercado(Supermercado supermercado) {
        this.supermercado = supermercado;
    }
    
    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
    
    @Override
    public String toString(){
        return this.nombre;
    }
}
