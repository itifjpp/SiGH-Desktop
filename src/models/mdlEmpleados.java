/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import java.io.ByteArrayInputStream;

/**
 * 
 * @author felipe de jesus <itifjpp@gmail.com>
 */
public class mdlEmpleados {
    public int intEmpleado_id=0;
    public String strNombre="";
    public String strApellidos="";
    public ByteArrayInputStream byteDp=null;
    
    public int getIntEmpleado_id() {
        return intEmpleado_id;
    }

    public void setIntEmpleado_id(int intEmpleado_id) {
        this.intEmpleado_id = intEmpleado_id;
    }

    public String getStrNombre() {
        return strNombre;
    }

    public void setStrNombre(String strNombre) {
        this.strNombre = strNombre;
    }

    public String getStrApellidos() {
        return strApellidos;
    }

    public void setStrApellidos(String strApellidos) {
        this.strApellidos = strApellidos;
    }

    public ByteArrayInputStream getByteDp() {
        return byteDp;
    }

    public void setByteDp(ByteArrayInputStream byteDp) {
        this.byteDp = byteDp;
    }

}
