/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms.aplicaciones;

import conexion.Conexion;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
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
public class Aplicacion extends javax.swing.JDialog {

    private Conexion con = new Conexion();
    private Metodos metodos = new Metodos();
    private MetodosTXT metodostxt = new MetodosTXT();
    private MetodosCombo metodoscombo = new MetodosCombo();
    private DefaultTableModel tableModelAplicaciones;
    private DefaultTableModel tableModelAllProductos;

    private DefaultTableModel tableModelAllProductosSelect;
    private DefaultTableModel tableModelProductosSelect;

    private Color colorVerde = new Color(6, 147, 27);
    private Color colorRojo = new Color(206, 16, 45);

    public Aplicacion(java.awt.Frame parent, Boolean modal) {
        super(parent, modal);
        initComponents();

        metodos.AnchuraColumna(tbPrincipal);

        //Poner fecha actual
        //dcFechaInscripcion.setDate(new Date());
        //LLamar metodos
        CargarTableAplicacion();
        CargarTablaAllProducto();

        CargarComboBoxes();

        //Permiso Roles de usuario
        //Permiso Roles de usuario
        String permisos = metodos.PermisoRol(codUsuario, "APLICACION");
        btnNuevo.setVisible(permisos.contains("A"));
        btnModificar.setVisible(permisos.contains("M"));
        btnEliminar.setVisible(permisos.contains("B"));

        //Cambiar color de disabled combo
        //metodoscombo.CambiarColorDisabledCombo(cbSexo, Color.BLACK);
        //metodoscombo.CambiarColorDisabledCombo(cbEstado, Color.BLACK);
    }

    //--------------------------METODOS----------------------------//
    private void CargarComboBoxes() {
        //Carga los combobox con las consultas
        metodoscombo.CargarComboConsulta(cbEmprendimiento, "SELECT emp_codigo, emp_descripcion FROM emprendimiento ORDER BY emp_descripcion", -1);
        metodoscombo.CargarComboConsulta(cbBloque, "SELECT blo_codigo, blo_descripcion FROM bloque ORDER BY blo_descripcion", 0);
    }

    private void CargarTableAplicacion() {//Realiza la consulta de los productos que tenemos en la base de datos
        /* String sentencia = "CALL SP_ApoderadoConsulta";
        String titlesJtabla[] = {"Código", "N° de cédula", "Nombre", "Apellido", "Sexo", "Dirección", "Teléfono", "Email", "Observación"};

        tbApoderado.setModel(con.ConsultaTableBD(sentencia, titlesJtabla, cbCampoBuscarApoderado));
        cbCampoBuscarApoderado.setSelectedIndex(1);
        metodos.AnchuraColumna(tbApoderado);

        if (tbApoderado.getModel().getRowCount() == 1) {
            lbCantRegistrosApoderado.setText(tbApoderado.getModel().getRowCount() + " Registro encontrado");
        } else {
            lbCantRegistrosApoderado.setText(tbApoderado.getModel().getRowCount() + " Registros encontrados");
        }*/
    }

