import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* Project 1: Carpark.java class. Defines parking slots array as well as methods for necessary functionality
* @author Mohamed Shariq Usoof <104841889>
* @version java 22
*/

public class Carpark {

    ArrayList<Parkingslot> Slots = new ArrayList<Parkingslot>();                    /* Arraylist which hold Parkingslot objects */
    DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");    /* DateTimeFormatter object, to be used in some methods */


    /** All the methods below are 'void' and do not return a String. I use System.out.println to output. */

    /**
   * Method for creating staff and visitor slots upon program opening
   *
   * User specifies number of staff and number of visitor slots
   * creates slots in correct format for each role
   * Prints out list of slots, and notifies number of slots created for each role
   */

    public String createSlots(int nostaff, int novisitor){
        for (int i = 0; i < nostaff; i = i + 1){                           /* for loop, adds staff parking slots */
            Slots.add(new Parkingslot("S" + String.format("%02d",i),true,false ,null));
        }
        for (int i = 0; i < novisitor; i = i + 1){                         /* for loop, adds visitor parking slots */
            Slots.add(new Parkingslot("V" + String.format("%02d",i),false,false ,null));
        }

        return "You have successfully created " + nostaff + " Staff slots and " + novisitor + " Visitor slots.";
    }

    /**
   * Method for listing all parking slots: slot id, staff/visitor slot, occupied/vacant
   *
   * No user input parameter, runs a for loops and prints using showSlot()
   * showSlot includes showCar if car is parked, see Parkingslot.java and Car.java
   * includes a tally that notifies user how many slots exist
   * if parking slot is occupied, also displays car registration number, owner name, parking duration and fee
   */
  
public String listSlots() {
    StringBuilder slotList = new StringBuilder();  // Use StringBuilder for efficiency
    int x = 0;

    for (int i = 0; i < Slots.size(); i++) {                       /* for loop, lists all slots */
        slotList.append(Slots.get(i).showSlot()).append("\n");      /* Append each slot's details */
        x++;                                                        /* Tallies number of slots */
    }

    slotList.append("\nThere are ").append(x).append(" parking slots listed.");  /* Append total number of slots */

    return slotList.toString();  // Return the full slot list with tally
}

    /**
   * Method for adding parking slot, all info provided by user, does not include car object
   *
   * Requires user input parameter slot id (String), and staffpark (boolean)
   * Displays error if parking slot already exists, or if pattern doesn't match
   */

   public String addSlot(String id, Boolean staffpark) {
    String pattern = "^[A-Z][0-9]{2}$";  // Storing String pattern - holds the format required
    Boolean exists = false;  // Boolean to store whether ID already exists
    int currentStaffSlots = 0;   // Variable to count current staff slots
    int currentVisitorSlots = 0; // Variable to count current visitor slots


        // Count current staff and visitor slots
        for (int i = 0; i < Slots.size(); i++) {
            if (Slots.get(i).getStaffPark()) {
                currentStaffSlots++;
            } else {
                currentVisitorSlots++;
            }
        }
    
        // Check if adding another slot exceeds the maximum limit of 10
        if (staffpark && currentStaffSlots >= 10) {
            return "Error: Maximum staff slots in this GUI is 10.";
        } else if (!staffpark && currentVisitorSlots >= 10) {
            return "Error: Maximum visitor slots in this GUI is 10.";
        }
    
    // Check if the slot ID already exists
    for (int i = 0; i < Slots.size(); i++) {  
        if (id.equals(Slots.get(i).getID())) {  // If statement checks if id already exists
            exists = true;
            break;
        }
    }

    // If slot ID matches the pattern
    if (id.matches(pattern)) {
        if (!exists) {  // If ID doesn't exist, add the slot
            Slots.add(new Parkingslot(id, staffpark, false, null));
            return "Successfully added parking slot with ID: " + id;
        } else {
            return "Error: Parking slot with the same ID already exists.";  // Error: ID already exists
        }
    } else {
        return "Error: Please ensure slot ID follows the correct format.";  // Error: ID format invalid
    }
}

