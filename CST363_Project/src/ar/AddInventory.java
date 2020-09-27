package ar;

import java.io.IOException; 
import java.io.PrintWriter; 
import java.sql.Connection; 
import java.sql.PreparedStatement; 
  
import javax.servlet.ServletException; 
import javax.servlet.annotation.WebServlet; 
import javax.servlet.http.HttpServlet; 
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse; 
  
// Import Database Connection Class file 
import ar.DatabaseConnection; 
  
// Servlet Name 
@WebServlet("/AddInventory") 
public class AddInventory extends HttpServlet { 
    private static final long serialVersionUID = 1L; 
  
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException 
    { 
        try { 
  
            // Initialize the database 
            Connection con = DatabaseConnection.initializeDatabase(); 

            // Create a SQL query to insert data into demo table 
            // Ex: table consists of two columns, so two '?' is used 
            PreparedStatement st1 = con 
                   .prepareStatement("insert into inventorydb.parts (part_name, part_description, part_catagories_catagory_id) values (?, ?, ?)");
            
            PreparedStatement st2 = con 
                    .prepareStatement("insert into inventorydb.inventory_bins (bin_qty, parts_part_name) values (?, ?)");
                       
            
            // For the first parameter, 
            // get the data using request object 
            // sets the data to st pointer 
            st1.setString(1, request.getParameter("partName"));
            st1.setString(2, request.getParameter("partDesc"));
            st1.setString(3, request.getParameter("partCategoryID"));
            
            st2.setString(1, request.getParameter("binQuantity"));
            st2.setString(2, request.getParameter("partName"));
           
                        
  
            // Execute the insert command using executeUpdate() 
            // to make changes in database 
            st1.executeUpdate();
            st2.executeUpdate();
             
            // Close all the connections 
            st1.close();
            st2.close();
       
            con.close(); 
  
            // Get a writer pointer  
            // to display the successful result 
            PrintWriter out = response.getWriter(); 
            out.println("<html><body><b>Successfully Added to Inventory"
                        + "</b></body></html>"); 
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
    } 
} 
