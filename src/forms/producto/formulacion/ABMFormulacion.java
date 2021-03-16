/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms.producto.formulacion;

import conexion.Conexion;
import forms.producto.ABMProducto;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import utilidades.Metodos;
import utilidades.MetodosCombo;

/**
 *
 * @author Arnaldo Cantero
 */
public class ABMFormulacion extends JDialog {

    private ABMProducto abmproducto; //Para que tenga relacion con su form padre
    Metodos metodos = new Metodos();
    MetodosCombo metodoscombo = new MetodosCombo();

    public ABMFormulacion(ABMProducto abmproducto, java.awt.Frame parent, Boolean modal) {
        super(parent, modal);
        this.abmproducto = abmproducto;
        initComponents();
        TablaConsulta(""); //Trae todos los registros
        //Carga los combobox con las consultas

        metodoscombo.CargarComboConsulta(cbEstado, "SELECT es_codigo, es_descripcion FROM estado ORDER BY es_descripcion",1);
        Limpiar();
        txtBuscar.requestFocus();

        //Shortcuts 
        btnNuevo.setMnemonic(KeyEvent.VK_N); //ALT+N
        btnModificar.setMnemonic(KeyEvent.VK_M); //ALT+M
        btnEliminar.setMnemonic(KeyEvent.VK_E); //ALT+E

    }

