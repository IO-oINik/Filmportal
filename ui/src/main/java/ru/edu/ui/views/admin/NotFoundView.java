package ru.edu.ui.views.admin;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouteNotFoundError;

public class NotFoundView extends RouteNotFoundError {

    public NotFoundView() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        layout.add(new H1("Not Found :("));

        getElement().appendChild(layout.getElement());
    }
}
