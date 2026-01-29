import { apiClient } from './api'

export interface ReturnRequest {
    id: string
    returnNumber: string
    orderNumber: string
    customer: string
    date: string
    status: 'Pending' | 'Approved' | 'Rejected' | 'Refunded'
    reason: string
}

const MOCK_RETURNS: ReturnRequest[] = [
    { id: '1', returnNumber: 'RET-001', orderNumber: 'ORD-2023-001', customer: 'Acme Corp', date: '2023-10-28', status: 'Pending', reason: 'Defective' },
    { id: '2', returnNumber: 'RET-002', orderNumber: 'ORD-2023-005', customer: 'Massive Dynamic', date: '2023-10-20', status: 'Refunded', reason: 'Change of mind' },
]

export const returnService = {
    async getReturns(): Promise<ReturnRequest[]> {
        return new Promise((resolve) => {
            setTimeout(() => resolve(MOCK_RETURNS), 400)
        })
    }
}
