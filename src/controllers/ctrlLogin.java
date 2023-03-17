/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author rana
 */
public class ctrlLogin {
    static Connection con=(Connection)ctrlConexion.ctrConecta();
    static PreparedStatement ps=null;
    public static ResultSet getAreasAcceso()throws SQLException{
        ps=con.prepareStatement("SELECT * FROM tbl_areasacceso");
        ResultSet rs=ps.executeQuery();
        return rs;
    }

   public static ResultSet pIniciarSesion(String strId,String strMatricula)throws SQLException{
        ps=con.prepareStatement("SELECT * FROM tbl_empleados WHERE empleado_id=? AND empleado_matricula=?");
        
        ps.setString(1, strId);
        ps.setString(2, strMatricula);
        ResultSet rs=ps.executeQuery();
        return rs;
    }
}