    public void TablaConsulta(String filtro) {//Realiza la consulta de los productos que tenemos en la base de datos
        String nombresp = "SP_FormulacionConsulta";
        String titlesJtabla[] = {"Codigo", "Descripción", "Abreviatura", "Estado"};
        String titlesconsulta[] = {"for_codigo", "for_descripcion", "for_abreviatura", "es_descripcion"};

        metodos.ConsultaFiltroTablaBD(tbTabla, titlesJtabla, titlesconsulta, nombresp, filtro, cbCampoBuscar);
        metodos.AnchuraColumna(tbTabla);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpPrincipal = new javax.swing.JPanel();
        jpBanner = new javax.swing.JPanel();
        lbBanner = new javax.swing.JLabel();
        jpTabla = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbTabla = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        lblBuscarCampo = new javax.swing.JLabel();
        cbCampoBuscar = new javax.swing.JComboBox();
        jpBotones = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jtpEdicion = new javax.swing.JTabbedPane();
        jpEdicion = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtAbreviatura = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cbEstado = new javax.swing.JComboBox<>();
        jpBotones2 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setTitle("Ventana Formulaciones");
        setBackground(new java.awt.Color(45, 62, 80));
        setResizable(false);

        jpPrincipal.setBackground(new java.awt.Color(45, 62, 80));
        jpPrincipal.setPreferredSize(new java.awt.Dimension(1580, 478));

        jpBanner.setPreferredSize(new java.awt.Dimension(1000, 82));

        lbBanner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/forms/banners/bannertabla.jpg"))); // NOI18N
        lbBanner.setMaximumSize(new java.awt.Dimension(1100, 52));
        lbBanner.setMinimumSize(new java.awt.Dimension(1100, 52));
        lbBanner.setPreferredSize(new java.awt.Dimension(1100, 52));

        javax.swing.GroupLayout jpBannerLayout = new javax.swing.GroupLayout(jpBanner);
        jpBanner.setLayout(jpBannerLayout);
        jpBannerLayout.setHorizontalGroup(
            jpBannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbBanner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpBannerLayout.setVerticalGroup(
            jpBannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBannerLayout.createSequentialGroup()
                .addComponent(lbBanner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jpTabla.setBackground(new java.awt.Color(45, 62, 80));
        jpTabla.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Formulación", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Sitka Subheading", 1, 28), new java.awt.Color(0, 204, 204))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/iconos40x40/IconoBuscar.png"))); // NOI18N
        jLabel10.setText("  BUSCAR ");
        jLabel10.setBorder(null);

        txtBuscar.setBackground(new java.awt.Color(0, 0, 0));
        txtBuscar.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(0, 204, 204));
        txtBuscar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtBuscar.setCaretColor(new java.awt.Color(0, 204, 204));
        txtBuscar.setDisabledTextColor(new java.awt.Color(0, 204, 204));
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        tbTabla.setAutoCreateRowSorter(true);
        tbTabla.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(153, 153, 153), null, new java.awt.Color(102, 102, 102)));
        tbTabla.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbTabla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tbTabla.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbTabla.setGridColor(new java.awt.Color(0, 153, 204));
        tbTabla.setOpaque(false);
        tbTabla.setRowHeight(20);
        tbTabla.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbTabla.getTableHeader().setReorderingAllowed(false);
        tbTabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTablaMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbTablaMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbTabla);

        lblBuscarCampo.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        lblBuscarCampo.setForeground(new java.awt.Color(255, 255, 255));
        lblBuscarCampo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBuscarCampo.setText("Buscar por:");

        javax.swing.GroupLayout jpTablaLayout = new javax.swing.GroupLayout(jpTabla);
        jpTabla.setLayout(jpTablaLayout);
        jpTablaLayout.setHorizontalGroup(
            jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTablaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 717, Short.MAX_VALUE)
                    .addGroup(jpTablaLayout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblBuscarCampo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbCampoBuscar, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpTablaLayout.setVerticalGroup(
            jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTablaLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbCampoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblBuscarCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        jpBotones.setBackground(new java.awt.Color(45, 62, 80));
        jpBotones.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jpBotones.setPreferredSize(new java.awt.Dimension(100, 50));

        btnNuevo.setBackground(new java.awt.Color(14, 154, 153));
        btnNuevo.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnNuevo.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/iconos40x40/IconoNuevo.png"))); // NOI18N
        btnNuevo.setText("NUEVO");
        btnNuevo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnModificar.setBackground(new java.awt.Color(14, 154, 153));
        btnModificar.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnModificar.setForeground(new java.awt.Color(255, 255, 255));
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/iconos40x40/IconoModifcar.png"))); // NOI18N
        btnModificar.setText("MODIFICAR");
        btnModificar.setEnabled(false);
        btnModificar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(14, 154, 153));
        btnEliminar.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/iconos40x40/IconoEliminar.png"))); // NOI18N
        btnEliminar.setText("ELIMINAR");
        btnEliminar.setEnabled(false);
        btnEliminar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpBotonesLayout = new javax.swing.GroupLayout(jpBotones);
        jpBotones.setLayout(jpBotonesLayout);
        jpBotonesLayout.setHorizontalGroup(
            jpBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
            .addComponent(btnNuevo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpBotonesLayout.setVerticalGroup(
            jpBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBotonesLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                .addGap(15, 15, 15)
                .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                .addGap(15, 15, 15)
                .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                .addGap(29, 29, 29))
        );

        jtpEdicion.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jtpEdicion.setName(""); // NOI18N

        jpEdicion.setBackground(new java.awt.Color(45, 62, 80));
        jpEdicion.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel5.setFont(new java.awt.Font("Sitka Subheading", 1, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Código:");

        txtCodigo.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtCodigo.setEnabled(false);
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoKeyTyped(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Sitka Subheading", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Descripción*:");

        txtDescripcion.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtDescripcion.setEnabled(false);
        txtDescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionActionPerformed(evt);
            }
        });
        txtDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDescripcionKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescripcionKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Sitka Subheading", 1, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Abreviatura:");

        txtAbreviatura.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtAbreviatura.setEnabled(false);
        txtAbreviatura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAbreviaturaActionPerformed(evt);
            }
        });
        txtAbreviatura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAbreviaturaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAbreviaturaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAbreviaturaKeyTyped(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Sitka Subheading", 1, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Estado*:");

        cbEstado.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        cbEstado.setEnabled(false);

        javax.swing.GroupLayout jpEdicionLayout = new javax.swing.GroupLayout(jpEdicion);
        jpEdicion.setLayout(jpEdicionLayout);
        jpEdicionLayout.setHorizontalGroup(
            jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpEdicionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpEdicionLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpEdicionLayout.createSequentialGroup()
                        .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jpEdicionLayout.createSequentialGroup()
                        .addComponent(txtDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAbreviatura, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))))
        );
        jpEdicionLayout.setVerticalGroup(
            jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpEdicionLayout.createSequentialGroup()
                .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpEdicionLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAbreviatura, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.CENTER, jpEdicionLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(5, 5, 5)
                .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jtpEdicion.addTab("Edición", jpEdicion);

        jpBotones2.setBackground(new java.awt.Color(45, 62, 80));
        jpBotones2.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        btnGuardar.setBackground(new java.awt.Color(0, 153, 255));
        btnGuardar.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Iconos20x20/IconoGuardar.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setToolTipText("Inserta el nuevo registro");
        btnGuardar.setEnabled(false);
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
        btnCancelar.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Iconos20x20/IconoCancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setToolTipText("Cancela la acción");
        btnCancelar.setEnabled(false);
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
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(btnCancelar)
                .addContainerGap())
        );
        jpBotones2Layout.setVerticalGroup(
            jpBotones2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBotones2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpBotones2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jpPrincipalLayout = new javax.swing.GroupLayout(jpPrincipal);
        jpPrincipal.setLayout(jpPrincipalLayout);
        jpPrincipalLayout.setHorizontalGroup(
            jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpBanner, javax.swing.GroupLayout.DEFAULT_SIZE, 1100, Short.MAX_VALUE)
            .addGroup(jpPrincipalLayout.createSequentialGroup()
                .addGroup(jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jtpEdicion, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jpPrincipalLayout.createSequentialGroup()
                            .addComponent(jpTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jpBotones, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jpPrincipalLayout.createSequentialGroup()
                        .addGap(313, 313, 313)
                        .addComponent(jpBotones2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpPrincipalLayout.setVerticalGroup(
            jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPrincipalLayout.createSequentialGroup()
                .addComponent(jpBanner, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpTabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpPrincipalLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jpBotones, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtpEdicion, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpBotones2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        jpBanner.getAccessibleContext().setAccessibleName("");
        jtpEdicion.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 918, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        getAccessibleContext().setAccessibleName("Inventario");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        //actualiza la tabla conforme a la letra que teclea
        TablaConsulta(txtBuscar.getText());

        //Convertir a mayuscula
        Character s = evt.getKeyChar();
        if (Character.isLetter(s)) {
            txtBuscar.setText(txtBuscar.getText().toUpperCase());
        }

        if (tbTabla.getSelectedRow() >= 0) {
            Limpiar();
        }
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void tbTablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTablaMouseClicked
        if (tbTabla.isEnabled() == true) {
            btnModificar.setEnabled(true);
            btnEliminar.setEnabled(true);
        }
    }//GEN-LAST:event_tbTablaMouseClicked

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void txtCodigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyReleased
        //Limitar cantidad de caracteres
        int longitud = txtCodigo.getText().length();
        if (longitud > 40) {
            txtCodigo.setText(txtCodigo.getText().substring(0, 41));
        }

        //txtPrecioTotal.setText("" + Integer.parseInt(txtDescripcion.getText()) * Integer.parseInt(txtDescripcion.getText()));
    }//GEN-LAST:event_txtCodigoKeyReleased

    private void txtCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyTyped
        // Verificar si la tecla pulsada no es un digito
        char caracter = evt.getKeyChar();
        if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
            evt.consume(); // ignorar el evento de teclado
        }
    }//GEN-LAST:event_txtCodigoKeyTyped

    private void txtDescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionActionPerformed

    private void txtDescripcionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionKeyReleased
        //Limitar cantidad de caracteres
        int longitud = txtDescripcion.getText().length();
        if (longitud > 70) {
            txtDescripcion.setText(txtDescripcion.getText().substring(0, 71));
        }

        //Convertir a mayuscula
        Character s = evt.getKeyChar();
        if (Character.isLetter(s)) {
            txtDescripcion.setText(txtDescripcion.getText().toUpperCase());
        }
    }//GEN-LAST:event_txtDescripcionKeyReleased

    private void txtDescripcionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionKeyTyped

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (txtCodigo.getText().equals("")) {//Si es nuevo
            RegistroNuevo();
        } else { //Si es modificar
            RegistroModificar();
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        ModoEdicion(false);
        Limpiar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        ModoEdicion(true);
        Limpiar();
        txtDescripcion.requestFocus();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        txtCodigo.setText(tbTabla.getValueAt(tbTabla.getSelectedRow(), 0).toString());
        txtDescripcion.setText(tbTabla.getValueAt(tbTabla.getSelectedRow(), 1).toString());
        txtAbreviatura.setText(tbTabla.getValueAt(tbTabla.getSelectedRow(), 2).toString());
        metodoscombo.SetSelectedNombreItem(cbEstado, tbTabla.getValueAt(tbTabla.getSelectedRow(), 3).toString());//COmpara y trae el que coincide con la celda

        ModoEdicion(true);
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        RegistroEliminar();
        ModoEdicion(false);
        Limpiar();


    }//GEN-LAST:event_btnEliminarActionPerformed

    private void txtDescripcionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionKeyPressed
        SiguienteFoco(evt);
    }//GEN-LAST:event_txtDescripcionKeyPressed

    private void txtCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyPressed
        SiguienteFoco(evt);
    }//GEN-LAST:event_txtCodigoKeyPressed

    private void btnGuardarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnGuardarKeyPressed

    }//GEN-LAST:event_btnGuardarKeyPressed

    private void txtAbreviaturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAbreviaturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAbreviaturaActionPerformed

    private void txtAbreviaturaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAbreviaturaKeyPressed

    }//GEN-LAST:event_txtAbreviaturaKeyPressed

    private void txtAbreviaturaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAbreviaturaKeyReleased
        //Convertir a mayuscula
        Character s = evt.getKeyChar();
        if (Character.isLetter(s)) {
            txtAbreviatura.setText(txtAbreviatura.getText().toUpperCase());
        }
    }//GEN-LAST:event_txtAbreviaturaKeyReleased

    private void txtAbreviaturaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAbreviaturaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAbreviaturaKeyTyped

    private void tbTablaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTablaMousePressed
        if (tbTabla.isEnabled() == true) {
            btnModificar.setEnabled(true);
            btnEliminar.setEnabled(true);
        }
    }//GEN-LAST:event_tbTablaMousePressed

    public void SiguienteFoco(KeyEvent evt) {
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            ((JComponent) evt.getSource()).transferFocus();//Con esta parte transfieres el foco al siguiente campo sea un Jtextfield, Jpasswordfield, boton, etc..
        }
    }

    private void ModoEdicion(boolean valor) {
        txtBuscar.setEnabled(!valor);
        tbTabla.setEnabled(!valor);
        txtDescripcion.setEnabled(valor);
        txtAbreviatura.setEnabled(valor);
        cbEstado.setEnabled(valor);
        btnNuevo.setEnabled(!valor);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnGuardar.setEnabled(valor);
        btnCancelar.setEnabled(valor);

        txtDescripcion.requestFocus();
    }

    private void Limpiar() {
        txtCodigo.setText("");
        txtDescripcion.setText("");
        txtAbreviatura.setText("");
        cbEstado.setSelectedIndex(-1);
        TablaConsulta(""); //Trae todos los registros
        txtBuscar.setText("");

        txtBuscar.requestFocus();
    }

    public void RegistroNuevo() {
        try {
            if (!txtDescripcion.getText().trim().isEmpty()
                    && cbEstado.getSelectedIndex() != -1) {
                String descri = txtDescripcion.getText();
                String abreviatura = txtAbreviatura.getText();

                int idestado = cbEstado.getItemAt(cbEstado.getSelectedIndex()).getCodigo();
                int confirmado = JOptionPane.showConfirmDialog(null, "¿Esta seguro crear este nuevo registro?", "Confirmación", JOptionPane.YES_OPTION);

                if (JOptionPane.YES_OPTION == confirmado) {
                    //REGISTRAR NUEVO
                    try {
                        Connection con = Conexion.ConectarBasedeDatos();
                        String sentencia = "CALL SP_FormulacionAlta ('" + descri + "', '" + abreviatura + "','" + idestado + "')";
                        System.out.println("Insertar registro: " + sentencia);
                        Statement st = (Statement) con.createStatement();
                        st.executeUpdate(sentencia);
                        st.close();
                        JOptionPane.showMessageDialog(this, "Se agrego correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                        ModoEdicion(false);
                        Limpiar();
                    } catch (HeadlessException ex) {
                        JOptionPane.showMessageDialog(this, "Ocurrió un Error " + ex.getMessage());
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Ocurrió un Error " + ex.getMessage());
                    }
                } else {
                    System.out.println("No se guardo el registro");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Completa los campos obligatorios marcados con *", "Error", JOptionPane.ERROR_MESSAGE);
                txtDescripcion.requestFocus();
            }

        } catch (HeadlessException ex) {
            JOptionPane.showMessageDialog(null, "Completar los campos obligarios marcados con * ", "Advertencia", JOptionPane.WARNING_MESSAGE);
            System.out.println("Completar los campos obligarios marcados con * " + ex);
            txtDescripcion.requestFocus();
        }
    }

    public void RegistroModificar() {
        //guarda los datos que se han modificado en los campos
        Connection con = Conexion.ConectarBasedeDatos();
        String sentencia;
        String cod = txtCodigo.getText();
        String descri = txtDescripcion.getText();
        String abreviatura = txtAbreviatura.getText();
        int idestado = cbEstado.getItemAt(cbEstado.getSelectedIndex()).getCodigo();

        if (!cod.isEmpty() && !descri.isEmpty()
                && !txtAbreviatura.getText().trim().isEmpty()
                && !cbEstado.getSelectedItem().toString().isEmpty()) {
            int confirmado = JOptionPane.showConfirmDialog(null, "¿Esta seguro de modificar este registro?", "Confirmación", JOptionPane.YES_OPTION);
            if (JOptionPane.YES_OPTION == confirmado) {

                sentencia = "CALL SP_FormulacionModificar(" + cod + ",'" + descri + "','" + abreviatura + "','" + idestado + "')";
                System.out.println("Actualizar registro: " + sentencia);
                getToolkit().beep();
                JOptionPane.showMessageDialog(null, "Registro modificado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);

                try {
                    PreparedStatement pst = con.prepareStatement(sentencia);
                    pst.executeUpdate();
                    pst.close();
                } catch (SQLException ex) {
                    System.out.println("Error al modificar registro " + ex);
                }
                ModoEdicion(false);
                Limpiar();
            } else {
                System.out.println("No se modifico registro");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No dejar vacio ningun campo", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void RegistroEliminar() {
        int filasel;
        String codigo;
        try {
            filasel = tbTabla.getSelectedRow();
            if (filasel == -1) {
                JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna fila", "Advertencia", JOptionPane.WARNING_MESSAGE);
                txtBuscar.requestFocus();
            } else {
                int confirmado = javax.swing.JOptionPane.showConfirmDialog(null, "¿Realmente desea eliminar este registro?", "Confirmación", JOptionPane.YES_OPTION);
                if (confirmado == JOptionPane.YES_OPTION) {
                    codigo = (String) tbTabla.getModel().getValueAt(filasel, 0);

                    Connection con = Conexion.ConectarBasedeDatos();
                    String sentence;
                    sentence = "CALL SP_FormulacionEliminar(" + codigo + ")";

                    try {
                        PreparedStatement pst = con.prepareStatement(sentence);
                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Registro eliminado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    }
                } else {
                    ModoEdicion(false);
                    Limpiar();
                    JOptionPane.showMessageDialog(null, "Cancelado correctamente", "Información", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (HeadlessException e) {
            System.out.println("Error al intentar eliminar registro" + e);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox cbCampoBuscar;
    private javax.swing.JComboBox<MetodosCombo> cbEstado;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel jpBanner;
    private javax.swing.JPanel jpBotones;
    private javax.swing.JPanel jpBotones2;
    private javax.swing.JPanel jpEdicion;
    private javax.swing.JPanel jpPrincipal;
    private javax.swing.JPanel jpTabla;
    private javax.swing.JTabbedPane jtpEdicion;
    private javax.swing.JLabel lbBanner;
    private javax.swing.JLabel lblBuscarCampo;
    private javax.swing.JTable tbTabla;
    private javax.swing.JTextField txtAbreviatura;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescripcion;
    // End of variables declaration//GEN-END:variables
}
