import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import university.Staff;
import university.University;

/**
 * The EcsSim class is the main class of the program. It is responsible for handling the simulation
 * and the user interface.
 *
 * @author Osama Heweitat
 * @version 3.0
 */
public class EcsSim {

  private University university;
  private ArrayList<Staff> availableStaff;
  private File staffFile;
  // static, I would've added it to the constructor instead but I wanted to follow the
  // specification
  private static String fileName;

  // green, cyan, red, yellow (all bright) color codes for coloring text in the console
  private static final String[] ANSI_COLOR_CODES = {"\u001B[92m", "\u001B[96m", "\u001B[91m",
      "\u001B[93m"};
  // resets the coloring so the text after it is not colored
  private static final String ANSI_RESET = "\u001B[0m";

  /**
   * Constructs a new EcsSim and creates a new university within it with the given funding. Also
   * creates a new array list of staff and fills it with staff from the given file.
   *
   * @param funding the funding of the university
   * @see #createStaffList
   */
  public EcsSim(int funding) {
    university = new University(funding);
    availableStaff = new ArrayList<Staff>();
    createStaffList();
  }

  /**
   * Creates a new array list of staff and fills it with staff from the staff configuration file.
   */
  public void createStaffList() {
    try {
      staffFile = new File(fileName);
      // using scanner as it's just simpler than using a BufferedReader
      try (Scanner scanner = new Scanner(staffFile)) {
        while (scanner.hasNextLine()) {
          // splits the line into the name and skill of the staff member by splitting the line at
          // the first "("
          String[] staffDetails = scanner.nextLine().split("\\(");
          // adds the staff member to the array list of staff, the skill is gotten by removing the
          // ")" from the end of the skill string and parsing it as an integer
          availableStaff.add(new Staff(staffDetails[0].strip(),
              Integer.parseInt(staffDetails[1].strip().replace(")", ""))));
        }
      }
    } catch (Exception e) {
      System.out.println("Error: " + e);
      e.printStackTrace();
    }
  }

  /**
   * Simulates one year of the university. Builds and upgrades buildings, collects student money,
   * hires staff, instructs students, pays maintenance costs, pays staff salaries, increases staff
   * experience, decreases reputation by uninstructed students, updates the staff roster, and
   * replenishes all staff stamina. FOr commenting/explaining the algorithm please check each method
   * for its explanation of its respective part of the "algorithm". 
   * Keep in mind the algorithm focuses on maximizing reputation and number of students while 
   * minimizing staff costs by minimally hiring (while trying to keep a balance with stamina).
   */
  public void simulate() {
    System.out.print(ANSI_COLOR_CODES[0]); // color the beginning section of the year
    university.buildAndUpgrade(); // 1a

    university.collectStudentMoney(); // 1b
    university.collectRecreationalProfits();

    availableStaff = university.hireStaff(availableStaff); // 1c

    System.out.println(ANSI_RESET
        + ANSI_COLOR_CODES[1]); // reset the coloring and color the during section of the year
    final int uninstructedStudents = university.instructStudents(); // 2

    System.out.println(ANSI_RESET
        + ANSI_COLOR_CODES[2]); // reset the coloring and color the end section of the year
    university.payMaintenanceCosts(); // 3a
    university.payStaffSalaries(); // 3b
    university.increaseStaffExperience(); // 3c
    university.decreaseReputationByUninstructedStudents(uninstructedStudents); // 3d
    university.updateStaffRoster(); // 3e
    university.replenishAllStamina(); // 3f
    System.out.print(ANSI_RESET); // reset the coloring
  }

  /**
   * Simulates the given number of years of the university. Prints the budget, reputation, and
   * number of students at the beginning and end of each year. It asks the user whether to continue
   * the simulation or not at the end of the given number of years, and requires the extra number
   * of years to simulate if yes. Also prints the budget, reputation, and number of students at the
   * end of the simulation.
   *
   * @param numberOfYears the number of years to simulate
   * @throws NumberFormatException if answered with a wrong format to the question of whether to continue simulation
   */
  public void simulate(int numberOfYears) {
    // try with resources to automatically close the scanner
    try (Scanner scanner = new Scanner(System.in)) {
      System.out.println(
          ANSI_COLOR_CODES[3] + "Simulation starting for " + numberOfYears + " years: " + "Budget: "
              + university.getBudget() + " Reputation: " + university.getReputation() + " Students: "
              + university.getNumberOfStudents() + "\n" + ANSI_RESET); // ansi codes for coloring
      for (int i = 0; i < numberOfYears + 1; i++) {
        System.out.println(ANSI_COLOR_CODES[3] + "Year " + (i + 1) + ":" + ANSI_RESET);
        simulate();
        System.out.println(
            ANSI_COLOR_CODES[3] + "End of Year " + (i + 1) + ": " + "Budget: "
                + university.getBudget() + " Reputation: "
                + university.getReputation() + " Students: " + university.getNumberOfStudents()
                + "\n\n" + ANSI_RESET); // ansi codes for coloring
        try {
          Thread.sleep(500); // wait 500 ms between each year
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        // if we've reached the end of the given number of years, ask the user whether to continue
        if(i == numberOfYears - 1){
          System.out.println(numberOfYears + " years reached, do you want to continue the simulation? (number of years to continue simulation for if yes, otherwise \"no\")");
          try {
            String input = scanner.nextLine();
            if(input.equals("no")){
              System.out.print("\n");
              break;
            } else {
              numberOfYears += Integer.parseInt(input);
            }
          } catch (NumberFormatException e) {
            throw new NumberFormatException(
                "Error: Invalid argument when continuing simulation, please ensure that you use the format:"
                    + " <number of years to continue simulation for (Integer)> or \"no\"");
          }
        }
      }
    }
    System.out.println(
        ANSI_COLOR_CODES[3] + "Simulation complete for " + numberOfYears + " years: " + "Budget: "
            + university.getBudget() + " Reputation: " + university.getReputation() + " Students: "
            + university.getNumberOfStudents() + ANSI_RESET); // once again, anci codes for coloring
  }

  /**
   * The main method of the program. Creates a new EcsSim with the given staff file and funding,
   * then simulates the given number of years.
   *
   * @param args the arguments passed to the program
   * @throws ArrayIndexOutOfBoundsException if there are not enough arguments passed to the program
   * @throws NumberFormatException          if the budget and/or years arguments passed to the
   *                                        program are not of the correct type
   */
  public static void main(String[] args) {
    try {
      fileName = args[0];
      EcsSim ecsSim = new EcsSim(Integer.parseInt(args[1]));
      ecsSim.simulate(Integer.parseInt(args[2]));
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new ArrayIndexOutOfBoundsException(
          "Error: Missing argument when starting program, please ensure that you use the format: "
              + "java EcsSim <staff filename (String)> <starting funding (Integer)> <number of "
              + "years to simulate (Integer)>");
    } catch (NumberFormatException e) {
      throw new NumberFormatException(
          "Error: Invalid argument when starting program, please ensure that you use the format:"
              + " java EcsSim <staff filename (String)> <starting funding (Integer)> "
              + "<number of years to simulate (Integer)>");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
