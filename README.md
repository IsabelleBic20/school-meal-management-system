# School Meal Management System

A full-stack application for managing school meals, including schools, products, menus, and distributions with comprehensive inventory and stock management features.

## Features

- **School Management**: Create, read, update, and delete schools with student count tracking
- **Product Management**: Manage products with expiration dates and stock quantities
- **Menu Management**: Create menus with associated products for specific dates
- **Distribution Management**: Track product distributions to schools with automatic stock updates
- **Stock Validation**: Prevent distributions that exceed available stock
- **Expired Product Tracking**: Flag and identify expired products
- **Low Stock Alerts**: Configurable thresholds for low stock warnings
- **REST API**: Complete REST API with pagination support
- **JWT Authentication**: Secure endpoints with JWT tokens
- **Responsive UI**: Material UI-based responsive frontend

## Tech Stack

### Backend
- Java 21
- Spring Boot 3.2.0
- Spring Data JPA
- Spring Security with JWT
- Maven
- SQL Server
- Flyway for database migrations
- JUnit 5 & Mockito for testing

### Frontend
- React 18
- TypeScript
- Vite
- Material UI (MUI)
- React Router
- React Query (TanStack Query)
- Axios

### DevOps
- Docker
- Docker Compose
- Nginx

## Project Structure

```
school-meal-management-system/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/schoolmeal/
│   │   │   │   ├── config/
│   │   │   │   ├── controller/
│   │   │   │   ├── dto/
│   │   │   │   ├── entity/
│   │   │   │   ├── exception/
│   │   │   │   ├── mapper/
│   │   │   │   ├── repository/
│   │   │   │   ├── security/
│   │   │   │   ├── service/
│   │   │   │   └── SchoolMealManagementApplication.java
│   │   │   └── resources/
│   │   │       ├── application.yml
│   │   │       └── db/migration/
│   │   └── test/
│   │       └── java/com/schoolmeal/service/
│   ├── pom.xml
│   └── Dockerfile
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── services/
│   │   ├── types/
│   │   ├── hooks/
│   │   ├── App.tsx
│   │   ├── main.tsx
│   │   └── index.css
│   ├── package.json
│   ├── vite.config.ts
│   ├── tsconfig.json
│   ├── Dockerfile
│   ├── nginx.conf
│   └── index.html
├── docker-compose.yml
├── README.md
├── ER_DIAGRAM.md
└── API_DOCUMENTATION.md
```

## Getting Started

### Prerequisites

- Docker and Docker Compose
- Node.js 20+ (for local frontend development)
- Java 21 (for local backend development)
- Maven 3.9+

### Running with Docker Compose

1. **Clone the repository**
   ```bash
   cd school-meal-management-system
   ```

2. **Build and start all services**
   ```bash
   docker-compose up --build
   ```

3. **Access the application**
   - Frontend: http://localhost:3000
   - Backend API: http://localhost:8080/api
   - SQL Server: localhost:1433

4. **Stop the services**
   ```bash
   docker-compose down
   ```

### Running Locally

#### Backend

1. **Navigate to backend directory**
   ```bash
   cd backend
   ```

2. **Update application.yml** with your SQL Server connection details

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

#### Frontend

1. **Navigate to frontend directory**
   ```bash
   cd frontend
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Start development server**
   ```bash
   npm run dev
   ```

## API Endpoints

### Schools
- `GET /api/schools` - Get all schools (paginated)
- `GET /api/schools/{id}` - Get school by ID
- `POST /api/schools` - Create new school
- `PUT /api/schools/{id}` - Update school
- `DELETE /api/schools/{id}` - Delete school

### Products
- `GET /api/products` - Get all products (paginated)
- `GET /api/products/{id}` - Get product by ID
- `POST /api/products` - Create new product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product
- `GET /api/products/expired` - Get expired products
- `GET /api/products/low-stock` - Get low stock products

### Menus
- `GET /api/menus` - Get all menus (paginated)
- `GET /api/menus/{id}` - Get menu by ID
- `POST /api/menus` - Create new menu
- `PUT /api/menus/{id}` - Update menu
- `DELETE /api/menus/{id}` - Delete menu

### Distributions
- `GET /api/distributions` - Get all distributions (paginated)
- `GET /api/distributions/{id}` - Get distribution by ID
- `POST /api/distributions` - Create new distribution
- `PUT /api/distributions/{id}` - Update distribution
- `DELETE /api/distributions/{id}` - Delete distribution
- `GET /api/distributions/school/{schoolId}` - Get distributions by school
- `GET /api/distributions/date-range` - Get distributions by date range

## Database Schema

See [ER_DIAGRAM.md](ER_DIAGRAM.md) for the complete entity relationship diagram.

### Main Tables

- **schools** - School information with student count
- **products** - Product inventory with expiration dates
- **menus** - Menu definitions for specific dates
- **menu_products** - Junction table for menu-product relationships
- **distributions** - Product distributions to schools

## Testing

### Run Unit Tests

```bash
cd backend
mvn test
```

Tests include:
- SchoolServiceTest
- ProductServiceTest
- DistributionServiceTest

## Business Rules

1. **Stock Prevention**: Distributions cannot exceed available product stock
2. **Stock Updates**: Product stock is automatically decremented on distribution creation
3. **Expiration Tracking**: Products are flagged as expired when expiration date passes
4. **Low Stock Alerts**: Products are flagged when stock falls below configured threshold
5. **Cascade Deletion**: Deleting a school removes associated distributions

## Environment Variables

### Backend (application.yml)
- `SPRING_DATASOURCE_URL`: SQL Server connection URL
- `SPRING_DATASOURCE_USERNAME`: Database username
- `SPRING_DATASOURCE_PASSWORD`: Database password
- `JWT_SECRET`: JWT signing secret (change in production)
- `JWT_EXPIRATION`: JWT token expiration time in milliseconds

## Error Handling

The application implements global exception handling with detailed error responses:
- `EntityNotFoundException` (404) - Resource not found
- `InsufficientStockException` (400) - Stock validation failure
- `DuplicateEntityException` (409) - Duplicate entity creation
- `MethodArgumentNotValidException` (400) - Validation errors

## Security

- JWT-based authentication
- CORS enabled for cross-origin requests
- Password encoding with BCrypt
- Request validation with Bean Validation

## Performance

- Pagination support for all list endpoints
- Database indexes on frequently queried columns
- Lazy loading of relationships
- Query optimization with Spring Data JPA

## Contributing

1. Follow SOLID principles and Clean Code practices
2. Add unit tests for new features
3. Ensure all tests pass before submitting changes
4. Follow the existing code style and conventions

## License

This project is licensed under the MIT License.

## Support

For issues or questions, please create an issue in the repository.

## Deployment

See [DEPLOYMENT.md](DEPLOYMENT.md) for production deployment guidelines (to be created).
