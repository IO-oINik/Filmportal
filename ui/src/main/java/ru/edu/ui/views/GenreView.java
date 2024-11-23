package ru.edu.ui.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.CrudOperationException;
import org.vaadin.crudui.crud.impl.GridCrud;
import ru.edu.ui.models.responses.GenreResponse;
import ru.edu.ui.services.GenreService;

@Route(value = "genre", layout = MainLayout.class)
public class GenreView extends VerticalLayout {

    public GenreView(GenreService genreService) {
        // crud instance
        GridCrud<GenreResponse> crud = new GridCrud<>(GenreResponse.class);

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
        crud.setFindAllOperation(genreService::findAll);
        crud.setAddOperation(genreService::create);
        crud.setUpdateOperation(genreService::edit);
        crud.setDeleteOperation(genreService::delete);
    }
}
