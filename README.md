# Tiketinaja Backend

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)

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
    -   Login berbasis JWT (JSON Web Token).
-   **Sistem Berbasis Peran (Role-Based)**:
    -   Otorisasi yang jelas untuk peran **Admin**, **Organizer**, dan **Buyer**.
-   **Manajemen Event & Tiket**:
    -   Operasi CRUD (Create, Read, Update, Delete) penuh untuk event dan jenis tiket.
-   **Manajemen Pesanan & Transaksi**:
    -   Integrasi dengan payment gateway Midtrans.
-   **Penanganan File Upload**:
    -   Menyimpan dan menyajikan gambar untuk poster event dan layout venue.
-   **Validasi Data & Paginasi**:
    -   Validasi input yang kuat dan dukungan paginasi untuk daftar data.

---

## 🛠️ Arsitektur & Teknologi

-   **Framework**: [Spring Boot](https://spring.io/projects/spring-boot)
-   **Bahasa**: [Java](https://www.java.com/) (versi 17 atau lebih tinggi)
-   **Akses Data**: [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
-   **Database**: [MySQL](https://www.mysql.com/)
-   **Keamanan**: [Spring Security](https://spring.io/projects/spring-security), [JSON Web Tokens (JWT)](https://jwt.io/)
-   **Build Tool**: [Maven](https://maven.apache.org/) atau [Gradle](https://gradle.org/)

---

## 🌐 Struktur API Endpoints

-   `/auth/**`: Endpoint untuk otentikasi.
-   `/public/**`: Endpoint yang dapat diakses publik.
-   `/buyer/**`: Endpoint khusus untuk pengguna yang sudah login.
-   `/organizer/**`: Endpoint untuk organizer.
-   `/admin/**`: Endpoint untuk admin.
-   `/uploads/**`: Path untuk menyajikan file statis seperti gambar.

---

## 🏁 Memulai

### Prasyarat

-   JDK (Java Development Kit) versi 17 atau lebih tinggi.
-   Maven atau Gradle.
-   Database MySQL yang sedang berjalan.
-   Akun Mailtrap (untuk testing email di environment development).
-   Akun Midtrans (untuk payment gateway).

### Instalasi & Menjalankan

1.  **Clone repositori ini:**
    ```bash
    git clone [https://github.com/ahmadnurilhuda/tiketinaja-backend.git](https://github.com/ahmadnurilhuda/tiketinaja-backend.git)
    cd tiketinaja-backend
    ```

2.  **Konfigurasi Lingkungan:**
    Buat file `.env` di direktori utama (root) proyek. Lihat bagian [Konfigurasi Lingkungan](#konfigurasi-lingkungan) di bawah untuk detailnya.

3.  **Build dan jalankan aplikasi:**
    -   **Dengan Maven:**
        ```bash
        ./mvnw spring-boot:run
        ```
    -   **Dengan Gradle:**
        ```bash
        ./gradlew bootRun
        ```

4.  Aplikasi akan berjalan di port `9988`.

### Konfigurasi Lingkungan

Proyek ini menggunakan sistem profil Spring (`dev` untuk development). Konfigurasi utama ada di `application.properties`, dan konfigurasi spesifik untuk development ada di `application-dev.properties`.

Data sensitif dimuat dari file `.env` di root proyek.

1.  **Buat file `.env`** di direktori utama proyek.

2.  **Isi file `.env`** dengan variabel berikut (ganti dengan nilai Anda sendiri):
    ```env
    # Kredensial Mailtrap
    MAIL_USERNAME=username_mailtrap_anda
    MAIL_PASSWORD=password_mailtrap_anda

    # Kunci rahasia untuk JWT (buat yang sangat acak dan panjang)
    JWT_SECRET=kunci-rahasia-jwt-anda-yang-sangat-panjang

    # Kunci Midtrans
    MIDTRANS_CLIENT_KEY=kunci_client_midtrans_anda
    MIDTRANS_SERVER_KEY=kunci_server_midtrans_anda
    ```

3.  **Pastikan `application-dev.properties` sudah benar:**
    Pastikan file `src/main/resources/application-dev.properties` Anda berisi konfigurasi database dan memuat variabel dari `.env`.
    ```properties
    # Konfigurasi Database Development
    spring.datasource.url=jdbc:mysql://localhost:3308/tiketinaja?useSSL=false&createDatabaseIfNotExist=true
    spring.datasource.username=root
    spring.datasource.password=

    # Konfigurasi Mailtrap untuk Development
    spring.mail.host=smtp.mailtrap.io
    spring.mail.port=2525
    spring.mail.username=${MAIL_USERNAME}
    spring.mail.password=${MAIL_PASSWORD}

    # Konfigurasi JWT Secret untuk Development
    jwt.secret=${JWT_SECRET}

    # Konfigurasi Midtrans
    midtrans.client-key=${MIDTRANS_CLIENT_KEY}
    midtrans.server-key=${MIDTRANS_SERVER_KEY}
    ```

---

## 🔒 Keamanan

-   **Otentikasi**: Setiap permintaan ke endpoint yang dilindungi harus menyertakan `Authorization` header dengan `Bearer Token` (JWT).
-   **Otorisasi**: Akses ke endpoint `/admin/**` dan `/organizer/**` dibatasi oleh peran pengguna melalui `Interceptor` di Spring.
