/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms.producto;

import conexion.Conexion;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import static login.Login.codUsuario;
import utilidades.Metodos;
import utilidades.MetodosCombo;
import utilidades.MetodosTXT;

/**
 *
 * @author Arnaldo Cantero
 */
public class ABMProducto extends javax.swing.JDialog {

    private Conexion con = new Conexion();
    private Metodos metodos = new Metodos();
    private MetodosTXT metodostxt = new MetodosTXT();
    private MetodosCombo metodoscombo = new MetodosCombo();
    private DefaultTableModel tableModelProducto;
    private Color colorVerde = new Color(6, 147, 27);
    private Color colorRojo = new Color(206, 16, 45);

    public ABMProducto(java.awt.Frame parent, Boolean modal) {
        super(parent, modal);
        initComponents();

        metodos.AnchuraColumna(tbPrincipal);

        //LLamar metodos
        //ConsultaAllAlumno(); //Trae todos los registros
        ConsultaAllProducto();
        CargarComboBoxes();

        //Permiso Roles de usuario
        //Permiso Roles de usuario
        String permisos = metodos.PermisoRol(codUsuario, "PRODUCTO");
        btnNuevo.setVisible(permisos.contains("A"));
        btnModificar.setVisible(permisos.contains("M"));
        btnEliminar.setVisible(permisos.contains("B"));

        //Cambiar color de disabled combo
        metodoscombo.CambiarColorDisabledCombo(cbClaseUso, Color.BLACK);
        metodoscombo.CambiarColorDisabledCombo(cbFabricante, Color.BLACK);
    }

    //--------------------------METODOS----------------------------//
    private void CargarComboBoxes() {
        //Carga los combobox con las consultas
        metodoscombo.CargarComboConsulta(cbClaseUso, "SELECT cu_codigo, cu_descripcion FROM clase_uso ORDER BY cu_descripcion", -1);
        metodoscombo.CargarComboConsulta(cbRegistrante, "SELECT re_codigo, re_descripcion FROM registrante ORDER BY re_descripcion", -1);
        metodoscombo.CargarComboConsulta(cbFabricante, "SELECT fa_codigo, fa_descripcion FROM fabricante ORDER BY fa_descripcion", -1);
        metodoscombo.CargarComboConsulta(cbFormulacion, "SELECT for_codigo, for_descripcion FROM formulacion ORDER BY for_descripcion", -1);
        metodoscombo.CargarComboConsulta(cbPaisOrigen, "SELECT po_codigo, po_descripcion FROM pais_origen ORDER BY po_descripcion", -1);
        metodoscombo.CargarComboConsulta(cbToxicologia, "SELECT to_codigo, to_descripcion FROM toxicologia ORDER BY to_descripcion", -1);
    }

