# API Documentation - School Meal Management System

## Base URL
```
http://localhost:8080/api
```

## Authentication
All endpoints require a valid JWT token in the Authorization header:
```
Authorization: Bearer <token>
```

## Pagination
List endpoints support pagination with the following query parameters:
- `page` (optional, default: 0) - Zero-based page number
- `size` (optional, default: 10) - Number of records per page

Response format:
```json
{
  "content": [...],
  "totalPages": 5,
  "totalElements": 50,
  "currentPage": 0,
  "pageSize": 10
}
```

## Schools Endpoints

### List All Schools
```
GET /schools?page=0&size=10
```

**Response:** 200 OK
```json
{
  "content": [
    {
      "id": 1,
      "name": "Primary School A",
      "address": "123 Main St",
      "studentCount": 500
    }
  ],
  "totalPages": 1,
  "totalElements": 1,
  "currentPage": 0,
  "pageSize": 10
}
```

### Get School by ID
```
GET /schools/{id}
```

**Parameters:**
- `id` (path, required) - School ID

**Response:** 200 OK
```json
{
  "id": 1,
  "name": "Primary School A",
  "address": "123 Main St",
  "studentCount": 500
}
```

### Create School
```
POST /schools
```

**Request Body:**
```json
{
  "name": "Primary School A",
  "address": "123 Main St",
  "studentCount": 500
}
```

**Response:** 201 Created
```json
{
  "id": 1,
  "name": "Primary School A",
  "address": "123 Main St",
  "studentCount": 500
}
```

**Validation Errors:**
- School name cannot be blank
- Address cannot be blank
- Student count must be positive

### Update School
```
PUT /schools/{id}
```

**Parameters:**
- `id` (path, required) - School ID

**Request Body:**
```json
{
  "name": "Primary School A",
  "address": "456 Oak Ave",
  "studentCount": 600
}
```

**Response:** 200 OK
```json
{
  "id": 1,
  "name": "Primary School A",
  "address": "456 Oak Ave",
  "studentCount": 600
}
```

### Delete School
```
DELETE /schools/{id}
```

**Parameters:**
- `id` (path, required) - School ID

**Response:** 204 No Content

---

## Products Endpoints

### List All Products
```
GET /products?page=0&size=10
```

**Response:** 200 OK
```json
{
  "content": [
    {
      "id": 1,
      "name": "Rice",
      "unit": "kg",
      "stockQuantity": 100,
      "expirationDate": "2024-12-31",
      "lowStockThreshold": 10,
      "expired": false,
      "lowStock": false
    }
  ],
  "totalPages": 1,
  "totalElements": 1,
  "currentPage": 0,
  "pageSize": 10
}
```

### Get Product by ID
```
GET /products/{id}
```

**Response:** 200 OK
```json
{
  "id": 1,
  "name": "Rice",
  "unit": "kg",
  "stockQuantity": 100,
  "expirationDate": "2024-12-31",
  "lowStockThreshold": 10,
  "expired": false,
  "lowStock": false
}
```

### Create Product
```
POST /products
```

**Request Body:**
```json
{
  "name": "Rice",
  "unit": "kg",
  "stockQuantity": 100,
  "expirationDate": "2024-12-31",
  "lowStockThreshold": 10
}
```

**Response:** 201 Created

### Update Product
```
PUT /products/{id}
```

**Request Body:**
```json
{
  "name": "Rice",
  "unit": "kg",
  "stockQuantity": 150,
  "expirationDate": "2024-12-31",
  "lowStockThreshold": 15
}
```

**Response:** 200 OK

### Delete Product
```
DELETE /products/{id}
```

**Response:** 204 No Content

### Get Expired Products
```
GET /products/expired?page=0&size=10
```

**Response:** 200 OK
```json
{
  "content": [
    {
      "id": 2,
      "name": "Flour",
      "unit": "kg",
      "stockQuantity": 50,
      "expirationDate": "2024-01-15",
      "lowStockThreshold": 10,
      "expired": true,
      "lowStock": false
    }
  ],
  "totalPages": 1,
  "totalElements": 1,
  "currentPage": 0,
  "pageSize": 10
}
```

### Get Low Stock Products
```
GET /products/low-stock?page=0&size=10
```

**Response:** 200 OK
```json
{
  "content": [
    {
      "id": 3,
      "name": "Sugar",
      "unit": "kg",
      "stockQuantity": 5,
      "expirationDate": "2024-06-30",
      "lowStockThreshold": 10,
      "expired": false,
      "lowStock": true
    }
  ],
  "totalPages": 1,
  "totalElements": 1,
  "currentPage": 0,
  "pageSize": 10
}
```

---

## Menus Endpoints

### List All Menus
```
GET /menus?page=0&size=10
```

**Response:** 200 OK
```json
{
  "content": [
    {
      "id": 1,
      "name": "Monday Lunch",
      "date": "2024-02-26",
      "products": [
        {
          "productId": 1,
          "productName": "Rice",
          "quantity": 50
        }
      ]
    }
  ],
  "totalPages": 1,
  "totalElements": 1,
  "currentPage": 0,
  "pageSize": 10
}
```

