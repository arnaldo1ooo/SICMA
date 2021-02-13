/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms.inventario.entrada;

import conexion.Conexion;
import forms.inventario.registrante.ABMEmpresaVendedora;
import forms.producto.ABMProductoViejo;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import metodos.Metodos;
import metodos.MetodosCombo;

/**
 *
 * @author Lic. Arnaldo Cantero
 */
public class AMEntrada extends javax.swing.JDialog {

    public AMEntrada(java.awt.Dialog parent, Boolean modal) {
        super(parent, modal);
        initComponents();

        //Mensaje al cerrar ventana
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                if (txtCodigo.getText().isEmpty()) {
                    if (JOptionPane.showConfirmDialog(rootPane, "¿Desea cancelar la nueva entrada?",
                            "Advertencia", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        dispose();
                    }
                } else {
                    if (JOptionPane.showConfirmDialog(rootPane, "¿Desea cancelar la modificación de la entrada?",
                            "Advertencia", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        dispose();
                    }
                }
            }
        });

        Calendar calendario = new GregorianCalendar();

        dcFechaEntrada.setCalendar(calendario);

        CargarCombos();
    }
    //-------------METODOS-------------//
    Metodos metodos = new Metodos();
    MetodosCombo metodoscombo = new MetodosCombo();

    private void CargarCombos() {
        metodoscombo.CargarComboBox(getCbEstablecimiento(), "SELECT estab_codigo, estab_descripcion FROM establecimiento ORDER BY estab_descripcion");
        metodoscombo.CargarComboBox(cbProducto, "SELECT pro_codigo, pro_descripcion FROM producto ORDER BY pro_descripcion");
        metodoscombo.CargarComboBox(cbEmpresaVendedora, "SELECT emv_codigo, emv_descripcion FROM empresa_vendedora ORDER BY emv_descripcion");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpPrincipal = new javax.swing.JPanel();
        jpBanner = new javax.swing.JPanel();
        lbBanner = new javax.swing.JLabel();
        jpTabla = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jbIImagen3 = new javax.swing.JLabel();
        dcFechaEntrada = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        cbProducto = new javax.swing.JComboBox<>();
        lbPresentacion = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtPrecioUnitario = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtPrecioTotal = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        cbPresentacion = new javax.swing.JComboBox();
        dcFechaCompra = new com.toedter.calendar.JDateChooser();
        jbIImagen4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taObs = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        cbEstablecimiento = new javax.swing.JComboBox<>();
        btnProducto = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        txtNumFactura = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        cbEmpresaVendedora = new javax.swing.JComboBox<>();
        btnEmpresaVendedora = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtCodUsuario = new javax.swing.JTextField();
        jpBotones2 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setTitle("Ventana nueva entrada");
        setBackground(new java.awt.Color(45, 62, 80));
        setModal(true);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);

        jpPrincipal.setBackground(new java.awt.Color(45, 62, 80));
        jpPrincipal.setPreferredSize(new java.awt.Dimension(1580, 478));

        jpBanner.setBackground(new java.awt.Color(0, 102, 204));
        jpBanner.setPreferredSize(new java.awt.Dimension(1000, 82));

        lbBanner.setFont(new java.awt.Font("sansserif", 1, 23)); // NOI18N
        lbBanner.setForeground(new java.awt.Color(255, 255, 255));
        lbBanner.setText("Nueva entrada");
        lbBanner.setMaximumSize(new java.awt.Dimension(1100, 52));
        lbBanner.setMinimumSize(new java.awt.Dimension(1100, 52));
        lbBanner.setPreferredSize(new java.awt.Dimension(1100, 52));

        javax.swing.GroupLayout jpBannerLayout = new javax.swing.GroupLayout(jpBanner);
        jpBanner.setLayout(jpBannerLayout);
        jpBannerLayout.setHorizontalGroup(
            jpBannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBannerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbBanner, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jpBannerLayout.setVerticalGroup(
            jpBannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBannerLayout.createSequentialGroup()
                .addComponent(lbBanner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jpTabla.setBackground(new java.awt.Color(45, 62, 80));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Código:");

        txtCodigo.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigo.setToolTipText("Codigo de registro");
        txtCodigo.setEnabled(false);
        txtCodigo.setFocusable(false);

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Cantidad*:");

        txtCantidad.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtCantidad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCantidad.setText("0");
        txtCantidad.setToolTipText("Cantidad del producto en unidades");
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCantidadKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });

        jbIImagen3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jbIImagen3.setForeground(new java.awt.Color(255, 255, 255));
        jbIImagen3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jbIImagen3.setText("Fecha de entrada*:");

        dcFechaEntrada.setToolTipText("Fecha de entrada del producto");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Producto*:");

        cbProducto.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        cbProducto.setToolTipText("El producto que se dará entrada");
        cbProducto.setMinimumSize(new java.awt.Dimension(55, 31));
        cbProducto.setName("Producto"); // NOI18N
        cbProducto.setPreferredSize(new java.awt.Dimension(55, 31));
        cbProducto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbProductoItemStateChanged(evt);
            }
        });

        lbPresentacion.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbPresentacion.setForeground(new java.awt.Color(255, 255, 255));
        lbPresentacion.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbPresentacion.setText("Lts");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Precio Unitario*:");

        txtPrecioUnitario.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtPrecioUnitario.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPrecioUnitario.setText("0");
        txtPrecioUnitario.setToolTipText("Precio por unidad del producto");
        txtPrecioUnitario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPrecioUnitarioKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioUnitarioKeyTyped(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("$");

        jLabel11.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Precio Total:");
        jLabel11.setToolTipText("");

        txtPrecioTotal.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtPrecioTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPrecioTotal.setText("0");
        txtPrecioTotal.setToolTipText("Precio total de la entrada de producto (Precio unitario x cantidad)");
        txtPrecioTotal.setEnabled(false);
        txtPrecioTotal.setFocusable(false);

        jLabel12.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("$");

        jLabel13.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Usuario:");

        txtUsuario.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtUsuario.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtUsuario.setToolTipText("Usuario que realiza la entrada");
        txtUsuario.setEnabled(false);
        txtUsuario.setFocusable(false);

        jLabel14.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Obs:");

        cbPresentacion.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cbPresentacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0.5", "1", "5", "10", "20", "25", "50", "100" }));
        cbPresentacion.setToolTipText("En que presentación viene el producto");
        cbPresentacion.setFocusable(false);
        cbPresentacion.setMinimumSize(new java.awt.Dimension(55, 31));
        cbPresentacion.setName("ClaseProducto"); // NOI18N
        cbPresentacion.setPreferredSize(new java.awt.Dimension(55, 31));

        dcFechaCompra.setToolTipText("Fecha en que se compró el producto");

        jbIImagen4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jbIImagen4.setForeground(new java.awt.Color(255, 255, 255));
        jbIImagen4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jbIImagen4.setText("Fecha de compra*:");

        taObs.setColumns(10);
        taObs.setRows(5);
        taObs.setToolTipText("Alguna observación de la entrada de producto");
        jScrollPane1.setViewportView(taObs);

        jLabel15.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Establecimiento*:");

        cbEstablecimiento.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        cbEstablecimiento.setToolTipText("El establecimiento en que se dará entrada de producto");
        cbEstablecimiento.setEnabled(false);
        cbEstablecimiento.setMinimumSize(new java.awt.Dimension(55, 31));
        cbEstablecimiento.setName("Establecimiento"); // NOI18N
        cbEstablecimiento.setPreferredSize(new java.awt.Dimension(55, 31));

        btnProducto.setFont(new java.awt.Font("Adobe Hebrew", 1, 18)); // NOI18N
        btnProducto.setText("...");
        btnProducto.setToolTipText("Ir a la ventana de Tipos de productos");
        btnProducto.setFocusable(false);
        btnProducto.setPreferredSize(new java.awt.Dimension(35, 31));
        btnProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductoActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Empresa vendedora*:");

        txtNumFactura.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtNumFactura.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumFactura.setToolTipText("N° de factura de la compra");
        txtNumFactura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumFacturaKeyTyped(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("N° de factura*:");

        cbEmpresaVendedora.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        cbEmpresaVendedora.setToolTipText("La empresa en donde se realizó la compra");
        cbEmpresaVendedora.setMinimumSize(new java.awt.Dimension(55, 31));
        cbEmpresaVendedora.setName("EmpresaVendedora"); // NOI18N
        cbEmpresaVendedora.setPreferredSize(new java.awt.Dimension(55, 31));

        btnEmpresaVendedora.setFont(new java.awt.Font("Adobe Hebrew", 1, 18)); // NOI18N
        btnEmpresaVendedora.setText("...");
        btnEmpresaVendedora.setToolTipText("Ir a la ventana de Tipos de empresas vendedoras");
        btnEmpresaVendedora.setFocusable(false);
        btnEmpresaVendedora.setPreferredSize(new java.awt.Dimension(35, 31));
        btnEmpresaVendedora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmpresaVendedoraActionPerformed(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(255, 255, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Campos con (*) son obligatorios");

        txtCodUsuario.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtCodUsuario.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCodUsuario.setToolTipText("");
        txtCodUsuario.setEnabled(false);
        txtCodUsuario.setFocusable(false);

        javax.swing.GroupLayout jpTablaLayout = new javax.swing.GroupLayout(jpTabla);
        jpTabla.setLayout(jpTablaLayout);
        jpTablaLayout.setHorizontalGroup(
            jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTablaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbIImagen3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpTablaLayout.createSequentialGroup()
                        .addGap(148, 148, 148)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpTablaLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNumFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpTablaLayout.createSequentialGroup()
                                .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jpTablaLayout.createSequentialGroup()
                                        .addComponent(txtPrecioUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(4, 4, 4)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(4, 4, 4)
                                .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jpTablaLayout.createSequentialGroup()
                                        .addComponent(cbPresentacion, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(2, 2, 2)
                                        .addComponent(lbPresentacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(108, 108, 108))
                                    .addGroup(jpTablaLayout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(4, 4, 4)
                                        .addComponent(txtPrecioTotal)
                                        .addGap(4, 4, 4)))
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cbEstablecimiento, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jpTablaLayout.createSequentialGroup()
                                .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(cbEmpresaVendedora, javax.swing.GroupLayout.Alignment.LEADING, 0, 354, Short.MAX_VALUE)
                                    .addComponent(cbProducto, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(2, 2, 2)
                                .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnEmpresaVendedora, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpTablaLayout.createSequentialGroup()
                                .addComponent(txtCodUsuario)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpTablaLayout.createSequentialGroup()
                                .addComponent(dcFechaEntrada, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbIImagen4, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dcFechaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 20, Short.MAX_VALUE))
        );
        jpTablaLayout.setVerticalGroup(
            jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpTablaLayout.createSequentialGroup()
                .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(dcFechaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbIImagen4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dcFechaEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbIImagen3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cbEstablecimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cbProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cbEmpresaVendedora, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEmpresaVendedora, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cbPresentacion, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrecioTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPrecioUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(3, 3, 3)
                .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpTablaLayout.createSequentialGroup()
                .addContainerGap(176, Short.MAX_VALUE)
                .addComponent(lbPresentacion, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(142, 142, 142))
        );

        jpBotones2.setBackground(new java.awt.Color(45, 62, 80));
        jpBotones2.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        btnGuardar.setBackground(new java.awt.Color(0, 153, 255));
        btnGuardar.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Iconos20x20/IconoGuardar.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setToolTipText("Inserta el nuevo registro");
        btnGuardar.setPreferredSize(new java.awt.Dimension(128, 36));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        btnGuardar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnGuardarKeyPressed(evt);
            }
        });

        btnCancelar.setBackground(new java.awt.Color(255, 0, 51));
        btnCancelar.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Iconos20x20/IconoCancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setToolTipText("Cancela la acción");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpBotones2Layout = new javax.swing.GroupLayout(jpBotones2);
        jpBotones2.setLayout(jpBotones2Layout);
        jpBotones2Layout.setHorizontalGroup(
            jpBotones2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBotones2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpBotones2Layout.setVerticalGroup(
            jpBotones2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBotones2Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jpBotones2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpPrincipalLayout = new javax.swing.GroupLayout(jpPrincipal);
        jpPrincipal.setLayout(jpPrincipalLayout);
        jpPrincipalLayout.setHorizontalGroup(
            jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpBanner, javax.swing.GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpPrincipalLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jpBotones2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(158, 158, 158))
            .addGroup(jpPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpPrincipalLayout.setVerticalGroup(
            jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPrincipalLayout.createSequentialGroup()
                .addComponent(jpBanner, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpBotones2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jpBanner.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleName("ABMEntrada");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        RegistroNuevoEntradaInventario();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        Limpiar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnGuardarKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            btnGuardar.doClick();
        }
    }//GEN-LAST:event_btnGuardarKeyPressed

    private void btnProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductoActionPerformed
        ABMProductoViejo abmproducto = new ABMProductoViejo(null, true, cbProducto);
        abmproducto.setVisible(true);
    }//GEN-LAST:event_btnProductoActionPerformed

    private void txtCantidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyReleased
        //Si texto es vacio poner 0
        if (txtCantidad.getText().isBlank() == true) {
            txtCantidad.setText("0");
        }

        //Calculo Precio Total
        double Cantidad = Double.parseDouble(txtCantidad.getText());
        double PrecioUnitario = Double.parseDouble(txtPrecioUnitario.getText().replace(",", "."));
        txtPrecioTotal.setText(formatodecimal.format(Cantidad * PrecioUnitario) + "");
    }//GEN-LAST:event_txtCantidadKeyReleased

    DecimalFormat formatodecimal = new DecimalFormat("#.##");
    private void txtPrecioUnitarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioUnitarioKeyReleased
        //Si texto es vacio poner 0
        if (txtPrecioUnitario.getText().isBlank() == true) {
            txtPrecioUnitario.setText("0");
        }

        //Calcular precio total
        double Cantidad = Double.parseDouble(txtCantidad.getText());
        double PrecioUnitario = Double.parseDouble(txtPrecioUnitario.getText().replace(",", "."));
        txtPrecioTotal.setText(formatodecimal.format(Cantidad * PrecioUnitario));
    }//GEN-LAST:event_txtPrecioUnitarioKeyReleased

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        //Limitar cantidad de caracteres
        int longitud = txtCantidad.getText().length();
        if (longitud > 20 - 1) {
            txtCantidad.setText(txtCantidad.getText().substring(0, 21));
        }

        // Verificar si la tecla pulsada no es un digito
        char caracter = evt.getKeyChar();
        if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
            evt.consume(); // ignorar el evento de teclado
        }

        if (txtCantidad.getText().equals("0") == true) {
            txtCantidad.setText("");
        }
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void btnEmpresaVendedoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpresaVendedoraActionPerformed
        ABMEmpresaVendedora abmempresavendedora = new ABMEmpresaVendedora(null, true);
        abmempresavendedora.setVisible(true);
    }//GEN-LAST:event_btnEmpresaVendedoraActionPerformed


    private void txtPrecioUnitarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioUnitarioKeyTyped
        //Limitar cantidad de caracteres
        int longitud = txtPrecioUnitario.getText().length();
        if (longitud > 20 - 1) {
            txtPrecioUnitario.setText(txtPrecioUnitario.getText().substring(0, 21));
        }

        // Que solo entre numeros y .
        char caracter = evt.getKeyChar();
        if ((((caracter < '0') || (caracter > '9'))
                && (caracter != KeyEvent.VK_BACK_SPACE)
                && (caracter != ',' || txtPrecioUnitario.getText().contains(",")))) {
            evt.consume(); // ignorar el evento de teclado
        }

        //Si al escribir hay un 0 se borra antes de escribir
        if (txtPrecioUnitario.getText().equals("0") == true) {
            txtPrecioUnitario.setText("");
        }
    }//GEN-LAST:event_txtPrecioUnitarioKeyTyped

    private void txtNumFacturaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumFacturaKeyTyped
        // Verificar si la tecla pulsada no es un digito
        char caracter = evt.getKeyChar();
        if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
            evt.consume(); // ignorar el evento de teclado
        }
    }//GEN-LAST:event_txtNumFacturaKeyTyped

    private void cbProductoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbProductoItemStateChanged
        EstadoProducto();
    }//GEN-LAST:event_cbProductoItemStateChanged

    private void EstadoProducto() {
        if (cbProducto.getSelectedIndex() != -1) {
            try {
                Conexion con = metodos.ObtenerRSSentencia("SELECT es_descripcion FROM producto, formulacion, estado "
                        + "WHERE pro_formulacion = for_codigo AND for_estado = es_codigo AND pro_codigo = '" + metodoscombo.ObtenerIdComboBox(cbProducto) + "'");
                con.getResultSet().next();

                String estado = con.getResultSet().getString("es_descripcion");
                if (estado.equals("ml/Ha")) {
                    lbPresentacion.setText("Lts");
                } else {
                    if (estado.equals("gr/Ha")) {
                        lbPresentacion.setText("Kgs");
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Error al verificar estado de producto");
                Logger.getLogger(AMEntrada.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void Limpiar() {
        txtCodigo.setText("");
        txtCantidad.setText("");
        txtNumFactura.setText("");
        txtPrecioTotal.setText("");
        txtUsuario.setText("");
        txtPrecioUnitario.setText("");
        cbEmpresaVendedora.setSelectedIndex(-1);
        getCbEstablecimiento().setSelectedIndex(-1);
        cbPresentacion.setSelectedIndex(1);
        cbProducto.setSelectedIndex(-1);
    }

    private void RegistroNuevoEntradaInventario() {
        try {
            if (dcFechaEntrada.getDate() != null
                    && dcFechaCompra.getDate() != null
                    && getCbEstablecimiento().getSelectedIndex() != -1
                    && cbProducto.getSelectedIndex() != -1
                    && !txtNumFactura.getText().isEmpty()
                    && cbEmpresaVendedora.getSelectedIndex() != -1
                    && !txtCantidad.getText().isEmpty()
                    && cbPresentacion.getSelectedIndex() != -1
                    && !txtPrecioUnitario.getText().isEmpty()) {

                if (dcFechaCompra.getDate().after(dcFechaEntrada.getDate())) {
                    JOptionPane.showMessageDialog(null, "La fecha de compra no puede ser después de la fecha de entrada", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd");
                DecimalFormat formatodecimal2 = new DecimalFormat("#.##");

                int idestablecimiento = metodoscombo.ObtenerIdComboBox(getCbEstablecimiento());
                int idproducto = metodoscombo.ObtenerIdComboBox(cbProducto);
                String numfactura = txtNumFactura.getText();
                int idempresavendedora = metodoscombo.ObtenerIdComboBox(cbEmpresaVendedora);
                String fechaentrada = formatofecha.format(dcFechaEntrada.getDate());
                String fechacompra = formatofecha.format(dcFechaCompra.getDate());
                double cantidad = Double.parseDouble(txtCantidad.getText());
                double presentacion = Double.parseDouble(cbPresentacion.getSelectedItem() + "");
                double preciounitario = Double.parseDouble(formatodecimal2.format(txtPrecioUnitario.getText().replace(",", ".")));
                double preciototal = Double.parseDouble(txtPrecioTotal.getText().replace(",", "."));
                String obs = taObs.getText();
                String codusuario = txtCodUsuario.getText();

                int confirmado = JOptionPane.showConfirmDialog(null, "¿Esta seguro crear este nuevo registro?", "Confirmación", JOptionPane.YES_OPTION);

                if (JOptionPane.YES_OPTION == confirmado) {
                    //REGISTRAR NUEVO
                    try {
                        try (Connection con = Conexion.ConectarBasedeDatos()) {
                            String sentencia = "CALL SP_EntradaAlta ('" + idestablecimiento + "', '" + idproducto + "', '" + numfactura
                                    + "', '" + idempresavendedora + "', '" + fechaentrada + "', '" + fechacompra + "', '"
                                    + cantidad + "', '" + presentacion + "', '" + preciounitario + "', '" + preciototal + "', '"
                                    + codusuario + "', '" + obs + "')";
                            System.out.println("Insertar registro: " + sentencia);
                            Statement statement = (Statement) con.createStatement();
                            statement.executeUpdate(sentencia);
                        }
                        RegistroNuevoInventario();
                        JOptionPane.showMessageDialog(this, "Se agrego correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                    } catch (HeadlessException ex) {
                        JOptionPane.showMessageDialog(this, "Ocurrió un Error " + ex.getMessage());
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Ocurrió un Error " + ex.getMessage());
                    }
                } else {
                    System.out.println("No se guardo el registro");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Completa todos los campos marcados con *", "Advertencia", JOptionPane.WARNING_MESSAGE);
                dcFechaEntrada.requestFocus();
            }

        } catch (HeadlessException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar registro", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Completar los campos obligarios marcados con * " + ex);
            txtCantidad.requestFocus();
        }
    }

    private void RegistroNuevoInventario() {
        try {
            SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd");
            int idestablecimiento = metodoscombo.ObtenerIdComboBox(getCbEstablecimiento());
            int idproducto = metodoscombo.ObtenerIdComboBox(cbProducto);
            double presentacion = Double.parseDouble(cbPresentacion.getSelectedItem() + "");
            String fechaultimaentrada = formatofecha.format(new Date());
            String fechaultimasalida = "0000-00-00";
            int cantidadentrada = 1;
            int cantidadsalida = 0;
            double existencia = Double.parseDouble(txtCantidad.getText());
            double existenciatotal = (existencia * presentacion) * 1000;
            double costototal = Double.parseDouble(txtPrecioTotal.getText().replace(",", "."));

            //REGISTRAR NUEVO INVENTARIO
            try {
                try ( Connection con = (Connection) Conexion.ConectarBasedeDatos()) {
                    String sentencia = "CALL SP_InventarioAlta ('" + idestablecimiento + "', '" + idproducto + "', '"
                            + presentacion + "', '" + fechaultimaentrada + "', '" + fechaultimasalida + "', '"
                            + cantidadentrada + "', '" + cantidadsalida + "', '" + existencia + "', '"
                            + existenciatotal + "', '" + costototal + "')";
                    System.out.println("Insertar registro: " + sentencia);
                    Statement statement = (Statement) con.createStatement();
                    statement.executeUpdate(sentencia);
                }
                Limpiar();
            } catch (HeadlessException ex) {
                JOptionPane.showMessageDialog(this, "Ocurrió un Error " + ex.getMessage());
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Ocurrió un Error " + ex.getMessage());
            }

        } catch (HeadlessException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar registro", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Completar los campos obligarios marcados con * " + ex);
            txtCantidad.requestFocus();
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEmpresaVendedora;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnProducto;
    private javax.swing.JComboBox<metodos.MetodosCombo> cbEmpresaVendedora;
    private javax.swing.JComboBox<metodos.MetodosCombo> cbEstablecimiento;
    private javax.swing.JComboBox cbPresentacion;
    private javax.swing.JComboBox<metodos.MetodosCombo> cbProducto;
    private com.toedter.calendar.JDateChooser dcFechaCompra;
    private com.toedter.calendar.JDateChooser dcFechaEntrada;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jbIImagen3;
    private javax.swing.JLabel jbIImagen4;
    private javax.swing.JPanel jpBanner;
    private javax.swing.JPanel jpBotones2;
    private javax.swing.JPanel jpPrincipal;
    private javax.swing.JPanel jpTabla;
    private javax.swing.JLabel lbBanner;
    private javax.swing.JLabel lbPresentacion;
    private javax.swing.JTextArea taObs;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodUsuario;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtNumFactura;
    private javax.swing.JTextField txtPrecioTotal;
    private javax.swing.JTextField txtPrecioUnitario;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables

    /**
     * @param cbEmpresaVendedora the cbEmpresaVendedora to set
     */
    public void setCbEmpresaVendedora(String cbEmpresaVendedora) {
        metodoscombo.setSelectedNombreItem(this.cbEmpresaVendedora, cbEmpresaVendedora);
    }

    /**
     * @param cbEstablecimiento the cbEstablecimiento to set
     */
    public void setCbEstablecimiento(javax.swing.JComboBox<metodos.MetodosCombo> cbEstablecimiento) {
        this.cbEstablecimiento = cbEstablecimiento;
    }

    /**
     * @param cbPresentacion the cbPresentacion to set
     */
    public void setCbPresentacion(String cbPresentacion) {
        this.cbPresentacion.setSelectedItem(cbPresentacion);
    }

    /**
     * @param dcFechaCompra the dcFechaCompra to set
     */
    public void setDcFechaCompra(Date dcFechaCompra) {
        this.dcFechaCompra.setDate(dcFechaCompra);
    }

    /**
     * @param dcFechaEntrada the dcFechaEntrada to set
     */
    public void setDcFechaEntrada(Date dcFechaEntrada) {
        this.dcFechaEntrada.setDate(dcFechaEntrada);
    }

    /**
     * @param lbPresentacion the lbPresentacion to set
     */
    public void setLbPresentacion(javax.swing.JLabel lbPresentacion) {
        this.lbPresentacion = lbPresentacion;
    }

    /**
     * @param taObs the taObs to set
     */
    public void setTaObs(String taObs) {
        this.taObs.setText(taObs);
    }

    /**
     * @param txtCantidad the txtCantidad to set
     */
    public void setTxtCantidad(String txtCantidad) {
        this.txtCantidad.setText(txtCantidad);
    }

    /**
     * @param txtCodigo the txtCodigo to set
     */
    public void setTxtCodigo(String txtCodigo) {
        this.txtCodigo.setText(txtCodigo);
    }

    /**
     * @param txtNumFactura the txtNumFactura to set
     */
    public void setTxtNumFactura(String txtNumFactura) {
        this.txtNumFactura.setText(txtNumFactura);
    }

    /**
     * @param txtPrecioTotal the txtPrecioTotal to set
     */
    public void setTxtPrecioTotal(String txtPrecioTotal) {
        this.txtPrecioTotal.setText(txtPrecioTotal);
    }

    /**
     * @param txtPrecioUnitario the txtPrecioUnitario to set
     */
    public void setTxtPrecioUnitario(String txtPrecioUnitario) {
        this.txtPrecioUnitario.setText(txtPrecioUnitario);
    }

    /**
     * @param txtUsuario the txtUsuario to set
     */
    public void setTxtUsuario(String txtUsuario) {
        this.txtUsuario.setText(txtUsuario);
    }

    /**
     * @param idproducto the cbProducto to set
     */
    public void setCbProducto(int idproducto) {
        MetodosCombo item;
        for (int i = 0; i < this.cbProducto.getItemCount(); i++) {
            item = (MetodosCombo) this.cbProducto.getItemAt(i);
            if (item.getId() == idproducto) {
                this.cbProducto.setSelectedIndex(i);
                break;
            } else {
            }
        }
    }

    /**
     * @return the cbEstablecimiento
     */
    public javax.swing.JComboBox<metodos.MetodosCombo> getCbEstablecimiento() {
        return cbEstablecimiento;
    }

    /**
     * @param txtCodUsuario the txtCodUsuario to set
     */
    public void setTxtCodUsuario(String txtCodUsuario) {
        this.txtCodUsuario.setText(txtCodUsuario);
    }
}
