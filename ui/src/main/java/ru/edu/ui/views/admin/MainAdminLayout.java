package ru.edu.ui.views.admin;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.*;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.server.VaadinSession;


@Route("/admin")
public class MainAdminLayout extends AppLayout implements BeforeEnterObserver, AfterNavigationObserver {
    private SideNav sideNavFilm = new SideNav();
    private SideNav sideNavAdmin = new SideNav();

    public MainAdminLayout() {
        UI.getCurrent().getElement().getThemeList().add(Lumo.DARK);
        if(VaadinSession.getCurrent().getAttribute("token") == null) {
            UI.getCurrent().navigate("login");
        }

        DrawerToggle drawerToggle = new DrawerToggle();
        addToNavbar(drawerToggle);
        sideNavFilm.setLabel("Редактор");
        sideNavFilm.addItem(new SideNavItem("Фильмы", FilmView.class));
        sideNavFilm.addItem(new SideNavItem("Жанры", GenreView.class));
        sideNavFilm.addItem(new SideNavItem("Страны", CountryView.class));
        sideNavFilm.addItem(new SideNavItem("Возрастные ограничения", AgeLimitView.class));
        sideNavFilm.addItem(new SideNavItem("Персоны", PersonView.class));

        if(VaadinSession.getCurrent().getAttribute("role") != null && VaadinSession.getCurrent().getAttribute("role").equals("ADMIN")) {
            sideNavAdmin.setLabel("Администратор");
            sideNavAdmin.addItem(new SideNavItem("Пользователи", UserView.class));
        }

        addToDrawer(sideNavFilm);
        addToDrawer(sideNavAdmin);
    }

    public String getTitle() {
        return "Панель администратора";
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if(VaadinSession.getCurrent().getAttribute("token") == null) {
            event.forwardTo("login");
        } else {
            if(VaadinSession.getCurrent().getAttribute("role").equals("USER")) {
                event.forwardTo("");
                UI.getCurrent().navigate("");
            }
        }
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        updatePageTitle();
    }

    public void updatePageTitle() {
        UI.getCurrent().getPage().setTitle(getTitle());
    }

}