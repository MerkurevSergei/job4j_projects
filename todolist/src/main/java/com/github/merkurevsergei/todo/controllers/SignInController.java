package com.github.merkurevsergei.todo.controllers;

import com.github.merkurevsergei.todo.model.User;
import com.github.merkurevsergei.todo.store.Store;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * The {@code SingInController} user authentication controller.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public class SignInController extends HttpServlet {
    @Inject
    private Store store;

    /**
     * Perform user authentication.
     *
     * @param req  request from client.
     * @param resp response for client.
     * @throws IOException if I/O exception occur.
     */
    @Override
    protected void
    doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        final Optional<User> optionalUser = store.findUserByName(name);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(password)) {
                HttpSession sc = req.getSession();
                sc.setAttribute("user", user);
                resp.sendRedirect(req.getContextPath());
            } else {
                try (PrintWriter writer =
                             new PrintWriter(resp.getOutputStream(),
                                     true, StandardCharsets.UTF_8)) {
                    writer.println(new JSONObject().append("message", "pass invalid!").toString());
                }
            }
        } else {
            try (PrintWriter writer =
                         new PrintWriter(resp.getOutputStream(),
                                 true, StandardCharsets.UTF_8)) {
                writer.println(new JSONObject().append("message", "user invalid!").toString());
            }
        }
    }
}



