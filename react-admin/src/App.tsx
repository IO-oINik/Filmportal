import {
    Admin, Resource
} from "react-admin";
import CustomAuthProvider from "./authProvider.ts"
import {dataProvider} from "./dataProvider.ts";
import {UserList, UserShow} from "./user.tsx"
import {GenreCreate, GenreEdit, GenreList} from "./genre.tsx";
import {CountryList, CountryEdit, CountryCreate} from "./country.tsx";
import {AgeLimitList, AgeLimitEdit, AgeLimitCreate} from "./ageLimit.tsx";
import {PersonCreate, PersonEdit, PersonList} from "./person.tsx";
import {FilmEdit, FilmList, FilmShow} from "./film.tsx";

export const App = () =>
    <Admin authProvider={CustomAuthProvider} dataProvider={dataProvider}>
        <Resource name="user" list={UserList} show={UserShow}></Resource>
        <Resource name="film" list={FilmList} show={FilmShow} edit={FilmEdit}></Resource>
        <Resource name="genre" list={GenreList} edit={GenreEdit} create={GenreCreate}></Resource>
        <Resource name="country" list={CountryList} edit={CountryEdit} create={CountryCreate}></Resource>
        <Resource name="age-limit" list={AgeLimitList} edit={AgeLimitEdit} create={AgeLimitCreate}></Resource>
        <Resource name="person" list={PersonList} edit={PersonEdit} create={PersonCreate}></Resource>
    </Admin>;
