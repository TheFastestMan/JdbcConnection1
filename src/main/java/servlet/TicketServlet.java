package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.TicketService;
import util.JspHelper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/tickets")
public class TicketServlet extends HttpServlet {

    private final TicketService ticketService = TicketService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long flightId = Long.valueOf(req.getParameter("flightId"));

        req.setAttribute("tickets",ticketService.findAllByFlightId(flightId));
        req.getRequestDispatcher(JspHelper.getPath("tickets")).forward(req,resp );


    }
}