    private void ConsultaAllProducto() {//Realiza la consulta de los productos que tenemos en la base de datos
        tableModelProducto = (DefaultTableModel) tbPrincipal.getModel();
        tableModelProducto.setRowCount(0);
        if (cbCampoBuscar.getItemCount() == 0) {
            metodos.CargarTitlesaCombo(cbCampoBuscar, tbPrincipal);
        }
        try {
            String sentencia = "CALL SP_ProductoConsulta()";
            con = con.ObtenerRSSentencia(sentencia);
            String numregistro, numventa, nombre, compuesto, registrante, fabricante, claseuso, formulacion, paisorigen, toxicologia;
            int codigo;

            while (con.getResultSet().next()) {
                codigo = con.getResultSet().getInt("pro_codigo");
                numregistro = con.getResultSet().getString("pro_numregistro");
                numventa = con.getResultSet().getString("pro_numventa");
                nombre = con.getResultSet().getString("pro_nombreproducto");
                compuesto = con.getResultSet().getString("co_descripcion");
                registrante = con.getResultSet().getString("re_descripcion");
                fabricante = con.getResultSet().getString("fa_descripcion");
                claseuso = con.getResultSet().getString("cu_descripcion");
                formulacion = con.getResultSet().getString("for_descripcion");
                paisorigen = con.getResultSet().getString("po_descripcion");
                toxicologia = con.getResultSet().getString("to_descripcion");

                tableModelProducto.addRow(new Object[]{codigo, numregistro, numventa, nombre, compuesto, registrante, fabricante, claseuso, formulacion, paisorigen, toxicologia});
            }
            tbPrincipal.setModel(tableModelProducto);
            metodos.AnchuraColumna(tbPrincipal);

            if (tbPrincipal.getModel().getRowCount() == 1) {
                lbCantRegistros.setText(tbPrincipal.getModel().getRowCount() + " Registro encontrado");
            } else {
                lbCantRegistros.setText(tbPrincipal.getModel().getRowCount() + " Registros encontrados");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void RegistroNuevoModificar() {
        try {
            if (ComprobarCampos() == true) {
                String codigo = txtCodigo.getText();
                String nombre = txtNumRegistro.getText();
                String apellido = txtNumVenta.getText();
                String cedula = null;
                String sexo = cbClaseUso.getSelectedItem().toString();
                int apoderado = metodoscombo.ObtenerIDSelectCombo(cbRegistrante);
                int estado = cbFabricante.getSelectedIndex();

                if (txtCodigo.getText().equals("")) {//Si es nuevo
                    int confirmado = JOptionPane.showConfirmDialog(this, "¿Esta seguro crear este nuevo registro?", "Confirmación", JOptionPane.YES_OPTION);
                    if (JOptionPane.YES_OPTION == confirmado) {
                        //REGISTRAR NUEVO
                        String sentencia = "CALL SP_AlumnoAlta ('" + nombre + "','" + apellido + "'," + cedula + ",'" + cedula + "','" + cedula
                                + "','" + sexo + "','" + cedula + "','" + cedula + "','" + cedula + "','" + apoderado + "','" + estado + "')";
                        con.EjecutarABM(sentencia, true);

                        ConsultaAllProducto(); //Actualizar tabla
                        Limpiar();
                        ModoEdicion(false);
                    }
                } else {
                    int confirmado = JOptionPane.showConfirmDialog(this, "¿Estás seguro de modificar este registro?", "Confirmación", JOptionPane.YES_OPTION);
                    if (JOptionPane.YES_OPTION == confirmado) {
                        String sentencia = "CALL SP_AlumnoModificar('" + codigo + "','" + nombre + "','" + apellido + "'," + cedula
                                + ",'" + cedula + "','" + cedula + "','" + sexo + "','" + cedula + "','" + cedula
                                + "','" + cedula + "','" + apoderado + "','" + estado + "')";

                        con.EjecutarABM(sentencia, true);
                        ConsultaAllProducto(); //Actualizar tabla                        
                        ModoEdicion(false);
                        Limpiar();
                        //this.repaint();
                    }
                }
            }
        } catch (HeadlessException ex) {
            JOptionPane.showMessageDialog(this, "Completar los campos obligarios marcados con * ", "Advertencia", JOptionPane.WARNING_MESSAGE);
            System.out.println("Completar los campos obligarios marcados con * " + ex);
            txtNumRegistro.requestFocus();
        }
    }

    private void RegistroEliminar() {
        int codigo;
        int filasel = tbPrincipal.getSelectedRow();
        if (filasel != -1) {
            int confirmado = javax.swing.JOptionPane.showConfirmDialog(this, "¿Realmente desea eliminar este alumno?, tambien se ELIMINARÁN las matriculas referentes al mismo", "Confirmación", JOptionPane.YES_OPTION);
            if (confirmado == JOptionPane.YES_OPTION) {
                codigo = Integer.parseInt(tbPrincipal.getValueAt(filasel, 0) + "");
                String sentencia = "CALL SP_AlumnoEliminar(" + codigo + ")";
                con.EjecutarABM(sentencia, true);

                ConsultaAllProducto(); //Actualizar tabla
                ModoEdicion(false);
                Limpiar();
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se ha seleccionado ninguna fila", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void ModoVistaPrevia() {
        txtCodigo.setText(metodos.SiStringEsNull(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 0) + ""));
        txtNumRegistro.setText(metodos.SiStringEsNull(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 1) + ""));
        txtNumVenta.setText(metodos.SiStringEsNull(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 2) + ""));
        txtNombreComercial.setText(metodos.SiStringEsNull(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 3) + ""));
        taCompuestos.setText(metodos.SiStringEsNull(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 4) + ""));
        metodoscombo.SetSelectedNombreItem(cbRegistrante, tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 5).toString());
        metodoscombo.SetSelectedNombreItem(cbFabricante, tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 6).toString());
        metodoscombo.SetSelectedNombreItem(cbClaseUso, tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 7).toString());
        metodoscombo.SetSelectedNombreItem(cbFormulacion, tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 8).toString());
        metodoscombo.SetSelectedNombreItem(cbPaisOrigen, tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 9).toString());
        metodoscombo.SetSelectedNombreItem(cbToxicologia, tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 10).toString());
    }

    private void ModoEdicion(boolean valor) {
        txtBuscar.setEnabled(!valor);
        tbPrincipal.setEnabled(!valor);

        txtNumRegistro.setEnabled(valor);
        txtNumVenta.setEnabled(valor);
        txtNombreComercial.setEnabled(valor);
        cbClaseUso.setEnabled(valor);
        cbRegistrante.setEnabled(valor);
        cbFabricante.setEnabled(valor);
        cbFormulacion.setEnabled(valor);
        cbPaisOrigen.setEnabled(valor);
        cbToxicologia.setEnabled(valor);

        btnCompuestos.setEnabled(valor);
        btnNuevo.setEnabled(!valor);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnGuardar.setEnabled(valor);
        btnCancelar.setEnabled(valor);

        txtNumRegistro.requestFocus();
    }

    private void Limpiar() {
        txtCodigo.setText("");
        txtNumRegistro.setText("");
        txtNumVenta.setText("");
        txtNombreComercial.setText("");
        taCompuestos.setText("");
        cbClaseUso.setSelectedItem("SIN DATOS");
        cbRegistrante.setSelectedItem("SIN DATOS");
        cbFabricante.setSelectedItem("SIN DATOS");
        cbFormulacion.setSelectedItem("SIN DATOS");
        cbPaisOrigen.setSelectedItem("SIN DATOS");
        cbToxicologia.setSelectedItem("SIN DATOS");

        lblNumRegistro.setForeground(Color.DARK_GRAY);
        lblNumVenta.setForeground(Color.DARK_GRAY);
        lblNombreComercial.setForeground(Color.DARK_GRAY);

        tbPrincipal.clearSelection();
    }

    public boolean ComprobarCampos() {
        if (metodostxt.ValidarCampoVacioTXT(txtNumRegistro, lblNumRegistro) == false) {
            return false;
        }

        if (metodostxt.ValidarCampoVacioTXT(txtNumVenta, lblNumVenta) == false) {
            return false;
        }

        if (cbRegistrante.getSelectedIndex() == -1) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(this, "Seleccione un apoderado", "Advertencia", JOptionPane.WARNING_MESSAGE);
            cbRegistrante.requestFocus();
            return false;
        }
        return true;
    }

