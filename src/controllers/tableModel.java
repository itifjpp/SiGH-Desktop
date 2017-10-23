/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author rana
 */
public class tableModel extends DefaultTableCellRenderer {
     
    @Override
    public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected,boolean hasFocus, int row, int column)
    {
        JLabel label = new JLabel();
            if (value!=null) {
            label.setHorizontalAlignment(JLabel.CENTER);
            //value is parameter which filled by byteOfImage
            label.setForeground(new Color(37, 102,89)); 
            label.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
            label.setText(value.toString());
        //label.setIcon(new ImageIcon(getClass().getResource(value.toString())));
        }
 
        return label;
    }
}
