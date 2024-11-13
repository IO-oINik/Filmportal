package ru.edu.ui.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;
import ru.edu.ui.models.responses.AgeLimitResponse;
import ru.edu.ui.services.AgeLimitService;

@Route(value = "age-limit", layout = MainLayout.class)
@RolesAllowed(value = {"ADMIN"})
public class AgeLimitView extends VerticalLayout {

    public AgeLimitView(AgeLimitService ageLimitService) {
        // crud instance
        GridCrud<AgeLimitResponse> crud = new GridCrud<>(AgeLimitResponse.class);

        // grid configuration
        crud.getGrid().setColumns("id", "age");
        crud.getGrid().setColumnReorderingAllowed(true);

        // form configuration
//        crud.getCrudFormFactory().setUseBeanValidation(true);
        crud.getCrudFormFactory().setVisibleProperties("age");
        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.ADD, "age");

        // layout configuration
        setSizeFull();
        add(crud);

        // logic configuration
        crud.setFindAllOperation(ageLimitService::findAll);
        crud.setAddOperation(ageLimitService::create);
        crud.setUpdateOperation(ageLimitService::edit);
        crud.setDeleteOperation(ageLimitService::delete);
    }
}
