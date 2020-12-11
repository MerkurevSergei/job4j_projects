package ru.job4j.dream.servlets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.User;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PsqlStore.class)
public class CandidateServletTest {

    @Test
    public void doGet() throws IOException, ServletException {
        PowerMockito.mockStatic(PsqlStore.class);
        PsqlStore store = PowerMockito.mock(PsqlStore.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        CandidateServlet candidateServlet = mock(CandidateServlet.class);

        when(PsqlStore.instOf()).thenReturn(store);
        when(store.findAllCandidates()).thenReturn(null);
        when(request.getRequestDispatcher("candidates.jsp")).thenReturn(requestDispatcher);
        doCallRealMethod().when(candidateServlet).doGet(request, response);

        candidateServlet.doGet(request, response);

        verify(candidateServlet, never()).doDelete(request, response);
        verify(request).setAttribute("candidates", null);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void doGetToDelete() throws IOException, ServletException {
        PowerMockito.mockStatic(PsqlStore.class);
        PsqlStore store = PowerMockito.mock(PsqlStore.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        CandidateServlet candidateServlet = mock(CandidateServlet.class);

        when(PsqlStore.instOf()).thenReturn(store);
        when(store.findAllCandidates()).thenReturn(null);
        when(request.getParameter("method")).thenReturn("delete");
        when(request.getRequestDispatcher("candidates.jsp")).thenReturn(requestDispatcher);
        doCallRealMethod().when(candidateServlet).doGet(request, response);

        candidateServlet.doGet(request, response);

        verify(candidateServlet).doDelete(request, response);
        verify(request).setAttribute("candidates", null);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void doPost() throws IOException {
        PowerMockito.mockStatic(PsqlStore.class);
        PsqlStore store = PowerMockito.mock(PsqlStore.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(PsqlStore.instOf()).thenReturn(store);
        when(request.getParameter("id")).thenReturn("0");
        when(request.getParameter("name")).thenReturn("0");
        when(request.getParameter("photoId")).thenReturn("0");
        when(request.getParameter("cityId")).thenReturn("0");

        new CandidateServlet().doPost(request, response);
        verify(store).save((Candidate) any());
        verify(response).sendRedirect(anyString() + "/candidates.do");
    }
}