# Quick Start Guide

## Prerequisites

- Docker and Docker Compose installed
- Git installed

## Running the Project

### Option 1: Using Docker Compose (Recommended)

1. **Navigate to project root**
   ```bash
   cd school-meal-management-system
   ```

2. **Build and start all services**
   ```bash
   docker-compose up --build
   ```

3. **Wait for all services to be healthy** (usually 30-60 seconds)
   - SQL Server needs to initialize the database
   - Backend needs to run Flyway migrations
   - Frontend needs to build

4. **Access the application**
   - **Frontend**: http://localhost:3000
   - **Backend API**: http://localhost:8080/api
   - **SQL Server**: localhost:1433 (sa / YourPassword123!)

5. **Test the API**
   ```bash
   # Get all schools
   curl http://localhost:8080/api/schools
   
   # Create a school
   curl -X POST http://localhost:8080/api/schools \
     -H "Content-Type: application/json" \
     -d '{
       "name": "Test School",
       "address": "123 Main St",
       "studentCount": 500
     }'
   ```

6. **Stop the services**
   ```bash
   docker-compose down
   ```

### Option 2: Running Locally

#### Backend Setup

1. **Prerequisites**
   - Java 21
   - Maven 3.9+
   - SQL Server 2022 or Express edition

2. **Navigate to backend**
   ```bash
   cd backend
   ```

3. **Update connection string in `src/main/resources/application.yml`**
   ```yaml
   spring:
     datasource:
       url: jdbc:sqlserver://localhost:1433;databaseName=SchoolMealDB;encrypt=true;trustServerCertificate=true
       username: sa
       password: YourPassword123!
   ```

4. **Build and run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

#### Frontend Setup

1. **Prerequisites**
   - Node.js 20+
   - npm or yarn

2. **Navigate to frontend**
   ```bash
   cd frontend
   ```

3. **Install dependencies**
   ```bash
   npm install
   ```

4. **Start development server**
   ```bash
   npm run dev
   ```

5. **Frontend will be available at** http://localhost:5173

## Project Features

### ✅ Implemented Features

1. **Backend (Java Spring Boot)**
   - REST API with CRUD operations for all entities
   - JWT authentication support
   - Global exception handling
   - Pagination support
   - Bean validation
   - Unit tests with JUnit 5 and Mockito
   - Flyway database migrations
   - MapStruct for entity mapping

2. **Frontend (React + TypeScript)**
   - Responsive Material UI design
   - React Router for navigation
   - React Query for server state management
   - Axios for API calls
   - Custom hooks for data fetching
   - Dashboard, Schools, Products, Menus, and Distributions pages

3. **Database (SQL Server)**
   - Normalized schema following 3NF
   - Proper indexing for performance
   - Referential integrity with cascade deletes
   - Automatic timestamp tracking

4. **DevOps**
   - Multi-stage Docker builds
   - Docker Compose orchestration
   - Health checks configured
   - Network isolation

5. **Documentation**
   - Comprehensive README
   - ER diagram
   - Complete API documentation
   - Setup instructions

### Business Rules Implemented

✓ Prevent distributions exceeding available stock
✓ Automatically update stock after distribution
✓ Flag expired products
✓ Flag low-stock products with configurable threshold
✓ Cascade deletion for related entities

### API Endpoints

All endpoints follow RESTful conventions:

- `GET /api/schools` - List schools (paginated)
- `POST /api/schools` - Create school
- `GET /api/schools/{id}` - Get school details
- `PUT /api/schools/{id}` - Update school
- `DELETE /api/schools/{id}` - Delete school

Similar patterns for `/products`, `/menus`, and `/distributions`

## Database Initialization

The database schema is automatically created by Flyway migrations located in:
```
backend/src/main/resources/db/migration/V1__Initial_Schema.sql
```

Tables created:
- schools
- products
- menus
- menu_products
- distributions

## Testing

### Run Backend Unit Tests
```bash
cd backend
mvn test
```

Tests cover:
- SchoolService CRUD operations
- ProductService with stock validation
- DistributionService with business rules

## Troubleshooting

### 1. Port Already in Use
If ports 3000, 8080, or 1433 are already in use:
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# macOS/Linux
lsof -i :8080
kill -9 <PID>
```

### 2. Docker Build Fails
```bash
# Clean and rebuild
docker-compose down -v
docker-compose up --build --force-recreate
```

### 3. Backend Connection Error
Ensure SQL Server is running and accessible:
```bash
# Test connection from backend container
docker exec school-meal-backend sh -c \
  "java -jar app.jar" 2>&1 | grep -i database
```

### 4. Frontend API Connection Issues
Check that the backend API is running:
```bash
curl http://localhost:8080/api/schools
```

## Environment Configuration

### Backend
- **JWT Secret**: Update `jwt.secret` in `application.yml` for production
- **Database**: Connection string in `application.yml`
- **CORS**: Configured to accept requests from frontend

### Frontend
- **API Base URL**: Configured as `/api` (proxied in development)
- **Production Build**: Run `npm run build` to generate dist folder

## Next Steps

1. **Test the application**
   - Access http://localhost:3000
   - Navigate through Schools, Products pages
   - Use the API documentation to test endpoints

2. **Customize for your needs**
   - Update JWT secret for production
   - Modify database schema if needed
   - Add additional features

3. **Deploy to production**
   - Use production database connection string
   - Enable HTTPS
   - Configure environment variables
   - Set up monitoring and logging

## Support

For issues or questions:
1. Check the logs: `docker-compose logs -f [service-name]`
2. Review the [API_DOCUMENTATION.md](API_DOCUMENTATION.md)
3. Check the [ER_DIAGRAM.md](ER_DIAGRAM.md)
4. Review the main [README.md](README.md)

## Performance Tips

1. **Database Optimization**
   - Indexes are pre-configured
   - Pagination limits are set to 10 items by default
   - Adjust in request parameters if needed

2. **Frontend Optimization**
   - React Query caches data automatically
   - Adjust cache time in hooks if needed
   - React Router lazy loading for better performance

3. **Backend Optimization**
   - Connection pooling configured
   - Batch operations in service layer
   - Lazy loading for relationships

## Production Deployment Checklist

- [ ] Change JWT secret in application.yml
- [ ] Use production SQL Server instance
- [ ] Enable HTTPS/SSL
- [ ] Configure CORS properly for production domain
- [ ] Set up logging and monitoring
- [ ] Configure backup strategy for database
- [ ] Use environment variables for sensitive data
- [ ] Test all endpoints in production
- [ ] Monitor performance and optimize if needed

Enjoy using the School Meal Management System! 🎓🍽️
