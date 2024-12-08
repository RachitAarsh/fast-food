
# **Fast Food App**

A dynamic and feature-rich mobile application designed to simplify food ordering and management for users and administrators.

## **Table of Contents**
- [Features](#features)
- [Tech Stack](#tech-stack)
- [API Documentation](#api-documentation)
- [Screenshots](#screenshots)
- [License](#license)

---

## **Features**

### **User Features**
- **Authentication**: Login and signup with Google, Facebook, or email.
- **Dynamic Home Page**: View categories, best-sellers, and a customizable carousel.
- **Item Details Page**: Dynamically displays food item details such as price, description, and discounts.

### **Admin Features**
- **CRUD Operations**: Add, update, delete, and manage food items.
- **Secure Login**: Admin access to item management.

### **API Features**
- Authentication for users and admins.
- Routes for listing, adding, editing, and deleting food items.

---

## **Tech Stack**

### **Frontend**
- Developed using Android Studio, Navigation Components, and Material Libraries.
- Responsive design for mobile devices.
- Integrates authentication and dynamic content via APIs.

### **Backend**
- Firebase (Authentication).
- Firestore Cloud.
- Realtime Database Firebase, Cloud Storage Firebase.


---

## **API Documentation**

### **Authentication**
| Method | Endpoint        | Description               |
|--------|------------------|---------------------------|
| POST   | `/signup`        | Registers a new user      |
| POST   | `/login`         | Logs in a user            |
| POST   | `/admin/login`   | Logs in an admin          |

### **Food Items**
| Method | Endpoint        | Description               |
|--------|------------------|---------------------------|
| GET    | `/items`         | Fetches all food items    |
| POST   | `/items`         | Adds a new food item      |
| PUT    | `/items/:id`     | Updates a food item       |
| DELETE | `/items/:id`     | Deletes a food item       |

---

## **Screenshots**

### **Splash Screen**
![Splash Screen](https://drive.google.com/file/d/1_3u2iphyYrFxr1LAp_E7KzqmHwh7F5UF/view?usp=sharing)

### **Role Selection Page**
*Add image here*

### **Admin Page**
*Add image here*

### **user page**
*Add image here*

[![Watch the Demo Video]("https://drive.google.com/file/d/1o6zcO75l-sxzvn-21nhZy8XQZMKJou3g/view?usp=sharing")


---
