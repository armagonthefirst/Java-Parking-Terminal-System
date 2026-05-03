/**
* Project 2: ParkingTerminalGUI.java class. Handles the graphical user interface and calls all the functional methods
* @author Mohamed Shariq Usoof <104841889>
* @version java 22
*/

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

    /**
   * Class, extending JFrame to assist with setting up components
   * 
   */

public class ParkingTerminalGUI extends JFrame {
    private JPanel staffSlotsPanel;                         // Creating staff slots area panel
    private JPanel visitorSlotsPanel;                       // Creating visitor slots area panel
    private JPanel inputPanel;                              // Creating input area panel (bottom)
    private int numStaffSlots;                              // Storing number of staff slots
    private int numVisitorSlots;                            // Storing number of visitor slots
    private static final int MAX_SLOTS = 10;                // Max slots for staff and visitor is 10 each, for GUI stability

    // Lists to keep track of buttons and parking slots
    private List<JButton> staffSlotButtons;
    private List<JButton> visitorSlotButtons;
    private List<Parkingslot> staffSlots;
    private List<Parkingslot> visitorSlots;

    private Carpark carpark; // Carpark object to handle slots and cars

/**
 * Constructor for ParkingTerminalGUI.
 * Initializes the main window of the application, setting up its size, layout, and components.
 * The constructor also initializes the carpark object and lists used to track parking slots and buttons.
 */

    public ParkingTerminalGUI() {
        super("Parking Slot Application");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize Carpark
        carpark = new Carpark();

        // Initialize lists
        staffSlotButtons = new ArrayList<>();
        visitorSlotButtons = new ArrayList<>();
        staffSlots = new ArrayList<>();
        visitorSlots = new ArrayList<>();

        // Call setupLayout to initialize all components and layout
        setupLayout();

        setVisible(true);
    }

        /**
     * Method for prompting user for staff/visitor slots upon App opening
     *
    * Prompts the user to enter the number of staff or visitor parking slots via a dialog box.
    * Validates user input
    * Keeps prompting the user until valid input is provided.
    * if valid, sends slots to Carpark.createSlots().
    *
    * @param message the message to be displayed in the input dialog box
    * @param max the maximum allowable value for the number of slots
    * @return the valid number entered by the user
    */

    private int promptForNumber(String message, int max) {
        int number = 0;
        while (number <= 0 || number > max) {
            String input = JOptionPane.showInputDialog(this, message);
            if (input != null) {
                try {
                    number = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid number between 1 and " + max);
                }
            }
        }
        return number;
    }

/**
 * Method to set up the layout of the parking terminal application GUI.
 *
 * Prompts the user to enter the number of staff and visitor parking slots.
 * Calls the carpark.createSlots() method to initialize parking slots.
 * Initializes GUI components, such as panels for staff and visitor slots, buttons representing slots,
 * and a menu panel to handle actions.
 * Configures the layout of the main components, including the parking slots, menu panel, and input panel.
 * Ensures that the user interface is visually structured and components are properly displayed.
 */

