/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms.zafra.establecimiento;

import conexion.Conexion;
import forms.producto.ABMProductoViejo;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import metodos.Metodos;
import metodos.MetodosCombo;
import metodos.MetodosImagen;
import metodos.VistaCompleta;

/**
 *
 * @author Arnaldo Cantero
 */
public final class ABMEstablecimiento extends javax.swing.JDialog {

    private MetodosCombo metodoscombo = new MetodosCombo();

    public ABMEstablecimiento(java.awt.Frame parent, Boolean modal) {
        super(parent, modal);

        initComponents();
        TablaPrincipalConsulta(txtBuscar.getText()); //Trae todos los registros
        txtBuscar.requestFocus();

        lbEstabSelect.setVisible(false);
        lbEstabSelectNombre.setVisible(false);
        CargarCombos();

        //Shortcuts 
        btnNuevo.setMnemonic(KeyEvent.VK_N); //ALT+N
        btnModificar.setMnemonic(KeyEvent.VK_M); //ALT+M
        btnEliminar.setMnemonic(KeyEvent.VK_E); //ALT+E
    }

    private void CargarCombos() {
        cbProductor.removeAllItems();
        cbDepartamento.removeAllItems();
        cbDistrito.removeAllItems();

        metodoscombo.CargarComboBox(cbProductor, "SELECT prod_codigo, CONCAT(prod_nombre, ' ', prod_apellido) FROM productor");
        metodoscombo.CargarComboBox(cbDepartamento, "SELECT dep_codigo, dep_descripcion FROM departamento");
        metodoscombo.CargarComboBox(cbDistrito, "SELECT dis_codigo, dis_descripcion FROM distrito WHERE dis_departamento = " + metodoscombo.ObtenerIdComboBox(cbDepartamento));
    }

    Metodos metodos = new Metodos();

    public void TablaPrincipalConsulta(String filtro) {//Realiza la consulta de los productos que tenemos en la base de datos
        String nombresp = "SP_EstablecimientoConsulta";
        String titlesJtabla[] = {"Codigo", "Denominación", "Productor", "Departamento", "Distrito", "Localidad", "Coordenada X", "Coordenada Y"};
        String titlesconsulta[] = {"estab_codigo", "estab_descripcion", "CONCAT(prod_nombre, ' ', prod_apellido)", "dep_descripcion", "dis_descripcion", "estab_localidad", "estab_x", "estab_y"};

        metodos.ConsultaFiltroTablaBD(tbPrincipal, titlesJtabla, titlesconsulta, nombresp, filtro, cbCampoBuscar);
        metodos.AnchuraColumna(tbPrincipal);
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbSumNumParcelas22 = new javax.swing.JPanel();
        jpBanner = new javax.swing.JPanel();
        lbBanner = new javax.swing.JLabel();
        jpTabla = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPrincipal = new javax.swing.JTable(){
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
        btnReporte = new javax.swing.JButton();
        jtpEdicion = new javax.swing.JTabbedPane();
        jpDatosGenerales = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        jbIImagen = new javax.swing.JLabel();
        cbProductor = new javax.swing.JComboBox<>();
        btnProductor = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        lbSumNumParcelas1 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lbSumExParcelas1 = new javax.swing.JLabel();
        jpUbicacion = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtX = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtLocalidad = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtY = new javax.swing.JTextField();
        jbIImagen1 = new javax.swing.JLabel();
        cbDepartamento = new javax.swing.JComboBox<>();
        cbDistrito = new javax.swing.JComboBox<>();
        jbIImagen2 = new javax.swing.JLabel();
        lbImagen = new javax.swing.JLabel();
        btnCargarImagen = new javax.swing.JButton();
        btnEliminarImagen = new javax.swing.JButton();
        btnPantallaCompleta = new javax.swing.JButton();
        jpParcelas = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        lbSumNumParcelas2 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lbSumExParcelas2 = new javax.swing.JLabel();
        scParcelas = new javax.swing.JScrollPane();
        tbParcelas = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jpBotones2 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lbEstabSelect = new javax.swing.JLabel();
        lbEstabSelectNombre = new javax.swing.JLabel();

        setTitle("Ventana Establecimientos");
        setBackground(new java.awt.Color(45, 62, 80));
        setResizable(false);

        lbSumNumParcelas22.setBackground(new java.awt.Color(45, 62, 80));
        lbSumNumParcelas22.setPreferredSize(new java.awt.Dimension(1580, 478));

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
        jpTabla.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Establecimientos", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Sitka Subheading", 1, 28), new java.awt.Color(0, 204, 204))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Iconos20x20/IconoBuscar.png"))); // NOI18N
        jLabel10.setText("  BUSCAR ");

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
        jScrollPane1.setViewportView(tbPrincipal);

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
                .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(jpTablaLayout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblBuscarCampo)
                        .addGap(4, 4, 4)
                        .addComponent(cbCampoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jpTablaLayout.setVerticalGroup(
            jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTablaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBuscarCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbCampoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                .addContainerGap())
        );

