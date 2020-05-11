/**
 * File: Project4.java
 * Author: Jared Buehner
 * Date: May 1, 2020
 * Purpose: This class constructs a GUI that allows users to add, remove, and display entries of properties
 * in a real estate database.
 */
// Imports.
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class Project4 {
    // Main method. Creates the main frame.
    public static void main(String[] args) {
        JFrame frame = new JFrame("Real Estate Database");
        frame.setSize(300,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new realEstatePanel());
        frame.setVisible(true);
    }
    // Defines the panel class.
    private static class realEstatePanel extends JPanel {
        // Adds GUI elements to the panel.
        private JLabel transactionLabel = new JLabel("Transaction No:");
        private JLabel addressLabel = new JLabel("Address:");
        private JLabel bedroomsLabel = new JLabel("Bedrooms:");
        private JLabel squareFootageLabel = new JLabel("Square Footage:");
        private JLabel priceLabel = new JLabel("Price:");
        private JTextField transactionField = new JTextField();
        private JTextField addressField = new JTextField();
        private JTextField bedroomsField = new JTextField();
        private JTextField squareFootageField = new JTextField();
        private JTextField priceField = new JTextField();
        private String[] operations = {"Insert", "Delete", "Find"};
        private JComboBox operationsList = new JComboBox(operations);
        private Status[] status = {Status.FOR_SALE, Status.UNDER_CONTRACT, Status.SOLD};
        private JComboBox statusList = new JComboBox(status);
        private JButton processButton = new JButton("Process");
        private JButton changeButton = new JButton("Change Status");
        // Tree map to store properties and ID's
        TreeMap<Integer, Property> propertyDb = new TreeMap<>();
        // Constructor for the panel.
        public realEstatePanel() {
            // Sets layout for panel and GUI elements.
            setSize(300, 300);
            setLayout(null);
            transactionLabel.setBounds(5, 10, 100, 25);
            transactionField.setBounds(150, 10, 150, 25);
            addressLabel.setBounds(5, 50, 100, 25);
            addressField.setBounds(150, 50, 150, 25);
            bedroomsLabel.setBounds(5, 90, 100, 25);
            bedroomsField.setBounds(150, 90, 150, 25);
            squareFootageLabel.setBounds(5, 130, 100, 25);
            squareFootageField.setBounds(150, 130, 150, 25);
            priceLabel.setBounds(5, 170, 100, 25);
            priceField.setBounds(150, 170, 150, 25);
            processButton.setBounds(0, 210, 150, 25);
            operationsList.setBounds(150, 210, 150, 25);
            changeButton.setBounds(0, 250, 150, 25);
            statusList.setBounds(150, 250, 150, 25);
            // Adds GUI elements to the panel.
            this.add(transactionLabel);
            this.add(transactionField);
            this.add(addressLabel);
            this.add(addressField);
            this.add(bedroomsLabel);
            this.add(bedroomsField);
            this.add(squareFootageLabel);
            this.add(squareFootageField);
            this.add(priceLabel);
            this.add(priceField);
            this.add(processButton);
            this.add(operationsList);
            this.add(changeButton);
            this.add(statusList);
            // Action Listener for the process button.
            processButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String processOption = String.valueOf(operationsList.getSelectedItem());
                    try {
                        switch (processOption) {
                            // Stores transaction ID and property information in the Tree Map.
                            case "Insert":
                                checkForDuplicates(getTransactionId());
                                propertyDb.put(getTransactionId(), getPropertyInfo());
                                JOptionPane.showMessageDialog(null, "Property has been successfully stored in the database.");
                                break;
                            // Deletes key value pair from the Tree Map.
                            case "Delete":
                                checkforExisting(getTransactionId());
                                propertyDb.remove(getTransactionId());
                                JOptionPane.showMessageDialog(null, "Property has been successfully removed from the database.");
                                break;
                            // Displays transaction ID and property information.
                            case "Find":
                                checkforExisting(getTransactionId());
                                Property getProperty = propertyDb.get(getTransactionId());
                                JOptionPane.showMessageDialog(null, getProperty.toString());
                                break;
                        }
                    // Catches and throws exception for imporper formatting.
                    } catch (NumberFormatException ne) {
                        JOptionPane.showMessageDialog(null, "Please enter proper number formatting.");
                    // Catches and throws exception if a duplicate exception is found.
                    } catch (DuplicateProperty de) {
                        JOptionPane.showMessageDialog(null, "Transaction ID already exists.");
                    // Catches and throws Not Found exception for the transaction ID.
                    } catch (PropertyNotFound pe) {
                        JOptionPane.showMessageDialog(null, "Transaction ID not found.");
                    }
                }
            });
            // Adds action listener to change status of a property.
            changeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Status statusOption = (Status) statusList.getSelectedItem();
                        checkforExisting(getTransactionId());
                        Property changeProperty = propertyDb.get(getTransactionId());
                        changeProperty.changeState(statusOption);
                        propertyDb.put(getTransactionId(), changeProperty);
                        JOptionPane.showMessageDialog(null, "Property status changed successfully.");
                    // Catches Not Found exception for the transaction ID.
                    } catch(PropertyNotFound pe) {
                        JOptionPane.showMessageDialog(null, "Transaction ID not found.");
                    // Catches exception for imporper number formatting.
                    } catch(NumberFormatException ne) {
                        JOptionPane.showMessageDialog(null, "Please enter proper number formatting.");
                    }
                }
            });
        }
        // Reads and throws exception if the user inputs an improper number format.
        private Property getPropertyInfo() throws NumberFormatException {
            String address = addressField.getText();
            int bedrooms = getInput(bedroomsField);
            int squareFeet = getInput(squareFootageField);
            int price = getInput(priceField);
            return new Property(address, bedrooms, squareFeet, price);
        }
        // Reads and throws exception if the user inputs an improper number format for the transaction ID.
        private int getTransactionId() throws NumberFormatException {
            return getInput(transactionField);
        }
        // Checks and throws an exception if a duplicate transaction ID is found.
        private void checkForDuplicates(int transactionId) throws DuplicateProperty{
            if(propertyDb.containsKey(transactionId)) {
                throw new DuplicateProperty();
            }
        }
        // Checks if the transaction ID exists and throws an exception if it does not exist.
        private void checkforExisting(int transactionId) throws PropertyNotFound {
            if(!propertyDb.containsKey(transactionId)) {
                throw new PropertyNotFound();
            }
        }
        // Converts strings into intergers.
        private int getInput(JTextField inputTextField) throws NumberFormatException {
            String inputString = inputTextField.getText();
            return Integer.parseInt(inputString);
        }
        // Throws exeption if the property already exists.
        private class DuplicateProperty extends Exception {
            public DuplicateProperty() {
                super();
            }
        }
        // Throws exception if property does not exist.
        private class PropertyNotFound extends Exception {
            public PropertyNotFound() {
                super();
            }
        }
    }
}