    private void setupLayout() {
        // Prompt the user for the number of staff and visitor slots
        numStaffSlots = promptForNumber("Enter the number of staff parking slots (max 10):", MAX_SLOTS);
        numVisitorSlots = promptForNumber("Enter the number of visitor parking slots (max 10):", MAX_SLOTS);
    
        // Call the carpark.createSlots() method to initialize the slots
        carpark.createSlots(numStaffSlots, numVisitorSlots);
    
        // create panels for staff and visitor slots
        staffSlotsPanel = new JPanel(new GridLayout(numStaffSlots, 1, 5, 5));
        visitorSlotsPanel = new JPanel(new GridLayout(numVisitorSlots, 1, 5, 5));
    
        staffSlotsPanel.setBackground(new Color(19, 29, 53)); // Dark Blue background
        visitorSlotsPanel.setBackground(new Color(19, 29, 53)); // Dark Blue background
    
        // Initialize the buttons representing the slots in the GUI
        initializeSlots();
    
        // Panel to hold the parking slots
        JPanel parkingSlotsPanel = new JPanel(new GridLayout(2, 1));
        parkingSlotsPanel.add(new JLabel("Staff Slots", SwingConstants.CENTER));
        parkingSlotsPanel.add(new JLabel("Visitor Slots", SwingConstants.CENTER));
        parkingSlotsPanel.add(staffSlotsPanel);
        parkingSlotsPanel.add(visitorSlotsPanel);
    
        // Panel for the menu on the right
        JPanel menuPanel = new JPanel(new GridLayout(8, 1, 5, 5));
        menuPanel.setBackground(new Color(19, 29, 53)); // Dark Blue background
        setupMenuPanel(menuPanel);
    
        // Add the parking slots panel to the center and menu to the right
        add(parkingSlotsPanel, BorderLayout.CENTER);
        add(menuPanel, BorderLayout.EAST);
    
        // Initialize input panel at the bottom, initially hidden
        inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setBackground(new Color(19, 29, 53)); // Dark Blue background
        inputPanel.setVisible(false); // Hidden until a menu option is selected
        add(inputPanel, BorderLayout.SOUTH);
    }
    
/**
 * Initializes parking slots for both staff and visitor sections in the GUI.
 *
 * Clears existing components and initializes buttons for each parking slot based on the data from the carpark object.
 * The buttons are added to the corresponding panels and their colors are set according to their status (occupied or vacant).
 * Adds event listeners to each button to handle user interactions.
 */

    private void initializeSlots() {
        // Initialize staff slots based on carpark object data
        staffSlotsPanel.removeAll(); // Remove previous components to avoid duplication
        staffSlotButtons.clear(); // Clear button references to ensure consistency
    
        List<Parkingslot> staffSlots = carpark.getStaffSlots(); // Assuming you have a method in Carpark to retrieve staff slots
        for (Parkingslot slot : staffSlots) {
            String slotID = slot.getID();
            JButton slotButton = new JButton(slotID + (slot.getParked() ? " - Occupied" : " - Vacant"));
            
            // Set the button color based on the slot's status
            if (slot.getParked()) {
                slotButton.setBackground(new Color(0, 255, 255)); // Cyan color for occupied slots
            } else {
                slotButton.setBackground(new Color(255, 216, 177)); // Light orange color for staff slots
            }
    
            staffSlotButtons.add(slotButton);
            staffSlotsPanel.add(slotButton);
    
            // Add ActionListener for each slot button
            slotButton.addActionListener(e -> {
                handleSlotClick(slot, slotButton);
            });
        }
    
        // Initialize visitor slots based on carpark object data
        visitorSlotsPanel.removeAll(); // Remove previous components to avoid duplication
        visitorSlotButtons.clear(); // Clear button references to ensure consistency
    
        List<Parkingslot> visitorSlots = carpark.getVisitorSlots(); // Assuming you have a method in Carpark to retrieve visitor slots
        for (Parkingslot slot : visitorSlots) {
            String slotID = slot.getID();
            JButton slotButton = new JButton(slotID + (slot.getParked() ? " - Occupied" : " - Vacant"));
            
            // Set the button color based on the slot's status
            if (slot.getParked()) {
                slotButton.setBackground(new Color(0, 255, 255)); // Cyan color for occupied slots
            } else {
                slotButton.setBackground(new Color(139, 0, 0)); // Dark reddish color for visitor slots
            }
    
            visitorSlotButtons.add(slotButton);
            visitorSlotsPanel.add(slotButton);
    
            // Add ActionListener for each slot button
            slotButton.addActionListener(e -> {
                handleSlotClick(slot, slotButton);
            });
        }
    
        revalidate();
        repaint();
    }

    /**
     * Main method for starting the GUI
     */

    public static void main(String[] args) {
        new ParkingTerminalGUI();
    }

/**
 * Displays a list of all parking slots.
 *
 * Retrieves information about all slots from the carpark object using the listSlots method.
 * Displays the details in a dialog box for the user.
 */

