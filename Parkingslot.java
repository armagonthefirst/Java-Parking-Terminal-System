/**
* Project 1: Parkingslot.java class. Constructs parking slot parameters as well as get/set methods and showSlot()
* @author Mohamed Shariq Usoof <104841889>
* @version java 22
*/

public class Parkingslot {

    private String id;                                               // Parking slot identifier
    private Boolean staffpark;                                       // Boolean, whether parking slot is for staff
    private Boolean carparked;                                       // Boolean, is there a car parked in this slot
    private Car cardetails;                                          // If a car is parked, stores details

    /**
   * Constructor for objects of class Parkingslot
   *
   * @param id registration number
   * @param staffpark whether parking is reserved for staff
   * @param carkparked stores whether this slot has a car parked
   * @param cardetails which car is parked
   */


    public Parkingslot(String id, Boolean staffpark, Boolean carparked, Car cardetails)
    {
        this.id = id;
        this.staffpark = staffpark;
        this.carparked = carparked;
        this.cardetails = cardetails;

    }

    /**
   * Method to retrieve slot ID
   *
   * @return returns the specified slot's ID as String
   */

    public String getID()
    {
        return id;
    }

    /**
   * Method to set slot ID
   *
   * @param id requires slot ID as String to insert into slot, doesn't output anything hence "void"
   */

    public void setID(String id)
    {
        this.id = id;
    }

    /**
   * Method to retrieve slot parking role
   *
   * @return returns the specified slot's parking role (true = staff, false = visitor) as Boolean
   */

    public Boolean getStaffPark()
    {
        return staffpark;
    }

    /**
   * Method to set slot parking role
   *
   * @param staffpark requires slot parking role (true = staff, false = visitor) as Boolean to insert into slot, doesn't output anything hence "void"
   */

    public void setStaffPark(Boolean staffpark)
    {
        this.staffpark = staffpark;
    }

    /**
   * Method to retrieve slot parked status
   *
   * @return returns the specified slot's parked status (true = occupied, false = vacant) as boolean
   */

    public Boolean getParked()
    {
        return carparked;
    }

    /**
   * Method to set slot parking role
   *
   * @param carparked requires slot parked status (true = occupied, false = vacant) as Boolean to insert into slot, doesn't output anything hence "void"
   */

    public void setParked(Boolean carparked)
    {
        this.carparked = carparked;
    }

    /**
   * Method to retrieve Car object parked in slot
   *
   * @return returns the specified slot's car object as Car (see Car.java)
   */

    public Car getCar()
    {
        return cardetails;
    }

    /**
   * Method to set Car object in slot
   *
   * @param cardetails requires slot's Car object as Car (see Car.java) to insert into slot, doesn't output anything hence "void"
   */

    public void setCar(Car cardetails)
    {
        this.cardetails = cardetails;
    }

    /**
     * Method to show specified Parkingslot object details
     * 
     * @return returns the specified slots details neatly as String: id, staffpark, carparked.
     * if carparked = true, it calls showCar(); and lists the car details as well
     * if staffpark = true "Staff parking" would be returned, if carparked = true "Occupied"
     */

    public String showSlot(){             

        String status;       
        String sstatus;     /* temporary strings to store parking role and occupied/vacant status */

        if (staffpark == true){
            sstatus = "Staff Parking";          /* Stores string to return depending on whether slot is for staff or not */
        } else{
            sstatus = "Visitor Parking";
        }

        if (carparked == true){
            status = "Occupied";                /* Stores string to return depending on whether slot is vacant or not */
            
            return "Slot ID: " + id + "\t" + " " + sstatus + " " +"\t" + "Status: " +  status + "\t" + cardetails.showCar();
        } else{
            status = "Vacant";
            return "Slot ID: " + id + "\t" + " " + sstatus + " " + "\t" + "Status: " +  status;
        }


    }
    
}