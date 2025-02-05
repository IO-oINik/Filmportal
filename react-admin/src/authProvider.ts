import { AuthProvider } from "react-admin";

const DEFAULT_AUTH_URL = "http://localhost:8222/api/v1/auth";
const REFRESH_URL = "/refresh-token";
const LOGOUT_URL = "/logout";
const LOGIN_URL = "/login"

interface LoginResponse {
    accessToken: string;
    expiresAt: number; // Время жизни access_token в секундах
}

let refreshTimeout: NodeJS.Timeout;

const scheduleTokenRefresh = (expiresAt: number) => {
    if (refreshTimeout) clearTimeout(refreshTimeout);

    // Обновляем токен за 30 секунд до истечения
    refreshTimeout = setTimeout(() => {
        CustomAuthProvider.refreshToken();
    }, (expiresAt - Date.now() - 30000));
};

const CustomAuthProvider: AuthProvider & { refreshToken: () => Promise<void> } = {
    login: async ({ username, password }) => {
        const response = await fetch(DEFAULT_AUTH_URL + LOGIN_URL, {
            method: "POST",
            body: JSON.stringify({ nickname: username, password }),
            headers: { "Content-Type": "application/json" },
            credentials: "include", // Отправляет и принимает куки
        });

        if (!response.ok) {
            throw new Error("Ошибка аутентификации");
        }

        const { accessToken, expiresAt }: LoginResponse = await response.json();

        localStorage.setItem("access_token", accessToken);
        localStorage.setItem("expires_at", expiresAt.toString());

        scheduleTokenRefresh(expiresAt);
    },

    logout: async () => {
        await fetch(DEFAULT_AUTH_URL + LOGOUT_URL, {
            method: "GET",
            credentials: "include", // Отправляет refresh_token (если в куке)
        }).catch(() => { /* Игнорируем ошибку */ });

        localStorage.removeItem("access_token");
        localStorage.removeItem("expires_at");
        if (refreshTimeout) clearTimeout(refreshTimeout);

        return Promise.resolve();
    },

    checkError: async ({status}) => {
        if (status === 401 || status === 403) {
            try {
                return await CustomAuthProvider.refreshToken();
            } catch {
                await CustomAuthProvider.logout("");
                return await Promise.reject();
            }
        }
        return Promise.resolve();
    },

    checkAuth: async () => {
        const token = localStorage.getItem("access_token");
        if (!token) return Promise.reject();

        const expiresAt = Number(localStorage.getItem("expires_at"));
        if (Date.now() > expiresAt) {
            try {
                return await CustomAuthProvider.refreshToken();
            } catch {
                return await Promise.reject();
            }
        }

        return Promise.resolve();
    },

    refreshToken: async () => {
        const response = await fetch(DEFAULT_AUTH_URL + REFRESH_URL, {
            method: "GET",
            headers: { "Content-Type": "application/json" },
            credentials: "include", // Отправляет refresh_token, если он в куке
        });

        if (!response.ok) {
            await CustomAuthProvider.logout("");
            return Promise.reject("Ошибка обновления токена");
        }

        const { accessToken, expiresAt }: LoginResponse = await response.json();

        localStorage.setItem("access_token", accessToken);
        localStorage.setItem("expires_at", expiresAt.toString());

        scheduleTokenRefresh(expiresAt);

        return Promise.resolve();
    },
};

export default CustomAuthProvider;