    private void listAllSlots() {
        // Call the carpark.listSlots method and get the output
        String slotDetails = carpark.listSlots();
    
        // Display the output in a dialog box
        JOptionPane.showMessageDialog(this, slotDetails, "List of Parking Slots", JOptionPane.INFORMATION_MESSAGE);
    }

/**
 * Prepares the input panel for finding a car by reg number
 *
 * Sets up input panel with fields to enter the car reg number
 * Prompts user to enter regist number and then calls the carpark.findCar
 * Displays result in dialog box
 */
    
    private void prepareFindCar() {
        // Logic to set up the input panel for finding a car
        clearInputPanel();
        inputPanel.setVisible(true);
        inputPanel.setLayout(new GridLayout(2, 2, 5, 5)); // 2 rows, 2 columns
    
        // Updated label colors to orange
        JLabel regLabel = new JLabel("Car RNO (E.G A0231):");
        regLabel.setForeground(new Color(253, 137, 45)); // Set text color to orange
        JTextField regField = new JTextField(5);
    
        JButton submitButton = new JButton("Find Car");
        submitButton.addActionListener(e -> {
            String carRNO = regField.getText();
            String result = carpark.findCar(carRNO);  // Call carpark.findCar method and store result
            JOptionPane.showMessageDialog(this, result);  // Show result in a dialog box
            clearInputPanel();
        });
    
        // Adding components to the input panel
        inputPanel.add(regLabel);
        inputPanel.add(regField);
        inputPanel.add(new JLabel()); // Empty placeholder
        inputPanel.add(submitButton);
    
        revalidate();
        repaint();
    }

/**
 * Prepares input panel for removing car by reg number
 *
 * Sets up panel with fields for reg number
 * Finds car using carpark.findCar()
 * If found, calls carpark.deleteCar() to remove it
 * Updates slot button to show park status, sets color based on staff or visitor
 * Shows error if car isn't found
 */

    private void prepareRemoveCar() {
        clearInputPanel();
        inputPanel.setVisible(true);
        inputPanel.setLayout(new GridLayout(2, 2, 5, 5)); // 2 rows, 2 columns with spacing
    
        JLabel regLabel = new JLabel("Car RNO (E.G A0231):");
        regLabel.setForeground(new Color(253, 137, 45)); // Set text color to orange
        JTextField regField = new JTextField(5);
    
        JButton submitButton = new JButton("Remove Car");
        submitButton.addActionListener(e -> {
            String carRNO = regField.getText();
    
            // Call findCar() to get the slot ID associated with the car registration number
            String findResult = carpark.findCar(carRNO);
            String slotID = null;
            if (findResult.contains("Your car has been found in slot")) {
                // Extract the slot ID after the phrase "Your car has been found in slot"
                int startIndex = findResult.indexOf("slot ") + 5;
                int endIndex = findResult.indexOf("\n", startIndex); // Find the newline to determine the end
                if (endIndex == -1) { 
                    endIndex = findResult.length(); // In case there's no newline, get until the end of the string
                }
                slotID = findResult.substring(startIndex, endIndex).trim(); // Trim to remove any unnecessary whitespace
            }
    
            // If slot ID is found, proceed to delete the car
            if (slotID != null) {
                String deleteResult = carpark.deleteCar(carRNO);
                JOptionPane.showMessageDialog(this, deleteResult); // Display the result of deletion
    
                // If the car is successfully unparked, update the corresponding slot button
                if (deleteResult.contains("Parking")) {
                    JButton slotButton = getSlotButtonById(slotID);
                    if (slotButton != null) {
                        if (slotID.startsWith("S")) {
                            slotButton.setBackground(new Color(255, 218, 179)); // Light orange for staff slot
                        } else {
                            slotButton.setBackground(new Color(139, 0, 0)); // Dark reddish for visitor slot
                        }
                        slotButton.setText(slotID + " - Vacant");
                    }
                }
            } else {
                // If no slot found, show an error message
                JOptionPane.showMessageDialog(this, "Error: The car with registration number " + carRNO + " is not parked here.");
            }
    
            clearInputPanel();
        });
    
        inputPanel.add(regLabel);
        inputPanel.add(regField);
        inputPanel.add(new JLabel()); // Empty placeholder for better alignment
        inputPanel.add(submitButton);
    
        revalidate();
        repaint();
    }

/**
 * Prepares input panel for adding a new parking slot
 *
 * Sets up panel with fields for slot ID and staff/visitor checkbox
 * Adds the slot using carpark.addSlot() if valid
 * Adds new button for the slot, sets color based on type (staff or visitor)
 * Shows success or error message in a dialog box
 */

