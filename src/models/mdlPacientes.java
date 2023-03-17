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
public class mdlPacientes {
    public int intPacienteId=0;
    public String strPacienteNombre="";
    public String strPacienteApellido="";
    public ByteArrayInputStream byteDp=null;

    public int getIntPacienteId() {
        return intPacienteId;
    }

    public void setIntPacienteId(int intPacienteId) {
        this.intPacienteId = intPacienteId;
    }

    public String getStrPacienteNombre() {
        return strPacienteNombre;
    }

    public void setStrPacienteNombre(String strPacienteNombre) {
        this.strPacienteNombre = strPacienteNombre;
    }

    public String getStrPacienteApellido() {
        return strPacienteApellido;
    }

    public void setStrPacienteApellido(String strPacienteApellido) {
        this.strPacienteApellido = strPacienteApellido;
    }
    
    
    public ByteArrayInputStream getByteDp() {
        return byteDp;
    }

    public void setByteDp(ByteArrayInputStream byteDp) {
        this.byteDp = byteDp;
    }

}