        jpBotones.setBackground(new java.awt.Color(45, 62, 80));
        jpBotones.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jpBotones.setPreferredSize(new java.awt.Dimension(100, 50));

        btnNuevo.setBackground(new java.awt.Color(14, 154, 153));
        btnNuevo.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnNuevo.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/iconos40x40/IconoNuevo.png"))); // NOI18N
        btnNuevo.setText("NUEVO");
        btnNuevo.setToolTipText("Le da la posibilidad de incorporar un nuevo registro a la Base de Datos");
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
        btnModificar.setToolTipText("Le da la posibilidad de modificar el registro seleccionado");
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
        btnEliminar.setToolTipText("Elimina el registro seleccionado");
        btnEliminar.setEnabled(false);
        btnEliminar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnReporte.setBackground(new java.awt.Color(14, 154, 153));
        btnReporte.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnReporte.setForeground(new java.awt.Color(255, 255, 255));
        btnReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/iconos40x40/IconoReporte.png"))); // NOI18N
        btnReporte.setText("REPORTE");
        btnReporte.setToolTipText("Genera un reporte de los registros de la tabla");
        btnReporte.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpBotonesLayout = new javax.swing.GroupLayout(jpBotones);
        jpBotones.setLayout(jpBotonesLayout);
        jpBotonesLayout.setHorizontalGroup(
            jpBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
            .addComponent(btnNuevo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnReporte, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpBotonesLayout.setVerticalGroup(
            jpBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBotonesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jtpEdicion.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jtpEdicion.setName(""); // NOI18N

        jpDatosGenerales.setBackground(new java.awt.Color(45, 62, 80));
        jpDatosGenerales.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Código:");

        txtCodigo.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtCodigo.setEnabled(false);

        jLabel7.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Denominación*:");

        txtDescripcion.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtDescripcion.setEnabled(false);
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

        jbIImagen.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jbIImagen.setForeground(new java.awt.Color(255, 255, 255));
        jbIImagen.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jbIImagen.setText("Productor/Repr. legal*:");

        cbProductor.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        cbProductor.setEnabled(false);
        cbProductor.setMinimumSize(new java.awt.Dimension(55, 31));
        cbProductor.setPreferredSize(new java.awt.Dimension(55, 31));
        cbProductor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbProductorActionPerformed(evt);
            }
        });

        btnProductor.setFont(new java.awt.Font("Adobe Hebrew", 1, 18)); // NOI18N
        btnProductor.setText("...");
        btnProductor.setToolTipText("Ir a la ventana de Tipos de productos");
        btnProductor.setEnabled(false);
        btnProductor.setPreferredSize(new java.awt.Dimension(35, 31));
        btnProductor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductorActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Extensión:");

        lbSumNumParcelas1.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        lbSumNumParcelas1.setForeground(new java.awt.Color(255, 255, 0));
        lbSumNumParcelas1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbSumNumParcelas1.setText("0");
        lbSumNumParcelas1.setToolTipText("");
        lbSumNumParcelas1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jLabel13.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("N° de parcelas registradas:");

