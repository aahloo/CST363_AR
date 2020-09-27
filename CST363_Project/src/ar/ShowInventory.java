package ar;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ShowInventory")
public class ShowInventory extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// SQL statements
	String sql = ("SELECT inventorydb.inventory_bins.parts_part_name AS Part, inventorydb.parts.part_description AS Description, sum(inventorydb.inventory_bins.bin_qty) AS Qty,\n" + 
       		"inventorydb.part_catagories.catagory_description AS Catagory\n" + 
       		"FROM inventorydb.inventory_bins\n" + 
       		"JOIN inventorydb.parts\n" + 
       		"ON inventorydb.inventory_bins.parts_part_name = inventorydb.parts.part_name\n" + 
       		"JOIN inventorydb.part_catagories\n" + 
       		"ON inventorydb.part_catagories.catagory_id = inventorydb.parts.part_catagories_catagory_id\n" + 
       		"GROUP BY inventorydb.inventory_bins.parts_part_name\n" + 
       		"ORDER BY 1");
	
	
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			        
			        try { 
			  
			            // Initialize the database 
			            Connection con = DatabaseConnection.initializeDatabase();
			            response.setContentType("text/html");  // Set response content type
			        	PrintWriter out = response.getWriter();
			        	PreparedStatement pstmt = null;
			            
			         // prepare sql select
						pstmt = con.prepareStatement(sql);
						ResultSet rs = pstmt.executeQuery();
						
						out.println("<!DOCTYPE HTML><html><body>");
						out.println("<table><tr><th>Part Name</th><th>Part Description</th><th>Part Quantity</th><th>Category</th></tr>");
						while (rs.next()) {
							out.println("<tr>");
							out.println("<td>"+rs.getString("Part")+"</td>");
							out.println("<td>"+rs.getString("Description")+"</td>");
							out.println("<td>"+rs.getString("Qty")+"</td>");
							out.println("<td>"+rs.getString("Catagory")+"</td>");
							out.println("</tr>");
						}
						rs.close();
						out.println("</table>");
						out.println("</body></html>");
						
						pstmt.close();
						con.close();
						out.flush();
					} catch (Exception e) {
						// Handle errors
						e.printStackTrace();
					} // end try
			            
			        
			     
			    }
			            

}