    /**
   * Method for deleting slot, ensure no car is parked
   *
   * Requires user input parameter slot id (String)
   * If slot Id is found and is vacant, deletes slot and prints success msg, stores deleted = true
   * if slot id is not found or slot is occupied, prints error msg
   */

   public String deleteSlot(String id) {
    Boolean deleted = false;

    // Loop through the slots to find the one with the matching ID
    for (int i = 0; i < Slots.size(); i++) {
        if (!Slots.get(i).getParked() && id.equals(Slots.get(i).getID())) {  // Check if the slot is vacant and ID matches
            Slots.remove(i);
            deleted = true;
            return "Parking slot with ID: " + id + " has been deleted.";  // Success message
        }
    }

    // If the slot was not found or is occupied
    if (!deleted) {
        return "Error: Parking slot with ID: " + id + " is currently occupied or doesn't exist.";
    }

    return "";  // This return should never be hit because of the above conditions
}


    /**
   * Method for deleting all parking slots where carparked=false
   *
   * Does not require user parameter
   * Uses for loop to go through all slots, if slot is not occupied, deletes
   * Tallies up the number of deleted slots and prints it to the user
   * modification made to the for loop (i--) to make sure no slot gets skipped
   */

   public String deleteVacants() {
    int deletedno = 0;

    // Loop through all the slots to find and delete vacant slots
    for (int i = 0; i < Slots.size(); i++) {
        if (!Slots.get(i).getParked()) {  // If the slot is not occupied, delete it
            Slots.remove(i);
            deletedno++;
            i--;  // Decrement i to avoid skipping the next slot after deletion
        }
    }

    // Return the message with the number of slots deleted
    return deletedno + " parking slots have been deleted.";
}

    /**
   * Method for parking car
   *
   * Requires many user input parameters: slot id, car regist no, owner name, owner staff status
   * slot Id is for parking slot, rest are for Car object
   * runs checks for whether parking slot ID is occupied, whether the car regist no is already parked
   * whether owners role (staff) matches parking space, if parking slot user wants to park in exists
   * Also runs a check on vehicle registration pattern
   */

   public String parkCar(String id, String rnum, String owner, Boolean staff) {
    Boolean occupied = false;      // Check if the slot is already occupied
    Boolean carparked = false;     // Check if the car is already parked in another slot
    Boolean staffmismatch = false; // Check if staff/visitor parking mismatch occurs
    Boolean exists = false;        // Check if the parking slot exists
    int x = 0;                     // To store the index of the slot to park the car in
    int slot = 0;                  // To store the index of the slot where the car is found parked
    LocalDateTime timenow = LocalDateTime.now();  // Current time when parking the car
    String pattern = "^[A-Z][0-9]{4}$";           // Registration number pattern

    // Loop through slots to check for occupancy, car already parked, or staff mismatch
    for (int i = 0; i < Slots.size(); i++) {
        if (id.equals(Slots.get(i).getID()) && Slots.get(i).getParked()) {
            occupied = true;  // Slot is occupied
        }
        if (Slots.get(i).getCar() != null && rnum.equals(Slots.get(i).getCar().getNum())) {
            carparked = true; // Car is already parked in another slot
            slot = i;
        }
        if (id.equals(Slots.get(i).getID()) && (Slots.get(i).getStaffPark() != staff)) {
            staffmismatch = true; // Staff/visitor mismatch
        }
    }

    // Check if the slot exists
    for (int i = 0; i < Slots.size(); i++) {
        if (id.equals(Slots.get(i).getID())) {
            exists = true;
            x = i;  // Store the index for parking the car
            break;
        }
    }

    // Now handle the error cases and return appropriate messages
    if (!carparked) { // Car is not already parked
        if (exists) {  // The slot exists
            if (!staffmismatch) { // No staff/visitor mismatch
                if (!occupied) {  // The slot is not occupied
                    if (rnum.matches(pattern)) { // Registration number format is valid
                        // Park the car
                        Slots.get(x).setCar(new Car(rnum, owner, staff, timenow)); 
                        Slots.get(x).setParked(true);

                        // Return success message with parking time
                        return "Car with Registration Number " + rnum + " successfully parked in slot ID " + id + 
                               ". Car parked at: " + timenow.format(date);
                    } else {
                        // Registration number format error
                        return "Error: Registration number doesn't match required format.";
                    }
                } else {
                    // Slot is already occupied
                    return "Error: This parking slot is already occupied.";
                }
            } else {
                // Staff/visitor mismatch
                return "Error: This parking slot isn't available to the owner (Visitors and Staff have separate parking).";
            }
        } else {
            // Slot ID not found
            return "Error: This parking slot does not exist.";
        }
    } else {
        // Car is already parked in another slot
        return "Error: This car is already parked in slot: " + Slots.get(slot).getID();
    }
}