 private void prepareAddSlot() {
    clearInputPanel();
    inputPanel.setVisible(true);
    inputPanel.setLayout(new GridLayout(3, 2, 5, 5)); // 3 rows, 2 columns with spacing

    JLabel slotLabel = new JLabel("Slot ID (E.G A00):");
    slotLabel.setForeground(new Color(253, 137, 45)); // Set text color to orange
    JTextField slotField = new JTextField(5);
    JCheckBox isStaffCheck = new JCheckBox("Is Staff?");
    isStaffCheck.setForeground(new Color(253, 137, 45)); // Set checkbox label color to orange

    JButton submitButton = new JButton("Add Slot");
    submitButton.addActionListener(e -> {
        String slotID = slotField.getText();
        boolean isStaff = isStaffCheck.isSelected();
        
        // Add the slot to the backend carpark
        String result = carpark.addSlot(slotID, isStaff);
        JOptionPane.showMessageDialog(this, result);

        if (result.contains("Successfully added")) {
            // Retrieve the updated list of slots from the backend
            List<Parkingslot> updatedSlots = isStaff ? carpark.getStaffSlots() : carpark.getVisitorSlots();
            
            // Find the newly added slot in the updated list
            Parkingslot newSlot = updatedSlots.stream()
                    .filter(slot -> slot.getID().equals(slotID))
                    .findFirst()
                    .orElse(null);
            
            if (newSlot != null) {
                // Create a new button for the slot
                JButton newSlotButton = new JButton(slotID + " - Vacant");
                
                // Set slot button colors based on type
                if (isStaff) {
                    newSlotButton.setBackground(new Color(255, 223, 186)); // Very light orange for staff slots
                    staffSlots.add(newSlot); // Add to staff slots list
                    staffSlotButtons.add(newSlotButton);
                    staffSlotsPanel.add(newSlotButton);
                } else {
                    newSlotButton.setBackground(new Color(139, 0, 0)); // Dark reddish for visitor slots
                    visitorSlots.add(newSlot); // Add to visitor slots list
                    visitorSlotButtons.add(newSlotButton);
                    visitorSlotsPanel.add(newSlotButton);
                }

                // Add ActionListener for the new slot button
                newSlotButton.addActionListener(event -> {
                    handleSlotClick(newSlot, newSlotButton);
                });

                // Refresh the GUI
                revalidate();
                repaint();
            }
        }

        clearInputPanel();
    });

    // Add updated components to the input panel
    inputPanel.add(slotLabel);
    inputPanel.add(slotField);
    inputPanel.add(isStaffCheck);
    inputPanel.add(submitButton);

    revalidate();
    repaint();
}

/**
 * Prepares input panel for deleting parking slot
 *
 * Sets up fields for entering slot ID
 * Calls carpark.deleteSlot() to delete if valid
 * Removes the slot button from the UI if successfully deleted
 * Shows a message box with the result
 */

private void prepareDeleteSlot() {
    clearInputPanel();
    inputPanel.setVisible(true);
    inputPanel.setLayout(new GridLayout(2, 2, 5, 5)); // 2 rows, 2 columns with spacing

    // Update label color to orange
    JLabel slotLabel = new JLabel("Slot ID (E.G A00):");
    slotLabel.setForeground(new Color(253, 137, 45)); // Set text color to orange
    JTextField slotField = new JTextField(5);

    JButton submitButton = new JButton("Delete Slot");
    submitButton.addActionListener(e -> {
        String slotID = slotField.getText();
        String result = carpark.deleteSlot(slotID);

        if (result.contains("deleted")) {
            // Remove the corresponding button from the GUI
            JButton slotButton = getSlotButtonById(slotID);
            if (slotButton != null) {
                if (slotID.startsWith("S")) {
                    staffSlotButtons.remove(slotButton);
                    staffSlotsPanel.remove(slotButton);
                    staffSlots.removeIf(slot -> slot.getID().equals(slotID));
                } else {
                    visitorSlotButtons.remove(slotButton);
                    visitorSlotsPanel.remove(slotButton);
                    visitorSlots.removeIf(slot -> slot.getID().equals(slotID));
                }
            }
        }

        JOptionPane.showMessageDialog(this, result);
        revalidate();
        repaint();
        clearInputPanel();
    });

    // Add updated components to the input panel
    inputPanel.add(slotLabel);
    inputPanel.add(slotField);
    inputPanel.add(submitButton);

    revalidate();
    repaint();
}

/**
 * Prepares input panel for parking a car in a specific slot
 *
 * Sets up fields for car reg number, owner name, and staff/visitor checkbox
 * Slot ID is pre-filled and non-editable
 * Calls carpark.parkCar() to park the car
 * Updates slot button text and color if parked successfully
 */

