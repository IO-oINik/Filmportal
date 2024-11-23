package ru.edu.ui.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.*;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.server.VaadinSession;


@Route("/admin")
public class MainLayout extends AppLayout implements BeforeEnterObserver, AfterNavigationObserver {
    private SideNav sideNav = new SideNav();

    public MainLayout() {
        UI.getCurrent().getElement().getThemeList().add(Lumo.DARK);
        if(VaadinSession.getCurrent().getAttribute("token") == null) {
            UI.getCurrent().navigate("login");
        }

        DrawerToggle drawerToggle = new DrawerToggle();
        addToNavbar(drawerToggle);
        sideNav.addItem(new SideNavItem("Фильмы", FilmView.class));
        sideNav.addItem(new SideNavItem("Жанры", GenreView.class));
        sideNav.addItem(new SideNavItem("Страны", CountryView.class));
        sideNav.addItem(new SideNavItem("Возрастные ограничения", AgeLimitView.class));
        sideNav.addItem(new SideNavItem("Персоны", PersonView.class));

        addToDrawer(sideNav);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if(VaadinSession.getCurrent().getAttribute("token") == null) {
            event.forwardTo("login");
        }
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        updatePageTitle();
    }

    public void updatePageTitle() {
//        Class<? extends HasComponents> viewClass = tabToView.get(tabs.getSelectedTab());
//        UI.getCurrent().getPage().setTitle(DemooUtils.getViewName(viewClass) + " - " + "Crud UI add-on demo");
    }

}