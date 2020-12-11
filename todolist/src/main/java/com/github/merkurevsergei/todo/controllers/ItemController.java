package com.github.merkurevsergei.todo.controllers;

import com.github.merkurevsergei.todo.model.Item;
import com.github.merkurevsergei.todo.model.User;
import com.github.merkurevsergei.todo.store.Store;
import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * The {@code ItemController} process requests to retrieve, insert and edit task.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public class ItemController extends HttpServlet {
    @Inject
    private Store store;

    /**
     * Retrieve items for client.
     *
     * @param req  request from client.
     * @param resp response for client.
     * @throws IOException if I/O exception occur.
     */
    @Override
    protected void
    doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        List<Item> items = "true".equals(req.getParameter("withCompleted"))
                ? store.findItems(user)
                : store.findItems(user, false);
        JSONArray ja = new JSONArray(items.toArray());
        try (PrintWriter writer =
                     new PrintWriter(resp.getOutputStream(), true, StandardCharsets.UTF_8)) {
            writer.println(ja.toString());
        }
    }

    /**
     * Add or update item.
     *
     * @param req  request from client.
     * @param resp response for client.
     * @throws IOException if I/O exception occur.
     */
    @Override
    protected void
    doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if ("update".equals(req.getParameter("method"))) {
            send(req, resp, (request, response) -> {
                        String strId = request.getParameter("id");
                        int id = NumberUtils.isParsable(strId)
                                ? NumberUtils.createInteger(strId)
                                : 0;
                        final Optional<Item> optionalItem = store.findItemById(id);
                        if (optionalItem.isPresent()) {
                            final Item item = optionalItem.get();
                            boolean done = "true".equals(req.getParameter("done"));
                            item.setDone(done);
                            store.update(item);
                            return new JSONObject().put("msg", "success update");
                        }
                        return new JSONObject().put("msg", "update failed");
                    }
            );
        } else {
            send(req, resp, (request, response) -> {
                        final Item item = new Item(
                                Optional.ofNullable(req.getParameter("title")).orElse(""),
                                Optional.ofNullable(req.getParameter("description")).orElse(""),
                                LocalDateTime.now(),
                                false
                        );
                        User user = (User) req.getSession().getAttribute("user");
                        if (!"".equals(item.getTitle()) && user != null) {
                            item.setUser(user);
                            String[] categoriesIds = Optional
                                    .ofNullable(req.getParameterValues("categories[]"))
                                    .orElse(new String[0]);
                            store.saveItem(item, categoriesIds);
                        }
                        return item.getId() == 0 ? new JSONObject() : new JSONObject(item);
                    }
            );
        }
    }

    private <R> void send(final HttpServletRequest req,
                          final HttpServletResponse resp,
                          final BiFunction<HttpServletRequest, HttpServletResponse, R> command)
            throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        R rsl = command.apply(req, resp);
        try (PrintWriter writer =
                     new PrintWriter(resp.getOutputStream(), true, StandardCharsets.UTF_8)) {
            writer.println(rsl.toString());
        }
    }
}