    private void prepareParkCar(String slotID) {
        clearInputPanel();
        inputPanel.setVisible(true);
        inputPanel.setLayout(new GridLayout(4, 2, 5, 5)); // 4 rows, 2 columns with spacing
    
        // Slot ID (pre-filled with the selected slot)
        JLabel slotLabel = new JLabel("Slot ID (E.G A00):");
        slotLabel.setForeground(new Color(253, 137, 45)); // Set text color to orange
        JTextField slotField = new JTextField(slotID, 5);
        slotField.setEditable(false); // Prevent editing of slot ID
    
        // Car Registration Number
        JLabel regLabel = new JLabel("Car RNO (E.G A0231):");
        regLabel.setForeground(new Color(253, 137, 45)); // Set text color to orange
        JTextField regField = new JTextField(5);
    
        // Owner Name
        JLabel ownerLabel = new JLabel("Owner Name:");
        ownerLabel.setForeground(new Color(253, 137, 45)); // Set text color to orange
        JTextField ownerField = new JTextField(5);
    
        // Staff/Visitor Checkbox
        JCheckBox isStaffCheck = new JCheckBox("Is Staff?");
        isStaffCheck.setSelected(slotID.startsWith("S")); // Default based on the slot type
    
        // Submit Button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String carRNO = regField.getText();
            String owner = ownerField.getText();
            boolean isStaff = isStaffCheck.isSelected();
    
            // Call carpark.parkCar method
            String result = carpark.parkCar(slotID, carRNO, owner, isStaff);
            JOptionPane.showMessageDialog(this, result);
    
            // If successfully parked, update slot button text and color to cyan
            if (result.contains("successfully parked")) {
                JButton slotButton = getSlotButtonById(slotID);
                if (slotButton != null) {
                    slotButton.setText(slotID + " - Occupied");
                    slotButton.setBackground(Color.CYAN); // Set background color to cyan when occupied
                }
            }
    
            clearInputPanel();
        });
    
        // Adding components to the input panel
        inputPanel.add(slotLabel);
        inputPanel.add(slotField);
        inputPanel.add(regLabel);
        inputPanel.add(regField);
        inputPanel.add(ownerLabel);
        inputPanel.add(ownerField);
        inputPanel.add(isStaffCheck);
        inputPanel.add(submitButton);
    
        revalidate();
        repaint();
    }

