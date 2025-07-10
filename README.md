# PartnerFinder-Backend API

A powerful backend API for a collaborative partner-finding platform. This Spring Boot application enables authentication, role-based access (POSTER, COLLABORATOR, ADMIN), project and profile management, and comment moderation. Built with MySQL, Spring Security, and JWT.

---

## 🌐 Deployment URL

[https://partnerfinder.onrender.com](https://partnerfinder.onrender.com)

---

## 🔐 Authentication & Authorization

* Supports **JWT Authentication**
* Role-based URL access via Spring Security
* Secure login and token handling

---

## 📁 Features & Endpoints

### ✅ Authentication

* `POST /api/auth/register/user` - Register as a collaborator (user)
* `POST /api/auth/register/poster` - Register as a poster
* `POST /api/auth/login` - Login to receive JWT token

### 👤 User Profile Management

* `POST /api/users/{userId}/profileDetails` - Create profile (COLLABORATOR only)
* `GET /api/users/{userId}/profileDetails` - Fetch user profile info
* `POST /api/users/{userId}/qualifications` - Add qualifications (COLLABORATOR only)
* `POST /api/users/{userId}/skills` - Add skills (COLLABORATOR only)
* `GET /api/users` - Get all collaborators (POSTER or COLLABORATOR)
* `GET /api/users/{userId}` - Get specific collaborator (COLLABORATOR only)

### 🧑‍💻 Project Management

* `POST /api/projects` - Create a project (POSTER only)
* `GET /api/projects` - Fetch all public projects
* `GET /api/projects/{id}` - Get a specific project by ID
* `PUT /api/projects/{id}` - Update a project (POSTER only)
* `DELETE /api/projects/{id}` - Delete a project (POSTER or ADMIN only)

### 💬 Comment System

* `POST /api/projects/{projectId}/comments` - Add comment to project (Any authenticated user)
* `GET /api/projects/{projectId}/comments` - Fetch comments for a project (Hidden comments are filtered)
* `PATCH /api/comments/{commentId}/hide` - Hide comment (Only POSTER or ADMIN)
* `DELETE /api/comments/{commentId}` - Delete comment (Only by owner, POSTER, or ADMIN)

### 📊 Dashboard Access

* `GET /dashboard/user` - Collaborator dashboard (COLLABORATOR only)
* `GET /dashboard/poster` - Poster dashboard (POSTER only)
* `GET /dashboard/admin` - Admin dashboard (ADMIN only)

### 📂 Project Category Management

* `POST /api/projectCategories` - Create project category (ADMIN only)
* `GET /api/projectCategories` - Fetch project categories (ALL ROLES)
* `PUT /api/projectCategories/{id}` - Update project category (ADMIN only)
* `DELETE /api/projectCategories/{id}` - Delete project category (ADMIN only)

### 🧑 Poster Management

* `POST /api/posters` - Register poster
* `GET /api/posters` - Get all posters (POSTER or COLLABORATOR)
* `POST /api/posters/{posterId}/profileDetails` - Add poster profile (POSTER only)
* `POST /api/posters/{posterId}/projects` - Create project for poster (POSTER only)
* `GET /api/posters/{posterId}/projects` - Get poster's projects (POSTER or ADMIN)
* `DELETE /api/posters/{posterId}/projects/{projectId}` - Delete poster project (POSTER only)
* `GET /api/posters/{posterId}/myApplications` - Get project applications (POSTER or ADMIN)
* `PUT /api/posters/{posterId}/myApplications/{applicationId}` - Update project application (POSTER only)
* `GET /api/posters/members/{posterId}` - Get approved members (POSTER or ADMIN)
* `POST /api/posters/approve-member/{posterId}` - Approve application (POSTER only)
* `POST /api/posters/projects/{projectId}/applications/{applicationId}/reject` - Reject application (POSTER only)

### 🤝 Collaborator Project Interaction

* `POST /api/users/{userId}/projects/{projectId}/apply` - Apply to project (COLLABORATOR only)
* `POST /api/users/{userId}/projects/{projectId}/yourApplications/{applicationId}/cancel` - Cancel application (COLLABORATOR only)
* `GET /api/users/{userId}/projects/yourApplications` - View own applications (COLLABORATOR only)

### 📥 Project Applications (Admin/Poster)

* `GET /api/projects/applications` - View all project applications (ADMIN only)
* `GET /api/projects/applications/{projectId}` - View applications for a project (POSTER only)

### 🛡️ Admin Controls

* `POST /api/admin/ban/{id}` - Ban user/poster (ADMIN only)
* `POST /api/admin/unban/{id}` - Unban user/poster (ADMIN only)
* `GET /api/admin/poster-all` - Get all posters (ADMIN only)
* `GET /api/admin/user-all` - Get all collaborators (ADMIN only)
* `DELETE /api/admin/user-delete/{id}` - Delete collaborator (ADMIN only)

---
## 📘 API Documentation (Swagger)

* Swagger UI: [https://partnerfinder.onrender.com/swagger-ui/index.html](https://partnerfinder.onrender.com/swagger-ui/index.html)
```
Explore and test all endpoints with role-specific access in a visual interface.
```

## 🔍 API Flow Diagram
```bash
   USER / POSTER / ADMIN
            ↓
  Register/Login (/api/auth/*)
            ↓
    Receives JWT Token
            ↓
 Access endpoints based on roles:
 - POSTER → `/dashboard/poster, /api/posters/**`
 - COLLABORATOR → `/dashboard/user, /api/users/**`
 - ADMIN → `/dashboard/admin, /api/admin/**`
```
## 🛠 Technologies

* Spring Boot 3
* Spring Security 6
* MySQL
* Hibernate/JPA
* JWT (JJWT 0.12+)
* Maven

---

## ⚙️ Project Setup

### Prerequisites

* Java 17+
* Maven 3.6+
* MySQL Server

### `application.properties`

```
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
jwt.secret=${JWT_SECRET}
jwt.expiration=86400000
```

Use environment variables for sensitive data.

### Run Locally

```bash
mvn clean install
mvn spring-boot:run
```

---

## 📄 License

This project is licensed under the **Apache License 2.0**.

---

## 🤝 Contribution

Pull requests are welcome. For major changes, open an issue first.

---

## 👨‍💻 Author

**Aniket Singh**
Build for you!.

---

## 📚 Docs

* Spring Security Docs: [https://docs.spring.io/spring-security/](https://docs.spring.io/spring-security/)
* JJWT Guide: [https://github.com/jwtk/jjwt](https://github.com/jwtk/jjwt)
* Hibernate ORM: [https://hibernate.org/orm/](https://hibernate.org/orm/)
* Spring Boot Docs: [https://docs.spring.io/spring-boot/](https://docs.spring.io/spring-boot/)
