package dao;

import model.Carro;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CarroDAO extends DAO {

    public CarroDAO() {
        super();
        conectar();
    }

    public void finalize() {
        close();
    }

    public boolean insert(Carro carro) {
        try {
            String sql = "INSERT INTO carros (marca, modelo, ano) VALUES (?, ?, ?)";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, carro.getMarca());
            st.setString(2, carro.getModelo());
            st.setInt(3, carro.getAno());
            st.executeUpdate();
            st.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Carro get(int id) {
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM carros WHERE id=" + id;
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return new Carro(
                        rs.getInt("id"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("ano")
                );
            }
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Carro> getAll() {
        List<Carro> carros = new ArrayList<>();
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM carros";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Carro carro = new Carro(
                        rs.getInt("id"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("ano")
                );
                carros.add(carro);
            }
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return carros;
    }

    public boolean update(Carro carro) {
        try {
            String sql = "UPDATE carros SET marca=?, modelo=?, ano=? WHERE id=?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, carro.getMarca());
            st.setString(2, carro.getModelo());
            st.setInt(3, carro.getAno());
            st.setInt(4, carro.getId());
            st.executeUpdate();
            st.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("DELETE FROM carros WHERE id=" + id);
            st.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

