/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package views;

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
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import controllers.ctrlConexion;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author felipe de jesus
 */
public class jdIngresoPacienteValidar extends javax.swing.JDialog {
    private DPFPCapture Lector=DPFPGlobal.getCaptureFactory().createCapture();
    private DPFPEnrollment Reclutador=DPFPGlobal.getEnrollmentFactory().createEnrollment();
    private DPFPVerification Verificador=DPFPGlobal.getVerificationFactory().createVerification();
    private DPFPTemplate dPFPTemplate;
    private static String TEMPLATE_PROPERTY="template";
    public static int intIdPaciente=0;
    /**
     * Creates new form jdIngresoPacienteValidar
     */
    public jdIngresoPacienteValidar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(this);
        setTitle("Validar ingreso de paciente");
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
                        ValidarHuellaDigital();
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
    public void ValidarHuellaDigital(){
        try {
            Connection con=(Connection)ctrlConexion.ctrConecta();
            PreparedStatement ps=null;
            ps=con.prepareStatement("SELECT p.paciente_id,p.paciente_dp FROM tbl_pacientes AS p WHERE p.paciente_dp!=''");
            ResultSet rs=ps.executeQuery();
            //OBTENEMOS TODOS LOS USUARIOS PARA COMPARAR LA HUELLA DIGITAL
            while (rs.next()) {   
                System.err.println(rs.getInt("paciente_id"));
                //LEE LA PLANTILLA DE LA BASE DE DATOS
                byte templateBuffer[]=rs.getBytes("paciente_dp");
                //CREA UNA NUEVA PLANTILLA CREADA A PARTIR DE LA GUARDADA EN LA BASE DE DATOS
                DPFPTemplate referenceTemplate=DPFPGlobal.getTemplateFactory().createTemplate(templateBuffer);
                //ENVIA PLANTILLA CREADA AL OBJETO CONTENEDOR DE TEMPLATE DEL COMPONENTE DE HUELLA DIGITAL
                setTemplate(referenceTemplate);
                //COMPARA LAS CARACTERISTICAS DE LA HUELLA DIGITAL RECIENTEMENTE CAPTURADA CON LA DE ALGUNA PLANTILLA
                //GUARDADA EN LA BASE DE DATOS QUE COINCIDA CON ESTE TIPO
                DPFPVerificationResult result=Verificador.verify(dPFPFeatureSetVerificacion, getTemplate());
                /*
                    COMPARA LAS PLANTILLAS ACTUAL Y LA QUE ESTA EN LA BASE DE DATOS SI ENCUENTRA
                    CORRESPONDENCIA DIBUJA EL MAPA E INDICA QUE EL NOMBRE DE LA PERSONA QUE COINCIDIO
                */
                if(result.isVerified()){
//                    principal.ObtenerInformacionUsuario(rs.getInt("empleado_id"));
                    //p.setVisible(true);

                    dispose();
                    this.intIdPaciente=rs.getInt("paciente_id");
                    jdlIngresoPacienteDetalle view =new jdlIngresoPacienteDetalle(null, true);
                    view.setLocationRelativeTo(this);
                    view.setVisible(true);
                    
                    return;
                }
            }
            //SI NO ENCUENTRA ALGUNA HUELLA CORRESPONDIENTE AL NOMBRE LO INDICAMOS CON UN MENSAJE
            JOptionPane.showMessageDialog(null, "NO EXISTE NINGÚN REGISTRO QUE COINCIDA CON LA HUELLA","VERIFICACIÓN DE HUELLA",JOptionPane.ERROR_MESSAGE);
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERROR AL GUARDAR LOS DATOS DE LA HUELLA");
        }
    }
    private void stop() {
        Lector.stopCapture();
        MostrarMensajes("NO SE ESTÁ USANDO EL LECTO DE HUELLA DIGITAL");
    }
    public void MostrarMensajes(String strTexto){
        jtaMensajes.append(strTexto+"\n");
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
        jPanel2 = new javax.swing.JPanel();
        jlbImageHuella = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaMensajes = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlbImageHuella, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlbImageHuella, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jtaMensajes.setEditable(false);
        jtaMensajes.setColumns(20);
        jtaMensajes.setRows(5);
        jScrollPane1.setViewportView(jtaMensajes);

        jButton1.setText("Ir a detalles");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField1.setText("1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
                    .addComponent(jTextField1)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Iniciar();
        start();
        EstadoHuellas();
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        Lector.stopCapture();
        Lector=null;  
    }//GEN-LAST:event_formWindowClosing

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Lector.stopCapture();
        Lector=null;
        dispose();

        this.intIdPaciente=Integer.parseInt( jTextField1.getText());
        jdlIngresoPacienteDetalle view =new jdlIngresoPacienteDetalle(null, true);
        view.setLocationRelativeTo(this);
        view.setVisible(true);        
    }//GEN-LAST:event_jButton1ActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(jdIngresoPacienteValidar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jdIngresoPacienteValidar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jdIngresoPacienteValidar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jdIngresoPacienteValidar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                jdIngresoPacienteValidar dialog = new jdIngresoPacienteValidar(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel jlbImageHuella;
    private javax.swing.JTextArea jtaMensajes;
    // End of variables declaration//GEN-END:variables
}
