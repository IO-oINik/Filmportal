import { Datagrid, List, NumberField, TextField, Edit, SimpleForm, NumberInput, Create } from 'react-admin';

export const AgeLimitList = () => (
    <List>
        <Datagrid>
            <TextField source="id" />
            <NumberField source="age" />
        </Datagrid>
    </List>
);

export const AgeLimitEdit = () => (
    <Edit>
        <SimpleForm>
            <NumberInput source="age" />
        </SimpleForm>
    </Edit>
);

export const AgeLimitCreate = () => (
    <Create>
        <SimpleForm>
            <NumberInput source="age" />
        </SimpleForm>
    </Create>
);