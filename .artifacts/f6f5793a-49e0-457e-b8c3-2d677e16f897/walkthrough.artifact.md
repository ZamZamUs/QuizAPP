# Walkthrough - Modern Soft High Contrast UI Redesign

Aplikasi **Pawal** telah sepenuhnya direnovasi dengan tampilan yang lebih modern, organik, dan profesional. Fokus utama adalah pada kenyamanan visual (*eye comfort*) tanpa mengorbankan kontras yang tinggi untuk keterbacaan.

## Perubahan Utama

### 1. Palet Warna Baru (Modern & Balanced)
- **Deep Indigo & Soft White**: Menggantikan skema Monokrom yang kaku. Memberikan kesan premium dan lebih ramah di mata.
- **Amber Accents**: Digunakan untuk elemen sekunder dan aksi penting (seperti FAB) agar menonjol secara elegan.
- **Surface Depth**: Menggunakan warna abu-abu lembut untuk latar belakang komponen agar teks lebih mudah dibaca.

### 2. Geometri Organik (Rounded Corners)
- **Sharp to Soft**: Seluruh komponen (Cards, Buttons, TextFields) kini memiliki sudut membulat (**16dp - 24dp**). Hal ini membuat UI terasa lebih modern dan ramah pengguna.
- **StyledCard**: Kartu kategori dan soal kini memiliki elevasi halus dan border yang sangat tipis untuk kesan bersih.

### 3. Komponen Design System Baru
- **ModernButton**: Tombol berukuran besar dengan sudut membulat sempurna (24dp) dan tipografi tebal.
- **ChoiceChips**: Menggantikan tombol radio standar untuk pemilihan kunci jawaban, memberikan area tekan yang lebih lega.
- **ModernProgress**: Indikator progres kuis kini lebih tebal dengan ujung bulat (*rounded cap*) untuk tampilan yang lebih modern.

### 4. Penyempurnaan Tata Letak (Screens)
- **Home**: Daftar kategori kini lebih lega dengan ikon navigasi baru.
- **Quiz**: Antarmuka kuis yang didesain ulang sepenuhnya dengan kartu soal yang bold dan pilihan jawaban yang memberikan feedback visual jelas saat dipilih.
- **Result**: Tampilan skor akhir yang dramatis dengan font berukuran besar (Black weight) dan ringkasan hasil yang rapi.

## Cara Memverifikasi
1. **Buka Aplikasi**: Anda akan langsung melihat header "PAWAL" dengan tipografi baru.
2. **Tambah Kategori**: Dialog input kini menggunakan `ModernTextField` yang elegan.
3. **Mulai Kuis**: Perhatikan animasi progres dan transisi antar soal yang lebih halus secara visual.

> [!TIP]
> Desain ini dirancang agar tetap konsisten baik di Mode Terang maupun Mode Gelap (Dark Mode), menjaga kontras tetap optimal di berbagai kondisi pencahayaan.
