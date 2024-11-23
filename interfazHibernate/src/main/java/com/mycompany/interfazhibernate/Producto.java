/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.interfazhibernate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author raulr
 */
@Entity
@Table(name="Productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="id")
    int id;
    @Column(name="cod_producto")
    int cod_producto;
    @Column(name="nombre")
    String nombre;
    @Column(name="categoria")
    String categoria;
    @Column(name="precio")
    String precio;
    @Column(name="marca")
    String marca;
    @Column(name="fecha_caducidad")
    String fecha_caducidad;
    
    @ManyToOne
    @JoinColumn(name="id_trabajador")
    private Trabajador trabajador;

    public Producto() {
        this.cod_producto = 0;
        this.nombre = "nombre";
        this.categoria = "categoria";
        this.precio = "precio";
        this.marca = "marca";
        this.fecha_caducidad = "fecha_caducidad";
    }

    public int getCod_producto() {
        return cod_producto;
    }
    
    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getPrecio() {
        return precio;
    }

    public String getMarca() {
        return marca;
    }

    public String getFecha_caducidad() {
        return fecha_caducidad;
    }
    
    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setCod_producto(int cod_producto) {
        this.cod_producto = cod_producto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setFecha_caducidad(String fecha_caducidad) {
        this.fecha_caducidad = fecha_caducidad;
    }
    
    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }
}
