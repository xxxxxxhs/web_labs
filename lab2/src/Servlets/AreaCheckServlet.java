package Servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/checkArea")
public class AreaCheckServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();

        List<Result> results = (List<Result>) context.getAttribute("results");
        if (results == null) {
            results = new ArrayList<>();
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Преобразуем список результатов в JSON
        PrintWriter out = response.getWriter();
        out.println("[");
        for (int i = 0; i < results.size(); i++) {
            Result result = results.get(i);
            out.println("{");
            out.println("\"x\": " + result.getX() + ",");
            out.println("\"y\": " + result.getY() + ",");
            out.println("\"r\": " + result.getR() + ",");
            out.println("\"hit\": \"" + result.getHit() + "\"");
            if (i < results.size() - 1) {
                out.println("},");
            } else {
                out.println("}");
            }
        }
        out.println("]");
        out.close();
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {

            double x = Double.parseDouble(request.getParameter("x"));
            double y = Double.parseDouble(request.getParameter("y"));
            double r = Double.parseDouble(request.getParameter("r"));

            String result = check(x, y, r) ? "hit" : "miss";

            ServletContext context = getServletContext();
            List<Result> results = (List<Result>) context.getAttribute("results");
            if (results == null) {
                results = new ArrayList<>();
            }

            Result newResult = new Result(x, y, r, result);
            results.add(newResult);

            context.setAttribute("results", results);

            request.setAttribute("results", results);
            request.getRequestDispatcher("/full_results.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            out.println("<html><body>");
            out.println("<h2>Error: Invalid input format. Please enter numeric values.</h2>");
            out.println("<br/><a href='/index.jsp'>Go back to form</a>");
            out.println("</body></html>");
        } finally {
            out.close();
        }
    }

    private boolean check(double x, double y, double r) {
        if (x >= 0 && y >= 0 && x*x + y*y <= r*r) {return true;}
        if (x <= 0 && y >= 0 && y <= r && x >= -r) {return true;}
        if (x <= 0 && y <= 0 && y >= -x - r) {return true;}
        return false;
    }
}
