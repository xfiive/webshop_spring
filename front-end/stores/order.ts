// stores/orderStore.ts

import { defineStore } from 'pinia';
import type { Order } from '~/models/order';

export const useOrderStore = defineStore('order', {
    state: () => ({
        orders: [] as Order[],
    }),
    actions: {
        addOrder(order: Order) {
            this.orders.push(order);
        },
        updateOrderCount(index: number, count: number) {
            const order = this.orders[index];
            if (order) {
                order.count = count;
            }
        },
        removeOrder(index: number) {
            this.orders.splice(index, 1);
        },
    },
});
