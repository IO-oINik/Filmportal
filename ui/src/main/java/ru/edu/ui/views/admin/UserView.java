package ru.edu.ui.views.admin;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.CrudOperationException;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;
import ru.edu.ui.models.responses.UserResponse;
import ru.edu.ui.services.RoleService;
import ru.edu.ui.services.UserService;

@Route(value = "user", layout = MainAdminLayout.class)
public class UserView extends VerticalLayout {

    public UserView(UserService userService, RoleService roleService) {
        // crud instance
        GridCrud<UserResponse> crud = new GridCrud<>(UserResponse.class);

        // grid configuration
        crud.getGrid().setColumns("id", "name", "surname", "email", "nickname", "role");
        crud.getGrid().setColumnReorderingAllowed(true);

        // form configuration
//        crud.getCrudFormFactory().setUseBeanValidation(true);
        crud.getCrudFormFactory().setVisibleProperties("name", "surname", "email", "nickname", "role");
        crud.getCrudFormFactory().setErrorListener(e -> {
            if(CrudOperationException.class.isAssignableFrom(e.getClass())) {
                crud.showNotification(e.getMessage());
            } else {
                crud.showNotification("Ошибка");
            }
        });
        crud.getCrudFormFactory().setFieldProvider("role",
                new ComboBoxProvider<>("role",
                        roleService.findAll(),
                        new TextRenderer<>(String::toString),
                        String::toString));

        // layout configuration
        setSizeFull();
        add(crud);

        // logic configuration
        crud.setFindAllOperation(userService::findAll);
        crud.setUpdateOperation(userService::edit);
        crud.setDeleteOperation(userService::delete);
        crud.setAddOperationVisible(false);
    }
}
