# Package: org.tatrman.esotools.api.domain

## Description
Contains the data classes (models) representing the business entities and Data Transfer Objects (DTOs) used within the API Server.

## Key Classes

### `ProductStock`
- **Represents**: Current stock level of a product and its future restock schedule.
- **Fields**: `productId`, `currentStock`, `productType`, `restockSchedule` (List of `RestockItem`).

### `OrderStatus`
- **Represents**: The status and details of a customer order.
- **Fields**: `orderId`, `status`, `paymentStatus`, `deliveryDate`, `totalAmount`.

### `ReturnStatus`
- **Represents**: A return request associated with a customer.
- **Fields**: `returnId`, `originalOrderId`, `status`, `resolutionType`.

### `Serializers.kt`
- **Description**: Custom serializers for `kotlinx.serialization`.
- **Key Serializer**: `LocalDateSerializer` handles `java.time.LocalDate` <-> String (ISO-8601) conversion.
