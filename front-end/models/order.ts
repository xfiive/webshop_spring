import type { Product, ProductOption, ProductOptionGroup, ProductProperties } from '~/models/product';

export type Order ={
    count: number;
    product: Product;
    orderGroups: OrderGroup[];
    backedPrice: number;
    refProdProp? : ProductProperties
}

export type OrderGroup = {
    group: ProductOptionGroup;
    options: ProductOption[];
}