        lbSumExParcelas1.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        lbSumExParcelas1.setForeground(new java.awt.Color(255, 255, 0));
        lbSumExParcelas1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbSumExParcelas1.setText("0 Has");
        lbSumExParcelas1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout jpDatosGeneralesLayout = new javax.swing.GroupLayout(jpDatosGenerales);
        jpDatosGenerales.setLayout(jpDatosGeneralesLayout);
        jpDatosGeneralesLayout.setHorizontalGroup(
            jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDatosGeneralesLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jbIImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(4, 4, 4)
                .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cbProductor, javax.swing.GroupLayout.Alignment.LEADING, 0, 457, Short.MAX_VALUE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpDatosGeneralesLayout.createSequentialGroup()
                        .addComponent(lbSumExParcelas1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addGap(4, 4, 4)
                        .addComponent(lbSumNumParcelas1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txtDescripcion))
                .addGap(4, 4, 4)
                .addComponent(btnProductor, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(283, Short.MAX_VALUE))
        );
        jpDatosGeneralesLayout.setVerticalGroup(
            jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDatosGeneralesLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cbProductor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbIImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProductor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbSumExParcelas1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbSumNumParcelas1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(52, 52, 52))
        );

        jtpEdicion.addTab("Datos Generales", jpDatosGenerales);

        jpUbicacion.setBackground(new java.awt.Color(45, 62, 80));
        jpUbicacion.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Coordenada X:");

        txtX.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtX.setEnabled(false);
        txtX.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtXKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtXKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtXKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Localidad:");

        txtLocalidad.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtLocalidad.setEnabled(false);
        txtLocalidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLocalidadKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLocalidadKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtLocalidadKeyTyped(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Coordenada Y:");

        txtY.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtY.setEnabled(false);
        txtY.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtYKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtYKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtYKeyTyped(evt);
            }
        });

        jbIImagen1.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jbIImagen1.setForeground(new java.awt.Color(255, 255, 255));
        jbIImagen1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jbIImagen1.setText("Departamento*:");

        cbDepartamento.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        cbDepartamento.setEnabled(false);
        cbDepartamento.setMinimumSize(new java.awt.Dimension(55, 31));
        cbDepartamento.setPreferredSize(new java.awt.Dimension(55, 31));
        cbDepartamento.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbDepartamentoItemStateChanged(evt);
            }
        });
        cbDepartamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbDepartamentoActionPerformed(evt);
            }
        });

        cbDistrito.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        cbDistrito.setEnabled(false);
        cbDistrito.setMinimumSize(new java.awt.Dimension(55, 31));
        cbDistrito.setPreferredSize(new java.awt.Dimension(55, 31));
        cbDistrito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbDistritoActionPerformed(evt);
            }
        });

        jbIImagen2.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jbIImagen2.setForeground(new java.awt.Color(255, 255, 255));
        jbIImagen2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jbIImagen2.setText("Distrito*:");

        lbImagen.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lbImagen.setForeground(new java.awt.Color(255, 255, 255));
        lbImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbImagen.setText("CROQUIS");
        lbImagen.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));
        lbImagen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lbImagen.setRequestFocusEnabled(false);

        btnCargarImagen.setBackground(new java.awt.Color(0, 153, 153));
        btnCargarImagen.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnCargarImagen.setText("+");
        btnCargarImagen.setToolTipText("Cargar una imagen del producto");
        btnCargarImagen.setEnabled(false);
        btnCargarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarImagenActionPerformed(evt);
            }
        });

        btnEliminarImagen.setBackground(new java.awt.Color(255, 0, 51));
        btnEliminarImagen.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnEliminarImagen.setText("-");
        btnEliminarImagen.setToolTipText("Eliminar imagen del producto");
        btnEliminarImagen.setEnabled(false);
        btnEliminarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarImagenActionPerformed(evt);
            }
        });

        btnPantallaCompleta.setBackground(new java.awt.Color(0, 255, 255));
        btnPantallaCompleta.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnPantallaCompleta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Iconos20x20/IconoPantallacompleta.png"))); // NOI18N
        btnPantallaCompleta.setToolTipText("Ampliar vista de Imagen del producto");
        btnPantallaCompleta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPantallaCompletaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpUbicacionLayout = new javax.swing.GroupLayout(jpUbicacion);
        jpUbicacion.setLayout(jpUbicacionLayout);
        jpUbicacionLayout.setHorizontalGroup(
            jpUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpUbicacionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbIImagen2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbIImagen1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(4, 4, 4)
                .addGroup(jpUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbDistrito, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtLocalidad)
                    .addGroup(jpUbicacionLayout.createSequentialGroup()
                        .addGroup(jpUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtX, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtY, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(176, 176, 176)
                .addComponent(lbImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(jpUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnEliminarImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPantallaCompleta, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCargarImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(474, 474, 474))
        );
        jpUbicacionLayout.setVerticalGroup(
            jpUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpUbicacionLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jpUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cbDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbIImagen1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cbDistrito, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbIImagen2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtX, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtY, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(9, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpUbicacionLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpUbicacionLayout.createSequentialGroup()
                        .addComponent(btnCargarImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPantallaCompleta, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpUbicacionLayout.createSequentialGroup()
                        .addComponent(lbImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9))))
        );

        jtpEdicion.addTab("Ubicación", jpUbicacion);

        jpParcelas.setBackground(new java.awt.Color(45, 62, 80));
        jpParcelas.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel15.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Extensión:");

        lbSumNumParcelas2.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        lbSumNumParcelas2.setForeground(new java.awt.Color(255, 255, 0));
        lbSumNumParcelas2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbSumNumParcelas2.setText("0");
        lbSumNumParcelas2.setToolTipText("");
        lbSumNumParcelas2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jLabel16.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("N° de parcelas registradas:");

        lbSumExParcelas2.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        lbSumExParcelas2.setForeground(new java.awt.Color(255, 255, 0));
        lbSumExParcelas2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbSumExParcelas2.setText("0 Has");
        lbSumExParcelas2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        tbParcelas.setAutoCreateRowSorter(true);
        tbParcelas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(153, 153, 153), null, new java.awt.Color(102, 102, 102)));
        tbParcelas.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbParcelas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbParcelas.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tbParcelas.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbParcelas.setGridColor(new java.awt.Color(0, 153, 204));
        tbParcelas.setOpaque(false);
        tbParcelas.setRowHeight(20);
        tbParcelas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbParcelas.getTableHeader().setReorderingAllowed(false);
        tbParcelas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbParcelasMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbParcelasMousePressed(evt);
            }
        });
        scParcelas.setViewportView(tbParcelas);

        javax.swing.GroupLayout jpParcelasLayout = new javax.swing.GroupLayout(jpParcelas);
        jpParcelas.setLayout(jpParcelasLayout);
        jpParcelasLayout.setHorizontalGroup(
            jpParcelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpParcelasLayout.createSequentialGroup()
                .addGroup(jpParcelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpParcelasLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(lbSumExParcelas2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16)
                        .addGap(4, 4, 4)
                        .addComponent(lbSumNumParcelas2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpParcelasLayout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(scParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 854, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(1374, 1374, 1374))
        );
        jpParcelasLayout.setVerticalGroup(
            jpParcelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpParcelasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpParcelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbSumExParcelas2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbSumNumParcelas2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jtpEdicion.addTab("Parcelas", jpParcelas);

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

        lbEstabSelect.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        lbEstabSelect.setForeground(new java.awt.Color(255, 255, 255));
        lbEstabSelect.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbEstabSelect.setText("Establecimiento en edición:");

        lbEstabSelectNombre.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        lbEstabSelectNombre.setForeground(new java.awt.Color(0, 255, 255));
        lbEstabSelectNombre.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbEstabSelectNombre.setText("Establecimiento");
        lbEstabSelectNombre.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout lbSumNumParcelas22Layout = new javax.swing.GroupLayout(lbSumNumParcelas22);
        lbSumNumParcelas22.setLayout(lbSumNumParcelas22Layout);
        lbSumNumParcelas22Layout.setHorizontalGroup(
            lbSumNumParcelas22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpBanner, javax.swing.GroupLayout.DEFAULT_SIZE, 1100, Short.MAX_VALUE)
            .addGroup(lbSumNumParcelas22Layout.createSequentialGroup()
                .addGroup(lbSumNumParcelas22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lbSumNumParcelas22Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jpTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jpBotones, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jtpEdicion, javax.swing.GroupLayout.PREFERRED_SIZE, 971, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(lbSumNumParcelas22Layout.createSequentialGroup()
                        .addGap(317, 317, 317)
                        .addComponent(jpBotones2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(lbSumNumParcelas22Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbEstabSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbEstabSelectNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        lbSumNumParcelas22Layout.setVerticalGroup(
            lbSumNumParcelas22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lbSumNumParcelas22Layout.createSequentialGroup()
                .addComponent(jpBanner, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(lbSumNumParcelas22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jpBotones, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(lbSumNumParcelas22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lbEstabSelectNombre, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbEstabSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(jtpEdicion, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpBotones2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jpBanner.getAccessibleContext().setAccessibleName("");
        jtpEdicion.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbSumNumParcelas22, javax.swing.GroupLayout.PREFERRED_SIZE, 971, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lbSumNumParcelas22, javax.swing.GroupLayout.PREFERRED_SIZE, 645, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleName("Inventario");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        //Convertir a mayuscula
        Character s = evt.getKeyChar();
        if (Character.isLetter(s)) {
            txtBuscar.setText(txtBuscar.getText().toUpperCase());
        }

        if (tbPrincipal.getSelectedRow() >= 0) {
            Limpiar();
        }

        //actualiza la tabla conforme a la letra que teclea
        TablaPrincipalConsulta(txtBuscar.getText());
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void tbPrincipalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPrincipalMouseClicked
        if (tbPrincipal.isEnabled() == true) {
            btnModificar.setEnabled(true);
            btnEliminar.setEnabled(true);
            ModoVistaPrevia();
        }
    }//GEN-LAST:event_tbPrincipalMouseClicked

    DecimalFormat df = new DecimalFormat("#.###");
    Double sum;
    int cont;

    private void ModoVistaPrevia() {
        txtCodigo.setText(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 0).toString());
        txtDescripcion.setText(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 1).toString());
        metodoscombo.setSelectedNombreItem(cbProductor, tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 2).toString());

        try {
            sum = 0.0;
            cont = 0;
            Conexion con = metodos.ObtenerRSSentencia("SELECT par_extension FROM parcela WHERE par_establecimiento = " + txtCodigo.getText());
            while (con.getResultSet().next()) {
                sum = sum + con.getResultSet().getDouble("par_extension");
                cont++;
            }
            lbSumExParcelas1.setText(df.format(sum) + " Has");
            lbSumExParcelas2.setText(lbSumExParcelas1.getText());
            lbSumNumParcelas1.setText(cont + "");
            lbSumNumParcelas2.setText(lbSumNumParcelas1.getText());
            con.DesconectarBasedeDatos();
        } catch (SQLException e) {
            System.out.println("Error al sumar extension de parcelas " + e);
        }

        metodoscombo.setSelectedNombreItem(cbDepartamento, tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 3).toString());
        metodoscombo.setSelectedNombreItem(cbDistrito, tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 4).toString());
        txtLocalidad.setText(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 5).toString());
        txtX.setText(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 6).toString());
        txtY.setText(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 7).toString());

        metodosimagen.LeerImagen(lbImagen, "src/forms/zafra/establecimiento/imagenescroquis/imagecroquis_" + txtCodigo.getText());

        ConsultaTablaParcelas();
    }

    private void ConsultaTablaParcelas() {
        String[] titlesEstablecimientos = {"Codigo", "Descripción", "Extensión", "Departamento", "Distrito", "Localidad", "Coordenada X", "Coordenada Y"};
        DefaultTableModel modeloParcelas = new DefaultTableModel(null, titlesEstablecimientos);
        Conexion con = metodos.ObtenerRSSentencia("SELECT par_codigo, par_descripcion, par_extension, dep_descripcion, dis_descripcion, par_localidad, par_x, par_y  FROM parcela, departamento, distrito WHERE par_distrito = dis_codigo AND dep_codigo = dis_departamento AND par_establecimiento = " + tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 0).toString());
        String registro[] = new String[con.NumColumnsRS()];
        try {
            while (con.getResultSet().next()) {
                registro[0] = (con.getResultSet().getString("par_codigo"));
                registro[1] = (con.getResultSet().getString("par_descripcion"));
                registro[2] = (con.getResultSet().getString("par_extension"));
                registro[3] = (con.getResultSet().getString("dep_descripcion"));
                registro[4] = (con.getResultSet().getString("dis_descripcion"));
                registro[5] = (con.getResultSet().getString("par_localidad"));
                registro[6] = (con.getResultSet().getString("par_x"));
                registro[7] = (con.getResultSet().getString("par_y"));
                modeloParcelas.addRow(registro);//agrega el registro a la tabla
            }
            tbParcelas.setModel(modeloParcelas);//asigna a la tabla el modelo creado
            con.DesconectarBasedeDatos();
            metodos.AnchuraColumna(tbParcelas);
        } catch (SQLException ex) {
            Logger.getLogger(ABMProductoViejo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
        lbEstabSelect.setVisible(true);
        lbEstabSelectNombre.setVisible(true);
        lbEstabSelectNombre.setText(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 1) + "");

        if (lbImagen.getIcon() != null) {
            btnEliminarImagen.setEnabled(true);
        }
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

    private void btnGuardarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnGuardarKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            btnGuardar.doClick();
        }
    }//GEN-LAST:event_btnGuardarKeyPressed

    private void tbPrincipalMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPrincipalMousePressed
        if (tbPrincipal.isEnabled() == true) {
            btnModificar.setEnabled(true);
            btnEliminar.setEnabled(true);
            ModoVistaPrevia();
        }
    }//GEN-LAST:event_tbPrincipalMousePressed

    private void btnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteActionPerformed

    }//GEN-LAST:event_btnReporteActionPerformed

    private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyTyped
        metodos.FiltroDeCaracteres(evt);
    }//GEN-LAST:event_txtBuscarKeyTyped

    private void txtXKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtXKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtXKeyPressed

    private void txtXKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtXKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtXKeyReleased

    private void txtXKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtXKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtXKeyTyped

    private void txtLocalidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLocalidadKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLocalidadKeyPressed

    private void txtLocalidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLocalidadKeyReleased
        Character s = evt.getKeyChar();
        if (Character.isLetter(s)) {
            txtLocalidad.setText(txtLocalidad.getText().toUpperCase());
        }
    }//GEN-LAST:event_txtLocalidadKeyReleased

    private void txtLocalidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLocalidadKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLocalidadKeyTyped

    private void btnProductorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductorActionPerformed
        /*ABMCliente abmproductor = new ABMCliente(null, false);
        abmproductor.setVisible(true);*/
    }//GEN-LAST:event_btnProductorActionPerformed

    private void txtYKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtYKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtYKeyPressed

    private void txtYKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtYKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtYKeyReleased

    private void txtYKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtYKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtYKeyTyped

    private void cbDepartamentoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbDepartamentoItemStateChanged
        cbDistrito.removeAllItems();
        metodoscombo.CargarComboBox(cbDistrito, "SELECT dis_codigo, dis_descripcion FROM distrito WHERE dis_departamento = " + metodoscombo.ObtenerIdComboBox(cbDepartamento));
        if (cbDistrito.getItemCount() > 0) {
            cbDistrito.setSelectedIndex(0);
        }
    }//GEN-LAST:event_cbDepartamentoItemStateChanged

    private void cbProductorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbProductorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbProductorActionPerformed

    MetodosImagen metodosimagen = new MetodosImagen();
    private void btnCargarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarImagenActionPerformed
        metodosimagen.CargarImagenFC(lbImagen);
        if (lbImagen.getIcon() != null) {
            btnEliminarImagen.setEnabled(true);
        }
    }//GEN-LAST:event_btnCargarImagenActionPerformed


    private void btnEliminarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarImagenActionPerformed
        lbImagen.setIcon(null);
        lbImagen.setText("CROQUIS");
        btnEliminarImagen.setEnabled(false);
    }//GEN-LAST:event_btnEliminarImagenActionPerformed

    private void btnPantallaCompletaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPantallaCompletaActionPerformed
        VistaCompleta vistacompleta = new VistaCompleta("src/forms/zafra/establecimiento/imagenescroquis/imagecroquis_" + txtCodigo.getText());
        metodos.centrarventanaJDialog(vistacompleta);
        vistacompleta.setVisible(true);
    }//GEN-LAST:event_btnPantallaCompletaActionPerformed

    private void tbParcelasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbParcelasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbParcelasMouseClicked

    private void tbParcelasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbParcelasMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbParcelasMousePressed

    private void cbDepartamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbDepartamentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbDepartamentoActionPerformed

    private void cbDistritoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbDistritoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbDistritoActionPerformed

    public void SiguienteFoco(KeyEvent evt) {
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            ((JComponent) evt.getSource()).transferFocus();//Con esta parte transfieres el foco al siguiente campo sea un Jtextfield, Jpasswordfield, boton, etc..
        }
    }

    private void ModoEdicion(boolean valor) {
        txtBuscar.setEnabled(!valor);
        tbPrincipal.setEnabled(!valor);
        txtDescripcion.setEnabled(valor);
        cbProductor.setEnabled(valor);
        btnProductor.setEnabled(valor);
        cbDepartamento.setEnabled(valor);
        cbDistrito.setEnabled(valor);
        txtLocalidad.setEnabled(valor);
        txtX.setEnabled(valor);
        txtY.setEnabled(valor);
        btnCargarImagen.setEnabled(valor);

        btnNuevo.setEnabled(!valor);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnGuardar.setEnabled(valor);
        btnCancelar.setEnabled(valor);
        btnReporte.setEnabled(!valor);
        btnEliminarImagen.setEnabled(valor);

        txtDescripcion.requestFocus();
    }

    private void Limpiar() {
        txtCodigo.setText("");
        txtDescripcion.setText("");
        cbProductor.setSelectedIndex(-1);
        lbSumExParcelas1.setText("0 Has");
        lbSumNumParcelas1.setText("0");
        cbDepartamento.setSelectedIndex(0);
        cbDistrito.setSelectedIndex(0);
        txtLocalidad.setText("");
        txtX.setText("");
        txtY.setText("");

        lbImagen.setIcon(null);
        lbImagen.setText("CROQUIS");
        jtpEdicion.setSelectedIndex(0);
        TablaPrincipalConsulta(txtBuscar.getText()); //Trae todos los registros

        lbEstabSelect.setVisible(false);
        lbEstabSelectNombre.setVisible(false);

        txtBuscar.requestFocus();
    }

    @SuppressWarnings("ConvertToTryWithResources")
    public void RegistroNuevo() {
        try {
            if (!txtDescripcion.getText().trim().isEmpty()
                    && !(cbProductor.getSelectedIndex() == -1)
                    && !(cbDistrito.getSelectedIndex() == -1)) {
                String descripcion = txtDescripcion.getText();
                int idproductor = metodoscombo.ObtenerIdComboBox(cbProductor);
                int iddistrito = metodoscombo.ObtenerIdComboBox(cbDistrito);
                String localidad = txtLocalidad.getText();
                String X = txtX.getText();
                String Y = txtY.getText();

                int confirmado = JOptionPane.showConfirmDialog(null, "¿Esta seguro crear este nuevo registro?", "Confirmación", JOptionPane.YES_OPTION);

                if (JOptionPane.YES_OPTION == confirmado) {
                    //REGISTRAR NUEVO
                    try {
                        Connection con = Conexion.ConectarBasedeDatos();
                        String sentencia = "CALL SP_EstablecimientoAlta ('" + descripcion + "','" + idproductor + "','" + iddistrito + "','" + localidad + "','" + X + "','" + Y + "')";
                        System.out.println("Insertar registro: " + sentencia);
                        Statement st = (Statement) con.createStatement();
                        st.executeUpdate(sentencia);

                        con.close();
                        st.close();
                        JOptionPane.showMessageDialog(this, "Se agrego correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                        ModoEdicion(false);
                        Limpiar();

                        //Guardar Croquis
                        metodosimagen.GuardarImagen("src/forms/zafra/establecimiento/imagenescroquis/imagecroquis_" + metodos.ObtenerIdUltimoRegistro("estab_codigo", "establecimiento"));

                    } catch (HeadlessException ex) {
                        JOptionPane.showMessageDialog(this, "Ocurrió un Error " + ex.getMessage());
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Ocurrió un Error " + ex.getMessage());
                    }
                } else {
                    System.out.println("No se guardo el registro");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Completa todos los campos marcados con *", "Error", JOptionPane.ERROR_MESSAGE);
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

        String codigo = txtCodigo.getText();
        String descripcion = txtDescripcion.getText();
        int idproductor = metodoscombo.ObtenerIdComboBox(cbProductor);
        int iddistrito = metodoscombo.ObtenerIdComboBox(cbDistrito);
        String localidad = txtLocalidad.getText();
        String X = txtX.getText();
        String Y = txtY.getText();

        //si los datos son diferentes de vacios
        if (!codigo.isEmpty() && !txtDescripcion.getText().trim().isEmpty()
                && !(cbProductor.getSelectedIndex() == -1)
                && !(cbDistrito.getSelectedIndex() == -1)) {
            int confirmado = JOptionPane.showConfirmDialog(null, "¿Esta seguro de modificar este registro?", "Confirmación", JOptionPane.YES_OPTION);
            if (JOptionPane.YES_OPTION == confirmado) {
                try {
                    sentencia = "CALL SP_EstablecimientoModificar(" + codigo + ",'" + descripcion + "','" + idproductor + "','" + iddistrito + "','" + localidad + "','" + X + "','" + Y + "')";
                    System.out.println("Actualizar registro: " + sentencia);
                    getToolkit().beep();
                    PreparedStatement pst = con.prepareStatement(sentencia);
                    pst.executeUpdate();
                    pst.close();
                    JOptionPane.showMessageDialog(null, "Registro modificado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);

                    //Eliminar o guardar imagen
                    if (lbImagen.getIcon() == null) { //Elimina Imagen
                        metodosimagen.EliminarImagen("src/forms/zafra/establecimiento/imagenescroquis/imagecroquis_" + txtCodigo.getText());
                    } else {//Guarda Imagen
                        metodosimagen.GuardarImagen("src/forms/zafra/establecimiento/imagenescroquis/imagecroquis_" + metodos.ObtenerIdUltimoRegistro("estab_codigo", "establecimiento"));
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al modificar registro " + ex, "Advertencia", JOptionPane.WARNING_MESSAGE);
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
            filasel = tbPrincipal.getSelectedRow();
            if (filasel == -1) {
                JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna fila", "Advertencia", JOptionPane.WARNING_MESSAGE);
                txtBuscar.requestFocus();
            } else {
                int confirmado = javax.swing.JOptionPane.showConfirmDialog(null, "¿Realmente desea eliminar este registro?", "Confirmación", JOptionPane.YES_OPTION);
                if (confirmado == JOptionPane.YES_OPTION) {
                    codigo = (String) tbPrincipal.getModel().getValueAt(filasel, 0);

                Connection con = Conexion.ConectarBasedeDatos();
                    String sentence;
                    sentence = "CALL SP_EstablecimientoEliminar(" + codigo + ")";

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
    private javax.swing.JButton btnCargarImagen;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminarImagen;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnPantallaCompleta;
    private javax.swing.JButton btnProductor;
    private javax.swing.JButton btnReporte;
    private javax.swing.JComboBox cbCampoBuscar;
    public javax.swing.JComboBox<metodos.MetodosCombo> cbDepartamento;
    public javax.swing.JComboBox<metodos.MetodosCombo> cbDistrito;
    public javax.swing.JComboBox<metodos.MetodosCombo> cbProductor;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jbIImagen;
    private javax.swing.JLabel jbIImagen1;
    private javax.swing.JLabel jbIImagen2;
    private javax.swing.JPanel jpBanner;
    private javax.swing.JPanel jpBotones;
    private javax.swing.JPanel jpBotones2;
    private javax.swing.JPanel jpDatosGenerales;
    private javax.swing.JPanel jpParcelas;
    private javax.swing.JPanel jpTabla;
    private javax.swing.JPanel jpUbicacion;
    private javax.swing.JTabbedPane jtpEdicion;
    private javax.swing.JLabel lbBanner;
    private javax.swing.JLabel lbEstabSelect;
    private javax.swing.JLabel lbEstabSelectNombre;
    private javax.swing.JLabel lbImagen;
    private javax.swing.JLabel lbSumExParcelas1;
    private javax.swing.JLabel lbSumExParcelas2;
    private javax.swing.JLabel lbSumNumParcelas1;
    private javax.swing.JLabel lbSumNumParcelas2;
    private javax.swing.JPanel lbSumNumParcelas22;
    private javax.swing.JLabel lblBuscarCampo;
    private javax.swing.JScrollPane scParcelas;
    private javax.swing.JTable tbParcelas;
    private javax.swing.JTable tbPrincipal;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtLocalidad;
    private javax.swing.JTextField txtX;
    private javax.swing.JTextField txtY;
    // End of variables declaration//GEN-END:variables
}
