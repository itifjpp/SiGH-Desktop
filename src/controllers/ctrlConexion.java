/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import java.io.*;
import java.sql.*;

/**
 * 
 * @author felipe de jesus <itifjpp@gmail.com>
 */
public class ctrlConexion {
    private static String strIp="",strPuerto="",strUser="",strPass="",strBd="";
    public static boolean leerIni(String strFile){
        try {
            FileReader reader=new FileReader("Config.ini");
            BufferedReader br=new BufferedReader(reader);
            String strLinea=br.readLine();
            while(strLinea!=null){
                int intStart=strLinea.indexOf("=");
                if(intStart>0){
                    String strCampo=strLinea.substring(0,intStart);
                    String strValor=strLinea.substring(intStart+1);
                    if(strCampo.equalsIgnoreCase("ip")){
                        strIp=strValor;
                    }if(strCampo.equalsIgnoreCase("puerto")){
                        strPuerto=strValor;
                    }if(strCampo.equalsIgnoreCase("usuario")){
                        strUser=strValor;
                    }if(strCampo.equalsIgnoreCase("contra")){
                        strPass=strValor;
                    }if(strCampo.equalsIgnoreCase("bd")){
                        strBd=strValor;
                    } 
                }
                strLinea=br.readLine();
            }
            reader.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static Connection ctrConecta(){
        leerIni("Config.ini");
        try {
            Class.forName("org.gjt.mm.mysql.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://"+strIp+":"+strPuerto +"/"+strBd,strUser, strPass);
            System.err.println("Conexión Exitosa");
            return con;
        } catch (Exception e) {
            System.out.print(e);
            e.printStackTrace();
            return null;
        }
    }
    public static Connection conecta(){
        try {
            Class.forName("org.gjt.mm.mysql.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/um_system","root", "");
            return con;
        } catch (Exception e) {
            System.out.print(e); 
            return null;
        }
    }
}
