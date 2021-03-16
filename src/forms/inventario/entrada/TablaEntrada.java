/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms.inventario.entrada;

import conexion.Conexion;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import utilidades.Metodos;
import utilidades.MetodosCombo;

/**
 *
 * @author Lic. Arnaldo Cantero
 */
public class TablaEntrada extends javax.swing.JDialog {

    private Conexion con = new Conexion();

    public TablaEntrada(java.awt.Frame parent, Boolean modal) {
        super(parent, modal);

        initComponents();
        CargarCombos();

        Calendar c2 = new java.util.GregorianCalendar();
        dcFechaFin.setCalendar(c2);

        TablaPrincipalConsulta(txtBuscar.getText());

    }

    //-------------METODOS-------------//
    Metodos metodos = new Metodos();
    MetodosCombo metodoscombo = new MetodosCombo();

    Boolean CombosListo = false;

    private void CargarCombos() {
        metodoscombo.CargarComboConsulta(cbProductorFiltro, "SELECT prod_codigo, CONCAT(prod_nombre, ' ', prod_apellido) FROM productor", 1);
        if (cbProductorFiltro.getItemCount() > 0) {
            cbProductorFiltro.setSelectedIndex(0);
        }
        metodoscombo.CargarComboConsulta(cbEstablecimientoFiltro, "SELECT estab_codigo, estab_descripcion FROM establecimiento WHERE estab_productor = " + metodoscombo.ObtenerIDSelectCombo(cbProductorFiltro), 1);
        if (cbEstablecimientoFiltro.getItemCount() > 0) {
            cbEstablecimientoFiltro.setSelectedIndex(0);
        }
        CombosListo = true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpPrincipal = new javax.swing.JPanel();
        jpBanner = new javax.swing.JPanel();
        lbBanner = new javax.swing.JLabel();
        jpTabla1 = new javax.swing.JPanel();
        lblBuscarCampo3 = new javax.swing.JLabel();
        cbProductorFiltro = new javax.swing.JComboBox();
        cbEstablecimientoFiltro = new javax.swing.JComboBox();
        lblBuscarCampo4 = new javax.swing.JLabel();
        jpTabla4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        scPrincipal3 = new javax.swing.JScrollPane();
        tbPrincipal = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        lblBuscarCampo5 = new javax.swing.JLabel();
        cbCampoBuscar = new javax.swing.JComboBox();
        lbCantRegistros = new javax.swing.JLabel();
        btnNuevaEntrada = new javax.swing.JButton();
        btnModificarEntrada = new javax.swing.JButton();
        jpTabla5 = new javax.swing.JPanel();
        lblBuscarCampo6 = new javax.swing.JLabel();
        lblBuscarCampo7 = new javax.swing.JLabel();
        dcFechaFin = new com.toedter.calendar.JDateChooser();
        dcFechaInicio = new com.toedter.calendar.JDateChooser();
        rb2 = new javax.swing.JRadioButton();
        rb1 = new javax.swing.JRadioButton();
        btnFiltrarporFecha = new javax.swing.JButton();

        setTitle("Ventana de entradas");
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
        lbBanner.setText("Entradas ");
        lbBanner.setMaximumSize(new java.awt.Dimension(1100, 52));
        lbBanner.setMinimumSize(new java.awt.Dimension(1100, 52));
        lbBanner.setPreferredSize(new java.awt.Dimension(1100, 52));

        javax.swing.GroupLayout jpBannerLayout = new javax.swing.GroupLayout(jpBanner);
        jpBanner.setLayout(jpBannerLayout);
        jpBannerLayout.setHorizontalGroup(
            jpBannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBannerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbBanner, javax.swing.GroupLayout.PREFERRED_SIZE, 827, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(163, 163, 163))
        );
        jpBannerLayout.setVerticalGroup(
            jpBannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBannerLayout.createSequentialGroup()
                .addComponent(lbBanner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jpTabla1.setBackground(new java.awt.Color(45, 62, 80));
        jpTabla1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Filtrar por establecimiento   ", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Yu Gothic UI Semibold", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        lblBuscarCampo3.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        lblBuscarCampo3.setForeground(new java.awt.Color(255, 255, 255));
        lblBuscarCampo3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBuscarCampo3.setText(" Productor");
        lblBuscarCampo3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        cbProductorFiltro.setFont(new java.awt.Font("sansserif", 0, 11)); // NOI18N
        cbProductorFiltro.setName("ProductorFiltro"); // NOI18N
        cbProductorFiltro.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbProductorFiltroItemStateChanged(evt);
            }
        });
        cbProductorFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbProductorFiltroActionPerformed(evt);
            }
        });

        cbEstablecimientoFiltro.setFont(new java.awt.Font("sansserif", 0, 11)); // NOI18N
        cbEstablecimientoFiltro.setName("EstablecimientoFiltro"); // NOI18N
        cbEstablecimientoFiltro.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbEstablecimientoFiltroItemStateChanged(evt);
            }
        });

        lblBuscarCampo4.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        lblBuscarCampo4.setForeground(new java.awt.Color(255, 255, 255));
        lblBuscarCampo4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBuscarCampo4.setText(" Establecimiento");
        lblBuscarCampo4.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout jpTabla1Layout = new javax.swing.GroupLayout(jpTabla1);
        jpTabla1.setLayout(jpTabla1Layout);
        jpTabla1Layout.setHorizontalGroup(
            jpTabla1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTabla1Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jpTabla1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbProductorFiltro, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jpTabla1Layout.createSequentialGroup()
                        .addGroup(jpTabla1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBuscarCampo3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblBuscarCampo4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbEstablecimientoFiltro, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(1, 1, 1))))
        );
        jpTabla1Layout.setVerticalGroup(
            jpTabla1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpTabla1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblBuscarCampo3)
                .addGap(1, 1, 1)
                .addComponent(cbProductorFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblBuscarCampo4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(cbEstablecimientoFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jpTabla4.setBackground(new java.awt.Color(45, 62, 80));
        jpTabla4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Iconos20x20/IconoBuscar.png"))); // NOI18N
        jLabel12.setText("  BUSCAR ");

        txtBuscar.setBackground(new java.awt.Color(0, 0, 0));
        txtBuscar.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(0, 204, 204));
        txtBuscar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtBuscar.setCaretColor(new java.awt.Color(0, 204, 204));
        txtBuscar.setDisabledTextColor(new java.awt.Color(0, 204, 204));
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarKeyTyped(evt);
            }
        });

        tbPrincipal.setAutoCreateRowSorter(true);
        tbPrincipal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(153, 153, 153), null, new java.awt.Color(102, 102, 102)));
        tbPrincipal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbPrincipal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbPrincipal.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tbPrincipal.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbPrincipal.setGridColor(new java.awt.Color(0, 153, 204));
        tbPrincipal.setOpaque(false);
        tbPrincipal.setRowHeight(20);
        tbPrincipal.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbPrincipal.getTableHeader().setReorderingAllowed(false);
        tbPrincipal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPrincipalMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbPrincipalMousePressed(evt);
            }
        });
        scPrincipal3.setViewportView(tbPrincipal);

        lblBuscarCampo5.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        lblBuscarCampo5.setForeground(new java.awt.Color(255, 255, 255));
        lblBuscarCampo5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBuscarCampo5.setText("Buscar por:");

        cbCampoBuscar.setName("CampoBuscarPrincipal"); // NOI18N

        lbCantRegistros.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lbCantRegistros.setForeground(new java.awt.Color(204, 204, 0));
        lbCantRegistros.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbCantRegistros.setText("0 Registros encontrados");
        lbCantRegistros.setPreferredSize(new java.awt.Dimension(57, 25));

        btnNuevaEntrada.setText("Nueva Entrada");
        btnNuevaEntrada.setToolTipText("");
        btnNuevaEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaEntradaActionPerformed(evt);
            }
        });

        btnModificarEntrada.setText("Modificar Entrada");
        btnModificarEntrada.setToolTipText("");
        btnModificarEntrada.setEnabled(false);
        btnModificarEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarEntradaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpTabla4Layout = new javax.swing.GroupLayout(jpTabla4);
        jpTabla4.setLayout(jpTabla4Layout);
        jpTabla4Layout.setHorizontalGroup(
            jpTabla4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTabla4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpTabla4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scPrincipal3)
                    .addGroup(jpTabla4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jpTabla4Layout.createSequentialGroup()
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblBuscarCampo5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cbCampoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(jpTabla4Layout.createSequentialGroup()
                            .addComponent(btnNuevaEntrada)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnModificarEntrada)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbCantRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jpTabla4Layout.setVerticalGroup(
            jpTabla4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTabla4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpTabla4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBuscarCampo5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbCampoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(scPrincipal3, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(jpTabla4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevaEntrada)
                    .addComponent(lbCantRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificarEntrada))
                .addGap(7, 7, 7))
        );

        jpTabla5.setBackground(new java.awt.Color(45, 62, 80));
        jpTabla5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Filtrar por fecha  ", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Yu Gothic UI Semibold", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        lblBuscarCampo6.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        lblBuscarCampo6.setForeground(new java.awt.Color(255, 255, 255));
        lblBuscarCampo6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBuscarCampo6.setText("Desde");
        lblBuscarCampo6.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        lblBuscarCampo7.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        lblBuscarCampo7.setForeground(new java.awt.Color(255, 255, 255));
        lblBuscarCampo7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBuscarCampo7.setText("Hasta");
        lblBuscarCampo7.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        dcFechaFin.setToolTipText("");

        dcFechaInicio.setToolTipText("");

        rb2.setForeground(new java.awt.Color(255, 255, 255));
        rb2.setText("Por fecha de compra");
        rb2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rb2ItemStateChanged(evt);
            }
        });
        rb2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb2ActionPerformed(evt);
            }
        });

        rb1.setForeground(new java.awt.Color(255, 255, 255));
        rb1.setSelected(true);
        rb1.setText("Por fecha de entrada");
        rb1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rb1ItemStateChanged(evt);
            }
        });
        rb1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb1ActionPerformed(evt);
            }
        });

        btnFiltrarporFecha.setText("Filtrar");
        btnFiltrarporFecha.setToolTipText("");
        btnFiltrarporFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltrarporFechaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpTabla5Layout = new javax.swing.GroupLayout(jpTabla5);
        jpTabla5.setLayout(jpTabla5Layout);
        jpTabla5Layout.setHorizontalGroup(
            jpTabla5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTabla5Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jpTabla5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpTabla5Layout.createSequentialGroup()
                        .addGroup(jpTabla5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dcFechaInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblBuscarCampo6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpTabla5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblBuscarCampo7, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpTabla5Layout.createSequentialGroup()
                                .addComponent(dcFechaFin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())))
                    .addGroup(jpTabla5Layout.createSequentialGroup()
                        .addComponent(rb1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rb2)
                        .addGap(0, 4, Short.MAX_VALUE))))
            .addGroup(jpTabla5Layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(btnFiltrarporFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jpTabla5Layout.setVerticalGroup(
            jpTabla5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpTabla5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpTabla5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rb1)
                    .addComponent(rb2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpTabla5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBuscarCampo7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBuscarCampo6))
                .addGap(2, 2, 2)
                .addGroup(jpTabla5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dcFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dcFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(btnFiltrarporFecha)
                .addContainerGap())
        );

        javax.swing.GroupLayout jpPrincipalLayout = new javax.swing.GroupLayout(jpPrincipal);
        jpPrincipal.setLayout(jpPrincipalLayout);
        jpPrincipalLayout.setHorizontalGroup(
            jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpBanner, javax.swing.GroupLayout.DEFAULT_SIZE, 1018, Short.MAX_VALUE)
            .addGroup(jpPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jpTabla1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpTabla5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(3, 3, 3)
                .addComponent(jpTabla4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(11, Short.MAX_VALUE))
        );
        jpPrincipalLayout.setVerticalGroup(
            jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPrincipalLayout.createSequentialGroup()
                .addComponent(jpBanner, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpPrincipalLayout.createSequentialGroup()
                        .addComponent(jpTabla1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpTabla5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jpTabla4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        jpBanner.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 1018, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleName("ABMEntrada");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cbEstablecimientoFiltroItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbEstablecimientoFiltroItemStateChanged
        if (cbProductorFiltro.getSelectedIndex() != -1 && CombosListo == true) {
            TablaPrincipalConsulta(txtBuscar.getText());
        }
    }//GEN-LAST:event_cbEstablecimientoFiltroItemStateChanged

    private void cbProductorFiltroItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbProductorFiltroItemStateChanged
        if (cbProductorFiltro.getSelectedIndex() != -1 && CombosListo == true) {
            metodoscombo.CargarComboConsulta(cbEstablecimientoFiltro, "SELECT estab_codigo, estab_descripcion FROM establecimiento WHERE estab_productor = " + metodoscombo.ObtenerIDSelectCombo(cbProductorFiltro), 1);
            if (cbEstablecimientoFiltro.getItemCount() > 0) {
                cbEstablecimientoFiltro.setSelectedIndex(0);
            }
            TablaPrincipalConsulta(txtBuscar.getText());
        }
    }//GEN-LAST:event_cbProductorFiltroItemStateChanged

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        //Convertir a mayuscula
        Character s = evt.getKeyChar();
        if (Character.isLetter(s)) {
            txtBuscar.setText(txtBuscar.getText().toUpperCase());
        }

        //actualiza la tabla conforme a la letra que teclea
        TablaPrincipalConsulta(txtBuscar.getText());
    }//GEN-LAST:event_txtBuscarKeyReleased

    String nombresp = "SP_EntradaConsulta";
    String titlesJtabla[] = {"Codigo", "Producto", "Presentación", "Cantidad", "Precio unitario", "Precio total", "Fecha entrada", "Fecha compra", "N° Factura", "Empresa vendedora", "Usuario", "Obs."};
    String titlesconsulta[] = {"en_codigo", "pro_descripcion", "en_presentacion", "en_cantidad", "en_preciounitario", "en_preciototal", "en_fechaentrada", "en_fechacompra", "en_numfactura", "emv_descripcion", "usu_nombre,usu_apellido", "en_obs"};

    String sentencia;
    String campoconsulta[];
    DefaultTableModel modelotabla;
    DecimalFormat df = new DecimalFormat("#.###");

    private void TablaPrincipalConsulta(String filtro) {//Realiza la consulta de los productos que tenemos en la base de datos
        modelotabla = new DefaultTableModel(null, titlesJtabla);
        //Rellenar el combo campo buscar
        if (cbCampoBuscar.getItemCount() == 0) {//Si combo esta vacio
            for (int i = 0; i < titlesJtabla.length; i++) {
                cbCampoBuscar.addItem(titlesJtabla[i]);
            }
            cbCampoBuscar.addItem("Todos");
            cbCampoBuscar.setSelectedIndex(1);
        }

        String campobuscar = "";
        String tipofecha = "";
        int idestablecimiento = metodoscombo.ObtenerIDSelectCombo(cbEstablecimientoFiltro);
        SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd");
        String fechainicio;
        if (dcFechaInicio.getDate() != null) {
            fechainicio = formatofecha.format(dcFechaInicio.getDate());
        } else {
            fechainicio = "2000-01-01";
        }
        String fechafin = formatofecha.format(dcFechaFin.getDate());

        //Filtro fecha
        if (rb1.isSelected() == true) {
            tipofecha = "en_fechaentrada";
        } else {
            if (rb2.isSelected() == true) {
                tipofecha = "en_fechacompra";
            }
        }

        if (cbEstablecimientoFiltro.getItemCount() > 0) {
            if (cbCampoBuscar.getSelectedItem() == "Todos") {
                campobuscar = campoconsulta[0]; //Cargar el combo campobuscar
                //Cargamos todos los titulos en un String separado por comas
                for (int i = 1; i < campoconsulta.length; i++) {
                    campobuscar = campobuscar + ", " + campoconsulta[i];
                }
            } else {
                campobuscar = titlesconsulta[cbCampoBuscar.getSelectedIndex()];
            }

            sentencia = "CALL " + nombresp + " ('" + campobuscar
                    + "', '" + filtro
                    + "', '" + idestablecimiento
                    + "', '" + tipofecha
                    + "', '" + fechainicio
                    + "', '" + fechafin + "');";
            System.out.println("sentencia filtro tabla BD: " + sentencia);

            Connection connection;
            Statement st;
            ResultSet rs;
            SimpleDateFormat formatofechanormal = new SimpleDateFormat("dd/MM/yyyy");  //25/08/2015
            try {
                connection = Conexion.ConectarBasedeDatos();
                st = connection.createStatement();
                rs = st.executeQuery(sentencia);
                ResultSetMetaData mdrs = rs.getMetaData();
                int numColumns = mdrs.getColumnCount();
                Object[] registro = new Object[numColumns]; //el numero es la cantidad de columnas del rs
                int CantRegistro = 0;
                while (rs.next()) {
                    registro[0] = (rs.getString("en_codigo"));
                    registro[1] = (rs.getString("pro_descripcion"));
                    registro[2] = (rs.getString("en_presentacion") + " " + EncontrarEstadoProducto(rs));
                    registro[3] = (rs.getString("en_cantidad"));
                    registro[4] = (rs.getString("en_preciounitario") + " $");
                    registro[5] = (rs.getString("en_preciototal") + " $");
                    registro[6] = (formatofechanormal.format(rs.getDate("en_fechaentrada")));
                    registro[7] = (formatofechanormal.format(rs.getDate("en_fechacompra")));
                    registro[8] = (rs.getString("en_numfactura"));
                    registro[9] = (rs.getString("emv_descripcion"));
                    registro[10] = (rs.getString("usu_nombre") + " " + rs.getString("usu_apellido"));
                    registro[11] = (rs.getString("en_obs"));
                    modelotabla.addRow(registro);//agrega el registro a la tabla 
                    CantRegistro = CantRegistro + 1;
                }
                tbPrincipal.setModel(modelotabla);//asigna a la tabla el modelo creado
                lbCantRegistros.setText(CantRegistro + " Registros encontrados");
                connection.close();
                st.close();
                rs.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
        metodos.AnchuraColumna(tbPrincipal);
    }

    private String EncontrarEstadoProducto(ResultSet rs) {
        String estado = "No encontrado";
        try {
            sentencia = "SELECT es_descripcion FROM producto, formulacion, estado "
                    + "WHERE pro_formulacion = for_codigo AND for_estado = es_codigo AND pro_codigo = '" + rs.getString("pro_codigo") + "'";
            con = con.ObtenerRSSentencia(sentencia);
            con.getResultSet().next();

            estado = con.getResultSet().getString("es_descripcion");
            if (estado.equals("ml/Ha")) {
                estado = ("Lts");
            } else {
                if (estado.equals("gr/Ha")) {
                    estado = ("Kgs");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al verificar estado de producto");
            Logger.getLogger(AMEntrada.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estado;
    }

    private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyTyped

    }//GEN-LAST:event_txtBuscarKeyTyped

    private void rb1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rb1ItemStateChanged

    }//GEN-LAST:event_rb1ItemStateChanged

    private void rb2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rb2ItemStateChanged

    }//GEN-LAST:event_rb2ItemStateChanged

    private void rb1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb1ActionPerformed
        rb2.setSelected(false);
    }//GEN-LAST:event_rb1ActionPerformed

    private void rb2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb2ActionPerformed
        rb1.setSelected(false);
    }//GEN-LAST:event_rb2ActionPerformed

    private void cbProductorFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbProductorFiltroActionPerformed

    }//GEN-LAST:event_cbProductorFiltroActionPerformed

    private void btnFiltrarporFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarporFechaActionPerformed
        if (dcFechaInicio.getDate() != null && dcFechaFin.getDate() != null) {
            TablaPrincipalConsulta(txtBuscar.getText());
        } else {
            JOptionPane.showMessageDialog(null, "Fecha de inicio y/o fin invalidas, verifique las mismas", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnFiltrarporFechaActionPerformed

    private void btnNuevaEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaEntradaActionPerformed
        AMEntrada amentrada = new AMEntrada(this, true);
        metodoscombo.SetSelectedNombreItem(amentrada.getCbEstablecimiento(), cbEstablecimientoFiltro.getSelectedItem().toString());

        amentrada.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                TablaPrincipalConsulta(txtBuscar.getText());
            }
        });

        amentrada.setVisible(true);
    }//GEN-LAST:event_btnNuevaEntradaActionPerformed

    private void btnModificarEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarEntradaActionPerformed
        AMEntrada amentrada = new AMEntrada(this, true);

        amentrada.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                TablaPrincipalConsulta(txtBuscar.getText());
            }
        });

        try {
            SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy/MM/dd");
            String codigo = tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 0).toString();
            String presentacion = tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 2).toString();
            String cantidad = tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 3).toString();
            String preciounitario = tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 4).toString();
            String preciototal = tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 5).toString();
            Date fechaentrada = formatofecha.parse(formatofecha.format(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 6).toString()));
            Date fechacompra = formatofecha.parse(formatofecha.format(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 7).toString()));
            String numfactura = tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 8).toString();
            String empresavendedora = tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 9).toString();
            String usuario = tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 10).toString();
            String obs = tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 11).toString();

            amentrada.setTxtCodigo(codigo);

            con = con.ObtenerRSSentencia("SELECT en_producto, en_usuario FROM entrada WHERE en_codigo = " + codigo);
            con.getResultSet().next();
            amentrada.setCbProducto(con.getResultSet().getInt("en_producto"));

            amentrada.setDcFechaEntrada(fechaentrada);
            amentrada.setDcFechaCompra(fechacompra);
            amentrada.setCbPresentacion(presentacion.replace(".0 Lts", "").replace(".0 Kgs", ""));
            amentrada.setTxtCantidad(cantidad);
            amentrada.setTxtPrecioUnitario(preciounitario.replace(" $", ""));
            amentrada.setTxtPrecioTotal(preciototal.replace(" $", ""));
            amentrada.setTxtNumFactura(numfactura);
            amentrada.setCbEmpresaVendedora(empresavendedora);
            amentrada.setTxtCodUsuario(con.getResultSet().getString("en_usuario"));
            amentrada.setTxtUsuario(usuario);
            amentrada.setTaObs(obs);

            con.DesconectarBasedeDatos();
        } catch (SQLException e) {
            System.out.println("Error al buscar producto");
        } catch (ParseException ex) {
            Logger.getLogger(TablaEntrada.class.getName()).log(Level.SEVERE, null, ex);
        }

        amentrada.setVisible(true);
    }//GEN-LAST:event_btnModificarEntradaActionPerformed

    private void tbPrincipalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPrincipalMouseClicked
        if (tbPrincipal.getSelectedRow() > 0) {
            btnModificarEntrada.setEnabled(true);
        }
    }//GEN-LAST:event_tbPrincipalMouseClicked

    private void tbPrincipalMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPrincipalMousePressed
        if (tbPrincipal.getSelectedRow() > 0) {
            btnModificarEntrada.setEnabled(true);
        }
    }//GEN-LAST:event_tbPrincipalMousePressed

    DecimalFormat formatodecimal = new DecimalFormat("#.##");

    public void SiguienteFoco(KeyEvent evt) {
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            ((JComponent) evt.getSource()).transferFocus();//Con esta parte transfieres el foco al siguiente campo sea un Jtextfield, Jpasswordfield, boton, etc..
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFiltrarporFecha;
    private javax.swing.JButton btnModificarEntrada;
    private javax.swing.JButton btnNuevaEntrada;
    private javax.swing.JComboBox cbCampoBuscar;
    private javax.swing.JComboBox cbEstablecimientoFiltro;
    private javax.swing.JComboBox cbProductorFiltro;
    private com.toedter.calendar.JDateChooser dcFechaFin;
    private com.toedter.calendar.JDateChooser dcFechaInicio;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JPanel jpBanner;
    private javax.swing.JPanel jpPrincipal;
    private javax.swing.JPanel jpTabla1;
    private javax.swing.JPanel jpTabla4;
    private javax.swing.JPanel jpTabla5;
    private javax.swing.JLabel lbBanner;
    private javax.swing.JLabel lbCantRegistros;
    private javax.swing.JLabel lblBuscarCampo3;
    private javax.swing.JLabel lblBuscarCampo4;
    private javax.swing.JLabel lblBuscarCampo5;
    private javax.swing.JLabel lblBuscarCampo6;
    private javax.swing.JLabel lblBuscarCampo7;
    private javax.swing.JRadioButton rb1;
    private javax.swing.JRadioButton rb2;
    private javax.swing.JScrollPane scPrincipal3;
    private javax.swing.JTable tbPrincipal;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
