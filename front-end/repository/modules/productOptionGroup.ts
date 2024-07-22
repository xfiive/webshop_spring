import HttpFactory from '~/repository/factory';
import type { ProductOptionGroup, ProductOption } from '~/models/product';

enum ApiUrl {
    Main = '/products/group',
    GetAll = '/find/all',
    GetById = '/find/',
    GetOptionsByGroupId = '/find/',
    PostAdd = '/add',
    PutByIdProperties = '/change/',
    PutById = '/change/',
    DeleteById = '/delete/',
}

class ProductOptionGroupModule extends HttpFactory {
    async getAll(): Promise<ProductOptionGroup[]> {
        return this.call<ProductOptionGroup[]>('get', `${ApiUrl.Main}${ApiUrl.GetAll}`);
    }

    async getById(id: number): Promise<ProductOptionGroup> {
        return this.call<ProductOptionGroup>('get', `${ApiUrl.Main}${ApiUrl.GetById}${id}`);
    }

    async getOptionsByGroupId(groupId: number): Promise<ProductOption[]> {
        return this.call<ProductOption[]>('get', `${ApiUrl.Main}${ApiUrl.GetOptionsByGroupId}${groupId}/options`);
    }

    async add(optionGroup: ProductOptionGroup): Promise<ProductOptionGroup> {
        return this.callAuth<ProductOptionGroup>('post', `${ApiUrl.Main}${ApiUrl.PostAdd}`, optionGroup);
    }

    async updatePropertiesId(id: number, propertiesId: number): Promise<ProductOptionGroup> {
        return this.callAuth<ProductOptionGroup>('put', `${ApiUrl.Main}${ApiUrl.PutByIdProperties}${id}/propertiesId`, { propertiesId });
    }

    async update(id: number, optionGroup: ProductOptionGroup): Promise<ProductOptionGroup> {
        return this.callAuth<ProductOptionGroup>('put', `${ApiUrl.Main}${ApiUrl.PutById}${id}`, optionGroup);
    }

    async deleteById(id: number): Promise<void> {
        return this.callAuth<void>('delete', `${ApiUrl.Main}${ApiUrl.DeleteById}${id}`);
    }
}

export default ProductOptionGroupModule;
