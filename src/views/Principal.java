/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import controllers.ctrConfig;
import views.empleados.jifEmpleados;
import controllers.ctrlEmpleados;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Calendar;
import javax.swing.JInternalFrame;
import views.pacientes.jifPacientes;

/**
 *
 * @author felipe de jesus <itifjpp@gmail.com>
 */
public class Principal extends javax.swing.JFrame {
    int intX,intY;
    ctrConfig ctrConfig= new ctrConfig();
    /**
     * Creates new form Principal
     */
    public Principal() {
        initComponents();
        setTitle("SiMA - Centro Integral de Cancerología"); 
        setLocationRelativeTo(this);
        this.getContentPane().setBackground(new Color(37, 102,89));
        accionesMenusCerrarSesion();
        jlbFecha.setText(this.ctrConfig.getCurrentDate());
        this.Thread1();
        
    }
//    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jdpEscritorio = new javax.swing.JDesktopPane();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jlbFecha = new javax.swing.JLabel();
        jlbHora = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jmOpciones = new javax.swing.JMenu();
        jmiIniciarSesion = new javax.swing.JMenuItem();
        jmiCerrarSesion = new javax.swing.JMenuItem();
        jmControlIngresos = new javax.swing.JMenu();
        jmiControlIngresosPacientes = new javax.swing.JMenuItem();
        jmAdministrar = new javax.swing.JMenu();
        jmiAdministrarEmpleados = new javax.swing.JMenuItem();
        jmiAdministrarPacientes = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(37, 102, 89));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jdpEscritorio.setBackground(new java.awt.Color(204, 204, 204));

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jdpEscritorio.setLayer(jButton1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jdpEscritorioLayout = new javax.swing.GroupLayout(jdpEscritorio);
        jdpEscritorio.setLayout(jdpEscritorioLayout);
        jdpEscritorioLayout.setHorizontalGroup(
            jdpEscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdpEscritorioLayout.createSequentialGroup()
                .addGap(161, 161, 161)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jdpEscritorioLayout.setVerticalGroup(
            jdpEscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdpEscritorioLayout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jButton1)
                .addContainerGap(386, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("CIC");

        jLabel2.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Centro Integral de Cancerología");

        jlbFecha.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jlbFecha.setForeground(new java.awt.Color(255, 255, 255));
        jlbFecha.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbFecha.setText("LUNES 20 DE OCTUBRE DEL 2017");

        jlbHora.setFont(new java.awt.Font("sansserif", 0, 20)); // NOI18N
        jlbHora.setForeground(new java.awt.Color(255, 255, 255));
        jlbHora.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlbHora.setText("12:24:50 PM");

        jMenuBar1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jMenuBar1MouseDragged(evt);
            }
        });
        jMenuBar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuBar1MousePressed(evt);
            }
        });

        jmOpciones.setText("Menú");
        jmOpciones.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jmOpciones.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jmOpciones.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jmOpciones.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jmiIniciarSesion.setText("Iniciar sesión");
        jmiIniciarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiIniciarSesionActionPerformed(evt);
            }
        });
        jmOpciones.add(jmiIniciarSesion);

        jmiCerrarSesion.setText("Cerrar sesión");
        jmOpciones.add(jmiCerrarSesion);

        jMenuBar1.add(jmOpciones);

        jmControlIngresos.setText("Control de ingresos");
        jmControlIngresos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmControlIngresosActionPerformed(evt);
            }
        });

        jmiControlIngresosPacientes.setText("Ingreso de pacientes");
        jmiControlIngresosPacientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiControlIngresosPacientesActionPerformed(evt);
            }
        });
        jmControlIngresos.add(jmiControlIngresosPacientes);

        jMenuBar1.add(jmControlIngresos);

        jmAdministrar.setText("Administrar");
        jmAdministrar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jmAdministrar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jmAdministrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jmAdministrar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jmAdministrar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jmiAdministrarEmpleados.setText("Empleados");
        jmiAdministrarEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiAdministrarEmpleadosActionPerformed(evt);
            }
        });
        jmAdministrar.add(jmiAdministrarEmpleados);

        jmiAdministrarPacientes.setText("Pacientes");
        jmiAdministrarPacientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiAdministrarPacientesActionPerformed(evt);
            }
        });
        jmAdministrar.add(jmiAdministrarPacientes);

        jMenuBar1.add(jmAdministrar);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlbHora, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(276, 276, 276)
                        .addComponent(jlbFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jdpEscritorio)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jdpEscritorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlbHora, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlbFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

    }//GEN-LAST:event_formWindowOpened

    private void jMenuBar1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuBar1MousePressed
        intX=evt.getX();
        intY=evt.getY();
    }//GEN-LAST:event_jMenuBar1MousePressed

    private void jMenuBar1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuBar1MouseDragged
        Point point=MouseInfo.getPointerInfo().getLocation();
        //jMenuBar1.setCursor(new Cursor(Cursor.MOVE_CURSOR));
        setLocation(point.x-intX, point.y-intY); 
        
    }//GEN-LAST:event_jMenuBar1MouseDragged

    private void jmiIniciarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiIniciarSesionActionPerformed
        jdLogin login=new jdLogin(this, true);
        login.setLocationRelativeTo(this);
        login.setVisible(true);
    }//GEN-LAST:event_jmiIniciarSesionActionPerformed

    private void jmiAdministrarPacientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiAdministrarPacientesActionPerformed
        
        final JInternalFrame[] frames = jdpEscritorio.getAllFrames();
        if( !Arrays.asList(frames).toString().contains("jifPacientes") ) {
            jifPacientes pacientes=new jifPacientes();
            jdpEscritorio.add(pacientes);
            Dimension desktopSize = jdpEscritorio.getSize();
            Dimension FrameSize = pacientes.getSize();
            pacientes.setLocation((desktopSize.width - FrameSize.width)/2,(desktopSize.height-FrameSize.height)/2);      
            pacientes.setVisible(true);return ;
        }
        
    }//GEN-LAST:event_jmiAdministrarPacientesActionPerformed

    private void jmiAdministrarEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiAdministrarEmpleadosActionPerformed
        
        final JInternalFrame[] frames = jdpEscritorio.getAllFrames();
        if( !Arrays.asList(frames).toString().contains("jifEmpleados") ) {
            jifEmpleados empleados=new jifEmpleados();
            jdpEscritorio.add(empleados);
            Dimension desktopSize = jdpEscritorio.getSize();
            Dimension FrameSize = empleados.getSize();
            empleados.setLocation((desktopSize.width - FrameSize.width)/2,(desktopSize.height-FrameSize.height)/2);      
            empleados.setVisible(true);

        }
        
    }//GEN-LAST:event_jmiAdministrarEmpleadosActionPerformed

    private void jmControlIngresosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmControlIngresosActionPerformed
        

    }//GEN-LAST:event_jmControlIngresosActionPerformed

    private void jmiControlIngresosPacientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiControlIngresosPacientesActionPerformed
        jdIngresoPacienteValidar view=new jdIngresoPacienteValidar(this, true);
        view.setLocationRelativeTo(this);
        view.setVisible(true);
    }//GEN-LAST:event_jmiControlIngresosPacientesActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        accionesMenusCerrarSesion();
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
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar jMenuBar1;
    public static javax.swing.JDesktopPane jdpEscritorio;
    private javax.swing.JLabel jlbFecha;
    private javax.swing.JLabel jlbHora;
    private javax.swing.JMenu jmAdministrar;
    private javax.swing.JMenu jmControlIngresos;
    private javax.swing.JMenu jmOpciones;
    private javax.swing.JMenuItem jmiAdministrarEmpleados;
    private javax.swing.JMenuItem jmiAdministrarPacientes;
    private javax.swing.JMenuItem jmiCerrarSesion;
    private javax.swing.JMenuItem jmiControlIngresosPacientes;
    private javax.swing.JMenuItem jmiIniciarSesion;
    // End of variables declaration//GEN-END:variables
    public void ObtenerInformacionUsuario(int intId){

        try {
            System.err.println(intId);
            ResultSet rs=ctrlEmpleados.getEmpleado(intId);
            if(rs.next()){
                //jlbUsuario.setText("USUARIO: "+rs.getString("empleado_nombre")+" "+rs.getString("empleado_apellidos"));
                //jlbMatricula.setText("MATRICULA: "+rs.getString("empleado_matricula"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void accionesMenusIniciarSesion(){
        
        jmAdministrar.setEnabled(true);
        jmiCerrarSesion.setEnabled(true);
        jmiIniciarSesion.setEnabled(false);
    }
    public void accionesMenusCerrarSesion(){
        jmAdministrar.setEnabled(false);
        jmiCerrarSesion.setEnabled(false);
        jmiIniciarSesion.setEnabled(true);
    }
    public void Thread1() {
        Thread t1 = new Thread() {
            public void run() {
                try {
                    for(;;) {
                        Calendar cal = Calendar.getInstance();
                        int hour = cal.get(Calendar.HOUR_OF_DAY);
                        int minute = cal.get(Calendar.MINUTE);
                        int second = cal.get(Calendar.SECOND);

                        jlbHora.setText(hour + ":" + minute + ":" + second);
                        sleep(1000);
                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } 
            }
        };
        t1.start();
    }
}
