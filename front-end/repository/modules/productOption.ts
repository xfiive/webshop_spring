import HttpFactory from '~/repository/factory';
import type { ProductOption } from '~/models/product';

enum ApiUrl {
    Main = '/products/option',
    GetAll = '/find/all',
    GetById = '/find/',
    PostAdd = '/add',
    PutById = '/update/',
    DeleteById = '/delete/'
}

class ProductOptionModule extends HttpFactory {
    async getAll(): Promise<ProductOption[]> {
        return this.call<ProductOption[]>('get', `${ApiUrl.Main}${ApiUrl.GetAll}`);
    }

    async getById(id: number): Promise<ProductOption> {
        return this.call<ProductOption>('get', `${ApiUrl.Main}${ApiUrl.GetById}${id}`);
    }

    async add(productOption: ProductOption): Promise<ProductOption> {
        return this.callAuth<ProductOption>('post', `${ApiUrl.Main}${ApiUrl.PostAdd}`, productOption);
    }

    async update(id: number, productOption: ProductOption): Promise<ProductOption> {
        return this.callAuth<ProductOption>('put', `${ApiUrl.Main}${ApiUrl.PutById}${id}`, productOption);
    }

    async deleteById(id: number): Promise<void> {
        return this.callAuth<void>('delete', `${ApiUrl.Main}${ApiUrl.DeleteById}${id}`);
    }
}

export default ProductOptionModule;
