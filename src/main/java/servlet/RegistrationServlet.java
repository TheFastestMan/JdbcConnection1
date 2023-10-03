package servlet;

import dto.UserDto;
import entity.Gender;
import entity.Role;
import entity.User;
import exception.ValidationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.UserService;
import util.JspHelper;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", Role.values());
        req.setAttribute("genders", Gender.values());
        req.getRequestDispatcher(JspHelper.getPath("registration")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("pwd");
        String role = req.getParameter("role");
        String gender = req.getParameter("gender");
        LocalDate birthday = null;
        try {
            String birthdayString = req.getParameter("birthday");
            if (birthdayString != null && !birthdayString.isEmpty()) {
                birthday = LocalDate.parse(birthdayString);
            }
        } catch (DateTimeParseException e) {
        }

        UserDto userDto = new UserDto(name, birthday, email, password, role, gender);


        boolean success = userService.registerUser(userDto);

        if (success) {
            resp.sendRedirect("successPage.jsp");
        } else {
            req.setAttribute("error", "Registration failed!");
            req.getRequestDispatcher(JspHelper.getPath("registration")).forward(req, resp);
        }
    }
}