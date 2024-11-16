package ru.edu.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;

import java.util.HashMap;
import java.util.Map;

public class MainLayout extends AppLayout implements BeforeEnterObserver, AfterNavigationObserver {
    private Tabs tabs = new Tabs();
    private Map<Tab, Class<? extends HasComponents>> tabToView = new HashMap<>();
    private Map<Class<? extends HasComponents>, Tab> viewToTab = new HashMap<>();

    public MainLayout() {
        AppLayout appLayout = new AppLayout();

        Image img = new Image("https://i.imgur.com/GPpnszs.png", "Vaadin Logo");
        img.setHeight("44px");
        addToNavbar(img);

        tabs.addSelectedChangeListener(event -> tabsSelectionChanged(event));
        addToNavbar(tabs);

        addTab(FilmView.class, "Фильмы");
        addTab(GenreView.class, "Жанры");
        addTab(AgeLimitView.class, "Возрастные ограничения");
        addTab(CountryView.class, "Страны");
        addTab(PersonView.class, "Артисты");
    }

    private void tabsSelectionChanged(Tabs.SelectedChangeEvent event) {
        if (event.isFromClient()) {
            UI.getCurrent().navigate((Class<? extends Component>) tabToView.get(event.getSelectedTab()));
        }
    }

    private void addTab(Class<? extends HasComponents> clazz, String name) {
        Tab tab = new Tab(name);
        tabs.add(tab);
        tabToView.put(tab, clazz);
        viewToTab.put(clazz, tab);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        selectTabByCurrentView(event);
    }

    public void selectTabByCurrentView(BeforeEnterEvent event) {
        Class<?> viewClass = event.getNavigationTarget();
        tabs.setSelectedTab(viewToTab.get(viewClass));
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        updatePageTitle();
    }

    public void updatePageTitle() {
        Class<? extends HasComponents> viewClass = tabToView.get(tabs.getSelectedTab());
//        UI.getCurrent().getPage().setTitle(DemooUtils.getViewName(viewClass) + " - " + "Crud UI add-on demo");
    }

}