import ProductModule from '~/repository/modules/product';
import AuthModule from '~/repository/modules/auth';
import ProductPropertiesModule from '~/repository/modules/productProperties';
import ProductOptionGroupModule from '~/repository/modules/productOptionGroup';
import ProductOptionModule from '~/repository/modules/productOption';
import type { FetchOptions } from 'ofetch';

export interface IApiInstance {
    product: ProductModule;
    auth: AuthModule;
    prodProp: {
        prop: ProductPropertiesModule;
        group: ProductOptionGroupModule;
        option: ProductOptionModule;
    };
}

export default defineNuxtPlugin((nuxtApp) => {
    const config = useRuntimeConfig();
    const fetchOptions: FetchOptions = {
        baseURL: config.public.apiUrl
    };
    const apiFetcher = $fetch.create(fetchOptions);
    const apiInstance: IApiInstance = {
        product: new ProductModule(apiFetcher),
        auth: new AuthModule(apiFetcher),
        prodProp: {
            prop: new ProductPropertiesModule(apiFetcher),
            group: new ProductOptionGroupModule(apiFetcher),
            option: new ProductOptionModule(apiFetcher)
        }
    };

    return {
        provide: {
            api: apiInstance
        }
    };
});