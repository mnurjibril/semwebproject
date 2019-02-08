/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package semwebclass;

import com.hp.hpl.jena.db.DBConnection;
import com.hp.hpl.jena.db.IDBConnection;

/**
 *
 * @author tarex
 */
public class ConnectToDB {
    
    static String DB_URL = "JDBC:mysql://localhost:3303/confMan_db";
    static String DB_USER ="root";
    static String DB_PASSWD = "";
    static String DB_NAME = "confMan_db";
    public static String DB_DRIVER = "com.mysql.jdbc.Driver";
    
    public static IDBConnection connectDB(){
        return new DBConnection(DB_URL, DB_USER, DB_PASSWD, DB_NAME);
    }
    
}
