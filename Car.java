import java.time.Duration;
import java.time.LocalDateTime;
/**
* Project 1: Car.java class. Constructs car parameters as well as get/set methods and showCar()
* @author Mohamed Shariq Usoof <104841889>
* @version java 22
*/

public class Car {

    private String rnum;                                         // Registration number
    private String owner;                                        // Owner name
    private Boolean staff;                                       // Boolean: Whether owner is a staff or not. True = staff, false = visitor
    private LocalDateTime parktime;

     /**
   * Constructor for objects of class Car
   *
   * @param rnum registration number
   * @param owner owner full name
   * @param staff checks whether owner is staff or not
   * @param parktime stores the time in which a car is parked
   */


    public Car(String rnum, String owner, Boolean staff, LocalDateTime parktime)              
    {
        this.rnum = rnum;                                                       
        this.owner = owner;
        this.staff = staff;
        this.parktime = parktime;
    }

    /**
   * Method to retrieve Car Registration number
   *
   * @return returns the specified cars Registration number (rnum) as a String
   */

    public String getNum()
    {
        return rnum;
    }

    /**
   * Method to set Car Registration number
   *
   * @param rnum requires registration number as String to insert into Car object, doesn't output anything hence "void"
   */

    public void setNum(String rnum)
    {
        this.rnum = rnum;
    }
    
    /**
   * Method to retrieve Owner name
   *
   * @return returns the specified cars Owner name as String
   */

    public String getOwner()
    {
        return owner;
    }

    /**
   * Method to set Owner name
   *
   * @param owner requires owner's name as String to insert into Car, doesn't output anything hence "void"
   */

    public void setOwner(String owner)
    {
        this.owner = owner;
    }

    /**
   * Method to retrieve whether Owner is staff
   *
   * @return returns the specified cars Owner staff status as Boolean. True if they are staff, false if they are visitor
   */
    
    public Boolean getStaff()
    {
        return staff;
    }

    /**
   * Method to set owner staff status
   *
   * @param staff requires owner's staff status as Boolean (true or false) to insert into Car, doesn't output anything hence "void"
   */

    public void setStaff(Boolean staff)
    {
        this.staff = staff;
    }


    /**
   * Method to retrieve time the Car was parked
   *
   * @return returns the specified cars parking time as LocalDateTime object
   */
    
   public LocalDateTime getParktime()
   {
       return parktime;
   }

   /**
  * Method to set the time the Car is being parked
  *
  * @param parktime requires Car parktime as LocalDateTime object to insert into Car, doesn't output anything hence "void"
  */

   public void setParktime(LocalDateTime parktime)
   {
       this.parktime = parktime;
   }


    /**
   * Method to retrieve Car object details (excluding staff status)
   *
   * @return returns the specified cars Registration number, owners name, parking duration, and parking fee neatly as a String.
   */

    public String showCar(){               /* method to display entire object (excluding staff/visitor role) */

        LocalDateTime timenow = LocalDateTime.now();                        /*Creating localdatetime object to store current time - timenow*/
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

        return "Car Registration: " + rnum + " " + "\t" + "Owner: " + owner + " " + "\t"  + "Parking Duration (H:M:S): " 
        + parklength + " Parking Fee: $" + "\t" + fee;
    }
}
