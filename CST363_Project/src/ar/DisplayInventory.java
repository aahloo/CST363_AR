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

/**
 * Servlet implementation class DisplayInventory
 */
@WebServlet("/DisplayInventory")
public class DisplayInventory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request,  
			HttpServletResponse response) 
			        throws ServletException, IOException 
			    { 
			        try { 
			  
			            // Initialize the database 
			            Connection con = DatabaseConnection.initializeDatabase(); 
			  
			            // Create a SQL query to insert data into demo table 
			            // Ex: table consists of two columns, so two '?' is used 
			            PreparedStatement st1 = con 
			                   .prepareStatement("SELECT parts_part_name AS Part, sum(bin_qty) AS Qty,\n" + 
			                   		"catagory_description AS Catagory\n" + 
			                   		"FROM inventorydb.inventory_bins\n" + 
			                   		"JOIN inventorydb.parts\n" + 
			                   		"ON parts_part_name = part_name\n" + 
			                   		"JOIN inventorydb.part_catagories\n" + 
			                   		"ON part_catagories_catagory_id = catagory_id\n" + 
			                   		"GROUP BY parts_part_name\n" + 
			                   		"ORDER BY 1");
			            
			            
			            st1.close();
			           
			            con.close(); 
			  
			            // Get a writer pointer  
			            // to display the successful result 
			            
			           
			        }
			        catch (Exception e) { 
			            e.printStackTrace(); 
			        }
			    }

}
