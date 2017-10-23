/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.empleados;

import controllers.ctrConfig;
import controllers.ctrlConexion;
import controllers.ctrlEmpleados;
import models.mdlEmpleados;
import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPErrorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPErrorEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.capture.event.DPFPSensorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPSensorEvent;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author rana
 */
public class jifEmpleado extends javax.swing.JInternalFrame {
    public int intEmpleadoId=0;
    static Connection con=(Connection)ctrlConexion.ctrConecta();
    static PreparedStatement ps=null;
    private DPFPCapture Lector=DPFPGlobal.getCaptureFactory().createCapture();
    private DPFPEnrollment Reclutador=DPFPGlobal.getEnrollmentFactory().createEnrollment();
    private DPFPVerification Verificador=DPFPGlobal.getVerificationFactory().createVerification();
    private DPFPTemplate dPFPTemplate;
    private static String TEMPLATE_PROPERTY="template";
    public jifEmpleado() {
        initComponents();
        btnGuardar.setEnabled(false);
    }
    protected void Iniciar(){
        Lector.addDataListener(new DPFPDataAdapter(){
            @Override 
            public void dataAcquired(final DPFPDataEvent e){
                SwingUtilities.invokeLater(new Runnable() { 
                    @Override 
                    public void run() { 
                        ProcesarCaptura(e.getSample());
                        MostrarMensajes("LA HUELLA DIGITAL HA SIDO CAPTURADO");
                    } 
                });
            }
        });
        Lector.addReaderStatusListener(new DPFPReaderStatusAdapter(){
            @Override 
            public void readerConnected(final DPFPReaderStatusEvent e){
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        MostrarMensajes("EL SENSOR DE HUELLA DIGITAL ESTA ACTIVADO O CONECTADO"); 
                    }
                });
            }
            @Override 
            public void readerDisconnected(final DPFPReaderStatusEvent e){
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        MostrarMensajes("EL SENSOR DE HUELLA DIGITAL ESTA DESACTIVADO O DESCONECTADO"); 
                    }
                });
            }
        });
        Lector.addSensorListener(new DPFPSensorAdapter(){
            @Override 
            public void fingerTouched(final DPFPSensorEvent e){
                SwingUtilities.invokeLater(new Runnable() {
                    @Override 
                    public void run() {
                        MostrarMensajes("EL DEDO HA SIDO COLOCADO SOBRE EL LECTOR DE HUELLA"); 
                    }
                });
            }
            @Override 
            public void fingerGone(final DPFPSensorEvent e){
                SwingUtilities.invokeLater(new Runnable() {
                    @Override 
                    public void run() {
                        MostrarMensajes("EL DEDO HA SIDO QUITADO DEL LECTOR DE HUELLA"); 
                    }
                });
            }
        });
        Lector.addErrorListener(new DPFPErrorAdapter(){
            public void errorReader(final DPFPErrorEvent e){
                SwingUtilities.invokeLater(new Runnable() {
                    @Override public void run() {
                        MostrarMensajes("ERROR: "+e.getError()); 
                    }
                });
            }
        });
    }
    
    public DPFPFeatureSet dPFPFeatureSetInscripcion;
    public DPFPFeatureSet dPFPFeatureSetVerificacion;
    
    public DPFPFeatureSet extraerCaracteristicas(DPFPSample sample, DPFPDataPurpose purpose){
        DPFPFeatureExtraction extractor=DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
        try {
            return extractor.createFeatureSet(sample, purpose);
        } catch (Exception e) {
            return null;
        }
    }
    public Image CrearImagenHuella(DPFPSample sample){
        return DPFPGlobal.getSampleConversionFactory().createImage(sample);
    }
    public void DibujarHuella(Image img){
        jlbImageHuella.setIcon(new ImageIcon(
            img.getScaledInstance(jlbImageHuella.getWidth(), jlbImageHuella.getHeight(), Image.SCALE_DEFAULT)
        ));
        repaint();
    }
    public void EstadoHuellas(){
        MostrarMensajes("MUESTRAS DE HUELLAS NECESARIAS PRA GUARDAR TEMPLATE");
        Reclutador.getFeaturesNeeded();
    }
    public void start(){
        Lector.startCapture();
        MostrarMensajes("UTILIZANDO EL LECTOR DE HUELLA DIGITAL");
    }
    public DPFPTemplate getTemplate(){
        return dPFPTemplate;
    }
    public void setTemplate(DPFPTemplate template){
        DPFPTemplate old=this.dPFPTemplate;
        this.dPFPTemplate=template;
        firePropertyChange(TEMPLATE_PROPERTY, old, template);
    }
    public void ProcesarCaptura(DPFPSample sample){
        //PROCESAR LA MUESTRA DE LA HUELLA Y CREAR UN CONJUNTO DE CARACTERISTICAS CON EL PROPOSITo DE INSCRIPCION
        dPFPFeatureSetInscripcion=extraerCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);
        //PROCESAR LA MUESTRA DE LA HUELLA Y CREAR UN CONJUNTO DE CARACTERISTICCAS CON EL PROPOSITO DE VERIFICACIÓN
        dPFPFeatureSetVerificacion=extraerCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);
        //COMPROVAR LA CALIDAD DE LA MUESTRA DE LA HUELLA Y LO AÑADE AL RECLUTADOR SU ES BUENO
        if(dPFPFeatureSetInscripcion!=null){
            try {
                System.out.println("LAS CARACTERISTICAS DE LA HUELLA HAN SIDO CREADAS");
                //AGREGAR LAS CARACTERISTICAS DE LA HUELLA A LA PLANTILLA A CREAR
                Reclutador.addFeatures(dPFPFeatureSetInscripcion);
                //DIBUJAR LA HUELLA DACTILAR CAPTURADA
                Image img=CrearImagenHuella(sample);
                DibujarHuella(img);
                //btnVerificar.setEnabled(true);
                //btnIdentificar.setEnabled(true);
                
            } catch (DPFPImageQualityException ex) {
                System.out.println("ERROR: "+ex.getMessage());
            }
            finally{
                EstadoHuellas();
                //COMPROBAR SI LA PLANTILLA SE HA CREADO
                switch(Reclutador.getTemplateStatus()){
                    case TEMPLATE_STATUS_READY://INFORME DE ÉXITO Y DETIENE LA CAPTURA DE HUELLA
                        stop();
                        setTemplate(Reclutador.getTemplate());
                        MostrarMensajes("LA PLANTILLA SE HA CREADO");
                        //btnIdentificar.setEnabled(true);
                        //btnVerificar.setEnabled(false);
                        btnGuardar.setEnabled(true);
                        btnGuardar.grabFocus();
                        break;
                    case TEMPLATE_STATUS_FAILED://INFORME DE FALLAS Y REINICI LA CAPTURA DE HUELLAS
                        Reclutador.clear();
                        stop();
                        EstadoHuellas();
                        setTemplate(null);
                        MostrarMensajes("LA PLANTILLA DE HUELLA NO PUDO SER CREADA");
                }
            }
        }
    }
    private void stop() {
        Lector.stopCapture();
        MostrarMensajes("NO SE ESTÁ USANDO EL LECTO DE HUELLA DIGITAL");
    }
    public void MostrarMensajes(String strTexto){
        jtaMensajes.append(strTexto+"\n");
    }
    public void GuardarHuella()throws SQLException{
        //Obtiene los datos del tamplate de la huella actual
        ByteArrayInputStream arrayInputStream=new ByteArrayInputStream(dPFPTemplate.serialize());
        Integer intHuella=dPFPTemplate.serialize().length;
        try {
            
            ps=con.prepareStatement("UPDATE os_empleados SET empleado_dp=? WHERE empleado_id=?");
            System.err.println("ID:"+intEmpleadoId);
            ps.setBinaryStream(1, arrayInputStream,intHuella);
            ps.setInt(2,intEmpleadoId);
            int intSql=ps.executeUpdate();
            con.close();
            ps.close();
            if(intSql>0){
                ctrConfig.msjInformation("DATOS GUARDADOS");
                this.dispose();
            }else{
                ctrConfig.msjError("ERROR AL GUARDAR LOS DATOS");
                this.dispose();
            }
        } catch (Exception e) {
            System.err.println("ERROR AL GUARDAR LOS DATOS DE LA HUELLA");
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jlbImageHuella = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jtfMatricula = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtfNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jtfApellidos = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jtfCategoria = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaMensajes = new javax.swing.JTextArea();
        btnIniciarLector = new javax.swing.JButton();
        btnStopLector = new javax.swing.JButton();

        setClosable(true);
        setTitle("EMPLEADO");

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlbImageHuella, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlbImageHuella, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel1.setText("MATRICULA");

        jtfMatricula.setEditable(false);

        jLabel2.setText("NOMBRE");

        jtfNombre.setEditable(false);

        jLabel3.setText("APELLIDOS");

        jtfApellidos.setEditable(false);

        jLabel4.setText("CATEGORÍA");

        jtfCategoria.setEditable(false);

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/save.png"))); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jtaMensajes.setColumns(20);
        jtaMensajes.setRows(5);
        jScrollPane1.setViewportView(jtaMensajes);

        btnIniciarLector.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/fingerprint-scan.png"))); // NOI18N
        btnIniciarLector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarLectorActionPerformed(evt);
            }
        });

        btnStopLector.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/x-button.png"))); // NOI18N
        btnStopLector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopLectorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnIniciarLector, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnStopLector, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jtfNombre)
                                            .addComponent(jtfMatricula)
                                            .addComponent(jLabel3)
                                            .addComponent(jtfApellidos)
                                            .addComponent(jLabel4)
                                            .addComponent(jtfCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(181, 181, 181)
                                .addComponent(btnGuardar)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnIniciarLector, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnStopLector, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try {
            GuardarHuella();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnIniciarLectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarLectorActionPerformed
        Iniciar();
        start();
        EstadoHuellas();
        btnGuardar.setEnabled(false);
//        btnIniciarLector.setEnabled(false);
//        btnStopLector.setEnabled(true);
    }//GEN-LAST:event_btnIniciarLectorActionPerformed

    private void btnStopLectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopLectorActionPerformed
        //btnStopLector.setEnabled(false);
        stop();
    }//GEN-LAST:event_btnStopLectorActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnIniciarLector;
    private javax.swing.JButton btnStopLector;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jlbImageHuella;
    private javax.swing.JTextArea jtaMensajes;
    private javax.swing.JTextField jtfApellidos;
    private javax.swing.JTextField jtfCategoria;
    private javax.swing.JTextField jtfMatricula;
    private javax.swing.JTextField jtfNombre;
    // End of variables declaration//GEN-END:variables
    public void obtenerInformacionEmpleado(int intId){
        try {
            ResultSet rs=ctrlEmpleados.getEmpleado(intId);
            if(rs.next()){
                intEmpleadoId=intId;
                jtfMatricula.setText(rs.getString("empleado_matricula"));
                jtfNombre.setText(rs.getString("empleado_nombre"));
                jtfApellidos.setText(rs.getString("empleado_apellidos"));
                jtfCategoria.setText(rs.getString("empleado_categoria"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
