/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.interfazhibernate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Raúl Rodríguez Fernández
 */
public final class Ventana1 extends javax.swing.JFrame {

        private ArrayList<Supermercado> supermercados;
        private ArrayList<Trabajador> trabajadores;
        private ArrayList<Producto> productos;
        private final DefaultTableModel tabla_supermercado;
        private final DefaultTableModel tabla_trabajador;
        private final DefaultTableModel tabla_producto;
        private boolean aniadir = false;
        private boolean modificar = false;
        private boolean mouseTable = false;
        
        public ArrayList<Supermercado> getSupermercado() {
            return this.supermercados;
        }
        
        public void setSupermercado(Supermercado supermercado) {
            this.supermercados.add(supermercado);
        }
        
        public ArrayList<Trabajador> getTrabajador() {
            return this.trabajadores;
        }
        
        public void setTrabajador(Trabajador trabajador) {
            this.trabajadores.add(trabajador);
        }
        
        public ArrayList<Producto> getProducto() {
            return this.productos;
        }
        
        public void setProducto(Producto producto) {
            this.productos.add(producto);
        }
        
    /**
     * Creates new form Ventana1
     */
    public Ventana1() {
        initComponents();

        this.iniciarTabla();
        this.tabla_supermercado = (DefaultTableModel)this.jTable_supermercados.getModel();
        this.tabla_trabajador = (DefaultTableModel)this.jTable_trabajadores.getModel();
        this.tabla_producto = (DefaultTableModel)this.jTable_productos.getModel();
        
        this.supermercados = new ArrayList<>();
        this.trabajadores = new ArrayList<>();
        this.productos = new ArrayList<>();
        
        this.conectar();
        
        this.selectAllSupermercados();
        this.selectAllTrabajadores();
        this.selectAllProductos();
    }
    
    public void conectar() {
        try {
            HibernateUtil.buildSessionFactory();
            HibernateUtil.openSession();
        } catch(HibernateException he) {
            he.printStackTrace();
        }
    }
    
    public void desconectar() {
        try {
            HibernateUtil.closeSessionFactory();
        } catch(HibernateException he) {
            he.printStackTrace();
        }
    }
    
    public void insert(Object object) {
        // Da de alta el objeto en la tabla de datos
        Session session = HibernateUtil.getCurrentSession();
        session.beginTransaction();
        session.save(object);
        //session.saveOrUpdate(supermercado);
        session.getTransaction().commit();
        session.close();
    }
    
    public void selectAllSupermercados(){
        // Prepara y ejecuta la consulta
        Query query = HibernateUtil.getCurrentSession().createQuery("FROM Supermercado");
        this.supermercados = (ArrayList<Supermercado>) query.list();
        // Muestra la lista en la JTable
        this.listarSupermercados();
    }
    
    public void listarSupermercados() {
        this.tabla_supermercado.setNumRows(0);
        for (Supermercado supermercado : this.supermercados) {
            Object[] fila = new Object[]{supermercado.getNombre(), supermercado.getDireccion(), supermercado.getEmail(), 
                supermercado.getTelefono(), supermercado.getCod_postal()};
            this.tabla_supermercado.addRow(fila);
            this.jComboBox1_supermercado_trabajador.addItem(supermercado);
        }
    }
    
    public void selectAllTrabajadores() {
        // Prepara y ejecuta la consulta
        Query query = HibernateUtil.getCurrentSession().createQuery("FROM Trabajador");
        this.trabajadores = (ArrayList<Trabajador>) query.list();
        // Muestra la lista en la JTable
        this.listarTrabajadores();
    }
    
    public void listarTrabajadores() {
        this.tabla_trabajador.setNumRows(0);
        for (Trabajador trabajador : this.trabajadores) {
            Object[] fila = null;
            fila = new Object[]{trabajador.getDni(), trabajador.getNombre(), trabajador.getTelefono(), 
                trabajador.getEmail(), trabajador.getDireccion()};
            this.tabla_trabajador.addRow(fila);
            this.jComboBox2_trabajador_producto.addItem(trabajador);
        }
    }
    
    public void selectAllProductos(){
        // Prepara y ejecuta la consulta
        Query query = HibernateUtil.getCurrentSession().createQuery("FROM Producto");
        this.productos = (ArrayList<Producto>) query.list();
        // Muestra la lista en la JTable
        this.listarProductos();
    }
    
    public void listarProductos() {
        this.tabla_producto.setNumRows(0);
        for (Producto producto : this.productos) {
            Object[] fila = null;
            fila = new Object[]{producto.getNombre(), producto.getCategoria(), producto.getPrecio(), 
                producto.getMarca(), producto.getFecha_caducidad()};
            this.tabla_producto.addRow(fila);
        }
    }
    
    public void modificar(Object object) {
        if (object == null)
            return;

        // Almacena los cambios en la tabla
        Session session = HibernateUtil.getCurrentSession();
        session.beginTransaction();
        session.update(object);
        //session.saveOrUpdate(enemigo);
        session.getTransaction().commit();
        session.close();
    }
    
    public void borrar(Object object) {
        if (object == null)
            return;

        // Elimina el personaje de la tabla de datos
        Session session = null;
        try {
                session = HibernateUtil.getCurrentSession();
                session.beginTransaction();
                session.delete(object);
                session.getTransaction().commit();
                session.close();
        } catch (Exception ex) {
                ex.printStackTrace();
                session.close();
        }
    }
    
