package ru.job4j.dream.servlets;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CandidateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if ("delete".equals(req.getParameter("method"))) {
            doDelete(req, resp);
        }
        req.setAttribute("candidates", PsqlStore.instOf().findAllCandidates());
        req.getRequestDispatcher("candidates.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        PsqlStore.instOf().save(new Candidate(
                        Integer.parseInt(req.getParameter("id")),
                        req.getParameter("name"),
                        req.getParameter("photoId"),
                        new City(
                                Integer.parseInt(req.getParameter("cityId")),
                                null)
                )
        );
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        int candidateId = Integer.parseInt(req.getParameter("id"));
        Candidate candidate = PsqlStore.instOf().findCandidateById(candidateId);
        try {
            Files.delete(Path.of(
                    "images" + File.separator + candidate.getPhotoId()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PsqlStore.instOf().deleteCandidate(candidate.getId());

    }
}
