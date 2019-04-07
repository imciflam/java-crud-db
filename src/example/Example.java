/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Vitalia
 */
public class Example {

    /**
     * @param args the command line arguments
     */
    
    
    private static String dbURL = "jdbc:derby://localhost:1527/task";
    private static String tableName = "aud";
    // jdbc Connection
    private static Connection conn = null;
    private static Statement stmt = null;

    public static void main(String[] args)  
    {
        while(true)
        {
        System.out.println("This is DB console client");
        System.out.println("If you want to add new column 'HUMAN', press 1");
        System.out.println("If you want to update  column 'HUMAN', press 2"); 
        System.out.println("If you want to delete column from 'HUMAN', press 3");
        System.out.println("If you want to look at 'HUMAN', press 4");
        System.out.println("If you want to add new column 'AUD', press 5");
        System.out.println("If you want to update  column 'AUD', press 6"); 
        System.out.println("If you want to delete column from 'AUD', press 7");
        System.out.println("If you want to look at 'AUD', press 8");
        System.out.println("If you want to look up average age, 9");
        System.out.println("If you want to look up square assigned to 1 human, 10");
        Scanner sc = new Scanner(System.in);
        String choice = sc.next();
        System.out.println(choice); 
        createConnection();
        switch(choice)
        {
            case "1":
                System.out.println("Enter new data for table");
                System.out.println("Example: 98-SSV-phd-40-5553535");
                Scanner sc1 = new Scanner(System.in);
                String choice1 = sc1.next();
                insert(choice1);
                break;
             case "2":
                System.out.println("Enter new data for table");
                System.out.println("Example: 98-SSV-phd-40-5553535");
                Scanner sc2 = new Scanner(System.in);
                String choice2 = sc2.next();
                update(choice2);
                break;
            case "3":
                System.out.println("Enter id which to delete");
                System.out.println("Example: 666");
                Scanner sc3 = new Scanner(System.in);
                String choice3 = sc3.next();
                delete(choice3);
                break;
            case "4":
                select();
                break;
             case "5":
                System.out.println("Enter new data for table");
                System.out.println("Example: 99-14-5-333-1");
                Scanner sc5 = new Scanner(System.in);
                String choice5 = sc5.next();
                insertAud(choice5);
                break;
             case "6":
                System.out.println("Enter new data for table");
                System.out.println("Example: 99-14-5-333-1");
                System.out.println("Warning - foreignkey should not be violated");
                Scanner sc6 = new Scanner(System.in);
                String choice6 = sc6.next();
                updateAud(choice6);
                break;
            case "7":
                System.out.println("Enter id which to delete");
                System.out.println("Example: 666");
                Scanner sc7 = new Scanner(System.in);
                String choice7 = sc7.next();
                deleteAud(choice7);
                break;
            case "8":
                selectAud();
                break;   
             case "9":
                findAge();
                break;  
                 case "10":
                 System.out.println("Enter humkey");
                System.out.println("Example: 1");
                Scanner sc10 = new Scanner(System.in);
                String choice10 = sc10.next();
                findSquare(choice10);
                break; 
                
        }
          
        //insertRestaurants();
        // TODO code application logic here
        }
    }
    private static void createConnection()
    {
        try
        {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            //Get a connection
            conn = DriverManager.getConnection(dbURL); 
        }
        catch (Exception except)
        {
            except.printStackTrace();
        }
    }
    
