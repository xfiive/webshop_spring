import HttpFactory from '~/repository/factory';
import type { Product } from '~/models/product';

enum ApiUrl {
    Main = '/products',
    GetAll = '/find/all',
    GetById = '/find/',
    GetAllByName = '/find/all/',
    PostAdd = '/add',
    PutById = '/change/',
    DeleteById = '/delete/',
}


class ProductModule extends HttpFactory {
    async getAll(): Promise<Product[]> {
        return this.call<Product[]>('GET', `${ApiUrl.Main}${ApiUrl.GetAll}`);
    }

    async add(product: Product): Promise<Product> {
        return this.callAuth<Product>('POST', `${ApiUrl.Main}${ApiUrl.PostAdd}`, product);
    }

    async put(productId: number, product: Product): Promise<Product> {
        return this.callAuth<Product>('PUT', `${ApiUrl.Main}${ApiUrl.PutById}${productId}`, product);
    }

    async deleteById(productId: number): Promise<void> {
        return this.callAuth<void>('DELETE', `${ApiUrl.Main}${ApiUrl.DeleteById}${productId}`);
    }
}

export default ProductModule;