// stores/prepareOrderStore.ts

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
                backedPrice: this.getAdditionalCost + properties.product.price,
                refProdProp: properties
            };

            // Add the new order to the orderStore
            orderStore.addOrder(newOrder);

            // Reset temporary data
            this.resetTmpOrderData();
        },
        /**
         * Action to load an existing order into tmpOrderData for editing.
         * @param order The order to be edited.
         */
        pushOrderToTmp(order: Order) {
            // Clear existing tmpOrderData
            this.resetTmpOrderData();

            // Populate tmpOrderData with the order's groups and options
            for (const orderGroup of order.orderGroups) {
                const group = orderGroup.group;
                const options = orderGroup.options;
                this.tmpOrderData.set(group, [...options]);
            }
        },
        pushUpdatedOrderFromTmp(orderIndex: number, properties: ProductProperties) {
            const orderStore = useOrderStore();

            if (properties.product == null) return;

            // Prepare order groups
            const orderGroups: OrderGroup[] = Array.from(this.tmpOrderData.entries()).map(
                ([group, options]) => ({
                    group,
                    options: [...options],
                })
            );

            // Create updated order
            // Update the existing order in orderStore
            orderStore.orders[orderIndex] = {
                count: orderStore.orders[orderIndex].count,
                product: properties.product,
                orderGroups,
                backedPrice: properties.product.price + this.getAdditionalCost,
                refProdProp: properties
            };

            // Reset temporary data
            this.resetTmpOrderData();
        },
    },

});
