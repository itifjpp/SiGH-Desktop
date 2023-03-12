/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import models.mdlEmpleados;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author rana
 */
public class ctrlEmpleados {
    static Connection con=(Connection)ctrlConexion.ctrConecta();
    static PreparedStatement ps=null;
    public static ResultSet getEmpleado(int intId)throws SQLException{
        ps=con.prepareStatement("SELECT * FROM tbl_empleados WHERE empleado_id=?");
        ps.setInt(1, intId);
        ResultSet rs=ps.executeQuery();
        return rs;
    }
    public static boolean updateEmpleado(mdlEmpleados e)throws SQLException{
        ps=con.prepareStatement("UPDATE tbl_empleados SET empleado_dp=? WHERE empleado_id=?");
        ps.setBinaryStream(1, e.getByteDp());
        ps.setInt(2,e.getIntEmpleado_id());
        int intSql=ps.executeUpdate();
        con.close();
        ps.close();
        if(intSql>0){
            return true;
        }else{
            return false;
        }
    }
    public static ResultSet getEmpleado(String strMatricula)throws SQLException{
        ps=con.prepareStatement("SELECT * FROM tbl_empleados WHERE empleado_matricula=?");
        ps.setString(1, strMatricula);
        ResultSet rs=ps.executeQuery();
        return rs;
    }
    public static ResultSet getEmpleados()throws SQLException{
        ps=con.prepareStatement("SELECT * FROM tbl_empleados");
        ResultSet rs=ps.executeQuery();
        return rs;
    }
    public static ResultSet getEmpleadoLike(String strCampo,String strAttribute)throws SQLException{
        ps=con.prepareStatement("SELECT * FROM tbl_empleados WHERE "+strAttribute+" LIKE (?)");
        ps.setString(1, "%"+strCampo+"%");
        ResultSet rs=ps.executeQuery();
        return rs;
    }
}
