package forms.producto;

import conexion.Conexion;
import forms.producto.dosis.AMDosis;
import forms.producto.ingrediente_activo.*;
import forms.producto.empresa_registrante.ABMEmpresaRegistrante;
import forms.producto.formulacion.ABMFormulacion;
import forms.producto.clase_producto.ABMClaseProducto;
import forms.producto.fabricante.ABMFabricante;
import forms.producto.tipoagroquimico.ABMTipoAgroquimico;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import metodos.MetodosCombo;
import metodos.Metodos;
import metodos.MetodosImagen;
import metodos.VistaCompleta;

/**
 *
 * @author Lic. Arnaldo Cantero
 */
public class ABMProducto extends javax.swing.JDialog {

    //TablaIngredientesActivos
    public String titlesIA[] = {"Id", "Descripción"};
    public String regIA[] = new String[2];
    public ArrayList<String> ArrayAnadidosIA = new ArrayList<>();
    public ArrayList<String> ArrayEliminadosIA = new ArrayList<>();
    public DefaultTableModel modeloTablaListaIA = new DefaultTableModel(null, titlesIA);

    //TablaDosis
    private final String titlesdosis[] = {"Id", "Dosis mínima", "Dosis máxima", "Cultivo", "IdCultivo"};
    private final String regdosis[];
    public DefaultTableModel modeloTablaDosis = new DefaultTableModel(null, titlesdosis);

    private final Metodos metodos = new Metodos();
    private final MetodosCombo metodoscombo = new MetodosCombo();
    private final Icon imagendefault;
    public String estado;
    public java.awt.Dialog d;
    private final JComboBox<metodos.MetodosCombo> cbProductoSelec;

    public ABMProducto(java.awt.Frame parent, Boolean modal, JComboBox<metodos.MetodosCombo> cbProducto) {
        super(parent, modal);
        this.regdosis = new String[5];
        cbProductoSelec = cbProducto;

        initComponents();

        CargarComboBoxes();
        imagendefault = lbImagen.getIcon();

        TablaPrincipalConsulta(txtBuscar.getText());
        txtBuscar.requestFocus();

        //Shortcuts 
        btnNuevo.setMnemonic(KeyEvent.VK_N); //ALT+N
        btnModificar.setMnemonic(KeyEvent.VK_M); //ALT+M
        btnEliminar.setMnemonic(KeyEvent.VK_E); //ALT+E
    }

    //-------------METODOS----------//
    boolean CombosListo = false;

    public void CargarComboBoxes() {
        //Carga los combobox con las consultas
        metodoscombo.CargarComboBox(cbFabricante, "SELECT fa_codigo, fa_descripcion FROM fabricante ORDER BY fa_descripcion");
        metodoscombo.CargarComboBox(cbClaseProducto, "SELECT cp_codigo, cp_descripcion FROM clase_producto ORDER BY cp_descripcion");
        metodoscombo.CargarComboBox(cbTipoAgroquimico, "SELECT ta_codigo, ta_descripcion FROM tipo_agroquimico ORDER BY ta_descripcion");
        metodoscombo.CargarComboBox(cbEmpresaRegistrante, "SELECT er_codigo, er_descripcion FROM empresa_registrante ORDER BY er_descripcion");
        metodoscombo.CargarComboBox(cbFormulacion, "SELECT for_codigo, concat(for_descripcion,' (',for_abreviatura,')') AS formulacion FROM formulacion ORDER BY for_descripcion");
        ModoEdicion(false);
        CombosListo = true;
    }

    public void TablaPrincipalConsulta(String filtro) {//Realiza la consulta de los productos que tenemos en la base de datos
        String nombreSp = "SP_ProductoConsulta";
        @SuppressWarnings("LocalVariableHidesMemberVariable")
        String titlesJtabla[] = {"Codigo", "Nombre comercial", "Fabricante", "Empresa Registrante", "Registro senave",
            "Tipo de producto", "Tipo de agroquimico", "Formulación", "Estado", "Ingredientes Activos"};
        String camposconsulta[] = {"pro_codigo", "pro_descripcion", "fa_descripcion", "er_descripcion", "pro_registrosenave",
            "cp_descripcion", "ta_descripcion", "for_descripcion,for_abreviatura", "es_descripcion", "ia_descripcion"};

        metodos.ConsultaFiltroTablaBD(tbPrincipal, titlesJtabla, camposconsulta, nombreSp, filtro, cbFiltroCampo);
        metodos.AnchuraColumna(tbPrincipal);
        lbCantRegistros.setText(metodos.CantRegistros + " Registros encontrados");
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
        scpPrincipal = new javax.swing.JScrollPane();
        tbPrincipal = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        lblBuscarCampo = new javax.swing.JLabel();
        cbFiltroCampo = new javax.swing.JComboBox();
        lbCantRegistros = new javax.swing.JLabel();
        jpBotones = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnInforme = new javax.swing.JButton();
        jtpEdicion = new javax.swing.JTabbedPane();
        jpDatosGenerales = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtNombreComercial = new javax.swing.JTextField();
        lbNumRegistro = new javax.swing.JLabel();
        txtNRegistro = new javax.swing.JTextField();
        btnTipoAgroquimico = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        cbFabricante = new javax.swing.JComboBox<>();
        cbTipoAgroquimico = new javax.swing.JComboBox<>();
        btnFabricante = new javax.swing.JButton();
        cbFormulacion = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        btnFormulacion = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        scpIngrActivos = new javax.swing.JScrollPane();
        tbIngrActivos = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        btnMas = new javax.swing.JButton();
        btnMenos = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cbEmpresaRegistrante = new javax.swing.JComboBox<>();
        btnEmpresaRegistrante = new javax.swing.JButton();
        jbIImagen = new javax.swing.JLabel();
        cbClaseProducto = new javax.swing.JComboBox<>();
        btnTipoProducto = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lbImagen = new javax.swing.JLabel();
        btnCargarImagen = new javax.swing.JButton();
        btnEliminarImagen = new javax.swing.JButton();
        btnPantallaCompleta = new javax.swing.JButton();
        lbDosis = new javax.swing.JLabel();
        scpDosis = new javax.swing.JScrollPane();
        tbDosis = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        btnMasDosis = new javax.swing.JButton();
        btnMenosDosis = new javax.swing.JButton();
        lbEstado = new javax.swing.JLabel();
        btnModificarDosis = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jpBotones2 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setTitle("Ventana Productos");
        setBackground(new java.awt.Color(45, 62, 80));
        setName("Dg_ABMProducto"); // NOI18N
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);

        jpPrincipal.setBackground(new java.awt.Color(45, 62, 80));

        jpBanner.setBackground(new java.awt.Color(0, 102, 204));
        jpBanner.setPreferredSize(new java.awt.Dimension(1000, 82));

        lbBanner.setFont(new java.awt.Font("sansserif", 1, 30)); // NOI18N
        lbBanner.setForeground(new java.awt.Color(255, 255, 255));
        lbBanner.setText("Productos");
        lbBanner.setMaximumSize(new java.awt.Dimension(1100, 52));
        lbBanner.setMinimumSize(new java.awt.Dimension(1100, 52));
        lbBanner.setPreferredSize(new java.awt.Dimension(1000, 52));

