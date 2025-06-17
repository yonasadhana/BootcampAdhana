package com.yoninaldo.dealership.dao;

import com.yoninaldo.dealership.model.Dealership;
import com.yoninaldo.dealership.model.Vehicle;
import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class DealershipDao {
    private final DataSource dataSource;
    private final VehicleDao vehicleDao;

    public DealershipDao(DataSource dataSource) {
        this.dataSource = dataSource;
        this.vehicleDao = new VehicleDao(dataSource);
    }

    public Dealership getDealership(int dealershipId) {
        String sql = "SELECT * FROM dealerships WHERE dealership_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, dealershipId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Dealership dealership = new Dealership(
                            rs.getString("name"),
                            rs.getString("address"),
                            rs.getString("phone")
                    );


                    List<Vehicle> vehicles = getVehiclesForDealership(dealershipId);
                    for (Vehicle vehicle : vehicles) {
                        dealership.addVehicle(vehicle);
                    }

                    return dealership;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Dealership getFirstDealership() {
        return getDealership(1); // Returns the first dealership as default
    }

    private List<Vehicle> getVehiclesForDealership(int dealershipId) {
        String sql = "SELECT v.* FROM vehicles v " +
                "JOIN inventory i ON v.VIN = i.VIN " +
                "WHERE i.dealership_id = ? AND v.sold = FALSE";

        List<Vehicle> vehicles = new java.util.ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, dealershipId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    vehicles.add(new Vehicle(
                            rs.getString("VIN"),
                            rs.getInt("year"),
                            rs.getString("make"),
                            rs.getString("model"),
                            rs.getString("type"),
                            rs.getString("color"),
                            rs.getInt("odometer"),
                            rs.getDouble("price"),
                            rs.getBoolean("sold")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicles;
    }

    public void saveDealership(Dealership dealership) {
        String sql = "UPDATE dealerships SET name = ?, address = ?, phone = ? WHERE dealership_id = 1";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dealership.getName());
            ps.setString(2, dealership.getAddress());
            ps.setString(3, dealership.getPhone());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}