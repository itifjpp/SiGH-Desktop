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
public class ctrlAbasto {
    static Connection con=(Connection)ctrlConexion.ctrConecta();
    static PreparedStatement ps=null;
    public static ResultSet getReporteConsumo(String strTemp)throws SQLException{
        ps=con.prepareStatement("SELECT * FROM abs_materiales, abs_reporte_consumo WHERE abs_materiales.material_id=abs_reporte_consumo.material_id AND abs_reporte_consumo.rc_temp=?");
        ps.setString(1, strTemp);
        ResultSet rs=ps.executeQuery();
        return rs;
    }
    public static ResultSet getMaterialExiste(String strMaterial)throws SQLException{
        ps=con.prepareStatement("SELECT * FROM abs_materiales WHERE abs_materiales.material_id=?");
        ps.setString(1, strMaterial);
        ResultSet rs=ps.executeQuery();
        return rs;
    }
    
    public static ResultSet getMaterialExisteReporte(int intMaterial)throws SQLException{
        ps=con.prepareStatement("SELECT * FROM abs_reporte_consumo WHERE abs_reporte_consumo.material_id=?");
        ps.setInt(1, intMaterial);
        ResultSet rs=ps.executeQuery();
        return rs;
    }
    public static boolean insertReporteConsumo(int intId, String strTemp, String strFecha, String strHora)throws SQLException{
        ps=con.prepareStatement("INSERT INTO abs_reporte_consumo VALUES(0,?,?,?,?,?)");
        ps.setString(1, strFecha);
        ps.setString(2, strHora);
        ps.setInt(3, intId);
        ps.setString(4, strTemp);
        ps.setInt(5, 0);
        int intInsert=ps.executeUpdate();
        
        if(intInsert>0){
            return true;
        }else{
            return false;
        }
    }
    public static boolean updateReporteConsumo(String strTemp,int intEmpleadoId)throws SQLException{
        ps=con.prepareStatement("UPDATE abs_reporte_consumo SET empleado_id=? WHERE rc_temp=?");
        ps.setInt(1, intEmpleadoId);
        ps.setString(2,strTemp);
        int intSql=ps.executeUpdate();
        con.close();
        ps.close();
        if(intSql>0){
            return true;
        }else{
            return false;
        }
    }
}
