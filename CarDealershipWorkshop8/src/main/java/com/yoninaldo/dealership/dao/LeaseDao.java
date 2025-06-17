package com.yoninaldo.dealership.dao;

import com.yoninaldo.dealership.model.Contract;
import com.yoninaldo.dealership.model.LeaseContract;
import com.yoninaldo.dealership.model.Vehicle;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LeaseDao {
    private final DataSource dataSource;
    private final VehicleDao vehicleDao;

    public LeaseDao(DataSource dataSource) {
        this.dataSource = dataSource;
        this.vehicleDao = new VehicleDao(dataSource);
    }

    public void saveLeaseContract(LeaseContract contract) {
        String sql = "INSERT INTO lease_contracts (VIN, customer_name, customer_email, contract_date, " +
                "lease_start_date, lease_end_date, expected_ending_value, lease_fee, " +
                "total_price, monthly_payment) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Convert date string to SQL Date
            LocalDate contractDate = LocalDate.parse(contract.getDate(), DateTimeFormatter.ofPattern("yyyyMMdd"));
            LocalDate endDate = contractDate.plusYears(3); // 3-year lease

            ps.setString(1, contract.getVehicle().getVin());
            ps.setString(2, contract.getCustomerName());
            ps.setString(3, contract.getCustomerEmail());
            ps.setDate(4, Date.valueOf(contractDate));
            ps.setDate(5, Date.valueOf(contractDate)); // lease_start_date
            ps.setDate(6, Date.valueOf(endDate)); // lease_end_date
            ps.setDouble(7, contract.getExpectedEndingValue());
            ps.setDouble(8, contract.getLeaseFee());
            ps.setDouble(9, contract.getTotalPrice());
            ps.setDouble(10, contract.getMonthlyPayment());

            ps.executeUpdate();

            // Mark vehicle as sold
            vehicleDao.markVehicleAsSold(contract.getVehicle().getVin());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Contract> getAllLeaseContracts() {
        List<Contract> contracts = new ArrayList<>();
        String sql = "SELECT * FROM lease_contracts ORDER BY contract_date DESC";

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                LeaseContract contract = mapResultSetToLeaseContract(rs);
                if (contract != null) {
                    contracts.add(contract);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contracts;
    }

    private LeaseContract mapResultSetToLeaseContract(ResultSet rs) throws SQLException {
        String vin = rs.getString("VIN");
        Vehicle vehicle = vehicleDao.getVehicleByVin(vin);

        if (vehicle == null) return null;

        String dateStr = rs.getDate("contract_date").toLocalDate()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        LeaseContract contract = new LeaseContract(
                dateStr,
                rs.getString("customer_name"),
                rs.getString("customer_email"),
                vehicle
        );

        contract.setExpectedEndingValue(rs.getDouble("expected_ending_value"));
        contract.setLeaseFee(rs.getDouble("lease_fee"));

        return contract;
    }
}