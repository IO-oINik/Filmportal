import {
    Datagrid,
    DateField,
    List,
    NumberField,
    ReferenceInput,
    SelectInput,
    TextField,
    SimpleForm,
    TextInput,
    DateInput,
    Edit,
    Create
} from 'react-admin';

export const PersonList = () => (
    <List>
        <Datagrid>
            <TextField source="id" />
            <TextField source="name" />
            <TextField source="surname" />
            <TextField source="nameForeign" />
            <DateField source="dateOfBirth" />
            <NumberField source="countryOfBirth.title" />
        </Datagrid>
    </List>
);

export const PersonEdit = () => (
    <Edit transform={(data) => ({ ...data, idCountry: data.countryOfBirth?.id })}>
        <SimpleForm>
            <TextInput source="name" />
            <TextInput source="surname" />
            <TextInput source="nameForeign" />
            <DateInput source="dateOfBirth" />
            <ReferenceInput source="countryOfBirth.id"
                            reference="country" >
                <SelectInput optionText="title" optionValue="id"/>
            </ReferenceInput>
        </SimpleForm>
    </Edit>
);

export const PersonCreate = () => (
    <Create transform={(data) => ({ ...data, idCountry: data.countryOfBirth?.id })}>
        <SimpleForm>
            <TextInput source="name" />
            <TextInput source="surname" />
            <TextInput source="nameForeign" />
            <DateInput source="dateOfBirth" />
            <ReferenceInput source="countryOfBirth.id"
                            reference="country" >
                <SelectInput optionText="title" optionValue="id"/>
            </ReferenceInput>
        </SimpleForm>
    </Create>
);