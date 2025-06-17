package com.yoninaldo.dealership.dao;

import com.yoninaldo.dealership.model.AddOn;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddOnDao {
    private final DataSource dataSource;

    public AddOnDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<AddOn> getAllAddOns() {
        List<AddOn> addOns = new ArrayList<>();
        String sql = "SELECT * FROM add_ons ORDER BY name";

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                addOns.add(new AddOn(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return addOns;
    }

    public AddOn getAddOnById(int id) {
        String sql = "SELECT * FROM add_ons WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new AddOn(
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getDouble("price")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}