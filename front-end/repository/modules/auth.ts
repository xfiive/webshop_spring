import HttpFactory from '~/repository/factory';
import { StatusCodes } from 'http-status-codes';
// import { FetchError } from 'ofetch';


enum AuthApiUrl {
    Login = '/admin/login',
    Logout = '/admin/logout',
}

interface UserPayloadInterface {
    username: string;
    password: string;
}

class AuthModule extends HttpFactory {
    async login(payload: UserPayloadInterface): Promise<string | null> {
        try {
            const { token } = await this.call<{ token: string }>('POST', AuthApiUrl.Login, {
                username: payload.username,
                passwordHash: payload.password,
            });
            return token;
        } catch (error: any/*: FetchError*/) {
            if (error.response && error.response.status === StatusCodes.NOT_ACCEPTABLE) {
                return null;
            }

            throw createError(error);
        }

    }
/*
    logout(): void {
        const token = useCookie('token');
        token.value = null;
    }*/
}

export default AuthModule;
