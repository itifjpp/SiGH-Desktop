/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import models.mdlPacientes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author rana
 */
public class ctrlPacientes {
    static Connection con=(Connection)ctrlConexion.ctrConecta();
    static PreparedStatement ps=null;
    public static ResultSet getPaciente(int intId)throws SQLException{
        ps=con.prepareStatement("SELECT * FROM tbl_pacientes WHERE paciente_id=?");
        ps.setInt(1, intId);
        ResultSet rs=ps.executeQuery();
        return rs;
    }
    public static boolean updatePaciente(mdlPacientes e)throws SQLException{
        ps=con.prepareStatement("UPDATE tbl_pacientes SET paciente_dp=? WHERE paciente_id=?");
        ps.setBinaryStream(1, e.getByteDp());
        ps.setInt(2,e.getIntPacienteId());
        int intSql=ps.executeUpdate();
        ps.close();
        if(intSql>0){
            return true;
        }else{
            return false;
        }
    }
    public static ResultSet getPaciente(String strMatricula)throws SQLException{
        ps=con.prepareStatement("SELECT * FROM tbl_pacientes WHERE paciente_id=?");
        ps.setString(1, strMatricula);
        ResultSet rs=ps.executeQuery();
        return rs;
    }
    public static ResultSet getPacientes()throws SQLException{
        ps=con.prepareStatement("SELECT * FROM tbl_pacientes ORDER BY paciente_id LIMIT 100");
        ResultSet rs=ps.executeQuery();
        return rs;
    }
    public static ResultSet getPacientesLike(String strCampo,String strAttribute)throws SQLException{
        ps=con.prepareStatement("SELECT * FROM tbl_pacientes WHERE "+strAttribute+" LIKE (?)");
        ps.setString(1, "%"+strCampo+"%");
        ResultSet rs=ps.executeQuery();
        return rs;
    }
    public static ResultSet getPacienteRegistros(int intPaciente)throws SQLException{
        ps=con.prepareStatement("SELECT * FROM tbl_pacientes WHERE paciente_id=?");
        ps.setInt(1, intPaciente);
        ResultSet rs=ps.executeQuery();
        return rs;
    }
   public static boolean registrarIngreso(int intPacienteId)throws SQLException{
        ps=con.prepareStatement("INSERT INTO tbl_pacientes SET paciente_dp=? WHERE paciente_id=?");

        ps.setInt(1,intPacienteId);
        int intSql=ps.executeUpdate();
        ps.close();
        if(intSql>0){
            return true;
        }else{
            return false;
        }
    }
    public static ResultSet getPacientesCalendario(int intPaciente)throws SQLException{
        ps=con.prepareStatement("SELECT c.*\n" +
                                "FROM tbl_calendario AS c\n" +
                                "INNER JOIN tbl_pacientes_ingresos AS ing ON ing.ingreso_id=c.ingreso_id\n" +
                                "INNER JOIN tbl_pacientes AS p ON p.paciente_id=ing.ingreso_id\n" +
                                "WHERE \n" +
                                "   p.paciente_id=?\n" +
                                "ORDER BY\n" +
                                "   c.calendario_fi DESC");
        ps.setInt(intPaciente, 1); 
        ResultSet rs=ps.executeQuery();
        return rs;
    } 
    public static boolean pPacienteConfirmarCalendario(String idCalendario)throws SQLException{
        ps=con.prepareStatement("UPDATE tbl_calendario SET calendario_estatus='Confirmado', calendario_f_llegada=NOW(), calendario_h_llegada=NOW() WHERE calendario_id=?");
        ps.setString(1,idCalendario);
        int intSql=ps.executeUpdate();
        ps.close();
        if(intSql>0){
            return true;
        }else{
            return false;
        }
    }
}
