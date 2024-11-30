package ru.edu.ui.views.admin;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.CrudOperationException;
import org.vaadin.crudui.crud.impl.GridCrud;
import ru.edu.ui.models.responses.CountryResponse;
import ru.edu.ui.services.CountryService;

@Route(value = "country", layout = MainAdminLayout.class)
public class CountryView extends VerticalLayout {

    public CountryView(CountryService countryService) {
        // crud instance
        GridCrud<CountryResponse> crud = new GridCrud<>(CountryResponse.class);

        // grid configuration
        crud.getGrid().setColumns("id", "title");
        crud.getGrid().setColumnReorderingAllowed(true);

        // form configuration
//        crud.getCrudFormFactory().setUseBeanValidation(true);
        crud.getCrudFormFactory().setVisibleProperties("title");
        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.ADD, "title");
        crud.getCrudFormFactory().setErrorListener(e -> {
            if(CrudOperationException.class.isAssignableFrom(e.getClass())) {
                crud.showNotification(e.getMessage());
            } else {
                crud.showNotification("Ошибка");
            }
        });

        // layout configuration
        setSizeFull();
        add(crud);

        // logic configuration
        crud.setFindAllOperation(countryService::findAll);
        crud.setAddOperation(countryService::create);
        crud.setUpdateOperation(countryService::edit);
        crud.setDeleteOperation(countryService::delete);
    }
}
