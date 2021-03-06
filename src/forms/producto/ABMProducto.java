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
import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import static login.Login.codUsuario;
import utilidades.Metodos;
import utilidades.MetodosCombo;
import utilidades.MetodosImagen;
import utilidades.MetodosTXT;
import utilidades.VistaCompleta;

/**
 *
 * @author Arnaldo Cantero
 */
public class ABMProducto extends javax.swing.JDialog {

    private Conexion con = new Conexion();
    private Metodos metodos = new Metodos();
    private MetodosTXT metodostxt = new MetodosTXT();
    private MetodosCombo metodoscombo = new MetodosCombo();
    private MetodosImagen metodosimagen = new MetodosImagen();
    private DefaultTableModel tableModelProducto;
    private DefaultTableModel tableModelCompuesto;
    private final String rutaFotoProducto = "C:\\SICMA\\productos\\imagenes\\";
    private final String rutaFotoDefault = "/src/images/IconoProductoSinFoto.png";
    private Color colorAdvertencia = new Color(206, 16, 45);
    private Color colorTitulos = Color.WHITE;
    private File elFichero;

    public ABMProducto(java.awt.Frame parent, Boolean modal) {
        super(parent, modal);
        initComponents();

        //LLamar metodos
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
            String numregistro, numventa, nombre, idcompuesto, tipo_compuesto, compuesto, registrante, fabricante, claseuso, formulacion, paisorigen, toxicologia;
            int codigo;

            while (con.getResultSet().next()) {
                codigo = con.getResultSet().getInt("pro_codigo");
                numregistro = con.getResultSet().getString("pro_numregistro");
                numventa = con.getResultSet().getString("pro_numventa");
                nombre = con.getResultSet().getString("pro_nombrecomercial");
                idcompuesto = con.getResultSet().getString("co_codigo");
                tipo_compuesto = con.getResultSet().getString("tc_descripcion");
                compuesto = con.getResultSet().getString("co_descripcion");
                registrante = con.getResultSet().getString("re_descripcion");
                fabricante = con.getResultSet().getString("fa_descripcion");
                claseuso = con.getResultSet().getString("cu_descripcion");
                formulacion = con.getResultSet().getString("for_descripcion");
                paisorigen = con.getResultSet().getString("po_descripcion");
                toxicologia = con.getResultSet().getString("to_descripcion");

                tableModelProducto.addRow(new Object[]{codigo, numregistro, numventa, nombre, idcompuesto, tipo_compuesto, compuesto, registrante, fabricante, claseuso, formulacion, paisorigen, toxicologia});
            }
            tbPrincipal.setModel(tableModelProducto);
            metodos.OcultarColumna(tbPrincipal, 4); //Ocultar columna idCompuesto
            metodos.AnchuraColumna(tbPrincipal);

            if (tbPrincipal.getModel().getRowCount() == 1) {
                lbCantRegistros.setText(tbPrincipal.getModel().getRowCount() + " Registro encontrado");
            } else {
                lbCantRegistros.setText(tbPrincipal.getModel().getRowCount() + " Registros encontrados");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        con.DesconectarBasedeDatos();
    }

    private void ConsultaAllCompuestos() {//Realiza la consulta de los productos que tenemos en la base de datos
        tableModelCompuesto = (DefaultTableModel) tbCompuestoBuscadorCompuesto.getModel();
        tableModelCompuesto.setRowCount(0);
        if (cbCampoBuscarCompuesto.getItemCount() == 0) {
            metodos.CargarTitlesaCombo(cbCampoBuscarCompuesto, tbCompuestoBuscadorCompuesto);
        }
        try {
            String sentencia = "CALL SP_CompuestoConsulta()";
            con = con.ObtenerRSSentencia(sentencia);
            int codigo;
            String descripcion, tipocompuesto;

            while (con.getResultSet().next()) {
                codigo = con.getResultSet().getInt("co_codigo");
                descripcion = con.getResultSet().getString("co_descripcion");
                tipocompuesto = con.getResultSet().getString("tc_descripcion");

                tableModelCompuesto.addRow(new Object[]{codigo, descripcion, tipocompuesto});
            }
            tbCompuestoBuscadorCompuesto.setModel(tableModelCompuesto);
            metodos.AnchuraColumna(tbCompuestoBuscadorCompuesto);

            if (tbCompuestoBuscadorCompuesto.getModel().getRowCount() == 1) {
                lblCantRegistroBuscadorCompuesto.setText(tbCompuestoBuscadorCompuesto.getModel().getRowCount() + " Registro encontrado");
            } else {
                lblCantRegistroBuscadorCompuesto.setText(tbCompuestoBuscadorCompuesto.getModel().getRowCount() + " Registros encontrados");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        con.DesconectarBasedeDatos();
    }

    public void RegistroNuevoModificar() {
        try {
            if (ComprobarCampos() == true) {
                String codigo, numregistro, numventa, nombrecomercial;
                int idcompuesto, idregistrante, idfabricante, idclaseuso, idformulacion, idpaisorigen, idtoxicologia;
                String sentencia;
                String ultimoIdProducto;

                codigo = txtCodigo.getText();
                numregistro = txtNumRegistro.getText();
                numventa = txtNumVenta.getText();
                nombrecomercial = txtNombreComercial.getText();
                idcompuesto = Integer.parseInt(txtIdCompuesto.getText());
                idregistrante = metodoscombo.ObtenerIDSelectCombo(cbRegistrante);
                idfabricante = metodoscombo.ObtenerIDSelectCombo(cbFabricante);
                idclaseuso = metodoscombo.ObtenerIDSelectCombo(cbClaseUso);
                idformulacion = metodoscombo.ObtenerIDSelectCombo(cbFormulacion);
                idpaisorigen = metodoscombo.ObtenerIDSelectCombo(cbPaisOrigen);
                idtoxicologia = metodoscombo.ObtenerIDSelectCombo(cbToxicologia);

                if (txtCodigo.getText().equals("")) {//Si es nuevo
                    int confirmado = JOptionPane.showConfirmDialog(this, "??Esta seguro crear este nuevo registro?", "Confirmaci??n", JOptionPane.YES_OPTION);
                    if (JOptionPane.YES_OPTION == confirmado) {
                        //REGISTRAR NUEVO
                        sentencia = "CALL SP_ProductoAlta ('" + numregistro + "','" + numventa + "','" + nombrecomercial + "','"
                                + idcompuesto + "'," + idregistrante + ",'" + idfabricante + "','" + idclaseuso + "','"
                                + idformulacion + "','" + idpaisorigen + "','" + idtoxicologia + "')";
                        ultimoIdProducto = con.ObtenerUltimoID("SELECT pro_codigo FROM producto WHERE pro_numregistro='" + txtNumRegistro.getText() + "'");
                    } else {
                        return;
                    }
                } else {
                    int confirmado = JOptionPane.showConfirmDialog(this, "??Est??s seguro de modificar este registro?", "Confirmaci??n", JOptionPane.YES_OPTION);
                    if (JOptionPane.YES_OPTION == confirmado) {
                        sentencia = "CALL SP_ProductoModificar('" + codigo + "','" + numregistro + "','" + numventa + "','"
                                + nombrecomercial + "','" + idcompuesto + "'," + idregistrante + ",'" + idfabricante + "','"
                                + idclaseuso + "','" + idformulacion + "','" + idpaisorigen + "','" + idtoxicologia + "')";
                        ultimoIdProducto = txtCodigo.getText();
                    } else {
                        return;
                    }
                }
                con.EjecutarABM(sentencia, false);
                //Guardarimagen
                metodosimagen.GuardarImagen(rutaFotoProducto + "image_" + ultimoIdProducto + "_A", elFichero);

                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(this, "El registro se agreg?? correctamente", "Informaci??n", JOptionPane.INFORMATION_MESSAGE);

                ConsultaAllProducto(); //Actualizar tabla
                Limpiar();
                ModoEdicion(false);
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
            String nombreComercialSelect = tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 3) + "";
            int confirmado = javax.swing.JOptionPane.showConfirmDialog(this, "??Realmente desea eliminar el producto '" + nombreComercialSelect + "'?", "Confirmaci??n", JOptionPane.YES_OPTION);
            if (confirmado == JOptionPane.YES_OPTION) {
                codigo = Integer.parseInt(tbPrincipal.getValueAt(filasel, 0) + "");
                String sentencia = "CALL SP_ProductoEliminar(" + codigo + ")";
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
        txtIdCompuesto.setText(metodos.SiStringEsNull(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 4) + ""));
        txtTipoCompuesto.setText(metodos.SiStringEsNull(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 5) + ""));
        taDescriCompuesto.setText(metodos.SiStringEsNull(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 6) + ""));
        metodoscombo.SetSelectedNombreItem(cbRegistrante, tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 7).toString());
        metodoscombo.SetSelectedNombreItem(cbFabricante, tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 8).toString());
        metodoscombo.SetSelectedNombreItem(cbClaseUso, tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 9).toString());
        metodoscombo.SetSelectedNombreItem(cbFormulacion, tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 10).toString());
        metodoscombo.SetSelectedNombreItem(cbPaisOrigen, tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 11).toString());
        metodoscombo.SetSelectedNombreItem(cbToxicologia, tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 12).toString());

        metodosimagen.LeerImagen(lblImagen, rutaFotoProducto + "image_" + txtCodigo.getText() + "_A", rutaFotoDefault);

        /*if (metodosimagen.LeerImagen(lbImagen, "/src/forms/producto/imagenes/image_" + txtCodigo.getText() + "_A") == false) {
            URL url = this.getClass().getResource("/forms/producto/imagenes/IconoProductoSinFoto.png");
            lbImagen.setIcon(new ImageIcon(url));
        }*/
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
        btnCargarImagen.setEnabled(valor);

        txtNumRegistro.requestFocus();
    }

    private void Limpiar() {
        txtCodigo.setText("");
        txtNumRegistro.setText("");
        txtNumVenta.setText("");
        txtNombreComercial.setText("");
        txtIdCompuesto.setText("");
        taDescriCompuesto.setText("");
        txtTipoCompuesto.setText("");
        cbClaseUso.setSelectedItem("SIN DATOS");
        cbRegistrante.setSelectedItem("SIN DATOS");
        cbFabricante.setSelectedItem("SIN DATOS");
        cbFormulacion.setSelectedItem("SIN DATOS");
        cbPaisOrigen.setSelectedItem("SIN DATOS");
        cbToxicologia.setSelectedItem("SIN DATOS");
        lblImagen.setIcon(null);

        tbPrincipal.clearSelection();
    }

    public boolean ComprobarCampos() {
        if (metodostxt.ValidarCampoVacioTXT(txtNumRegistro, lblNumRegistro) == false) {
            return false;
        }

        if (txtCodigo.getText().equals("")) { //Si es nuevo producto
            boolean existe = con.SiYaExisteEnLaBD("SELECT pro_numregistro FROM producto WHERE pro_numregistro='" + txtNumRegistro.getText() + "'");

            if (existe == true) { //Si ya existe el numero de cedula en la tabla
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(this, "El N??mero de registro ingresado ya se encuentra registrado", "Error", JOptionPane.ERROR_MESSAGE);
                lblNumRegistro.setForeground(colorAdvertencia);
                txtNumRegistro.requestFocus();
                return false;
            }

        }

        if (metodostxt.ValidarCampoVacioTXT(txtNumVenta, lblNumVenta) == false) {
            return false;
        }

        if (metodostxt.ValidarCampoVacioTXT(txtNombreComercial, lblNombreComercial) == false) {
            return false;
        }

        if (taDescriCompuesto.getText().equals("") == true) {
            lblCompuestos.setForeground(Color.RED);
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(this, "Complete el campo con titulo en rojo", "Advertencia", JOptionPane.WARNING_MESSAGE);
            taDescriCompuesto.requestFocus();
            return false;
        }

        if (cbRegistrante.getSelectedIndex() == -1) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(this, "Seleccione un registrante", "Advertencia", JOptionPane.WARNING_MESSAGE);
            cbRegistrante.requestFocus();
            return false;
        }

        if (cbFabricante.getSelectedIndex() == -1) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(this, "Seleccione un fabricante", "Advertencia", JOptionPane.WARNING_MESSAGE);
            cbFabricante.requestFocus();
            return false;
        }

        if (cbClaseUso.getSelectedIndex() == -1) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(this, "Seleccione una clase de uso", "Advertencia", JOptionPane.WARNING_MESSAGE);
            cbClaseUso.requestFocus();
            return false;
        }

        if (cbFormulacion.getSelectedIndex() == -1) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(this, "Seleccione una formulacion", "Advertencia", JOptionPane.WARNING_MESSAGE);
            cbFormulacion.requestFocus();
            return false;
        }

        if (cbPaisOrigen.getSelectedIndex() == -1) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(this, "Seleccione un pais de origen", "Advertencia", JOptionPane.WARNING_MESSAGE);
            cbPaisOrigen.requestFocus();
            return false;
        }

        if (cbToxicologia.getSelectedIndex() == -1) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(this, "Seleccione un nivel de toxicologia", "Advertencia", JOptionPane.WARNING_MESSAGE);
            cbToxicologia.requestFocus();
            return false;
        }

        return true;
    }