    public void iniciarTabla() {
        this.jButton_borrar_supermercado.setEnabled(false);
        this.jButton_borrar_trabajador.setEnabled(false);
        this.jButton_borrar_producto.setEnabled(false);
        
        this.jButton_modificar_supermercado.setEnabled(false);
        this.jButton_modificar_trabajador.setEnabled(false);
        this.jButton_modificar_producto.setEnabled(false);
        
        this.jButton_guardar_supermercado.setEnabled(false);
        this.jButton_guardar_trabajador.setEnabled(false);
        this.jButton_guardar_producto.setEnabled(false);
        
        this.jButton_cancelar_supermercado.setEnabled(false);
        this.jButton_cancelar_trabajador.setEnabled(false);
        this.jButton_cancelar_producto.setEnabled(false);
        
        this.jLabel_nombre_supermercado.setEnabled(false);
        this.jLabel_direccion_supermercado.setEnabled(false);
        this.jLabel_email_supermercado.setEnabled(false);
        this.jLabel_telefono_supermercado.setEnabled(false);
        this.jLabel_codigo_postal_supermercado.setEnabled(false);
        
        this.jTextField_nombre_supermercado.setEnabled(false);
        this.jTextField_direccion_supermercado.setEnabled(false);
        this.jTextField_email_supermercado.setEnabled(false);
        this.jTextField_telefono_supermercado.setEnabled(false);
        this.jTextField_codigo_postal_supermercado.setEnabled(false);
        this.jTextField_comprobar_supermercado.setEnabled(false);
        
        this.jLabel_dni_trabajador.setEnabled(false);
        this.jLabel_nombre_trabajador.setEnabled(false);
        this.jLabel_telefono_trabajador.setEnabled(false);
        this.jLabel_email_trabajador.setEnabled(false);
        this.jLabel_direccion_trabajador.setEnabled(false);
        this.jLabel_supermercado_trabajador.setEnabled(false);
        
        this.jTextField_dni_trabajador.setEnabled(false);
        this.jTextField_nombre_trabajador.setEnabled(false);
        this.jTextField_telefono_trabajador.setEnabled(false);
        this.jTextField_email_trabajador.setEnabled(false);
        this.jTextField_direccion_trabajador.setEnabled(false);
        this.jComboBox1_supermercado_trabajador.setEnabled(false);
        this.jTextField_comprobar_trabajador.setEnabled(false);
        
        this.jLabel_nombre_producto.setEnabled(false);
        this.jLabel_categoria_producto.setEnabled(false);
        this.jLabel_precio_producto.setEnabled(false);
        this.jLabel_marca_producto.setEnabled(false);
        this.jLabel_fecha_caducidad_producto.setEnabled(false);
        this.jLabel_trabajador_producto.setEnabled(false);
        
        this.jTextField_nombre_producto.setEnabled(false);
        this.jTextField_categoria_producto.setEnabled(false);
        this.jTextField_precio_producto.setEnabled(false);
        this.jTextField_marca_producto.setEnabled(false);
        this.jTextField_fecha_caducidad_producto.setEnabled(false);
        this.jComboBox2_trabajador_producto.setEnabled(false);
        this.jTextField_comprobar_producto.setEnabled(false);
    }
      
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane = new javax.swing.JTabbedPane();
        jPanel_supermercados = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_supermercados = new javax.swing.JTable();
        jButton_aniadir_supermercado = new javax.swing.JButton();
        jButton_modificar_supermercado = new javax.swing.JButton();
        jButton_borrar_supermercado = new javax.swing.JButton();
        jLabel_nombre_supermercado = new javax.swing.JLabel();
        jLabel_direccion_supermercado = new javax.swing.JLabel();
        jLabel_email_supermercado = new javax.swing.JLabel();
        jTextField_nombre_supermercado = new javax.swing.JTextField();
        jTextField_direccion_supermercado = new javax.swing.JTextField();
        jTextField_email_supermercado = new javax.swing.JTextField();
        jButton_guardar_supermercado = new javax.swing.JButton();
        jButton_cancelar_supermercado = new javax.swing.JButton();
        jTextField_telefono_supermercado = new javax.swing.JTextField();
        jLabel_telefono_supermercado = new javax.swing.JLabel();
        jTextField_codigo_postal_supermercado = new javax.swing.JTextField();
        jLabel_codigo_postal_supermercado = new javax.swing.JLabel();
        jTextField_comprobar_supermercado = new javax.swing.JTextField();
        jPanel_trabajadores = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_trabajadores = new javax.swing.JTable();
        jLabel_dni_trabajador = new javax.swing.JLabel();
        jLabel_nombre_trabajador = new javax.swing.JLabel();
        jLabel_telefono_trabajador = new javax.swing.JLabel();
        jTextField_dni_trabajador = new javax.swing.JTextField();
        jTextField_nombre_trabajador = new javax.swing.JTextField();
        jTextField_telefono_trabajador = new javax.swing.JTextField();
        jButton_guardar_trabajador = new javax.swing.JButton();
        jButton_cancelar_trabajador = new javax.swing.JButton();
        jButton_aniadir_trabajador = new javax.swing.JButton();
        jButton_modificar_trabajador = new javax.swing.JButton();
        jButton_borrar_trabajador = new javax.swing.JButton();
        jLabel_email_trabajador = new javax.swing.JLabel();
        jTextField_email_trabajador = new javax.swing.JTextField();
        jTextField_direccion_trabajador = new javax.swing.JTextField();
        jLabel_direccion_trabajador = new javax.swing.JLabel();
        jLabel_supermercado_trabajador = new javax.swing.JLabel();
        jComboBox1_supermercado_trabajador = new javax.swing.JComboBox<>();
        jTextField_comprobar_trabajador = new javax.swing.JTextField();
        jPanel_productos = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable_productos = new javax.swing.JTable();
        jLabel_nombre_producto = new javax.swing.JLabel();
        jLabel_categoria_producto = new javax.swing.JLabel();
        jLabel_precio_producto = new javax.swing.JLabel();
        jLabel_fecha_caducidad_producto = new javax.swing.JLabel();
        jTextField_precio_producto = new javax.swing.JTextField();
        jTextField_nombre_producto = new javax.swing.JTextField();
        jTextField_categoria_producto = new javax.swing.JTextField();
        jButton_guardar_producto = new javax.swing.JButton();
        jButton_cancelar_producto = new javax.swing.JButton();
        jButton_borrar_producto = new javax.swing.JButton();
        jButton_modificar_producto = new javax.swing.JButton();
        jButton_aniadir_producto = new javax.swing.JButton();
        jLabel_marca_producto = new javax.swing.JLabel();
        jTextField_marca_producto = new javax.swing.JTextField();
        jTextField_fecha_caducidad_producto = new javax.swing.JTextField();
        jLabel_trabajador_producto = new javax.swing.JLabel();
        jComboBox2_trabajador_producto = new javax.swing.JComboBox<>();
        jTextField_comprobar_producto = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jTable_supermercados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Dirección", "Email", "Teléfono", "Código_postal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_supermercados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_supermercadosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_supermercados);
        if (jTable_supermercados.getColumnModel().getColumnCount() > 0) {
            jTable_supermercados.getColumnModel().getColumn(0).setResizable(false);
            jTable_supermercados.getColumnModel().getColumn(1).setResizable(false);
            jTable_supermercados.getColumnModel().getColumn(2).setResizable(false);
            jTable_supermercados.getColumnModel().getColumn(3).setResizable(false);
            jTable_supermercados.getColumnModel().getColumn(4).setResizable(false);
        }

        jButton_aniadir_supermercado.setText("Añadir");
        jButton_aniadir_supermercado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_aniadir_supermercadoActionPerformed(evt);
            }
        });

        jButton_modificar_supermercado.setText("Modificar");
        jButton_modificar_supermercado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_modificar_supermercadoActionPerformed(evt);
            }
        });

        jButton_borrar_supermercado.setText("Borrar");
        jButton_borrar_supermercado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_borrar_supermercadoActionPerformed(evt);
            }
        });

        jLabel_nombre_supermercado.setText("Nombre:");

        jLabel_direccion_supermercado.setText("Dirección:");

        jLabel_email_supermercado.setText("Email:");

        jTextField_nombre_supermercado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_nombre_supermercadoActionPerformed(evt);
            }
        });

        jButton_guardar_supermercado.setText("Guardar");
        jButton_guardar_supermercado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_guardar_supermercadoActionPerformed(evt);
            }
        });

        jButton_cancelar_supermercado.setText("Cancelar");
        jButton_cancelar_supermercado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_cancelar_supermercadoActionPerformed(evt);
            }
        });

        jTextField_telefono_supermercado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_telefono_supermercadoActionPerformed(evt);
            }
        });

        jLabel_telefono_supermercado.setText("Teléfono:");

        jTextField_codigo_postal_supermercado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_codigo_postal_supermercadoActionPerformed(evt);
            }
        });

        jLabel_codigo_postal_supermercado.setText("Código postal");

        jTextField_comprobar_supermercado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_comprobar_supermercadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_supermercadosLayout = new javax.swing.GroupLayout(jPanel_supermercados);
        jPanel_supermercados.setLayout(jPanel_supermercadosLayout);
        jPanel_supermercadosLayout.setHorizontalGroup(
            jPanel_supermercadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_supermercadosLayout.createSequentialGroup()
                .addGroup(jPanel_supermercadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_supermercadosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel_supermercadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton_modificar_supermercado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_aniadir_supermercado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_borrar_supermercado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel_supermercadosLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel_supermercadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel_nombre_supermercado)
                            .addComponent(jLabel_direccion_supermercado)
                            .addComponent(jLabel_email_supermercado)
                            .addComponent(jLabel_telefono_supermercado)
                            .addComponent(jLabel_codigo_postal_supermercado))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel_supermercadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField_nombre_supermercado, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                            .addComponent(jTextField_direccion_supermercado)
                            .addComponent(jTextField_email_supermercado)
                            .addComponent(jTextField_telefono_supermercado)
                            .addComponent(jTextField_codigo_postal_supermercado)
                            .addComponent(jTextField_comprobar_supermercado, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel_supermercadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton_guardar_supermercado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_cancelar_supermercado, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_supermercadosLayout.setVerticalGroup(
            jPanel_supermercadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_supermercadosLayout.createSequentialGroup()
                .addGroup(jPanel_supermercadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_supermercadosLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jButton_aniadir_supermercado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_modificar_supermercado, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_borrar_supermercado))
                    .addGroup(jPanel_supermercadosLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(42, 42, 42)
                .addGroup(jPanel_supermercadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_nombre_supermercado)
                    .addComponent(jTextField_nombre_supermercado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_supermercadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_direccion_supermercado)
                    .addComponent(jTextField_direccion_supermercado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_supermercadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_email_supermercado)
                    .addComponent(jTextField_email_supermercado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_supermercadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_telefono_supermercado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_telefono_supermercado)
                    .addComponent(jButton_guardar_supermercado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_supermercadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_codigo_postal_supermercado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_codigo_postal_supermercado)
                    .addComponent(jButton_cancelar_supermercado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField_comprobar_supermercado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("Supermercado", jPanel_supermercados);

        jTable_trabajadores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "DNI", "Nombre", "Teléfono", "Email", "Dirección"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_trabajadores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_trabajadoresMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable_trabajadores);
        if (jTable_trabajadores.getColumnModel().getColumnCount() > 0) {
            jTable_trabajadores.getColumnModel().getColumn(0).setResizable(false);
            jTable_trabajadores.getColumnModel().getColumn(1).setResizable(false);
            jTable_trabajadores.getColumnModel().getColumn(2).setResizable(false);
            jTable_trabajadores.getColumnModel().getColumn(3).setResizable(false);
            jTable_trabajadores.getColumnModel().getColumn(4).setResizable(false);
        }

        jLabel_dni_trabajador.setText("DNI:");

        jLabel_nombre_trabajador.setText("Nombre:");

        jLabel_telefono_trabajador.setText("Teléfono:");

        jTextField_dni_trabajador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_dni_trabajadorActionPerformed(evt);
            }
        });

        jButton_guardar_trabajador.setText("Guardar");
        jButton_guardar_trabajador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_guardar_trabajadorActionPerformed(evt);
            }
        });

        jButton_cancelar_trabajador.setText("Cancelar");
        jButton_cancelar_trabajador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_cancelar_trabajadorActionPerformed(evt);
            }
        });

        jButton_aniadir_trabajador.setText("Añadir");
        jButton_aniadir_trabajador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_aniadir_trabajadorActionPerformed(evt);
            }
        });

        jButton_modificar_trabajador.setText("Modificar");
        jButton_modificar_trabajador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_modificar_trabajadorActionPerformed(evt);
            }
        });

        jButton_borrar_trabajador.setText("Borrar");
        jButton_borrar_trabajador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_borrar_trabajadorActionPerformed(evt);
            }
        });

        jLabel_email_trabajador.setText("Email:");

        jLabel_direccion_trabajador.setText("Dirección:");

        jLabel_supermercado_trabajador.setText("Supermercado:");

        jComboBox1_supermercado_trabajador.setModel(new javax.swing.DefaultComboBoxModel<Supermercado>());
        jComboBox1_supermercado_trabajador.setEnabled(false);

        jComboBox1_supermercado_trabajador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1_supermercado_trabajadorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_trabajadoresLayout = new javax.swing.GroupLayout(jPanel_trabajadores);
        jPanel_trabajadores.setLayout(jPanel_trabajadoresLayout);
        jPanel_trabajadoresLayout.setHorizontalGroup(
            jPanel_trabajadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_trabajadoresLayout.createSequentialGroup()
                .addGroup(jPanel_trabajadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_trabajadoresLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel_trabajadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton_modificar_trabajador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_aniadir_trabajador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_borrar_trabajador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel_trabajadoresLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel_trabajadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_trabajadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel_dni_trabajador)
                                .addComponent(jLabel_telefono_trabajador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel_email_trabajador)
                                .addComponent(jLabel_nombre_trabajador, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel_direccion_trabajador)
                            .addComponent(jLabel_supermercado_trabajador))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel_trabajadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField_dni_trabajador, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                            .addComponent(jTextField_nombre_trabajador)
                            .addComponent(jTextField_telefono_trabajador)
                            .addComponent(jTextField_email_trabajador)
                            .addComponent(jTextField_direccion_trabajador)
                            .addComponent(jComboBox1_supermercado_trabajador, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField_comprobar_trabajador))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel_trabajadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton_guardar_trabajador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_cancelar_trabajador, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_trabajadoresLayout.setVerticalGroup(
            jPanel_trabajadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_trabajadoresLayout.createSequentialGroup()
                .addGroup(jPanel_trabajadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_trabajadoresLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jButton_aniadir_trabajador)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_modificar_trabajador, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_borrar_trabajador))
                    .addGroup(jPanel_trabajadoresLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(42, 42, 42)
                .addGroup(jPanel_trabajadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_dni_trabajador)
                    .addComponent(jTextField_dni_trabajador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_trabajadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_nombre_trabajador)
                    .addComponent(jTextField_nombre_trabajador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_trabajadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_telefono_trabajador)
                    .addComponent(jTextField_telefono_trabajador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_trabajadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_email_trabajador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_email_trabajador))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_trabajadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_direccion_trabajador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_direccion_trabajador)
                    .addComponent(jButton_guardar_trabajador))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_trabajadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1_supermercado_trabajador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_supermercado_trabajador)
                    .addComponent(jButton_cancelar_trabajador))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField_comprobar_trabajador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("Trabajadores", jPanel_trabajadores);

        jTable_productos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Categoría", "Precio", "Marca", "Fecha de caducidad"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_productos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_productosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable_productos);
        if (jTable_productos.getColumnModel().getColumnCount() > 0) {
            jTable_productos.getColumnModel().getColumn(0).setResizable(false);
            jTable_productos.getColumnModel().getColumn(1).setResizable(false);
            jTable_productos.getColumnModel().getColumn(2).setResizable(false);
            jTable_productos.getColumnModel().getColumn(3).setResizable(false);
            jTable_productos.getColumnModel().getColumn(4).setResizable(false);
        }

        jLabel_nombre_producto.setText("Nombre:");

        jLabel_categoria_producto.setText("Categoría:");

        jLabel_precio_producto.setText("Precio:");

        jLabel_fecha_caducidad_producto.setText("Fecha de caducidad:");

        jTextField_nombre_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_nombre_productoActionPerformed(evt);
            }
        });

        jButton_guardar_producto.setText("Guardar");
        jButton_guardar_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_guardar_productoActionPerformed(evt);
            }
        });

        jButton_cancelar_producto.setText("Cancelar");
        jButton_cancelar_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_cancelar_productoActionPerformed(evt);
            }
        });

        jButton_borrar_producto.setText("Borrar");
        jButton_borrar_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_borrar_productoActionPerformed(evt);
            }
        });

        jButton_modificar_producto.setText("Modificar");
        jButton_modificar_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_modificar_productoActionPerformed(evt);
            }
        });

        jButton_aniadir_producto.setText("Añadir");
        jButton_aniadir_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_aniadir_productoActionPerformed(evt);
            }
        });

        jLabel_marca_producto.setText("Marca:");

        jLabel_trabajador_producto.setText("Trabajador:");

        jComboBox2_trabajador_producto.setModel(new javax.swing.DefaultComboBoxModel<Trabajador>());
        jComboBox2_trabajador_producto.setEnabled(false);

        jComboBox2_trabajador_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2_trabajador_productoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_productosLayout = new javax.swing.GroupLayout(jPanel_productos);
        jPanel_productos.setLayout(jPanel_productosLayout);
        jPanel_productosLayout.setHorizontalGroup(
            jPanel_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_productosLayout.createSequentialGroup()
                .addGroup(jPanel_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_productosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton_modificar_producto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_aniadir_producto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_borrar_producto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel_productosLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel_nombre_producto)
                            .addComponent(jLabel_categoria_producto)
                            .addComponent(jLabel_precio_producto)
                            .addComponent(jLabel_fecha_caducidad_producto)
                            .addComponent(jLabel_marca_producto)
                            .addComponent(jLabel_trabajador_producto))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextField_marca_producto, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_nombre_producto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                            .addComponent(jTextField_categoria_producto, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_precio_producto, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_fecha_caducidad_producto, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox2_trabajador_producto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField_comprobar_producto, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton_guardar_producto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_cancelar_producto, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_productosLayout.setVerticalGroup(
            jPanel_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_productosLayout.createSequentialGroup()
                .addGroup(jPanel_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_productosLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jButton_aniadir_producto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_modificar_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_borrar_producto))
                    .addGroup(jPanel_productosLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(42, 42, 42)
                .addGroup(jPanel_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_nombre_producto)
                    .addComponent(jTextField_nombre_producto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_categoria_producto)
                    .addComponent(jTextField_categoria_producto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_precio_producto)
                    .addComponent(jTextField_precio_producto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_marca_producto)
                    .addComponent(jTextField_marca_producto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_fecha_caducidad_producto)
                    .addComponent(jTextField_fecha_caducidad_producto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_guardar_producto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_trabajador_producto)
                    .addComponent(jComboBox2_trabajador_producto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_cancelar_producto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField_comprobar_producto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("Productos", jPanel_productos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane)
                .addContainerGap())
        );

        jTabbedPane.getAccessibleContext().setAccessibleName("Ciudad");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1_supermercado_trabajadorActionPerformed(java.awt.event.ActionEvent evt) {
        
    }
    
    private void jComboBox2_trabajador_productoActionPerformed(java.awt.event.ActionEvent evt) {
        
    }
    
    private void jButton_aniadir_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_aniadir_productoActionPerformed
        this.jTextField_nombre_producto.setText("");
        this.jTextField_categoria_producto.setText("");
        this.jTextField_precio_producto.setText("");
        this.jTextField_marca_producto.setText("");
        this.jTextField_fecha_caducidad_producto.setText("");
        this.jTextField_comprobar_producto.setText("");
        
        this.jLabel_nombre_producto.setEnabled(true);
        this.jLabel_categoria_producto.setEnabled(true);
        this.jLabel_precio_producto.setEnabled(true);
        this.jLabel_marca_producto.setEnabled(true);
        this.jLabel_fecha_caducidad_producto.setEnabled(true);
        this.jLabel_trabajador_producto.setEnabled(true);
        
        this.jTextField_nombre_producto.setEnabled(true);
        this.jTextField_categoria_producto.setEnabled(true);
        this.jTextField_precio_producto.setEnabled(true);
        this.jTextField_marca_producto.setEnabled(true);
        this.jTextField_fecha_caducidad_producto.setEnabled(true);
        this.jComboBox2_trabajador_producto.setEnabled(true);
        
        this.jButton_guardar_producto.setEnabled(true);
        this.jButton_cancelar_producto.setEnabled(true);
        
        this.jButton_modificar_producto.setEnabled(false);
        this.jButton_borrar_producto.setEnabled(false);
        
        this.aniadir = true;
    }//GEN-LAST:event_jButton_aniadir_productoActionPerformed

    private void jButton_modificar_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_modificar_productoActionPerformed
        this.jLabel_nombre_producto.setEnabled(true);
        this.jLabel_categoria_producto.setEnabled(true);
        this.jLabel_precio_producto.setEnabled(true);
        this.jLabel_marca_producto.setEnabled(true);
        this.jLabel_fecha_caducidad_producto.setEnabled(true);
        this.jLabel_trabajador_producto.setEnabled(true);
        
        this.jTextField_nombre_producto.setEnabled(true);
        this.jTextField_categoria_producto.setEnabled(true);
        this.jTextField_precio_producto.setEnabled(true);
        this.jTextField_marca_producto.setEnabled(true);
        this.jTextField_fecha_caducidad_producto.setEnabled(true);
        this.jComboBox2_trabajador_producto.setEnabled(true);
        
        this.jButton_guardar_producto.setEnabled(true);
        this.jButton_cancelar_producto.setEnabled(true);
        
        this.modificar = true;
    }//GEN-LAST:event_jButton_modificar_productoActionPerformed
    
    private void jButton_borrar_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_borrar_productoActionPerformed
        int j = this.jTable_productos.getSelectedRow();
            
        this.borrar(this.productos.get(j));
        this.productos.remove(this.productos.get(j));
        this.tabla_producto.removeRow(this.jTable_productos.getSelectedRow());
        
        this.jTextField_nombre_producto.setText("");
        this.jTextField_categoria_producto.setText("");
        this.jTextField_precio_producto.setText("");
        this.jTextField_marca_producto.setText("");
        this.jTextField_fecha_caducidad_producto.setText("");
        this.jTextField_comprobar_producto.setText("");
        
        this.jTextField_nombre_producto.setEnabled(false);
        this.jTextField_categoria_producto.setEnabled(false);
        this.jTextField_precio_producto.setEnabled(false);
        this.jTextField_marca_producto.setEnabled(false);
        this.jTextField_fecha_caducidad_producto.setEnabled(false);
        this.jComboBox2_trabajador_producto.setEnabled(false);
        
        this.jButton_cancelar_producto.setEnabled(false);
        
        this.jButton_modificar_producto.setEnabled(false);
        this.jButton_borrar_producto.setEnabled(false);
        
        if(this.mouseTable) {
            this.jButton_aniadir_producto.setEnabled(true);
            this.mouseTable = false;
        }
    }//GEN-LAST:event_jButton_borrar_productoActionPerformed

    private void jButton_cancelar_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_cancelar_productoActionPerformed
        this.jLabel_nombre_producto.setEnabled(false);
        this.jLabel_categoria_producto.setEnabled(false);
        this.jLabel_precio_producto.setEnabled(false);
        this.jLabel_marca_producto.setEnabled(false);
        this.jLabel_fecha_caducidad_producto.setEnabled(false);
        this.jLabel_trabajador_producto.setEnabled(false);
        
        this.jTextField_nombre_producto.setEnabled(false);
        this.jTextField_categoria_producto.setEnabled(false);
        this.jTextField_precio_producto.setEnabled(false);
        this.jTextField_marca_producto.setEnabled(false);
        this.jTextField_fecha_caducidad_producto.setEnabled(false);
        this.jComboBox2_trabajador_producto.setEnabled(false);
        
        this.jTextField_nombre_producto.setText("");
        this.jTextField_categoria_producto.setText("");
        this.jTextField_precio_producto.setText("");
        this.jTextField_marca_producto.setText("");
        this.jTextField_fecha_caducidad_producto.setText("");
        this.jTextField_comprobar_producto.setText("");
        
        this.jButton_guardar_producto.setEnabled(false);
        this.jButton_cancelar_producto.setEnabled(false);
        
        this.jButton_modificar_producto.setEnabled(false);
        this.jButton_borrar_producto.setEnabled(false);
        
        this.aniadir = false;
        this.modificar = false;
        
        if(this.mouseTable) {
            this.jButton_aniadir_producto.setEnabled(true);
            this.mouseTable = false;
        }
    }//GEN-LAST:event_jButton_cancelar_productoActionPerformed

    private void jButton_guardar_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_guardar_productoActionPerformed
        boolean buscar_nombre = false;
        for (int i = 0; i < this.productos.size() && !buscar_nombre; i++) {
            if(this.productos.get(i).getNombre().equals(this.jTextField_nombre_producto.getText())) {
                buscar_nombre = true;
            }
        }
        
        Trabajador trabajador;
        if(this.aniadir) {
            this.modificar = false;
            if(!buscar_nombre) {
                Producto producto = new Producto();
                String contenido_tabla[] = new String[5];
                int aux = this.productos.size();
                if(aux > 0) {
                    aux--;
                    producto.setCod_producto(this.productos.get(aux).getCod_producto() + 1);
                } else {
                    producto.setCod_producto(1);
                }

                trabajador = (Trabajador)this.jComboBox2_trabajador_producto.getSelectedItem();
                producto.setTrabajador(trabajador);

                if(this.jTextField_nombre_producto.getText().equals("") || this.jTextField_categoria_producto.getText().equals("") 
                   || this.jTextField_precio_producto.getText().equals("") || this.jTextField_marca_producto.getText().equals("") 
                   || this.jTextField_fecha_caducidad_producto.getText().equals("")) {
                    this.jTextField_comprobar_producto.setText("Complete todos los campos");
                } else {
                    contenido_tabla[0] = this.jTextField_nombre_producto.getText();
                    contenido_tabla[1] = this.jTextField_categoria_producto.getText();
                    contenido_tabla[2] = this.jTextField_precio_producto.getText();
                    contenido_tabla[3] = this.jTextField_marca_producto.getText();
                    contenido_tabla[4] = this.jTextField_fecha_caducidad_producto.getText();

                    producto.setNombre(contenido_tabla[0]);
                    producto.setCategoria(contenido_tabla[1]);
                    producto.setPrecio(contenido_tabla[2]);
                    producto.setMarca(contenido_tabla[3]);
                    producto.setFecha_caducidad(contenido_tabla[4]);

                    tabla_producto.addRow(contenido_tabla);
                    this.productos.add(producto);

                    this.aniadir = false;
                    
                    this.insert(producto);
                }
            } else {
                this.jTextField_comprobar_producto.setText("Ya existe un producto con ese nombre");
            }
        } else if(this.modificar) {
            this.aniadir = false;
            int i = this.jTable_productos.getSelectedRow();
            String contenido_tabla[] = new String[5];

            trabajador = (Trabajador)this.jComboBox2_trabajador_producto.getSelectedItem();

            if(this.jTextField_nombre_producto.getText().equals("") || this.jTextField_categoria_producto.getText().equals("") 
               || this.jTextField_precio_producto.getText().equals("") || this.jTextField_marca_producto.getText().equals("") 
               || this.jTextField_fecha_caducidad_producto.getText().equals("")) {
                this.jTextField_comprobar_producto.setText("Complete todos los campos");
            } else {
                contenido_tabla[0] = this.jTextField_nombre_producto.getText();
                contenido_tabla[1] = this.jTextField_categoria_producto.getText();
                contenido_tabla[2] = this.jTextField_precio_producto.getText();
                contenido_tabla[3] = this.jTextField_marca_producto.getText();
                contenido_tabla[4] = this.jTextField_fecha_caducidad_producto.getText();

                this.productos.get(i).setNombre(contenido_tabla[0]);
                this.productos.get(i).setCategoria(contenido_tabla[1]);
                this.productos.get(i).setPrecio(contenido_tabla[2]);
                this.productos.get(i).setMarca(contenido_tabla[3]);
                this.productos.get(i).setFecha_caducidad(contenido_tabla[4]);
                this.productos.get(i).setTrabajador(trabajador);

                this.tabla_producto.setValueAt(contenido_tabla[0], jTable_productos.getSelectedRow(),0);
                this.tabla_producto.setValueAt(contenido_tabla[1], jTable_productos.getSelectedRow(),1);
                this.tabla_producto.setValueAt(contenido_tabla[2], jTable_productos.getSelectedRow(),2);
                this.tabla_producto.setValueAt(contenido_tabla[3], jTable_productos.getSelectedRow(),3);
                this.tabla_producto.setValueAt(contenido_tabla[4], jTable_productos.getSelectedRow(),4);

                this.modificar = false;

                if(this.mouseTable) {
                    this.jButton_aniadir_producto.setEnabled(true);
                    this.mouseTable = false;
                }
                
                this.modificar(this.productos.get(i));
            }
        }

        if(!this.jTextField_nombre_producto.getText().equals("") && !this.jTextField_categoria_producto.getText().equals("") 
               && !this.jTextField_precio_producto.getText().equals("") && !this.jTextField_marca_producto.getText().equals("") 
               && !this.jTextField_fecha_caducidad_producto.getText().equals("")) {
            this.jLabel_nombre_producto.setEnabled(false);
            this.jLabel_categoria_producto.setEnabled(false);
            this.jLabel_precio_producto.setEnabled(false);
            this.jLabel_marca_producto.setEnabled(false);
            this.jLabel_fecha_caducidad_producto.setEnabled(false);
            this.jLabel_trabajador_producto.setEnabled(false);

            this.jTextField_nombre_producto.setEnabled(false);
            this.jTextField_categoria_producto.setEnabled(false);
            this.jTextField_precio_producto.setEnabled(false);
            this.jTextField_marca_producto.setEnabled(false);
            this.jTextField_fecha_caducidad_producto.setEnabled(false);
            this.jComboBox2_trabajador_producto.setEnabled(false);

            this.jTextField_nombre_producto.setText("");
            this.jTextField_categoria_producto.setText("");
            this.jTextField_precio_producto.setText("");
            this.jTextField_marca_producto.setText("");
            this.jTextField_fecha_caducidad_producto.setText("");
            this.jTextField_comprobar_producto.setText("");

            this.jButton_guardar_producto.setEnabled(false);
            this.jButton_cancelar_producto.setEnabled(false);
            
            this.jButton_modificar_producto.setEnabled(false);
            this.jButton_borrar_producto.setEnabled(false);
        }
    }//GEN-LAST:event_jButton_guardar_productoActionPerformed

    private void jTextField_nombre_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_nombre_productoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_nombre_productoActionPerformed

    private void jTable_productosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_productosMouseClicked
        int i = this.jTable_productos.getSelectedRow(),j;
        boolean buscar_trabajador = false;
        this.jButton_modificar_producto.setEnabled(true);
        this.jButton_borrar_producto.setEnabled(true);
        
        this.jTextField_nombre_producto.setText(jTable_productos.getValueAt(jTable_productos.getSelectedRow(), 0).toString());
        this.jTextField_categoria_producto.setText(jTable_productos.getValueAt(jTable_productos.getSelectedRow(), 1).toString());
        this.jTextField_precio_producto.setText(jTable_productos.getValueAt(jTable_productos.getSelectedRow(), 2).toString());
        this.jTextField_marca_producto.setText(jTable_productos.getValueAt(jTable_productos.getSelectedRow(), 3).toString());
        this.jTextField_fecha_caducidad_producto.setText(jTable_productos.getValueAt(jTable_productos.getSelectedRow(), 4).toString());
        
        for (j = 0; j < this.trabajadores.size() && !buscar_trabajador; j++) {
            if(this.productos.get(i).getTrabajador().getCod_trabajador() == this.trabajadores.get(j).getCod_trabajador()) {
                buscar_trabajador = true;
            }
        }
        j--;
        this.jComboBox2_trabajador_producto.setSelectedItem(this.trabajadores.get(j));
        
        this.mouseTable = true;
    }//GEN-LAST:event_jTable_productosMouseClicked

    private void jButton_borrar_trabajadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_borrar_trabajadorActionPerformed
        boolean busqueda_ID = false;
        int j = this.jTable_trabajadores.getSelectedRow();
        
        for (int i = 0; i < this.productos.size() && !busqueda_ID; i++) {
            if(this.trabajadores.get(j).getCod_trabajador() == productos.get(i).getTrabajador().getCod_trabajador()) {
                busqueda_ID = true;
            }
        }
        
        if(!busqueda_ID) {
            this.borrar(this.trabajadores.get(j));
            this.jComboBox2_trabajador_producto.removeItem(this.trabajadores.get(j));
            this.trabajadores.remove(this.trabajadores.get(j));
            tabla_trabajador.removeRow(jTable_trabajadores.getSelectedRow());
        } else if(busqueda_ID) {
            this.jTextField_comprobar_trabajador.setText("Borre primero los productos asociados");
        }
        
        this.jTextField_dni_trabajador.setText("");
        this.jTextField_nombre_trabajador.setText("");
        this.jTextField_telefono_trabajador.setText("");
        this.jTextField_email_trabajador.setText("");
        this.jTextField_direccion_trabajador.setText("");
        
        this.jTextField_dni_trabajador.setEnabled(false);
        this.jTextField_nombre_trabajador.setEnabled(false);
        this.jTextField_telefono_trabajador.setEnabled(false);
        this.jTextField_email_trabajador.setEnabled(false);
        this.jTextField_direccion_trabajador.setEnabled(false);
        this.jComboBox1_supermercado_trabajador.setEnabled(false);
        
        this.jButton_guardar_trabajador.setEnabled(false);
        this.jButton_cancelar_trabajador.setEnabled(false);
        
        this.jButton_modificar_trabajador.setEnabled(false);
        this.jButton_borrar_trabajador.setEnabled(false);
        
        if(this.mouseTable) {
            this.jButton_aniadir_trabajador.setEnabled(true);
            this.mouseTable = false;
        }
    }//GEN-LAST:event_jButton_borrar_trabajadorActionPerformed

    private void jButton_modificar_trabajadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_modificar_trabajadorActionPerformed
        this.jLabel_dni_trabajador.setEnabled(true);
        this.jLabel_nombre_trabajador.setEnabled(true);
        this.jLabel_telefono_trabajador.setEnabled(true);
        this.jLabel_email_trabajador.setEnabled(true);
        this.jLabel_direccion_trabajador.setEnabled(true);
        this.jLabel_supermercado_trabajador.setEnabled(true);
        
        this.jTextField_dni_trabajador.setEnabled(true);
        this.jTextField_nombre_trabajador.setEnabled(true);
        this.jTextField_telefono_trabajador.setEnabled(true);
        this.jTextField_email_trabajador.setEnabled(true);
        this.jTextField_direccion_trabajador.setEnabled(true);
        this.jComboBox1_supermercado_trabajador.setEnabled(true);
        
        this.jButton_guardar_trabajador.setEnabled(true);
        this.jButton_cancelar_trabajador.setEnabled(true);
        
        this.modificar = true;
    }//GEN-LAST:event_jButton_modificar_trabajadorActionPerformed

    private void jButton_aniadir_trabajadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_aniadir_trabajadorActionPerformed
        this.jTextField_dni_trabajador.setText("");
        this.jTextField_nombre_trabajador.setText("");
        this.jTextField_telefono_trabajador.setText("");
        this.jTextField_email_trabajador.setText("");
        this.jTextField_direccion_trabajador.setText("");
        this.jTextField_comprobar_trabajador.setText("");
        
        this.jLabel_dni_trabajador.setEnabled(true);
        this.jLabel_nombre_trabajador.setEnabled(true);
        this.jLabel_telefono_trabajador.setEnabled(true);
        this.jLabel_email_trabajador.setEnabled(true);
        this.jLabel_direccion_trabajador.setEnabled(true);
        this.jLabel_supermercado_trabajador.setEnabled(true);
        
        this.jTextField_dni_trabajador.setEnabled(true);
        this.jTextField_nombre_trabajador.setEnabled(true);
        this.jTextField_telefono_trabajador.setEnabled(true);
        this.jTextField_email_trabajador.setEnabled(true);
        this.jTextField_direccion_trabajador.setEnabled(true);
        this.jComboBox1_supermercado_trabajador.setEnabled(true);
        
        this.jButton_guardar_trabajador.setEnabled(true);
        this.jButton_cancelar_trabajador.setEnabled(true);
        
        this.jButton_modificar_trabajador.setEnabled(false);
        this.jButton_borrar_trabajador.setEnabled(false);
        
        this.jTextField_comprobar_trabajador.setText("");
        
        this.aniadir = true;
    }//GEN-LAST:event_jButton_aniadir_trabajadorActionPerformed

    private void jButton_cancelar_trabajadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_cancelar_trabajadorActionPerformed
        this.jLabel_dni_trabajador.setEnabled(false);
        this.jLabel_nombre_trabajador.setEnabled(false);
        this.jLabel_telefono_trabajador.setEnabled(false);
        this.jLabel_email_trabajador.setEnabled(false);
        this.jLabel_direccion_trabajador.setEnabled(false);
        this.jLabel_supermercado_trabajador.setEnabled(false);
        
        this.jTextField_dni_trabajador.setEnabled(false);
        this.jTextField_nombre_trabajador.setEnabled(false);
        this.jTextField_telefono_trabajador.setEnabled(false);
        this.jTextField_email_trabajador.setEnabled(false);
        this.jTextField_direccion_trabajador.setEnabled(false);
        this.jComboBox1_supermercado_trabajador.setEnabled(false);
        
        this.jTextField_dni_trabajador.setText("");
        this.jTextField_nombre_trabajador.setText("");
        this.jTextField_telefono_trabajador.setText("");
        this.jTextField_email_trabajador.setText("");
        this.jTextField_direccion_trabajador.setText("");
        this.jTextField_comprobar_trabajador.setText("");
        
        this.jButton_guardar_trabajador.setEnabled(false);
        this.jButton_cancelar_trabajador.setEnabled(false);
        
        this.jButton_modificar_trabajador.setEnabled(false);
        this.jButton_borrar_trabajador.setEnabled(false);
        
        this.aniadir = false;
        this.modificar = false;
        
        if(this.mouseTable) {
            this.jButton_aniadir_trabajador.setEnabled(true);
            this.mouseTable = false;
        }
    }//GEN-LAST:event_jButton_cancelar_trabajadorActionPerformed

    public void modificarTrabajadorDesdeGuardar(int i, String contenido_tabla[], Supermercado supermercado) {
        this.trabajadores.get(i).setDni(contenido_tabla[0]);
        this.trabajadores.get(i).setNombre(contenido_tabla[1]);
        this.trabajadores.get(i).setTelefono(contenido_tabla[2]);
        this.trabajadores.get(i).setEmail(contenido_tabla[3]);
        this.trabajadores.get(i).setDireccion(contenido_tabla[4]);
        this.trabajadores.get(i).setSupermercado(supermercado);
        
        this.modificar(trabajadores.get(i));
    }
    
    private void jButton_guardar_trabajadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_guardar_trabajadorActionPerformed
        boolean buscar_nombre = false;
        for (int i = 0; i < this.trabajadores.size() && !buscar_nombre; i++) {
            if(this.trabajadores.get(i).getDni().equals(this.jTextField_dni_trabajador.getText())) {
                buscar_nombre = true;
            }
        }
        
        Supermercado supermercado;
        if(this.aniadir) {
            this.modificar = false;
            if(!buscar_nombre) {
                String contenido_tabla[] = new String[5];
                Trabajador trabajador = new Trabajador();
                int aux = this.trabajadores.size();
                if(aux > 0) {
                    aux--;
                    trabajador.setCod_trabajador(this.trabajadores.get(aux).getCod_trabajador() + 1);
                } else {
                    trabajador.setCod_trabajador(1);
                }

                supermercado = (Supermercado)this.jComboBox1_supermercado_trabajador.getSelectedItem();
                trabajador.setSupermercado(supermercado);

                if(this.jTextField_dni_trabajador.getText().equals("") || this.jTextField_nombre_trabajador.getText().equals("") 
                   || this.jTextField_telefono_trabajador.getText().equals("") || this.jTextField_email_trabajador.getText().equals("") 
                   || this.jTextField_direccion_trabajador.getText().equals("")) {
                    this.jTextField_comprobar_trabajador.setText("Complete todos los campos");
                } else {
                    contenido_tabla[0] = this.jTextField_dni_trabajador.getText();
                    contenido_tabla[1] = this.jTextField_nombre_trabajador.getText();
                    contenido_tabla[2] = this.jTextField_telefono_trabajador.getText();
                    contenido_tabla[3] = this.jTextField_email_trabajador.getText();
                    contenido_tabla[4] = this.jTextField_direccion_trabajador.getText();

                    trabajador.setDni(contenido_tabla[0]);
                    trabajador.setNombre(contenido_tabla[1]);
                    trabajador.setTelefono(contenido_tabla[2]);
                    trabajador.setEmail(contenido_tabla[3]);
                    trabajador.setDireccion(contenido_tabla[4]);

                    tabla_trabajador.addRow(contenido_tabla);
                    this.trabajadores.add(trabajador);
                    this.jComboBox2_trabajador_producto.addItem(trabajador);

                    this.aniadir = false;
                    
                    this.insert(trabajador);
                }
            } else {
                this.jTextField_comprobar_trabajador.setText("Ya existe un trabajador con ese DNI");
            }
        } else if(this.modificar) {
            this.aniadir = false;
            int i = this.jTable_trabajadores.getSelectedRow();
            String contenido_tabla[] = new String[5];

            supermercado = (Supermercado)this.jComboBox1_supermercado_trabajador.getSelectedItem();

            if(this.jTextField_dni_trabajador.getText().equals("") || this.jTextField_nombre_trabajador.getText().equals("") 
               || this.jTextField_telefono_trabajador.getText().equals("") || this.jTextField_email_trabajador.getText().equals("") 
               || this.jTextField_direccion_trabajador.getText().equals("")) {
                this.jTextField_comprobar_trabajador.setText("Complete todos los campos");
            } else {
                contenido_tabla[0] = this.jTextField_dni_trabajador.getText();
                contenido_tabla[1] = this.jTextField_nombre_trabajador.getText();
                contenido_tabla[3] = this.jTextField_telefono_trabajador.getText();
                contenido_tabla[2] = this.jTextField_email_trabajador.getText();
                contenido_tabla[4] = this.jTextField_direccion_trabajador.getText();

                this.modificarTrabajadorDesdeGuardar(i, contenido_tabla, supermercado);

                this.tabla_trabajador.setValueAt(contenido_tabla[0], jTable_trabajadores.getSelectedRow(),0);
                this.tabla_trabajador.setValueAt(contenido_tabla[1], jTable_trabajadores.getSelectedRow(),1);
                this.tabla_trabajador.setValueAt(contenido_tabla[2], jTable_trabajadores.getSelectedRow(),2);
                this.tabla_trabajador.setValueAt(contenido_tabla[3], jTable_trabajadores.getSelectedRow(),3);
                this.tabla_trabajador.setValueAt(contenido_tabla[4], jTable_trabajadores.getSelectedRow(),4);

                this.modificar = false;

                if(this.mouseTable) {
                    this.jButton_aniadir_trabajador.setEnabled(true);
                    this.mouseTable = false;
                }
            }
        }

        if(!this.jTextField_dni_trabajador.getText().equals("") && !this.jTextField_nombre_trabajador.getText().equals("") 
               && !this.jTextField_telefono_trabajador.getText().equals("") && !this.jTextField_email_trabajador.getText().equals("") 
               && !this.jTextField_direccion_trabajador.getText().equals("")) {
            this.jLabel_dni_trabajador.setEnabled(false);
            this.jLabel_nombre_trabajador.setEnabled(false);
            this.jLabel_telefono_trabajador.setEnabled(false);
            this.jLabel_email_trabajador.setEnabled(false);
            this.jLabel_direccion_trabajador.setEnabled(false);
            this.jLabel_supermercado_trabajador.setEnabled(false);

            this.jTextField_dni_trabajador.setEnabled(false);
            this.jTextField_nombre_trabajador.setEnabled(false);
            this.jTextField_telefono_trabajador.setEnabled(false);
            this.jTextField_email_trabajador.setEnabled(false);
            this.jTextField_direccion_trabajador.setEnabled(false);
            this.jComboBox1_supermercado_trabajador.setEnabled(false);

            this.jTextField_dni_trabajador.setText("");
            this.jTextField_nombre_trabajador.setText("");
            this.jTextField_telefono_trabajador.setText("");
            this.jTextField_email_trabajador.setText("");
            this.jTextField_direccion_trabajador.setText("");
            this.jTextField_comprobar_trabajador.setText("");

            this.jButton_guardar_trabajador.setEnabled(false);
            this.jButton_cancelar_trabajador.setEnabled(false);
            
            this.jButton_modificar_trabajador.setEnabled(false);
            this.jButton_borrar_trabajador.setEnabled(false);
        }
    }//GEN-LAST:event_jButton_guardar_trabajadorActionPerformed

    private void jTextField_dni_trabajadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_dni_trabajadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_dni_trabajadorActionPerformed

    private void jTable_trabajadoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_trabajadoresMouseClicked
        int i = this.jTable_trabajadores.getSelectedRow(),j;
        boolean buscar_super = false;
        this.jButton_modificar_trabajador.setEnabled(true);
        this.jButton_borrar_trabajador.setEnabled(true);
        
        this.jTextField_dni_trabajador.setText(jTable_trabajadores.getValueAt(jTable_trabajadores.getSelectedRow(),0).toString());
        this.jTextField_nombre_trabajador.setText(jTable_trabajadores.getValueAt(jTable_trabajadores.getSelectedRow(),1).toString());
        this.jTextField_telefono_trabajador.setText(jTable_trabajadores.getValueAt(jTable_trabajadores.getSelectedRow(),2).toString());
        this.jTextField_email_trabajador.setText(jTable_trabajadores.getValueAt(jTable_trabajadores.getSelectedRow(),3).toString());
        this.jTextField_direccion_trabajador.setText(jTable_trabajadores.getValueAt(jTable_trabajadores.getSelectedRow(),4).toString());
        
        
        for (j = 0; j < this.supermercados.size() && !buscar_super; j++) {
            if(this.trabajadores.get(i).getSupermercado().getCod_super() == this.supermercados.get(j).getCod_super()) {
                buscar_super = true;
            }
        }
        j--;
        this.jComboBox1_supermercado_trabajador.setSelectedItem(this.supermercados.get(j));
        
        this.jTextField_comprobar_trabajador.setText("");
        
        this.mouseTable = true;
    }//GEN-LAST:event_jTable_trabajadoresMouseClicked

    private void jTextField_codigo_postal_supermercadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_codigo_postal_supermercadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_codigo_postal_supermercadoActionPerformed

    private void jTextField_telefono_supermercadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_telefono_supermercadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_telefono_supermercadoActionPerformed

    private void jButton_cancelar_supermercadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_cancelar_supermercadoActionPerformed
        this.jLabel_nombre_supermercado.setEnabled(false);
        this.jLabel_direccion_supermercado.setEnabled(false);
        this.jLabel_email_supermercado.setEnabled(false);
        this.jLabel_telefono_supermercado.setEnabled(false);
        this.jLabel_codigo_postal_supermercado.setEnabled(false);
        
        this.jTextField_nombre_supermercado.setEnabled(false);
        this.jTextField_direccion_supermercado.setEnabled(false);
        this.jTextField_email_supermercado.setEnabled(false);
        this.jTextField_telefono_supermercado.setEnabled(false);
        this.jTextField_codigo_postal_supermercado.setEnabled(false);
        
        this.jTextField_nombre_supermercado.setText("");
        this.jTextField_direccion_supermercado.setText("");
        this.jTextField_email_supermercado.setText("");
        this.jTextField_telefono_supermercado.setText("");
        this.jTextField_codigo_postal_supermercado.setText("");
        this.jTextField_comprobar_supermercado.setText("");
        
        this.jButton_guardar_supermercado.setEnabled(false);
        this.jButton_cancelar_supermercado.setEnabled(false);
        
        this.jButton_modificar_supermercado.setEnabled(false);
        this.jButton_borrar_supermercado.setEnabled(false);
        
        this.aniadir = false;
        this.modificar = false;
        
        if(this.mouseTable) {
            this.jButton_aniadir_supermercado.setEnabled(true);
            this.mouseTable = false;
        }
    }//GEN-LAST:event_jButton_cancelar_supermercadoActionPerformed

    public void aniadirContenido(Supermercado supermercado, String contenido_tabla[]) {
        int aux = this.supermercados.size();
        if(aux > 0) {
            aux--;
            supermercado.setCod_super(this.supermercados.get(aux).getCod_super() + 1);
        } else {
            supermercado.setCod_super(1);
        }
        
        supermercado.setNombre(contenido_tabla[0]);
        supermercado.setDireccion(contenido_tabla[1]);
        supermercado.setEmail(contenido_tabla[2]);
        supermercado.setTelefono(contenido_tabla[3]);
        supermercado.setCod_postal(contenido_tabla[4]);
        
        this.supermercados.add(supermercado);
        
        this.insert(supermercado);
    }
    
    public void aniadirSupermercado() throws IOException {
        String contenido_tabla[] = new String[5];
        Supermercado supermercado = new Supermercado();

        if(this.jTextField_nombre_supermercado.getText().equals("") || this.jTextField_direccion_supermercado.getText().equals("") 
           || this.jTextField_email_supermercado.getText().equals("") || this.jTextField_telefono_supermercado.getText().equals("") 
           || this.jTextField_codigo_postal_supermercado.getText().equals("")) {
            this.jTextField_comprobar_supermercado.setText("Complete todos los campos");
        } else {
            contenido_tabla[0] = this.jTextField_nombre_supermercado.getText();
            contenido_tabla[1] = this.jTextField_direccion_supermercado.getText();
            contenido_tabla[2] = this.jTextField_email_supermercado.getText();
            contenido_tabla[3] = this.jTextField_telefono_supermercado.getText();
            contenido_tabla[4] = this.jTextField_codigo_postal_supermercado.getText();

            this.aniadirContenido(supermercado, contenido_tabla);

            this.tabla_supermercado.addRow(contenido_tabla);
            this.jComboBox1_supermercado_trabajador.addItem(supermercado);

            this.aniadir = false;
        }
    }
    
    private void jButton_guardar_supermercadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_guardar_supermercadoActionPerformed
        boolean buscar_nombre = false;
        int i;
        for (int j = 0; j < this.supermercados.size() && !buscar_nombre; j++) {
            if(this.supermercados.get(j).getNombre().equals(this.jTextField_nombre_supermercado.getText())) {
                buscar_nombre = true;
            }
        }
        
        if(this.aniadir) {
            this.modificar = false;
            if(!buscar_nombre) {
                try {
                    this.aniadirSupermercado();
                } catch (IOException ex) {
                    Logger.getLogger(Ventana1.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                this.jTextField_comprobar_supermercado.setText("Ya existe un supermercado con ese nombre");
            }
        } else if(this.modificar) {
            this.aniadir = false;
            i = this.jTable_supermercados.getSelectedRow();
            String contenido_tabla[] = new String[5];
            
            if(this.jTextField_nombre_supermercado.getText().equals("") || this.jTextField_direccion_supermercado.getText().equals("") 
               || this.jTextField_email_supermercado.getText().equals("") || this.jTextField_telefono_supermercado.getText().equals("") 
               || this.jTextField_codigo_postal_supermercado.getText().equals("")) {
                this.jTextField_comprobar_supermercado.setText("Complete todos los campos");
            } else {
                contenido_tabla[0] = this.jTextField_nombre_supermercado.getText();
                contenido_tabla[1] = this.jTextField_direccion_supermercado.getText();
                contenido_tabla[2] = this.jTextField_email_supermercado.getText();
                contenido_tabla[3] = this.jTextField_telefono_supermercado.getText();
                contenido_tabla[4] = this.jTextField_codigo_postal_supermercado.getText();

                this.supermercados.get(i).setNombre(contenido_tabla[0]);
                this.supermercados.get(i).setDireccion(contenido_tabla[1]);
                this.supermercados.get(i).setEmail(contenido_tabla[2]);
                this.supermercados.get(i).setTelefono(contenido_tabla[3]);
                this.supermercados.get(i).setCod_postal(contenido_tabla[4]);

                this.tabla_supermercado.setValueAt(contenido_tabla[0], jTable_supermercados.getSelectedRow(),0);
                this.tabla_supermercado.setValueAt(contenido_tabla[1], jTable_supermercados.getSelectedRow(),1);
                this.tabla_supermercado.setValueAt(contenido_tabla[2], jTable_supermercados.getSelectedRow(),2);
                this.tabla_supermercado.setValueAt(contenido_tabla[3], jTable_supermercados.getSelectedRow(),3);
                this.tabla_supermercado.setValueAt(contenido_tabla[4], jTable_supermercados.getSelectedRow(),4);

                this.modificar = false;

                if(this.mouseTable) {
                    this.jButton_aniadir_supermercado.setEnabled(true);
                    this.mouseTable = false;
                }
                
                this.modificar(this.supermercados.get(i));
            }
        }

        if(!this.jTextField_nombre_supermercado.getText().equals("") && !this.jTextField_direccion_supermercado.getText().equals("") 
               && !this.jTextField_email_supermercado.getText().equals("") && !this.jTextField_telefono_supermercado.getText().equals("") 
               && !this.jTextField_codigo_postal_supermercado.getText().equals("")) {
            this.jLabel_nombre_supermercado.setEnabled(false);
            this.jLabel_direccion_supermercado.setEnabled(false);
            this.jLabel_email_supermercado.setEnabled(false);
            this.jLabel_telefono_supermercado.setEnabled(false);
            this.jLabel_codigo_postal_supermercado.setEnabled(false);

            this.jTextField_nombre_supermercado.setEnabled(false);
            this.jTextField_direccion_supermercado.setEnabled(false);
            this.jTextField_email_supermercado.setEnabled(false);
            this.jTextField_telefono_supermercado.setEnabled(false);
            this.jTextField_codigo_postal_supermercado.setEnabled(false);

            this.jTextField_nombre_supermercado.setText("");
            this.jTextField_direccion_supermercado.setText("");
            this.jTextField_email_supermercado.setText("");
            this.jTextField_telefono_supermercado.setText("");
            this.jTextField_codigo_postal_supermercado.setText("");
            this.jTextField_comprobar_supermercado.setText("");

            this.jButton_guardar_supermercado.setEnabled(false);
            this.jButton_cancelar_supermercado.setEnabled(false);
            
            this.jButton_modificar_supermercado.setEnabled(false);
            this.jButton_borrar_supermercado.setEnabled(false);
        }
    }//GEN-LAST:event_jButton_guardar_supermercadoActionPerformed

    private void jTextField_nombre_supermercadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_nombre_supermercadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_nombre_supermercadoActionPerformed

    private void jButton_borrar_supermercadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_borrar_supermercadoActionPerformed
        int j = this.jTable_supermercados.getSelectedRow();
        boolean busqueda_ID = false;
        
        for (int i = 0; i < this.trabajadores.size() && !busqueda_ID; i++) {
            if(this.supermercados.get(j).getCod_super() == trabajadores.get(i).getSupermercado().getCod_super()) {
                busqueda_ID = true;
            }
        }
        
        if(!busqueda_ID) {
            this.borrar(this.supermercados.get(j));
            this.jComboBox1_supermercado_trabajador.removeItem(this.supermercados.get(j));
            this.supermercados.remove(this.supermercados.get(j));
            this.tabla_supermercado.removeRow(jTable_supermercados.getSelectedRow());
        } else if(busqueda_ID) {
            this.jTextField_comprobar_supermercado.setText("Borre primero los trabajadores de este supermercado");
        }
        
        this.jTextField_nombre_supermercado.setText("");
        this.jTextField_direccion_supermercado.setText("");
        this.jTextField_email_supermercado.setText("");
        this.jTextField_telefono_supermercado.setText("");
        this.jTextField_codigo_postal_supermercado.setText("");
        
        this.jTextField_nombre_supermercado.setEnabled(false);
        this.jTextField_direccion_supermercado.setEnabled(false);
        this.jTextField_email_supermercado.setEnabled(false);
        this.jTextField_telefono_supermercado.setEnabled(false);
        this.jTextField_codigo_postal_supermercado.setEnabled(false);
        
        this.jButton_guardar_supermercado.setEnabled(false);
        this.jButton_cancelar_supermercado.setEnabled(false);
        
        this.jButton_modificar_supermercado.setEnabled(false);
        this.jButton_borrar_supermercado.setEnabled(false);
        
        if(this.mouseTable) {
            this.jButton_aniadir_supermercado.setEnabled(true);
            this.mouseTable = false;
        }
    }//GEN-LAST:event_jButton_borrar_supermercadoActionPerformed

    private void jButton_modificar_supermercadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_modificar_supermercadoActionPerformed
        this.jLabel_nombre_supermercado.setEnabled(true);
        this.jLabel_direccion_supermercado.setEnabled(true);
        this.jLabel_email_supermercado.setEnabled(true);
        this.jLabel_telefono_supermercado.setEnabled(true);
        this.jLabel_codigo_postal_supermercado.setEnabled(true);
        
        this.jTextField_nombre_supermercado.setEnabled(true);
        this.jTextField_direccion_supermercado.setEnabled(true);
        this.jTextField_email_supermercado.setEnabled(true);
        this.jTextField_telefono_supermercado.setEnabled(true);
        this.jTextField_codigo_postal_supermercado.setEnabled(true);
        
        this.jButton_guardar_supermercado.setEnabled(true);
        this.jButton_cancelar_supermercado.setEnabled(true);
        
        this.modificar = true;
    }//GEN-LAST:event_jButton_modificar_supermercadoActionPerformed

    private void jButton_aniadir_supermercadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_aniadir_supermercadoActionPerformed
        this.jTextField_nombre_supermercado.setText("");
        this.jTextField_direccion_supermercado.setText("");
        this.jTextField_email_supermercado.setText("");
        this.jTextField_telefono_supermercado.setText("");
        this.jTextField_codigo_postal_supermercado.setText("");
        this.jTextField_comprobar_supermercado.setText("");
        
        this.jLabel_nombre_supermercado.setEnabled(true);
        this.jLabel_direccion_supermercado.setEnabled(true);
        this.jLabel_email_supermercado.setEnabled(true);
        this.jLabel_telefono_supermercado.setEnabled(true);
        this.jLabel_codigo_postal_supermercado.setEnabled(true);
        
        this.jTextField_nombre_supermercado.setEnabled(true);
        this.jTextField_direccion_supermercado.setEnabled(true);
        this.jTextField_email_supermercado.setEnabled(true);
        this.jTextField_telefono_supermercado.setEnabled(true);
        this.jTextField_codigo_postal_supermercado.setEnabled(true);
        
        this.jButton_guardar_supermercado.setEnabled(true);
        this.jButton_cancelar_supermercado.setEnabled(true);
        
        this.jButton_modificar_supermercado.setEnabled(false);
        this.jButton_borrar_supermercado.setEnabled(false);
        
        this.jTextField_comprobar_supermercado.setText("");
        
        this.aniadir = true;
    }//GEN-LAST:event_jButton_aniadir_supermercadoActionPerformed

    private void jTable_supermercadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_supermercadosMouseClicked
        this.jButton_modificar_supermercado.setEnabled(true);
        this.jButton_borrar_supermercado.setEnabled(true);
        
        this.jTextField_nombre_supermercado.setText(jTable_supermercados.getValueAt(jTable_supermercados.getSelectedRow(),0).toString());
        this.jTextField_direccion_supermercado.setText(jTable_supermercados.getValueAt(jTable_supermercados.getSelectedRow(),1).toString());
        this.jTextField_email_supermercado.setText(jTable_supermercados.getValueAt(jTable_supermercados.getSelectedRow(),2).toString());
        this.jTextField_telefono_supermercado.setText(jTable_supermercados.getValueAt(jTable_supermercados.getSelectedRow(),3).toString());
        this.jTextField_codigo_postal_supermercado.setText(jTable_supermercados.getValueAt(jTable_supermercados.getSelectedRow(),4).toString());
        
        this.jTextField_comprobar_supermercado.setText("");
        
        this.mouseTable = true;
    }//GEN-LAST:event_jTable_supermercadosMouseClicked

    private void jTextField_comprobar_supermercadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_comprobar_supermercadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_comprobar_supermercadoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_aniadir_producto;
    private javax.swing.JButton jButton_aniadir_supermercado;
    private javax.swing.JButton jButton_aniadir_trabajador;
    private javax.swing.JButton jButton_borrar_producto;
    private javax.swing.JButton jButton_borrar_supermercado;
    private javax.swing.JButton jButton_borrar_trabajador;
    public javax.swing.JButton jButton_cancelar_producto;
    private javax.swing.JButton jButton_cancelar_supermercado;
    private javax.swing.JButton jButton_cancelar_trabajador;
    public javax.swing.JButton jButton_guardar_producto;
    private javax.swing.JButton jButton_guardar_supermercado;
    private javax.swing.JButton jButton_guardar_trabajador;
    private javax.swing.JButton jButton_modificar_producto;
    private javax.swing.JButton jButton_modificar_supermercado;
    private javax.swing.JButton jButton_modificar_trabajador;
    private javax.swing.JComboBox<Supermercado> jComboBox1_supermercado_trabajador;
    private javax.swing.JComboBox<Trabajador> jComboBox2_trabajador_producto;
    private javax.swing.JLabel jLabel_categoria_producto;
    private javax.swing.JLabel jLabel_codigo_postal_supermercado;
    private javax.swing.JLabel jLabel_direccion_supermercado;
    private javax.swing.JLabel jLabel_direccion_trabajador;
    private javax.swing.JLabel jLabel_dni_trabajador;
    private javax.swing.JLabel jLabel_email_supermercado;
    private javax.swing.JLabel jLabel_email_trabajador;
    private javax.swing.JLabel jLabel_fecha_caducidad_producto;
    private javax.swing.JLabel jLabel_marca_producto;
    private javax.swing.JLabel jLabel_nombre_producto;
    private javax.swing.JLabel jLabel_nombre_supermercado;
    private javax.swing.JLabel jLabel_nombre_trabajador;
    private javax.swing.JLabel jLabel_precio_producto;
    private javax.swing.JLabel jLabel_supermercado_trabajador;
    private javax.swing.JLabel jLabel_telefono_supermercado;
    private javax.swing.JLabel jLabel_telefono_trabajador;
    private javax.swing.JLabel jLabel_trabajador_producto;
    private javax.swing.JPanel jPanel_productos;
    private javax.swing.JPanel jPanel_supermercados;
    private javax.swing.JPanel jPanel_trabajadores;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTable jTable_productos;
    private javax.swing.JTable jTable_supermercados;
    private javax.swing.JTable jTable_trabajadores;
    private javax.swing.JTextField jTextField_categoria_producto;
    public javax.swing.JTextField jTextField_codigo_postal_supermercado;
    private javax.swing.JTextField jTextField_comprobar_producto;
    public javax.swing.JTextField jTextField_comprobar_supermercado;
    public javax.swing.JTextField jTextField_comprobar_trabajador;
    public javax.swing.JTextField jTextField_direccion_supermercado;
    public javax.swing.JTextField jTextField_direccion_trabajador;
    public javax.swing.JTextField jTextField_dni_trabajador;
    public javax.swing.JTextField jTextField_email_supermercado;
    public javax.swing.JTextField jTextField_email_trabajador;
    private javax.swing.JTextField jTextField_fecha_caducidad_producto;
    private javax.swing.JTextField jTextField_marca_producto;
    private javax.swing.JTextField jTextField_nombre_producto;
    public javax.swing.JTextField jTextField_nombre_supermercado;
    public javax.swing.JTextField jTextField_nombre_trabajador;
    private javax.swing.JTextField jTextField_precio_producto;
    public javax.swing.JTextField jTextField_telefono_supermercado;
    public javax.swing.JTextField jTextField_telefono_trabajador;
    // End of variables declaration//GEN-END:variables

    public void setComoboBox1(Supermercado supermercado) {
        this.jComboBox1_supermercado_trabajador.addItem(supermercado);
    }
    public void formWindowClosing(java.awt.event.WindowEvent evt) {
        
    }
}