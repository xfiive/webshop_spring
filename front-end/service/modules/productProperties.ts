import ServiceFactory from '~/service/factory';
import type { GroupModificationMode, ProductProperties, Product } from '~/models/product';

class ProductPropertiesModule extends ServiceFactory {

    async pushRaw(propObj: ProductProperties) {
        const product = propObj.product;
        const api = this.api;

        const newProp = await api.prodProp.prop.add({
            description: '',
            product: propObj.product,
            productPropertiesId: 0,
            productOptionGroups: []
        }).catch(() => {
            throw createError({ message: 'Failed to create ProductProperties' });
        });

        const revertProp = async () => await api.prodProp.prop.deleteById(newProp.productPropertiesId);
        let revertGroupsIDs = [] as number[];
        const revertGroups = async () => {
            for (const id of revertGroupsIDs)
                await api.prodProp.group.deleteById(id);

        };
        let revertOptionsIDs = [] as number[];
        const revertOptions = async () => {
            for (const id of revertGroupsIDs)
                await api.prodProp.option.deleteById(id);
        };

        for (const group of propObj.productOptionGroups) {
            const newGroup = await api.prodProp.group.add({
                productOptions: group.productOptions,
                productOptionGroupId: 0,
                groupModificationMode: group.groupModificationMode.toUpperCase() as GroupModificationMode,
                name: group.name,
                isRequired: group.isRequired,
                productPropertiesId: newProp.productPropertiesId
            }).catch(async () => {
                await revertOptions();
                await revertGroups();
                await revertProp();

                throw createError({ message: 'Failed to create ProductOptionGroup', data: group });
            });

            revertGroupsIDs.push(newGroup.productOptionGroupId);

            for (const option of group.productOptions) {
                const newOption = await api.prodProp.option.add({
                    accessible: option.accessible,
                    name: option.name,
                    image: option.image,
                    price: option.price,
                    productOptionId: 0,
                    groupId: newGroup.productOptionGroupId
                }).catch(async () => {
                    await revertOptions();
                    await revertGroups();
                    await revertProp();

                    throw createError({ message: 'Failed to create ProductOptionGroup', data: option });
                });

                revertOptionsIDs.push(newOption.productOptionId);
            }
        }

        const newProduct = await api.product.put(product.productId, {
            productPropertiesId: newProp.productPropertiesId,
            productId: product.productId,
            productDescription: product.productDescription,
            price: product.price,
            productImage: product.productImage,
            productName: product.productName
        });
        
        if (newProp.productPropertiesId !== newProduct.productPropertiesId) {
            await revertOptions();
            await revertGroups();
            await revertProp();

            throw createError({ message: 'Failed to update productPropertiesId in product', data: newProduct });
        }
    }

    async getById(id: number): Promise<ProductProperties>{
        const prop = await this.api.prodProp.prop.getById(id);

        prop.productOptionGroups = await this.api.prodProp.prop.getGroupsByPropertiesId(id);

        for (let idx = 0; idx < prop.productOptionGroups.length; idx++) {
            let group = prop.productOptionGroups[idx];
            group.productOptions = await this.api.prodProp.group.getOptionsByGroupId(group.productOptionGroupId);
        }

        return prop;
    }
}

export default ProductPropertiesModule;