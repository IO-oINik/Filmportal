package ru.edu.ui.views;

import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldBase;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.CrudOperationException;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;
import ru.edu.ui.models.responses.*;
import ru.edu.ui.services.*;

import java.util.stream.Stream;


@Route(value = "film", layout = MainLayout.class)
public class FilmView extends VerticalLayout {

    private final GenreService genreService;
    private final PersonService personService;

    public FilmView(FilmService filmService, AgeLimitService ageLimitService, CountryService countryService, GenreService genreService, PersonService personService) {
        // crud instance
        GridCrud<FilmResponse> crud = new GridCrud<>(FilmResponse.class);

        // grid configuration
        crud.getGrid().setColumns(
                "id",
                "title",
                "titleForeign",
                "yearOfProduction",
                "releaseDateInWorld",
                "releaseDateInRussia",
                "budget",
                "durationInSeconds",
                "ageLimit");
        crud.getGrid().setColumnReorderingAllowed(true);

        // form configuration
//        crud.getCrudFormFactory().setUseBeanValidation(true);
        crud.getCrudFormFactory().setVisibleProperties(
                "title",
                "titleForeign",
                "description",
                "slogan",
                "yearOfProduction",
                "releaseDateInWorld",
                "releaseDateInRussia",
                "budget",
                "durationInSeconds",
                "ageLimit",
                "countries",
                "genres",
                "directors",
                "screenwriters",
                "producers",
                "actors");

        crud.getCrudFormFactory().setFieldProvider("ageLimit",
                new ComboBoxProvider<>("ageLimit",
                        ageLimitService.findAll(),
                        new TextRenderer<>(AgeLimitResponse::toString),
                        AgeLimitResponse::toString));
        crud.getCrudFormFactory().setFieldProvider("description", film -> new TextArea());
        crud.getCrudFormFactory().setFieldProvider("countries", film -> {
            MultiSelectComboBox<CountryResponse> comboBox = new MultiSelectComboBox<>();
            comboBox.setItems(countryService.findAll());
            comboBox.setItemLabelGenerator(CountryResponse::toString);
            comboBox.setSelectedItemsOnTop(true);
            return comboBox;
        });
        crud.getCrudFormFactory().setFieldProvider("genres", film -> {
            MultiSelectComboBox<GenreResponse> comboBox = new MultiSelectComboBox<>();
            comboBox.setItems(genreService.findAll());
            comboBox.setItemLabelGenerator(GenreResponse::getTitle);
            comboBox.setSelectedItemsOnTop(true);
            return comboBox;
        });
        crud.getCrudFormFactory().setFieldProvider("directors", film -> {
            MultiSelectComboBox<PersonResponse> comboBox = new MultiSelectComboBox<>();
            comboBox.setItems(personService.findAll());
            comboBox.setItemLabelGenerator(PersonResponse::toString);
            comboBox.setSelectedItemsOnTop(true);
            return comboBox;
        });
        crud.getCrudFormFactory().setFieldProvider("screenwriters", film -> {
            MultiSelectComboBox<PersonResponse> comboBox = new MultiSelectComboBox<>();
            comboBox.setItems(personService.findAll());
            comboBox.setItemLabelGenerator(PersonResponse::toString);
            comboBox.setSelectedItemsOnTop(true);
            return comboBox;
        });
        crud.getCrudFormFactory().setFieldProvider("producers", film -> {
            MultiSelectComboBox<PersonResponse> comboBox = new MultiSelectComboBox<>();
            comboBox.setItems(personService.findAll());
            comboBox.setItemLabelGenerator(PersonResponse::toString);
            comboBox.setSelectedItemsOnTop(true);
            return comboBox;
        });
        crud.getCrudFormFactory().setFieldProvider("actors", film -> {
            MultiSelectComboBox<PersonResponse> comboBox = new MultiSelectComboBox<>();
            comboBox.setItems(personService.findAll());
            comboBox.setItemLabelGenerator(PersonResponse::toString);
            comboBox.setSelectedItemsOnTop(true);
            return comboBox;
        });
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
        crud.setFindAllOperation(filmService::findAll);
        crud.setAddOperation(filmService::create);
        crud.setUpdateOperation(filmService::edit);
        crud.setDeleteOperation(filmService::delete);
        this.genreService = genreService;
        this.personService = personService;

        // visible details
        crud.getGrid().setItemDetailsRenderer(createFilmDetailsRenderer());
    }
    private static ComponentRenderer<FilmDetailsFormLayout, FilmResponse> createFilmDetailsRenderer() {
        return new ComponentRenderer<>(FilmDetailsFormLayout::new, FilmDetailsFormLayout::setFilm);
    }
    private static class FilmDetailsFormLayout extends FormLayout {
        private final MultiSelectComboBox<GenreResponse> genresField = new MultiSelectComboBox<>("Жанры");
        private final MultiSelectComboBox<PersonResponse> directorsField = new MultiSelectComboBox<>("Режисёры");
        private final MultiSelectComboBox<PersonResponse> screenwritersField = new MultiSelectComboBox<>("Сценаристы");
        private final MultiSelectComboBox<PersonResponse> producersField = new MultiSelectComboBox<>("Продюсеры");
        private final MultiSelectComboBox<PersonResponse> actorsField = new MultiSelectComboBox<>("Актёры");
        private final TextField sloganField = new TextField("Слоган");
        private final TextArea descriptionField = new TextArea("Описание");


        public FilmDetailsFormLayout() {
            Stream.of(genresField, directorsField, screenwritersField, producersField, actorsField).forEach(field -> {
                field.setReadOnly(true);
                add(field);
            });
            Stream.of(sloganField, descriptionField).forEach(field -> {
                ((TextFieldBase) field).setReadOnly(true);
                add(field);
            });
            setResponsiveSteps(new ResponsiveStep("0", 3));
            setColspan(descriptionField, 3);
        }
        public void setFilm(FilmResponse film) {
            genresField.setItems(film.getGenres());
            genresField.select(film.getGenres());
            directorsField.setItems(film.getDirectors());
            directorsField.select(film.getDirectors());
            screenwritersField.setItems(film.getScreenwriters());
            screenwritersField.select(film.getScreenwriters());
            producersField.setItems(film.getProducers());
            producersField.select(film.getProducers());
            actorsField.setItems(film.getActors());
            actorsField.select(film.getActors());
            sloganField.setValue(film.getSlogan());
            descriptionField.setValue(film.getDescription());
        }
    }
}