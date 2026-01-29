import { apiClient } from './api'

export interface Order {
    id: string
    orderNumber: string
    customer: string
    date: string
    amount: number
    status: 'Completed' | 'Pending' | 'Processing' | 'Cancelled'
}

const MOCK_ORDERS: Order[] = [
    { id: '1', orderNumber: 'ORD-2023-001', customer: 'Acme Corp', date: '2023-10-25', amount: 1250.00, status: 'Completed' },
    { id: '2', orderNumber: 'ORD-2023-002', customer: 'Globex Inc', date: '2023-10-26', amount: 450.50, status: 'Processing' },
    { id: '3', orderNumber: 'ORD-2023-003', customer: 'Soylent Corp', date: '2023-10-27', amount: 3200.00, status: 'Pending' },
    { id: '4', orderNumber: 'ORD-2023-004', customer: 'Initech', date: '2023-10-24', amount: 150.00, status: 'Cancelled' },
]

export const orderService = {
    async getOrders(): Promise<Order[]> {
        return new Promise((resolve) => {
            setTimeout(() => resolve(MOCK_ORDERS), 600)
        })
    }
}
