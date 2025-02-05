import {
    ArrayField,
    ChipField,
    Datagrid,
    DateField,
    List,
    NumberField,
    SingleFieldList,
    TextField,
    Show,
    SimpleShowLayout,
    DateInput,
    Edit,
    NumberInput,
    SimpleForm,
    TextInput,
    ReferenceArrayInput,
    SelectArrayInput,
    ReferenceInput,
    SelectInput,
    useRecordContext
} from 'react-admin';
import {useEffect, useState} from "react";
import {getHeaders} from "./dataProvider.ts";

const AverageField = ({ endpoint }: { source: string; endpoint: (record: any) => string }) => {
    const record = useRecordContext();
    const [value, setValue] = useState<number | null>(null);

    useEffect(() => {
        if (!record) return;

        fetch(endpoint(record) , {
            method: "GET",
            headers: getHeaders()
        })
            .then((res) => res.json())
            .then((data) => setValue(data))
            .catch((error) => console.error("Ошибка загрузки данных:", error));
    }, [record, endpoint]);

    if (!record) return null;

    return <span>{value !== null ? value : "Загрузка..."}</span>;
};

export const FilmList = () => (
    <List>
        <Datagrid>
            <TextField source="id" />
            <TextField source="title" />
            <TextField source="titleForeign" />
            <TextField source="description" />
            <TextField source="slogan" />
            <TextField source="yearOfProduction" />
            <DateField source="releaseDateInWorld" />
            <DateField source="releaseDateInRussia" />
            <NumberField source="budget" />
            <NumberField source="ageLimit.age" />
            <ArrayField source="countries">
                <SingleFieldList>
                    <ChipField source="title" />
                </SingleFieldList>
            </ArrayField>
            <ArrayField source="genres">
                <SingleFieldList>
                    <ChipField source="title" />
                </SingleFieldList>
            </ArrayField>
            <AverageField source="average score" endpoint={(record) => `http://localhost:8222/api/v1/film-score/${record.id}/average`}/>
        </Datagrid>
    </List>
);

export const FilmShow = () => (
    <Show>
        <SimpleShowLayout>
            <TextField source="title" label="Название" />
            <TextField source="titleForeign" label="Оригинальное название" />
            <TextField source="description" label="Описание" />
            <TextField source="slogan" label="Слоган" />
            <NumberField source="yearOfProduction" label="Год производства" />
            <DateField source="releaseDateInWorld" label="Дата выхода в мире" />
            <DateField source="releaseDateInRussia" label="Дата выхода в России" />
            <NumberField source="budget" label="Бюджет" />
            <NumberField source="durationInSeconds" label="Длительность (сек)" />

            <TextField source="ageLimit.age" />

            <ArrayField source="countries" label="Страны">
                <SingleFieldList>
                    <ChipField source="title" />
                </SingleFieldList>
            </ArrayField>

            <ArrayField source="genres" label="Жанры">
                <SingleFieldList>
                    <ChipField source="title" />
                </SingleFieldList>
            </ArrayField>

            <ArrayField source="directors" label="Режиссёры">
                <SingleFieldList>
                    <TextField source="surname" />
                </SingleFieldList>
            </ArrayField>

            <ArrayField source="screenwriters" label="Сценаристы">
                <SingleFieldList>
                    <TextField source="surname" />
                </SingleFieldList>
            </ArrayField>

            <ArrayField source="producers" label="Продюсеры">
                <SingleFieldList>
                    <TextField source="surname" />
                </SingleFieldList>
            </ArrayField>

            <ArrayField source="actors" label="Актёры">
                <SingleFieldList>
                    <TextField source="surname" />
                </SingleFieldList>
            </ArrayField>
        </SimpleShowLayout>
    </Show>
);

export const FilmEdit = () => (
    <Edit transform={(data) => ({
            ...data,
            idAgeLimit: data.ageLimit?.id,
            countryIds: data.countries?.map((c: any) => c.id),
            genreIds: data.genres?.map((g: any) => g.id),
            directorIds: data.directors?.map((d: any) => d.id),
            screenwriterIds: data.screenwriters?.map((s: any) => s.id),
            producerIds: data.producers?.map((p: any) => p.id),
            actorIds: data.actors?.map((a: any) => a.id),
    })}>
        <SimpleForm>
            <TextInput source="title" label="Название" />
            <TextInput source="titleForeign" label="Оригинальное название" />
            <TextInput source="description" label="Описание" />
            <TextInput source="slogan" label="Слоган" />
            <NumberInput source="yearOfProduction" label="Год производства" />
            <DateInput source="releaseDateInWorld" label="Дата выхода в мире" />
            <DateInput source="releaseDateInRussia" label="Дата выхода в России" />
            <NumberInput source="budget" label="Бюджет" />
            <NumberInput source="durationInSeconds" label="Длительность (секунды)" />

            <ReferenceInput source="ageLimit.id" reference="age-limit" label="Возрастной рейтинг">
                <SelectInput optionText="age" optionValue="id" label="Возрастной рейтинг"/>
            </ReferenceInput>

            <ReferenceArrayInput source="countryIds" reference="country" label="Страны">
                <SelectArrayInput optionText="title" />
            </ReferenceArrayInput>

            <ReferenceArrayInput source="genreIds" reference="genre" label="Жанры">
                <SelectArrayInput optionText="title" />
            </ReferenceArrayInput>

            <ReferenceArrayInput source="directorIds" reference="person" label="Режиссёры">
                <SelectArrayInput optionText={(record) => `${record.name} ${record.surname}`} />
            </ReferenceArrayInput>

            <ReferenceArrayInput source="screenwriterIds" reference="person" label="Сценаристы">
                <SelectArrayInput optionText={(record) => `${record.name} ${record.surname}`} />
            </ReferenceArrayInput>

            <ReferenceArrayInput source="producerIds" reference="person" label="Продюсеры">
                <SelectArrayInput optionText={(record) => `${record.name} ${record.surname}`} />
            </ReferenceArrayInput>

            <ReferenceArrayInput source="actorIds" reference="person" label="Актёры">
                <SelectArrayInput optionText={(record) => `${record.name} ${record.surname}`} />
            </ReferenceArrayInput>
        </SimpleForm>
    </Edit>
);