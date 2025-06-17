package com.yoninaldo.dealership.dao;

import com.yoninaldo.dealership.model.Vehicle;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDao {
    private final DataSource dataSource;

    public VehicleDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public List<Vehicle> getVehiclesByPriceRange(double minPrice, double maxPrice) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles WHERE price >= ? AND price <= ? AND sold = FALSE";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, minPrice);
            ps.setDouble(2, maxPrice);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    vehicles.add(mapResultSetToVehicle(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicles;
    }

    public List<Vehicle> getVehiclesByMakeModel(String make, String model) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql;

        if (model == null || model.isEmpty()) {
            sql = "SELECT * FROM vehicles WHERE make = ? AND sold = FALSE";
        } else {
            sql = "SELECT * FROM vehicles WHERE make = ? AND model = ? AND sold = FALSE";
        }

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, make);
            if (model != null && !model.isEmpty()) {
                ps.setString(2, model);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    vehicles.add(mapResultSetToVehicle(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicles;
    }

    public List<Vehicle> getVehiclesByYearRange(int minYear, int maxYear) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles WHERE year >= ? AND year <= ? AND sold = FALSE";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, minYear);
            ps.setInt(2, maxYear);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    vehicles.add(mapResultSetToVehicle(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicles;
    }

    public List<Vehicle> getVehiclesByColor(String color) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles WHERE color = ? AND sold = FALSE";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, color);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    vehicles.add(mapResultSetToVehicle(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicles;
    }

    public List<Vehicle> getVehiclesByMileageRange(int minMileage, int maxMileage) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles WHERE odometer >= ? AND odometer <= ? AND sold = FALSE";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, minMileage);
            ps.setInt(2, maxMileage);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    vehicles.add(mapResultSetToVehicle(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicles;
    }

    public List<Vehicle> getVehiclesByType(String vehicleType) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles WHERE type = ? AND sold = FALSE";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, vehicleType);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    vehicles.add(mapResultSetToVehicle(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicles;
    }


    public void addVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO vehicles (VIN, make, model, year, color, type, odometer, price, sold) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, vehicle.getVin());
            ps.setString(2, vehicle.getMake());
            ps.setString(3, vehicle.getModel());
            ps.setInt(4, vehicle.getYear());
            ps.setString(5, vehicle.getColor());
            ps.setString(6, vehicle.getVehicleType());
            ps.setInt(7, vehicle.getOdometer());
            ps.setDouble(8, vehicle.getPrice());
            ps.setBoolean(9, vehicle.isSold());

            ps.executeUpdate();

            addVehicleToInventory(vehicle.getVin(), 1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeVehicle(String vin) {
        removeVehicleFromInventory(vin);

        String sql = "DELETE FROM vehicles WHERE VIN = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, vin);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles WHERE sold = FALSE";

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                vehicles.add(mapResultSetToVehicle(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicles;
    }

    public Vehicle getVehicleByVin(String vin) {
        String sql = "SELECT * FROM vehicles WHERE VIN = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, vin);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToVehicle(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void markVehicleAsSold(String vin) {
        String sql = "UPDATE vehicles SET sold = TRUE WHERE VIN = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, vin);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addVehicleToInventory(String vin, int dealershipId) {
        String sql = "INSERT INTO inventory (dealership_id, VIN) VALUES (?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, dealershipId);
            ps.setString(2, vin);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void removeVehicleFromInventory(String vin) {
        String sql = "DELETE FROM inventory WHERE VIN = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, vin);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Vehicle mapResultSetToVehicle(ResultSet rs) throws SQLException {
        return new Vehicle(
                rs.getString("VIN"),
                rs.getInt("year"),
                rs.getString("make"),
                rs.getString("model"),
                rs.getString("type"),
                rs.getString("color"),
                rs.getInt("odometer"),
                rs.getDouble("price"),
                rs.getBoolean("sold")
        );
    }
}