//--------------------------iniComponent()No tocar----------------------------//
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BuscadorCompuesto = new javax.swing.JDialog();
        panel6 = new org.edisoncor.gui.panel.Panel();
        jLabel12 = new javax.swing.JLabel();
        txtBuscarCompuesto = new javax.swing.JTextField();
        lblBuscarCampoCompuesto = new javax.swing.JLabel();
        cbCampoBuscarCompuesto = new javax.swing.JComboBox();
        scCompuestoBuscadorCompuesto = new javax.swing.JScrollPane();
        tbCompuestoBuscadorCompuesto = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        lblCantRegistroBuscadorCompuesto = new javax.swing.JLabel();
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
        lblCompuestos = new javax.swing.JLabel();
        btnMasDosis = new javax.swing.JButton();
        btnModificarDosis = new javax.swing.JButton();
        btnMenosDosis = new javax.swing.JButton();
        lblImagen = new javax.swing.JLabel();
        btnCargarImagen = new javax.swing.JButton();
        btnEliminarImagen = new javax.swing.JButton();
        btnPantallaCompleta = new javax.swing.JButton();
        lblEstado4 = new javax.swing.JLabel();
        cbToxicologia = new javax.swing.JComboBox<>();
        txtNombreComercial = new javax.swing.JTextField();
        lblNombreComercial = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taDescriCompuesto = new javax.swing.JTextArea();
        txtIdCompuesto = new javax.swing.JTextField();
        lblNombreComercial1 = new javax.swing.JLabel();
        scDosis = new javax.swing.JScrollPane();
        tbDosis = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jLabel14 = new javax.swing.JLabel();
        txtTipoCompuesto = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        btnConsultarRegistrantes = new javax.swing.JButton();
        btnQuitarCompuestos2 = new javax.swing.JButton();
        jpBotones2 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        panel1 = new org.edisoncor.gui.panel.Panel();
        labelMetric1 = new org.edisoncor.gui.label.LabelMetric();

        BuscadorCompuesto.setTitle("Buscador de apoderados");
        BuscadorCompuesto.setModal(true);
        BuscadorCompuesto.setSize(new java.awt.Dimension(760, 310));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/iconos40x40/IconoBuscar40.png"))); // NOI18N
        jLabel12.setText("  BUSCAR ");

        txtBuscarCompuesto.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        txtBuscarCompuesto.setForeground(new java.awt.Color(0, 153, 153));
        txtBuscarCompuesto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtBuscarCompuesto.setCaretColor(new java.awt.Color(0, 204, 204));
        txtBuscarCompuesto.setDisabledTextColor(new java.awt.Color(0, 204, 204));
        txtBuscarCompuesto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarCompuestoKeyReleased(evt);
            }
        });

        lblBuscarCampoCompuesto.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        lblBuscarCampoCompuesto.setForeground(new java.awt.Color(255, 255, 255));
        lblBuscarCampoCompuesto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBuscarCampoCompuesto.setText("Buscar por:");

        tbCompuestoBuscadorCompuesto.setAutoCreateRowSorter(true);
        tbCompuestoBuscadorCompuesto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tbCompuestoBuscadorCompuesto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbCompuestoBuscadorCompuesto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Descripci??n", "Tipo de compuesto"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbCompuestoBuscadorCompuesto.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tbCompuestoBuscadorCompuesto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbCompuestoBuscadorCompuesto.setGridColor(new java.awt.Color(0, 153, 204));
        tbCompuestoBuscadorCompuesto.setOpaque(false);
        tbCompuestoBuscadorCompuesto.setRowHeight(20);
        tbCompuestoBuscadorCompuesto.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbCompuestoBuscadorCompuesto.getTableHeader().setReorderingAllowed(false);
        tbCompuestoBuscadorCompuesto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbCompuestoBuscadorCompuestoMousePressed(evt);
            }
        });
        tbCompuestoBuscadorCompuesto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbCompuestoBuscadorCompuestoKeyReleased(evt);
            }
        });
        scCompuestoBuscadorCompuesto.setViewportView(tbCompuestoBuscadorCompuesto);

        lblCantRegistroBuscadorCompuesto.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblCantRegistroBuscadorCompuesto.setForeground(new java.awt.Color(153, 153, 0));
        lblCantRegistroBuscadorCompuesto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCantRegistroBuscadorCompuesto.setText("0 Registros encontrados");
        lblCantRegistroBuscadorCompuesto.setPreferredSize(new java.awt.Dimension(57, 25));

        javax.swing.GroupLayout panel6Layout = new javax.swing.GroupLayout(panel6);
        panel6.setLayout(panel6Layout);
        panel6Layout.setHorizontalGroup(
            panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(scCompuestoBuscadorCompuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 717, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel6Layout.createSequentialGroup()
                        .addComponent(lblCantRegistroBuscadorCompuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8))
                    .addGroup(panel6Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscarCompuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblBuscarCampoCompuesto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbCampoBuscarCompuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panel6Layout.setVerticalGroup(
            panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel6Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cbCampoBuscarCompuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBuscarCampoCompuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBuscarCompuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(scCompuestoBuscadorCompuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(lblCantRegistroBuscadorCompuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout BuscadorCompuestoLayout = new javax.swing.GroupLayout(BuscadorCompuesto.getContentPane());
        BuscadorCompuesto.getContentPane().setLayout(BuscadorCompuestoLayout);
        BuscadorCompuestoLayout.setHorizontalGroup(
            BuscadorCompuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorCompuestoLayout.setVerticalGroup(
            BuscadorCompuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ventana Productos");
        setBackground(new java.awt.Color(45, 62, 80));
        setModal(true);
        setSize(new java.awt.Dimension(1000, 699));

        jpPrincipal.setBackground(new java.awt.Color(0, 153, 153));
        jpPrincipal.setPreferredSize(new java.awt.Dimension(1580, 478));

        jpTabla.setBackground(new java.awt.Color(0, 153, 153));
        jpTabla.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        scPrincipal.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tbPrincipal.setAutoCreateRowSorter(true);
        tbPrincipal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tbPrincipal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        tbPrincipal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "N?? de registro", "N?? de venta", "Nombre del producto", "IdCompuesto", "TIpo Compuesto", "Compuesto", "Registrante", "Fabricante", "Clase de uso", "Formulacion", "Pais de origen", "Toxicologia"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
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
        lbCantRegistros.setForeground(new java.awt.Color(204, 204, 0));
        lbCantRegistros.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbCantRegistros.setText("0 Registros encontrados");
        lbCantRegistros.setPreferredSize(new java.awt.Dimension(57, 25));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/iconos40x40/IconoBuscar40.png"))); // NOI18N
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
        lblBuscarCampoApoderado1.setForeground(new java.awt.Color(255, 255, 255));
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
                .addComponent(scPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbCantRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jpBotones.setBackground(new java.awt.Color(0, 153, 153));
        jpBotones.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jpBotones.setPreferredSize(new java.awt.Dimension(100, 50));

        btnNuevo.setBackground(new java.awt.Color(14, 154, 153));
        btnNuevo.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnNuevo.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/iconos40x40/IconoNuevo40.png"))); // NOI18N
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
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/iconos40x40/IconoModifcar40.png"))); // NOI18N
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
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/iconos40x40/IconoEliminar40.png"))); // NOI18N
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

        jpEdicion.setBackground(new java.awt.Color(0, 153, 153));
        jpEdicion.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        lblCodigo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblCodigo.setForeground(new java.awt.Color(255, 255, 255));
        lblCodigo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodigo.setText("C??digo:");
        lblCodigo.setFocusable(false);

        txtCodigo.setFont(new java.awt.Font("Dialog", 0, 13)); // NOI18N
        txtCodigo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCodigo.setEnabled(false);

        lblNumRegistro.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblNumRegistro.setForeground(new java.awt.Color(255, 255, 255));
        lblNumRegistro.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNumRegistro.setText("N?? de registro*:");
        lblNumRegistro.setFocusable(false);

        txtNumRegistro.setFont(new java.awt.Font("Dialog", 0, 13)); // NOI18N
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

        lblNumVenta.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblNumVenta.setForeground(new java.awt.Color(255, 255, 255));
        lblNumVenta.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNumVenta.setText("N?? de venta*:");
        lblNumVenta.setFocusable(false);

        txtNumVenta.setFont(new java.awt.Font("Dialog", 0, 13)); // NOI18N
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

        jLabel2.setForeground(new java.awt.Color(51, 204, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Campos con (*) son obligatorios");

        lblSexo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblSexo.setForeground(new java.awt.Color(255, 255, 255));
        lblSexo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSexo.setText("Clase de uso*:");
        lblSexo.setToolTipText("");
        lblSexo.setFocusable(false);

        cbClaseUso.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        cbClaseUso.setEnabled(false);

        lblEstado.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblEstado.setForeground(new java.awt.Color(255, 255, 255));
        lblEstado.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblEstado.setText("Fabricante*:");
        lblEstado.setToolTipText("");
        lblEstado.setFocusable(false);

        cbFabricante.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        cbFabricante.setEnabled(false);
        cbFabricante.setNextFocusableComponent(btnGuardar);

        lblEstado1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblEstado1.setForeground(new java.awt.Color(255, 255, 255));
        lblEstado1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblEstado1.setText("Registrante*:");
        lblEstado1.setToolTipText("");
        lblEstado1.setFocusable(false);

        cbRegistrante.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        cbRegistrante.setEnabled(false);
        cbRegistrante.setNextFocusableComponent(cbFabricante);

        lblEstado2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblEstado2.setForeground(new java.awt.Color(255, 255, 255));
        lblEstado2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblEstado2.setText("Formulaci??n*:");
        lblEstado2.setToolTipText("");
        lblEstado2.setFocusable(false);

        lblEstado3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblEstado3.setForeground(new java.awt.Color(255, 255, 255));
        lblEstado3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblEstado3.setText("Pa??s de origen*:");
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

        lblCompuestos.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblCompuestos.setForeground(new java.awt.Color(255, 255, 255));
        lblCompuestos.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCompuestos.setText("Compuestos*:");

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

        lblImagen.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lblImagen.setForeground(new java.awt.Color(255, 255, 255));
        lblImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImagen.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));
        lblImagen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

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

        lblEstado4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblEstado4.setForeground(new java.awt.Color(255, 255, 255));
        lblEstado4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblEstado4.setText("Toxicolog??a*:");
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

        lblNombreComercial.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblNombreComercial.setForeground(new java.awt.Color(255, 255, 255));
        lblNombreComercial.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNombreComercial.setText("Nombre comercial*:");
        lblNombreComercial.setFocusable(false);

        taDescriCompuesto.setColumns(20);
        taDescriCompuesto.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        taDescriCompuesto.setForeground(new java.awt.Color(0, 0, 0));
        taDescriCompuesto.setLineWrap(true);
        taDescriCompuesto.setRows(5);
        taDescriCompuesto.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        taDescriCompuesto.setEnabled(false);
        jScrollPane1.setViewportView(taDescriCompuesto);

        txtIdCompuesto.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        txtIdCompuesto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIdCompuesto.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtIdCompuesto.setEnabled(false);
        txtIdCompuesto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtIdCompuestoFocusLost(evt);
            }
        });
        txtIdCompuesto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIdCompuestoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdCompuestoKeyTyped(evt);
            }
        });

        lblNombreComercial1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblNombreComercial1.setForeground(new java.awt.Color(255, 255, 255));
        lblNombreComercial1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNombreComercial1.setText("Dosis prom.:");
        lblNombreComercial1.setFocusable(false);

        scDosis.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tbDosis.setAutoCreateRowSorter(true);
        tbDosis.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tbDosis.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tbDosis.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Dosis promedio", "Cultivo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbDosis.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tbDosis.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbDosis.setEnabled(false);
        tbDosis.setGridColor(new java.awt.Color(0, 153, 204));
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
        tbDosis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbDosisKeyReleased(evt);
            }
        });
        scDosis.setViewportView(tbDosis);

        jLabel14.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Id del compuesto");

        txtTipoCompuesto.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        txtTipoCompuesto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtTipoCompuesto.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTipoCompuesto.setEnabled(false);
        txtTipoCompuesto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTipoCompuestoFocusLost(evt);
            }
        });
        txtTipoCompuesto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTipoCompuestoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTipoCompuestoKeyTyped(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Tipo compuesto:");

        btnConsultarRegistrantes.setBackground(new java.awt.Color(255, 255, 255));
        btnConsultarRegistrantes.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        btnConsultarRegistrantes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Iconos20x20/IconoBuscar.png"))); // NOI18N
        btnConsultarRegistrantes.setToolTipText("Consultar Registrantes");
        btnConsultarRegistrantes.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnConsultarRegistrantes.setEnabled(false);
        btnConsultarRegistrantes.setPreferredSize(new java.awt.Dimension(35, 31));
        btnConsultarRegistrantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarRegistrantesActionPerformed(evt);
            }
        });

        btnQuitarCompuestos2.setBackground(new java.awt.Color(255, 255, 255));
        btnQuitarCompuestos2.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        btnQuitarCompuestos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Iconos20x20/IconoBuscar.png"))); // NOI18N
        btnQuitarCompuestos2.setToolTipText("Consultar Registrantes");
        btnQuitarCompuestos2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnQuitarCompuestos2.setEnabled(false);
        btnQuitarCompuestos2.setPreferredSize(new java.awt.Dimension(35, 31));
        btnQuitarCompuestos2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarCompuestos2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpEdicionLayout = new javax.swing.GroupLayout(jpEdicion);
        jpEdicion.setLayout(jpEdicionLayout);
        jpEdicionLayout.setHorizontalGroup(
            jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpEdicionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpEdicionLayout.createSequentialGroup()
                        .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpEdicionLayout.createSequentialGroup()
                                .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblNumRegistro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblNumVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblNombreComercial, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                                    .addComponent(lblCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblCompuestos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(2, 2, 2))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpEdicionLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpEdicionLayout.createSequentialGroup()
                                        .addComponent(btnQuitarCompuestos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnCompuestos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpEdicionLayout.createSequentialGroup()
                                        .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtIdCompuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(7, 7, 7)))))
                        .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNumRegistro, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpEdicionLayout.createSequentialGroup()
                                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane1)
                            .addComponent(txtNumVenta, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                            .addComponent(txtNombreComercial, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEstado1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEstado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSexo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEstado2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblEstado3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblEstado4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNombreComercial1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(1, 1, 1))
                    .addGroup(jpEdicionLayout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTipoCompuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(96, 96, 96)))
                .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cbToxicologia, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbPaisOrigen, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbFormulacion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbFabricante, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbRegistrante, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbClaseUso, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scDosis, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpEdicionLayout.createSequentialGroup()
                        .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnConsultarRegistrantes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnQuitarCompuestos2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEliminarImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCargarImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPantallaCompleta, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jpEdicionLayout.createSequentialGroup()
                        .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnModificarDosis, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnMasDosis, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnMenosDosis, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpEdicionLayout.setVerticalGroup(
            jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpEdicionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpEdicionLayout.createSequentialGroup()
                        .addComponent(btnConsultarRegistrantes, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(105, 105, 105)
                        .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpEdicionLayout.createSequentialGroup()
                                .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(lblCompuestos, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblEstado3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbPaisOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(4, 4, 4)
                                .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblEstado4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbToxicologia, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jpEdicionLayout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnCompuestos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnQuitarCompuestos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpEdicionLayout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(txtIdCompuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jpEdicionLayout.createSequentialGroup()
                        .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombreComercial1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(scDosis, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpEdicionLayout.createSequentialGroup()
                                .addComponent(btnMasDosis, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnModificarDosis, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMenosDosis, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jpEdicionLayout.createSequentialGroup()
                .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpEdicionLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(btnCargarImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPantallaCompleta, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpEdicionLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(lblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEstado1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbRegistrante, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(lblNumRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNumRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbFabricante, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnQuitarCompuestos2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(lblNumVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNumVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbClaseUso, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbFormulacion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblEstado2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(lblNombreComercial, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNombreComercial, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(4, 4, 4)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jpEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTipoCompuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jtpEdicion.addTab("Edici??n", jpEdicion);

        jpBotones2.setBackground(new java.awt.Color(0, 153, 153));
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
        btnCancelar.setToolTipText("Cancela la acci??n");
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

        panel1.setColorPrimario(new java.awt.Color(0, 102, 102));
        panel1.setColorSecundario(new java.awt.Color(0, 153, 153));

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
                            .addComponent(jtpEdicion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jpBotones, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                    .addComponent(jpTabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(8, 8, 8)
                .addComponent(jtpEdicion, javax.swing.GroupLayout.PREFERRED_SIZE, 324, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpBotones2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jtpEdicion.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
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
        //Cantidad de caracteres
        metodostxt.TxtCantidadCaracteresKeyTyped(txtNumVenta, 70);
    }//GEN-LAST:event_txtNumVentaKeyTyped

    private void txtNumVentaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumVentaKeyReleased
        metodostxt.TxtColorLabelKeyReleased(txtNumVenta, lblNumVenta, colorTitulos);
    }//GEN-LAST:event_txtNumVentaKeyReleased

    private void txtNumRegistroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumRegistroKeyTyped
        //Cantidad de caracteres
        metodostxt.TxtCantidadCaracteresKeyTyped(txtNumRegistro, 45);
    }//GEN-LAST:event_txtNumRegistroKeyTyped

    private void txtNumRegistroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumRegistroKeyReleased
        metodostxt.TxtColorLabelKeyReleased(txtNumRegistro, lblNumRegistro, colorTitulos);
    }//GEN-LAST:event_txtNumRegistroKeyReleased

    private void tbPrincipalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPrincipalKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_UP || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            ModoVistaPrevia();
        }
    }//GEN-LAST:event_tbPrincipalKeyReleased

    private void txtBuscarCompuestoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarCompuestoKeyReleased
        metodos.FiltroJTable(txtBuscarCompuesto.getText(), cbCampoBuscarCompuesto.getSelectedIndex(), tbCompuestoBuscadorCompuesto);

        if (tbCompuestoBuscadorCompuesto.getRowCount() == 1) {
            lblCantRegistroBuscadorCompuesto.setText(tbCompuestoBuscadorCompuesto.getRowCount() + " Registro encontrado");
        } else {
            lblCantRegistroBuscadorCompuesto.setText(tbCompuestoBuscadorCompuesto.getRowCount() + " Registros encontrados");
        }
    }//GEN-LAST:event_txtBuscarCompuestoKeyReleased

    private void tbCompuestoBuscadorCompuestoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbCompuestoBuscadorCompuestoMousePressed
        if (evt.getClickCount() == 2) {
            DobleClickTbCompuesto();
        }
    }//GEN-LAST:event_tbCompuestoBuscadorCompuestoMousePressed

    private void DobleClickTbCompuesto() throws NumberFormatException {
        int idCompuestoSelect = Integer.parseInt(tbCompuestoBuscadorCompuesto.getValueAt(tbCompuestoBuscadorCompuesto.getSelectedRow(), 0) + "");
        String descripcionSelect = tbCompuestoBuscadorCompuesto.getValueAt(tbCompuestoBuscadorCompuesto.getSelectedRow(), 1) + "";
        String tipocompuestoSelect = tbCompuestoBuscadorCompuesto.getValueAt(tbCompuestoBuscadorCompuesto.getSelectedRow(), 2) + "";
        txtIdCompuesto.setText(idCompuestoSelect + "");
        taDescriCompuesto.setText(descripcionSelect);
        txtTipoCompuesto.setText(tipocompuestoSelect);

        if (taDescriCompuesto.getText().equals("") == false) {
            lblCompuestos.setForeground(colorTitulos);
        }

        BuscadorCompuesto.dispose();
    }

    private void tbCompuestoBuscadorCompuestoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbCompuestoBuscadorCompuestoKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            DobleClickTbCompuesto();
        }
    }//GEN-LAST:event_tbCompuestoBuscadorCompuestoKeyReleased

    private void tbPrincipalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPrincipalMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPrincipalMouseClicked

    private void txtNumRegistroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumRegistroFocusLost
        txtNumRegistro.setText(metodostxt.QuitaEspaciosString(txtNumRegistro.getText()));
    }//GEN-LAST:event_txtNumRegistroFocusLost

    private void txtNumVentaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumVentaFocusLost
        txtNumVenta.setText(metodostxt.QuitaEspaciosString(txtNumVenta.getText()));
        txtNumVenta.setText(metodostxt.MayusCadaPrimeraLetra(txtNumVenta.getText()));
    }//GEN-LAST:event_txtNumVentaFocusLost

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        metodos.FiltroJTable(txtBuscar.getText(), cbCampoBuscar.getSelectedIndex(), tbPrincipal);
        metodos.AnchuraColumna(tbPrincipal);
        Limpiar();
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
            JOptionPane.showMessageDialog(null, "No se seleccion?? ninguna formulaci??n", "Error", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(null, "No se seleccion?? ninguna formulaci??n", "Error", JOptionPane.ERROR_MESSAGE);
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
        elFichero = metodosimagen.CargarImagenFC(lblImagen);
    }//GEN-LAST:event_btnCargarImagenActionPerformed

    private void btnEliminarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarImagenActionPerformed
        /*URL url = this.getClass().getResource("/forms/producto/iconos/IconoProductoSinFoto.png");
        lbImagen.setIcon(new ImageIcon(url));

        btnEliminarImagen.setEnabled(!(lbImagen.getIcon().toString().equals(imagendefault.toString()))); //Revisa si el icono es default*/
    }//GEN-LAST:event_btnEliminarImagenActionPerformed

    private void btnPantallaCompletaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPantallaCompletaActionPerformed
        VistaCompleta vistacompleta = new VistaCompleta("src/forms/producto/imagenes/image_" + txtCodigo.getText() + "_A", rutaFotoDefault);
        vistacompleta.setLocationRelativeTo(this);
        vistacompleta.setVisible(true);
    }//GEN-LAST:event_btnPantallaCompletaActionPerformed

    private void txtNombreComercialFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNombreComercialFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreComercialFocusLost

    private void txtNombreComercialKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreComercialKeyReleased
        metodostxt.TxtColorLabelKeyReleased(txtNombreComercial, lblNombreComercial, colorTitulos);
    }//GEN-LAST:event_txtNombreComercialKeyReleased

    private void txtNombreComercialKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreComercialKeyTyped
        metodostxt.TxtCantidadCaracteresKeyTyped(txtNombreComercial, 400);
    }//GEN-LAST:event_txtNombreComercialKeyTyped

    private void btnQuitarCompuestosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarCompuestosActionPerformed
        /*ArrayEliminadosIA.add(tbIngrActivos.getValueAt(tbIngrActivos.getSelectedRow(), 0).toString()); //Guarda el id del registro eliminado en la lista de elimiados ListaEliminados

        DefaultTableModel ModeloTabla = (DefaultTableModel) tbIngrActivos.getModel();
        ModeloTabla.removeRow(tbIngrActivos.getSelectedRow());
        btnMenos.setEnabled(false);*/
    }//GEN-LAST:event_btnQuitarCompuestosActionPerformed

    private void btnCompuestosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompuestosActionPerformed
        ConsultaAllCompuestos();

        BuscadorCompuesto.setLocationRelativeTo(this);
        BuscadorCompuesto.setVisible(true);
    }//GEN-LAST:event_btnCompuestosActionPerformed

    private void cbFormulacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFormulacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbFormulacionActionPerformed

    private void txtIdCompuestoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdCompuestoFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdCompuestoFocusLost

    private void txtIdCompuestoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdCompuestoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdCompuestoKeyReleased

    private void txtIdCompuestoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdCompuestoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdCompuestoKeyTyped

    private void tbDosisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDosisMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbDosisMouseClicked

    private void tbDosisMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDosisMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbDosisMousePressed

    private void tbDosisKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDosisKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tbDosisKeyReleased

    private void txtTipoCompuestoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTipoCompuestoFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTipoCompuestoFocusLost

    private void txtTipoCompuestoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTipoCompuestoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTipoCompuestoKeyReleased

    private void txtTipoCompuestoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTipoCompuestoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTipoCompuestoKeyTyped

    private void btnConsultarRegistrantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarRegistrantesActionPerformed

    }//GEN-LAST:event_btnConsultarRegistrantesActionPerformed

    private void btnQuitarCompuestos2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarCompuestos2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnQuitarCompuestos2ActionPerformed

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
    private javax.swing.JDialog BuscadorCompuesto;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCargarImagen;
    private javax.swing.JButton btnCompuestos;
    private javax.swing.JButton btnConsultarRegistrantes;
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
    private javax.swing.JButton btnQuitarCompuestos2;
    private javax.swing.JComboBox cbCampoBuscar;
    private javax.swing.JComboBox cbCampoBuscarCompuesto;
    private javax.swing.JComboBox<MetodosCombo> cbClaseUso;
    private javax.swing.JComboBox<MetodosCombo> cbFabricante;
    private javax.swing.JComboBox<MetodosCombo> cbFormulacion;
    private javax.swing.JComboBox<MetodosCombo> cbPaisOrigen;
    private javax.swing.JComboBox<MetodosCombo> cbRegistrante;
    private javax.swing.JComboBox<MetodosCombo> cbToxicologia;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JLabel lblBuscarCampoApoderado1;
    private javax.swing.JLabel lblBuscarCampoCompuesto;
    private javax.swing.JLabel lblCantRegistroBuscadorCompuesto;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblCompuestos;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblEstado1;
    private javax.swing.JLabel lblEstado2;
    private javax.swing.JLabel lblEstado3;
    private javax.swing.JLabel lblEstado4;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JLabel lblNombreComercial;
    private javax.swing.JLabel lblNombreComercial1;
    private javax.swing.JLabel lblNumRegistro;
    private javax.swing.JLabel lblNumVenta;
    private javax.swing.JLabel lblSexo;
    private org.edisoncor.gui.panel.Panel panel1;
    private org.edisoncor.gui.panel.Panel panel6;
    private javax.swing.JScrollPane scCompuestoBuscadorCompuesto;
    private javax.swing.JScrollPane scDosis;
    private javax.swing.JScrollPane scPrincipal;
    private javax.swing.JTextArea taDescriCompuesto;
    private javax.swing.JTable tbCompuestoBuscadorCompuesto;
    private javax.swing.JTable tbDosis;
    private javax.swing.JTable tbPrincipal;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtBuscarCompuesto;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtIdCompuesto;
    private javax.swing.JTextField txtNombreComercial;
    private javax.swing.JTextField txtNumRegistro;
    private javax.swing.JTextField txtNumVenta;
    private javax.swing.JTextField txtTipoCompuesto;
    // End of variables declaration//GEN-END:variables
}