/**
 * Prepares input panel for parking a car
 *
 * this is the second prepareParkCar() method, this one is without an input, I needed two methods one with input and one without
 * Sets up fields for slot ID, car reg number, owner name, and staff/visitor checkbox
 * User enters all details, then calls carpark.parkCar() to park the car
 * Updates slot button text and color to cyan if parked successfully
 */

    private void prepareParkCar() {
        // This method gets called when the user selects "Park a car" from the menu
        clearInputPanel();
        inputPanel.setVisible(true);
        inputPanel.setLayout(new GridLayout(4, 2, 5, 5)); // 4 rows, 2 columns with spacing
    
        // Slot ID
        JLabel slotLabel = new JLabel("Slot ID (E.G A00):");
        slotLabel.setForeground(new Color(253, 137, 45)); // Set text color to orange
        JTextField slotField = new JTextField(5);
    
        // Car Registration Number
        JLabel regLabel = new JLabel("Car RNO (E.G A0231):");
        regLabel.setForeground(new Color(253, 137, 45)); // Set text color to orange
        JTextField regField = new JTextField(5);
    
        // Owner Name
        JLabel ownerLabel = new JLabel("Owner Name:");
        ownerLabel.setForeground(new Color(253, 137, 45)); // Set text color to orange
        JTextField ownerField = new JTextField(5);
    
        // Staff/Visitor Checkbox
        JCheckBox isStaffCheck = new JCheckBox("Is Staff?");
    
        // Submit Button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String slotID = slotField.getText();
            String carRNO = regField.getText();
            String owner = ownerField.getText();
            boolean isStaff = isStaffCheck.isSelected();
    
            // Call carpark.parkCar method
            String result = carpark.parkCar(slotID, carRNO, owner, isStaff);
    
            // Show the result to the user
            JOptionPane.showMessageDialog(this, result);
    
            // If the car is successfully parked, update the slot button text to indicate it's occupied
            if (result.contains("successfully parked")) {
                JButton slotButton = getSlotButtonById(slotID);
                if (slotButton != null) {
                    slotButton.setText(slotID + " - Occupied");
                    slotButton.setBackground(Color.CYAN); // Set background color to cyan when occupied
                }
            }
    
            clearInputPanel(); // Clear the input fields after submission
        });
    
        // Adding components to the input panel
        inputPanel.add(slotLabel);
        inputPanel.add(slotField);
        inputPanel.add(regLabel);
        inputPanel.add(regField);
        inputPanel.add(ownerLabel);
        inputPanel.add(ownerField);
        inputPanel.add(isStaffCheck);
        inputPanel.add(submitButton);
    
        revalidate();
        repaint();
    }

/**
 * Prepares to delete all vacant slots when selected from the menu.
 *
 * Calls the deleteVacants() method in carpark to remove all vacant slots from the backend.
 * Updates the GUI to reflect the changes by removing vacant slot buttons.
 */
private void prepareDeleteAllVacantSlots() {
    int confirmed = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete all vacant slots?",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
    );

    if (confirmed == JOptionPane.YES_OPTION) {
        String result = carpark.deleteVacants();
        JOptionPane.showMessageDialog(this, result);
    
        // Remove vacant slots from GUI
        List<JButton> staffSlotButtonsCopy = new ArrayList<>(staffSlotButtons);
        for (JButton button : staffSlotButtonsCopy) {
            if (button.getText().contains("Vacant")) {
                staffSlotsPanel.remove(button);
                staffSlotButtons.remove(button);
            }
        }
    
        List<JButton> visitorSlotButtonsCopy = new ArrayList<>(visitorSlotButtons);
        for (JButton button : visitorSlotButtonsCopy) {
            if (button.getText().contains("Vacant")) {
                visitorSlotsPanel.remove(button);
                visitorSlotButtons.remove(button);
            }
        }
    
        // Revalidate and repaint to update the GUI
        revalidate();
        repaint();
    }
}

