import {DataProvider} from "react-admin";

const apiUrl = 'http://localhost:8222/api/v1';

export const getHeaders = () => {
    const token = localStorage.getItem("access_token");
    return {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
    };
};

export const dataProvider: DataProvider = {
    getList: async (resource, params) => {
        const { page, perPage } = params.pagination || {};
        const { field, order } = params.sort || {};
        const queryParams: Record<string, string> = {};

        if (page !== undefined) queryParams.page = String(page);
        if (perPage !== undefined) queryParams.pageSize = String(perPage);
        if (field !== undefined) queryParams.sort = String(field);
        if (order !== undefined) queryParams.order = String(order);

        const query = new URLSearchParams(queryParams).toString();

        const response = await fetch(`${apiUrl}/${resource}/all?${query}`, {
            method: "GET",
            headers: getHeaders(),
        });

        if (!response.ok) {
            throw new Error("Ошибка загрузки списка ресурсов");
        }

        const data = await response.json();

        if (resource == "film") {
            return data
        } else {
            return {data: data, total: data.length}
        }
    },

    getOne: async (resource, params) => {
        const response = await fetch(`${apiUrl}/${resource}/${params.id}`, {
            method: "GET",
            headers: getHeaders(),
        });

        if (!response.ok) {
            throw new Error("Ошибка загрузки ресурса");
        }

        const data = await response.json();
        return { data };
    },

    update: async (resource, params) => {
        const response = await fetch(`${apiUrl}/${resource}/${params.id}/edit`, {
            method: "POST",
            headers: getHeaders(),
            body: JSON.stringify(params.data),
        });

        if (!response.ok) {
            throw new Error("Ошибка обновления");
        }

        const data = await response.json();
        return { data };
    },

    create: async (resource, params) => {
        const response = await fetch(`${apiUrl}/${resource}/create`, {
            method: "POST",
            headers: getHeaders(),
            body: JSON.stringify(params.data),
        });

        if (!response.ok) {
            throw new Error("Ошибка создания пользователя");
        }

        const data = await response.json();
        return { data };
    },

    getMany: async (resource, params) => {
        const response = await fetch(`${apiUrl}/${resource}/all`, {
            method: "GET",
            headers: getHeaders(),
        });

        if (!response.ok) {
            throw new Error("Ошибка получения списка ресурсов (getMany)");
        }

        const data = await response.json();

        // Проверяем, что ids переданы и фильтруем данные
        const filteredData = params.ids && params.ids.length > 0
            ? data.filter((item: any) => params.ids.includes(item.id))  // Фильтруем по id
            : data;

        // Возвращаем отфильтрованные данные, где берем все поля объекта, включая id
        return {
            data: filteredData.map((item: any) => ({
                id: item.id,
                ...item,
            })),
        };

    },

    delete: async (resource, params) => {
        const response = await fetch(`${apiUrl}/${resource}/${params.id}/delete`, {
            method: "DELETE",
            headers: getHeaders(),
        });

        if (!response.ok) {
            throw new Error("Ошибка удаления ресурса");
        }

        return { data: params.previousData ?? { id: params.id } as Record<any, any>};
    },

    getManyReference: () => Promise.reject(new Error("getManyReference не реализован")),

    deleteMany: () => Promise.reject(new Error("deleteMany не реализован")),

    updateMany: () => Promise.reject(new Error("updateMany не реализован")),
};