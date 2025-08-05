// JDBC or java database connectivity is an api which is used to connect to a database from a java program

// 1.import sql
import java.sql.*;
public class jdbc {
    public static void main(String[] args) throws SQLException
    {
        System.out.println("This is a jdbc program");
        Connection con=null;
        Statement st1 = null; 
        PreparedStatement st2 = null; 
        //  forName throws class not found exception and most methods in jdbc throw sql exception so use try-catch
        try {
            /*2. get class from vendor and load driver class ,
                 add the mysql-connector-java-8.0.30.jar file to the referenced libraries
                 jar or java archive is a .class files in compressed format*/
            Class.forName("com.mysql.cj.jdbc.Driver");
            /*3. Establish connection (Connection is an interface in .sql package)
                 getConnection is a static method
                 provide localhost/IP address(for remote computer), db_name,username,password 
                 sql is running at port 3306(localhost)*/
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/college", "root", "P@$$w0rd");
            System.out.println("Connection established");

            /* 4. create statement : use interface createStatement or prepareStatement */
            st1 = con.createStatement();

            /*5. Execute : boolean ececute() for DDL and dyamic sql (true if return is a resultSet like in select, else false for create, drop, insert, update, delete), 
                 int executeUpdate() for DML like insert, update, delete, sometimes used with create, drop. exception if used with select
                 ResultSet executeQuery() for DQL like select, throws exception if used with non query like drop, insert, update, delete
                 we can use any method to execute any SQL command but to manage the return type we need to use specific method */
            System.out.println(st1.execute("create table  if not exists fees (id int, course_name varchar(10), fees int)")); //false
            System.out.println(st1.executeUpdate("insert into fees values(1, 'Computer', 5000),(2, 'English', 6000),(3, 'Maths', 7000)"));//3(no of rows affected)
            ResultSet r= st1.executeQuery("select * from fees");
            while(r.next()) {
                System.out.println("ID: " + r.getInt("id") + ", Course Name: " + r.getString(2) + ", Fees: " + r.getInt(3));
            }

            st2 = con.prepareStatement("insert into fees values(?,?,?)"); // dynamic ie. we can pass value to SQL command during runtime
            st2.setInt(1,4);
            st2.setString(2, "Science");
            st2.setInt(3, 8000);
            System.out.println(st2.executeUpdate());//1
        } catch (Exception e) {
            System.out.println(e);
        }
        // 6. close the statement and the connection even if the error occurs
        // close method throws SQLException so catch it in the main method 
        finally         
        {   
            Statement st3 = con.createStatement();
            System.out.println(st3.execute("drop table if exists fees"));//false

            st1.close();
            st2.close(); 
            st3.close();  

            con.close();
       } 
    }
}
