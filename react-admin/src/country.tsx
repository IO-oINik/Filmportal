import { Datagrid, List, TextField, Edit, SimpleForm, TextInput, Create  } from 'react-admin';

export const CountryList = () => (
    <List>
        <Datagrid>
            <TextField source="id" />
            <TextField source="title" />
        </Datagrid>
    </List>
);

export const CountryEdit = () => (
    <Edit>
        <SimpleForm>
            <TextInput source="title" />
        </SimpleForm>
    </Edit>
);

export const CountryCreate = () => (
    <Create>
        <SimpleForm>
            <TextInput source="title" />
        </SimpleForm>
    </Create>
);