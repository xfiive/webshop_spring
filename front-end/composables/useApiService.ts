import ProductPropertiesModule from '~/service/modules/productProperties';

interface IApiServiceInstance {
    prodProp: ProductPropertiesModule;
}

let apiService: IApiServiceInstance | null = null;

export default function(): IApiServiceInstance {
    if (!apiService) {
        const api = useApi();

        apiService = {
            prodProp: new ProductPropertiesModule(api)
        };
    }
    return apiService;
}
;
