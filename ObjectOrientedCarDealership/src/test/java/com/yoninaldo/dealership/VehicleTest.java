package com.yoninaldo.dealership;

import com.yoninaldo.dealership.Vehicle;
import com.yoninaldo.dealership.VehicleType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    @Test
    public void constructor_should_initializeVehicleCorrectly() {
        // arrange
        int vin = 12345;
        int year = 2021;
        String make = "Mercedes-Benz";
        String model = "S-Class";
        VehicleType vehicleType = VehicleType.CAR;
        String color = "Black";
        int odometer = 18750;
        double price = 89995.00;

        // act
        Vehicle vehicle = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);

        // assert
        assertEquals(vin, vehicle.getVin());
        assertEquals(year, vehicle.getYear());
        assertEquals(make, vehicle.getMake());
        assertEquals(model, vehicle.getModel());
        assertEquals(vehicleType, vehicle.getVehicleType());
        assertEquals(color, vehicle.getColor());
        assertEquals(odometer, vehicle.getOdometer());
        assertEquals(price, vehicle.getPrice());
    }

    @Test
    public void setters_should_updateValues() {
        // arrange
        Vehicle vehicle = new Vehicle(12345, 2021, "Mercedes-Benz", "S-Class",
                VehicleType.CAR, "Black", 18750, 89995.00);
        int newVin = 98765;
        int newYear = 2022;
        String newMake = "BMW";
        String newModel = "X7";
        VehicleType newType = VehicleType.SUV;
        String newColor = "White";
        int newOdometer = 10000;
        double newPrice = 92500.00;

        // act
        vehicle.setVin(newVin);
        vehicle.setYear(newYear);
        vehicle.setMake(newMake);
        vehicle.setModel(newModel);
        vehicle.setVehicleType(newType);
        vehicle.setColor(newColor);
        vehicle.setOdometer(newOdometer);
        vehicle.setPrice(newPrice);

        // assert
        assertEquals(newVin, vehicle.getVin());
        assertEquals(newYear, vehicle.getYear());
        assertEquals(newMake, vehicle.getMake());
        assertEquals(newModel, vehicle.getModel());
        assertEquals(newType, vehicle.getVehicleType());
        assertEquals(newColor, vehicle.getColor());
        assertEquals(newOdometer, vehicle.getOdometer());
        assertEquals(newPrice, vehicle.getPrice());
    }

    @ParameterizedTest
    @CsvSource({
            "12345, 2021, Mercedes-Benz, S-Class, CAR, Black, 18750, 89995.00",
            "23456, 2022, BMW, X7, SUV, Alpine White, 12300, 92500.00",
            "67890, 2020, Bentley, Continental GT, CAR, British Racing Green, 15600, 165000.00",
            "24680, 2020, Bugatti, Chiron, CAR, French Racing Blue, 1200, 3200000.00"
    })
    public void toString_should_formatCorrectly(int vin, int year, String make, String model,
                                                String typeStr, String color, int odometer, double price) {
        // arrange
        VehicleType vehicleType = VehicleType.valueOf(typeStr);
        Vehicle vehicle = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
        String expected = String.format("%d | %d | %s | %s | %s | %s | %d | %.2f",
                vin, year, make, model, vehicleType, color, odometer, price);

        // act
        String result = vehicle.toString();

        // assert
        assertEquals(expected, result);
    }
}