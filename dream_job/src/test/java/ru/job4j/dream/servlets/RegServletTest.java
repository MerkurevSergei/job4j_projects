package ru.job4j.dream.servlets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PsqlStore.class)
public class RegServletTest {

    @Test
    public void doGet() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

        when(request.getRequestDispatcher("reg.jsp")).thenReturn(requestDispatcher);
        new RegServlet().doGet(request, response);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void doPostWhenSuccess() throws IOException, ServletException {
        PowerMockito.mockStatic(PsqlStore.class);
        PsqlStore store = PowerMockito.mock(PsqlStore.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

        when(PsqlStore.instOf()).thenReturn(store);
        when(request.getParameter("name")).thenReturn("test");
        when(request.getParameter("email")).thenReturn("test@test.ru");
        when(request.getParameter("password")).thenReturn("test");
        when(request.getRequestDispatcher("auth.do")).thenReturn(requestDispatcher);
        when(store.findUserByEmail("test@test.ru"))
                .thenReturn(new User(0, "test", "test@test.ru", "test"));

        new RegServlet().doPost(request, response);
        verify(store).save((User) any());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void doPostWhenError() throws IOException, ServletException {
        PowerMockito.mockStatic(PsqlStore.class);
        PsqlStore store = PowerMockito.mock(PsqlStore.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

        when(PsqlStore.instOf()).thenReturn(store);
        when(request.getParameter("name")).thenReturn("test");
        when(request.getParameter("email")).thenReturn("test@test.ru");
        when(request.getParameter("password")).thenReturn("test");
        when(request.getRequestDispatcher("reg.jsp")).thenReturn(requestDispatcher);
        when(store.findUserByEmail("test@test.ru"))
                .thenReturn(new User(1, "test", "test@test.ru", "test"));

        new RegServlet().doPost(request, response);
        verify(request).setAttribute("error", "Не верное имя, email или пароль");
        verify(requestDispatcher).forward(request, response);
    }
}