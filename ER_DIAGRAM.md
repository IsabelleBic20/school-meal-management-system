# ER Diagram - School Meal Management System

## Entity Relationship Diagram

```
┌─────────────────────┐
│      SCHOOLS        │
├─────────────────────┤
│ id (PK)             │
│ name                │
│ address             │
│ student_count       │
│ created_at          │
│ updated_at          │
└─────────────────────┘
         │
         │ 1:N
         │
         ├──────────────────────────┐
         │                          │
         ▼                          ▼
┌─────────────────────┐    ┌──────────────────────┐
│  DISTRIBUTIONS      │    │   MENU_PRODUCTS      │
├─────────────────────┤    ├──────────────────────┤
│ id (PK)             │    │ id (PK)              │
│ school_id (FK)──────┼────┼─ SCHOOLS (PK)       │
│ product_id (FK)─────┼────┼─ PRODUCTS (PK)      │
│ quantity            │    │ quantity             │
│ delivery_date       │    │                      │
│ created_at          │    └──────────────────────┘
│ updated_at          │              │
└─────────────────────┘              │ N:1
         ▲                           │
         │                           ▼
         │                    ┌──────────────────────┐
         │                    │      MENUS           │
         │                    ├──────────────────────┤
         │                    │ id (PK)              │
         │                    │ name                 │
         │                    │ date                 │
         │                    │ created_at           │
         │                    │ updated_at           │
         │                    └──────────────────────┘
         │
         └─────────────────────────────────────────────┐
                                                      │
                                                      ▼
                                           ┌──────────────────────┐
                                           │     PRODUCTS         │
                                           ├──────────────────────┤
                                           │ id (PK)              │
                                           │ name                 │
                                           │ unit                 │
                                           │ stock_quantity       │
                                           │ expiration_date      │
                                           │ low_stock_threshold  │
                                           │ created_at           │
                                           │ updated_at           │
                                           └──────────────────────┘
```

## Relationships

### Schools → Distributions (1:N)
- A school can have multiple distributions
- Delete: CASCADE (deleting a school deletes its distributions)

### Products → Distributions (1:N)
- A product can be distributed to multiple schools
- Delete: CASCADE (deleting a product deletes its distributions)

### Products ↔ Menus (M:N)
- A product can be in multiple menus
- A menu can have multiple products
- Junction table: menu_products
- Delete: CASCADE (deleting either entity removes the relationship)

### Products ↔ Menus through Menu_Products (M:N)
- Allows tracking of product quantities in specific menus

## Indexes

- `IDX_MenuProducts_MenuId` - on menu_products(menu_id)
- `IDX_MenuProducts_ProductId` - on menu_products(product_id)
- `IDX_Distributions_SchoolId` - on distributions(school_id)
- `IDX_Distributions_ProductId` - on distributions(product_id)
- `IDX_Distributions_DeliveryDate` - on distributions(delivery_date)
- `IDX_Products_ExpirationDate` - on products(expiration_date)
- `IDX_Products_StockThreshold` - on products(stock_quantity, low_stock_threshold)

## Database Normalization

The schema follows Third Normal Form (3NF):

1. **First Normal Form (1NF)**
   - All attributes contain atomic values
   - No repeating groups

2. **Second Normal Form (2NF)**
   - All non-key attributes depend on the entire primary key
   - No partial dependencies

3. **Third Normal Form (3NF)**
   - No transitive dependencies between non-key attributes
   - Each non-key attribute depends directly on the primary key

## Data Constraints

- **Primary Keys**: All tables have an IDENTITY primary key
- **Foreign Keys**: Referential integrity enforced with cascade delete
- **Not Null**: All key fields are NOT NULL
- **Unique Constraints**:
  - schools.name (unique)
  - products.name (unique)
  - menu_products (menu_id, product_id) - composite unique
- **Default Values**:
  - created_at, updated_at: GETDATE()
  - low_stock_threshold: 10

## Growth Considerations

The schema is designed to handle:
- Thousands of schools
- Tens of thousands of products
- Millions of distributions
- Historical tracking with created_at/updated_at timestamps
