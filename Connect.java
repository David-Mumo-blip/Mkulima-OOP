import java.sql.*;
  //File name Connect.java 
  public class Connect {    public static void main(String[] args) throws SQLException 
  {String url = "jdbc:mysql://localhost:3306/school";      
  String user = "root";       
  String password = "";
  Connection conn = DriverManager.getConnection ( url, user,  password);        
  System.out.println("Connected!"); 
  conn.close(); 
  }
  }