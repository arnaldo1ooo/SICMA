/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms.aplicaciones;

import conexion.Conexion;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
    private DefaultTableModel modeltableAlumnos;
    private Color colorVerde = new Color(6, 147, 27);
    private Color colorRojo = new Color(206, 16, 45);

    public Aplicacion(java.awt.Frame parent, Boolean modal) {
        super(parent, modal);
        initComponents();

        metodos.AnchuraColumna(tbPrincipal);

        //Poner fecha actual
        //dcFechaInscripcion.setDate(new Date());
        //LLamar metodos
        //ConsultaAllAlumno(); //Trae todos los registros
        TablaAllApoderado();
        CargarComboBoxes();

        //Permiso Roles de usuario
        //Permiso Roles de usuario
        String permisos = metodos.PermisoRol(codUsuario, "ALUMNO");
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
       // metodoscombo.CargarComboConsulta(cbApoderado, "SELECT apo_codigo, CONCAT(apo_nombre,' ', apo_apellido) AS nomape FROM apoderado ORDER BY apo_nombre", -1);
    }

    private void TablaAllApoderado() {//Realiza la consulta de los productos que tenemos en la base de datos
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

//--------------------------iniComponent()No tocar----------------------------//
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        NuevoAplicacion = new javax.swing.JDialog();
        jpPrincipal1 = new javax.swing.JPanel();
        panel2 = new org.edisoncor.gui.panel.Panel();
        labelMetric2 = new org.edisoncor.gui.label.LabelMetric();
        jLabel2 = new javax.swing.JLabel();
        jpTabla2 = new javax.swing.JPanel();
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
        scPrincipal1 = new javax.swing.JScrollPane();
        tbPrincipal1 = new javax.swing.JTable(){
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

        NuevoAplicacion.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        NuevoAplicacion.setTitle("Ventana Aplicaciones");
        NuevoAplicacion.setBackground(new java.awt.Color(45, 62, 80));
        NuevoAplicacion.setModal(true);
        NuevoAplicacion.setResizable(false);
        NuevoAplicacion.setSize(new java.awt.Dimension(952, 621));

        jpPrincipal1.setBackground(new java.awt.Color(233, 255, 255));
        jpPrincipal1.setPreferredSize(new java.awt.Dimension(1580, 478));

        panel2.setColorPrimario(new java.awt.Color(0, 153, 153));
        panel2.setColorSecundario(new java.awt.Color(233, 255, 255));

        labelMetric2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelMetric2.setText("NUEVA APLICACIÓN");
        labelMetric2.setDireccionDeSombra(110);
        labelMetric2.setFocusable(false);
        labelMetric2.setFont(new java.awt.Font("Cooper Black", 0, 28)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nueva aplicación en XXXXXX");

        jpTabla2.setBackground(new java.awt.Color(233, 255, 255));
        jpTabla2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jpTabla2Layout = new javax.swing.GroupLayout(jpTabla2);
        jpTabla2.setLayout(jpTabla2Layout);
        jpTabla2Layout.setHorizontalGroup(
            jpTabla2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jpTabla2Layout.setVerticalGroup(
            jpTabla2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 204, Short.MAX_VALUE)
        );

        jpTabla3.setBackground(new java.awt.Color(233, 255, 255));
        jpTabla3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Productos aplicados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Dialog", 1, 14))); // NOI18N

        scPrincipal3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tbPrincipal3.setAutoCreateRowSorter(true);
        tbPrincipal3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tbPrincipal3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbPrincipal3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "idProducto", "Nombre Comercial", "N° de registro", "Registrante", "Principio activo", "Dosis", "Unidad dosis"
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
                .addContainerGap()
                .addComponent(scPrincipal3, javax.swing.GroupLayout.PREFERRED_SIZE, 689, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jpTabla3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnModificar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNuevo1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 6, Short.MAX_VALUE))
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

        btnEliminar3.setBackground(new java.awt.Color(14, 154, 153));
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
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jpTabla3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jpTabla2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(labelMetric2, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(504, 504, 504))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                                .addComponent(btnEliminar3)
                                .addGap(348, 348, 348))))))
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
                .addGap(18, 18, 18)
                .addComponent(jpTabla3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEliminar3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpPrincipal1Layout = new javax.swing.GroupLayout(jpPrincipal1);
        jpPrincipal1.setLayout(jpPrincipal1Layout);
        jpPrincipal1Layout.setHorizontalGroup(
            jpPrincipal1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpPrincipal1Layout.setVerticalGroup(
            jpPrincipal1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout NuevoAplicacionLayout = new javax.swing.GroupLayout(NuevoAplicacion.getContentPane());
        NuevoAplicacion.getContentPane().setLayout(NuevoAplicacionLayout);
        NuevoAplicacionLayout.setHorizontalGroup(
            NuevoAplicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpPrincipal1, javax.swing.GroupLayout.DEFAULT_SIZE, 1078, Short.MAX_VALUE)
        );
        NuevoAplicacionLayout.setVerticalGroup(
            NuevoAplicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpPrincipal1, javax.swing.GroupLayout.PREFERRED_SIZE, 593, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ventana Aplicaciones");
        setBackground(new java.awt.Color(45, 62, 80));
        setModal(true);
        setResizable(false);
        setSize(new java.awt.Dimension(952, 621));

        jpPrincipal.setBackground(new java.awt.Color(233, 255, 255));
        jpPrincipal.setPreferredSize(new java.awt.Dimension(1580, 478));

        panel1.setColorPrimario(new java.awt.Color(0, 153, 153));
        panel1.setColorSecundario(new java.awt.Color(233, 255, 255));

        labelMetric1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelMetric1.setText("APLICACIONES");
        labelMetric1.setDireccionDeSombra(110);
        labelMetric1.setFocusable(false);
        labelMetric1.setFont(new java.awt.Font("Cooper Black", 0, 28)); // NOI18N

        jpTabla.setBackground(new java.awt.Color(233, 255, 255));
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

        jpTabla1.setBackground(new java.awt.Color(233, 255, 255));
        jpTabla1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Productos aplicados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Dialog", 1, 14))); // NOI18N

        scPrincipal1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tbPrincipal1.setAutoCreateRowSorter(true);
        tbPrincipal1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tbPrincipal1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbPrincipal1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre Comercial", "Principio activo", "Dosis", "Unidad dosis"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbPrincipal1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tbPrincipal1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbPrincipal1.setGridColor(new java.awt.Color(0, 153, 204));
        tbPrincipal1.setOpaque(false);
        tbPrincipal1.setRowHeight(20);
        tbPrincipal1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbPrincipal1.getTableHeader().setReorderingAllowed(false);
        tbPrincipal1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPrincipal1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbPrincipal1MousePressed(evt);
            }
        });
        tbPrincipal1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPrincipal1KeyReleased(evt);
            }
        });
        scPrincipal1.setViewportView(tbPrincipal1);

        javax.swing.GroupLayout jpTabla1Layout = new javax.swing.GroupLayout(jpTabla1);
        jpTabla1.setLayout(jpTabla1Layout);
        jpTabla1Layout.setHorizontalGroup(
            jpTabla1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTabla1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scPrincipal1, javax.swing.GroupLayout.DEFAULT_SIZE, 884, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpTabla1Layout.setVerticalGroup(
            jpTabla1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTabla1Layout.createSequentialGroup()
                .addComponent(scPrincipal1, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
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
        btnEliminar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/iconos40x40/IconoEliminar.png"))); // NOI18N
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEliminar1, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpBotonesLayout.setVerticalGroup(
            jpBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBotonesLayout.createSequentialGroup()
                .addGroup(jpBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpBotonesLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jpBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNuevo)
                            .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jpBotonesLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(btnEliminar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
        jLabel6.setText("Subparcela:");

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Parcela:");

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Subparcela:");

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
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(labelMetric1, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(188, 188, 188)
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(187, 187, 187)
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addGap(0, 79, Short.MAX_VALUE)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                        .addComponent(jpBotones, javax.swing.GroupLayout.PREFERRED_SIZE, 722, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(175, 175, 175))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                        .addComponent(jpTabla1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(93, 93, 93))))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelMetric1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)))
                .addGap(18, 18, 18)
                .addComponent(jpTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpTabla1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jpBotones, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpPrincipalLayout = new javax.swing.GroupLayout(jpPrincipal);
        jpPrincipal.setLayout(jpPrincipalLayout);
        jpPrincipalLayout.setHorizontalGroup(
            jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpPrincipalLayout.setVerticalGroup(
            jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 1078, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 639, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleName("Alumno");
        getAccessibleContext().setAccessibleDescription("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        Limpiar();
        ModoEdicion(true);
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

    private void tbPrincipal1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPrincipal1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPrincipal1MouseClicked

    private void tbPrincipal1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPrincipal1MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPrincipal1MousePressed

    private void tbPrincipal1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPrincipal1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPrincipal1KeyReleased

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
        // TODO add your handling code here:
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog NuevoAplicacion;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminar1;
    private javax.swing.JButton btnEliminar2;
    private javax.swing.JButton btnEliminar3;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnModificar1;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnNuevo1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jpBotones;
    private javax.swing.JPanel jpPrincipal;
    private javax.swing.JPanel jpPrincipal1;
    private javax.swing.JPanel jpTabla;
    private javax.swing.JPanel jpTabla1;
    private javax.swing.JPanel jpTabla2;
    private javax.swing.JPanel jpTabla3;
    private org.edisoncor.gui.label.LabelMetric labelMetric1;
    private org.edisoncor.gui.label.LabelMetric labelMetric2;
    private org.edisoncor.gui.panel.Panel panel1;
    private org.edisoncor.gui.panel.Panel panel2;
    private javax.swing.JScrollPane scPrincipal;
    private javax.swing.JScrollPane scPrincipal1;
    private javax.swing.JScrollPane scPrincipal3;
    private javax.swing.JTable tbPrincipal;
    private javax.swing.JTable tbPrincipal1;
    private javax.swing.JTable tbPrincipal3;
    // End of variables declaration//GEN-END:variables
}
