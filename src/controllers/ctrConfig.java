/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rana
 */
public class ctrConfig {
    static String strTitulo="Sistema de Gestión Hospitalaria Magdalena";
    
    public static void msjInformation(String strMsj){
        JOptionPane.showMessageDialog(null, strMsj,strTitulo,JOptionPane.INFORMATION_MESSAGE);
    }
    public static void msjWarning(String strMsj){
        JOptionPane.showMessageDialog(null, strMsj,strTitulo,JOptionPane.WARNING_MESSAGE);
    }
    public static void msjError(String strMsj){
        JOptionPane.showMessageDialog(null, strMsj,strTitulo,JOptionPane.ERROR_MESSAGE);
    }
    /*CONFIGURACIONES DE TABLA*/
    static DefaultTableModel model=new DefaultTableModel();
    
    public static void configTableHader(Object[] objAtributos,JTable jTable){
        for (int i = 0; i < objAtributos.length; i++) {
            model.addColumn(objAtributos[i]);
        }
        jTable.setModel(model);
    }
    public static void openFileExplorer(String strUrl){
        try {
            Process process=Runtime.getRuntime().exec("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe \"www.google.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
