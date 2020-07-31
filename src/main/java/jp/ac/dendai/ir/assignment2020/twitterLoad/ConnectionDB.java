package jp.ac.dendai.ir.assignment2020.twitterLoad;
/*
 * Twitterからデータを取得するプログラムについては下記サイトのコードを引用・参考にしています。
 * https://qiita.com/michiruFX105/items/d860fac602ab78772942
 *
 * */

import java.util.Properties;
import java.io.FileInputStream;
import java.io.InputStream;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    public static void main(String[] args) {
        ConnectionDB connectionDB = new ConnectionDB();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = connectionDB.getConnection();
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e3) {
            e3.printStackTrace();
        }
    }

    public Connection getConnection() throws ClassNotFoundException {

        //外部ファイルから認証情報を取得
        Properties prop = new Properties();
        String file = "config.properties";
        try {
            InputStream is = new FileInputStream(file);
            prop.load(is);
            is.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //PostgreSQLの接続準備他
        String driverClassName = "org.postgresql.Driver";
        String url = "jdbc:postgresql://ec2-34-236-215-156.compute-1.amazonaws.com/d8kt6miao94dup";
        String user = prop.getProperty("usr");
        String password = prop.getProperty("pass");
        Connection connection;
       //Statement statement;
        String sql = "SELECT * FROM tweet2";

        try {
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(url, user, password);
           // statement = connection.createStatement();
           // showTable(statement.executeQuery(sql)); //  1.

          //  statement.executeUpdate( "INSERT INTO address VALUES('鈴木', 'Tokyo', '0120-00-0000', 'suzuki@tokyo')"); //  2.
          //  showTable(statement.executeQuery(sql)); //  3.

           // statement.executeUpdate( "UPDATE address SET tel = '03-333-3333' WHERE address = 'Tokyo'"); //  4.
          //  showTable(statement.executeQuery(sql)); //  5.

           // statement.executeUpdate( "DELETE FROM address WHERE email = 'suzuki@tokyo'"); //  6.
           // showTable(statement.executeQuery(sql)); //  7.

           // statement.close();
           // connection.close();
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* source
        try {

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/twitter?serverTimezone=JST", "user", "password");

            return connection;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        */

        return null;
    }
}
