import { apiClient } from './api'

export interface ProductStock {
    id: string
    name: string
    sku: string
    quantity: number
    restockDate?: string
    status: 'In Stock' | 'Low Stock' | 'Out of Stock'
}

const MOCK_STOCK: ProductStock[] = [
    { id: '1', name: 'Wireless Mouse', sku: 'WM-001', quantity: 154, status: 'In Stock' },
    { id: '2', name: 'Mechanical Keyboard', sku: 'MK-002', quantity: 12, status: 'Low Stock', restockDate: '2023-11-15' },
    { id: '3', name: 'USB-C Monitor', sku: 'MON-003', quantity: 0, status: 'Out of Stock', restockDate: '2023-11-20' },
    { id: '4', name: 'Ergonomic Chair', sku: 'CHR-004', quantity: 45, status: 'In Stock' },
    { id: '5', name: 'Laptop Stand', sku: 'LST-005', quantity: 8, status: 'Low Stock' },
]

export const stockService = {
    async getStock(): Promise<ProductStock[]> {
        // Simulate API call
        return new Promise((resolve) => {
            setTimeout(() => resolve(MOCK_STOCK), 500)
        })
        // Real call: return apiClient.get('/stock').then(res => res.data)
    }
}
