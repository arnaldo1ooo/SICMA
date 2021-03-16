/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms.zafra;

import conexion.Conexion;
import java.awt.HeadlessException;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import utilidades.Metodos;
import utilidades.MetodosCombo;
import utilidades.MetodosImagen;
import utilidades.VistaCompleta;

/**
 *
 * @author Arnaldo Cantero
 */
public final class ABMZafra extends javax.swing.JDialog {

    private MetodosCombo metodoscombo = new MetodosCombo();

    public ABMZafra(java.awt.Frame parent, Boolean modal) {
        super(parent, modal);
        initComponents();

        txtBuscar.requestFocus();
        CargarCombos();
        TablaPrincipalConsulta(txtBuscar.getText()); //Trae todos los registros

        //Shortcuts 
        btnNuevo.setMnemonic(KeyEvent.VK_N); //ALT+N
        btnModificar.setMnemonic(KeyEvent.VK_M); //ALT+M
        btnEliminar.setMnemonic(KeyEvent.VK_E); //ALT+E
    }

    //-------------METODOS----------//
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
        metodoscombo.CargarComboConsulta(cbParcelaFiltro, "SELECT par_codigo, par_descripcion FROM parcela WHERE par_establecimiento = " + metodoscombo.ObtenerIDSelectCombo(cbEstablecimientoFiltro), 1);
        if (cbParcelaFiltro.getItemCount() > 0) {
            cbParcelaFiltro.setSelectedIndex(0);
        }
        ModoEdicion(false);
        CombosListo = true;
    }

    Metodos metodos = new Metodos();
    String nombresp = "SP_ZafraConsulta";
    String titlesJtabla[] = {"Codigo", "Parcela", "Tipo de Zafra", "Fecha de inicio", "Fecha de finalización", "Costo total", "Costo total estimado", "Estado actual"};
    String titlesconsulta[] = {"zaf_codigo", "par_descripcion", "zaf_tipozafra", "", "zaf_iniciofecha", "zaf_finfecha", "zaf_costototal", "zaf_costototalestimado", "zaf_estado"};

    String sentencia;
    String campoconsulta[];
    DefaultTableModel modelotabla;
    DecimalFormat formatodecimal = new DecimalFormat("#.###");
    SimpleDateFormat formatofecha = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat parseadorfecha = new SimpleDateFormat("dd-MM-yy");

    public void TablaPrincipalConsulta(String filtro) {//Realiza la consulta de los productos que tenemos en la base de datos        
        modelotabla = new DefaultTableModel(null, titlesJtabla);
        //Rellenar el combo campo buscar
        if (cbCampoBuscar.getItemCount() == 0) {//Si combo esta vacio
            for (int i = 0; i < titlesJtabla.length; i++) {
                cbCampoBuscar.addItem(titlesJtabla[i]);
            }
            cbCampoBuscar.addItem("Todos");
            cbCampoBuscar.setSelectedIndex(1);
        }

        if (cbCampoBuscar.getSelectedItem() == "Todos") {
            String todoscamposconsulta = campoconsulta[0]; //Cargar el combo campobuscar
            //Cargamos todos los titulos en un String separado por comas
            for (int i = 1; i < campoconsulta.length; i++) {
                todoscamposconsulta = todoscamposconsulta + ", " + campoconsulta[i];
            }
            sentencia = "CALL " + nombresp + " ('" + todoscamposconsulta + "', '" + filtro + " ('" + metodoscombo.ObtenerIDSelectCombo(cbParcelaFiltro) + "');";
        } else {
            sentencia = "CALL " + nombresp + " ('" + titlesconsulta[cbCampoBuscar.getSelectedIndex()] + "', '" + filtro + "', '" + metodoscombo.ObtenerIDSelectCombo(cbParcelaFiltro) + "');";
        }
        System.out.println("sentencia filtro tabla BD: " + sentencia);

        Connection connection;
        Statement st;
        ResultSet rs;
        try {
            connection = (Connection) Conexion.ConectarBasedeDatos();
            st = connection.createStatement();
            rs = st.executeQuery(sentencia);
            ResultSetMetaData mdrs = rs.getMetaData();
            int numColumns = mdrs.getColumnCount();
            Object[] registro = new Object[numColumns]; //el numero es la cantidad de columnas del rs
            while (rs.next()) {
                registro[0] = (rs.getString("zaf_codigo"));
                registro[1] = (rs.getString("par_descripcion"));
                registro[2] = (rs.getString("zaf_tipozafra"));
                registro[3] = (formatofecha.format(parseadorfecha.parse(rs.getString("zaf_iniciofecha"))));
                if (rs.getString("zaf_finfecha") != null) {
                    registro[4] = (formatofecha.format(parseadorfecha.parse(rs.getString("zaf_finfecha"))));
                }
                registro[5] = (formatodecimal.format(Double.parseDouble(rs.getString("zaf_costototal"))));
                registro[6] = (formatodecimal.format(Double.parseDouble(rs.getString("zaf_costototalestimado"))));

                registro[7] = (rs.getString("zaf_estado"));

                modelotabla.addRow(registro);//agrega el registro a la tabla  
            }
            tbPrincipal.setModel(modelotabla);//asigna a la tabla el modelo creado

            connection.close();
            st.close();
            rs.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ABMZafra.class.getName()).log(Level.SEVERE, null, ex);
        }
        metodos.AnchuraColumna(tbPrincipal);
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpPrincipal = new javax.swing.JPanel();
        jpBanner = new javax.swing.JPanel();
        lbBanner = new javax.swing.JLabel();
        jpTabla = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        scPrincipal = new javax.swing.JScrollPane();
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
        jbIImagen3 = new javax.swing.JLabel();
        cbProductor1 = new javax.swing.JComboBox<>();
        jbIImagen4 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jbIImagen5 = new javax.swing.JLabel();
        jbIImagen6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtDescripcion1 = new javax.swing.JTextField();
        jbIImagen7 = new javax.swing.JLabel();
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
        jpBotones2 = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JButton();
        btnGuardar1 = new javax.swing.JButton();
        jpTabla1 = new javax.swing.JPanel();
        lblBuscarCampo3 = new javax.swing.JLabel();
        cbProductorFiltro = new javax.swing.JComboBox();
        cbEstablecimientoFiltro = new javax.swing.JComboBox();
        lblBuscarCampo4 = new javax.swing.JLabel();
        lblBuscarCampo5 = new javax.swing.JLabel();
        cbParcelaFiltro = new javax.swing.JComboBox();

        setTitle("Ventana Zafras");
        setBackground(new java.awt.Color(45, 62, 80));
        setPreferredSize(new java.awt.Dimension(1144, 660));
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);

        jpPrincipal.setBackground(new java.awt.Color(45, 62, 80));
        jpPrincipal.setPreferredSize(new java.awt.Dimension(1144, 648));

        jpBanner.setBackground(new java.awt.Color(0, 102, 204));
        jpBanner.setPreferredSize(new java.awt.Dimension(1000, 82));

        lbBanner.setFont(new java.awt.Font("sansserif", 1, 30)); // NOI18N
        lbBanner.setForeground(new java.awt.Color(255, 255, 255));
        lbBanner.setText("Zafras");
        lbBanner.setMaximumSize(new java.awt.Dimension(1100, 52));
        lbBanner.setMinimumSize(new java.awt.Dimension(1100, 52));
        lbBanner.setPreferredSize(new java.awt.Dimension(1100, 52));

        javax.swing.GroupLayout jpBannerLayout = new javax.swing.GroupLayout(jpBanner);
        jpBanner.setLayout(jpBannerLayout);
        jpBannerLayout.setHorizontalGroup(
            jpBannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBannerLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(lbBanner, javax.swing.GroupLayout.PREFERRED_SIZE, 1200, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jpBannerLayout.setVerticalGroup(
            jpBannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBannerLayout.createSequentialGroup()
                .addComponent(lbBanner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
        scPrincipal.setViewportView(tbPrincipal);

        lblBuscarCampo.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        lblBuscarCampo.setForeground(new java.awt.Color(255, 255, 255));
        lblBuscarCampo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBuscarCampo.setText("Buscar por:");

        cbCampoBuscar.setName("CampoBuscarPrincipal"); // NOI18N

        javax.swing.GroupLayout jpTablaLayout = new javax.swing.GroupLayout(jpTabla);
        jpTabla.setLayout(jpTablaLayout);
        jpTablaLayout.setHorizontalGroup(
            jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTablaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 671, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpTablaLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblBuscarCampo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbCampoBuscar, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(8, Short.MAX_VALUE))
        );
        jpTablaLayout.setVerticalGroup(
            jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTablaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cbCampoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBuscarCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(scPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
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
        btnNuevo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnNuevoKeyPressed(evt);
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
            .addGroup(jpBotonesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReporte, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpBotonesLayout.setVerticalGroup(
            jpBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBotonesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jtpEdicion.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jtpEdicion.setName(""); // NOI18N

        jpDatosGenerales.setBackground(new java.awt.Color(45, 62, 80));
        jpDatosGenerales.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Código:");

        txtCodigo.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtCodigo.setEnabled(false);

        jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Costo Total:");

        txtDescripcion.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtDescripcion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDescripcion.setText("0");
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

        jbIImagen.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jbIImagen.setForeground(new java.awt.Color(255, 255, 255));
        jbIImagen.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jbIImagen.setText("Parcela:");

        cbProductor.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        cbProductor.setEnabled(false);
        cbProductor.setMinimumSize(new java.awt.Dimension(55, 31));
        cbProductor.setName("Productor"); // NOI18N
        cbProductor.setPreferredSize(new java.awt.Dimension(55, 31));
        cbProductor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbProductorActionPerformed(evt);
            }
        });

        jbIImagen3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jbIImagen3.setForeground(new java.awt.Color(255, 255, 255));
        jbIImagen3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jbIImagen3.setText("Fecha de inicio:");

        cbProductor1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        cbProductor1.setEnabled(false);
        cbProductor1.setMinimumSize(new java.awt.Dimension(55, 31));
        cbProductor1.setName("Productor"); // NOI18N
        cbProductor1.setPreferredSize(new java.awt.Dimension(55, 31));
        cbProductor1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbProductor1ActionPerformed(evt);
            }
        });

        jbIImagen4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jbIImagen4.setForeground(new java.awt.Color(255, 255, 255));
        jbIImagen4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jbIImagen4.setText("Tipo de Zafra:");

        jbIImagen5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jbIImagen5.setForeground(new java.awt.Color(255, 255, 255));
        jbIImagen5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jbIImagen5.setText("Fecha de Finalización:");

        jbIImagen6.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jbIImagen6.setForeground(new java.awt.Color(255, 255, 255));
        jbIImagen6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jbIImagen6.setText("$");

        jLabel11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Costo Total Estimado:");

        txtDescripcion1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtDescripcion1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDescripcion1.setText("0");
        txtDescripcion1.setEnabled(false);
        txtDescripcion1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDescripcion1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescripcion1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcion1KeyTyped(evt);
            }
        });

        jbIImagen7.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jbIImagen7.setForeground(new java.awt.Color(255, 255, 255));
        jbIImagen7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jbIImagen7.setText("$");

        javax.swing.GroupLayout jpDatosGeneralesLayout = new javax.swing.GroupLayout(jpDatosGenerales);
        jpDatosGenerales.setLayout(jpDatosGeneralesLayout);
        jpDatosGeneralesLayout.setHorizontalGroup(
            jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDatosGeneralesLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbIImagen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbIImagen4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jpDatosGeneralesLayout.createSequentialGroup()
                        .addComponent(jbIImagen3, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)))
                .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDatosGeneralesLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbProductor, 0, 321, Short.MAX_VALUE)
                            .addGroup(jpDatosGeneralesLayout.createSequentialGroup()
                                .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDescripcion1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(4, 4, 4)
                                .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jbIImagen7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jbIImagen6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbProductor1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jpDatosGeneralesLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbIImagen5, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(361, Short.MAX_VALUE))
        );
        jpDatosGeneralesLayout.setVerticalGroup(
            jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDatosGeneralesLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cbProductor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbIImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbProductor1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbIImagen4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbIImagen7, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtDescripcion1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbIImagen6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbIImagen3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jbIImagen5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jtpEdicion.addTab("Datos Generales", jpDatosGenerales);

        jpUbicacion.setBackground(new java.awt.Color(45, 62, 80));
        jpUbicacion.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
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

        jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
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

        jLabel9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
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

        jbIImagen1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jbIImagen1.setForeground(new java.awt.Color(255, 255, 255));
        jbIImagen1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jbIImagen1.setText("Departamento*:");

        cbDepartamento.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        cbDepartamento.setEnabled(false);
        cbDepartamento.setMinimumSize(new java.awt.Dimension(55, 31));
        cbDepartamento.setName("Departamento"); // NOI18N
        cbDepartamento.setPreferredSize(new java.awt.Dimension(55, 31));
        cbDepartamento.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbDepartamentoItemStateChanged(evt);
            }
        });

        cbDistrito.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        cbDistrito.setEnabled(false);
        cbDistrito.setMinimumSize(new java.awt.Dimension(55, 31));
        cbDistrito.setName("Distrito"); // NOI18N
        cbDistrito.setPreferredSize(new java.awt.Dimension(55, 31));

        jbIImagen2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
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
                .addGroup(jpUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cbDistrito, javax.swing.GroupLayout.Alignment.LEADING, 0, 243, Short.MAX_VALUE)
                    .addComponent(txtY, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtX, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtLocalidad, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbDepartamento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(100, 100, 100)
                .addComponent(lbImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(jpUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnEliminarImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPantallaCompleta, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCargarImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(324, Short.MAX_VALUE))
        );
        jpUbicacionLayout.setVerticalGroup(
            jpUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpUbicacionLayout.createSequentialGroup()
                .addGroup(jpUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpUbicacionLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jpUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(cbDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbIImagen1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(cbDistrito, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbIImagen2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtLocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jpUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtX, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txtY, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jpUbicacionLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jpUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpUbicacionLayout.createSequentialGroup()
                                .addComponent(btnCargarImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminarImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPantallaCompleta, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jtpEdicion.addTab("Ubicación", jpUbicacion);

        jpBotones2.setBackground(new java.awt.Color(45, 62, 80));
        jpBotones2.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

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

        btnGuardar1.setBackground(new java.awt.Color(0, 153, 255));
        btnGuardar1.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        btnGuardar1.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Iconos20x20/IconoGuardar.png"))); // NOI18N
        btnGuardar1.setText("Guardar");
        btnGuardar1.setToolTipText("Inserta el nuevo registro");
        btnGuardar1.setEnabled(false);
        btnGuardar1.setPreferredSize(new java.awt.Dimension(128, 36));
        btnGuardar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardar1ActionPerformed(evt);
            }
        });
        btnGuardar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnGuardar1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jpBotones2Layout = new javax.swing.GroupLayout(jpBotones2);
        jpBotones2.setLayout(jpBotones2Layout);
        jpBotones2Layout.setHorizontalGroup(
            jpBotones2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBotones2Layout.createSequentialGroup()
                .addContainerGap(158, Short.MAX_VALUE)
                .addComponent(btnCancelar)
                .addContainerGap())
            .addGroup(jpBotones2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpBotones2Layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(btnGuardar1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(138, Short.MAX_VALUE)))
        );
        jpBotones2Layout.setVerticalGroup(
            jpBotones2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBotones2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jpBotones2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpBotones2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(btnGuardar1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jpTabla1.setBackground(new java.awt.Color(45, 62, 80));
        jpTabla1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Filtrar ", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Yu Gothic UI Semibold", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        lblBuscarCampo3.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        lblBuscarCampo3.setForeground(new java.awt.Color(255, 255, 255));
        lblBuscarCampo3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBuscarCampo3.setText(" Productor");
        lblBuscarCampo3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        cbProductorFiltro.setFont(new java.awt.Font("sansserif", 0, 10)); // NOI18N
        cbProductorFiltro.setName("ProductorFiltro"); // NOI18N
        cbProductorFiltro.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbProductorFiltroItemStateChanged(evt);
            }
        });

        cbEstablecimientoFiltro.setFont(new java.awt.Font("sansserif", 0, 10)); // NOI18N
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

        lblBuscarCampo5.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        lblBuscarCampo5.setForeground(new java.awt.Color(255, 255, 255));
        lblBuscarCampo5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBuscarCampo5.setText(" Parcela");
        lblBuscarCampo5.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        cbParcelaFiltro.setFont(new java.awt.Font("sansserif", 0, 10)); // NOI18N
        cbParcelaFiltro.setName("ParcelaFiltro"); // NOI18N

        javax.swing.GroupLayout jpTabla1Layout = new javax.swing.GroupLayout(jpTabla1);
        jpTabla1.setLayout(jpTabla1Layout);
        jpTabla1Layout.setHorizontalGroup(
            jpTabla1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBuscarCampo3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblBuscarCampo4, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
            .addComponent(lblBuscarCampo5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cbProductorFiltro, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cbEstablecimientoFiltro, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cbParcelaFiltro, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpTabla1Layout.setVerticalGroup(
            jpTabla1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTabla1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblBuscarCampo3)
                .addGap(1, 1, 1)
                .addComponent(cbProductorFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblBuscarCampo4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(cbEstablecimientoFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblBuscarCampo5)
                .addGap(1, 1, 1)
                .addComponent(cbParcelaFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        cbProductorFiltro.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout jpPrincipalLayout = new javax.swing.GroupLayout(jpPrincipal);
        jpPrincipal.setLayout(jpPrincipalLayout);
        jpPrincipalLayout.setHorizontalGroup(
            jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpBanner, javax.swing.GroupLayout.DEFAULT_SIZE, 1237, Short.MAX_VALUE)
            .addGroup(jpPrincipalLayout.createSequentialGroup()
                .addComponent(jtpEdicion, javax.swing.GroupLayout.PREFERRED_SIZE, 1139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jpPrincipalLayout.createSequentialGroup()
                .addGroup(jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpPrincipalLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jpTabla1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpBotones, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpPrincipalLayout.createSequentialGroup()
                        .addGap(417, 417, 417)
                        .addComponent(jpBotones2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpPrincipalLayout.setVerticalGroup(
            jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPrincipalLayout.createSequentialGroup()
                .addComponent(jpBanner, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpPrincipalLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jpBotones, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                            .addComponent(jpTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpPrincipalLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jpTabla1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtpEdicion, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpBotones2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jpBanner.getAccessibleContext().setAccessibleName("");
        jtpEdicion.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpPrincipal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 603, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        getAccessibleContext().setAccessibleName("ABMZafra");

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
            VistaPrevia();
        }
    }//GEN-LAST:event_tbPrincipalMouseClicked

    Double sum;
    int cont;

    private void VistaPrevia() {
        txtCodigo.setText(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 0).toString());
        txtDescripcion.setText(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 1).toString());
        metodoscombo.SetSelectedNombreItem(cbProductor, tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 2).toString());

        metodoscombo.SetSelectedNombreItem(cbDepartamento, tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 3).toString());
        metodoscombo.SetSelectedNombreItem(cbDistrito, tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 4).toString());
        txtLocalidad.setText(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 5).toString());
        txtX.setText(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 6).toString());
        txtY.setText(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 7).toString());

        metodosimagen.LeerImagen(lbImagen, "src/forms/zafra/establecimiento/imagenescroquis/imagecroquis_" + txtCodigo.getText());
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

    private void tbPrincipalMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPrincipalMousePressed
        if (tbPrincipal.isEnabled() == true) {
            btnModificar.setEnabled(true);
            btnEliminar.setEnabled(true);
            VistaPrevia();
        }
    }//GEN-LAST:event_tbPrincipalMousePressed

    private void btnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteActionPerformed

    }//GEN-LAST:event_btnReporteActionPerformed

    private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyTyped

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
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLocalidadKeyReleased

    private void txtLocalidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLocalidadKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLocalidadKeyTyped

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
        metodoscombo.CargarComboConsulta(cbDistrito, "SELECT dis_codigo, dis_descripcion FROM distrito WHERE dis_departamento = " + metodoscombo.ObtenerIDSelectCombo(cbDepartamento), 1);
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

        vistacompleta.setVisible(true);
    }//GEN-LAST:event_btnPantallaCompletaActionPerformed

    private void cbProductorFiltroItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbProductorFiltroItemStateChanged
        if (CombosListo == true) {
            cbEstablecimientoFiltro.removeAllItems();
            cbParcelaFiltro.removeAllItems();

            metodoscombo.CargarComboConsulta(cbEstablecimientoFiltro, "SELECT estab_codigo, estab_descripcion FROM establecimiento WHERE estab_productor = " + metodoscombo.ObtenerIDSelectCombo(cbProductorFiltro), 1);
            if (cbEstablecimientoFiltro.getItemCount() > 0) {
                cbEstablecimientoFiltro.setSelectedIndex(0);
            }
            metodoscombo.CargarComboConsulta(cbParcelaFiltro, "SELECT par_codigo, par_descripcion FROM parcela WHERE par_establecimiento = " + metodoscombo.ObtenerIDSelectCombo(cbEstablecimientoFiltro), 1);
            if (cbParcelaFiltro.getItemCount() > 0) {
                cbParcelaFiltro.setSelectedIndex(0);
            }
        }
    }//GEN-LAST:event_cbProductorFiltroItemStateChanged

    private void cbEstablecimientoFiltroItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbEstablecimientoFiltroItemStateChanged
        if (CombosListo == true) {
            cbParcelaFiltro.removeAllItems();
            metodoscombo.CargarComboConsulta(cbParcelaFiltro, "SELECT par_codigo, par_descripcion FROM parcela WHERE par_establecimiento = " + metodoscombo.ObtenerIDSelectCombo(cbEstablecimientoFiltro), 1);
            if (cbParcelaFiltro.getItemCount() > 0) {
                cbParcelaFiltro.setSelectedIndex(0);
            }
        }
    }//GEN-LAST:event_cbEstablecimientoFiltroItemStateChanged

    private void btnNuevoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnNuevoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNuevoKeyPressed

    private void cbProductor1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbProductor1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbProductor1ActionPerformed

    private void txtDescripcion1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcion1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcion1KeyPressed

    private void txtDescripcion1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcion1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcion1KeyReleased

    private void txtDescripcion1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcion1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcion1KeyTyped

    private void btnGuardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardar1ActionPerformed

    private void btnGuardar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnGuardar1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardar1KeyPressed

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
        cbDepartamento.setEnabled(valor);
        cbDistrito.setEnabled(valor);
        txtLocalidad.setEnabled(valor);
        txtX.setEnabled(valor);
        txtY.setEnabled(valor);
        btnCargarImagen.setEnabled(valor);

        btnNuevo.setEnabled(!valor);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnCancelar.setEnabled(valor);
        btnReporte.setEnabled(!valor);
        btnEliminarImagen.setEnabled(valor);
        txtDescripcion.requestFocus();
    }

    private void Limpiar() {
        txtCodigo.setText("");
        txtDescripcion.setText("");
        cbProductor.setSelectedIndex(-1);
        cbDepartamento.setSelectedIndex(0);
        cbDistrito.setSelectedIndex(0);
        txtLocalidad.setText("");
        txtX.setText("");
        txtY.setText("");

        lbImagen.setIcon(null);
        lbImagen.setText("CROQUIS");

        TablaPrincipalConsulta(txtBuscar.getText()); //Trae todos los registros

        txtBuscar.requestFocus();
    }

    @SuppressWarnings("ConvertToTryWithResources")
    public void RegistroNuevo() {
        try {
            if (!txtDescripcion.getText().trim().isEmpty()
                    && !(cbProductor.getSelectedIndex() == -1)
                    && !(cbDistrito.getSelectedIndex() == -1)) {
                String descripcion = txtDescripcion.getText();
                int idproductor = metodoscombo.ObtenerIDSelectCombo(cbProductor);
                int iddistrito = metodoscombo.ObtenerIDSelectCombo(cbDistrito);
                String localidad = txtLocalidad.getText();
                String X = txtX.getText();
                String Y = txtY.getText();

                int confirmado = JOptionPane.showConfirmDialog(null, "¿Esta seguro crear este nuevo registro?", "Confirmación", JOptionPane.YES_OPTION);

                if (JOptionPane.YES_OPTION == confirmado) {
                    //REGISTRAR NUEVO
                    try {
                        Conexion con = new Conexion();
                        String sentencia = "CALL SP_EstablecimientoAlta ('" + descripcion + "','" + idproductor + "','" + iddistrito + "','" + localidad + "','" + X + "','" + Y + "')";
                        System.out.println("Insertar registro: " + sentencia);

                        JOptionPane.showMessageDialog(this, "Se agrego correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                        ModoEdicion(false);
                        Limpiar();
                        con.DesconectarBasedeDatos();

                        //Guardar Croquis
                        metodosimagen.GuardarImagen("src/forms/zafra/establecimiento/imagenescroquis/imagecroquis_" + con.ObtenerUltimoID("estab_codigo FROM establecimiento"));

                    } catch (HeadlessException ex) {
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
        int idproductor = metodoscombo.ObtenerIDSelectCombo(cbProductor);
        int iddistrito = metodoscombo.ObtenerIDSelectCombo(cbDistrito);
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
                        //metodosimagen.GuardarImagen("src/forms/zafra/establecimiento/imagenescroquis/imagecroquis_" + con("estab_codigo", "establecimiento"));
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
    private javax.swing.JButton btnGuardar1;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnPantallaCompleta;
    private javax.swing.JButton btnReporte;
    private javax.swing.JComboBox cbCampoBuscar;
    public javax.swing.JComboBox<utilidades.MetodosCombo> cbDepartamento;
    public javax.swing.JComboBox<utilidades.MetodosCombo> cbDistrito;
    private javax.swing.JComboBox cbEstablecimientoFiltro;
    private javax.swing.JComboBox cbParcelaFiltro;
    public javax.swing.JComboBox<utilidades.MetodosCombo> cbProductor;
    public javax.swing.JComboBox<utilidades.MetodosCombo> cbProductor1;
    private javax.swing.JComboBox cbProductorFiltro;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jbIImagen;
    private javax.swing.JLabel jbIImagen1;
    private javax.swing.JLabel jbIImagen2;
    private javax.swing.JLabel jbIImagen3;
    private javax.swing.JLabel jbIImagen4;
    private javax.swing.JLabel jbIImagen5;
    private javax.swing.JLabel jbIImagen6;
    private javax.swing.JLabel jbIImagen7;
    private javax.swing.JPanel jpBanner;
    private javax.swing.JPanel jpBotones;
    private javax.swing.JPanel jpBotones2;
    private javax.swing.JPanel jpDatosGenerales;
    private javax.swing.JPanel jpPrincipal;
    private javax.swing.JPanel jpTabla;
    private javax.swing.JPanel jpTabla1;
    private javax.swing.JPanel jpUbicacion;
    private javax.swing.JTabbedPane jtpEdicion;
    private javax.swing.JLabel lbBanner;
    private javax.swing.JLabel lbImagen;
    private javax.swing.JLabel lblBuscarCampo;
    private javax.swing.JLabel lblBuscarCampo3;
    private javax.swing.JLabel lblBuscarCampo4;
    private javax.swing.JLabel lblBuscarCampo5;
    private javax.swing.JScrollPane scPrincipal;
    private javax.swing.JTable tbPrincipal;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtDescripcion1;
    private javax.swing.JTextField txtLocalidad;
    private javax.swing.JTextField txtX;
    private javax.swing.JTextField txtY;
    // End of variables declaration//GEN-END:variables
}
