package application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class jdbc1
{
  public static void main(String[] args)
  {
    Connection connection = null;
    try
    {
      // create a database connection
      
      connection = DriverManager.getConnection("jdbc:sqlite:task_db.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.
      statement.executeUpdate("drop table if exists task_db");
      statement.executeUpdate("create table task_db (mission string, place string, hr1 string, mn1 string, hr2 string, mn2 string, is_deleted integer, color string)");
      statement.executeUpdate("insert into task_db values('eat icecream', 'classroom 306', '17', '00', '18', '00', 0, '#07447D')");
      statement.executeUpdate("insert into task_db values('coding', 'classroom 207', '17', '30', '19', '00', 0, '#1C9B83')");
      ResultSet rs = statement.executeQuery("select * from task_db");
      while(rs.next())
      {
        // read the result set
        System.out.println("mission = " + rs.getString("mission"));
        System.out.println("place = " + rs.getString("place"));
        System.out.println("hr1 = " + rs.getString("hr1"));
        System.out.println("mn1 = " + rs.getString("mn1"));
        System.out.println("hr2 = " + rs.getString("hr2"));
        System.out.println("mn2 = " + rs.getString("mn2"));
        System.out.println("is_deleted = " + rs.getInt("is_deleted"));
        System.out.println("color = " + rs.getString("color"));
      }
    }
    catch(SQLException e)
    {
      // if the error message is "out of memory",
      // it probably means no database file is found
      System.err.println(e.getMessage());
    }
    finally
    {
      try
      {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e)
      {
        // connection close failed.
        System.err.println(e.getMessage());
      }
    }
  }
}
