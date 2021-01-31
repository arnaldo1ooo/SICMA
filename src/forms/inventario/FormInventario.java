/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms.inventario;

import conexion.Conexion;
import forms.inventario.entrada.AMEntrada;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import metodos.Metodos;
import metodos.MetodosCombo;

/**
 *
 * @author Lic. Arnaldo Cantero
 */
public class FormInventario extends javax.swing.JDialog {

    private Metodos metodos = new Metodos();
    private MetodosCombo metodoscombo = new MetodosCombo();
    private Conexion con;

    public FormInventario(java.awt.Frame parent, Boolean modal) {
        super(parent, modal);
        initComponents();

        txtFiltro.requestFocus();

        CargarCombos();

        TablaConsulta(txtFiltro.getText()); //Trae todos los registros
    }

    //-------------METODOS----------//
    private void CargarCombos() {
        metodoscombo.CargarComboBox(cbProductor, "SELECT prod_codigo, CONCAT(prod_nombre,' ', prod_apellido) FROM productor ORDER BY prod_nombre");
        metodoscombo.CargarComboBox(cbEstablecimiento, "SELECT estab_codigo, estab_descripcion FROM establecimiento WHERE estab_productor = " + metodoscombo.ObtenerIdComboBox(cbProductor) + " ORDER BY estab_descripcion");
        if (cbProductor.getItemCount() > 0) {
            cbProductor.setSelectedIndex(0);
        }
        if (cbEstablecimiento.getItemCount() > 0) {
            cbEstablecimiento.setSelectedIndex(0);
        }
    }

    private void TablaConsulta(String filtro) {//Realiza la consulta de los productos que tenemos en la base de datos
        String titlesJtabla[] = {"Código", "Producto", "Presentación", "Última entrada", "Última salida", "Cant entrada",
            "Cant salida", "Existencia (unidades)", "Existencia (Kg/Lt)", "Costo total ($)"};
        String titlesconsulta[] = {"in_codigo", "pro_descripcion", "in_presentacion", "in_fechaultimaentrada", "in_fechaultimasalida",
            "in_cantidadentrada", "in_cantidadsalida", "in_existencia", "in_existenciatotal", "in_costototal"};
        String nombresp = "SP_InventarioConsulta";
        int idestablecimiento = metodoscombo.ObtenerIdComboBox(cbEstablecimiento);

        ConsultaFiltroTablaBD(tbTabla, titlesJtabla, titlesconsulta, nombresp, filtro, cbCampoBuscar, idestablecimiento);
        metodos.AnchuraColumna(tbTabla);
    }

    int CantRegistros = 0;

    public void ConsultaFiltroTablaBD(JTable LaTabla, String titlesJtabla[], String campoconsulta[], String nombresp, String filtro, JComboBox cbCampoBuscar, int idestablecimiento) {
        String sentencia;
        DefaultTableModel modelotabla = new DefaultTableModel(null, titlesJtabla);

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
            sentencia = "CALL " + nombresp + " ('" + todoscamposconsulta + "', '" + filtro + "', '" + idestablecimiento + "');";
        } else {
            sentencia = "CALL " + nombresp + " ('" + campoconsulta[cbCampoBuscar.getSelectedIndex()] + "', '" + filtro + "', '" + idestablecimiento + "');";
        }

        cbCampoBuscar.setEnabled(true);

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
            String estado;
            CantRegistros = 0;
            while (rs.next()) {
                estado = EncontrarEstadoProducto(rs);
                registro[0] = (rs.getString("in_codigo"));
                registro[1] = (rs.getString("pro_descripcion"));
                registro[2] = (rs.getString("in_presentacion") + " " + estado);
                registro[3] = (rs.getString("in_fechaultimaentrada"));
                registro[4] = (rs.getString("in_fechaultimasalida"));
                registro[5] = (rs.getString("in_cantidadentrada"));
                registro[6] = (rs.getString("in_cantidadsalida"));
                registro[7] = (rs.getInt("in_existencia") + " unidades");
                registro[8] = ((rs.getDouble("in_existenciatotal") / 1000) + " " + estado);
                registro[9] = (rs.getString("in_costototal") + " $");
                modelotabla.addRow(registro);//agrega el registro a la tabla
                CantRegistros = CantRegistros + 1;
            }
            LaTabla.setModel(modelotabla);//asigna a la tabla el modelo creado

            connection.close();
            st.close();
            rs.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

