# Care Plus API - Postman Collection Guide

## Overview

This guide explains how to use the complete Postman collection for the Care Plus API with JWT authentication, security testing, and automated workflows.

## Files Generated

1. **Care-Plus-API-JWT-Complete.postman_collection.json** - Complete collection with all endpoints
2. **Care-Plus-Environment.postman_environment.json** - Environment variables
3. **POSTMAN-GUIDE.md** - This documentation file

## Import Instructions

### 1. Import Collection
1. Open Postman
2. Click **Import** button
3. Select **File** tab
4. Choose `Care-Plus-API-JWT-Complete.postman_collection.json`
5. Click **Import**

### 2. Import Environment
1. In Postman, click the **Environments** tab
2. Click **Import**
3. Select `Care-Plus-Environment.postman_environment.json`
4. Click **Import**
5. Select the "Care Plus API - JWT Environment" from the environment dropdown

## Collection Structure

The collection is organized into 8 main folders:

### 1. Authentication
- **1.1 Register User** - Create new user account
- **1.2 Login - Success** - Login and get JWT token
- **1.3 Validate Token** - Verify JWT token validity
- **1.4 Logout** - Invalidate current session

### 2. Protected Routes
- **2.1 List All Patients** - Get all patients (requires JWT)
- **2.2 Create Patient** - Create new patient (requires JWT)
- **2.3 Get Patient by ID** - Get specific patient (requires JWT)
- **2.4 Update Patient** - Update patient data (requires JWT)
- **2.5 Delete Patient** - Delete patient (requires JWT)

### 3. Authentication Tests
- **3.1 Login - Invalid Email** - Test invalid email format
- **3.2 Login - Wrong Password** - Test incorrect password
- **3.3 Login - Non-existent User** - Test unknown user

### 4. Password Validation Tests
- **4.1 Register - Password Too Short** - < 8 characters
- **4.2 Register - Password Too Long** - > 32 characters
- **4.3 Register - No Uppercase** - Missing uppercase letter
- **4.4 Register - No Lowercase** - Missing lowercase letter
- **4.5 Register - No Number** - Missing number
- **4.6 Register - No Special Character** - Missing special char
- **4.7 Register - Valid Password** - All requirements met

### 5. Authorization Tests
- **5.1 Protected Route - No Token** - Access without JWT
- **5.2 Protected Route - Invalid Token** - Access with invalid JWT
- **5.3 Protected Route - Malformed Token** - Access with malformed JWT

### 6. Session Management Tests
- **6.1 Session Inactivity Test** - Test 1+ minute inactivity
- **6.2 Session After Inactivity** - Verify session expiration
- **6.3 Session Longevity Test** - Test 5+ minute session limit

### 7. Security Tests
- **7.1 SQL Injection Test** - Test SQL injection protection
- **7.2 Token Tampering Test** - Test JWT tampering detection
- **7.3 Token Reuse After Logout** - Test token invalidation

### 8. Complete Flow Test
- **8.1 Register New User** - Create test user
- **8.2 Login with New User** - Authenticate test user
- **8.3 Access Protected Resource** - Use protected endpoints
- **8.4 Create Patient with Flow Token** - Create data with JWT
- **8.5 Logout Flow User** - Clean up test session

## Environment Variables

