/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms.producto.dosis;

import forms.producto.ABMProductoViejo;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import metodos.MetodosCombo;

/**
 *
 * @author Arnaldo Cantero
 */
public class AMDosis extends JDialog {

    private ABMProductoViejo abmproducto; //Para que tenga relacion con su form padre

    public AMDosis(ABMProductoViejo abmproducto, java.awt.Dialog parent, Boolean modal) {
        super(parent,modal);
        this.abmproducto = abmproducto;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpPrincipal = new javax.swing.JPanel();
        jpBanner = new javax.swing.JPanel();
        lbBanner = new javax.swing.JLabel();
        jpBotones = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtDosisMin = new javax.swing.JTextField();
        lbDosisMinEstado = new javax.swing.JLabel();
        lbDosisMaxEstado = new javax.swing.JLabel();
        txtDosisMax = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cbCultivo = new javax.swing.JComboBox<>();
        btnCultivo = new javax.swing.JButton();
        lbDosisMinEstadoKgLt = new javax.swing.JLabel();
        lbDosisMinKgLt = new javax.swing.JLabel();
        lbDosisMaxKgLt = new javax.swing.JLabel();
        lbDosisMaxEstadoKgLt = new javax.swing.JLabel();
        jpBotones2 = new javax.swing.JPanel();
        btnOk = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();

        setTitle("Ventana nueva dosis recomendada");
        setBackground(new java.awt.Color(45, 62, 80));
        setModal(true);
        setResizable(false);

        jpPrincipal.setBackground(new java.awt.Color(45, 62, 80));
        jpPrincipal.setPreferredSize(new java.awt.Dimension(1580, 478));

        jpBanner.setPreferredSize(new java.awt.Dimension(1000, 82));

        lbBanner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/forms/banners/bannertabla.jpg"))); // NOI18N
        lbBanner.setMaximumSize(new java.awt.Dimension(1100, 52));
        lbBanner.setMinimumSize(new java.awt.Dimension(1100, 52));
        lbBanner.setPreferredSize(new java.awt.Dimension(1100, 52));

        javax.swing.GroupLayout jpBannerLayout = new javax.swing.GroupLayout(jpBanner);
        jpBanner.setLayout(jpBannerLayout);
        jpBannerLayout.setHorizontalGroup(
            jpBannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbBanner, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jpBannerLayout.setVerticalGroup(
            jpBannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBannerLayout.createSequentialGroup()
                .addComponent(lbBanner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jpBotones.setBackground(new java.awt.Color(45, 62, 80));
        jpBotones.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jpBotones.setPreferredSize(new java.awt.Dimension(100, 50));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Código:");

        txtCodigo.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtCodigo.setEnabled(false);
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoKeyTyped(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Dosis mínima*:");

        txtDosisMin.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtDosisMin.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDosisMin.setText("0");
        txtDosisMin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDosisMinKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDosisMinKeyTyped(evt);
            }
        });

        lbDosisMinEstado.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        lbDosisMinEstado.setForeground(new java.awt.Color(255, 255, 255));
        lbDosisMinEstado.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbDosisMinEstado.setText("esta/Ha");

        lbDosisMaxEstado.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        lbDosisMaxEstado.setForeground(new java.awt.Color(255, 255, 255));
        lbDosisMaxEstado.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbDosisMaxEstado.setText("esta/Ha");

        txtDosisMax.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtDosisMax.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDosisMax.setText("0");
        txtDosisMax.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDosisMaxKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDosisMaxKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Dosis máxima*:");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Cultivo*:");

        cbCultivo.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N

        btnCultivo.setText("...");

        lbDosisMinEstadoKgLt.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        lbDosisMinEstadoKgLt.setForeground(new java.awt.Color(255, 255, 255));
        lbDosisMinEstadoKgLt.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbDosisMinEstadoKgLt.setText("esta/Ha");

        lbDosisMinKgLt.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        lbDosisMinKgLt.setForeground(new java.awt.Color(255, 255, 255));
        lbDosisMinKgLt.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbDosisMinKgLt.setText("0,0");

        lbDosisMaxKgLt.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        lbDosisMaxKgLt.setForeground(new java.awt.Color(255, 255, 255));
        lbDosisMaxKgLt.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbDosisMaxKgLt.setText("0,0");

        lbDosisMaxEstadoKgLt.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        lbDosisMaxEstadoKgLt.setForeground(new java.awt.Color(255, 255, 255));
        lbDosisMaxEstadoKgLt.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbDosisMaxEstadoKgLt.setText("esta/Ha");

        javax.swing.GroupLayout jpBotonesLayout = new javax.swing.GroupLayout(jpBotones);
        jpBotones.setLayout(jpBotonesLayout);
        jpBotonesLayout.setHorizontalGroup(
            jpBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBotonesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(4, 4, 4)
                .addGroup(jpBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpBotonesLayout.createSequentialGroup()
                        .addComponent(cbCultivo, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(btnCultivo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpBotonesLayout.createSequentialGroup()
                        .addGroup(jpBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtDosisMax, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                            .addComponent(txtDosisMin, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(4, 4, 4)
                        .addGroup(jpBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbDosisMinEstado)
                            .addComponent(lbDosisMaxEstado))))
                .addGap(18, 18, 18)
                .addGroup(jpBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbDosisMinKgLt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbDosisMaxKgLt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(4, 4, 4)
                .addGroup(jpBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbDosisMinEstadoKgLt)
                    .addComponent(lbDosisMaxEstadoKgLt))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        jpBotonesLayout.setVerticalGroup(
            jpBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBotonesLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jpBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jpBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnCultivo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbCultivo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lbDosisMinEstadoKgLt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbDosisMinEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDosisMin, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbDosisMinKgLt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lbDosisMaxEstadoKgLt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbDosisMaxKgLt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbDosisMaxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDosisMax, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11))
        );

        jpBotones2.setBackground(new java.awt.Color(45, 62, 80));
        jpBotones2.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        btnOk.setBackground(new java.awt.Color(0, 153, 255));
        btnOk.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        btnOk.setForeground(new java.awt.Color(255, 255, 255));
        btnOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Iconos20x20/IconoOk.png"))); // NOI18N
        btnOk.setText("OK");
        btnOk.setToolTipText("Inserta el nuevo registro");
        btnOk.setPreferredSize(new java.awt.Dimension(128, 36));
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });
        btnOk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnOkKeyPressed(evt);
            }
        });

        btnVolver.setBackground(new java.awt.Color(255, 0, 51));
        btnVolver.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        btnVolver.setForeground(new java.awt.Color(255, 255, 255));
        btnVolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Iconos20x20/iconoVolver.png"))); // NOI18N
        btnVolver.setText("Volver");
        btnVolver.setToolTipText("Cancela la acción");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpBotones2Layout = new javax.swing.GroupLayout(jpBotones2);
        jpBotones2.setLayout(jpBotones2Layout);
        jpBotones2Layout.setHorizontalGroup(
            jpBotones2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBotones2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnVolver)
                .addContainerGap())
        );
        jpBotones2Layout.setVerticalGroup(
            jpBotones2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBotones2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpBotones2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jpPrincipalLayout = new javax.swing.GroupLayout(jpPrincipal);
        jpPrincipal.setLayout(jpPrincipalLayout);
        jpPrincipalLayout.setHorizontalGroup(
            jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpBanner, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
            .addGroup(jpPrincipalLayout.createSequentialGroup()
                .addGroup(jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpPrincipalLayout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addComponent(jpBotones2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpPrincipalLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jpBotones, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpPrincipalLayout.setVerticalGroup(
            jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPrincipalLayout.createSequentialGroup()
                .addComponent(jpBanner, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpBotones, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpBotones2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jpBanner.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleName("Inventario");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyReleased
        //Limitar cantidad de caracteres
        int longitud = txtCodigo.getText().length();
        if (longitud > 40) {
            txtCodigo.setText(txtCodigo.getText().substring(0, 41));
        }
    }//GEN-LAST:event_txtCodigoKeyReleased

    private void txtCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyTyped
        // Verificar si la tecla pulsada no es un digito
        char caracter = evt.getKeyChar();
        if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
            evt.consume(); // ignorar el evento de teclado
        }
    }//GEN-LAST:event_txtCodigoKeyTyped

    private void txtDosisMinKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDosisMinKeyReleased
        ConversionDosisMin();
    }//GEN-LAST:event_txtDosisMinKeyReleased

    public void ConversionDosisMin() {
        if (txtDosisMin.getText().isEmpty() == false) {
            DecimalFormat df = new DecimalFormat("#.###");
            if (lbDosisMinEstado.getText().equals("ml/Ha")) {
                lbDosisMinKgLt.setText(df.format(Double.parseDouble(Double.parseDouble(txtDosisMin.getText().replace(',', '.')) / 1000 + ""))); //Pasar de militro a litro
                lbDosisMinEstadoKgLt.setText("lt/Ha");
            } else {
                if (lbDosisMinEstado.getText().equals("gr/Ha")) {
                    lbDosisMinKgLt.setText(df.format(Double.parseDouble(Double.parseDouble(txtDosisMin.getText().replace(',', '.')) / 1000 + ""))); //Pasar de militro a litro
                    lbDosisMinEstadoKgLt.setText("kg/Ha");
                } else {
                    if (lbDosisMinEstado.getText().equals("gPh3/m3")) {
                        lbDosisMinKgLt.setText(df.format(Double.parseDouble(Double.parseDouble(txtDosisMin.getText().replace(',', '.')) * 10000 + ""))); //Pasar de militro a litro
                        lbDosisMinEstadoKgLt.setText("gPh3/cm3/Ha");
                    }
                }
            }
        } else {
            lbDosisMinKgLt.setText("0"); //Pasar de militro a litro
        }
    }

    public void ConversionDosisMax() {
        if (txtDosisMax.getText().isEmpty() == false) {
            DecimalFormat df = new DecimalFormat("#.###");
            if (lbDosisMaxEstado.getText().equals("ml/Ha")) {
                lbDosisMaxKgLt.setText(df.format(Double.parseDouble(Double.parseDouble(txtDosisMax.getText().replace(',', '.')) / 1000 + ""))); //Pasar de militro a litro
                lbDosisMaxEstadoKgLt.setText("lt/Ha");
            } else {
                if (lbDosisMaxEstado.getText().equals("gr/Ha")) {
                    lbDosisMaxKgLt.setText(df.format(Double.parseDouble(Double.parseDouble(txtDosisMax.getText().replace(',', '.')) / 1000 + ""))); //Pasar de militro a litro
                    lbDosisMaxEstadoKgLt.setText("kg/Ha");
                } else {
                    if (lbDosisMaxEstado.getText().equals("gPh3/m3")) {
                        lbDosisMaxKgLt.setText(df.format(Double.parseDouble(Double.parseDouble(txtDosisMax.getText().replace(',', '.')) * 10000 + ""))); //Pasar de militro a litro
                        lbDosisMaxEstadoKgLt.setText("gPh3/cm3/Ha");
                    }
                }
            }
            if (lbDosisMaxEstado.getText().isEmpty()) {
                lbDosisMaxKgLt.setText("0"); //Pasar de militro a litro
            }
        } else {
            lbDosisMaxKgLt.setText("0"); //Pasar de militro a litro
        }
    }

    private void txtDosisMinKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDosisMinKeyTyped
        // Verificar si la tecla pulsada no es un digito
        char caracter = evt.getKeyChar();
        if ((((caracter < '0') || (caracter > '9'))
                && (caracter != KeyEvent.VK_BACK_SPACE)
                && (caracter != ',' || txtDosisMin.getText().contains(",")))) {
            evt.consume(); // ignorar el evento de teclado
        }

        //Limitar cantidad de caracteres
        int longitud = txtDosisMin.getText().length();
        if (longitud > 7) {
            txtDosisMin.setText(txtDosisMin.getText().substring(0, 8));
        }
    }//GEN-LAST:event_txtDosisMinKeyTyped

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed

        if (cbCultivo.getSelectedIndex() != -1 && !txtDosisMin.getText().isEmpty() && !txtDosisMax.getText().isEmpty()) {
            if (Double.parseDouble(txtDosisMin.getText().replace(",", ".")) <= Double.parseDouble(txtDosisMax.getText().replace(",", "."))) {
                if (txtCodigo.getText().equals("")) {//Si es nuevo
                    RegistroNuevo();
                } else { //Si es modificar
                    RegistroModificar();
                }
            } else {
                JOptionPane.showMessageDialog(null, "La dosis mínima no puede ser mayor a la dosis máxima", "Error", JOptionPane.ERROR_MESSAGE);
                txtDosisMin.requestFocus();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Complete todos los campos marcados con *", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void txtCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyPressed
        SiguienteFoco(evt);
    }//GEN-LAST:event_txtCodigoKeyPressed

    private void btnOkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnOkKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            btnOk.doClick();
        }
    }//GEN-LAST:event_btnOkKeyPressed

    private void txtDosisMaxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDosisMaxKeyReleased
        ConversionDosisMax();
    }//GEN-LAST:event_txtDosisMaxKeyReleased

    private void txtDosisMaxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDosisMaxKeyTyped
        // Verificar si la tecla pulsada no es un digito
        char caracter = evt.getKeyChar();
        if ((((caracter < '0') || (caracter > '9'))
                && (caracter != KeyEvent.VK_BACK_SPACE)
                && (caracter != ',' || txtDosisMax.getText().contains(",")))) {
            evt.consume(); // ignorar el evento de teclado
        }

        //Limitar cantidad de caracteres
        int longitud = txtDosisMax.getText().length();
        if (longitud > 7) {
            txtDosisMax.setText(txtDosisMax.getText().substring(0, 8));
        }
    }//GEN-LAST:event_txtDosisMaxKeyTyped

    public void SiguienteFoco(KeyEvent evt) {
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            ((JComponent) evt.getSource()).transferFocus();//Con esta parte transfieres el foco al siguiente campo sea un Jtextfield, Jpasswordfield, boton, etc..
        }
    }

    String registro[] = new String[5];
    MetodosCombo metodoscombo = new MetodosCombo();

    public void RegistroNuevo() {
        //Si el cultivo ya existe en la tabla
        for (int i = 0; i < abmproducto.tbDosis.getRowCount(); i++) {
            if (cbCultivo.getSelectedItem().toString().equals(abmproducto.tbDosis.getValueAt(i, 3).toString())) {

                int confirmado = JOptionPane.showConfirmDialog(null, "La dosis para este cultivo ya existe, ¿Desea reemplazarlo con los nuevos datos?", "Confirmación", JOptionPane.YES_OPTION);
                if (JOptionPane.YES_OPTION == confirmado) {
                    abmproducto.tbDosis.setValueAt(txtDosisMin.getText() + " " + lbDosisMinEstado.getText(), i, 1);
                    abmproducto.tbDosis.setValueAt(txtDosisMax.getText() + " " + lbDosisMaxEstado.getText(), i, 2);
                    abmproducto.tbDosis.setValueAt(cbCultivo.getSelectedItem(), i, 3);
                    abmproducto.tbDosis.setValueAt(metodoscombo.ObtenerIdComboBox(cbCultivo), i, 4);
                    dispose();
                    return;
                } else {
                    return;
                }
            }
        }

        registro[0] = txtCodigo.getText();
        registro[1] = txtDosisMin.getText() + " " + lbDosisMinEstado.getText();
        registro[2] = txtDosisMax.getText() + " " + lbDosisMaxEstado.getText();
        registro[3] = cbCultivo.getSelectedItem() + "";
        registro[4] = metodoscombo.ObtenerIdComboBox(cbCultivo) + "";

        abmproducto.modeloTablaDosis.addRow(registro);//agrega el registro a la tabla
        abmproducto.tbDosis.setModel(abmproducto.modeloTablaDosis);
        abmproducto.OcultarColumna(abmproducto.tbDosis, 0);
        abmproducto.tbDosis.getColumnModel().getColumn(1).setPreferredWidth(50);
        abmproducto.tbDosis.getColumnModel().getColumn(2).setPreferredWidth(50);
        abmproducto.tbDosis.getColumnModel().getColumn(3).setPreferredWidth(40);
        abmproducto.OcultarColumna(abmproducto.tbDosis, 4);
        dispose();
    }

    public void RegistroModificar() {
        //Si el cultivo ya existe en la tabla
        Boolean comparacioncodigo, comparacioncultivo;
        for (int i = 0; i < abmproducto.tbDosis.getRowCount(); i++) {
            comparacioncodigo = txtCodigo.getText().equals(abmproducto.tbDosis.getValueAt(i, 0).toString());
            comparacioncultivo = cbCultivo.getSelectedItem().toString().equals(abmproducto.tbDosis.getValueAt(i, 3).toString());
            if (comparacioncultivo == true && comparacioncodigo == false) {
                int confirmado = JOptionPane.showConfirmDialog(null, "La dosis para este cultivo ya existe, ¿Desea reemplazarlo con los nuevos datos?", "Confirmación", JOptionPane.YES_OPTION);
                if (JOptionPane.YES_OPTION == confirmado) {
                    abmproducto.tbDosis.setValueAt(txtDosisMin.getText() + " " + lbDosisMaxEstado.getText(), i, 1);
                    abmproducto.tbDosis.setValueAt(txtDosisMax.getText() + " " + lbDosisMaxEstado.getText(), i, 2);
                    abmproducto.tbDosis.setValueAt(cbCultivo.getSelectedItem(), i, 3);
                    abmproducto.tbDosis.setValueAt(metodoscombo.ObtenerIdComboBox(cbCultivo), i, 4);
                    dispose();
                }
            } else {
                abmproducto.tbDosis.setValueAt(txtDosisMin.getText() + " " + lbDosisMaxEstado.getText(), abmproducto.tbDosis.getSelectedRow(), 1);
                abmproducto.tbDosis.setValueAt(txtDosisMax.getText() + " " + lbDosisMaxEstado.getText(), abmproducto.tbDosis.getSelectedRow(), 2);
                abmproducto.tbDosis.setValueAt(cbCultivo.getSelectedItem(), abmproducto.tbDosis.getSelectedRow(), 3);
                abmproducto.tbDosis.setValueAt(metodoscombo.ObtenerIdComboBox(cbCultivo), abmproducto.tbDosis.getSelectedRow(), 4);
                dispose();
            }
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnCultivo;
    public javax.swing.JButton btnOk;
    public javax.swing.JButton btnVolver;
    public javax.swing.JComboBox<MetodosCombo> cbCultivo;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel8;
    public javax.swing.JLabel jLabel9;
    public javax.swing.JPanel jpBanner;
    public javax.swing.JPanel jpBotones;
    public javax.swing.JPanel jpBotones2;
    public javax.swing.JPanel jpPrincipal;
    public javax.swing.JLabel lbBanner;
    public javax.swing.JLabel lbDosisMaxEstado;
    public javax.swing.JLabel lbDosisMaxEstadoKgLt;
    public javax.swing.JLabel lbDosisMaxKgLt;
    public javax.swing.JLabel lbDosisMinEstado;
    public javax.swing.JLabel lbDosisMinEstadoKgLt;
    public javax.swing.JLabel lbDosisMinKgLt;
    public javax.swing.JTextField txtCodigo;
    public javax.swing.JTextField txtDosisMax;
    public javax.swing.JTextField txtDosisMin;
    // End of variables declaration//GEN-END:variables
}
