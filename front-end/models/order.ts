import type { Product, ProductOption, ProductOptionGroup } from '~/models/product';

export type Order ={
    count: number;
    product: Product;
    orderGroups: OrderGroup[];
    backedPrice: number;
}

export type OrderGroup = {
    group: ProductOptionGroup;
    options: ProductOption[];
}