import type { $Fetch } from 'nitropack';

type RequestMethods =
    'delete'
    | 'get'
    | 'put'
    | 'GET'
    | 'HEAD'
    | 'PATCH'
    | 'POST'
    | 'PUT'
    | 'DELETE'
    | 'CONNECT'
    | 'OPTIONS'
    | 'TRACE'
    | 'head'
    | 'patch'
    | 'post'
    | 'connect'
    | 'options'
    | 'trace';

class HttpFactory {
    private readonly $fetch: $Fetch;

    constructor(fetcher: $Fetch) {
        this.$fetch = fetcher;
    }

    /**
     * method - GET, POST, PUT
     * URL
     **/
    async call<T>(method: RequestMethods, url: string, body?: object, extras = {}): Promise<T> {
        return await this.$fetch(url, { method, body, ...extras });
    }

    async callAuth<T>(method: RequestMethods, url: string, body?: object, extras = {}): Promise<T> {
        return await this.$fetch(url,
            {
                method, body,
                ...{
                    ...extras,
                    headers: {
                        Authorization: `Bearer ${useAuthStore().getToken || ''}`,
                        ...(extras as any)?.headers
                    }
                }
            });
    }
}

export default HttpFactory;