        lbCantRegistros.setText(CantRegistros + " Registros encontrados");
    }

    private String EncontrarEstadoProducto(ResultSet rs) {
        String estado = "No encontrado";
        try {
            String sentencia = "SELECT es_descripcion FROM producto, formulacion, estado "
                    + "WHERE pro_formulacion = for_codigo AND for_estado = es_codigo AND pro_codigo = '" + rs.getString("pro_codigo") + "'";
            con = metodos.ObtenerRSSentencia(sentencia);
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


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpPrincipal = new javax.swing.JPanel();
        jpBanner = new javax.swing.JPanel();
        lbBanner = new javax.swing.JLabel();
        jpTabla = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtFiltro = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbTabla = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        lblBuscarCampo = new javax.swing.JLabel();
        cbCampoBuscar = new javax.swing.JComboBox();
        lblBuscarCampo3 = new javax.swing.JLabel();
        cbProductor = new javax.swing.JComboBox();
        cbEstablecimiento = new javax.swing.JComboBox();
        lblBuscarCampo4 = new javax.swing.JLabel();
        btnNuevaEntrada = new javax.swing.JButton();
        btnGuardar2 = new javax.swing.JButton();
        lbCantRegistros = new javax.swing.JLabel();

        setTitle("Ventana Inventario");
        setBackground(new java.awt.Color(45, 62, 80));
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);

        jpPrincipal.setBackground(new java.awt.Color(45, 62, 80));
        jpPrincipal.setPreferredSize(new java.awt.Dimension(1580, 478));

        jpBanner.setBackground(new java.awt.Color(0, 102, 204));
        jpBanner.setPreferredSize(new java.awt.Dimension(1000, 82));

        lbBanner.setBackground(new java.awt.Color(0, 204, 204));
        lbBanner.setFont(new java.awt.Font("sansserif", 1, 30)); // NOI18N
        lbBanner.setForeground(new java.awt.Color(255, 255, 255));
        lbBanner.setText("Inventario");
        lbBanner.setMaximumSize(new java.awt.Dimension(1100, 52));
        lbBanner.setMinimumSize(new java.awt.Dimension(1100, 52));
        lbBanner.setPreferredSize(new java.awt.Dimension(1100, 52));

        javax.swing.GroupLayout jpBannerLayout = new javax.swing.GroupLayout(jpBanner);
        jpBanner.setLayout(jpBannerLayout);
        jpBannerLayout.setHorizontalGroup(
            jpBannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBannerLayout.createSequentialGroup()
                .addGap(0, 36, Short.MAX_VALUE)
                .addComponent(lbBanner, javax.swing.GroupLayout.PREFERRED_SIZE, 946, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jpBannerLayout.setVerticalGroup(
            jpBannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBannerLayout.createSequentialGroup()
                .addComponent(lbBanner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jpTabla.setBackground(new java.awt.Color(45, 62, 80));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Iconos20x20/IconoBuscar.png"))); // NOI18N
        jLabel10.setText("  BUSCAR ");

        txtFiltro.setBackground(new java.awt.Color(0, 0, 0));
        txtFiltro.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        txtFiltro.setForeground(new java.awt.Color(0, 204, 204));
        txtFiltro.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtFiltro.setCaretColor(new java.awt.Color(0, 204, 204));
        txtFiltro.setDisabledTextColor(new java.awt.Color(0, 204, 204));
        txtFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFiltroKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFiltroKeyTyped(evt);
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

        lblBuscarCampo3.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        lblBuscarCampo3.setForeground(new java.awt.Color(255, 255, 255));
        lblBuscarCampo3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBuscarCampo3.setText(" Productor:");
        lblBuscarCampo3.setToolTipText("");

        cbProductor.setName("ProductorFiltro"); // NOI18N
        cbProductor.setNextFocusableComponent(cbEstablecimiento);
        cbProductor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbProductorItemStateChanged(evt);
            }
        });

        cbEstablecimiento.setName("EstablecimientoFiltro"); // NOI18N
        cbEstablecimiento.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbEstablecimientoItemStateChanged(evt);
            }
        });

        lblBuscarCampo4.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        lblBuscarCampo4.setForeground(new java.awt.Color(255, 255, 255));
        lblBuscarCampo4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBuscarCampo4.setText(" Establecimiento:");
        lblBuscarCampo4.setToolTipText("");

        btnNuevaEntrada.setBackground(new java.awt.Color(0, 153, 102));
        btnNuevaEntrada.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnNuevaEntrada.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevaEntrada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Iconos25x25/IconoEntrada.png"))); // NOI18N
        btnNuevaEntrada.setText("Nueva entrada");
        btnNuevaEntrada.setToolTipText("Inserta el nuevo registro");
        btnNuevaEntrada.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNuevaEntrada.setIconTextGap(0);
        btnNuevaEntrada.setPreferredSize(new java.awt.Dimension(128, 36));
        btnNuevaEntrada.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnNuevaEntrada.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNuevaEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaEntradaActionPerformed(evt);
            }
        });
        btnNuevaEntrada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnNuevaEntradaKeyPressed(evt);
            }
        });

        btnGuardar2.setBackground(new java.awt.Color(153, 0, 0));
        btnGuardar2.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnGuardar2.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Iconos25x25/IconoSalida.png"))); // NOI18N
        btnGuardar2.setText("Nueva salida");
        btnGuardar2.setToolTipText("Inserta el nuevo registro");
        btnGuardar2.setEnabled(false);
        btnGuardar2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardar2.setIconTextGap(0);
        btnGuardar2.setPreferredSize(new java.awt.Dimension(128, 36));
        btnGuardar2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnGuardar2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuardar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardar2ActionPerformed(evt);
            }
        });
        btnGuardar2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnGuardar2KeyPressed(evt);
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpTablaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbCantRegistros, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 954, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpTablaLayout.createSequentialGroup()
                        .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblBuscarCampo4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblBuscarCampo3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(4, 4, 4)
                        .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cbProductor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbEstablecimiento, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtFiltro, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 475, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpTablaLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNuevaEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnGuardar2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpTablaLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(lblBuscarCampo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbCampoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(366, 366, 366))
        );
        jpTablaLayout.setVerticalGroup(
            jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTablaLayout.createSequentialGroup()
                .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpTablaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnNuevaEntrada, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                            .addComponent(btnGuardar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblBuscarCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbCampoBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                            .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jpTablaLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblBuscarCampo3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbProductor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblBuscarCampo4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbEstablecimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbCantRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jpPrincipalLayout = new javax.swing.GroupLayout(jpPrincipal);
        jpPrincipal.setLayout(jpPrincipalLayout);
        jpPrincipalLayout.setHorizontalGroup(
            jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPrincipalLayout.createSequentialGroup()
                .addComponent(jpBanner, javax.swing.GroupLayout.PREFERRED_SIZE, 982, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpBanner.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 981, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleName("FormInventario");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtFiltroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltroKeyReleased
        //actualiza la tabla conforme a la letra que teclea
        if (txtFiltro.getText().trim().length() >= 1) {
            TablaConsulta(txtFiltro.getText());
            tbTabla.setVisible(true);
        } else {
            TablaConsulta(txtFiltro.getText());
        }

        //Convertir a mayuscula
        Character s = evt.getKeyChar();
        if (Character.isLetter(s)) {
            txtFiltro.setText(txtFiltro.getText().toUpperCase());
        }
    }//GEN-LAST:event_txtFiltroKeyReleased

    private void tbTablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTablaMouseClicked

    }//GEN-LAST:event_tbTablaMouseClicked

    private void tbTablaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTablaMousePressed

    }//GEN-LAST:event_tbTablaMousePressed

    private void txtFiltroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltroKeyTyped
        // Verificar si la tecla pulsada no es '
        char caracter = evt.getKeyChar();
        if (caracter == "'".charAt(0)) {
            evt.consume(); // ignorar el evento de teclado
        }
    }//GEN-LAST:event_txtFiltroKeyTyped

    private void cbProductorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbProductorItemStateChanged
        metodoscombo.CargarComboBox(cbEstablecimiento, "SELECT estab_codigo, estab_descripcion FROM establecimiento WHERE estab_productor = " + metodoscombo.ObtenerIdComboBox(cbProductor) + " ORDER BY estab_descripcion");
        if (cbEstablecimiento.getItemCount() > 0) {
            cbEstablecimiento.setSelectedIndex(0);
        }
    }//GEN-LAST:event_cbProductorItemStateChanged

    private void cbEstablecimientoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbEstablecimientoItemStateChanged

    }//GEN-LAST:event_cbEstablecimientoItemStateChanged

    private void btnNuevaEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaEntradaActionPerformed
        if (cbEstablecimiento.getSelectedIndex() != -1) {
            AMEntrada abmentrada = new AMEntrada(this, true);
            metodoscombo.setSelectedNombreItem(abmentrada.getCbEstablecimiento(), cbEstablecimiento.getSelectedItem().toString());

            abmentrada.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    TablaConsulta(txtFiltro.getText());
                }
            });
            //hago visible el dialogo
            abmentrada.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un establecimiento", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnNuevaEntradaActionPerformed

    private void btnNuevaEntradaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnNuevaEntradaKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            btnNuevaEntrada.doClick();
        }
    }//GEN-LAST:event_btnNuevaEntradaKeyPressed

    private void btnGuardar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardar2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardar2ActionPerformed

    private void btnGuardar2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnGuardar2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardar2KeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar2;
    private javax.swing.JButton btnNuevaEntrada;
    private javax.swing.JComboBox cbCampoBuscar;
    private javax.swing.JComboBox cbEstablecimiento;
    private javax.swing.JComboBox cbProductor;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel jpBanner;
    private javax.swing.JPanel jpPrincipal;
    private javax.swing.JPanel jpTabla;
    private javax.swing.JLabel lbBanner;
    private javax.swing.JLabel lbCantRegistros;
    private javax.swing.JLabel lblBuscarCampo;
    private javax.swing.JLabel lblBuscarCampo3;
    private javax.swing.JLabel lblBuscarCampo4;
    private javax.swing.JTable tbTabla;
    private javax.swing.JTextField txtFiltro;
    // End of variables declaration//GEN-END:variables
}
