package ar;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UpdateInventory")
public class UpdateInventory extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// database URL
		static final String DB_URL = "jdbc:mysql://localhost:3306/inventorydb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

		// Database credentials
		static final String USER = "root";
		static final String PASS = "N!tr0gen2O2**";
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// SQL statements
		String sql = "SELECT parts_part_name, bin_qty from inventory_bins where parts_part_name = ?";
		String usql = "UPDATE inventory_bins\n" + 
						"SET bin_qty = ?\n" + 
						"WHERE parts_part_name = ?";
		
		response.setContentType("text/html"); // Set response content type
		PrintWriter out = response.getWriter();
		
		
		try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS)) {
			
			// Initialize the database 
            
            
			
			// get input data from form
			String part_name = request.getParameter("part");
			
			     
						
			 // get data from form and convert to integer values
						
			 int new_qty = Integer.parseInt(request.getParameter("bin_quantity"));

			 // prepare sql select
			 PreparedStatement pstmt =  con.prepareStatement(usql);
			 pstmt.setInt(1, new_qty);
			 pstmt.setString(2, part_name);
						
						
			 int row_count = pstmt.executeUpdate();
						
			 pstmt = con.prepareStatement(sql);
			 pstmt.setString(1, part_name);
			 ResultSet rs = pstmt.executeQuery();

			 out.println("<!DOCTYPE HTML><html><body>");
			 out.println("<p>Rows updated = " + row_count + "</p>");
			 out.println("<table> <tr><th>Part Name</th><th>Bin Quantity</th></tr>");
			 while (rs.next()) {
				 out.println("<tr>");
				 out.println("<td>" + rs.getString("parts_part_name") + "</td>");
				 out.println("<td>" + rs.getString("bin_qty") + "</td>");
				 out.println("</tr>");
			 }
			 
			 rs.close();
			 out.println("</table>");
			 out.println("</body></html>");
		} catch (SQLException e) {
				// Handle errors
				 e.printStackTrace();
		}  
		
	
	
	}
	
}
