/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

/**
 *
 * @author rana
 */
public class tableModelButton extends DefaultTableCellRenderer {
     
    @Override
    public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected,boolean hasFocus, int row, int column)
    {
        TableModel model = table.getModel();
        String strEstatus=(String)table.getValueAt(row, 2);
        JButton btn = new JButton();
        if (value!=null) {
            btn.setHorizontalAlignment(JLabel.CENTER);
            //value is parameter which filled by byteOfImage
            btn.setForeground(new Color(37, 102,89)); 
            btn.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
            btn.setText(value.toString());
            
        //label.setIcon(new ImageIcon(getClass().getResource(value.toString())));
        }
        if(!strEstatus.equals("En espera")){
            btn.setEnabled(false); 
        }
        return btn;
    }
}