//--------------------------iniComponent()No tocar----------------------------//
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BuscadorCompuestos = new javax.swing.JDialog();
        panel6 = new org.edisoncor.gui.panel.Panel();
        jLabel12 = new javax.swing.JLabel();
        txtBuscarApoderado = new javax.swing.JTextField();
        lblBuscarCampoApoderado = new javax.swing.JLabel();
        cbCampoBuscarApoderado = new javax.swing.JComboBox();
        scApoderado = new javax.swing.JScrollPane();
        tbApoderado = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        lbCantRegistrosApoderado = new javax.swing.JLabel();
        jpPrincipal = new javax.swing.JPanel();
        jpTabla = new javax.swing.JPanel();
        scPrincipal = new javax.swing.JScrollPane();
        tbPrincipal = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        lbCantRegistros = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        lblBuscarCampoApoderado1 = new javax.swing.JLabel();
        cbCampoBuscar = new javax.swing.JComboBox();
        jpBotones = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jtpEdicion = new javax.swing.JTabbedPane();
        jpEdicion = new javax.swing.JPanel();
        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblNumRegistro = new javax.swing.JLabel();
        txtNumRegistro = new javax.swing.JTextField();
        lblNumVenta = new javax.swing.JLabel();
        txtNumVenta = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        lblSexo = new javax.swing.JLabel();
        cbClaseUso = new javax.swing.JComboBox<>();
        lblEstado = new javax.swing.JLabel();
        cbFabricante = new javax.swing.JComboBox<>();
        lblEstado1 = new javax.swing.JLabel();
        cbRegistrante = new javax.swing.JComboBox<>();
        lblEstado2 = new javax.swing.JLabel();
        lblEstado3 = new javax.swing.JLabel();
        cbPaisOrigen = new javax.swing.JComboBox<>();
        cbFormulacion = new javax.swing.JComboBox<>();
        btnQuitarCompuestos = new javax.swing.JButton();
        btnCompuestos = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        btnMasDosis = new javax.swing.JButton();
        btnModificarDosis = new javax.swing.JButton();
        btnMenosDosis = new javax.swing.JButton();
        lbImagen = new javax.swing.JLabel();
        btnCargarImagen = new javax.swing.JButton();
        btnEliminarImagen = new javax.swing.JButton();
        btnPantallaCompleta = new javax.swing.JButton();
        lblEstado4 = new javax.swing.JLabel();
        cbToxicologia = new javax.swing.JComboBox<>();
        txtNombreComercial = new javax.swing.JTextField();
        lblNombreComercial = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taCompuestos = new javax.swing.JTextArea();
        jpBotones2 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        panel1 = new org.edisoncor.gui.panel.Panel();
        labelMetric1 = new org.edisoncor.gui.label.LabelMetric();

        BuscadorCompuestos.setTitle("Buscador de apoderados");
        BuscadorCompuestos.setModal(true);
        BuscadorCompuestos.setSize(new java.awt.Dimension(760, 310));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/iconos40x40/IconoBuscar.png"))); // NOI18N
        jLabel12.setText("  BUSCAR ");

        txtBuscarApoderado.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        txtBuscarApoderado.setForeground(new java.awt.Color(0, 153, 153));
        txtBuscarApoderado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtBuscarApoderado.setCaretColor(new java.awt.Color(0, 204, 204));
        txtBuscarApoderado.setDisabledTextColor(new java.awt.Color(0, 204, 204));
        txtBuscarApoderado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarApoderadoKeyReleased(evt);
            }
        });

        lblBuscarCampoApoderado.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        lblBuscarCampoApoderado.setForeground(new java.awt.Color(255, 255, 255));
        lblBuscarCampoApoderado.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBuscarCampoApoderado.setText("Buscar por:");

        tbApoderado.setAutoCreateRowSorter(true);
        tbApoderado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tbApoderado.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbApoderado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbApoderado.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tbApoderado.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbApoderado.setGridColor(new java.awt.Color(0, 153, 204));
        tbApoderado.setOpaque(false);
        tbApoderado.setRowHeight(20);
        tbApoderado.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbApoderado.getTableHeader().setReorderingAllowed(false);
        tbApoderado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbApoderadoMousePressed(evt);
            }
        });
        tbApoderado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbApoderadoKeyReleased(evt);
            }
        });
        scApoderado.setViewportView(tbApoderado);

        lbCantRegistrosApoderado.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lbCantRegistrosApoderado.setForeground(new java.awt.Color(153, 153, 0));
        lbCantRegistrosApoderado.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbCantRegistrosApoderado.setText("0 Registros encontrados");
        lbCantRegistrosApoderado.setPreferredSize(new java.awt.Dimension(57, 25));

        javax.swing.GroupLayout panel6Layout = new javax.swing.GroupLayout(panel6);
        panel6.setLayout(panel6Layout);
        panel6Layout.setHorizontalGroup(
            panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(scApoderado, javax.swing.GroupLayout.PREFERRED_SIZE, 717, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel6Layout.createSequentialGroup()
                        .addComponent(lbCantRegistrosApoderado, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8))
                    .addGroup(panel6Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscarApoderado, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblBuscarCampoApoderado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbCampoBuscarApoderado, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panel6Layout.setVerticalGroup(
            panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel6Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cbCampoBuscarApoderado, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBuscarCampoApoderado, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBuscarApoderado, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(scApoderado, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(lbCantRegistrosApoderado, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout BuscadorCompuestosLayout = new javax.swing.GroupLayout(BuscadorCompuestos.getContentPane());
        BuscadorCompuestos.getContentPane().setLayout(BuscadorCompuestosLayout);
        BuscadorCompuestosLayout.setHorizontalGroup(
            BuscadorCompuestosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorCompuestosLayout.setVerticalGroup(
            BuscadorCompuestosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ventana Productos");
        setBackground(new java.awt.Color(45, 62, 80));
        setModal(true);
        setSize(new java.awt.Dimension(952, 621));

        jpPrincipal.setBackground(new java.awt.Color(233, 255, 255));
        jpPrincipal.setPreferredSize(new java.awt.Dimension(1580, 478));

        jpTabla.setBackground(new java.awt.Color(233, 255, 255));
        jpTabla.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        scPrincipal.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tbPrincipal.setAutoCreateRowSorter(true);
        tbPrincipal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tbPrincipal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        tbPrincipal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "N° de registro", "N° de venta", "Nombre del producto", "Compuesto", "Registrante", "Fabricante", "Clase de uso", "Formulacion", "Pais de origen", "Toxicologia"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
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
        tbPrincipal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPrincipalKeyReleased(evt);
            }
        });
        scPrincipal.setViewportView(tbPrincipal);

        lbCantRegistros.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lbCantRegistros.setForeground(new java.awt.Color(153, 153, 0));
        lbCantRegistros.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbCantRegistros.setText("0 Registros encontrados");
        lbCantRegistros.setPreferredSize(new java.awt.Dimension(57, 25));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/iconos40x40/IconoBuscar.png"))); // NOI18N
        jLabel13.setText("  BUSCAR ");

        txtBuscar.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(0, 0, 0));
        txtBuscar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
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

        lblBuscarCampoApoderado1.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        lblBuscarCampoApoderado1.setForeground(new java.awt.Color(0, 0, 0));
        lblBuscarCampoApoderado1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBuscarCampoApoderado1.setText("Buscar por:");

        javax.swing.GroupLayout jpTablaLayout = new javax.swing.GroupLayout(jpTabla);
        jpTabla.setLayout(jpTablaLayout);
        jpTablaLayout.setHorizontalGroup(
            jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTablaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpTablaLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(lblBuscarCampoApoderado1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbCampoBuscar, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(scPrincipal, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbCantRegistros, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpTablaLayout.setVerticalGroup(
            jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTablaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbCampoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBuscarCampoApoderado1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(scPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbCantRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jpBotones.setBackground(new java.awt.Color(233, 255, 255));
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
            .addGroup(jpBotonesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnModificar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpBotonesLayout.setVerticalGroup(
            jpBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBotonesLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(btnNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnModificar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEliminar)
                .addGap(26, 26, 26))
        );

        jtpEdicion.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jtpEdicion.setName(""); // NOI18N

        jpEdicion.setBackground(new java.awt.Color(233, 255, 255));
        jpEdicion.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        lblCodigo.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblCodigo.setForeground(new java.awt.Color(102, 102, 102));
        lblCodigo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodigo.setText("Código:");
        lblCodigo.setFocusable(false);

        txtCodigo.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        txtCodigo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCodigo.setEnabled(false);

        lblNumRegistro.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblNumRegistro.setForeground(new java.awt.Color(102, 102, 102));
        lblNumRegistro.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNumRegistro.setText("N° de registro*:");
        lblNumRegistro.setFocusable(false);

        txtNumRegistro.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        txtNumRegistro.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNumRegistro.setEnabled(false);
        txtNumRegistro.setNextFocusableComponent(txtNumVenta);
        txtNumRegistro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumRegistroFocusLost(evt);
            }
        });
        txtNumRegistro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNumRegistroKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumRegistroKeyTyped(evt);
            }
        });

        lblNumVenta.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblNumVenta.setForeground(new java.awt.Color(102, 102, 102));
        lblNumVenta.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNumVenta.setText("N° de venta*:");
        lblNumVenta.setFocusable(false);

        txtNumVenta.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        txtNumVenta.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNumVenta.setEnabled(false);
        txtNumVenta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumVentaFocusLost(evt);
            }
        });
        txtNumVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNumVentaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumVentaKeyTyped(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(0, 0, 153));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Campos con (*) son obligatorios");

        lblSexo.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblSexo.setForeground(new java.awt.Color(102, 102, 102));
        lblSexo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSexo.setText("Clase de uso*:");
        lblSexo.setToolTipText("");
        lblSexo.setFocusable(false);

        cbClaseUso.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        cbClaseUso.setEnabled(false);

        lblEstado.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblEstado.setForeground(new java.awt.Color(102, 102, 102));
        lblEstado.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblEstado.setText("Fabricante*:");
        lblEstado.setToolTipText("");
        lblEstado.setFocusable(false);

        cbFabricante.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        cbFabricante.setEnabled(false);
        cbFabricante.setNextFocusableComponent(btnGuardar);

        lblEstado1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblEstado1.setForeground(new java.awt.Color(102, 102, 102));
        lblEstado1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblEstado1.setText("Registrante*:");
        lblEstado1.setToolTipText("");
        lblEstado1.setFocusable(false);

        cbRegistrante.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        cbRegistrante.setEnabled(false);
        cbRegistrante.setNextFocusableComponent(cbFabricante);

        lblEstado2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblEstado2.setForeground(new java.awt.Color(102, 102, 102));
        lblEstado2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblEstado2.setText("Formulación*:");
        lblEstado2.setToolTipText("");
        lblEstado2.setFocusable(false);

        lblEstado3.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblEstado3.setForeground(new java.awt.Color(102, 102, 102));
        lblEstado3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblEstado3.setText("País de origen*:");
        lblEstado3.setToolTipText("");
        lblEstado3.setFocusable(false);

        cbPaisOrigen.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        cbPaisOrigen.setEnabled(false);
        cbPaisOrigen.setNextFocusableComponent(btnGuardar);

        cbFormulacion.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        cbFormulacion.setEnabled(false);
        cbFormulacion.setNextFocusableComponent(cbFabricante);
        cbFormulacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFormulacionActionPerformed(evt);
            }
        });

        btnQuitarCompuestos.setBackground(new java.awt.Color(255, 0, 51));
        btnQuitarCompuestos.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        btnQuitarCompuestos.setText("-");
        btnQuitarCompuestos.setToolTipText("Elimina el ingrediente activo seleccionado en la lista");
        btnQuitarCompuestos.setEnabled(false);
        btnQuitarCompuestos.setPreferredSize(new java.awt.Dimension(35, 31));
        btnQuitarCompuestos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarCompuestosActionPerformed(evt);
            }
        });

        btnCompuestos.setBackground(new java.awt.Color(0, 153, 153));
        btnCompuestos.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        btnCompuestos.setText("+");
        btnCompuestos.setToolTipText("Despliega ka ventana de compuestos");
        btnCompuestos.setEnabled(false);
        btnCompuestos.setPreferredSize(new java.awt.Dimension(35, 31));
        btnCompuestos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompuestosActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Compuestos*:");

        btnMasDosis.setBackground(new java.awt.Color(0, 153, 153));
        btnMasDosis.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        btnMasDosis.setText("+");
        btnMasDosis.setToolTipText("Agrega una nueva dosis recomendada a la lista");
        btnMasDosis.setEnabled(false);
        btnMasDosis.setPreferredSize(new java.awt.Dimension(35, 31));
        btnMasDosis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMasDosisActionPerformed(evt);
            }
        });

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

        lblEstado4.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblEstado4.setForeground(new java.awt.Color(102, 102, 102));
        lblEstado4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblEstado4.setText("Toxicología*:");
        lblEstado4.setToolTipText("");
        lblEstado4.setFocusable(false);

        cbToxicologia.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        cbToxicologia.setEnabled(false);
        cbToxicologia.setNextFocusableComponent(btnGuardar);

        txtNombreComercial.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        txtNombreComercial.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNombreComercial.setEnabled(false);
        txtNombreComercial.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNombreComercialFocusLost(evt);
            }
        });
        txtNombreComercial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreComercialKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreComercialKeyTyped(evt);
            }
        });

        lblNombreComercial.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblNombreComercial.setForeground(new java.awt.Color(102, 102, 102));
        lblNombreComercial.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNombreComercial.setText("Nombre comercial*:");
        lblNombreComercial.setFocusable(false);

        taCompuestos.setColumns(20);
        taCompuestos.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        taCompuestos.setForeground(new java.awt.Color(0, 0, 0));
        taCompuestos.setLineWrap(true);
        taCompuestos.setRows(5);
        taCompuestos.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        taCompuestos.setEnabled(false);
        jScrollPane1.setViewportView(taCompuestos);

        javax.swing.GroupLayout jpEdicionLayout = new javax.swing.GroupLayout(jpEdicion);
        jpEdicion.setLayout(jpEdicionLayout);
        jpEdicionLayout.setHorizontalGroup(
            jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpEdicionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpEdicionLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnQuitarCompuestos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCompuestos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jpEdicionLayout.createSequentialGroup()
                        .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNumRegistro, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNumVenta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNombreComercial, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCodigo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(2, 2, 2))
                    .addGroup(jpEdicionLayout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtNumRegistro, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpEdicionLayout.createSequentialGroup()
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombreComercial, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNumVenta, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpEdicionLayout.createSequentialGroup()
                        .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jpEdicionLayout.createSequentialGroup()
                                .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblEstado2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblEstado3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblEstado4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(2, 2, 2)
                                .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbFormulacion, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbPaisOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbToxicologia, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jpEdicionLayout.createSequentialGroup()
                                .addComponent(lblEstado1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(cbRegistrante, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpEdicionLayout.createSequentialGroup()
                                .addComponent(lblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(cbFabricante, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpEdicionLayout.createSequentialGroup()
                                .addComponent(lblSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(cbClaseUso, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEliminarImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCargarImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPantallaCompleta, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34))
                    .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnMasDosis, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnModificarDosis, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnMenosDosis, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jpEdicionLayout.setVerticalGroup(
            jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpEdicionLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(btnCargarImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminarImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPantallaCompleta, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(148, 148, 148)
                .addComponent(btnMasDosis, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModificarDosis, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnMenosDosis, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jpEdicionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpEdicionLayout.createSequentialGroup()
                        .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(lblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(lblEstado1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbRegistrante, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(4, 4, 4)
                        .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(lblNumRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNumRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(lblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbFabricante, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(4, 4, 4)
                        .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(lblNumVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNumVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(lblSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbClaseUso, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(4, 4, 4)
                        .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbFormulacion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblEstado2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(lblNombreComercial, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNombreComercial, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(4, 4, 4)
                        .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpEdicionLayout.createSequentialGroup()
                                .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblEstado3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbPaisOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(4, 4, 4)
                                .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblEstado4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbToxicologia, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jpEdicionLayout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(btnCompuestos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnQuitarCompuestos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1)))
                    .addGroup(jpEdicionLayout.createSequentialGroup()
                        .addComponent(lbImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)))
                .addGap(152, 152, 152))
        );

        jtpEdicion.addTab("Edición", jpEdicion);

        jpBotones2.setBackground(new java.awt.Color(233, 255, 255));
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

        btnCancelar.setBackground(new java.awt.Color(255, 138, 138));
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
                .addGap(18, 18, 18)
                .addComponent(btnCancelar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpBotones2Layout.setVerticalGroup(
            jpBotones2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBotones2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpBotones2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panel1.setColorPrimario(new java.awt.Color(0, 153, 153));
        panel1.setColorSecundario(new java.awt.Color(233, 255, 255));

        labelMetric1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelMetric1.setText("PRODUCTOS");
        labelMetric1.setDireccionDeSombra(110);
        labelMetric1.setFocusable(false);
        labelMetric1.setFont(new java.awt.Font("Cooper Black", 0, 28)); // NOI18N

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(labelMetric1, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelMetric1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpPrincipalLayout = new javax.swing.GroupLayout(jpPrincipal);
        jpPrincipal.setLayout(jpPrincipalLayout);
        jpPrincipalLayout.setHorizontalGroup(
            jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jpPrincipalLayout.createSequentialGroup()
                .addGroup(jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpPrincipalLayout.createSequentialGroup()
                        .addGap(351, 351, 351)
                        .addComponent(jpBotones2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpPrincipalLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtpEdicion, javax.swing.GroupLayout.PREFERRED_SIZE, 965, Short.MAX_VALUE)
                            .addGroup(jpPrincipalLayout.createSequentialGroup()
                                .addComponent(jpTabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jpBotones, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jpPrincipalLayout.setVerticalGroup(
            jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPrincipalLayout.createSequentialGroup()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jpBotones, javax.swing.GroupLayout.PREFERRED_SIZE, 239, Short.MAX_VALUE)
                    .addComponent(jpTabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(8, 8, 8)
                .addComponent(jtpEdicion, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpBotones2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jtpEdicion.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 977, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleName("");
        getAccessibleContext().setAccessibleDescription("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        RegistroNuevoModificar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        Limpiar();
        ModoEdicion(false);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        Limpiar();
        ModoEdicion(true);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        ModoEdicion(true);
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        RegistroEliminar();
        Limpiar();
        ModoEdicion(false);

        ConsultaAllProducto();
    }//GEN-LAST:event_btnEliminarActionPerformed

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

    private void txtNumVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumVentaKeyTyped
        metodostxt.SoloTextoKeyTyped(evt);

        //Cantidad de caracteres
        metodostxt.TxtCantidadCaracteresKeyTyped(txtNumVenta, 30);
    }//GEN-LAST:event_txtNumVentaKeyTyped

    private void txtNumVentaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumVentaKeyReleased
        metodostxt.TxtColorLabelKeyReleased(txtNumVenta, lblNumVenta);
    }//GEN-LAST:event_txtNumVentaKeyReleased

    private void txtNumRegistroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumRegistroKeyTyped
        metodostxt.SoloTextoKeyTyped(evt);

        //Cantidad de caracteres
        metodostxt.TxtCantidadCaracteresKeyTyped(txtNumRegistro, 30);
    }//GEN-LAST:event_txtNumRegistroKeyTyped

    private void txtNumRegistroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumRegistroKeyReleased
        metodostxt.TxtColorLabelKeyReleased(txtNumRegistro, lblNumRegistro);
    }//GEN-LAST:event_txtNumRegistroKeyReleased

    private void tbPrincipalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPrincipalKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_UP || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            ModoVistaPrevia();
        }
    }//GEN-LAST:event_tbPrincipalKeyReleased

    private void txtBuscarApoderadoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarApoderadoKeyReleased
        metodos.FiltroJTable(txtBuscarApoderado.getText(), cbCampoBuscarApoderado.getSelectedIndex(), tbApoderado);

        if (tbApoderado.getRowCount() == 1) {
            lbCantRegistrosApoderado.setText(tbApoderado.getRowCount() + " Registro encontrado");
        } else {
            lbCantRegistrosApoderado.setText(tbApoderado.getRowCount() + " Registros encontrados");
        }
    }//GEN-LAST:event_txtBuscarApoderadoKeyReleased

    private void tbApoderadoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbApoderadoMousePressed
        if (evt.getClickCount() == 2) {
            int codselect = Integer.parseInt(tbApoderado.getValueAt(tbApoderado.getSelectedRow(), 0) + "");
            metodoscombo.SetSelectedCodigoItem(cbRegistrante, codselect);
            BuscadorCompuestos.dispose();
        }
    }//GEN-LAST:event_tbApoderadoMousePressed

    private void tbApoderadoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbApoderadoKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int codselect = Integer.parseInt(tbApoderado.getValueAt(tbApoderado.getSelectedRow(), 0) + "");
            metodoscombo.SetSelectedCodigoItem(cbRegistrante, codselect);
            BuscadorCompuestos.dispose();
        }
    }//GEN-LAST:event_tbApoderadoKeyReleased

    private void tbPrincipalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPrincipalMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPrincipalMouseClicked

    private void txtNumRegistroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumRegistroFocusLost
        txtNumRegistro.setText(metodostxt.QuitaEspaciosString(txtNumRegistro.getText()));
        txtNumRegistro.setText(metodostxt.MayusCadaPrimeraLetra(txtNumRegistro.getText()));
    }//GEN-LAST:event_txtNumRegistroFocusLost

    private void txtNumVentaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumVentaFocusLost
        txtNumVenta.setText(metodostxt.QuitaEspaciosString(txtNumVenta.getText()));
        txtNumVenta.setText(metodostxt.MayusCadaPrimeraLetra(txtNumVenta.getText()));
    }//GEN-LAST:event_txtNumVentaFocusLost

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        metodos.FiltroJTable(txtBuscar.getText(), cbCampoBuscar.getSelectedIndex(), tbPrincipal);
        metodos.AnchuraColumna(tbPrincipal);
        if (tbPrincipal.getRowCount() == 1) {
            lbCantRegistros.setText(tbPrincipal.getRowCount() + " Registro encontrado");
        } else {
            lbCantRegistros.setText(tbPrincipal.getRowCount() + " Registros encontrados");
        }
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyTyped
        metodostxt.FiltroCaracteresProhibidos(evt);
    }//GEN-LAST:event_txtBuscarKeyTyped

    private void btnMasDosisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMasDosisActionPerformed
        /*if (cbFormulacion.getSelectedIndex() != -1) {
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
        }*/
    }//GEN-LAST:event_btnMasDosisActionPerformed

    private void btnModificarDosisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarDosisActionPerformed
        /*if (cbFormulacion.getSelectedIndex() != -1) {
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
        }*/
    }//GEN-LAST:event_btnModificarDosisActionPerformed

    private void btnModificarDosisKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnModificarDosisKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModificarDosisKeyReleased

    private void btnMenosDosisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenosDosisActionPerformed
        /*iddosis = tbDosis.getValueAt(tbDosis.getSelectedRow(), 0).toString();
        if (tbDosis.getValueAt(tbDosis.getSelectedRow(), 0).toString().equals("") == false) {
            ArrayEliminadosDosis.add(tbDosis.getValueAt(tbDosis.getSelectedRow(), 0).toString()); //Guarda el id del registro eliminado en la lista de elimiados ListaEliminados
        }

        DefaultTableModel ModeloTabla = (DefaultTableModel) tbDosis.getModel();
        ModeloTabla.removeRow(tbDosis.getSelectedRow());
        btnMenosDosis.setEnabled(false);*/
    }//GEN-LAST:event_btnMenosDosisActionPerformed

    private void btnCargarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarImagenActionPerformed
        //metodosimagen.CargarImagenFC(lbImagen);
    }//GEN-LAST:event_btnCargarImagenActionPerformed

    private void btnEliminarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarImagenActionPerformed
        /*URL url = this.getClass().getResource("/forms/producto/iconos/IconoProductoSinFoto.png");
        lbImagen.setIcon(new ImageIcon(url));

        btnEliminarImagen.setEnabled(!(lbImagen.getIcon().toString().equals(imagendefault.toString()))); //Revisa si el icono es default*/
    }//GEN-LAST:event_btnEliminarImagenActionPerformed

    private void btnPantallaCompletaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPantallaCompletaActionPerformed
        /*VistaCompleta vistacompleta = new VistaCompleta("src/forms/producto/imagenes/image_" + txtCodigo.getText());
        metodos.centrarventanaJDialog(vistacompleta);
        vistacompleta.setVisible(true);*/
    }//GEN-LAST:event_btnPantallaCompletaActionPerformed

    private void txtNombreComercialFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNombreComercialFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreComercialFocusLost

    private void txtNombreComercialKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreComercialKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreComercialKeyReleased

    private void txtNombreComercialKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreComercialKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreComercialKeyTyped

    private void btnQuitarCompuestosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarCompuestosActionPerformed
        /*ArrayEliminadosIA.add(tbIngrActivos.getValueAt(tbIngrActivos.getSelectedRow(), 0).toString()); //Guarda el id del registro eliminado en la lista de elimiados ListaEliminados

        DefaultTableModel ModeloTabla = (DefaultTableModel) tbIngrActivos.getModel();
        ModeloTabla.removeRow(tbIngrActivos.getSelectedRow());
        btnMenos.setEnabled(false);*/
    }//GEN-LAST:event_btnQuitarCompuestosActionPerformed

    private void btnCompuestosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompuestosActionPerformed
        /*ABMIngredienteActivo abmingredienteactivo = new ABMIngredienteActivo(this, null, true);
        abmingredienteactivo.getBtnAnadir().setEnabled(true); //Se activa el boton anadir
        abmingredienteactivo.setVisible(true);*/
    }//GEN-LAST:event_btnCompuestosActionPerformed

    private void cbFormulacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFormulacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbFormulacionActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ABMProducto.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ABMProducto dialog = new ABMProducto(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog BuscadorCompuestos;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCargarImagen;
    private javax.swing.JButton btnCompuestos;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminarImagen;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnMasDosis;
    private javax.swing.JButton btnMenosDosis;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnModificarDosis;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnPantallaCompleta;
    private javax.swing.JButton btnQuitarCompuestos;
    private javax.swing.JComboBox cbCampoBuscar;
    private javax.swing.JComboBox cbCampoBuscarApoderado;
    private javax.swing.JComboBox<String> cbClaseUso;
    private javax.swing.JComboBox<String> cbFabricante;
    private javax.swing.JComboBox<MetodosCombo> cbFormulacion;
    private javax.swing.JComboBox<String> cbPaisOrigen;
    private javax.swing.JComboBox<MetodosCombo> cbRegistrante;
    private javax.swing.JComboBox<String> cbToxicologia;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel jpBotones;
    private javax.swing.JPanel jpBotones2;
    private javax.swing.JPanel jpEdicion;
    private javax.swing.JPanel jpPrincipal;
    private javax.swing.JPanel jpTabla;
    private javax.swing.JTabbedPane jtpEdicion;
    private org.edisoncor.gui.label.LabelMetric labelMetric1;
    private javax.swing.JLabel lbCantRegistros;
    private javax.swing.JLabel lbCantRegistrosApoderado;
    private javax.swing.JLabel lbImagen;
    private javax.swing.JLabel lblBuscarCampoApoderado;
    private javax.swing.JLabel lblBuscarCampoApoderado1;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblEstado1;
    private javax.swing.JLabel lblEstado2;
    private javax.swing.JLabel lblEstado3;
    private javax.swing.JLabel lblEstado4;
    private javax.swing.JLabel lblNombreComercial;
    private javax.swing.JLabel lblNumRegistro;
    private javax.swing.JLabel lblNumVenta;
    private javax.swing.JLabel lblSexo;
    private org.edisoncor.gui.panel.Panel panel1;
    private org.edisoncor.gui.panel.Panel panel6;
    private javax.swing.JScrollPane scApoderado;
    private javax.swing.JScrollPane scPrincipal;
    private javax.swing.JTextArea taCompuestos;
    private javax.swing.JTable tbApoderado;
    private javax.swing.JTable tbPrincipal;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtBuscarApoderado;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtNombreComercial;
    private javax.swing.JTextField txtNumRegistro;
    private javax.swing.JTextField txtNumVenta;
    // End of variables declaration//GEN-END:variables
}
