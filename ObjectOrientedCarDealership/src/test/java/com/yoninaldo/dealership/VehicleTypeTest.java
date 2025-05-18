package com.yoninaldo.dealership;

import com.yoninaldo.dealership.VehicleType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

class VehicleTypeTest {

    @Test
    public void valueOf_should_returnCorrectEnum() {
        // act & assert
        assertEquals(VehicleType.CAR, VehicleType.valueOf("CAR"));
        assertEquals(VehicleType.SUV, VehicleType.valueOf("SUV"));
        assertEquals(VehicleType.TRUCK, VehicleType.valueOf("TRUCK"));
        assertEquals(VehicleType.VAN, VehicleType.valueOf("VAN"));
    }

    @Test
    public void valueOf_should_throwException_forInvalidType() {
        // act & assert
        assertThrows(IllegalArgumentException.class, () -> {
            VehicleType.valueOf("SEDAN");
        });
    }

    @ParameterizedTest
    @CsvSource({
            "car, CAR",
            "suv, SUV",
            "truck, TRUCK",
            "van, VAN"
    })
    public void fromString_should_convertStringToEnum(String input, String expectedEnumName) {

        // act
        VehicleType result = VehicleType.fromString(input);

        // assert
        assertEquals(VehicleType.valueOf(expectedEnumName), result);
    }
}