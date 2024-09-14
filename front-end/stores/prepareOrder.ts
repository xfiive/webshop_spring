// stores/prepareOrderStore.ts

import { defineStore } from 'pinia';
import type { ProductOption, ProductOptionGroup, ProductProperties } from '~/models/product';
import type { Order, OrderGroup } from '~/models/order';

export const usePrepareOrderStore = defineStore('prepareOrder', {
    state: () => ({
        tmpOrderData: new Map<ProductOptionGroup, ProductOption[]>(),
    }),
    getters: {
        getAdditionalCost(): number {
            let sum = 0;

            if (!this.tmpOrderData) return 0;

            this.tmpOrderData.forEach((options) => {
                options.forEach((option) => {
                    sum += option.price;
                });
            });

            return sum;
        },
    },
    actions: {
        setTmpOrderData(group: ProductOptionGroup, options: ProductOption[]) {
            this.tmpOrderData.set(group, options);
        },
        resetTmpOrderData() {
            this.tmpOrderData.clear();
        },
        isValidateOrder(properties: ProductProperties): boolean {
            for (const group of properties.productOptionGroups) {
                if (group.isRequired && (!this.tmpOrderData.get(group)?.length)) {
                    return false;
                }
            }
            return true;
        },
        pushOrderFromTmp(properties: ProductProperties) {
            if (properties.product == null)
                return;

            const orderStore = useOrderStore();

            // Prepare order groups
            const orderGroups: OrderGroup[] = Array.from(this.tmpOrderData.entries()).map(
                ([group, options]) => ({
                    group,
                    options: [...options],
                })
            );

            // Create a new order
            const newOrder: Order = {
                count: 1,
                product: properties.product,
                orderGroups,
                backedPrice: this.getAdditionalCost + properties.product.price
            };

            // Add the new order to the orderStore
            orderStore.addOrder(newOrder);
        },
    },
});
