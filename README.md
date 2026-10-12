# katalon-petstore-web

Automation testing web untuk **JPetStore** (demo) menggunakan **Katalon Studio Enterprise 10.x** (tipe proyek `WEBUI`).

- **AUT (Application Under Test):** https://petstore.octoperf.com/actions/Catalog.action
- **Metode penulisan test:** Hybrid (Manual steps + Script). **Tidak** menggunakan Record Web.
- **Kredensial:** tidak disimpan di repo — salin `Profiles/default.glbl.example` menjadi `default.glbl` lalu isi nilainya secara lokal.

## Struktur Proyek

```
katalon-petstore-web/
├── Profiles/default.glbl.example  # Template Global Variables (*.glbl asli di-ignore)
├── Object Repository/Petstore/    # Test object (XPath), dibagi per halaman
│   ├── Page_Catalog/              #   katalog/home
│   ├── Page_SignIn/               #   form sign-in
│   ├── Page_Category/             #   daftar produk per kategori / hasil search
│   ├── Page_Product/              #   detail produk (add to cart)
│   ├── Page_Cart/                 #   shopping cart
│   └── Page_Order/                #   halaman order/checkout
├── Data Files/                    # Data-driven testing (template *.example.csv)
│   ├── LoginData.example.csv      #   template: username, password, expected
│   └── SearchData.example.csv     #   template: keyword, expectedProductId
├── Test Cases/Petstore/           # Test case (.tc) + script Groovy
├── Scripts/Petstore/              # Script Groovy per test case
└── Test Suites/TS_Petstore_Regression.ts  # Test Suite (semua test case)
```

## Global Variables (`Profiles/default.glbl`)

Kredensial **tidak di-hardcode** di test case — semuanya diambil dari Global Variables:

| Variable | Nilai |
|---|---|
| `baseUrl` | https://petstore.octoperf.com/actions/Catalog.action |
| `signInUrl` | https://petstore.octoperf.com/actions/Account.action?signonForm= |
| `orderUrl` | https://petstore.octoperf.com/actions/Order.action?newOrderForm= |
| `validUsername` / `validPassword` | kredensial valid (diisi lokal, tidak di-commit) |
| `invalidUsername` / `invalidPassword` | kredensial invalid (diisi lokal, tidak di-commit) |
| `categoryId` | FISH |
| `productId` | FI-SW-01 |
| `itemId` | EST-1 |

## Test Suite

**`Test Suites/TS_Petstore_Regression`** — menjalankan seluruh test case secara berurutan. Menjalankan suite ini otomatis menghasilkan **execution report** (HTML/CSV/JUnit) pada folder `Reports/`.

## Test Coverage

| ID | Test Case | Tipe | DDT | Deskripsi |
|----|-----------|------|:---:|-----------|
| TC01 | `TC01_Login_Valid_Credentials` | Positive | — | Login dengan kredensial valid, verifikasi link Sign Out muncul |
| TC02 | `TC02_Login_Invalid_Credentials` | Negative | — | Login dengan kredensial salah, verifikasi pesan error |
| TC03 | `TC03_Search_Product_DDT` | Positive | ✔ | Search produk (fish/shark/koi), verifikasi product ID sesuai data |
| TC04 | `TC04_Add_Product_To_Cart` | Positive | — | Buka kategori FISH → produk FI-SW-01 → add item EST-1 → verifikasi di cart |
| TC05 | `TC05_Checkout_Without_Login` | Negative | — | Akses checkout tanpa login, verifikasi pesan "You must sign on..." |
| TC06 | `TC06_Login_DataDriven` | Positive + Negative | ✔ | Login data-driven (3 baris: 1 valid, 2 invalid), verifikasi hasil per baris |

**Rangkuman:** 6 test case (≥5), mencakup **positive** & **negative**, serta **Data-Driven Testing** (TC03 & TC06 via Data Files).

## Cara Menjalankan

### A. Katalon Studio (IDE)
1. Buka Katalon Studio, pilih **Open Project** → arahkan ke folder proyek ini.
2. Salin `Profiles/default.glbl.example` → `Profiles/default.glbl` dan isi kredensial, lalu pastikan profil aktif = **default**.
3. Di Test Explorer, buka **Test Suites → TS_Petstore_Regression**.
4. Klik **Run**, pilih browser (mis. Chrome).
5. Lihat hasil di tab **Log** / **Report**, dan folder `Reports/`.


## Report

Report hasil eksekusi dibuat otomatis di `Reports/<timestamp>/...` oleh Katalon (format HTML, CSV, dan JUnit). Untuk membuat report, cukup jalankan **TS_Petstore_Regression**.

## Catatan

- Kredensial diisi pada `Profiles/default.glbl` (lokal) dan **tidak di-commit** ke repository; data DDT dibuat dari `Data Files/*.example.csv`.
- Jika mengubah file `default.glbl` di luar IDE, buka **Profiles → default** lalu klik **Save** (atau buka ulang proyek) agar Katalon meregenerasi class `internal.GlobalVariable`.
- `Libs/`, `bin/`, dan `Reports/` adalah artefak build (lihat `.gitignore`).
