package sourse.outils;

import java.sql.*;

public class DB {
    private Connection cnx;
    private PreparedStatement pstmt;
    private void openConnection()
    {
        try {

                Class.forName("com.mysql.cj.jdbc.Driver");

                String url="jdbc:mysql://localhost:3306/automobile ?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                String usr="root",pwd="";
                cnx= DriverManager.getConnection(url,usr,pwd);

        } catch (Exception ex)
        {
            ex.printStackTrace();

        }
    }
    public void preparedStatement(String sql)
    {
        try
        {
            openConnection();
            pstmt=cnx.prepareStatement(sql);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public ResultSet executeQuery()
    {
        try {

            return pstmt.executeQuery();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    public int executUpdate(String sql)
    {
        try
        {
            return pstmt.executeUpdate();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return 0;
    }
    public void closConnection()
    {
        try
        {
            if (pstmt !=null && !pstmt.isClosed())
            {
                pstmt.close();
            }
            if (cnx !=null && !cnx.isClosed())
            {
                pstmt.close();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            //System.out.println(ex.getMessage());
        }

    }
    public PreparedStatement getPstmt() throws SQLException {
        return pstmt;
       
    }
}
