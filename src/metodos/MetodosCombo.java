/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodos;

import conexion.Conexion;
import java.awt.Color;
import java.awt.Graphics;
import java.sql.SQLException;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Lic. Arnaldo Cantero
 */
public class MetodosCombo {

    private int id;
    private String descripcion;

    public MetodosCombo() {
    }

    public MetodosCombo(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return this.descripcion;
    }

    public void setSelectedNombreItem(JComboBox ElCombo, String nombreitem) {
        MetodosCombo item;
        for (int i = 0; i < ElCombo.getItemCount(); i++) {
            item = (MetodosCombo) ElCombo.getItemAt(i);
            if (item.getDescripcion().equalsIgnoreCase(nombreitem)) {
                ElCombo.setSelectedIndex(i);
                break;
            }
        }
    }

    public void CargarComboBox(JComboBox ElCombo, String sentencia) {
        //Cambiar color de texto del combo cuando esta disabled
        ElCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public void paint(Graphics g) {
                setForeground(Color.BLACK);
                super.paint(g);
            }
        });
        
        ElCombo.removeAllItems(); //Vaciamos el combo
        try {
            AutoCompleteDecorator.decorate(ElCombo);
            Conexion con = new Conexion();
            con.ConectarBasedeDatos();
            System.out.println("Cargar combo (" + ElCombo.getName() + "): " + sentencia);
            con=con.ObtenerRSSentencia(sentencia);

            while (con.getResultSet().next()) {
                ElCombo.addItem(new MetodosCombo(
                        con.getResultSet().getInt(1),
                        con.getResultSet().getString(2)));
            }
            ElCombo.setSelectedIndex(-1);
            ElCombo.setMaximumRowCount(ElCombo.getModel().getSize()); //Hace que se despliegue en toda la pantalla vertical el combo
            con.DesconectarBasedeDatos();

            AnadirScrollHorizontal(ElCombo);
        } catch (NumberFormatException | SQLException e) {
            System.out.println("Error al cargar combo " + e);
        }
    }

    public int ObtenerIdComboBox(JComboBox<MetodosCombo> ElCombo) {
        int id = -1;
        try {
            id = ElCombo.getItemAt(ElCombo.getSelectedIndex()).getId();
        } catch (Exception e) {
            System.out.println("ObtenerIdCombo: No se selecciono ningun item en el combo " + e);
        }
        return id;
    }

    private void AnadirScrollHorizontal(JComboBox box) {
        if (box.getItemCount() == 0) {
            return;
        }
        Object comp = box.getUI().getAccessibleChild(box, 0);
        if (!(comp instanceof JPopupMenu)) {
            return;
        }
        JPopupMenu popup = (JPopupMenu) comp;
        JScrollPane scrollPane = (JScrollPane) popup.getComponent(0);
        scrollPane.setHorizontalScrollBar(new JScrollBar(JScrollBar.HORIZONTAL));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }
}
