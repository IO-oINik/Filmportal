import { Datagrid, EmailField, List, TextField, Show, SimpleShowLayout } from 'react-admin';

export const UserList = () => (
    <List>
        <Datagrid>
            <TextField source="name" label="Имя"/>
            <TextField source="surname" label="Фамилия"/>
            <TextField source="nickname" label="Никнейм"/>
            <EmailField source="email" label="Почта"/>
            <TextField source="role" label="Роль"/>
        </Datagrid>
    </List>
);

export const UserShow = () => (
    <Show>
        <SimpleShowLayout>
            <TextField source="id" />
            <TextField source="name" />
            <TextField source="surname" />
            <TextField source="nickname" />
            <EmailField source="email" />
            <TextField source="role" />
            <TextField source="dateOfCreation" />
            <TextField source="dateOfModified" />
        </SimpleShowLayout>
    </Show>
);