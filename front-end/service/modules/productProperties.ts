import ServiceFactory from '~/service/factory';
import type { GroupModificationMode, ProductProperties, Product } from '~/models/product';

class ProductPropertiesModule extends ServiceFactory {

    getExampleData(): ProductProperties[] {
        return [
            {
                'product': {
                    'productId': 5,
                    'productName': ' Low Quality',
                    'productPropertiesId': 0,
                    'productDescription': 'What a quality',
                    'price': 99,
                    'productImage': 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAMAAABEpIrGAAAAAXNSR0IArs4c6QAAADBQTFRFAP//AAAAdAA0zgB0AJAANM4AdP80kAAA/wAA/1dXkDQA/1cA/5AAkJAAzs4A//8A7QqA3gAAABB0Uk5TAP///////////////////8BQi0MAAACaSURBVDiNrZPRDoAgCEV7kCH8/weHWokIWlv3qXVO4Op2HHOSxLmtOQC4iLnyCBeBG48EMYSv1rclQXCIQ0kCwAxQroyDmRpPcopmUEbNa4pRIrAG+/ycb6U+e+XZ0oUxt2AHTCMegcgYn4XtiuiUfwjhe3gvuEb/6rGgqrIa4BtDrXA2TO+uIg7UdM7JqtNBr5d8918YRd08AaKZEzDPiUojAAAAAElFTkSuQmCC'
                }, 'productPropertiesId': 0, 'productOptionGroups': [{
                    'name': 'Extras',
                    'isRequired': false,
                    'groupModificationMode': 'MODIFIABLE',
                    'availableOptionsState': 'MULTIPLE_OPTIONS_ALLOWED',
                    'productOptions': [{
                        'productOptionId': 0,
                        'groupId': 0,
                        'name': 'Something Yellow',
                        'image': 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAMAAABEpIrGAAAAAXNSR0IArs4c6QAAADBQTFRFAP//AAAAdAA0zgB0AJAANM4AdP80kAAA/wAA/1dXkDQA/1cA/5AAkJAAzs4A//8A7QqA3gAAABB0Uk5TAP///////////////////8BQi0MAAACVSURBVDiNrZNBEoAgDAM50P7/yZpQsCKa0bEDB8hqQyml/Bo14la2iCUC2SNWCOWegsis7/IBAKmTzv0B7DMTTR9ELBKRAPi7AF03bGJhEwGH+JgaKfzm8BkOIZpzWPXsczjwDvjZRbLgxjGZCMCYgoW2RrwFZApl8uGYslC61PKy9HXLhtEtp5tWt32RD6chj0/vY2xiuQtVQ0F7QAAAAABJRU5ErkJggg==',
                        'price': 12,
                        'accessible': true
                    }, {
                        'productOptionId': 0,
                        'groupId': 0,
                        'name': 'Something Green',
                        'image': 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAMAAABEpIrGAAAAAXNSR0IArs4c6QAAADBQTFRFAP//AAAAdAA0zgB0AJAANM4AdP80kAAA/wAA/1dXkDQA/1cA/5AAkJAAzs4A//8A7QqA3gAAABB0Uk5TAP///////////////////8BQi0MAAACWSURBVDiNrZPREYAgDENZgHb/bTWhYEU0p2cPPiBPG0op5deoEbeyRSwRyB6xQij3FERmfZcPAEiddO4PYJ+ZaPogYpGIBMDfBei6YRMLmwg4xMfUSOE3h89wCNGcw6pnn8OBd8DPLpIFN47JRADGFCy0NeItIFMokw/HlIXSpZaXpa9bNoxuOd20uu2LfDgNeXx6H2MDD9wg/ZJ3QvcAAAAASUVORK5CYII=',
                        'price': 54,
                        'accessible': true
                    }],
                    'productOptionGroupId': 0,
                    'productPropertiesId': 0
                }], 'description': ''
            }

        ];
    }

    async pushRaw(propObj: ProductProperties) {
        // console.log(JSON.stringify(propObj));
        // return;

        throw createError('deprecated');
        /*
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
                }*/
    }

    async getById(id: number): Promise<ProductProperties> {
        const prop = await this.api.prodProp.prop.getById(id);
        /*

                prop.productOptionGroups = await this.api.prodProp.prop.getGroupsByPropertiesId(id);

                for (let idx = 0; idx < prop.productOptionGroups.length; idx++) {
                    let group = prop.productOptionGroups[idx];
                    group.productOptions = await this.api.prodProp.group.getOptionsByGroupId(group.productOptionGroupId);
                }
        */

        return prop;
    }
}

export default ProductPropertiesModule;