

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet("/dummyServlet")
public class DummyServlet extends HttpServlet {
	
	@Override 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("hola mundo desde servlets");
	}
	
}
