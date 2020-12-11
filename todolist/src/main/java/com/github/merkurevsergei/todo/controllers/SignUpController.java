package com.github.merkurevsergei.todo.controllers;

import com.github.merkurevsergei.todo.model.User;
import com.github.merkurevsergei.todo.store.Store;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * The {@code SingUpController} user sign up controller.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public class SignUpController extends HttpServlet {
    @Inject
    private Store store;

    /**
     * Add user to database and redirect to {@code SignInController}.
     *
     * @param req  request from client.
     * @param resp response for client.
     * @throws ServletException if Servlet exception occur.
     * @throws IOException      if I/O exception occur.
     */
    @Override
    protected void
    doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String name = req.getParameter("name");
        String password = req.getParameter("password");
        User user = new User(0, name, password);
        if (!exist(user)) {
            store.save(user);
            req.getRequestDispatcher("/sign-in").forward(req, resp);
        } else {
            try (PrintWriter writer =
                         new PrintWriter(resp.getOutputStream(), true, StandardCharsets.UTF_8)) {
                writer.println(new JSONObject()
                        .append("msg", "user already exist!")
                        .toString()
                );
            }
        }
    }

    private boolean exist(User user) {
        String name = user.getName();
        return store.findUserByName(name).isPresent();
    }

}
