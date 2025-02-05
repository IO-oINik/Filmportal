import {Datagrid, List, TextField, Edit, SimpleForm, TextInput, Create} from 'react-admin';

export const GenreList = () => (
    <List>
        <Datagrid>
            <TextField source="id" />
            <TextField source="title" />
        </Datagrid>
    </List>
);

export const GenreEdit = () => (
    <Edit>
        <SimpleForm>
            <TextInput source="title" />
        </SimpleForm>
    </Edit>
);

export const GenreCreate = () => (
    <Create>
        <SimpleForm>
            <TextInput source="title" />
        </SimpleForm>
    </Create>
);