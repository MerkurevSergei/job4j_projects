package ru.job4j.dream.servlets;

import org.json.JSONArray;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CitiesServlet extends HttpServlet {
    @Override
    protected void
    doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        JSONArray ja = new JSONArray(PsqlStore.instOf().findAllCities());
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.println(ja.toString());
        writer.flush();
    }
}
