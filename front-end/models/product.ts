export type Product = {
    productId: number;
    productName: string;
    productPropertiesId: number;  // Note: Just the ID, not a full ProductProperties object
    productDescription: string | null;
    price: number;  // Using 'number' for BigDecimal equivalent
    productImage: string;
};


export type ProductProperties = {
    productPropertiesId: number;
    product: Product; // This type will need to be defined based on the 'Product' class.
    description: string;
    productOptionGroups: ProductOptionGroup[]; // This type will also need to be defined.
};


export type GroupModificationMode = /*'modifiable' | 'unmodifiable' | */'' | 'MODIFIABLE' | 'UNMODIFIABLE';
export type AvailableOptionsState = '' | 'SINGLE_OPTION_ALLOWED' | 'MULTIPLE_OPTIONS_ALLOWED';


export type ProductOptionGroup = {
    productOptionGroupId: number;
    productPropertiesId: number;
    name: string;
    isRequired: boolean;
    groupModificationMode: GroupModificationMode;  // Enum type to be defined
    availableOptionsState: AvailableOptionsState;
    productOptions: ProductOption[];  // Assuming these types are already defined
};


export type ProductOption = {
    productOptionId: number;
    groupId: number;
    name: string;
    image: string;  // Assuming this is a Base64 encoded string
    price: number;  // Using 'number' to handle BigDecimal
    accessible: boolean;
};

