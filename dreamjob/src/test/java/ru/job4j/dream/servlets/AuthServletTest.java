package ru.job4j.dream.servlets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.dream.model.User;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PsqlStore.class)
public class AuthServletTest {

    @Test
    public void doGet() throws ServletException, IOException {
        HttpSession session = mock(HttpSession.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("login.jsp")).thenReturn(requestDispatcher);

        new AuthServlet().doGet(request, response);
        verify(session, never()).setAttribute("user", null);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void doGetWhenActionQuit() throws ServletException, IOException {
        HttpSession session = mock(HttpSession.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("action")).thenReturn("quit");
        when(request.getRequestDispatcher("login.jsp")).thenReturn(requestDispatcher);

        new AuthServlet().doGet(request, response);
        verify(session).setAttribute("user", null);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void doPostWhenSuccess() throws Exception {
        PsqlStore store = PowerMockito.mock(PsqlStore.class);
        PowerMockito.mockStatic(PsqlStore.class);

        HttpSession session = mock(HttpSession.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        User user = mock(User.class);

        when(PsqlStore.instOf()).thenReturn(store);
        when(store.findUserByEmail(any())).thenReturn(user);
        when(user.isEmpty()).thenReturn(false);
        when(user.getPassword()).thenReturn("test");
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("password")).thenReturn("test");

        new AuthServlet().doPost(request, response);
        verify(session).setAttribute("user", user);
        verify(response).sendRedirect(anyString() + "/posts.do");
    }

    @Test
    public void doPostWhenError() throws Exception {
        PsqlStore store = PowerMockito.mock(PsqlStore.class);
        PowerMockito.mockStatic(PsqlStore.class);

        HttpSession session = mock(HttpSession.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        User user = mock(User.class);

        when(PsqlStore.instOf()).thenReturn(store);
        when(store.findUserByEmail(any())).thenReturn(user);
        when(user.isEmpty()).thenReturn(true);
        when(user.getPassword()).thenReturn("test");
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("password")).thenReturn("test1");
        when(request.getRequestDispatcher("login.jsp")).thenReturn(requestDispatcher);

        new AuthServlet().doPost(request, response);
        verify(session, never()).setAttribute("user", user);
        verify(request).setAttribute("error", "Не верный email или пароль");
        verify(requestDispatcher).forward(request, response);
    }
}