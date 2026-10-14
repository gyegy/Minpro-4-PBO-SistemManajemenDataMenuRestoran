package dao;

import config.Koneksi;
import model.Kategori;
import model.Menu;
import model.MenuMakanan;
import model.MenuMinuman;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RestoranDAO {

    public List<Kategori> getAllKategori() {

        List<Kategori> daftarKategori = new ArrayList<>();

        String sql = "SELECT id_kategori, nama_kategori, deskripsi "
                + "FROM kategori";

        Connection conn = Koneksi.getConnection();

        if (conn == null) {
            System.out.println("Koneksi database tidak tersedia.");
            return daftarKategori;
        }

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Kategori kategori = new Kategori(
                        rs.getString("id_kategori"),
                        rs.getString("nama_kategori"),
                        rs.getString("deskripsi")
                );

                daftarKategori.add(kategori);
            }

        } catch (SQLException e) {
            System.out.println("Gagal mengambil kategori: " + e.getMessage());
        }

        return daftarKategori;
    }

    public boolean tambahKategori(Kategori kategori) {

        String sql = "INSERT INTO kategori "
                + "(id_kategori, nama_kategori, deskripsi) "
                + "VALUES (?, ?, ?)";

        Connection conn = Koneksi.getConnection();

        if (conn == null) {
            System.out.println("Koneksi database tidak tersedia.");
            return false;
        }

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, kategori.getIdKategori());
            ps.setString(2, kategori.getNamaKategori());
            ps.setString(3, kategori.getDeskripsi());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal menambahkan kategori: " + e.getMessage());

            return false;
        }
    }

    public List<Menu> getAllMenu() {

        List<Menu> daftarMenu = new ArrayList<>();

        String sql = "SELECT m.id_menu, m.nama_menu, "
                + "m.id_kategori, k.nama_kategori, k.deskripsi, "
                + "m.harga, m.jenis_menu, m.detail_jenis "
                + "FROM menu m "
                + "JOIN kategori k ON m.id_kategori = k.id_kategori";

        Connection conn = Koneksi.getConnection();

        if (conn == null) {
            System.out.println("Koneksi database tidak tersedia.");
            return daftarMenu;
        }

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Kategori kategori = new Kategori(
                        rs.getString("id_kategori"),
                        rs.getString("nama_kategori"),
                        rs.getString("deskripsi")
                );

                String idMenu = rs.getString("id_menu");
                String namaMenu = rs.getString("nama_menu");
                double harga = rs.getDouble("harga");
                String jenisMenu = rs.getString("jenis_menu");
                String detailJenis = rs.getString("detail_jenis");

                Menu menu;

                if ("MAKANAN".equalsIgnoreCase(jenisMenu)) {

                    menu = new MenuMakanan(
                            idMenu,
                            namaMenu,
                            kategori,
                            harga,
                            detailJenis
                    );

                } else if ("MINUMAN".equalsIgnoreCase(jenisMenu)) {

                    menu = new MenuMinuman(
                            idMenu,
                            namaMenu,
                            kategori,
                            harga,
                            detailJenis
                    );

                } else {

                    System.out.println("Jenis menu tidak dikenali: " + idMenu);

                    continue;
                }

                daftarMenu.add(menu);
            }

        } catch (SQLException e) {
            System.out.println("Gagal mengambil menu: " + e.getMessage());
        }

        return daftarMenu;
    }

    public boolean tambahMenu(Menu menu) {

        String sql = "INSERT INTO menu "
                + "(id_menu, nama_menu, id_kategori, harga, "
                + "jenis_menu, detail_jenis) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        Connection conn = Koneksi.getConnection();

        if (conn == null) {
            System.out.println("Koneksi database tidak tersedia.");
            return false;
        }

        String jenisMenu;
        String detailJenis;

        if (menu instanceof MenuMakanan makanan) {

            jenisMenu = "MAKANAN";
            detailJenis = makanan.getJenisMakanan();

        } else if (menu instanceof MenuMinuman minuman) {

            jenisMenu = "MINUMAN";
            detailJenis = minuman.getJenisMinuman();

        } else {
            return false;
        }

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, menu.getIdMenu());
            ps.setString(2, menu.getNamaMenu());
            ps.setString(3, menu.getKategori().getIdKategori());
            ps.setDouble(4, menu.getHarga());
            ps.setString(5, jenisMenu);
            ps.setString(6, detailJenis);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal menambahkan menu: " + e.getMessage());

            return false;
        }
    }

    public Menu getMenuById(String idMenu) {

        String sql = "SELECT m.id_menu, m.nama_menu, "
                + "m.id_kategori, k.nama_kategori, k.deskripsi, "
                + "m.harga, m.jenis_menu, m.detail_jenis "
                + "FROM menu m "
                + "JOIN kategori k ON m.id_kategori = k.id_kategori "
                + "WHERE m.id_menu = ?";

        Connection conn = Koneksi.getConnection();

        if (conn == null) {
            System.out.println("Koneksi database tidak tersedia.");
            return null;
        }

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idMenu);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Kategori kategori = new Kategori(
                            rs.getString("id_kategori"),
                            rs.getString("nama_kategori"),
                            rs.getString("deskripsi")
                    );

                    String id = rs.getString("id_menu");
                    String nama = rs.getString("nama_menu");
                    double harga = rs.getDouble("harga");
                    String jenis = rs.getString("jenis_menu");
                    String detail = rs.getString("detail_jenis");

                    if ("MAKANAN".equalsIgnoreCase(jenis)) {

                        return new MenuMakanan(
                                id,
                                nama,
                                kategori,
                                harga,
                                detail
                        );

                    } else if ("MINUMAN".equalsIgnoreCase(jenis)) {

                        return new MenuMinuman(
                                id,
                                nama,
                                kategori,
                                harga,
                                detail
                        );
                    }

                    System.out.println("Jenis menu tidak dikenali: " + id);
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal mencari menu: " + e.getMessage());
        }

        return null;
    }

    public boolean updateMenu(
            String idMenu,
            String namaMenu,
            Kategori kategori,
            double harga,
            String detailJenis) {

        String sql = "UPDATE menu SET "
                + "nama_menu = ?, "
                + "id_kategori = ?, "
                + "harga = ?, "
                + "detail_jenis = ? "
                + "WHERE id_menu = ?";

        Connection conn = Koneksi.getConnection();

        if (conn == null || kategori == null) {
            System.out.println("Koneksi atau kategori tidak tersedia.");
            return false;
        }

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, namaMenu);
            ps.setString(2, kategori.getIdKategori());
            ps.setDouble(3, harga);
            ps.setString(4, detailJenis);
            ps.setString(5, idMenu);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal memperbarui menu: " + e.getMessage());

            return false;
        }
    }

    public boolean hapusMenu(String idMenu) {

        String sql = "DELETE FROM menu WHERE id_menu = ?";

        Connection conn = Koneksi.getConnection();

        if (conn == null) {
            System.out.println("Koneksi database tidak tersedia.");
            return false;
        }

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idMenu);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal menghapus menu: " + e.getMessage()    );

            return false;
        }
    }
}