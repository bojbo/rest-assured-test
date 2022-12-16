package testassured;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Month;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

public class Numbers {
    public static boolean isOdd(int number) {
        return number % 2 == 0;
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5,4, -3, Integer.MAX_VALUE})
    void isOdd_ShouldReturnTrueForOddNumbers(int number) {
        assertTrue(Numbers.isOdd(number));
    }

    @ParameterizedTest
    @EnumSource(Month.class)
    void getValueForAMonth_IsAlwaysBetweenOneAndTwelve(Month month) {
        int monthNumber = month.getValue();
        System.out.printf("%s月", monthNumber);
        assertTrue(monthNumber >= 1 && monthNumber <= 12);
    }

    @ParameterizedTest
    @EnumSource(value = Month.class, names = {"APRIL", "JUNE", "SEPTEMBER", "NOVEMBER"})
    void SomeMonth_Are30DaysLong(Month month) {
        final boolean isALeapYear = false;
        assertEquals(30, month.length(isALeapYear));
    }
}


