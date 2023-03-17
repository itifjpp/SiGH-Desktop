/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rana
 */
public class ctrConfig {
    static String strTitulo="SiMA";
    
    public static void msjInformation(String strMsj){
        JOptionPane.showMessageDialog(null, strMsj,"Información",JOptionPane.INFORMATION_MESSAGE);
    }
    public static void msjWarning(String strMsj){
        JOptionPane.showMessageDialog(null, strMsj,"Información",JOptionPane.WARNING_MESSAGE);
    }
    public static void msjError(String strMsj){
        JOptionPane.showMessageDialog(null, strMsj,"Información",JOptionPane.ERROR_MESSAGE);
    }
    /*CONFIGURACIONES DE TABLA*/
    static DefaultTableModel model=new DefaultTableModel();
    
    public static void configTableHader(Object[] objAtributos,JTable jTable){
        for (int i = 0; i < objAtributos.length; i++) {
            model.addColumn(objAtributos[i]);
        }
        jTable.setModel(model);
    }
    
    
    public static String getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    public static void openFileExplorer(String strUrl){
        try {
            Process process=Runtime.getRuntime().exec("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe \""+strUrl+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
