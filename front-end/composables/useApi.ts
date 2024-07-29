import type { IApiInstance } from '~/plugins/api';


export const useApi = (): IApiInstance => useNuxtApp().$api;
