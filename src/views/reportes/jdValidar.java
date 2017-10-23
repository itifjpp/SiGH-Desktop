/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.reportes;

import views.*;
import controllers.ctrlConexion;
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
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import controllers.ctrConfig;
import controllers.ctrlAbasto;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author felipe de jesus <itifjpp@gmail.com>
 */
public class jdValidar extends java.awt.Dialog {
    String strNumTemp="";
    int intEmpleadoId=0;
    /**
     * Creates new form Login
     */
    private DPFPCapture Lector=DPFPGlobal.getCaptureFactory().createCapture();
    private DPFPEnrollment Reclutador=DPFPGlobal.getEnrollmentFactory().createEnrollment();
    private DPFPVerification Verificador=DPFPGlobal.getVerificationFactory().createVerification();
    private DPFPTemplate dPFPTemplate;
    private static String TEMPLATE_PROPERTY="template";
    public jdValidar(java.awt.Frame parent, boolean modal){
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(this);
        setTitle("ESCANEAR HUELLA DIGITAL");
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
            ps=con.prepareStatement("SELECT e.empleado_id,e.empleado_dp,e.empleado_nombre,e.empleado_apellidos,e.empleado_matricula FROM os_empleados AS e WHERE e.empleado_dp!=''");
            ResultSet rs=ps.executeQuery();
            //OBTENEMOS TODOS LOS USUARIOS PARA COMPARAR LA HUELLA DIGITAL
            while (rs.next()) {   
                System.err.println(rs.getInt("empleado_id"));
                //LEE LA PLANTILLA DE LA BASE DE DATOS
                byte templateBuffer[]=rs.getBytes("empleado_dp");
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
                    // principal.ObtenerInformacionUsuario(rs.getInt("empleado_id"));
                    //p.setVisible(true);
                    intEmpleadoId=rs.getInt("empleado_id");
                    jlbEmpleado.setText(rs.getString("empleado_nombre"));
                    jlbApellidos.setText(rs.getString("empleado_apellidos"));
                    jlbMatricula.setText(rs.getString("empleado_matricula"));
                    jbtFinalizar.setEnabled(true);
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
        
        MostrarMensajes("NO SE ESTÁ USANDO EL LECTOR DE HUELLA DIGITAL");
    }
    public void MostrarMensajes(String strTexto){
        jtaMensajes.append(strTexto+"\n");
        System.out.println(strTexto);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jlbImageHuella = new javax.swing.JLabel();
        jlbInformacion = new javax.swing.JLabel();
        jlbEmpleado = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaMensajes = new javax.swing.JTextArea();
        jlbApellidos = new javax.swing.JLabel();
        jbtFinalizar = new javax.swing.JButton();
        jlbMatricula = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlbImageHuella.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlbImageHuella, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlbImageHuella, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jlbInformacion.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jlbInformacion.setText("INFORMACIÓN DEL USUARIO");

        jlbEmpleado.setText("En espera de autenticación");
        jlbEmpleado.setToolTipText("");

        jtaMensajes.setEditable(false);
        jtaMensajes.setColumns(20);
        jtaMensajes.setRows(5);
        jScrollPane1.setViewportView(jtaMensajes);

        jlbApellidos.setText("En espera de autenticación");

        jbtFinalizar.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jbtFinalizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/checked-box.png"))); // NOI18N
        jbtFinalizar.setText("FINALIZAR ");
        jbtFinalizar.setEnabled(false);
        jbtFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtFinalizarActionPerformed(evt);
            }
        });

        jlbMatricula.setText("En espera de autenticación");

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel1.setText("NOMBRE:");

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel2.setText("APELLIDOS:");

        jLabel3.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel3.setText("MATRICULA:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlbEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jlbInformacion, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                            .addComponent(jlbApellidos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jlbMatricula, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbtFinalizar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jlbInformacion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlbEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlbApellidos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlbMatricula)
                        .addGap(18, 18, 18)
                        .addComponent(jbtFinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Iniciar();
        start();
        EstadoHuellas();
    }//GEN-LAST:event_formWindowOpened

    private void jbtFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtFinalizarActionPerformed
        actualizarReporteConsumos();
        
    }//GEN-LAST:event_jbtFinalizarActionPerformed

    /**
     * @param args the command line arguments
     */



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtFinalizar;
    private javax.swing.JLabel jlbApellidos;
    private javax.swing.JLabel jlbEmpleado;
    private javax.swing.JLabel jlbImageHuella;
    private javax.swing.JLabel jlbInformacion;
    private javax.swing.JLabel jlbMatricula;
    private javax.swing.JTextArea jtaMensajes;
    // End of variables declaration//GEN-END:variables

    void getNumTem(String strNumAleatorio) {
        strNumTemp=strNumAleatorio;
    }
    public void actualizarReporteConsumos(){
        try {
            boolean bolUpdate=ctrlAbasto.updateReporteConsumo(strNumTemp, intEmpleadoId);
            if(bolUpdate){
                ctrConfig.msjInformation("DATOS GUARDADOS");
                this.dispose();
                Principal.jdpEscritorio.removeAll();
                Principal.jdpEscritorio.revalidate();
                Principal.jdpEscritorio.repaint();
                ctrConfig.openFileExplorer("http://localhost/sigTRIAGE/Inicio/Documentos/ConsumoMaterialesOsteo?numTemp="+strNumTemp);
                
            }else{
                ctrConfig.msjError("A OCURRIDO UN ERROR AL GUARDAR LA INFORMACIÓN");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
    
    public void pdfReporteConsumo(){
        try {
            BaseColor baseColor=new BaseColor(50,50,200);
            Paragraph head=new Paragraph("SISTEMA DE GESTIÓN HOSPITALARIA",new Font(Font.FontFamily.HELVETICA,20,Font.BOLDITALIC,baseColor));
            head.setAlignment(Element.ALIGN_CENTER);
            
            
            float anchoCols[]={0.5f,3.0f,1,5f,2.0f,2.0f};
            PdfPTable  datos=new  PdfPTable(anchoCols);
            datos.setWidthPercentage(100);
            datos.getDefaultCell().setBorderWidth(0.1f);
            datos.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            String strHeaders[]={"N°","FOLIO","FECHA Y HORA","MATERIAL"};
            for(int i=0; i<5; i++){
                PdfPCell heads=new PdfPCell(new Paragraph(strHeaders[i], new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC, baseColor)));
                heads.setBackgroundColor(new BaseColor(200,200,200));
                heads.setBorderColor(new BaseColor(180,180,180));
                heads.setHorizontalAlignment(Element.ALIGN_CENTER);
                heads.setFixedHeight(30f);
                heads.setVerticalAlignment(Element.ALIGN_MIDDLE);
                datos.addCell(heads);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
    }
}
