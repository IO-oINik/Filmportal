package ru.edu.ui.views.admin;

import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.CrudOperationException;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;
import ru.edu.ui.models.responses.CountryResponse;
import ru.edu.ui.models.responses.PersonResponse;
import ru.edu.ui.services.CountryService;
import ru.edu.ui.services.PersonService;


@Route(value = "person", layout = MainAdminLayout.class)
public class PersonView extends VerticalLayout {
    private final PersonService personService;

    public PersonView(PersonService personService, CountryService countryService) {
        // crud instance
        GridCrud<PersonResponse> crud = new GridCrud<>(PersonResponse.class);

        // grid configuration
        crud.getGrid().setColumns("id", "name", "surname", "nameForeign", "dateOfBirth", "countryOfBirth");
        crud.getGrid().setColumnReorderingAllowed(true);

        // form configuration
        crud.getCrudFormFactory().setUseBeanValidation(true);
        crud.getCrudFormFactory().setVisibleProperties("name", "surname", "nameForeign", "dateOfBirth", "countryOfBirth");
        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.ADD, "name", "surname", "nameForeign", "dateOfBirth", "countryOfBirth");
        crud.getCrudFormFactory().setFieldProvider("countryOfBirth", new ComboBoxProvider<>(countryService.findAll()));
        crud.getCrudFormFactory().setFieldProvider("countryOfBirth",
                new ComboBoxProvider<>("countryOfBirth",
                        countryService.findAll(),
                        new TextRenderer<>(CountryResponse::getTitle),
                        CountryResponse::getTitle));
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
        crud.setFindAllOperation(personService::findAll);
        crud.setAddOperation(personService::create);
        crud.setUpdateOperation(personService::edit);
        crud.setDeleteOperation(personService::delete);

        // visible added columns
        crud.getGrid().addColumn(createCareersRenderer()).setHeader("Careers");
        this.personService = personService;
    }

    private ComponentRenderer<CareersLayout, PersonResponse> createCareersRenderer() {
        return new ComponentRenderer<>(CareersLayout::new, CareersLayout::setPerson);
    }
    private class CareersLayout extends FormLayout {
        private final MultiSelectComboBox<String> careersField = new MultiSelectComboBox<>();

        public CareersLayout() {
            careersField.setReadOnly(true);
            add(careersField);
        }
        public void setPerson(PersonResponse personResponse) {
            var careersResponse = personService.getCareers(personResponse);
            careersField.setItems(careersResponse.getCareers());
            careersField.select(careersResponse.getCareers());
        }
    }
}
