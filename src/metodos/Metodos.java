/*
 * 
 * Carga una consulta realizada a un combobox
 * 
 */
package metodos;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import conexion.Conexion;
import forms.producto.ABMProductoViejo;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;

/**
 *
 * @author Lic. Arnaldo Cantero
 */
public class Metodos {

    public int CantRegistros = 0;
    private Conexion con = new Conexion();

    public Conexion ObtenerRSSentencia(String sentencia) { //con.Desconectar luego de usar el metodo

        try {
            con.ConectarBasedeDatos();
            con=con.ObtenerRSSentencia(sentencia);

            int cantreg = 0;
            while (con.getResultSet().next() && cantreg < 2) { //Revisamos cuantos registro trajo la consulta
                cantreg++;
            }

            switch (cantreg) {
                case 0:
                    System.out.println("ObtenerRSSentencia no trajo ningun resultado");
                    break;
                case 1:
                    System.out.println("ObtenerRSSentencia trajo un resultado");
                    con.getResultSet().beforeFirst(); //Ponemos antes del primer registro en el puntero
                    break;
                case 2:
                    System.out.println("ObtenerRSSentencia trajo mas de un resultado");
                    con.getResultSet().beforeFirst(); //Ponemos antes del primer registro en el puntero
                    break;
                default:
                //aca se escribe lo que si o si se ejecuta
            }

        } catch (SQLException e) {
            System.out.println("Error al EjecutarSentencia ObtenerRSSentencia, sentencia: " + sentencia + "ERROR " + e);
        }
        return con;
    }

    public void AnchuraColumna(JTable LaTabla) {
        LaTabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); //Desactiva el autoresize
        TableColumnModel ModeloColumna = LaTabla.getColumnModel();
        int anchoacumulado = 0;
        int anchoadicional = 21;
        int cantidadcolumns = LaTabla.getColumnCount();
        int cantidadfilas = LaTabla.getRowCount();
        String nomheader; //Header = Cabecera
        TableColumn columnactual;
        Component componente;
        TableCellRenderer renderer;
        int anchoheaderenpixel;

        //Obtener tamano de String en pixeles
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB); //Elegir el tipo de fuente que usa
        Graphics2D graphics2d = img.createGraphics();
        Font font = new Font("Tahoma", Font.PLAIN, 14); //Poner la fuente tipo y tamano que se usa en la tabla
        FontMetrics fontmetrics = graphics2d.getFontMetrics(font);
        //System.out.println(fm.stringWidth("Este es un ejemplo"));
        graphics2d.dispose();

        for (int numdecolumna = 0; numdecolumna < cantidadcolumns; numdecolumna++) {
            int anchomax = 50; // Min width 
            columnactual = ModeloColumna.getColumn(numdecolumna);
            for (int fila = 0; fila < cantidadfilas; fila++) {
                renderer = LaTabla.getCellRenderer(fila, numdecolumna);
                componente = LaTabla.prepareRenderer(renderer, fila, numdecolumna);
                nomheader = columnactual.getHeaderValue().toString(); //Header es cabecera de la columna
                anchoheaderenpixel = fontmetrics.stringWidth(nomheader);
                anchomax = Math.max(componente.getPreferredSize().width + anchoadicional, anchomax);
                if (anchomax <= anchoheaderenpixel || cantidadfilas == 0) { //Si el ancho del registtro mas largo de la columna es menor a la cabecera 
                    anchomax = anchoheaderenpixel;
                }
            }
            if (cantidadfilas == 0) { //Si no hay ningun registro
                nomheader = columnactual.getHeaderValue().toString();
                anchoheaderenpixel = fontmetrics.stringWidth(nomheader);
                anchomax = anchoheaderenpixel + anchoadicional;
            }
            if (numdecolumna < (cantidadcolumns - 1)) { //Si no es la ultima columna
                columnactual.setPreferredWidth(anchomax); //Asigna a la columna el ancho del registro mas largo de la columna 
                anchoacumulado = anchoacumulado + anchomax; //Acumula el ancho de las columnas excepto el ultimo
            } else { //Ultima columna
                int anchototal = (int) LaTabla.getParent().getSize().getWidth(); //Tamano total del scroll que contiene a la tabla
                if ((anchoacumulado + anchomax) <= anchototal) {//Si la suma de la anchura de todas las columnas es menor o igual al ancho del scroll
                    int resta = anchototal - anchoacumulado; //Resta entre el ancho total del scroll y el ancho sumado de las columnas anteriores
                    columnactual.setPreferredWidth(resta);
                } else { //Si es mayor asigna el ancho del registro mas largo de la columna
                    columnactual.setPreferredWidth(anchomax);
                }
            }
        }
    }

    public void CambiarColorAlternadoTabla(JTable LaTabla, final Color colorback1, final Color colorback2) {
        LaTabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? colorback1 : colorback2);
                return c;
            }
        }
        );
    }

    public void ConsultaFiltroTablaBD(JTable LaTabla, String titlesJtabla[], String campoconsulta[], String nombresp, String filtro, JComboBox cbCampoBuscar) {
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
            sentencia = "CALL " + nombresp + " ('" + todoscamposconsulta + "', '" + filtro + "');";
        } else {
            sentencia = "CALL " + nombresp + " ('" + campoconsulta[cbCampoBuscar.getSelectedIndex()] + "', '" + filtro + "');";
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
            CantRegistros = 0;
            while (rs.next()) {
                for (int j = 0; j < numColumns; j++) {
                    registro[j] = (rs.getString(j + 1));
                }
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
    }

    public void FiltroDeCaracteres(java.awt.event.KeyEvent evt) {
        // Verificar si la tecla pulsada no es '
        char caracter = evt.getKeyChar();

        if (caracter == "'".charAt(0) || caracter == "\\".charAt(0)) {
            evt.consume(); // ignorar el evento de teclado
        }
    }

    public int ObtenerIdUltimoRegistro(String campocodigo, String tabla) {
        int idultimoregistro = -1;
        try {
            Conexion con = this.ObtenerRSSentencia("SELECT MAX(" + campocodigo + ") AS idultimoregistro FROM " + tabla);

            while (con.getResultSet().next()) {
                idultimoregistro = con.getResultSet().getInt("idultimoregistro");
            }
            con.DesconectarBasedeDatos();
        } catch (SQLException ex) {
            Logger.getLogger(ABMProductoViejo.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No se pudo obtener el idultimoproducto: " + idultimoregistro);
        }
        return idultimoregistro;
    }

    public void centrarventanaJInternalFrame(JInternalFrame LaVentana) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - LaVentana.getWidth()) / 2);
        int y = 0; //(int) ((dimension.getHeight() - LaVentana.getHeight()) / 2);
        LaVentana.setLocation(x, y);
    }

    public void centrarventanaJDialog(JDialog LaVentana) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - LaVentana.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - LaVentana.getHeight()) / 2);
        LaVentana.setLocation(x, y);
    }
}
