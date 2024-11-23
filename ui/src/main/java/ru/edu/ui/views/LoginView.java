package ru.edu.ui.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.Lumo;
import ru.edu.ui.exceptions.AuthException;
import ru.edu.ui.services.AuthService;

@Route(value = "/login")
public class LoginView extends VerticalLayout implements BeforeEnterListener {
    private AuthService authService;

    public LoginView(AuthService authService) {
        this.authService = authService;

        UI.getCurrent().getElement().getThemeList().add(Lumo.DARK);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        LoginI18n i18n = LoginI18n.createDefault();
        i18n.getForm().setTitle("Вход");
        i18n.getForm().setUsername("Имя пользователя");
        i18n.getForm().setPassword("Пароль");
        i18n.getForm().setSubmit("Войти");
        i18n.getErrorMessage().setTitle("Ошибка входа");
        i18n.getErrorMessage().setMessage("Неверное имя пользователя или пароль.");

        LoginForm loginForm = new LoginForm();
        loginForm.setForgotPasswordButtonVisible(false);
        loginForm.setI18n(i18n);

        loginForm.addLoginListener(event -> {
            String username = event.getUsername();
            String password = event.getPassword();

            try {
                String token = authService.getToken(username, password);
                VaadinSession.getCurrent().setAttribute("token", token);
                UI.getCurrent().navigate("/admin");
            } catch (AuthException e) {
                loginForm.setError(true);
            } catch (RuntimeException e) {
                loginForm.setError(true);
                i18n.getErrorMessage().setTitle("Неизвестная ошибка");
            }
        });

        add(loginForm);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(VaadinSession.getCurrent().getAttribute("token") != null) {
            UI.getCurrent().navigate("/admin");
        }
    }
}