/**
 * Sets up the menu panel with buttons for various actions
 *
 * Adds buttons for listing slots, parking a car, finding a car, removing a car, adding/deleting a slot, and exiting the app
 * Each button triggers its corresponding method when clicked
 */

    private void setupMenuPanel(JPanel menuPanel) {
        addMenuButton(menuPanel, "List all slots", e -> listAllSlots());
        addMenuButton(menuPanel, "Delete slot", e -> prepareDeleteSlot());
        addMenuButton(menuPanel, "Park a car", e -> prepareParkCar());
        addMenuButton(menuPanel, "Find a car", e -> prepareFindCar());
        addMenuButton(menuPanel, "Remove a car", e -> prepareRemoveCar());
        addMenuButton(menuPanel, "Add a parking slot", e -> prepareAddSlot());
        addMenuButton(menuPanel, "Delete all vacant slots", e -> prepareDeleteAllVacantSlots());
        addMenuButton(menuPanel, "Exit", e -> System.exit(0));
    }

/**
 * Adds a button to the menu panel
 *
 * Creates a button with given text and action listener
 * Sets button color to orange and adds it to the menu panel
 */

    private void addMenuButton(JPanel menuPanel, String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setBackground(new Color(253, 137, 45)); // Orange color
        button.setForeground(Color.BLACK);
        button.addActionListener(actionListener);
        menuPanel.add(button);
    }

/**
 * Clears the input panel
 *
 * Removes all components and hides the input panel
 */

    private void clearInputPanel() {
        inputPanel.removeAll();
        inputPanel.setVisible(false);
    }

/**
 * Gets the parking slot by its ID
 *
 * Searches either staff or visitor slots based on the isStaff flag
 * Returns the matching Parkingslot object if found, otherwise null
 */

    private Parkingslot getSlotById(String slotID, boolean isStaff) {
        List<Parkingslot> slotList = isStaff ? staffSlots : visitorSlots;
        for (Parkingslot slot : slotList) {
            if (slot.getID().equals(slotID)) {
                return slot;
            }
        }
        return null;
    }

/**
 * Handles click events on parking slot buttons
 *
 * If slot is occupied, prompts user to either show details or unpark
 * If slot is vacant, prompts user to park a car
 * Updates UI based on actions taken (unparking sets color back to original based on staff/visitor)
 */

private void handleSlotClick(Parkingslot slot, JButton slotButton) {
    if (slot == null) {
        JOptionPane.showMessageDialog(this, "Invalid Slot!");
        return;
    }

    if (slot.getParked()) {
        // Custom button options for an occupied slot
        Object[] options = {"Show Details", "Unpark", "Cancel"};
        int option = JOptionPane.showOptionDialog(
                this,
                "Slot " + slot.getID() + " is currently occupied. Do you want to view car details or unpark?",
                "Slot Occupied",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (option == JOptionPane.YES_OPTION) {
            String carDetails = slot.getCar().showCar();
            JOptionPane.showMessageDialog(this, carDetails);
        } else if (option == JOptionPane.NO_OPTION) {
            // Unpark the car
            String result = carpark.deleteCar(slot.getCar().getNum());
            JOptionPane.showMessageDialog(this, result);
            slotButton.setText(slot.getID() + " - Vacant");

            // Reset the button background color based on whether it's a staff or visitor slot
            if (slot.getID().startsWith("S")) {
                slotButton.setBackground(new Color(253, 137, 45)); // Light orange for staff slots
            } else {
                slotButton.setBackground(new Color(166, 28, 28)); // Dark reddish for visitor slots
            }
        }
    } else {
        // If the slot is vacant, give the option to park a car
        int option = JOptionPane.showConfirmDialog(
                this,
                "Slot " + slot.getID() + " is vacant. Do you want to park a car here?",
                "Slot Vacant",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (option == JOptionPane.YES_OPTION) {
            prepareParkCar(slot.getID());
        }
    }
}

/**
 * Gets the JButton associated with a given slot ID
 *
 * Searches through both staff and visitor slot buttons
 * Returns the button matching the given slot ID, or null if not found
 */

private JButton getSlotButtonById(String slotID) {
    // Search in staff slot buttons
    for (JButton button : staffSlotButtons) {
        if (button.getText().startsWith(slotID)) {
            return button;
        }
    }

    // Search in visitor slot buttons
    for (JButton button : visitorSlotButtons) {
        if (button.getText().startsWith(slotID)) {
            return button;
        }
    }

    return null;  // Return null if no matching button is found
}


}
