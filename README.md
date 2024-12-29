# 🚀 **Online Judge Platform**

An advanced **Online Judge Platform** designed to mirror the functionalities of popular platforms like **Codeforces** and **CodeChef**. Users can submit programming solutions, receive real-time feedback, and participate in competitive contests.

---

## 📝 **Overview**

The platform evaluates user submissions against test cases and provides accurate feedback such as:

- ✅ **Accepted (AC)**
- ❌ **Wrong Answer (WA)**
- ⏳ **Time Limit Exceeded (TLE)**
- 💥 **Runtime Error (RE)**
- 🛑 **Compilation Error (CE)**

It also supports **user management**, an **admin panel** for problem curation, and efficient **matchmaking for contests**.

---

## ⚙️ **Tech Stack**

### **Backend (Java - Spring Boot)**
- RESTful APIs for user, problem, and submission management.
- Role-based access control (**Admin/User**).
- Persistent storage with **MySQL**.

### **Microservices (Go)**
- Matchmaking service for contests.
- Code execution microservice using secure sandboxes.

### **Frontend (Vue.js)**
- User-friendly interface for problem-solving and contest participation.
- Admin dashboard for problem management.

### **Database**
- **MongoDB** for user sessions and caching.
- **MySQL** for relational data storage.

### **Deployment**
- Dockerized services using **Docker Compose**.
- **Kubernetes** configuration for scalability.

---

## 📁 **Project Structure**

```plaintext
/online-judge-platform
├── backend-java/          # Java Spring Boot backend
│   ├── controller/        # REST controllers
│   ├── service/           # Business logic
│   ├── model/             # Entity models
│   ├── repository/        # Database access
│   ├── config/            # Configurations
│   ├── pom.xml            # Maven dependencies
│
├── backend-go/            # Go microservices
│   ├── handler/           # Handlers for services
│   ├── config/            # Configuration files
│   ├── Dockerfile
│
├── frontend/              # Vue.js Frontend
│   ├── components/        # UI components
│   ├── views/             # Page views
│   ├── router/            # Routing
│   ├── store/             # State management
│   ├── package.json
│
├── database/              # Database setup
│   ├── mongodb-init/
│   ├── mysql-init/
│
├── deployment/            # Deployment configs
│   ├── Dockerfile
│   ├── docker-compose.yml
│   ├── k8s-deployment.yaml
│
├── docs/                  # Documentation and API specs
│
└── README.md
```

---

## 🛠️ **Setup Instructions**

### **1. Clone the Repository**
```bash
git clone https://github.com/machack1512/Online-Coding-Judge-Platform
cd online-judge-platform
```

### **2. Backend - Java (Spring Boot)**
```bash
cd backend-java
mvn clean install
mvn spring-boot:run
```

### **3. Microservices - Go**
```bash
cd backend-go
go mod tidy
go run main.go
```

### **4. Frontend - Vue.js**
```bash
cd frontend
npm install
npm run serve
```

### **5. Deployment with Docker Compose**
```bash
docker-compose up --build
```

### **6. Deployment with Kubernetes**
```bash
kubectl apply -f deployment/k8s-deployment.yaml
```

---

## 🌐 **Usage**
- Access the frontend: **[http://localhost:8080](http://localhost:8080)**
- **Admin:** Manage problems and contests.
- **Users:** Register, submit solutions, and monitor progress.

---

## 📜 **License**
This project is licensed under the **MIT License**. See [LICENSE](./LICENSE) for details.

---

**Happy Coding! 💻🎯**
