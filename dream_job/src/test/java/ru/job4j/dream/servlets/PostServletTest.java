package ru.job4j.dream.servlets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PsqlStore.class)
public class PostServletTest {

    @Test
    public void doGet() throws ServletException, IOException {
        PowerMockito.mockStatic(PsqlStore.class);
        PsqlStore store = PowerMockito.mock(PsqlStore.class);
        HttpSession session = mock(HttpSession.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        PostServlet postServlet = mock(PostServlet.class);

        when(PsqlStore.instOf()).thenReturn(store);
        when(store.findAllPosts()).thenReturn(null);
        when(session.getAttribute("user")).thenReturn(null);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("posts.jsp")).thenReturn(requestDispatcher);
        doCallRealMethod().when(postServlet).doGet(request, response);

        postServlet.doGet(request, response);

        verify(request).setAttribute("posts", null);
        verify(request).setAttribute("user", null);
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
        when(request.getParameter("desc")).thenReturn("0");

        new PostServlet().doPost(request, response);
        verify(store).save((Post) any());
        verify(response).sendRedirect(anyString() + "/posts.do");
    }
}