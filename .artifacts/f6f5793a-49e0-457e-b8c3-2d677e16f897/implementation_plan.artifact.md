# Implementation Plan - Redesign UI/UX Monochrome & High Contrast

Redesign tampilan aplikasi **Bank Soal & Quiz Master** menjadi tema Monokrom (Hitam-Putih) minimalis modern dengan aksen *high contrast* untuk elemen penting. Meskipun aplikasi saat ini menggunakan **Jetpack Compose**, saya juga akan menyertakan padanan XML sesuai permintaan Anda.

## User Review Required

> [!IMPORTANT]
> Aplikasi Anda saat ini dibangun menggunakan **Jetpack Compose**, bukan XML Layout tradisional. Saya akan mengimplementasikan desain baru ini secara langsung di kode Compose Anda agar aplikasi tetap berjalan, namun saya juga akan memberikan file XML (`colors.xml`, `themes.xml`) sebagai referensi desain atau untuk komponen sistem (seperti Splash Screen).

## Proposed Changes

### 1. Color Palette & Theming (Compose)

#### [MODIFY] [Color.kt](file:///C:/Users/Lenovo/AndroidStudioProjects/Pawal/app/src/main/java/com/example/pawal/ui/theme/Color.kt)
- Mendefinisikan warna monokrom: `PureBlack`, `PureWhite`, `DarkGrey`, `LightGrey`.
- Menambahkan warna aksen: `AccentNeon` (misalnya Kuning Neon atau Cyan) untuk elemen interaktif.

#### [MODIFY] [Theme.kt](file:///C:/Users/Lenovo/AndroidStudioProjects/Pawal/app/src/main/java/com/example/pawal/ui/theme/Theme.kt)
- Mengatur `ColorScheme` agar menggunakan palet monokrom.
- Menonaktifkan `dynamicColor` agar konsistensi desain terjaga.

#### [MODIFY] [Type.kt](file:///C:/Users/Lenovo/AndroidStudioProjects/Pawal/app/src/main/java/com/example/pawal/ui/theme/Type.kt)
- Menggunakan tipografi yang lebih tajam dan modern (Clean Sans-Serif).

---

### 2. UI Components & Styling (Compose)

#### [NEW] [DesignSystem.kt](file:///C:/Users/Lenovo/AndroidStudioProjects/Pawal/app/src/main/java/com/example/pawal/ui/components/DesignSystem.kt)
- Membuat komponen kustom: `MonochromeCard` dengan border tajam, `HighContrastButton`, dan `StyledTextField`.
- Menambahkan efek visual seperti *subtle glowing border* atau *geometric background accents*.

#### [MODIFY] [HomeScreen.kt](file:///C:/Users/Lenovo/AndroidStudioProjects/Pawal/app/src/main/java/com/example/pawal/ui/screens/HomeScreen.kt)
- Menerapkan layout yang lebih bersih dengan whitespace yang luas.
- Menggunakan ikon geometris dan aksen kontras tinggi pada FAB dan Card kategori.

#### [MODIFY] [QuizScreen.kt](file:///C:/Users/Lenovo/AndroidStudioProjects/Pawal/app/src/main/java/com/example/pawal/ui/screens/QuizScreen.kt)
- Redesign kartu soal dan pilihan jawaban menjadi lebih bold dan minimalis.

---

### 3. XML Resource Support (Legacy/Reference)

#### [NEW] [colors.xml](file:///C:/Users/Lenovo/AndroidStudioProjects/Pawal/app/src/main/res/values/colors.xml)
- Definisi palet warna monokrom dalam format XML.

#### [NEW] [themes.xml](file:///C:/Users/Lenovo/AndroidStudioProjects/Pawal/app/src/main/res/values/themes.xml)
- Definisi tema monokrom untuk sistem Android (Material 3).

#### [NEW] [custom_button_bg.xml](file:///C:/Users/Lenovo/AndroidStudioProjects/Pawal/app/src/main/res/drawable/custom_button_bg.xml)
- Contoh drawable XML untuk tombol dengan border tajam dan rounded corners.

---

## Verification Plan

### Manual Verification
1.  **Visual Check**: Memastikan palet warna Hitam, Putih, dan Abu-abu mendominasi UI.
2.  **Contrast Check**: Memastikan elemen aksen (tombol simpan, mulai kuis) terlihat sangat mencolok.
3.  **Consistency Check**: Memeriksa seluruh halaman (Home, Detail, Quiz) memiliki gaya border dan spacing yang seragam.
4.  **Readability**: Memastikan teks Putih di atas Hitam (dan sebaliknya) mudah dibaca.
