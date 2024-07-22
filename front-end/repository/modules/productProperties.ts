import HttpFactory from '~/repository/factory';
import type { ProductProperties, ProductOptionGroup } from '~/models/product';

enum ApiUrl {
    Main = '/products/properties',
    GetAll = '/find/all',
    GetById = '/find/',
    GetGroupsByPropertiesId = '/find/',
    PostAdd = '/add',
    PutById = '/change/',
    DeleteById = '/delete/',
}

class ProductPropertiesModule extends HttpFactory {
    async getAll(): Promise<ProductProperties[]> {
        return this.call<ProductProperties[]>('get', `${ApiUrl.Main}${ApiUrl.GetAll}`);
    }

    async getById(id: number): Promise<ProductProperties> {
        return this.call<ProductProperties>('get', `${ApiUrl.Main}${ApiUrl.GetById}${id}`);
    }

    async getGroupsByPropertiesId(propertiesId: number): Promise<ProductOptionGroup[]> {
        return this.call<ProductOptionGroup[]>('get', `${ApiUrl.Main}${ApiUrl.GetGroupsByPropertiesId}${propertiesId}/groups`);
    }

    async add(properties: ProductProperties): Promise<ProductProperties> {
        return this.callAuth<ProductProperties>('post', `${ApiUrl.Main}${ApiUrl.PostAdd}`, properties);
    }

    async update(id: number, properties: ProductProperties): Promise<ProductProperties> {
        return this.call<ProductProperties>('put', `${ApiUrl.Main}${ApiUrl.PutById}${id}`, properties);
    }

    async deleteById(id: number): Promise<void> {
        return this.callAuth<void>('delete', `${ApiUrl.Main}${ApiUrl.DeleteById}${id}`);
    }
}

export default ProductPropertiesModule;
