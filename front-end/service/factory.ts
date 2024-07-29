import type { IApiInstance } from '~/plugins/api';


class ServiceFactory {
    api: IApiInstance;

    constructor(api: IApiInstance) {
        this.api = api;
    }
}

export default ServiceFactory;