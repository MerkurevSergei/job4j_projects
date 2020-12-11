package com.github.merkurevsergei.todo.controllers;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The {@code SingOutController} user sign out controller.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public class SignOutController extends HttpServlet {

    /**
     * Perform user session invalidate.
     *
     * @param req  request from client.
     * @param resp response for client.
     * @throws IOException if I/O exception occur.
     */
    @Override
    protected void
    doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath());
    }
}
