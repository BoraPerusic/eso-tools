export interface ChatMessage {
    id: string
    role: 'user' | 'assistant'
    content: string
    timestamp: Date
}

export type MessageStreamHandler = (chunk: string) => void
export type DoneHandler = () => void

export const agentService = {
    async sendMessageStream(
        prompt: string,
        onChunk: MessageStreamHandler,
        onDone: DoneHandler
    ) {
        // Simulate network latency
        const delay = (ms: number) => new Promise(r => setTimeout(r, ms))

        await delay(600) // Initial thinking time

        // Mock response logic based on keywords
        let responseText = ""

        if (prompt.toLowerCase().includes('stock')) {
            responseText = "Based on the database, here is the stock status for **Wireless Mouse**:\n\n- It is currently **in stock** (154 units).\n- The SKU is `WM-001`.\n\nIs there anything else you need to know about inventory?"
        } else if (prompt.toLowerCase().includes('order')) {
            responseText = "I found recent orders for **Acme Corp**.\n\n| Order # | Amount | Status |\n|---|---|---|\n| ORD-2023-001 | $1,250.00 | Completed |\n\nWould you like to see the details of a specific order?"
        } else {
            responseText = "I understand you're asking about: _" + prompt + "_.\n\nI can help you check **Stock Levels**, **Order Status**, or **Returns**. Please ask specific questions like:\n\n> What is the stock of Wireless Mouse?"
        }

        // Simulate streaming character by character or word by word
        const chunks = responseText.split(/(?=[ \n])/) // Split by words/spaces

        for (const chunk of chunks) {
            onChunk(chunk)
            // Random typing delay
            await delay(Math.random() * 30 + 10)
        }

        onDone()
    }
}
