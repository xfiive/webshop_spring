import type { IApiInstance } from '~/composables/useApi';

class ServiceFactory {
    api: IApiInstance;

    constructor(api: IApiInstance) {
        this.api = api;
    }
}

export default ServiceFactory;