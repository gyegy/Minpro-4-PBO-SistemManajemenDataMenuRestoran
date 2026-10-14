package controller;

import dao.RestoranDAO;
import java.util.List;
import model.Kategori;
import model.Menu;
import model.MenuMakanan;
import model.MenuMinuman;
import view.RestoranView;

    public class RestoranController {

        private final RestoranView view;
        private final RestoranDAO restoranDAO;

        public RestoranController(RestoranView view) {

        this.view = view;
        this.restoranDAO = new RestoranDAO();
    }

    public void jalankanProgram() {

        boolean berjalan = true;

        while (berjalan) {

            view.tampilkanMenuUtama();

            int pilihan = view.inputPilihan(
                    "Pilih menu (1-8): ",
                    1,
                    8
            );

            switch (pilihan) {

                case 1 -> tambahKategori();

                case 2 -> tampilkanKategori();

                case 3 -> tambahMenu();

                case 4 -> tampilkanMenu();

                case 5 -> updateMenu();

                case 6 -> hapusMenu();

                case 7 -> {

                    berjalan = false;

                    view.tampilkanPesan("Program selesai. Terima kasih");
                }

                case 8 -> hitungHargaSetelahDiskon();
            }
        }

        view.tutupScanner();
    }
  
    private void tambahKategori() {

        view.tampilkanJudul("TAMBAH KATEGORI");

        String idKategori = view.inputString("ID Kategori: ");
        String namaKategori = view.inputString("Nama Kategori: ");
        String deskripsi = view.inputString("Deskripsi: ");

        Kategori kategoriBaru = new Kategori(
                idKategori,
                namaKategori,
                deskripsi
        );

        if (restoranDAO.tambahKategori(kategoriBaru)) {

            view.tampilkanPesan("Kategori berhasil ditambahkan ke database");

        } else {

            view.tampilkanPesan("Kategori gagal ditambahkan ke database");
        }
    }   



        private void tampilkanKategori() {

        view.tampilkanDaftarKategori(
                restoranDAO.getAllKategori()
        );
    }

    
    private void tambahMenu() {

        view.tampilkanJudul("TAMBAH MENU");

        String idMenu = view.inputString("ID Menu: ");
        String namaMenu = view.inputString("Nama Menu: ");

        Kategori kategoriDipilih = pilihKategori();

        if (kategoriDipilih == null) {
            return;
        }

        double harga = view.inputHarga("Harga Menu: ");

        view.tampilkanPilihanJenisMenu();

        int jenisMenu = view.inputPilihan(
                "Pilih jenis menu (1-2): ",
                1,
                2
        );

        Menu menuBaru;

        if (jenisMenu == 1) {

            String jenisMakanan = view.inputString(
                    "Jenis Makanan: "
            );

            menuBaru = new MenuMakanan(
                    idMenu,
                    namaMenu,
                    kategoriDipilih,
                    harga,
                    jenisMakanan
            );

        } else {

            String jenisMinuman = view.inputString(
                    "Jenis Minuman: "
            );

            menuBaru = new MenuMinuman(
                    idMenu,
                    namaMenu,
                    kategoriDipilih,
                    harga,
                    jenisMinuman
            );
        }

        if (restoranDAO.tambahMenu(menuBaru)) {

            view.tampilkanPesan("Menu berhasil ditambahkan ke database");

        } else {

            view.tampilkanPesan("Menu gagal ditambahkan ke database");
        }
    }


    private void tampilkanMenu() {

        view.tampilkanDaftarMenu(
                restoranDAO.getAllMenu()
        );
    }

    
    private void updateMenu() {

        view.tampilkanJudul("UPDATE MENU");

        String idTarget = view.inputString("Masukkan ID Menu: ");

        Menu menu = restoranDAO.getMenuById(idTarget);

        if (menu == null) {

            view.tampilkanPesan("Menu tidak ditemukan di database");

            return;
        }

        view.tampilkanMenuSaatIni(menu);

        String namaBaru = view.inputString("Nama Menu Baru: ");

        Kategori kategoriBaru = pilihKategori();

        if (kategoriBaru == null) {
            return;
        }

        double hargaBaru = view.inputHarga("Harga Baru: ");

        String detailJenis;

        if (menu instanceof MenuMakanan) {

            detailJenis = view.inputString("Jenis Makanan Baru: ");

        } else {

            detailJenis = view.inputString("Jenis Minuman Baru: ");
        }

        if (restoranDAO.updateMenu(
                idTarget,
                namaBaru,
                kategoriBaru,
                hargaBaru,
                detailJenis
        )) {

            view.tampilkanPesan("Menu berhasil diperbarui di database" );

        } else {

            view.tampilkanPesan("Menu gagal diperbarui di database");
        }
    }



    private void hapusMenu() {

        view.tampilkanJudul("HAPUS MENU");

        String idTarget = view.inputString("Masukkan ID Menu: ");

        if (restoranDAO.hapusMenu(idTarget)) {

            view.tampilkanPesan("Menu berhasil dihapus dari database");

        } else {

            view.tampilkanPesan("Menu gagal dihapus. "+ "Pastikan ID menu tersedia.");
        }
    }


    
    private void hitungHargaSetelahDiskon() {

        view.tampilkanJudul("HITUNG HARGA SETELAH DISKON");

        String idMenu = view.inputString("Masukkan ID Menu: ");

        Menu menu = restoranDAO.getMenuById(idMenu);

        if (menu == null) {

            view.tampilkanPesan("Menu tidak ditemukan di database");

            return;
        }

        double hargaNormal = menu.hitungHargaSetelahDiskon();

        double persentaseDiskon = view.inputPersentase("Persentase Diskon (%): ");

        double hargaAkhir = menu.hitungHargaSetelahDiskon(persentaseDiskon);

        view.tampilkanHasilDiskon(
                menu,
                hargaNormal,
                persentaseDiskon,
                hargaAkhir
        );
    }
    
    private Kategori pilihKategori() {

        List<Kategori> daftarKategori = restoranDAO.getAllKategori();

        if (daftarKategori.isEmpty()) {

            view.tampilkanPesan("Belum ada kategori di database. "+ "Tambahkan kategori terlebih dahulu.");

            return null;
        }

        view.tampilkanPilihanKategori(daftarKategori);

        String idKategori = view.inputString("ID Kategori: ");

        for (Kategori kategori : daftarKategori) {

            if (kategori.getIdKategori().equalsIgnoreCase(idKategori)) {
                return kategori;
            }
        }

        view.tampilkanPesan("Kategori tidak ditemukan");

        return null;
    }
}