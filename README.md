# Smart Expiry Risk-Based Inventory Management System

## 📌 Overview

Smart Expiry Risk-Based Inventory Management System is a backend-driven inventory platform designed to manage perishable stock using:

- FEFO (First Expire First Out) logic
- Risk scoring engine
- Expiry alerts
- Low stock alerts
- Financial loss tracking
- Analytics dashboard

The system ensures optimized stock movement and minimizes expiry-related losses.

---

## 🚀 Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA
- MySQL
- Docker & Docker Compose
- Spring Security (HTTP Basic)
- Maven

---

## 🏗️ Architecture

Browser  
↓  
Spring Boot Container  
↓  
MySQL Container

The application runs fully containerized using Docker Compose.

---

## 🔥 Core Features

### 1️⃣ FEFO Engine
Automatically sells stock based on earliest expiry date.

### 2️⃣ Risk Scoring
Batches nearing expiry are assigned risk scores.

### 3️⃣ Alert Engine
- Low Stock Alerts
- Expiring Soon Alerts
- High Risk Alerts

### 4️⃣ Wastage Tracking
Records damaged/expired stock and calculates financial loss.

### 5️⃣ Analytics
- Monthly wastage reports
- Product-wise loss analysis
- Dashboard summary metrics

### 6️⃣ Secure APIs
Protected using Spring Security (HTTP Basic Authentication).

---

## 🐳 Running with Docker

### Step 1: Build the Project

```bash
./mvnw clean package