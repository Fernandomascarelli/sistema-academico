package persistencia;

import java.sql.*;

public class DatabaseConnection {

    private static Connection conn = null;

    private static void criarConexao() {
        String url = "jdbc:mysql://localhost:3306/trabfinal";
        String user = "root";
        String pass = "1234";

        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Conexao realizada!");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Falha ao criar conexao com o banco de dados\n"
                    + e.getMessage());
        }

    }
    
    public static Connection getConnection()
    {
        try {
            if(conn==null || conn.isClosed()){
                criarConexao();
            }
            return conn;
        } catch (SQLException ex) {
            System.out.println("Falha ao obter conexao!\n"+ex.getMessage());
            return null;
        }
    }

}
