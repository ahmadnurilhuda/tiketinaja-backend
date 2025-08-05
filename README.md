# Tiketinaja Backend

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Postgres](https://img.shields.io/badge/postgresql-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)

Ini adalah repositori backend untuk **Tiketinaja**, sebuah REST API yang dibangun dengan Spring Boot untuk melayani aplikasi frontend Next.js.

## 📋 Daftar Isi

- [Tentang Proyek](#tentang-proyek)
- [Fitur Utama](#fitur-utama)
- [Arsitektur & Teknologi](#arsitektur--teknologi)
- [Struktur API Endpoints](#struktur-api-endpoints)
- [Memulai](#memulai)
  - [Prasyarat](#prasyarat)
  - [Instalasi & Menjalankan](#instalasi--menjalankan)
  - [Konfigurasi Lingkungan](#konfigurasi-lingkungan)
- [Keamanan](#keamanan)

---

## 🚀 Tentang Proyek

Backend **Tiketinaja** berfungsi sebagai server API yang menangani semua logika bisnis, interaksi database, dan otentikasi. Dibangun di atas fondasi Spring Boot, API ini menyediakan serangkaian endpoint RESTful yang terstruktur untuk mendukung semua fungsionalitas yang dibutuhkan oleh frontend, mulai dari pendaftaran pengguna hingga pemrosesan transaksi.

---

## ✨ Fitur Utama

-   **Manajemen Pengguna & Otentikasi**:
    -   Registrasi pengguna dengan verifikasi email.
    -   Login berbasis JWT (JSON Web Token) dengan refresh token.
    -   Pengelolaan profil pengguna.
-   **Sistem Berbasis Peran (Role-Based)**:
    -   Otorisasi yang jelas untuk peran **Admin**, **Organizer**, dan **Buyer**.
    -   Endpoint yang dilindungi berdasarkan peran pengguna.
-   **Manajemen Event & Tiket**:
    -   Operasi CRUD (Create, Read, Update, Delete) penuh untuk event dan jenis tiket.
    -   Endpoint publik untuk menampilkan daftar event kepada semua pengguna.
-   **Manajemen Pesanan**:
    -   Logika untuk membuat pesanan dan menampilkan riwayat transaksi.
-   **Penanganan File Upload**:
    -   Menyimpan dan menyajikan gambar untuk poster event dan layout venue.
-   **Validasi Data**:
    -   Validasi input yang kuat di level DTO (Data Transfer Object) untuk memastikan integritas data.
-   **Paginasi & Penyortiran**:
    -   Dukungan paginasi dan penyortiran untuk endpoint yang mengembalikan daftar data.

---

## 🛠️ Arsitektur & Teknologi

-   **Framework**: [Spring Boot](https://spring.io/projects/spring-boot)
-   **Bahasa**: [Java](https://www.java.com/) (versi 17 atau lebih tinggi)
-   **Akses Data**: [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
-   **Database**: [MySQL](https://www.mysql.com/)
-   **Keamanan**: [Spring Security](https://spring.io/projects/spring-security), [JSON Web Tokens (JWT)](https://jwt.io/)
-   **Validasi**: Jakarta Bean Validation
-   **Build Tool**: [Maven](https://maven.apache.org/)

---

## 🌐 Struktur API Endpoints

API ini memiliki beberapa grup endpoint utama yang dilindungi berdasarkan peran:

-   `/auth/**`: Endpoint untuk otentikasi (login, register, verifikasi).
-   `/public/**`: Endpoint yang dapat diakses publik (melihat event, kategori, kota).
-   `/buyer/**`: Endpoint khusus untuk pengguna yang sudah login (membuat pesanan, melihat tiket).
-   `/organizer/**`: Endpoint untuk organizer (mengelola event, tiket, melihat dashboard).
-   `/admin/**`: Endpoint untuk admin (mengelola data master, pengguna).
-   `/uploads/**`: Path untuk menyajikan file statis seperti gambar.

---

## 🏁 Memulai

### Prasyarat

-   JDK (Java Development Kit) versi 17 atau lebih tinggi.
-   Maven
-   Database MySQL yang sedang berjalan.

### Instalasi & Menjalankan

1.  **Clone repositori ini:**
    ```bash
    git clone [https://github.com/ahmadnurilhuda/tiketinaja-backend.git](https://github.com/ahmadnurilhuda/tiketinaja-backend.git)
    cd tiketinaja-backend
    ```

2.  **Konfigurasi Database:**
    Buka `src/main/resources/application.properties` (atau `.yml`) dan sesuaikan konfigurasi database Anda.

3.  **Build dan jalankan aplikasi:**
    -   **Dengan Maven:**
        ```bash
        ./mvnw spring-boot:run
        ```

4.  Aplikasi akan berjalan di port `9988` (atau port yang Anda konfigurasikan).

### Konfigurasi Lingkungan

Pengaturan penting berada di `src/main/resources/application.properties`. Pastikan Anda mengatur:

-   **Koneksi Database**:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/tiketinaja_db
    spring.datasource.username=postgres
    spring.datasource.password=password_anda
    ```
-   **Konfigurasi JWT**:
    ```properties
    jwt.secret=kunci-rahasia-jwt-anda-yang-sangat-panjang
    jwt.expiration.ms=86400000 # 24 jam
    ```
-   **Path Penyimpanan File**:
    ```properties
    file.upload-dir=./uploads
    ```

---

## 🔒 Keamanan

-   **Otentikasi**: Setiap permintaan ke endpoint yang dilindungi harus menyertakan `Authorization` header dengan `Bearer Token` (JWT).
-   **Otorisasi**: Akses ke endpoint `/admin/**` dan `/organizer/**` dibatasi oleh peran pengguna melalui `Interceptor` di Spring. Pengguna biasa (Buyer) tidak akan bisa mengakses endpoint ini.