| Variable | Type | Description |
|-----------|------|-------------|
| `base_url` | string | API base URL (default: http://localhost:8080) |
| `jwt_token` | secret | JWT authentication token (auto-set) |
| `token_type` | string | Token type (always "Bearer") |
| `login_time` | string | Login timestamp (for session tracking) |
| `user_email` | string | Default admin email (admin@careplus.com) |
| `user_password` | secret | Default admin password (Admin@123) |

## Usage Instructions

### Quick Start

1. **Start the API:**
   ```bash
   cd java-challege-care-plus
   ./mvnw.cmd spring-boot:run
   ```

2. **Run Authentication Flow:**
   - Execute **1.1 Register User** (optional)
   - Execute **1.2 Login - Success** (saves JWT token)
   - Execute **2.1 List All Patients** (uses saved token)

3. **Run Security Tests:**
   - Execute all tests in folder **3. Authentication Tests**
   - Execute all tests in folder **4. Password Validation Tests**
   - Execute all tests in folder **5. Authorization Tests**

### Automated Features

#### Token Management
- JWT tokens are automatically saved after successful login
- Authorization headers are automatically added to requests
- Tokens are cleared after logout

#### Session Tracking
- Login time is tracked for session management
- Inactivity warnings are logged in console
- Session expiration tests are automated

#### Variable Management
- Created patient IDs are automatically stored
- Test user credentials are saved for reuse
- Flow test variables are managed separately

## Test Cases Coverage

### Authentication (CT01-CT04)
- CT01: Valid login credentials
- CT02: Invalid email format
- CT03: Non-existent email
- CT04: Incorrect password

### Password Validation (CT05-CT11)
- CT05: Password < 8 characters
- CT06: Password > 32 characters
- CT07: No uppercase letter
- CT08: No lowercase letter
- CT09: No number
- CT10: No special character
- CT11: Valid password format

### JWT Token (CT12-CT15)
- CT12: Valid token access
- CT13: Missing token
- CT14: Invalid token signature
- CT15: Expired token

### Session Management (CT16-CT18)
- CT16: Inactivity > 1 minute
- CT17: Session > 5 minutes
- CT18: Frequent usage validation

### Authorization (CT19-CT21)
- CT19: Protected endpoint access
- CT20: Unauthorized access attempt
- CT21: Valid authorization

### Security (CT22-CT24)
- CT22: SQL injection protection
- CT23: Token tampering detection
- CT24: Token reuse after logout

## Advanced Features

### Session Testing
The collection includes automated session testing:

1. **Inactivity Test**: Waits 2 minutes to test session expiration
2. **Longevity Test**: Tests 5+ minute session limits
3. **Activity Tracking**: Monitors session usage patterns

### Security Testing
Comprehensive security validation:

1. **Input Validation**: Tests SQL injection attempts
2. **Token Security**: Validates JWT tampering detection
3. **Session Security**: Tests token invalidation after logout

### Performance Monitoring
Built-in performance assertions:

- Response time < 3000ms
- Content type validation
- Security headers checking
- Session age warnings

## Troubleshooting

### Common Issues

1. **401 Unauthorized Errors**
   - Ensure you've run **1.2 Login - Success** first
   - Check that `jwt_token` environment variable is set
   - Verify token hasn't expired (15-minute limit)

2. **400 Bad Request Errors**
   - Check request body format
   - Validate password requirements
   - Ensure email format is correct

3. **Connection Errors**
   - Verify API is running on `http://localhost:8080`
   - Check `base_url` environment variable
   - Ensure no firewall blocking

### Debug Information

Enable console logging in Postman:
1. Open Postman Console (View > Show Postman Console)
2. Run requests to see detailed logs
3. Check for session warnings and errors

## Customization

### Adding New Tests

1. Duplicate existing request
2. Modify request body/parameters
3. Update test scripts as needed
4. Add to appropriate folder

### Modifying Environment

1. Select environment from dropdown
2. Click **Edit** to modify variables
3. Update `base_url` for different environments
4. Change default credentials if needed

### Test Data

Default test credentials:
- **Admin**: admin@careplus.com / Admin@123
- **Test Users**: Various formats for validation testing

## Best Practices

1. **Sequential Execution**: Run tests in order for proper session management
2. **Environment Reset**: Clear environment variables between test runs
3. **Token Refresh**: Re-login if token expires during testing
4. **Console Monitoring**: Watch console for session warnings
5. **Test Isolation**: Use different emails for registration tests

## API Documentation

Additional API documentation available at:
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Support

For issues with the Postman collection:
1. Check console logs for detailed errors
2. Verify API is running and accessible
3. Ensure environment variables are correctly set
4. Review test scripts for syntax errors

---

**Note**: This collection is designed for testing the Care Plus API with JWT authentication. Ensure the API is running with proper database configuration before executing tests.