    private void CargarTablaAllProducto() {//Realiza la consulta de los productos que tenemos en la base de datos
        tableModelAllProductosSelect = (DefaultTableModel) tbAllProductosSelect.getModel();
        tableModelAllProductosSelect.setRowCount(0);
        if (cbCampoBuscarProductosSelect.getItemCount() == 0) {
            metodos.CargarTitlesaCombo(cbCampoBuscarProductosSelect, tbAllProductosSelect);
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
                nombre = con.getResultSet().getString("pro_nombrecomercial");
                compuesto = con.getResultSet().getString("co_descripcion");
                registrante = con.getResultSet().getString("re_descripcion");
                fabricante = con.getResultSet().getString("fa_descripcion");
                claseuso = con.getResultSet().getString("cu_descripcion");
                formulacion = con.getResultSet().getString("for_descripcion");
                paisorigen = con.getResultSet().getString("po_descripcion");
                toxicologia = con.getResultSet().getString("to_descripcion");

                tableModelAllProductosSelect.addRow(new Object[]{codigo, numregistro, numventa, nombre, compuesto, registrante, fabricante, claseuso, formulacion, paisorigen, toxicologia});
            }
            tbAllProductosSelect.setModel(tableModelAllProductosSelect);
            metodos.AnchuraColumna(tbAllProductosSelect);

            if (tbAllProductosSelect.getModel().getRowCount() == 1) {
                lbCantRegistrosProductosSelect.setText(tbAllProductosSelect.getModel().getRowCount() + " Registro encontrado");
            } else {
                lbCantRegistrosProductosSelect.setText(tbAllProductosSelect.getModel().getRowCount() + " Registros encontrados");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        con.DesconectarBasedeDatos();
    }

    public void RegistroNuevoModificar() {
        /* try {
            if (ComprobarCampos() == true) {
                String codigo = txtCodigo.getText();
                String nombre = txtNombre.getText();
                String apellido = txtApellido.getText();
                String cedula = null;
                if (chbSincedula.isSelected() == false) {
                    cedula = metodostxt.StringSinPuntosMiles(txtCedula.getText()) + "";
                    cedula = metodostxt.QuitaEspaciosString(cedula);
                }
                SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd");
                String fechanacimiento = formatofecha.format(dcFechaNacimiento.getDate());
                String fechainscripcion = formatofecha.format(dcFechaInscripcion.getDate());
                String sexo = cbSexo.getSelectedItem().toString();
                String telefono = txtTelefono.getText();
                String email = txtEmail.getText();
                String obs = taObs.getText();
                int apoderado = metodoscombo.ObtenerIDSelectCombo(cbApoderado);
                int estado = cbEstado.getSelectedIndex();

                if (txtCodigo.getText().equals("")) {//Si es nuevo
                    int confirmado = JOptionPane.showConfirmDialog(this, "¿Esta seguro crear este nuevo registro?", "Confirmación", JOptionPane.YES_OPTION);
                    if (JOptionPane.YES_OPTION == confirmado) {
                        //REGISTRAR NUEVO
                        String sentencia = "CALL SP_AlumnoAlta ('" + nombre + "','" + apellido + "'," + cedula + ",'" + fechanacimiento + "','" + fechainscripcion
                                + "','" + sexo + "','" + telefono + "','" + email + "','" + obs + "','" + apoderado + "','" + estado + "')";
                        con.EjecutarABM(sentencia, true);

                        ConsultaAllAlumno(); //Actualizar tabla
                        Limpiar();
                        ModoEdicion(false);
                    }
                } else {
                    int confirmado = JOptionPane.showConfirmDialog(this, "¿Estás seguro de modificar este registro?", "Confirmación", JOptionPane.YES_OPTION);
                    if (JOptionPane.YES_OPTION == confirmado) {
                        String sentencia = "CALL SP_AlumnoModificar('" + codigo + "','" + nombre + "','" + apellido + "'," + cedula
                                + ",'" + fechanacimiento + "','" + fechainscripcion + "','" + sexo + "','" + telefono + "','" + email
                                + "','" + obs + "','" + apoderado + "','" + estado + "')";

                        con.EjecutarABM(sentencia, true);
                        ConsultaAllAlumno(); //Actualizar tabla                        
                        ModoEdicion(false);
                        Limpiar();
                        //this.repaint();
                    }
                }
            }
        } catch (HeadlessException ex) {
            JOptionPane.showMessageDialog(this, "Completar los campos obligarios marcados con * ", "Advertencia", JOptionPane.WARNING_MESSAGE);
            System.out.println("Completar los campos obligarios marcados con * " + ex);
            txtNombre.requestFocus();
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

                ConsultaAllAlumno(); //Actualizar tabla
                ModoEdicion(false);
                Limpiar();
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se ha seleccionado ninguna fila", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void ConsultaAllAlumno() {//Realiza la consulta de los productos que tenemos en la base de datos
        modeltableAlumnos = (DefaultTableModel) tbPrincipal.getModel();
        modeltableAlumnos.setRowCount(0); //Vacia tabla

        if (cbCampoBuscar.getItemCount() == 0) {
            metodos.CargarTitlesaCombo(cbCampoBuscar, tbPrincipal);
        }

        try {
            String sentencia = "CALL SP_AlumnoConsulta()";
            con = con.ObtenerRSSentencia(sentencia);
            int codigo;
            String nombre, apellido, sexo, cedula, telefono, fechanac, fechains, email, obs, apoderado, estado;
            while (con.getResultSet().next()) {
                codigo = con.getResultSet().getInt("alu_codigo");
                nombre = con.getResultSet().getString("alu_nombre");
                apellido = con.getResultSet().getString("alu_apellido");
                cedula = con.getResultSet().getString("alu_cedula");
                if (cedula == null) {
                    cedula = "0";
                }
                fechanac = con.getResultSet().getString("fechanacimiento");
                fechains = con.getResultSet().getString("fechainscripcion");
                sexo = con.getResultSet().getString("alu_sexo");
                telefono = con.getResultSet().getString("alu_telefono");
                email = con.getResultSet().getString("alu_email");
                obs = con.getResultSet().getString("alu_obs");
                apoderado = con.getResultSet().getString("nomapeapoderado");
                estado = con.getResultSet().getString("estado");

                modeltableAlumnos.addRow(new Object[]{codigo, nombre, apellido, cedula, fechanac, fechains, sexo, telefono, email, obs, apoderado, estado});
            }
            tbPrincipal.setModel(modeltableAlumnos);
            metodos.AnchuraColumna(tbPrincipal);
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        con.DesconectarBasedeDatos();

        if (tbPrincipal.getModel().getRowCount() == 1) {
            lbCantRegistros.setText(tbPrincipal.getModel().getRowCount() + " Registro encontrado");
        } else {
            lbCantRegistros.setText(tbPrincipal.getModel().getRowCount() + " Registros encontrados");
        }*/
    }

    private void ModoVistaPrevia() {
        /*txtCodigo.setText(metodos.SiStringEsNull(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 0) + ""));
        txtNombre.setText(metodos.SiStringEsNull(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 1) + ""));
        txtApellido.setText(metodos.SiStringEsNull(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 2) + ""));
        txtCedula.setText(metodos.SiStringEsNull(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 3) + ""));
        txtCedula.setText(metodostxt.StringPuntosMiles(txtCedula.getText()));

        chbSincedula.setSelected(false);
        if (txtCedula.getText().equals("0")) {
            chbSincedula.setSelected(true);
        }

        try {
            Date fechaParseada;
            fechaParseada = new SimpleDateFormat("dd/MM/yyyy").parse(metodos.SiStringEsNull(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 4).toString() + ""));
            dcFechaNacimiento.setDate(fechaParseada);

            //Obtener edad
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fechaNac = LocalDate.parse(metodos.SiStringEsNull(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 4) + ""), fmt);
            LocalDate ahora = LocalDate.now();
            Period periodo = Period.between(fechaNac, ahora);
            txtEdad.setText(metodos.SiStringEsNull(periodo.getYears() + ""));

            fechaParseada = new SimpleDateFormat("dd/MM/yyyy").parse(metodos.SiStringEsNull(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 5) + ""));
            dcFechaInscripcion.setDate(fechaParseada);
        } catch (ParseException e) {
            System.out.println("Error al parsear fecha");
        }

        cbSexo.setSelectedItem(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 6).toString());
        txtTelefono.setText(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 7).toString());
        txtEmail.setText(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 8).toString());
        taObs.setText(tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 9).toString());

        String apoderado = tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 10).toString();
        metodoscombo.SetSelectedNombreItem(cbApoderado, apoderado);

        String estado = tbPrincipal.getValueAt(tbPrincipal.getSelectedRow(), 11).toString();
        cbEstado.setSelectedItem(estado);*/
    }

    private void ModoEdicion(boolean valor) {
        /*tbPrincipal.setEnabled(!valor);

        txtNombre.setEnabled(valor);
        txtApellido.setEnabled(valor);

        if (chbSincedula.isSelected()) {
            txtCedula.setEnabled(false);
        } else {
            txtCedula.setEnabled(true);
        }
        chbSincedula.setEnabled(valor);
        if (valor == false) {
            txtCedula.setEnabled(valor);
        }

        dcFechaNacimiento.setEnabled(valor);
        dcFechaInscripcion.setEnabled(valor);
        cbSexo.setEnabled(valor);
        txtTelefono.setEnabled(valor);
        txtEmail.setEnabled(valor);
        taObs.setEnabled(valor);
        cbApoderado.setEnabled(valor);
        btnBuscarApoderado.setEnabled(valor);
        cbEstado.setEnabled(valor);
        btnNuevo.setEnabled(!valor);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnGuardar.setEnabled(valor);
        btnCancelar.setEnabled(valor);

        txtNombre.requestFocus();*/
    }

    private void Limpiar() {
        /*txtCodigo.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtCedula.setText("");
        chbSincedula.setSelected(false);
        dcFechaNacimiento.setCalendar(null);
        txtEdad.setText("");
        Calendar c2 = new GregorianCalendar();
        dcFechaInscripcion.setCalendar(c2);
        cbSexo.setSelectedIndex(0);
        txtEmail.setText("");
        txtTelefono.setText("");
        taObs.setText("");
        cbEstado.setSelectedItem(0);
        cbApoderado.setSelectedIndex(-1);
        lblFechaIngreso.setForeground(Color.DARK_GRAY);
        lblNombre.setForeground(Color.DARK_GRAY);
        lblApellido.setForeground(Color.DARK_GRAY);
        lblCedula.setForeground(Color.DARK_GRAY);

        tbPrincipal.clearSelection();*/
    }

    public boolean ComprobarCampos() {
        /* if (metodostxt.ValidarCampoVacioTXT(txtNombre, lblNombre) == false) {
            return false;
        }

        if (metodostxt.ValidarCampoVacioTXT(txtApellido, lblApellido) == false) {
            return false;
        }

        if (metodostxt.ValidarCampoVacioTXT(txtCedula, lblCedula) == false) {
            return false;
        }

        if (metodostxt.ValidarCampoVacioTXT(txtCedula, lblCedula) == false) {
            return false;
        }

        if (txtCedula.getText().equals("0") && chbSincedula.isSelected() == false) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(this, "El N° de cédula no puede ser 0", "Error", JOptionPane.ERROR_MESSAGE);
            txtCedula.requestFocus();
            return false;
        }

        if (txtCodigo.getText().equals("") && txtCedula.getText().equals("0") == false) {
            try {
                con = con.ObtenerRSSentencia("SELECT alu_cedula FROM alumno WHERE alu_cedula='" + metodostxt.StringSinPuntosMiles(txtCedula.getText()) + "'");
                if (con.getResultSet().next() == true) { //Si ya existe el numero de cedula en la tabla
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(this, "El N° de cédula ingresado ya se encuentra registrado", "Error", JOptionPane.ERROR_MESSAGE);
                    lblCedula.setForeground(colorRojo);
                    lblCedula.requestFocus();
                    return false;
                }
            } catch (SQLException e) {
                System.out.println("Error al buscar si ci ya existe en bd: " + e);
            } catch (NullPointerException e) {
                System.out.println("La CI ingresada no existe en la bd, aprobado: " + e);
            }
            con.DesconectarBasedeDatos();
        }

        if (dcFechaNacimiento.getDate().after(new Date()) == true) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(this, "La fecha de nacimiento no puede ser mayor a la fecha actual", "Advertencia", JOptionPane.WARNING_MESSAGE);
            dcFechaNacimiento.requestFocus();
            return false;
        }

        if (dcFechaNacimiento.getDate() == null) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(this, "Ingrese una fecha de nacimiento válida", "Advertencia", JOptionPane.WARNING_MESSAGE);
            dcFechaNacimiento.requestFocus();
            return false;
        }

        if (dcFechaInscripcion.getDate() == null) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(this, "Ingrese una fecha de inscripción válida", "Advertencia", JOptionPane.WARNING_MESSAGE);
            dcFechaInscripcion.requestFocus();
            return false;
        }

        if (cbApoderado.getSelectedIndex() == -1) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(this, "Seleccione un apoderado", "Advertencia", JOptionPane.WARNING_MESSAGE);
            cbApoderado.requestFocus();
            return false;
        }*/
        return true;
    }

    private void AnhadirProducto() {
        int idProductoSelect = (int) tbAllProductosSelect.getValueAt(tbAllProductosSelect.getSelectedRow(), 0);
        int idProductoTabla;

        for (int f = 0; f < tbProductosSelect.getRowCount(); f++) {
            idProductoTabla = (int) tbProductosSelect.getValueAt(f, 0);
            if (idProductoSelect == idProductoTabla) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(SelectProductos, "Este producto ya se encuentra seleccionado", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        if (tbAllProductosSelect.getRowCount() > 0) {
            int idProducto;
            String nombreComercial, compuesto, numRegistro, registrante, claseUso;
            idProducto = (int) tbAllProductosSelect.getValueAt(tbAllProductosSelect.getSelectedRow(), 0);
            nombreComercial = tbAllProductosSelect.getValueAt(tbAllProductosSelect.getSelectedRow(), 3) + "";
            compuesto = tbAllProductosSelect.getValueAt(tbAllProductosSelect.getSelectedRow(), 4) + "";
            numRegistro = tbAllProductosSelect.getValueAt(tbAllProductosSelect.getSelectedRow(), 1) + "";
            registrante = tbAllProductosSelect.getValueAt(tbAllProductosSelect.getSelectedRow(), 5) + "";
            claseUso = tbAllProductosSelect.getValueAt(tbAllProductosSelect.getSelectedRow(), 7) + "";

            tableModelProductosSelect = (DefaultTableModel) tbProductosSelect.getModel();
            tableModelProductosSelect.addRow(new Object[]{idProducto, nombreComercial, compuesto, numRegistro, registrante, claseUso});
            metodos.AnchuraColumna(tbProductosSelect);
        } else {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(SelectProductos, "No se escogió ningún producto para añadir", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

//--------------------------iniComponent()No tocar----------------------------//
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        NuevoAplicacion = new javax.swing.JDialog();
        panel2 = new org.edisoncor.gui.panel.Panel();
        labelMetric2 = new org.edisoncor.gui.label.LabelMetric();
        jLabel2 = new javax.swing.JLabel();
        jpTabla2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        cbCultivoNuevoApl = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        cbCultivoNuevoApl1 = new javax.swing.JComboBox<>();
        cbCultivoNuevoApl2 = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        cbCultivoNuevoApl3 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jpTabla3 = new javax.swing.JPanel();
        scPrincipal3 = new javax.swing.JScrollPane();
        tbPrincipal3 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        btnNuevo1 = new javax.swing.JButton();
        btnModificar1 = new javax.swing.JButton();
        btnEliminar2 = new javax.swing.JButton();
        btnEliminar3 = new javax.swing.JButton();
        SelectProductos = new javax.swing.JDialog();
        panel6 = new org.edisoncor.gui.panel.Panel();
        jLabel12 = new javax.swing.JLabel();
        txtBuscarAgregarProducto = new javax.swing.JTextField();
        lblBuscarCampoApoderado = new javax.swing.JLabel();
        cbCampoBuscarProductosSelect = new javax.swing.JComboBox();
        lbCantRegistrosProductosSelect = new javax.swing.JLabel();
        scAllProductosSelect = new javax.swing.JScrollPane();
        tbAllProductosSelect = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButton1 = new javax.swing.JButton();
        jpSelectProductos = new javax.swing.JPanel();
        scSelectProductos = new javax.swing.JScrollPane();
        tbProductosSelect = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jButton2 = new javax.swing.JButton();
        btnVaciarTabla = new javax.swing.JButton();
        lblCantProductosSeleccionados = new javax.swing.JLabel();
        btnNombresComerciales = new org.edisoncor.gui.button.ButtonColoredAction();
        btnCompuestos = new org.edisoncor.gui.button.ButtonColoredAction();
        btnNumRegistros = new org.edisoncor.gui.button.ButtonColoredAction();
        btnRegistrantes = new org.edisoncor.gui.button.ButtonColoredAction();
        lblCantProductosSeleccionados1 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jpPrincipal = new javax.swing.JPanel();
        panel1 = new org.edisoncor.gui.panel.Panel();
        labelMetric1 = new org.edisoncor.gui.label.LabelMetric();
        jpTabla = new javax.swing.JPanel();
        scPrincipal = new javax.swing.JScrollPane();
        tbPrincipal = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jpTabla1 = new javax.swing.JPanel();
        scProductos = new javax.swing.JScrollPane();
        tbProductos = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jpBotones = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnEliminar1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbEmprendimiento = new javax.swing.JComboBox<>();
        cbParcela = new javax.swing.JComboBox<>();
        cbLote = new javax.swing.JComboBox<>();
        cbBloque = new javax.swing.JComboBox<>();
        cbSubParcela = new javax.swing.JComboBox<>();
        cbSubLote = new javax.swing.JComboBox<>();

        NuevoAplicacion.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        NuevoAplicacion.setTitle("Ventana Aplicaciones");
        NuevoAplicacion.setBackground(new java.awt.Color(45, 62, 80));
        NuevoAplicacion.setModal(true);
        NuevoAplicacion.setPreferredSize(new java.awt.Dimension(889, 705));
        NuevoAplicacion.setSize(new java.awt.Dimension(889, 705));

        panel2.setColorPrimario(new java.awt.Color(0, 102, 102));
        panel2.setColorSecundario(new java.awt.Color(0, 153, 153));
        panel2.setPreferredSize(new java.awt.Dimension(889, 705));

        labelMetric2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelMetric2.setText("NUEVA APLICACIÓN");
        labelMetric2.setDireccionDeSombra(110);
        labelMetric2.setFocusable(false);
        labelMetric2.setFont(new java.awt.Font("Cooper Black", 0, 28)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nueva aplicación en XXXXXX");

        jpTabla2.setBackground(new java.awt.Color(0, 153, 153));
        jpTabla2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Datos de la aplicación", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Cultivo*:");

        cbCultivoNuevoApl.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Fecha de apl.*:");

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Área aplicada*:");

        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Objetivo de apl.*:");

        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Aplicador*:");

        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Tipo de pico*:");

        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("EPI*:");

        cbCultivoNuevoApl1.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N

        cbCultivoNuevoApl2.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N

        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Volumen de agua*:");

        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("Humedad*:");

        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel19.setText("lts");

        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Viento*:");

        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("Observación:");

        cbCultivoNuevoApl3.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTextField1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jTextField2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField2.setToolTipText("");

        jTextField3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField3.setToolTipText("");

        jTextField4.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jTextField5.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("Temperatura*:");

        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("lts");

        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("lts");

        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("lts");

        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel26.setText("lts");

        javax.swing.GroupLayout jpTabla2Layout = new javax.swing.GroupLayout(jpTabla2);
        jpTabla2.setLayout(jpTabla2Layout);
        jpTabla2Layout.setHorizontalGroup(
            jpTabla2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTabla2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpTabla2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpTabla2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbCultivoNuevoApl2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cbCultivoNuevoApl1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbCultivoNuevoApl3, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbCultivoNuevoApl, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jpTabla2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpTabla2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpTabla2Layout.createSequentialGroup()
                        .addGroup(jpTabla2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jpTabla2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58))
        );
        jpTabla2Layout.setVerticalGroup(
            jpTabla2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTabla2Layout.createSequentialGroup()
                .addGroup(jpTabla2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpTabla2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jpTabla2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(jpTabla2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpTabla2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpTabla2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpTabla2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1)))
                    .addGroup(jpTabla2Layout.createSequentialGroup()
                        .addGroup(jpTabla2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpTabla2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbCultivoNuevoApl, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpTabla2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpTabla2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpTabla2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbCultivoNuevoApl2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpTabla2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbCultivoNuevoApl3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpTabla2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbCultivoNuevoApl1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpTabla3.setBackground(new java.awt.Color(0, 153, 153));
        jpTabla3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Productos aplicados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Dialog", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        scPrincipal3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tbPrincipal3.setAutoCreateRowSorter(true);
        tbPrincipal3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tbPrincipal3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbPrincipal3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "idProducto", "Nombre Comercial", "Principio activo", "N° de registro", "Registrante", "Clase de uso", "Dosis", "Unidad dosis"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbPrincipal3.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tbPrincipal3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbPrincipal3.setGridColor(new java.awt.Color(0, 153, 204));
        tbPrincipal3.setOpaque(false);
        tbPrincipal3.setRowHeight(20);
        tbPrincipal3.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbPrincipal3.getTableHeader().setReorderingAllowed(false);
        tbPrincipal3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPrincipal3MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbPrincipal3MousePressed(evt);
            }
        });
        tbPrincipal3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPrincipal3KeyReleased(evt);
            }
        });
        scPrincipal3.setViewportView(tbPrincipal3);

        btnNuevo1.setBackground(new java.awt.Color(14, 154, 153));
        btnNuevo1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnNuevo1.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/iconos40x40/IconoNuevo40.png"))); // NOI18N
        btnNuevo1.setText("Agregar");
        btnNuevo1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNuevo1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnNuevo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevo1ActionPerformed(evt);
            }
        });

        btnModificar1.setBackground(new java.awt.Color(14, 154, 153));
        btnModificar1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnModificar1.setForeground(new java.awt.Color(255, 255, 255));
        btnModificar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/iconos40x40/IconoModifcar40.png"))); // NOI18N
        btnModificar1.setText("Modificar");
        btnModificar1.setEnabled(false);
        btnModificar1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnModificar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificar1ActionPerformed(evt);
            }
        });

        btnEliminar2.setBackground(new java.awt.Color(14, 154, 153));
        btnEliminar2.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnEliminar2.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/iconos40x40/IconoEliminar40.png"))); // NOI18N
        btnEliminar2.setText("Quitar");
        btnEliminar2.setEnabled(false);
        btnEliminar2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEliminar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminar2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpTabla3Layout = new javax.swing.GroupLayout(jpTabla3);
        jpTabla3.setLayout(jpTabla3Layout);
        jpTabla3Layout.setHorizontalGroup(
            jpTabla3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTabla3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(scPrincipal3, javax.swing.GroupLayout.PREFERRED_SIZE, 689, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpTabla3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnModificar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNuevo1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );
        jpTabla3Layout.setVerticalGroup(
            jpTabla3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTabla3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpTabla3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpTabla3Layout.createSequentialGroup()
                        .addComponent(btnNuevo1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificar1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(btnEliminar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(scPrincipal3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        btnEliminar3.setBackground(new java.awt.Color(51, 51, 51));
        btnEliminar3.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnEliminar3.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/iconos40x40/IconoGuardar40.png"))); // NOI18N
        btnEliminar3.setText("GUARDAR");
        btnEliminar3.setEnabled(false);
        btnEliminar3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEliminar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminar3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(labelMetric2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jpTabla3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jpTabla2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGap(357, 357, 357)
                        .addComponent(btnEliminar3)))
                .addGap(0, 31, Short.MAX_VALUE))
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelMetric2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpTabla2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jpTabla3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEliminar3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout NuevoAplicacionLayout = new javax.swing.GroupLayout(NuevoAplicacion.getContentPane());
        NuevoAplicacion.getContentPane().setLayout(NuevoAplicacionLayout);
        NuevoAplicacionLayout.setHorizontalGroup(
            NuevoAplicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        NuevoAplicacionLayout.setVerticalGroup(
            NuevoAplicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        SelectProductos.setTitle("Agregar producto");
        SelectProductos.setModal(true);
        SelectProductos.setPreferredSize(new java.awt.Dimension(1060, 660));
        SelectProductos.setSize(new java.awt.Dimension(1060, 660));

        panel6.setPreferredSize(new java.awt.Dimension(1060, 620));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/iconos40x40/IconoBuscar40.png"))); // NOI18N
        jLabel12.setText("  BUSCAR ");

        txtBuscarAgregarProducto.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        txtBuscarAgregarProducto.setForeground(new java.awt.Color(0, 153, 153));
        txtBuscarAgregarProducto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtBuscarAgregarProducto.setCaretColor(new java.awt.Color(0, 204, 204));
        txtBuscarAgregarProducto.setDisabledTextColor(new java.awt.Color(0, 204, 204));
        txtBuscarAgregarProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarAgregarProductoKeyReleased(evt);
            }
        });

        lblBuscarCampoApoderado.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        lblBuscarCampoApoderado.setForeground(new java.awt.Color(255, 255, 255));
        lblBuscarCampoApoderado.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBuscarCampoApoderado.setText("Buscar por:");

        lbCantRegistrosProductosSelect.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lbCantRegistrosProductosSelect.setForeground(new java.awt.Color(153, 153, 0));
        lbCantRegistrosProductosSelect.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbCantRegistrosProductosSelect.setText("0 Registros encontrados");
        lbCantRegistrosProductosSelect.setPreferredSize(new java.awt.Dimension(57, 25));

        scAllProductosSelect.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tbAllProductosSelect.setAutoCreateRowSorter(true);
        tbAllProductosSelect.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tbAllProductosSelect.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        tbAllProductosSelect.setModel(new javax.swing.table.DefaultTableModel(
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
        tbAllProductosSelect.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tbAllProductosSelect.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbAllProductosSelect.setGridColor(new java.awt.Color(0, 153, 204));
        tbAllProductosSelect.setOpaque(false);
        tbAllProductosSelect.setRowHeight(20);
        tbAllProductosSelect.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbAllProductosSelect.getTableHeader().setReorderingAllowed(false);
        tbAllProductosSelect.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbAllProductosSelectMouseClicked(evt);
            }
        });
        scAllProductosSelect.setViewportView(tbAllProductosSelect);

        jButton1.setBackground(new java.awt.Color(0, 102, 0));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Añadir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jpSelectProductos.setBackground(new java.awt.Color(32, 39, 55));
        jpSelectProductos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Productos seleccionados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Dialog", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        jpSelectProductos.setForeground(new java.awt.Color(0, 0, 0));

        scSelectProductos.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tbProductosSelect.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tbProductosSelect.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        tbProductosSelect.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "idProducto", "Nombre Comercial", "Compuesto", "N° de registro", "Registrante", "Clase de uso", "Dosis", "Unidad de dosis"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbProductosSelect.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tbProductosSelect.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbProductosSelect.setGridColor(new java.awt.Color(0, 153, 204));
        tbProductosSelect.setOpaque(false);
        tbProductosSelect.setRowHeight(20);
        tbProductosSelect.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbProductosSelect.getTableHeader().setReorderingAllowed(false);
        tbProductosSelect.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbProductosSelectMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbProductosSelectMousePressed(evt);
            }
        });
        tbProductosSelect.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbProductosSelectKeyReleased(evt);
            }
        });
        scSelectProductos.setViewportView(tbProductosSelect);

        jButton2.setBackground(new java.awt.Color(0, 102, 0));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Agregar productos seleccionados");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnVaciarTabla.setBackground(new java.awt.Color(153, 0, 51));
        btnVaciarTabla.setForeground(new java.awt.Color(255, 255, 255));
        btnVaciarTabla.setText("Vaciar productos seleccionados");
        btnVaciarTabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVaciarTablaActionPerformed(evt);
            }
        });

        lblCantProductosSeleccionados.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblCantProductosSeleccionados.setForeground(new java.awt.Color(153, 153, 0));
        lblCantProductosSeleccionados.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCantProductosSeleccionados.setText("0 Productos seleccionados");
        lblCantProductosSeleccionados.setPreferredSize(new java.awt.Dimension(57, 25));

        btnNombresComerciales.setBackground(new java.awt.Color(0, 102, 102));
        btnNombresComerciales.setText("Nombres Comerciales");
        btnNombresComerciales.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        btnNombresComerciales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNombresComercialesActionPerformed(evt);
            }
        });

        btnCompuestos.setBackground(new java.awt.Color(0, 102, 102));
        btnCompuestos.setText("Compuestos");
        btnCompuestos.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        btnCompuestos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompuestosActionPerformed(evt);
            }
        });

        btnNumRegistros.setBackground(new java.awt.Color(0, 102, 102));
        btnNumRegistros.setText("N° de registros");
        btnNumRegistros.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        btnNumRegistros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumRegistrosActionPerformed(evt);
            }
        });

        btnRegistrantes.setBackground(new java.awt.Color(0, 102, 102));
        btnRegistrantes.setText("Registrantes");
        btnRegistrantes.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        btnRegistrantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrantesActionPerformed(evt);
            }
        });

        lblCantProductosSeleccionados1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblCantProductosSeleccionados1.setForeground(new java.awt.Color(255, 255, 255));
        lblCantProductosSeleccionados1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCantProductosSeleccionados1.setText("Copiar");
        lblCantProductosSeleccionados1.setPreferredSize(new java.awt.Dimension(57, 25));

        javax.swing.GroupLayout jpSelectProductosLayout = new javax.swing.GroupLayout(jpSelectProductos);
        jpSelectProductos.setLayout(jpSelectProductosLayout);
        jpSelectProductosLayout.setHorizontalGroup(
            jpSelectProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpSelectProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpSelectProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scSelectProductos)
                    .addGroup(jpSelectProductosLayout.createSequentialGroup()
                        .addComponent(lblCantProductosSeleccionados1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNombresComerciales, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCompuestos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNumRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRegistrantes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 125, Short.MAX_VALUE)
                        .addComponent(lblCantProductosSeleccionados, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpSelectProductosLayout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnVaciarTabla)))
                .addContainerGap())
        );
        jpSelectProductosLayout.setVerticalGroup(
            jpSelectProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpSelectProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scSelectProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpSelectProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jpSelectProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblCantProductosSeleccionados1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNombresComerciales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCompuestos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNumRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnRegistrantes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblCantProductosSeleccionados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(36, 36, 36)
                .addGroup(jpSelectProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVaciarTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel6Layout = new javax.swing.GroupLayout(panel6);
        panel6.setLayout(panel6Layout);
        panel6Layout.setHorizontalGroup(
            panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel6Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbCantRegistrosProductosSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel6Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscarAgregarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(179, 179, 179)
                        .addComponent(lblBuscarCampoApoderado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbCampoBuscarProductosSelect, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(scAllProductosSelect)
                    .addComponent(jpSelectProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        panel6Layout.setVerticalGroup(
            panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel6Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cbCampoBuscarProductosSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBuscarCampoApoderado, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBuscarAgregarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(scAllProductosSelect, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(lbCantRegistrosProductosSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpSelectProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(28, 28, 28))
        );

        jpSelectProductos.getAccessibleContext().setAccessibleName("Productosseleccionados");

        javax.swing.GroupLayout SelectProductosLayout = new javax.swing.GroupLayout(SelectProductos.getContentPane());
        SelectProductos.getContentPane().setLayout(SelectProductosLayout);
        SelectProductosLayout.setHorizontalGroup(
            SelectProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        SelectProductosLayout.setVerticalGroup(
            SelectProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel6, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ventana Aplicaciones");
        setBackground(new java.awt.Color(45, 62, 80));
        setModal(true);
        setSize(new java.awt.Dimension(952, 621));

        jpPrincipal.setBackground(new java.awt.Color(233, 255, 255));
        jpPrincipal.setPreferredSize(new java.awt.Dimension(1580, 478));

        panel1.setColorPrimario(new java.awt.Color(0, 140, 119));
        panel1.setColorSecundario(new java.awt.Color(233, 255, 255));

        labelMetric1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelMetric1.setText("APLICACIONES");
        labelMetric1.setDireccionDeSombra(110);
        labelMetric1.setFocusable(false);
        labelMetric1.setFont(new java.awt.Font("Cooper Black", 0, 28)); // NOI18N

        jpTabla.setBackground(new java.awt.Color(0, 140, 119));
        jpTabla.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        scPrincipal.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tbPrincipal.setAutoCreateRowSorter(true);
        tbPrincipal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tbPrincipal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbPrincipal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Fecha", "Cultivo", "Area aplicada", "Objetivo de aplicación", "Aplicador", "EPI", "Tipo de pico", "Volumen de agua", "Temperatura", "Humedad", "Viento", "Observación"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class
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

        javax.swing.GroupLayout jpTablaLayout = new javax.swing.GroupLayout(jpTabla);
        jpTabla.setLayout(jpTablaLayout);
        jpTablaLayout.setHorizontalGroup(
            jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTablaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scPrincipal)
                .addContainerGap())
        );
        jpTablaLayout.setVerticalGroup(
            jpTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTablaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jpTabla1.setBackground(new java.awt.Color(0, 140, 119));
        jpTabla1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Productos aplicados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Dialog", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        jpTabla1.setForeground(new java.awt.Color(0, 0, 0));

        scProductos.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tbProductos.setAutoCreateRowSorter(true);
        tbProductos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tbProductos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "idProducto", "Nombre Comercial", "Principio activo", "N° de registro", "Registrante", "Clase de uso", "Dosis", "Unidad de dosis"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbProductos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tbProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbProductos.setGridColor(new java.awt.Color(0, 153, 204));
        tbProductos.setOpaque(false);
        tbProductos.setRowHeight(20);
        tbProductos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbProductos.getTableHeader().setReorderingAllowed(false);
        tbProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbProductosMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbProductosMousePressed(evt);
            }
        });
        tbProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbProductosKeyReleased(evt);
            }
        });
        scProductos.setViewportView(tbProductos);

        javax.swing.GroupLayout jpTabla1Layout = new javax.swing.GroupLayout(jpTabla1);
        jpTabla1.setLayout(jpTabla1Layout);
        jpTabla1Layout.setHorizontalGroup(
            jpTabla1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTabla1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scProductos, javax.swing.GroupLayout.DEFAULT_SIZE, 884, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpTabla1Layout.setVerticalGroup(
            jpTabla1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTabla1Layout.createSequentialGroup()
                .addComponent(scProductos, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                .addContainerGap())
        );

        jpBotones.setBackground(new java.awt.Color(233, 255, 255));
        jpBotones.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jpBotones.setPreferredSize(new java.awt.Dimension(100, 50));

        btnNuevo.setBackground(new java.awt.Color(14, 154, 153));
        btnNuevo.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnNuevo.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/iconos40x40/IconoNuevo40.png"))); // NOI18N
        btnNuevo.setText("Nueva aplicación");
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
        btnModificar.setText("Modificar aplicación");
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
        btnEliminar.setText("Eliminar aplicación");
        btnEliminar.setEnabled(false);
        btnEliminar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnEliminar1.setBackground(new java.awt.Color(14, 154, 153));
        btnEliminar1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnEliminar1.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/iconos40x40/IconoBuscar40.png"))); // NOI18N
        btnEliminar1.setText("Generar planilla");
        btnEliminar1.setEnabled(false);
        btnEliminar1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEliminar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpBotonesLayout = new javax.swing.GroupLayout(jpBotones);
        jpBotones.setLayout(jpBotonesLayout);
        jpBotonesLayout.setHorizontalGroup(
            jpBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBotonesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModificar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminar1, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpBotonesLayout.setVerticalGroup(
            jpBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBotonesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo)
                    .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Emprendimiento:");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Bloque:");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Parcela:");

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("SubParcela:");

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Lote:");

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("SubLote:");

        cbEmprendimiento.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N

        cbParcela.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N

        cbLote.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N

        cbBloque.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N

        cbSubParcela.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N

        cbSubLote.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jpTabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(labelMetric1, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 757, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbEmprendimiento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbBloque, 0, 232, Short.MAX_VALUE))
                .addGap(52, 52, 52)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbParcela, 0, 252, Short.MAX_VALUE)
                    .addComponent(cbSubParcela, 0, 252, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbLote, 0, 252, Short.MAX_VALUE)
                    .addComponent(cbSubLote, 0, 252, Short.MAX_VALUE))
                .addGap(21, 21, 21))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jpTabla1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109))
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(178, 178, 178)
                .addComponent(jpBotones, javax.swing.GroupLayout.PREFERRED_SIZE, 742, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelMetric1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbEmprendimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbLote, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbBloque, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbSubParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbSubLote, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jpTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpTabla1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jpBotones, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpPrincipalLayout = new javax.swing.GroupLayout(jpPrincipal);
        jpPrincipal.setLayout(jpPrincipalLayout);
        jpPrincipalLayout.setHorizontalGroup(
            jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpPrincipalLayout.setVerticalGroup(
            jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 1126, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleName("Alumno");
        getAccessibleContext().setAccessibleDescription("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        NuevoAplicacion.setLocationRelativeTo(this);
        NuevoAplicacion.setVisible(true);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        ModoEdicion(true);
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        /*RegistroEliminar();
        Limpiar();
        ModoEdicion(false);

        ConsultaAllAlumno();*/
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void tbProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProductosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbProductosMouseClicked

    private void tbProductosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProductosMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbProductosMousePressed

    private void tbProductosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbProductosKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DELETE && tbProductosSelect.getSelectedRowCount() > 0) {
            JOptionPane.showMessageDialog(SelectProductos, "Has pulsado Delete");
        }
    }//GEN-LAST:event_tbProductosKeyReleased

    private void btnEliminar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminar1ActionPerformed

    private void tbPrincipal3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPrincipal3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPrincipal3MouseClicked

    private void tbPrincipal3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPrincipal3MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPrincipal3MousePressed

    private void tbPrincipal3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPrincipal3KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPrincipal3KeyReleased

    private void btnNuevo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevo1ActionPerformed
        SelectProductos.setLocationRelativeTo(NuevoAplicacion);
        SelectProductos.setVisible(true);
    }//GEN-LAST:event_btnNuevo1ActionPerformed

    private void btnModificar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModificar1ActionPerformed

    private void btnEliminar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminar2ActionPerformed

    private void btnEliminar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminar3ActionPerformed

    private void tbPrincipalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPrincipalKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_UP || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            ModoVistaPrevia();
        }
    }//GEN-LAST:event_tbPrincipalKeyReleased

    private void tbPrincipalMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPrincipalMousePressed
        if (tbPrincipal.isEnabled() == true) {
            btnModificar.setEnabled(true);
            btnEliminar.setEnabled(true);

            ModoVistaPrevia();
        }
    }//GEN-LAST:event_tbPrincipalMousePressed

    private void tbPrincipalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPrincipalMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPrincipalMouseClicked

    private void txtBuscarAgregarProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarAgregarProductoKeyReleased
        metodos.FiltroJTable(txtBuscarAgregarProducto.getText(), cbCampoBuscarProductosSelect.getSelectedIndex(), tbAllProductosSelect);

        if (tbAllProductosSelect.getRowCount() == 1) {
            lbCantRegistrosProductosSelect.setText(tbAllProductosSelect.getRowCount() + " Registro encontrado");
        } else {
            lbCantRegistrosProductosSelect.setText(tbAllProductosSelect.getRowCount() + " Registros encontrados");
        }
    }//GEN-LAST:event_txtBuscarAgregarProductoKeyReleased

    private void tbAllProductosSelectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbAllProductosSelectMouseClicked
        if (evt.getClickCount() == 2) {
            AnhadirProducto();
        }
    }//GEN-LAST:event_tbAllProductosSelectMouseClicked

    private void tbProductosSelectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProductosSelectMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbProductosSelectMouseClicked

    private void tbProductosSelectMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProductosSelectMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbProductosSelectMousePressed

    private void tbProductosSelectKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbProductosSelectKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tbProductosSelectKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        AnhadirProducto();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnVaciarTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVaciarTablaActionPerformed
        tableModelProductosSelect = (DefaultTableModel) tbProductosSelect.getModel();
        tableModelProductosSelect.setRowCount(0);
    }//GEN-LAST:event_btnVaciarTablaActionPerformed

    private void btnNumRegistrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumRegistrosActionPerformed
        if (tbProductosSelect.getRowCount() <= 0) { //Si la tabla esta vacia
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(SelectProductos, "No se seleccionó ningún producto");
            return;
        }

        String numRegistros = "";
        String numRegistroActual = "";
        for (int i = 0; i < tbProductosSelect.getRowCount(); i++) {
            numRegistroActual = tbProductosSelect.getValueAt(i, 3) + "";

            if (numRegistroActual.equals("")) {
                numRegistroActual = ".......";
            }
            if (numRegistroActual.contains("-B")) {
                numRegistroActual = numRegistroActual.replace("-B", "");
            }

            numRegistros = numRegistros + " / " + numRegistroActual;
        }
        numRegistros = numRegistros.replaceFirst(" / ", ""); //Saca el primer /

        copiarPortapapeles(numRegistros);

        System.out.println("Se copió los numeros de registros seleccionados al portapapeles: " + numRegistros);
    }//GEN-LAST:event_btnNumRegistrosActionPerformed

    private void btnCompuestosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompuestosActionPerformed
        if (tbProductosSelect.getRowCount() <= 0) { //Si la tabla esta vacia
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(SelectProductos, "No se seleccionó ningún producto");
            return;
        }

        String compuestos = "";
        String compuestoActual = "";

        if (compuestos.length() > 50) {
            compuestos = compuestos.substring(0, 47) + "...";
        }

        for (int i = 0; i < tbProductosSelect.getRowCount(); i++) {
            compuestoActual = tbProductosSelect.getValueAt(i, 2) + "";

            compuestos = compuestos + compuestoActual + "\n\n";
        }

        copiarPortapapeles(metodos.PrimerLetraPalabraMayus(compuestos));

        System.out.println("Se copió los compuestos seleccionados al portapapeles: " + compuestos);
    }//GEN-LAST:event_btnCompuestosActionPerformed

    private void btnRegistrantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrantesActionPerformed
        if (tbProductosSelect.getRowCount() <= 0) { //Si la tabla esta vacia
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(SelectProductos, "No se seleccionó ningún producto");
            return;
        }

        String registrantes = "";
        String registranteActual = "";
        for (int i = 0; i < tbProductosSelect.getRowCount(); i++) {
            registranteActual = tbProductosSelect.getValueAt(i, 4) + "";

            registrantes = registrantes + " / " + registranteActual;
        }
        registrantes = registrantes.replaceFirst(" / ", ""); //Saca el primer /

        copiarPortapapeles(registrantes);

        System.out.println("Se copió los registrantes seleccionados al portapapeles: " + registrantes);
    }//GEN-LAST:event_btnRegistrantesActionPerformed

    private void btnNombresComercialesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNombresComercialesActionPerformed
        if (tbProductosSelect.getRowCount() <= 0) { //Si la tabla esta vacia
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(SelectProductos, "No se seleccionó ningún producto");
            return;
        }

        String nombresComerciales = "";
        String nombreComercialActual = "";
        for (int i = 0; i < tbProductosSelect.getRowCount(); i++) {
            nombreComercialActual = tbProductosSelect.getValueAt(i, 1) + "";

            nombresComerciales = nombresComerciales + nombreComercialActual + "\n\n";
        }

        copiarPortapapeles(metodos.PrimerLetraPalabraMayus(nombresComerciales));

        System.out.println("Se copió los nombres comerciales seleccionados al portapapeles: " + nombresComerciales);
    }//GEN-LAST:event_btnNombresComercialesActionPerformed

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
            java.util.logging.Logger.getLogger(Aplicacion.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Aplicacion dialog = new Aplicacion(new javax.swing.JFrame(), true);
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

    public void copiarPortapapeles(String textoACopiar) {
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection ss = new StringSelection(textoACopiar);
        cb.setContents(ss, ss);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog NuevoAplicacion;
    private javax.swing.JDialog SelectProductos;
    private org.edisoncor.gui.button.ButtonColoredAction btnCompuestos;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminar1;
    private javax.swing.JButton btnEliminar2;
    private javax.swing.JButton btnEliminar3;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnModificar1;
    private org.edisoncor.gui.button.ButtonColoredAction btnNombresComerciales;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnNuevo1;
    private org.edisoncor.gui.button.ButtonColoredAction btnNumRegistros;
    private org.edisoncor.gui.button.ButtonColoredAction btnRegistrantes;
    private javax.swing.JButton btnVaciarTabla;
    private javax.swing.JComboBox<MetodosCombo> cbBloque;
    private javax.swing.JComboBox cbCampoBuscarProductosSelect;
    private javax.swing.JComboBox<MetodosCombo> cbCultivoNuevoApl;
    private javax.swing.JComboBox<MetodosCombo> cbCultivoNuevoApl1;
    private javax.swing.JComboBox<MetodosCombo> cbCultivoNuevoApl2;
    private javax.swing.JComboBox<MetodosCombo> cbCultivoNuevoApl3;
    private javax.swing.JComboBox<MetodosCombo> cbEmprendimiento;
    private javax.swing.JComboBox<MetodosCombo> cbLote;
    private javax.swing.JComboBox<MetodosCombo> cbParcela;
    private javax.swing.JComboBox<MetodosCombo> cbSubLote;
    private javax.swing.JComboBox<MetodosCombo> cbSubParcela;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JPanel jpBotones;
    private javax.swing.JPanel jpPrincipal;
    private javax.swing.JPanel jpSelectProductos;
    private javax.swing.JPanel jpTabla;
    private javax.swing.JPanel jpTabla1;
    private javax.swing.JPanel jpTabla2;
    private javax.swing.JPanel jpTabla3;
    private org.edisoncor.gui.label.LabelMetric labelMetric1;
    private org.edisoncor.gui.label.LabelMetric labelMetric2;
    private javax.swing.JLabel lbCantRegistrosProductosSelect;
    private javax.swing.JLabel lblBuscarCampoApoderado;
    private javax.swing.JLabel lblCantProductosSeleccionados;
    private javax.swing.JLabel lblCantProductosSeleccionados1;
    private org.edisoncor.gui.panel.Panel panel1;
    private org.edisoncor.gui.panel.Panel panel2;
    private org.edisoncor.gui.panel.Panel panel6;
    private javax.swing.JScrollPane scAllProductosSelect;
    private javax.swing.JScrollPane scPrincipal;
    private javax.swing.JScrollPane scPrincipal3;
    private javax.swing.JScrollPane scProductos;
    private javax.swing.JScrollPane scSelectProductos;
    private javax.swing.JTable tbAllProductosSelect;
    private javax.swing.JTable tbPrincipal;
    private javax.swing.JTable tbPrincipal3;
    private javax.swing.JTable tbProductos;
    private javax.swing.JTable tbProductosSelect;
    private javax.swing.JTextField txtBuscarAgregarProducto;
    // End of variables declaration//GEN-END:variables
}