### Get Menu by ID
```
GET /menus/{id}
```

**Response:** 200 OK

### Create Menu
```
POST /menus
```

**Request Body:**
```json
{
  "name": "Monday Lunch",
  "date": "2024-02-26",
  "products": [
    {
      "productId": 1,
      "quantity": 50
    }
  ]
}
```

**Response:** 201 Created

### Update Menu
```
PUT /menus/{id}
```

**Request Body:**
```json
{
  "name": "Monday Lunch Updated",
  "date": "2024-02-26",
  "products": [
    {
      "productId": 1,
      "quantity": 60
    }
  ]
}
```

**Response:** 200 OK

### Delete Menu
```
DELETE /menus/{id}
```

**Response:** 204 No Content

---

## Distributions Endpoints

### List All Distributions
```
GET /distributions?page=0&size=10
```

**Response:** 200 OK
```json
{
  "content": [
    {
      "id": 1,
      "schoolId": 1,
      "schoolName": "Primary School A",
      "productId": 1,
      "productName": "Rice",
      "quantity": 50,
      "deliveryDate": "2024-02-26"
    }
  ],
  "totalPages": 1,
  "totalElements": 1,
  "currentPage": 0,
  "pageSize": 10
}
```

### Get Distribution by ID
```
GET /distributions/{id}
```

**Response:** 200 OK

### Create Distribution
```
POST /distributions
```

**Request Body:**
```json
{
  "schoolId": 1,
  "productId": 1,
  "quantity": 50,
  "deliveryDate": "2024-02-26"
}
```

**Response:** 201 Created

**Business Rules:**
- Quantity cannot exceed available product stock
- Stock is automatically decremented upon creation

**Error Responses:**
- 400 Bad Request: Insufficient stock
  ```json
  {
    "status": 400,
    "message": "Insufficient stock for product 'Rice'. Available: 30, Requested: 50",
    "timestamp": "2024-02-26T10:30:00",
    "path": "/distributions"
  }
  ```

### Update Distribution
```
PUT /distributions/{id}
```

**Request Body:**
```json
{
  "schoolId": 1,
  "productId": 1,
  "quantity": 60,
  "deliveryDate": "2024-02-26"
}
```

**Response:** 200 OK

**Note:** Stock is adjusted based on the quantity difference.

### Delete Distribution
```
DELETE /distributions/{id}
```

**Response:** 204 No Content

**Note:** Stock is restored when distribution is deleted.

### Get Distributions by School
```
GET /distributions/school/{schoolId}?page=0&size=10
```

**Parameters:**
- `schoolId` (path, required) - School ID

**Response:** 200 OK

### Get Distributions by Date Range
```
GET /distributions/date-range?startDate=2024-02-01&endDate=2024-02-29&page=0&size=10
```

**Parameters:**
- `startDate` (query, required) - Start date (YYYY-MM-DD)
- `endDate` (query, required) - End date (YYYY-MM-DD)
- `page` (query, optional) - Page number
- `size` (query, optional) - Page size

**Response:** 200 OK

---

## Error Responses

### 404 Not Found
```json
{
  "status": 404,
  "message": "School not found with id: 999",
  "timestamp": "2024-02-26T10:30:00",
  "path": "/schools/999"
}
```

### 409 Conflict
```json
{
  "status": 409,
  "message": "School with name 'Primary School A' already exists",
  "timestamp": "2024-02-26T10:30:00",
  "path": "/schools"
}
```

### 400 Bad Request (Validation)
```json
{
  "status": 400,
  "message": "name: School name cannot be blank, studentCount: Student count must be positive",
  "timestamp": "2024-02-26T10:30:00",
  "path": "/schools"
}
```

### 500 Internal Server Error
```json
{
  "status": 500,
  "message": "An unexpected error occurred: [error details]",
  "timestamp": "2024-02-26T10:30:00",
  "path": "/schools"
}
```

---

## Request/Response Content-Type
All endpoints expect and return `application/json` content type.

## HTTP Status Codes
- `200 OK` - Request successful
- `201 Created` - Resource successfully created
- `204 No Content` - Resource successfully deleted or updated
- `400 Bad Request` - Validation or business rule error
- `404 Not Found` - Resource not found
- `409 Conflict` - Duplicate entity
- `500 Internal Server Error` - Server error

## Example Usage with cURL

### Create a School
```bash
curl -X POST http://localhost:8080/api/schools \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer your_token" \
  -d '{
    "name": "Primary School A",
    "address": "123 Main St",
    "studentCount": 500
  }'
```

### Get All Schools
```bash
curl http://localhost:8080/api/schools?page=0&size=10 \
  -H "Authorization: Bearer your_token"
```

### Create Distribution
```bash
curl -X POST http://localhost:8080/api/distributions \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer your_token" \
  -d '{
    "schoolId": 1,
    "productId": 1,
    "quantity": 50,
    "deliveryDate": "2024-02-26"
  }'
```