        javax.swing.GroupLayout jpBannerLayout = new javax.swing.GroupLayout(jpBanner);
        jpBanner.setLayout(jpBannerLayout);
        jpBannerLayout.setHorizontalGroup(
            jpBannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBannerLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(lbBanner, javax.swing.GroupLayout.PREFERRED_SIZE, 1187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpBannerLayout.setVerticalGroup(
            jpBannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbBanner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jpTabla.setBackground(new java.awt.Color(45, 62, 80));
        jpTabla.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbPrincipal.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tbPrincipal.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbPrincipal.setGridColor(new java.awt.Color(0, 153, 153));
        tbPrincipal.setOpaque(false);
        tbPrincipal.setRowHeight(20);
        tbPrincipal.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbPrincipal.setShowGrid(false);
        tbPrincipal.getTableHeader().setReorderingAllowed(false);
        tbPrincipal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPrincipalMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbPrincipalMousePressed(evt);
            }
        });
        tbPrincipal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPrincipalKeyPressed(evt);
            }
        });
        scpPrincipal.setViewportView(tbPrincipal);

        lblBuscarCampo.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        lblBuscarCampo.setForeground(new java.awt.Color(255, 255, 255));
        lblBuscarCampo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBuscarCampo.setText("Buscar por:");

        cbFiltroCampo.setEnabled(false);
        cbFiltroCampo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbFiltroCampoItemStateChanged(evt);
            }
        });

        lbCantRegistros.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lbCantRegistros.setForeground(new java.awt.Color(204, 204, 0));
        lbCantRegistros.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbCantRegistros.setText("0 Registros encontrados");
        lbCantRegistros.setPreferredSize(new java.awt.Dimension(57, 25));

        javax.swing.GroupLayout jpTablaLayout = new javax.swing.GroupLayout(jpTabla);
        jpTabla.setLayout(jpTablaLayout);
        jpTablaLayout.setHorizontalGroup(
            jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTablaLayout.createSequentialGroup()
                .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpTablaLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar)
                        .addGap(18, 18, 18)
                        .addComponent(lblBuscarCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbFiltroCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpTablaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scpPrincipal))
                    .addGroup(jpTablaLayout.createSequentialGroup()
                        .addGap(747, 747, 747)
                        .addComponent(lbCantRegistros, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpTablaLayout.setVerticalGroup(
            jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTablaLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblBuscarCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbFiltroCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addComponent(scpPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(lbCantRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3))
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

        btnInforme.setBackground(new java.awt.Color(14, 154, 153));
        btnInforme.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnInforme.setForeground(new java.awt.Color(255, 255, 255));
        btnInforme.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/iconos40x40/IconoReporte.png"))); // NOI18N
        btnInforme.setText("REPORTE");
        btnInforme.setToolTipText("Genera un reporte de los registros de la tabla");
        btnInforme.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jpBotonesLayout = new javax.swing.GroupLayout(jpBotones);
        jpBotones.setLayout(jpBotonesLayout);
        jpBotonesLayout.setHorizontalGroup(
            jpBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBotonesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnInforme, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModificar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpBotonesLayout.setVerticalGroup(
            jpBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBotonesLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(btnNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModificar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnInforme)
                .addGap(15, 15, 15))
        );

        jtpEdicion.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jtpEdicion.setEnabled(false);
        jtpEdicion.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jtpEdicion.setName(""); // NOI18N

        jpDatosGenerales.setBackground(new java.awt.Color(45, 62, 80));
        jpDatosGenerales.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Código:");
        jLabel5.setPreferredSize(new java.awt.Dimension(57, 25));

        txtCodigo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtCodigo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCodigo.setEnabled(false);
        txtCodigo.setMinimumSize(new java.awt.Dimension(13, 25));
        txtCodigo.setPreferredSize(new java.awt.Dimension(13, 27));
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoKeyTyped(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Fabricante*:");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtNombreComercial.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtNombreComercial.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNombreComercial.setEnabled(false);
        txtNombreComercial.setPreferredSize(new java.awt.Dimension(13, 27));
        txtNombreComercial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreComercialActionPerformed(evt);
            }
        });
        txtNombreComercial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreComercialKeyReleased(evt);
            }
        });

        lbNumRegistro.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbNumRegistro.setForeground(new java.awt.Color(255, 255, 255));
        lbNumRegistro.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNumRegistro.setText("Nº de registro*:");

        txtNRegistro.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtNRegistro.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNRegistro.setEnabled(false);
        txtNRegistro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNRegistroKeyTyped(evt);
            }
        });

        btnTipoAgroquimico.setFont(new java.awt.Font("Adobe Hebrew", 1, 18)); // NOI18N
        btnTipoAgroquimico.setText("...");
        btnTipoAgroquimico.setToolTipText("Ir a la ventana de Tipos de agroquimicos");
        btnTipoAgroquimico.setEnabled(false);
        btnTipoAgroquimico.setPreferredSize(new java.awt.Dimension(35, 31));
        btnTipoAgroquimico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTipoAgroquimicoActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Nombre comercial*:");

        cbFabricante.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cbFabricante.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cbFabricante.setEnabled(false);
        cbFabricante.setMinimumSize(new java.awt.Dimension(55, 31));
        cbFabricante.setName("Fabricante"); // NOI18N
        cbFabricante.setPreferredSize(new java.awt.Dimension(55, 31));

        cbTipoAgroquimico.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cbTipoAgroquimico.setEnabled(false);
        cbTipoAgroquimico.setMinimumSize(new java.awt.Dimension(55, 31));
        cbTipoAgroquimico.setName("TipoAgroquimico"); // NOI18N
        cbTipoAgroquimico.setPreferredSize(new java.awt.Dimension(55, 31));

        btnFabricante.setFont(new java.awt.Font("Adobe Hebrew", 1, 18)); // NOI18N
        btnFabricante.setText("...");
        btnFabricante.setToolTipText("Ir a la ventana de Fabricantes");
        btnFabricante.setEnabled(false);
        btnFabricante.setPreferredSize(new java.awt.Dimension(35, 31));
        btnFabricante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFabricanteActionPerformed(evt);
            }
        });

        cbFormulacion.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cbFormulacion.setEnabled(false);
        cbFormulacion.setMinimumSize(new java.awt.Dimension(55, 31));
        cbFormulacion.setName("Formulacion"); // NOI18N
        cbFormulacion.setPreferredSize(new java.awt.Dimension(55, 31));
        cbFormulacion.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbFormulacionItemStateChanged(evt);
            }
        });
        cbFormulacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFormulacionActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Formulación*:");

        btnFormulacion.setFont(new java.awt.Font("Adobe Hebrew", 1, 18)); // NOI18N
        btnFormulacion.setText("...");
        btnFormulacion.setToolTipText("Ir a la ventana de formulaciones");
        btnFormulacion.setEnabled(false);
        btnFormulacion.setPreferredSize(new java.awt.Dimension(35, 31));
        btnFormulacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFormulacionActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("Principios Activos*:");
        jLabel11.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        tbIngrActivos.setAutoCreateRowSorter(true);
        tbIngrActivos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(153, 153, 153), null, new java.awt.Color(102, 102, 102)));
        tbIngrActivos.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        tbIngrActivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbIngrActivos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tbIngrActivos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbIngrActivos.setEnabled(false);
        tbIngrActivos.setGridColor(new java.awt.Color(0, 153, 153));
        tbIngrActivos.setOpaque(false);
        tbIngrActivos.setRowHeight(20);
        tbIngrActivos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbIngrActivos.getTableHeader().setReorderingAllowed(false);
        tbIngrActivos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbIngrActivosMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbIngrActivosMousePressed(evt);
            }
        });
        scpIngrActivos.setViewportView(tbIngrActivos);

        btnMas.setBackground(new java.awt.Color(0, 153, 153));
        btnMas.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        btnMas.setText("+");
        btnMas.setToolTipText("Agrega un nuevo ingrediente activo a la lista");
        btnMas.setEnabled(false);
        btnMas.setNextFocusableComponent(btnGuardar);
        btnMas.setPreferredSize(new java.awt.Dimension(35, 31));
        btnMas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMasActionPerformed(evt);
            }
        });

        btnMenos.setBackground(new java.awt.Color(255, 0, 51));
        btnMenos.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        btnMenos.setText("-");
        btnMenos.setToolTipText("Elimina el ingrediente activo seleccionado en la lista");
        btnMenos.setEnabled(false);
        btnMenos.setPreferredSize(new java.awt.Dimension(35, 31));
        btnMenos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenosActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Empresa registrante*:");

        cbEmpresaRegistrante.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cbEmpresaRegistrante.setEnabled(false);
        cbEmpresaRegistrante.setMinimumSize(new java.awt.Dimension(55, 31));
        cbEmpresaRegistrante.setName("EmpresaRegistrante"); // NOI18N
        cbEmpresaRegistrante.setPreferredSize(new java.awt.Dimension(55, 31));

        btnEmpresaRegistrante.setFont(new java.awt.Font("Adobe Hebrew", 1, 18)); // NOI18N
        btnEmpresaRegistrante.setText("...");
        btnEmpresaRegistrante.setToolTipText("Ir a la ventana de Empresas registrantes");
        btnEmpresaRegistrante.setEnabled(false);
        btnEmpresaRegistrante.setPreferredSize(new java.awt.Dimension(35, 31));
        btnEmpresaRegistrante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmpresaRegistranteActionPerformed(evt);
            }
        });

        jbIImagen.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jbIImagen.setForeground(new java.awt.Color(255, 255, 255));
        jbIImagen.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jbIImagen.setText("Clase de producto*:");

        cbClaseProducto.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cbClaseProducto.setEnabled(false);
        cbClaseProducto.setMinimumSize(new java.awt.Dimension(55, 31));
        cbClaseProducto.setName("ClaseProducto"); // NOI18N
        cbClaseProducto.setPreferredSize(new java.awt.Dimension(55, 31));

        btnTipoProducto.setFont(new java.awt.Font("Adobe Hebrew", 1, 18)); // NOI18N
        btnTipoProducto.setText("...");
        btnTipoProducto.setToolTipText("Ir a la ventana de Tipos de productos");
        btnTipoProducto.setEnabled(false);
        btnTipoProducto.setPreferredSize(new java.awt.Dimension(35, 31));
        btnTipoProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTipoProductoActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Tipo de Agroquimico*:");

        lbImagen.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lbImagen.setForeground(new java.awt.Color(255, 255, 255));
        lbImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/forms/producto/iconos/IconoProductoSinFoto.png"))); // NOI18N
        lbImagen.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));
        lbImagen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

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

        lbDosis.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbDosis.setForeground(new java.awt.Color(255, 255, 255));
        lbDosis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbDosis.setText("Dosis recomendada");
        lbDosis.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        tbDosis.setAutoCreateRowSorter(true);
        tbDosis.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(153, 153, 153), null, new java.awt.Color(102, 102, 102)));
        tbDosis.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        tbDosis.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbDosis.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tbDosis.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbDosis.setEnabled(false);
        tbDosis.setGridColor(new java.awt.Color(0, 153, 153));
        tbDosis.setOpaque(false);
        tbDosis.setRowHeight(20);
        tbDosis.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbDosis.getTableHeader().setReorderingAllowed(false);
        tbDosis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDosisMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbDosisMousePressed(evt);
            }
        });
        scpDosis.setViewportView(tbDosis);

        btnMasDosis.setBackground(new java.awt.Color(0, 153, 153));
        btnMasDosis.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        btnMasDosis.setText("+");
        btnMasDosis.setToolTipText("Agrega una nueva dosis recomendada a la lista");
        btnMasDosis.setEnabled(false);
        btnMasDosis.setNextFocusableComponent(btnGuardar);
        btnMasDosis.setPreferredSize(new java.awt.Dimension(35, 31));
        btnMasDosis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMasDosisActionPerformed(evt);
            }
        });

        btnMenosDosis.setBackground(new java.awt.Color(255, 0, 51));
        btnMenosDosis.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        btnMenosDosis.setText("-");
        btnMenosDosis.setToolTipText("Elimina la dosis recomendada seleccionada en la lista");
        btnMenosDosis.setEnabled(false);
        btnMenosDosis.setPreferredSize(new java.awt.Dimension(35, 31));
        btnMenosDosis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenosDosisActionPerformed(evt);
            }
        });

        lbEstado.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbEstado.setForeground(new java.awt.Color(255, 255, 255));
        lbEstado.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbEstado.setText("(Estado)");
        lbEstado.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        btnModificarDosis.setBackground(new java.awt.Color(204, 102, 0));
        btnModificarDosis.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        btnModificarDosis.setText("*");
        btnModificarDosis.setToolTipText("Modifica la dosis recomendada seleccionada en la lista");
        btnModificarDosis.setEnabled(false);
        btnModificarDosis.setPreferredSize(new java.awt.Dimension(35, 31));
        btnModificarDosis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarDosisActionPerformed(evt);
            }
        });
        btnModificarDosis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btnModificarDosisKeyReleased(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(255, 255, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Campos con (*) son obligatorios");

        javax.swing.GroupLayout jpDatosGeneralesLayout = new javax.swing.GroupLayout(jpDatosGenerales);
        jpDatosGenerales.setLayout(jpDatosGeneralesLayout);
        jpDatosGeneralesLayout.setHorizontalGroup(
            jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDatosGeneralesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbNumRegistro, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                    .addComponent(jbIImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(4, 4, 4)
                .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombreComercial, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpDatosGeneralesLayout.createSequentialGroup()
                        .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                            .addComponent(scpIngrActivos, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(cbClaseProducto, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(4, 4, 4)
                        .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnMas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnMenos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTipoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jpDatosGeneralesLayout.createSequentialGroup()
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(47, 47, 47)
                .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(4, 4, 4)
                .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDatosGeneralesLayout.createSequentialGroup()
                        .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cbFormulacion, javax.swing.GroupLayout.Alignment.LEADING, 0, 263, Short.MAX_VALUE)
                            .addComponent(cbTipoAgroquimico, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbEmpresaRegistrante, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbFabricante, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(4, 4, 4)
                        .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnFabricante, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEmpresaRegistrante, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTipoAgroquimico, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnFormulacion, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jpDatosGeneralesLayout.createSequentialGroup()
                        .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpDatosGeneralesLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(lbDosis, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(lbEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(scpDosis, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnMasDosis, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnModificarDosis, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnMenosDosis, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(11, 11, 11)
                .addComponent(lbImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEliminarImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCargarImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPantallaCompleta, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46))
        );
        jpDatosGeneralesLayout.setVerticalGroup(
            jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDatosGeneralesLayout.createSequentialGroup()
                .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDatosGeneralesLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(btnCargarImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPantallaCompleta, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpDatosGeneralesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpDatosGeneralesLayout.createSequentialGroup()
                                .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)
                                    .addComponent(cbFabricante, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnFabricante, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(1, 1, 1)
                                .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNombreComercial, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbEmpresaRegistrante, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnEmpresaRegistrante, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(1, 1, 1)
                                .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(lbNumRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbTipoAgroquimico, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnTipoAgroquimico, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(1, 1, 1)
                                .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(btnFormulacion, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbFormulacion, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jbIImagen)
                                    .addComponent(cbClaseProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnTipoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(1, 1, 1)
                                .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbDosis, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jpDatosGeneralesLayout.createSequentialGroup()
                                        .addComponent(btnMas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                                        .addComponent(btnMenos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(scpIngrActivos, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addGroup(jpDatosGeneralesLayout.createSequentialGroup()
                                        .addComponent(btnMasDosis, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnModificarDosis, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnMenosDosis, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(scpDosis, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))))
                .addGap(26, 26, 26))
        );

        jtpEdicion.addTab("Datos Generales", jpDatosGenerales);

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelar)
                .addContainerGap())
        );
        jpBotones2Layout.setVerticalGroup(
            jpBotones2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBotones2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpBotones2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpPrincipalLayout = new javax.swing.GroupLayout(jpPrincipal);
        jpPrincipal.setLayout(jpPrincipalLayout);
        jpPrincipalLayout.setHorizontalGroup(
            jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpBanner, javax.swing.GroupLayout.DEFAULT_SIZE, 1241, Short.MAX_VALUE)
            .addComponent(jtpEdicion, javax.swing.GroupLayout.PREFERRED_SIZE, 1241, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jpPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpPrincipalLayout.createSequentialGroup()
                        .addComponent(jpTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jpBotones, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jpPrincipalLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jpBotones2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(485, 485, 485))))
        );
        jpPrincipalLayout.setVerticalGroup(
            jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPrincipalLayout.createSequentialGroup()
                .addComponent(jpBanner, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpBotones, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtpEdicion, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpBotones2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jpBanner.getAccessibleContext().setAccessibleName("");
        jtpEdicion.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 1224, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        getAccessibleContext().setAccessibleName("ABMProducto");

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
        if (evt.getClickCount() == 2 && this.isModal() == true) {
            metodoscombo.setSelectedNombreItem(cbProductoSelec, tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 1).toString());
            this.dispose();
        }

        if (tbPrincipal.isEnabled() == true) {
            Limpiar();
            ModoVistaPrevia();
            btnModificar.setEnabled(true);
            btnEliminar.setEnabled(true);
        }
    }//GEN-LAST:event_tbPrincipalMouseClicked

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
        tbPrincipal.clearSelection();
        TablaPrincipalConsulta(txtBuscar.getText()); //Trae todos los registros
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        txtBuscar.setText("");
        ModoEdicion(true);
        Limpiar();
        txtNombreComercial.requestFocus();
    }//GEN-LAST:event_btnNuevoActionPerformed
    String titlesJtabla[] = {"Codigo", "Nombre comercial", "Fabricante", "Empresa Registrante", "Registro senave",
        "País de origen", "Tipo de agroquimico", "Formulación", "Estado"};
    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        ModoEdicion(true);
    }//GEN-LAST:event_btnModificarActionPerformed

    MetodosImagen metodosimagen = new MetodosImagen();

    private void ModoVistaPrevia() throws HeadlessException {
        txtCodigo.setText(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 0).toString());
        txtNombreComercial.setText(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 1).toString());
        metodoscombo.setSelectedNombreItem(cbFabricante, tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 2).toString());//Compara y trae el que coincide con la celda
        metodoscombo.setSelectedNombreItem(cbEmpresaRegistrante, tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 3).toString());
        txtNRegistro.setText(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 4).toString());
        metodoscombo.setSelectedNombreItem(cbClaseProducto, tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 5).toString());
        metodoscombo.setSelectedNombreItem(cbTipoAgroquimico, tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 6).toString());

        metodoscombo.setSelectedNombreItem(cbFormulacion, tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 7).toString());

        TablaListaIsAsConsulta(txtCodigo.getText());
        TablaDosisConsulta(txtCodigo.getText());

        metodosimagen.LeerImagen(lbImagen, "/src/forms/producto/imagenes/image_" + txtCodigo.getText());
    }

    private void TablaListaIsAsConsulta(String idproducto) {
        Conexion con = metodos.ObtenerRSSentencia("CALL SP_ListaIngredientesActivosConsulta('" + idproducto + "')");
        String registro[] = new String[con.NumColumnsRS()];
        try {
            while (con.rs.next()) {
                registro[0] = (con.rs.getString("lia_ingredienteactivo"));
                registro[1] = (con.rs.getString("ia_descripcion"));
                modeloTablaListaIA.addRow(registro);//agrega el registro a la tabla  
            }
            tbIngrActivos.setModel(modeloTablaListaIA);//asigna a la tabla el modelo creado
            con.DesconectarBasedeDatos();
            OcultarColumna(tbIngrActivos, 0);
        } catch (SQLException ex) {
            Logger.getLogger(ABMProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void TablaDosisConsulta(String idproducto) {
        Conexion con = metodos.ObtenerRSSentencia("CALL SP_DosisConsulta('" + idproducto + "')");
        try {
            String registro[] = new String[con.NumColumnsRS()]; //Cantidad de columnas
            DecimalFormat df = new DecimalFormat("#.###");
            while (con.rs.next()) {
                registro[0] = (con.rs.getString("do_codigo"));
                registro[1] = (df.format(Double.parseDouble(con.rs.getString("do_dosismin"))) + " " + estado);
                registro[2] = (df.format(Double.parseDouble(con.rs.getString("do_dosismax"))) + " " + estado);
                registro[3] = (con.rs.getString("tc_descripcion"));
                registro[4] = (con.rs.getString("do_tipocultivo"));
                modeloTablaDosis.addRow(registro);//agrega el registro a la tabla  
            }
            tbDosis.setModel(modeloTablaDosis);//asigna a la tabla el modelo creado
            con.DesconectarBasedeDatos();
            OcultarColumna(tbDosis, 0);
            tbDosis.getColumnModel().getColumn(1).setPreferredWidth(50);
            tbDosis.getColumnModel().getColumn(2).setPreferredWidth(50);
            tbDosis.getColumnModel().getColumn(3).setPreferredWidth(40);
            OcultarColumna(tbDosis, 4);

        } catch (SQLException ex) {
            Logger.getLogger(ABMProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void OcultarColumna(JTable LaTabla, int NumColumn) {
        LaTabla.getColumnModel().getColumn(NumColumn).setMaxWidth(0);
        LaTabla.getColumnModel().getColumn(NumColumn).setMinWidth(0);
        LaTabla.getColumnModel().getColumn(NumColumn).setPreferredWidth(0);
    }

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        RegistroEliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void tbPrincipalMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPrincipalMousePressed
        if (evt.getClickCount() == 2 && this.isModal() == true) {
            metodoscombo.setSelectedNombreItem(cbProductoSelec, tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 1).toString());
            this.dispose();
        }

        if (tbPrincipal.isEnabled() == true) {
            Limpiar();
            ModoVistaPrevia();
            btnModificar.setEnabled(true);
            btnEliminar.setEnabled(true);
        }
    }//GEN-LAST:event_tbPrincipalMousePressed

    private void btnPantallaCompletaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPantallaCompletaActionPerformed
        VistaCompleta vistacompleta = new VistaCompleta("src/forms/producto/imagenes/image_" + txtCodigo.getText());
        metodos.centrarventanaJDialog(vistacompleta);
        vistacompleta.setVisible(true);
    }//GEN-LAST:event_btnPantallaCompletaActionPerformed

    private void btnEliminarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarImagenActionPerformed
        URL url = this.getClass().getResource("/forms/producto/iconos/IconoProductoSinFoto.png");
        lbImagen.setIcon(new ImageIcon(url));

        btnEliminarImagen.setEnabled(!(lbImagen.getIcon().toString().equals(imagendefault.toString()))); //Revisa si el icono es default
    }//GEN-LAST:event_btnEliminarImagenActionPerformed

    private void btnCargarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarImagenActionPerformed
        metodosimagen.CargarImagenFC(lbImagen);
    }//GEN-LAST:event_btnCargarImagenActionPerformed

    private void btnTipoProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTipoProductoActionPerformed
        ABMClaseProducto abmclaseproducto = new ABMClaseProducto(this, null, false);
        abmclaseproducto.setVisible(true);
    }//GEN-LAST:event_btnTipoProductoActionPerformed

    private void btnEmpresaRegistranteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpresaRegistranteActionPerformed
        ABMEmpresaRegistrante abmempresaregistrante = new ABMEmpresaRegistrante(this, null, false);
        abmempresaregistrante.framepadre = "ABMProducto"; //Avisa al framehijo (abm) que el framepadre es ABMProducto
        abmempresaregistrante.setVisible(true);
    }//GEN-LAST:event_btnEmpresaRegistranteActionPerformed

    private void btnMenosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenosActionPerformed
        ArrayEliminadosIA.add(tbIngrActivos.getValueAt(tbIngrActivos.getSelectedRow(), 0).toString()); //Guarda el id del registro eliminado en la lista de elimiados ListaEliminados

        DefaultTableModel ModeloTabla = (DefaultTableModel) tbIngrActivos.getModel();
        ModeloTabla.removeRow(tbIngrActivos.getSelectedRow());
        btnMenos.setEnabled(false);
    }//GEN-LAST:event_btnMenosActionPerformed

    private void btnMasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMasActionPerformed
        ABMIngredienteActivo abmingredienteactivo = new ABMIngredienteActivo(this, null, true);
        abmingredienteactivo.getBtnAnadir().setEnabled(true); //Se activa el boton anadir
        abmingredienteactivo.setVisible(true);
    }//GEN-LAST:event_btnMasActionPerformed

    private void tbIngrActivosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbIngrActivosMousePressed
        if (tbIngrActivos.isEnabled() == true) {
            btnMenos.setEnabled(true);
        }
    }//GEN-LAST:event_tbIngrActivosMousePressed

    private void tbIngrActivosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbIngrActivosMouseClicked
        if (tbIngrActivos.isEnabled() == true) {
            btnMenos.setEnabled(true);
        }
    }//GEN-LAST:event_tbIngrActivosMouseClicked

    private void btnFormulacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFormulacionActionPerformed
        ABMFormulacion abmformulacion = new ABMFormulacion(this, null, false);
        abmformulacion.setVisible(true);
    }//GEN-LAST:event_btnFormulacionActionPerformed

    private void btnFabricanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFabricanteActionPerformed
        ABMFabricante abmtipocultivo = new ABMFabricante(this, null, false);
        abmtipocultivo.setVisible(true);
    }//GEN-LAST:event_btnFabricanteActionPerformed

    private void txtNRegistroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNRegistroKeyTyped
        // Verificar si la tecla pulsada no es un digito
        //char caracter = evt.getKeyChar();
        //if ((caracter != '-')) {
        //if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
        //evt.consume(); // ignorar el evento de teclado
        //}
        //}

        int longitud = txtNRegistro.getText().length();
        if (longitud > 20) {
            txtNRegistro.setText(txtNRegistro.getText().substring(0, 21));
        };
    }//GEN-LAST:event_txtNRegistroKeyTyped

    private void txtNombreComercialKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreComercialKeyReleased
        //Limitar cantidad de caracteres
        int longitud = txtNombreComercial.getText().length();
        if (longitud > 70) {
            txtNombreComercial.setText(txtNombreComercial.getText().substring(0, 71));
        };

        //Convertir a mayuscula
        Character s = evt.getKeyChar();
        if (Character.isLetter(s)) {
            txtNombreComercial.setText(txtNombreComercial.getText().toUpperCase());
        }
    }//GEN-LAST:event_txtNombreComercialKeyReleased

    private void txtNombreComercialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreComercialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreComercialActionPerformed

    private void txtCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyTyped
        // Verificar si la tecla pulsada no es un digito
        char caracter = evt.getKeyChar();
        if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
            evt.consume(); // ignorar el evento de teclado
        }
    }//GEN-LAST:event_txtCodigoKeyTyped

    private void txtCodigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyReleased
        //Limitar cantidad de caracteres
        int longitud = txtCodigo.getText().length();
        if (longitud > 40) {
            txtCodigo.setText(txtCodigo.getText().substring(0, 41));
        };
    }//GEN-LAST:event_txtCodigoKeyReleased

    private void tbDosisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDosisMouseClicked
        if (tbDosis.isEnabled() == true) {
            btnModificarDosis.setEnabled(true);
            btnMenosDosis.setEnabled(true);
        }
    }//GEN-LAST:event_tbDosisMouseClicked

    private void tbDosisMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDosisMousePressed
        if (tbDosis.isEnabled() == true) {
            btnModificarDosis.setEnabled(true);
            btnMenosDosis.setEnabled(true);
        }
    }//GEN-LAST:event_tbDosisMousePressed

    private void btnMasDosisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMasDosisActionPerformed
        if (cbFormulacion.getSelectedIndex() != -1) {
            AMDosis amdosis = new AMDosis(this, this, true);
            metodoscombo.CargarComboBox(amdosis.cbCultivo, "SELECT tc_codigo, tc_descripcion FROM tipo_cultivo ORDER BY tc_descripcion");
            amdosis.lbDosisMinEstado.setText((lbEstado.getText().replace("(", "")).replace(")", ""));
            amdosis.lbDosisMaxEstado.setText((lbEstado.getText().replace("(", "")).replace(")", ""));
            amdosis.ConversionDosisMin();
            amdosis.ConversionDosisMax();

            amdosis.addWindowListener(new WindowAdapter() { //Ejecuta al cerrar este Jdialog
                @Override
                public void windowClosed(WindowEvent e) {
                    System.out.println("Se cerro dialog");
                }
            });
            amdosis.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "No se seleccionó ninguna formulación", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnMasDosisActionPerformed

    ArrayList<String> ArrayEliminadosDosis = new ArrayList<>();
    String iddosis;
    private void btnMenosDosisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenosDosisActionPerformed
        iddosis = tbDosis.getValueAt(tbDosis.getSelectedRow(), 0).toString();
        if (tbDosis.getValueAt(tbDosis.getSelectedRow(), 0).toString().equals("") == false) {
            ArrayEliminadosDosis.add(tbDosis.getValueAt(tbDosis.getSelectedRow(), 0).toString()); //Guarda el id del registro eliminado en la lista de elimiados ListaEliminados
        }

        DefaultTableModel ModeloTabla = (DefaultTableModel) tbDosis.getModel();
        ModeloTabla.removeRow(tbDosis.getSelectedRow());
        btnMenosDosis.setEnabled(false);
    }//GEN-LAST:event_btnMenosDosisActionPerformed

    private void btnTipoAgroquimicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTipoAgroquimicoActionPerformed
        ABMTipoAgroquimico abmtipoagroquimico = new ABMTipoAgroquimico(this, this, false);
        abmtipoagroquimico.setVisible(true);
    }//GEN-LAST:event_btnTipoAgroquimicoActionPerformed

    @SuppressWarnings("static-access")
    private void tbPrincipalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPrincipalKeyPressed
        char car = (char) evt.getKeyCode();
        System.out.println("Se oprimio " + car);
        if (car == KeyEvent.VK_UP || car == evt.VK_DOWN) {//Al apretar ENTER QUE HAGA ALGO
            if (tbPrincipal.isEnabled() == true) {
                Limpiar();
                ModoVistaPrevia();
                btnModificar.setEnabled(true);
                btnEliminar.setEnabled(true);
            }
        }
    }//GEN-LAST:event_tbPrincipalKeyPressed

    private void cbFiltroCampoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbFiltroCampoItemStateChanged
        if (cbFiltroCampo.isEnabled()) {
            TablaPrincipalConsulta(txtBuscar.getText());
        }
    }//GEN-LAST:event_cbFiltroCampoItemStateChanged

    private void cbFormulacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFormulacionActionPerformed

    }//GEN-LAST:event_cbFormulacionActionPerformed

    private void cbFormulacionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbFormulacionItemStateChanged
        if (CombosListo == true) {
            estado = ObtenerEstado();
            lbEstado.setText("(" + estado + ")");
        }
    }//GEN-LAST:event_cbFormulacionItemStateChanged

    private String ObtenerEstado() {
        String estado = "";
        if (cbFormulacion.getSelectedIndex() != -1) {
            Conexion con = metodos.ObtenerRSSentencia("SELECT es_descripcion, for_descripcion FROM formulacion,estado WHERE for_estado = es_codigo AND for_codigo = " + metodoscombo.ObtenerIdComboBox(cbFormulacion));
            try {
                while (con.rs.next()) {
                    estado = con.rs.getString("es_descripcion");
                }
                con.DesconectarBasedeDatos();
            } catch (SQLException ex) {
                Logger.getLogger(ABMProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return estado;
    }

    private void btnModificarDosisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarDosisActionPerformed
        if (cbFormulacion.getSelectedIndex() != -1) {
            AMDosis amdosis = new AMDosis(this, this, true);
            amdosis.setTitle("Ventana modificar dosis recomendada");
            amdosis.txtCodigo.setText(tbDosis.getValueAt(tbDosis.getSelectedRow(), 0) + "");

            StringTokenizer st = new StringTokenizer(tbDosis.getValueAt(tbDosis.getSelectedRow(), 1) + "", " ");
            amdosis.txtDosisMin.setText(st.nextToken());

            st = new StringTokenizer(tbDosis.getValueAt(tbDosis.getSelectedRow(), 2) + "", " ");
            amdosis.txtDosisMax.setText(st.nextToken());

            amdosis.lbDosisMinEstado.setText(lbEstado.getText().replace("(", "").replace(")", ""));
            amdosis.lbDosisMaxEstado.setText(amdosis.lbDosisMinEstado.getText());

            metodoscombo.CargarComboBox(amdosis.cbCultivo, "SELECT tc_codigo, tc_descripcion FROM tipo_cultivo ORDER BY tc_descripcion");
            metodoscombo.setSelectedNombreItem(amdosis.cbCultivo, tbDosis.getValueAt(tbDosis.getSelectedRow(), 3) + "");

            amdosis.ConversionDosisMin();
            amdosis.ConversionDosisMax();

            amdosis.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "No se seleccionó ninguna formulación", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnModificarDosisActionPerformed

    private void btnModificarDosisKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnModificarDosisKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModificarDosisKeyReleased

    private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyTyped
        metodos.FiltroDeCaracteres(evt);
    }//GEN-LAST:event_txtBuscarKeyTyped

    int horizontal;
    int vertical;

    private void ModoEdicion(boolean valor) {
        txtBuscar.setEnabled(!valor);

        /*//System.out.println("tabla color background " + tbPrincipal.getb);
        if (valor == true) { //Si esta en modo edicion cambia color tabla
            tbPrincipal.setBackground(Color.red);
            tbPrincipal.setForeground(Color.blue);
        } else {
            //metodos.CambiarColorAlternadoTabla(tbPrincipal, new Color(255, 255, 255), new Color(57, 105, 138));
        }*/
        btnNuevo.setEnabled(!valor);
        btnModificar.setEnabled(!valor);
        btnEliminar.setEnabled(!valor);
        btnGuardar.setEnabled(valor);
        btnCancelar.setEnabled(valor);

        if (valor == true) {
            horizontal = HORIZONTAL_SCROLLBAR_NEVER;
            vertical = VERTICAL_SCROLLBAR_NEVER;
        } else {
            horizontal = HORIZONTAL_SCROLLBAR_AS_NEEDED;
            vertical = VERTICAL_SCROLLBAR_AS_NEEDED;
        }
        scpPrincipal.setHorizontalScrollBarPolicy(horizontal);
        scpPrincipal.setVerticalScrollBarPolicy(vertical);
        tbPrincipal.setEnabled(!valor);

        txtNombreComercial.setEnabled(valor);
        txtNRegistro.setEnabled(valor);
        cbTipoAgroquimico.setEnabled(valor);
        btnTipoAgroquimico.setEnabled(valor);
        cbFormulacion.setEnabled(valor);
        btnFormulacion.setEnabled(valor);
        tbIngrActivos.setEnabled(valor);
        btnMas.setEnabled(valor);
        btnMenos.setEnabled(false);
        tbDosis.setEnabled(valor);
        btnMasDosis.setEnabled(valor);
        btnModificarDosis.setEnabled(false);
        btnMenosDosis.setEnabled(false);
        cbFabricante.setEnabled(valor);
        btnFabricante.setEnabled(valor);
        cbEmpresaRegistrante.setEnabled(valor);
        btnEmpresaRegistrante.setEnabled(valor);
        cbClaseProducto.setEnabled(valor);
        btnTipoProducto.setEnabled(valor);
        btnCargarImagen.setEnabled(valor);
        try {
            btnEliminarImagen.setEnabled(!(lbImagen.getIcon().toString().equals(imagendefault.toString())));
        } catch (Exception e) {
            System.out.println("Error al comparar imagen con imagendefault");
        }
        jtpEdicion.setEnabled(valor);
        jtpEdicion.setSelectedIndex(0);
    }

    TableRowSorter trsfiltro;

    private void Limpiar() {
        txtCodigo.setText("");
        txtNombreComercial.setText("");
        txtNRegistro.setText("");
        cbFabricante.setSelectedIndex(-1);
        cbTipoAgroquimico.setSelectedIndex(-1);
        cbFormulacion.setSelectedIndex(-1);

        cbEmpresaRegistrante.setSelectedIndex(-1);
        cbClaseProducto.setSelectedIndex(-1);

        URL url = this.getClass().getResource("/forms/producto/iconos/IconoProductoSinFoto.png");
        lbImagen.setIcon(new ImageIcon(url));

        modeloTablaListaIA.setRowCount(0);
        modeloTablaDosis.setRowCount(0);

        ArrayAnadidosIA.clear(); //Vacia la lista de Ingredientes Activos anadidos
        ArrayEliminadosIA.clear(); //Vacia la lista de Ingredientes Activos eliminados
        ArrayEliminadosDosis.clear(); //Vacia la lista de Ingredientes Activos eliminados

        lbEstado.setText("");

        txtBuscar.requestFocus();
        System.out.println("Limpiar");
    }

    @SuppressWarnings("ConvertToTryWithResources")
    public void RegistroNuevo() {
        try {
            if (!txtNombreComercial.getText().trim().isEmpty()
                    && !txtNRegistro.getText().trim().isEmpty()
                    && !(cbFabricante.getSelectedIndex() == -1) && !(cbTipoAgroquimico.getSelectedIndex() == -1)
                    && !(cbFormulacion.getSelectedIndex() == -1) && !(tbIngrActivos.getRowCount() == 0) //Si tabla de Ingredientes Activos es vacio
                    && !(cbEmpresaRegistrante.getSelectedIndex() == -1) && !(cbClaseProducto.getSelectedIndex() == -1)) {
                int confirmado = JOptionPane.showConfirmDialog(null, "¿Esta seguro crear este nuevo registro?", "Confirmación", JOptionPane.YES_OPTION);

                if (JOptionPane.YES_OPTION == confirmado) {
                    try {
                        //Guardar el producto
                        String nombrecomercial = txtNombreComercial.getText();
                        int idfabricante = cbFabricante.getItemAt(cbFabricante.getSelectedIndex()).getId();
                        int idtipoagroquimico = cbTipoAgroquimico.getItemAt(cbTipoAgroquimico.getSelectedIndex()).getId();
                        int idempresaregistrante = cbEmpresaRegistrante.getItemAt(cbEmpresaRegistrante.getSelectedIndex()).getId();
                        int idtipoproducto = cbClaseProducto.getItemAt(cbClaseProducto.getSelectedIndex()).getId();
                        int idformulacion = cbFormulacion.getItemAt(cbFormulacion.getSelectedIndex()).getId();
                        String nregistro = txtNRegistro.getText();

                        String sentencia = "CALL SP_ProductoAlta ('" + nombrecomercial + "','" + idfabricante + "','" + idtipoagroquimico
                                + "','" + idempresaregistrante + "','" + idtipoproducto + "','" + idformulacion + "','" + nregistro + "')";
                        System.out.println("Insertar registro: " + sentencia);

                        Connection con = (Connection) Conexion.GetConnection();
                        Statement st = (Statement) con.createStatement();
                        st.executeUpdate(sentencia);
                        con.close();
                        st.close();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error al guardar Producto... " + e, "Error", JOptionPane.ERROR_MESSAGE);
                        if (e.getErrorCode() == 1062) { //Si el codigo del error es 1062
                            JOptionPane.showMessageDialog(null, "El número de registro SENAVE ya existe... " + e.getMessage());
                            txtNRegistro.requestFocus();
                        }
                        return;
                    }

                    //Guardar sus ingredientes activos
                    String idultimoproducto = "";
                    try {
                        Conexion con = metodos.ObtenerRSSentencia("SELECT MAX(pro_codigo) AS idultimoproducto FROM producto");
                        while (con.rs.next()) {
                            idultimoproducto = con.rs.getString("idultimoproducto");
                        }
                        con.DesconectarBasedeDatos();
                    } catch (SQLException ex) {
                        Logger.getLogger(ABMProducto.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("No se pudo obtener el idultimoproducto: " + idultimoproducto);
                    }

                    String idingredienteactivo;
                    int cantidadfilas = tbIngrActivos.getRowCount();
                    try {
                        Connection con = (Connection) Conexion.GetConnection();
                        Statement st = (Statement) con.createStatement();
                        for (int i = 0; i < cantidadfilas; i++) { //Obtiene y guarda el id de cada ingrediente activo anadido
                            idingredienteactivo = tbIngrActivos.getValueAt(i, 0).toString();

                            String sentencia = "CALL SP_ListaIngredientesActivosAlta ('" + idultimoproducto + "','" + idingredienteactivo + "')";
                            System.out.println("Insertar registro lia: " + sentencia);
                            st.executeUpdate(sentencia); //Ejecuta la sentencia 
                        }
                        con.close();
                        st.close();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error al guardar lista de Ingredientes Activos del producto... " + e, "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    //Guardar dosis recomendadas
                    String idproducto, idcultivo;
                    double dosismin, dosismax;
                    cantidadfilas = tbDosis.getRowCount();
                    try {
                        Connection con = (Connection) Conexion.GetConnection();
                        Statement st = (Statement) con.createStatement();
                        for (int i = 0; i < cantidadfilas; i++) { //Obtiene y guarda el id de cada ingrediente activo anadido
                            idproducto = idultimoproducto;
                            idcultivo = tbDosis.getValueAt(i, 4).toString();
                            String[] cortaString = tbDosis.getValueAt(i, 1).toString().split(" ");
                            dosismin = Double.parseDouble(cortaString[0].replace(",", "."));
                            cortaString = tbDosis.getValueAt(i, 2).toString().split(" ");
                            dosismax = Double.parseDouble(cortaString[0].replace(",", "."));

                            String sentencia = "CALL SP_DosisAlta ('" + idproducto + "','" + idcultivo + "','" + dosismin + "','" + dosismax + "')";
                            System.out.println("Insertar registro dosis recomendada: " + sentencia);
                            st.executeUpdate(sentencia); //Ejecuta la sentencia 
                        }
                        con.close();
                        st.close();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error al guardar Dosis recomendada del producto... " + e, "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    //Guardarimagen
                    metodosimagen.GuardarImagen("\\src\\forms\\producto\\imagenes\\image_" + idultimoproducto);

                    JOptionPane.showMessageDialog(this, "El registro se agregó correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                    ModoEdicion(false);
                    Limpiar();
                    TablaPrincipalConsulta(txtBuscar.getText());

                    //Seleccionar registro nuevo en la tabla
                    for (int i = 0; i < tbPrincipal.getRowCount(); i++) {
                        if (tbPrincipal.getValueAt(i, 0).equals(idultimoproducto)) {
                            tbPrincipal.changeSelection(i, 1, false, false);
                            return;
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Completa todos los campos marcados con *", "Error", JOptionPane.ERROR_MESSAGE);
                txtNombreComercial.requestFocus();
            }
        } catch (HeadlessException ex) {
            System.out.println("Error al guardar registro " + ex);
        }
    }

    @SuppressWarnings("ConvertToTryWithResources")
    public void RegistroModificar() {
        //guarda los datos que se han modificado en los campos
        String codigo = txtCodigo.getText();
        String nombrecomercial = txtNombreComercial.getText();
        int idfabricante = cbFabricante.getItemAt(cbFabricante.getSelectedIndex()).getId();
        int idtipoagroquimico = cbTipoAgroquimico.getItemAt(cbTipoAgroquimico.getSelectedIndex()).getId();
        int idempresaregistrante = cbEmpresaRegistrante.getItemAt(cbEmpresaRegistrante.getSelectedIndex()).getId();
        int idtipoproducto = cbClaseProducto.getItemAt(cbClaseProducto.getSelectedIndex()).getId();
        int idformulacion = cbFormulacion.getItemAt(cbFormulacion.getSelectedIndex()).getId();

        String nregistro = txtNRegistro.getText();

        //si los datos son diferentes de vacios
        if (!txtNombreComercial.getText().trim().isEmpty() && !txtNRegistro.getText().trim().isEmpty()
                && !(cbFabricante.getSelectedIndex() == -1) && !(cbTipoAgroquimico.getSelectedIndex() == -1)
                && !(cbFormulacion.getSelectedIndex() == -1) && !(tbIngrActivos.getRowCount() == 0) //Si tabla de Ingredientes Activos es vacio
                && !(cbEmpresaRegistrante.getSelectedIndex() == -1) && !(cbClaseProducto.getSelectedIndex() == -1)) {
            int confirmado = JOptionPane.showConfirmDialog(null, "¿Esta seguro de modificar este registro?", "Confirmación", JOptionPane.YES_OPTION);
            if (JOptionPane.YES_OPTION == confirmado) {
                String sentencia = "CALL SP_ProductoModificar('" + codigo + "','" + nombrecomercial + "','" + idfabricante + "','"
                        + idtipoagroquimico + "','" + idempresaregistrante + "','" + idtipoproducto + "','" + idformulacion + "','" + nregistro + "')";
                System.out.println("Actualizar registro: " + sentencia);
                getToolkit().beep(); //Suena
                try {
                    Connection con = conexion.Conexion.GetConnection();
                    PreparedStatement pst = con.prepareStatement(sentencia);
                    pst.executeUpdate();
                    con.close();
                    pst.close();
                } catch (SQLException ex) {
                    System.out.println("Error al modificar registro " + ex);
                    return;
                }

                //Guardar la Lista modificada de Ingredientes Activos
                int idproducto = Integer.parseInt(txtCodigo.getText());
                int cantidadfilas = tbIngrActivos.getRowCount();
                int idIA;

                try {
                    //Busca y compara todos los IAs eliminados con lo que hay en tbIngrActivos
                    Connection con = conexion.Conexion.GetConnection();
                    PreparedStatement pst = con.prepareStatement(sentencia);;

                    for (int i = 0; i < ArrayEliminadosIA.size(); i++) {
                        for (int c = 0; c < cantidadfilas; c++) {
                            idIA = Integer.parseInt(tbIngrActivos.getValueAt(c, 0).toString());
                            if (idIA == Integer.parseInt(ArrayEliminadosIA.get(i))) { //Si el id del array coincide con un id de la tabla
                                System.out.println("El registro ha ser eliminado existe en la tabla");
                            } else {  //Elimina de la bd                          
                                try {
                                    sentencia = "CALL SP_ListaIngredientesActivosEliminar('" + idproducto + "','" + ArrayEliminadosIA.get(i) + "')";
                                    pst = con.prepareStatement(sentencia);
                                    pst.executeUpdate();
                                    System.out.println("Se ha eliminado el registro del IA de la lista, idIngredienteActivo: " + ArrayEliminadosIA.get(i) + "sentencia " + sentencia);
                                } catch (SQLException ex) {
                                    System.out.println("Error al eliminar el ingrediente activo de la lista " + ex);
                                }
                            }
                        }
                    }
                    con.close();
                    pst.close();

                    //Elimina las dosis de la lista de eliminados
                    con = (Connection) Conexion.GetConnection();
                    Statement st = con.createStatement();
                    @SuppressWarnings("LocalVariableHidesMemberVariable")
                    String iddosis;
                    for (int i = 0; i < ArrayEliminadosDosis.size(); i++) {
                        iddosis = ArrayEliminadosDosis.get(i);
                        if (iddosis.equals("") == false) {
                            sentencia = "CALL SP_DosisEliminar('" + iddosis + "')";
                            pst = con.prepareStatement(sentencia);
                            pst.executeUpdate();
                            System.out.println("Dosis recomedado eliminado: " + sentencia);
                        }
                    }
                    con.close();
                    st.close();
                } catch (SQLException ex) {
                    System.out.println("Error al realizar consulta de ingredientes activos... " + ex);
                    Logger.getLogger(ABMProducto.class.getName()).log(Level.SEVERE, null, ex);
                }

                //Guardar dosis recomendadas
                int idcultivo;
                double dosismin, dosismax;
                idproducto = Integer.parseInt(txtCodigo.getText());
                cantidadfilas = tbDosis.getRowCount();
                try {
                    Connection con = (Connection) Conexion.GetConnection();
                    Statement st = (Statement) con.createStatement();
                    for (int i = 0; i < cantidadfilas; i++) { //Obtiene y guarda el id de cada ingrediente activo anadido
                        codigo = tbDosis.getValueAt(i, 0).toString();
                        if (codigo.isEmpty() == false) { //Guarda dosis recomendada modificado
                            idcultivo = Integer.parseInt(tbDosis.getValueAt(i, 4).toString());
                            String[] cortaString = tbDosis.getValueAt(i, 1).toString().split(" ");
                            dosismin = Double.parseDouble(cortaString[0].replace(",", "."));
                            cortaString = tbDosis.getValueAt(i, 2).toString().split(" ");
                            dosismax = Double.parseDouble(cortaString[0].replace(",", "."));

                            sentencia = "CALL SP_DosisModificar('" + codigo + "','" + idproducto + "','" + idcultivo + "','" + dosismin + "','" + dosismax + "')";
                            System.out.println("Modificar registro dosis recomendada: " + sentencia);
                            st.executeUpdate(sentencia); //Ejecuta la sentencia 
                        } else { //Guardar dosis recomendada nuevo
                            idcultivo = Integer.parseInt(tbDosis.getValueAt(i, 4).toString());
                            String[] cortaString = tbDosis.getValueAt(i, 1).toString().split(" ");
                            dosismin = Double.parseDouble(cortaString[0].replace(",", "."));
                            cortaString = tbDosis.getValueAt(i, 2).toString().split(" ");
                            dosismax = Double.parseDouble(cortaString[0].replace(",", "."));

                            sentencia = "CALL SP_DosisAlta('" + idproducto + "','" + idcultivo + "','" + dosismin + "','" + dosismax + "')";
                            System.out.println("Insertar registro dosis recomendada: " + sentencia);
                            st.executeUpdate(sentencia); //Ejecuta la sentencia 
                        }
                    }
                    con.close();
                    st.close();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error al guardar Dosis recomendada del producto... " + e, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //Eliminar o guardar imagen
                String rutaimagen = System.getProperty("user.dir") + "\\src\\forms\\producto\\imagenes\\image_" + txtCodigo.getText();
                if (lbImagen.getIcon() == null) { //Elimina Imagen
                    metodosimagen.EliminarImagen(rutaimagen);
                    System.out.println("Se elimino la imagen: " + rutaimagen);
                } else {//Guarda Imagen
                    metodosimagen.GuardarImagen(rutaimagen); //+ metodos.ObtenerIdUltimoRegistro("estab_codigo", "establecimiento"));
                    System.out.println("Se modificó la imagen: " + rutaimagen);
                }
            }
            JOptionPane.showMessageDialog(null, "Registro modificado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
            ModoEdicion(false);
            Limpiar();
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

                    String sentencia = "CALL SP_ProductoEliminar(" + codigo + ")";
                    try {
                        Connection con = conexion.Conexion.GetConnection();
                        PreparedStatement pst = con.prepareStatement(sentencia);
                        pst.executeUpdate();
                        con.close();
                        pst.close();
                        JOptionPane.showMessageDialog(null, "Registro eliminado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error al eliminar registro " + ex);
                    }

                    //Eliminar imagen
                    try {
                        File fichero = new File("src/forms/producto/imagenes/" + "image_" + codigo + ".jpg");
                        if (fichero.delete()) {
                            System.out.println("La imagen ha sido borrado satisfactoriamente");
                        } else {
                            System.out.println("La imagen no puede ser borrado por que no se encuentra probablemente");
                        }
                    } catch (Exception e) {
                        System.out.println("Error al querer eliminar imagen " + e);
                    }

                    ModoEdicion(false);
                    Limpiar();
                    TablaPrincipalConsulta("");
                } else {
                    txtBuscar.setText("");
                    txtBuscar.requestFocus();
                    JOptionPane.showMessageDialog(null, "Cancelado correctamente", "Información", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (HeadlessException e) {
            System.out.println("Error al intentar eliminar registro " + e);
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCargarImagen;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminarImagen;
    private javax.swing.JButton btnEmpresaRegistrante;
    private javax.swing.JButton btnFabricante;
    private javax.swing.JButton btnFormulacion;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnInforme;
    private javax.swing.JButton btnMas;
    private javax.swing.JButton btnMasDosis;
    private javax.swing.JButton btnMenos;
    private javax.swing.JButton btnMenosDosis;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnModificarDosis;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnPantallaCompleta;
    private javax.swing.JButton btnTipoAgroquimico;
    private javax.swing.JButton btnTipoProducto;
    public javax.swing.JComboBox<metodos.MetodosCombo> cbClaseProducto;
    private javax.swing.JComboBox<metodos.MetodosCombo> cbEmpresaRegistrante;
    private javax.swing.JComboBox<metodos.MetodosCombo> cbFabricante;
    private javax.swing.JComboBox cbFiltroCampo;
    private javax.swing.JComboBox<metodos.MetodosCombo> cbFormulacion;
    private javax.swing.JComboBox<metodos.MetodosCombo> cbTipoAgroquimico;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jbIImagen;
    private javax.swing.JPanel jpBanner;
    private javax.swing.JPanel jpBotones;
    private javax.swing.JPanel jpBotones2;
    private javax.swing.JPanel jpDatosGenerales;
    private javax.swing.JPanel jpPrincipal;
    private javax.swing.JPanel jpTabla;
    private javax.swing.JTabbedPane jtpEdicion;
    private javax.swing.JLabel lbBanner;
    private javax.swing.JLabel lbCantRegistros;
    private javax.swing.JLabel lbDosis;
    public javax.swing.JLabel lbEstado;
    private javax.swing.JLabel lbImagen;
    private javax.swing.JLabel lbNumRegistro;
    private javax.swing.JLabel lblBuscarCampo;
    private javax.swing.JScrollPane scpDosis;
    private javax.swing.JScrollPane scpIngrActivos;
    private javax.swing.JScrollPane scpPrincipal;
    public javax.swing.JTable tbDosis;
    public javax.swing.JTable tbIngrActivos;
    private javax.swing.JTable tbPrincipal;
    private javax.swing.JTextField txtBuscar;
    public javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtNRegistro;
    private javax.swing.JTextField txtNombreComercial;
    // End of variables declaration//GEN-END:variables
}
