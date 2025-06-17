package com.yoninaldo.dealership.dao;

import com.yoninaldo.dealership.model.AddOn;
import com.yoninaldo.dealership.model.Contract;
import com.yoninaldo.dealership.model.SalesContract;
import com.yoninaldo.dealership.model.Vehicle;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SalesDao {
    private final DataSource dataSource;
    private final VehicleDao vehicleDao;

    public SalesDao(DataSource dataSource) {
        this.dataSource = dataSource;
        this.vehicleDao = new VehicleDao(dataSource);
    }

    public void saveSalesContract(SalesContract contract) {
        String sql = "INSERT INTO sales_contracts (VIN, customer_name, customer_email, contract_date, " +
                "sale_price, sales_tax_amount, recording_fee, processing_fee, total_price, " +
                "finance, monthly_payment) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Convert date string to SQL Date
            LocalDate date = LocalDate.parse(contract.getDate(), DateTimeFormatter.ofPattern("yyyyMMdd"));

            ps.setString(1, contract.getVehicle().getVin());
            ps.setString(2, contract.getCustomerName());
            ps.setString(3, contract.getCustomerEmail());
            ps.setDate(4, Date.valueOf(date));
            ps.setDouble(5, contract.getVehicle().getPrice());
            ps.setDouble(6, contract.getSalesTaxAmount());
            ps.setDouble(7, contract.getRecordingFee());
            ps.setDouble(8, contract.getProcessingFee());
            ps.setDouble(9, contract.getTotalPrice());
            ps.setBoolean(10, contract.isFinance());
            ps.setDouble(11, contract.getMonthlyPayment());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int contractId = rs.getInt(1);

                    saveContractAddOns(contractId, contract.getAddOns());
                }
            }

            vehicleDao.markVehicleAsSold(contract.getVehicle().getVin());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveContractAddOns(int contractId, List<AddOn> addOns) {
        if (addOns.isEmpty()) return;

        String sql = "INSERT INTO contract_add_ons (contract_id, add_on_id) VALUES (?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (AddOn addOn : addOns) {
                int addOnId = getAddOnId(addOn.getName());
                if (addOnId > 0) {
                    ps.setInt(1, contractId);
                    ps.setInt(2, addOnId);
                    ps.addBatch();
                }
            }

            ps.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getAddOnId(String addOnName) {
        String sql = "SELECT id FROM add_ons WHERE name = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, addOnName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public List<Contract> getAllSalesContracts() {
        List<Contract> contracts = new ArrayList<>();
        String sql = "SELECT * FROM sales_contracts ORDER BY contract_date DESC";

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                SalesContract contract = mapResultSetToSalesContract(rs);
                if (contract != null) {
                    contracts.add(contract);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contracts;
    }

    private SalesContract mapResultSetToSalesContract(ResultSet rs) throws SQLException {
        String vin = rs.getString("VIN");
        Vehicle vehicle = vehicleDao.getVehicleByVin(vin);

        if (vehicle == null) return null;

        String dateStr = rs.getDate("contract_date").toLocalDate()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        SalesContract contract = new SalesContract(
                dateStr,
                rs.getString("customer_name"),
                rs.getString("customer_email"),
                vehicle,
                rs.getBoolean("finance")
        );

        contract.setSalesTaxAmount(rs.getDouble("sales_tax_amount"));
        contract.setRecordingFee(rs.getDouble("recording_fee"));
        contract.setProcessingFee(rs.getDouble("processing_fee"));


        int contractId = rs.getInt("id");
        List<AddOn> addOns = getAddOnsForContract(contractId);
        for (AddOn addOn : addOns) {
            contract.addAddOn(addOn);
        }

        return contract;
    }

    private List<AddOn> getAddOnsForContract(int contractId) {
        List<AddOn> addOns = new ArrayList<>();
        String sql = "SELECT a.* FROM add_ons a " +
                "JOIN contract_add_ons ca ON a.id = ca.add_on_id " +
                "WHERE ca.contract_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, contractId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    addOns.add(new AddOn(
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getDouble("price")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return addOns;
    }
}