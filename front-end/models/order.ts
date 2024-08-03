import type { ProductOption, ProductOptionGroup } from '~/models/product';

export type Order ={
    count: number;
    product: Product;
    orderGroups: OrderGroup[];
}

export type OrderGroup = {
    group: ProductOptionGroup;
    options: ProductOption[];
}