    private static void insert(String data)
    {
        try
        {
            ArrayList<String> inputdata = new ArrayList<>();
 
             for (String retval : data.split("-")) {
             inputdata.add(retval);
            }  
            
            stmt = conn.createStatement();
            stmt.execute("INSERT INTO HUMAN (HUMKEY, HUMNAME, HUMJOB, HUMAGE, HUMPHONE) VALUES ("+ Integer.valueOf(inputdata.get(0)) 
                    +", '"+ (inputdata.get(1)) +
                    "', '" + (inputdata.get(2))+  
                    "', " + Integer.valueOf(inputdata.get(3))+ 
                    ", " + Integer.valueOf(inputdata.get(4))+")");

            //stmt.execute("INSERT INTO AUD (AUDKEY, AUDSQUARE, AUDBUILDING, AUDNUM, HUMKEY) VALUES (2, 30, 7, 335, 1)");
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }
    
    private static void update(String data)
    {
        try
        {
            ArrayList<String> inputdata = new ArrayList<>();
 
             for (String retval : data.split("-")) {
             inputdata.add(retval);
            }  
            
            stmt = conn.createStatement();
            stmt.execute("UPDATE HUMAN SET HUMNAME = '" + (inputdata.get(1))+"',HUMJOB = '"+(inputdata.get(2)) +"',HUMAGE= "+Integer.valueOf(inputdata.get(3))+", HUMPHONE="+Integer.valueOf(inputdata.get(4))+ " WHERE humkey ="+Integer.valueOf(inputdata.get(0)));

            //stmt.execute("INSERT INTO AUD (AUDKEY, AUDSQUARE, AUDBUILDING, AUDNUM, HUMKEY) VALUES (2, 30, 7, 335, 1)");
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }
    
    
    private static void delete(String data)
    {
        try
        {
            ArrayList<String> inputdata = new ArrayList<>();
 
             for (String retval : data.split("-")) {
             inputdata.add(retval);
            }  
            
            stmt = conn.createStatement();
            stmt.execute("DELETE FROM HUMAN WHERE HUMKEY ="+Integer.valueOf(inputdata.get(0)));

            //stmt.execute("INSERT INTO AUD (AUDKEY, AUDSQUARE, AUDBUILDING, AUDNUM, HUMKEY) VALUES (2, 30, 7, 335, 1)");
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }
    
    private static void select()
    {
        try
        {
            stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("SELECT * FROM HUMAN ORDER BY HUMKEY ASC");
            ResultSetMetaData rsmd = results.getMetaData();
            int numberCols = rsmd.getColumnCount();
            for (int i=1; i<=numberCols; i++)
            {
                //print Column Names
                System.out.print(rsmd.getColumnLabel(i)+"\t\t\t");  
            }

            System.out.println("\n--------------------------------------------------------------------------");

            while(results.next())
            {
                int audkey = results.getInt(1);
                String audsquare = results.getString(2);
                String audbuilding = results.getString(3);
                String audnum = results.getString(4);
                String humkey = results.getString(5); 
                System.out.println(audkey + "\t\t\t" + audsquare + "\t\t\t" + audbuilding + "\t\t\t" + audnum + "\t\t\t" + humkey);
            }
            results.close();
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }
 
 
     private static void insertAud(String data)
    {
        try
        {
            ArrayList<String> inputdata = new ArrayList<>();
 
             for (String retval : data.split("-")) {
             inputdata.add(retval);
            }  
            
            stmt = conn.createStatement();
            stmt.execute("INSERT INTO AUD (AUDKEY, AUDSQUARE, AUDBUILDING, AUDNUM, HUMKEY) VALUES ("+ Integer.valueOf(inputdata.get(0)) 
                    +", "+ Integer.valueOf(inputdata.get(1)) +
                    ", " + Integer.valueOf(inputdata.get(2))+  
                    ", " + Integer.valueOf(inputdata.get(3))+ 
                    ", " + Integer.valueOf(inputdata.get(4))+")");

            //stmt.execute("INSERT INTO AUD (AUDKEY, AUDSQUARE, AUDBUILDING, AUDNUM, HUMKEY) VALUES (2, 30, 7, 335, 1)");
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }
    
    private static void updateAud(String data)
    {
        try
        {
            ArrayList<String> inputdata = new ArrayList<>();
 
             for (String retval : data.split("-")) {
             inputdata.add(retval);
            }  
            
            stmt = conn.createStatement();
            stmt.execute("UPDATE AUD SET AUDSQUARE = " + Integer.valueOf(inputdata.get(1))+",AUDBUILDING = "+Integer.valueOf(inputdata.get(2)) +",AUDNUM= "+Integer.valueOf(inputdata.get(3))+", HUMKEY="+Integer.valueOf(inputdata.get(4))+ " WHERE AUDKEY ="+Integer.valueOf(inputdata.get(0)));

            //stmt.execute("INSERT INTO AUD (AUDKEY, AUDSQUARE, AUDBUILDING, AUDNUM, HUMKEY) VALUES (2, 30, 7, 335, 1)");
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }
    
    
    private static void deleteAud(String data)
    {
        try
        {
            ArrayList<String> inputdata = new ArrayList<>();
 
             for (String retval : data.split("-")) {
             inputdata.add(retval);
            }  
            
            stmt = conn.createStatement();
            stmt.execute("DELETE FROM AUD WHERE AUDKEY ="+Integer.valueOf(inputdata.get(0)));

            //stmt.execute("INSERT INTO AUD (AUDKEY, AUDSQUARE, AUDBUILDING, AUDNUM, HUMKEY) VALUES (2, 30, 7, 335, 1)");
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }
    
    private static void selectAud()
    {
        try
        {
            stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("SELECT * FROM AUD ORDER BY AUDKEY ASC");
            ResultSetMetaData rsmd = results.getMetaData();
            int numberCols = rsmd.getColumnCount();
            for (int i=1; i<=numberCols; i++)
            {
                //print Column Names
                System.out.print(rsmd.getColumnLabel(i)+"\t\t\t");  
            }

            System.out.println("\n--------------------------------------------------------------------------");

            while(results.next())
            {
                int audkey = results.getInt(1);
                String audsquare = results.getString(2);
                String audbuilding = results.getString(3);
                String audnum = results.getString(4);
                String humkey = results.getString(5); 
                System.out.println(audkey + "\t\t\t" + audsquare + "\t\t\t" + audbuilding + "\t\t\t" + audnum + "\t\t\t" + humkey);
            }
            results.close();
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }
    
    private static void findAge()
    {
        try
        {
            stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("SELECT AVG(HUMAGE) FROM HUMAN");
            ResultSetMetaData rsmd = results.getMetaData(); 
            System.out.println("\n--------------------------------------------------------------------------");

            while(results.next())
            {
                int audkey = results.getInt(1); 
                System.out.println(audkey + "\t\t\t ");
            }
            results.close();
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }
    
        private static void findSquare(String humkey)
    {
        try
        {
            Integer.valueOf(humkey);
            stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("SELECT SUM(AUDSQUARE) FROM AUD INNER JOIN HUMAN ON AUD.HUMKEY = HUMAN.HUMKEY WHERE HUMAN.HUMKEY ="+Integer.valueOf(humkey));
            ResultSetMetaData rsmd = results.getMetaData(); 
            System.out.println("\n--------------------------------------------------------------------------");

            while(results.next())
            {
                int audkey = results.getInt(1); 
                System.out.println(audkey + "\t\t\t ");
            }
            results.close();
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }
    
}
