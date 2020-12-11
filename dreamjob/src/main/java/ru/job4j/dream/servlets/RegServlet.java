package ru.job4j.dream.servlets;

import ru.job4j.dream.model.User;
import ru.job4j.dream.store.PsqlStore;
import ru.job4j.dream.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("reg.jsp").forward(req, resp);
    }

    @Override
    protected void
    doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Store store = PsqlStore.instOf();
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if (isValidName(name) && isValidEmail(email) && isValidPassword(password)
                && store.findUserByEmail(email).isEmpty()) {
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            store.save(user);
            req.getRequestDispatcher("auth.do").forward(req, resp);
        } else {
            req.setAttribute("error", "Не верное имя, email или пароль");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        }

    }

    private boolean isValidName(String name) {
        name = Optional.ofNullable(name).orElse("");
        return !name.isBlank();
    }

    private boolean isValidPassword(String password) {
        password = Optional.ofNullable(password).orElse("");
        return !password.isBlank();
    }

    private boolean isValidEmail(String email) {
        email = Optional.ofNullable(email).orElse("");
        Pattern emailRegex = Pattern.compile(
                "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE
        );
        Matcher matcher = emailRegex.matcher(email);
        return matcher.find();
    }
}