    /**
   * Method for finding car by registration number, if found, show slot and owner
   *
   * Requires car registration number to look for
   * Runs a for loop, if registration number found, prints success message and car details as well as parking time and fee
   * if not found, prints error message
   */

   public String findCar(String rnum) {
    boolean found = false;
    StringBuilder result = new StringBuilder(); // To store and return the result message

    // Loop through slots to find the car
    for (int i = 0; i < Slots.size(); i++) {
        if (Slots.get(i).getCar() != null && rnum.equals(Slots.get(i).getCar().getNum())) {
            found = true;
            // Car found, return the slot ID and car details
            result.append("Your car has been found in slot ").append(Slots.get(i).getID()).append("\n")
                  .append("Car details are as follows: ").append(Slots.get(i).getCar().showCar());
            break;
        }
    }

    // If car is not found, return an error message
    if (!found) {
        result.append("Error: The car with registration number ").append(rnum).append(" is not parked here.");
    }

    return result.toString(); // Return the final result message
}

    /**
   * Method for removing car by registration number
   *
   * Requires car registration number to look for
   * Runs a for loop, if registration number found, unparks car (sets car object to null, and sets parked to false)
   * if car registration number is not found, prints error message
   */

   public String deleteCar(String rnum) {
    boolean found = false;
    StringBuilder result = new StringBuilder(); // To store and return the result message
    LocalDateTime parktime;
    LocalDateTime timenow = LocalDateTime.now();
    

    // Loop through slots to find the car
    for (int i = 0; i < Slots.size(); i++) {
        if (Slots.get(i).getCar() != null && rnum.equals(Slots.get(i).getCar().getNum())) {
            found = true;
            // Reset the car object and parked status
            parktime = Slots.get(i).getCar().getParktime();

            Duration length = Duration.between(parktime, timenow);              /*Duration object that calculates difference between time of parking and timenow */
            long hours = length.toHours();
            long minutes = length.toMinutesPart();                              /*Converts duration object to hours minutes and seconds */
            long seconds = length.toSecondsPart();
            String parklength = String.format("%02d:%02d:%02d", hours, minutes, seconds); /*Stores parking time as a string, with neat format */
            int fee;

            if (hours < 1){
                fee = 5;
            } else{
                fee = (int) hours * 5;                                      /* calculates parking fee, stores fee as integer */
            }

            Slots.get(i).setCar(null);
            Slots.get(i).setParked(false);
            // Success message
            result.append("Car with registration number ").append(rnum).append(" has been unparked! Parking Fee: $").append(fee);
            break;
        }
    }

    // If car is not found, return an error message
    if (!found) {
        result.append("Error: The car with registration number ").append(rnum).append(" is not parked here.");
    }

    return result.toString(); // Return the final result message
}


public List<Parkingslot> getStaffSlots() {
    return Slots.stream().filter(Parkingslot::getStaffPark).collect(Collectors.toList());
}

public List<Parkingslot> getVisitorSlots() {
    return Slots.stream().filter(slot -> !slot.getStaffPark()).collect(Collectors.toList());
}

    
}

