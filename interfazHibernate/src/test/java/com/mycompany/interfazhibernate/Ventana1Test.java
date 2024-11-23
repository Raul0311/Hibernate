/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.interfazhibernate;

import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author raulr
 */
public class Ventana1Test {
    
    public Ventana1Test() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of testAniadirSupermercado method, of class Ventana1.
     */
    @Test
    public void testAniadirSupermercado() {
        System.out.println("aniadirSupermercado");
        Ventana1 instance = new Ventana1();
        Supermercado supermercado = new Supermercado();
        ArrayList<Supermercado> supermercados;
        String contenido_tabla[] = new String[5];
        contenido_tabla[0] = "Coviran";
        contenido_tabla[1] = "Av. Alfaguara";
        contenido_tabla[2] = "coviran@gmail.com";
        contenido_tabla[3] = "632347534";
        contenido_tabla[4] = "18170";
        instance.aniadirContenido(supermercado, contenido_tabla);
        
        instance.conectar();
        instance.insert(supermercado);
        instance.selectAllSupermercados();
        supermercados = instance.getSupermercado();
        
        assertEquals(contenido_tabla[0], supermercados.get(supermercados.size() - 1).getNombre());
    }

    /**
     * Test of testModificarTrabajador method, of class Ventana1.
     */
    @Test
    public void testModificarTrabajador() {
        System.out.println("modificarTrabajador");
        Ventana1 instance = new Ventana1();
        Trabajador trabajador = new Trabajador();
        ArrayList<Trabajador> trabajadores = new ArrayList();
        instance.selectAllTrabajadores();
        trabajadores = instance.getTrabajador();
        
        ArrayList<Supermercado> supermercados = new ArrayList();
        instance.selectAllSupermercados();
        supermercados = instance.getSupermercado();
        
        String contenido_tabla[] = new String[5];
        System.out.println(trabajadores.get(0).getCod_trabajador());
        System.out.println(trabajadores.get(0).getDni());
        System.out.println(trabajadores.get(0).getNombre());
        System.out.println(trabajadores.get(0).getDireccion());
        System.out.println(trabajadores.get(0).getEmail());
        System.out.println(trabajadores.get(0).getTelefono());
        
        trabajadores.get(0).setDni("84976529L");
        trabajadores.get(0).setNombre("Alberto Muñoz Sánchez");
        trabajadores.get(0).setTelefono("629403948");
        trabajadores.get(0).setEmail("alberto@gmail.com");
        trabajadores.get(0).setDireccion("C. Nazcoz");
        trabajadores.get(0).setSupermercado(supermercados.get(0));
        
        trabajador.setId(trabajadores.get(0).getId());
        trabajador.setCod_trabajador(trabajadores.get(0).getCod_trabajador());
        trabajador.setDni("84976529L");
        trabajador.setNombre("Alberto Muñoz Sánchez");
        trabajador.setTelefono("629403948");
        trabajador.setEmail("alberto@gmail.com");
        trabajador.setDireccion("C. Nazcoz");
        trabajador.setSupermercado(supermercados.get(0));
        
        instance.modificar(trabajadores.get(0));
        
        trabajadores = new ArrayList();
        instance.selectAllTrabajadores();
        trabajadores = instance.getTrabajador();
        System.out.println(trabajadores.get(0).getCod_trabajador());
        System.out.println(trabajadores.get(0).getDni());
        System.out.println(trabajadores.get(0).getNombre());
        System.out.println(trabajadores.get(0).getDireccion());
        System.out.println(trabajadores.get(0).getEmail());
        System.out.println(trabajadores.get(0).getTelefono());
        
        assertEquals(trabajadores.get(0).getDni(), trabajador.getDni());
    }
    
    /**
     * Test of testBorrarProducto method, of class Ventana1.
     */
    @Test
    public void testBorrarProducto() {
        System.out.println("borrarProducto");
        Ventana1 instance = new Ventana1();
        int tamanioAntes, tamanioDespues;
        ArrayList<Producto> productos = new ArrayList();
        instance.selectAllProductos();
        productos = instance.getProducto();
        tamanioAntes = productos.size();
        
        for (int i = 0; i < productos.size(); i++) {
            System.out.println(productos.get(i).getCod_producto());
            System.out.println(productos.get(i).getNombre());
            System.out.println(productos.get(i).getCategoria());
            System.out.println(productos.get(i).getPrecio());
            System.out.println(productos.get(i).getMarca());
            System.out.println(productos.get(i).getFecha_caducidad());
            System.out.println(productos.get(i).getTrabajador().getCod_trabajador());
        }
        
        instance.borrar(productos.get(productos.size() - 1));
        
        instance.selectAllProductos();
        productos = instance.getProducto();
        tamanioDespues = productos.size();
        
        for (int i = 0; i < productos.size(); i++) {
            System.out.println(productos.get(i).getCod_producto());
            System.out.println(productos.get(i).getNombre());
            System.out.println(productos.get(i).getCategoria());
            System.out.println(productos.get(i).getPrecio());
            System.out.println(productos.get(i).getMarca());
            System.out.println(productos.get(i).getFecha_caducidad());
            System.out.println(productos.get(i).getTrabajador().getCod_trabajador());
        }
        
        assertNotEquals(tamanioAntes, tamanioDespues);
    }

    /**
     * Test of testLeerSupermercados method, of class Ventana1.
     */
    @Test
    public void testLeerSupermercados() {
        System.out.println("leerSupermercados");
        Ventana1 instance = new Ventana1();
        Supermercado supermercado = new Supermercado();
        ArrayList<Supermercado> supermercados = new ArrayList<>();
        String contenido_tabla[] = new String[5];
        contenido_tabla[0] = "Carrefour";
        contenido_tabla[1] = "C. Alfaguara";
        contenido_tabla[2] = "carrefour@gmail.com";
        contenido_tabla[3] = "593293847";
        contenido_tabla[4] = "18170";
        instance.aniadirContenido(supermercado, contenido_tabla);
        
        instance.conectar();
        instance.insert(supermercado);
        instance.selectAllSupermercados();
        supermercados = instance.getSupermercado();
        
        for (int i = 0; i < supermercados.size(); i++) {
            System.out.println(supermercados.get(i).getCod_super());
            System.out.println(supermercados.get(i).getNombre());
            System.out.println(supermercados.get(i).getDireccion());
            System.out.println(supermercados.get(i).getEmail());
            System.out.println(supermercados.get(i).getTelefono());
            System.out.println(supermercados.get(i).getCod_postal());
        }
        
        assertEquals(contenido_tabla[0], supermercados.get(supermercados.size() - 1).getNombre());
    }
}
