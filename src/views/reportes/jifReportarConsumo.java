/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.reportes;

import controllers.ctrConfig;
import controllers.ctrlAbasto;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import javax.swing.table.DefaultTableModel;
import views.Principal;
import static views.Principal.jdpEscritorio;

/**
 *
 * @author rana
 */
public class jifReportarConsumo extends javax.swing.JInternalFrame {
    String strNumAleatorio;
    /**
     * Creates new form jifReportarConsumo
     */
    public jifReportarConsumo() {
        initComponents();
        configurarTablaConsumo();
        Calendar calendario = new GregorianCalendar();
        Random random=new Random();
        String date=new SimpleDateFormat("YYYYMMdd").format(new Date());
        strNumAleatorio=date+calendario.get(Calendar.HOUR_OF_DAY)+calendario.get(Calendar.MINUTE)+calendario.get(Calendar.SECOND);
        jbtGenerarReporte.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtfNumeroFolio = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtReporteConsumos = new javax.swing.JTable();
        jlbMensaje = new javax.swing.JLabel();
        jbtGenerarReporte = new javax.swing.JButton();

        setClosable(true);

        jtfNumeroFolio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfNumeroFolioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfNumeroFolioFocusLost(evt);
            }
        });
        jtfNumeroFolio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfNumeroFolioActionPerformed(evt);
            }
        });
        jtfNumeroFolio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfNumeroFolioKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfNumeroFolioKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfNumeroFolioKeyTyped(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel1.setText("ESCANEAR CODIGO DE BARRAS");

        jtReporteConsumos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jtReporteConsumos);

        jlbMensaje.setFont(new java.awt.Font("sansserif", 0, 10)); // NOI18N
        jlbMensaje.setText("EL CODIGO NO EXISTE");

        jbtGenerarReporte.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jbtGenerarReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/list.png"))); // NOI18N
        jbtGenerarReporte.setText("GENERAR REPORTE");
        jbtGenerarReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtGenerarReporteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtfNumeroFolio)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlbMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbtGenerarReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfNumeroFolio, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlbMensaje)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtGenerarReporte)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtfNumeroFolioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfNumeroFolioKeyPressed

    }//GEN-LAST:event_jtfNumeroFolioKeyPressed

    private void jtfNumeroFolioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfNumeroFolioKeyReleased
        if(evt.getKeyCode()==evt.VK_ENTER && !jtfNumeroFolio.getText().equals("")){
            agregarReporteConsumo(jtfNumeroFolio.getText());
            System.err.println(strNumAleatorio);
            jtfNumeroFolio.setText("");
        }
    }//GEN-LAST:event_jtfNumeroFolioKeyReleased

    private void jtfNumeroFolioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfNumeroFolioKeyTyped
        char c=evt.getKeyChar();
        if(c<'0'|| c>'9'){
            evt.consume();//ignora el caracter digitado
            //validar el numero maxinos de caracteres digitados
        }else  if (jtfNumeroFolio.getText().length() >= 10) {
            //evt.setKeyChar((char) KeyEvent.VK_CLEAR);//Limpiar el caracter ingresado
        }
    }//GEN-LAST:event_jtfNumeroFolioKeyTyped

    private void jtfNumeroFolioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfNumeroFolioActionPerformed

    }//GEN-LAST:event_jtfNumeroFolioActionPerformed

    private void jtfNumeroFolioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfNumeroFolioFocusLost
    }//GEN-LAST:event_jtfNumeroFolioFocusLost

    private void jtfNumeroFolioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfNumeroFolioFocusGained
    }//GEN-LAST:event_jtfNumeroFolioFocusGained

    private void jbtGenerarReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtGenerarReporteActionPerformed
        jdValidar validar=new jdValidar(null, true);
        validar.getNumTem(strNumAleatorio);
        validar.setVisible(true);
    }//GEN-LAST:event_jbtGenerarReporteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtGenerarReporte;
    private javax.swing.JLabel jlbMensaje;
    private javax.swing.JTable jtReporteConsumos;
    private javax.swing.JTextField jtfNumeroFolio;
    // End of variables declaration//GEN-END:variables
    DefaultTableModel model=new DefaultTableModel();
    public void configurarTablaConsumo(){
        model.addColumn("N°");
        model.addColumn("FECHA Y HORA");
        model.addColumn("CODIGO");
        model.addColumn("MATERIAL");
        
        
        jtReporteConsumos.setModel(model);
        /*ESTABLECEMOS LAS DIMENCIONES DE LAS CELDAS DEL ENCABEZADO*/
        jtReporteConsumos.getColumnModel().getColumn(0).setMaxWidth(50);
        jtReporteConsumos.getColumnModel().getColumn(1).setMaxWidth(150);
        jtReporteConsumos.getColumnModel().getColumn(2).setMaxWidth(200);
        jtReporteConsumos.getColumnModel().getColumn(3).setMaxWidth(400);
    }
    int intNumRow=0;
    public void agregarReporteConsumo(String strNum){
        try {
            ResultSet rs=ctrlAbasto.getMaterialExiste(strNum);
            if(rs.next()){
                //ResultSet rs2=ctrlAbasto.getMaterialExisteReporte(intNum);
                //if(rs2.next()){
                    jlbMensaje.setText("EL CODIGO ESCANEADO RA FUE REGISTRADO");
                //}else{
                    Calendar calendario = new GregorianCalendar();
                    String rcFecha=new SimpleDateFormat("YYYY-MM-dd").format(new Date());
                    String rcHora=calendario.get(Calendar.HOUR_OF_DAY)+":"+calendario.get(Calendar.MINUTE)+":"+calendario.get(Calendar.SECOND);
                        boolean bolInsert=ctrlAbasto.insertReporteConsumo(rs.getInt("material_id"),strNumAleatorio,rcFecha,rcHora);
                    if(bolInsert){
                        tableReporteConsumos();
                        jbtGenerarReporte.setEnabled(true);
                        System.out.println("DATO AGREGADO");
                    }else{
                        System.err.println("ERROR AL GUARDAR");
                    }
                //}
            }else{
                jlbMensaje.setText("EL CODIGO ESCANEADO NO PERTENECE A NINGÚN MATERIAL");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void tableReporteConsumos(){
        for (int i = jtReporteConsumos.getRowCount()-1; i >=0; i--) {
            model.removeRow(i);
        }
        try {
            ResultSet rs=ctrlAbasto.getReporteConsumo(strNumAleatorio);
            while (rs.next()) { 
                intNumRow++;
                Object object[]={
                    intNumRow,
                    rs.getString("rc_fecha")+" "+rs.getString("rc_hora"),
                    rs.getString("material_id"),
                    rs.getString("material_nombre"),  
                };
                model.addRow(object);
                jtReporteConsumos.setModel(model);
            }
            intNumRow=0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}