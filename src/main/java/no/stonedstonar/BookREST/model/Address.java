package no.stonedstonar.BookREST.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.RowId;
import org.springframework.data.annotation.Reference;
import org.springframework.web.bind.annotation.Mapping;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Represents a basic object that holds the address of someplace.
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
@Entity
public class Address implements Serializable {

    @Id
    @GeneratedValue
    private long addressId;

    @Column(nullable = false)
    private String streetName;

    @Column(nullable = false)
    private int houseNumber;

    private char houseLetter;

    private int floor;

    private int apartmentNumber;

    @Column(nullable = false)
    private int postalCode;

    /**
     * An empty constructor for JPA.
     */
    public Address() {
    }

    /**
     * Makes an instance of the Address class.
     * @param userId the id of the user that lives on this address.
     * @param streetName the name of the street.
     * @param houseNumber the house number.
     * @param houseLetter the living quarter letter.
     * @param floor the floor of the living area.
     * @param apartmentNumber the apartment number.
     * @param postalCode the postal code of the area.
     */
    @JsonCreator
    public Address(long userId, String streetName, int houseNumber, char houseLetter, int floor, int apartmentNumber, int postalCode){
        checkStreetName(streetName);
        checkHouseNumber(houseNumber);
        checkIfNumberIsBetween(0, 99, floor, "floor");
        checkIfNumberIsBetween(0, 99, apartmentNumber, "apartment number");
        checkPostalCode(postalCode);
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.houseLetter = houseLetter;
        this.floor = floor;
        this.apartmentNumber = apartmentNumber;
        this.postalCode = postalCode;
    }

    /**
     * Makes an instance of the Address class.
     * @param streetName the name of the street.
     * @param houseNumber the house number.
     * @param postalCode the postal code of the area.
     */
    @JsonCreator
    public Address(String streetName, int houseNumber, int postalCode){
        checkStreetName(streetName);
        checkHouseNumber(houseNumber);
        checkPostalCode(postalCode);
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
    }

    /**
     * Gets the street name.
     * @return the street name.
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Gets the house number.
     * @return house number.
     */
    public int getHouseNumber() {
        return houseNumber;
    }

    /**
     * Gets the house letter.
     * @return the house letter.
     */
    public char getHouseLetter() {
        return houseLetter;
    }

    /**
     * Gets the floor.
     * @return the floor.
     */
    public int getFloor() {
        return floor;
    }

    /**
     * Gets the apartment number.
     * @return the apartment number.
     */
    public int getApartmentNumber() {
        return apartmentNumber;
    }

    /**
     * Gets the postal code.
     * @return the postal code.
     */
    public int getPostalCode() {
        return postalCode;
    }

    /**
     * Checks if the input house number is above zero.
     * @param houseNumber the house number.
     */
    private void checkHouseNumber(int houseNumber){
        checkIfLongIsAboveZero(houseNumber, "housenumber");
    }

    /**
     * Checks if the input street name is null or empty.
     * @param streetName the streetname to check.
     */
    private void checkStreetName(String streetName){
        checkString(streetName, "street name");
    }

    /**
     * Checks if the postalcode is a valid format.
     * @param postalCode the postalcode to check.
     */
    private void checkPostalCode(int postalCode){
        checkIfNumberIsBetween(0, 10000, postalCode, "postalcode");
    }

    /**
     * Gets the whole address of the user.
     * @return the whole address as one string.
     */
    public String getWholeAddress(){
        if (floor == 0 && apartmentNumber == 0){
            return streetName + " " + houseNumber + " " + postalCode;
        }else {
            return streetName + " " + houseNumber + " " + houseLetter + convertNumberToCorrectTextFormat(floor, 2)
                    + convertNumberToCorrectTextFormat(apartmentNumber, 2) + " "
                    + postalCode;
        }
    }

    /**
     * Converts a number like 2 into 02.
     * @param number the number to convert.
     * @param amountOfDigits the amount of digits the number should have.
     * @return a string with the wanted amount of digits.
     */
    public String convertNumberToCorrectTextFormat(long number, int amountOfDigits){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(number);
        while (stringBuilder.length() < amountOfDigits){
            stringBuilder.append("0");
        }
        return stringBuilder.toString();
    }

    /**
     * Checks if the number is between a min and max value.
     * @param min the minimum value the number can be.
     * @param max the maximum value the number can be.
     * @param number the number you want to check.
     * @param prefix the error the exception should show.
     */
    private void checkIfNumberIsBetween(long min, long max, long number, String prefix){
        if (number < min || number > max){
            throw new IllegalArgumentException("The " + prefix + " must be between " + min + " and " + max + ".");
        }
    }

    /**
     * Checks if the input long is above zero.
     * @param number the number to check.
     * @param prefix the prefix the error should have.
     */
    private void checkIfLongIsAboveZero(long number, String prefix){
        if (number <= 0){
            throw new IllegalArgumentException("The " + prefix + " must be above zero.");
        }
    }

    /**
     * Checks if a string is of a valid format or not.
     * @param stringToCheck the string you want to check.
     * @param errorPrefix the error the exception should have if the string is invalid.
     */
    private void checkString(String stringToCheck, String errorPrefix){
        checkIfObjectIsNull(stringToCheck, errorPrefix);
        if (stringToCheck.isEmpty()){
            throw new IllegalArgumentException("The " + errorPrefix + " cannot be empty.");
        }
    }

    /**
     * Checks if an object is null.
     * @param object the object you want to check.
     * @param error the error message the exception should have.
     */
    private void checkIfObjectIsNull(Object object, String error){
       if (object == null){
           throw new IllegalArgumentException("The " + error + " cannot be null.");
       }
    }
}
