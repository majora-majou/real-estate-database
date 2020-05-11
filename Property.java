/**
 * File: Property.java
 * Author: Jared Buehner
 * Date: May 1, 2020
 * Purpose: This class implements ChangeableState interface and defines Property object to store property information
 */
public class Property <T extends Enum <T>> implements StateChangeable <T> {
    private String propertyAddress;
    private int numberOfBedrooms;
    private int squareFootage;
    private int price;
    private Status saleStatus;
    // Property constructor that defines status as for sale.
    public Property(String propertyAddress, int numberOfBedrooms, int squareFootage, int price) {
        this.propertyAddress = propertyAddress;
        this.numberOfBedrooms = numberOfBedrooms;
        this.squareFootage = squareFootage;
        this.price = price;
        this.saleStatus = Status.FOR_SALE;
    }
    // Method to change the status of a property.
    public void changeState(T inputStatus) {
       this.saleStatus = (Status)inputStatus;
    }
    // Method to return a string of property information.
    @Override
    public String toString() {
        return new String("Property Address: " + this.propertyAddress + "\nNumber of Bedrooms: " + this.numberOfBedrooms
        + "\nSquare Footage: " + this.squareFootage + "\nPrice: $" + this.price + "\nCurrent Status: " + this.saleStatus);
    }
}
