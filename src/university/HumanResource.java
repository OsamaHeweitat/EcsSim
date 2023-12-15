package university;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

/**
 * The human resource department of the university, containing all the staff. It is responsible for
 * paying staff salaries, having staff instruct students, and updating the staff roster to handle
 * staff retiring or leaving.
 *
 * @see Staff
 */
public class HumanResource {

  private HashMap<Staff, Float> staffSalary;

  // the minimum stamina a staff member should have (taken into account when calculating how many
  // students a staff member should instruct)
  private static final int MINIMUM_STAMINA = 40;
  // the modifier for the costs of staff, used when calculating when to hire staff
  private static final float COSTS_MODIFIER_STAFF = 1.1f;

  /**
   * Constructs a new human resource department with no staff.
   */
  public HumanResource() {
    staffSalary = new HashMap<Staff, Float>();
  }

  /**
   * Adds a staff member to this human resource department, a salary is calculated using the formula
   * salary = skill * 9.5-10.5%.
   *
   * @param staff the staff member
   */
  public void addStaff(Staff staff) {
    staffSalary.put(staff, (float) (staff.getSkill() * ((Math.random()) + 9.5) / 100));
  }

  /**
   * Returns an iterator over the staff in this human resource department.
   *
   * @return an iterator over the staff in this human resource department
   */
  public Iterator<Staff> getStaff() {
    return staffSalary.keySet().iterator();
  }

  /**
   * Returns the total salaries of all staff members in this human resource department.
   *
   * @return the total salaries of all staff members in this human resource department
   */
  public float getSalary() {
    float totalSalary = 0;
    for (Staff staff : staffSalary.keySet()) {
      totalSalary += staffSalary.get(staff);
    }
    return totalSalary;
  }

  /**
   * Hires staff members from the available staff. Returns the available staff after hiring staff.
   * Staff members are hired until the number of students instructed by the staff members is at
   * least 90% of the number of students in the university. Staff members are hired in order of
   * skill, starting with the highest skill. Staff members are only hired if the budget minus the
   * staff member's maximum starting salary is greater than the total costs of the university
   * multiplied by the staff costs modifier (to ensure that the budget won't become negative at the
   * end of the year by paying costs).
   *
   * @param availableStaff   the available staff in the staff market
   * @param budget           the budget of the university
   * @param allCosts         the total costs of the university
   * @param numberOfStudents the number of students in the university
   * @return the available staff in the staff market after hiring staff
   * @see #calculateHypotheticalInstructedStudents()
   */
  public ArrayList<Staff> hireStaff(ArrayList<Staff> availableStaff, float budget, float allCosts,
      int numberOfStudents) {
    // calculates the number of students that would be instructed if all current staff instructed
    // as many students as they can until reaching the minimum stamina
    int hypotheticalInstructedStudents = calculateHypotheticalInstructedStudents();

    // while the maximum number of students that can be instructed by the staff members is less
    // than 90% of the number of students in the university, hire staff. labelled loop, so it can
    // be broken out of inside the for loop later on
    mainLoop:
    while (hypotheticalInstructedStudents < 0.90
        * numberOfStudents) {
      // sorts the available staff by skill, starting with the highest skill
      Collections.sort(availableStaff);
      int i = 0;
      // iterates through the availableStaff in order of skill, checks if the budget minus the
      // staff member's maximum starting salary is greater than the total costs of the university
      // multiplied by the staff costs modifier, if so, hire the staff
      for (Staff staff : availableStaff) {
        if ((budget - staff.getStartingSalaryMax()) > allCosts * COSTS_MODIFIER_STAFF) {
          addStaff(staff);
          availableStaff.remove(staff);
          System.out.println("Hired " + staff.getName() + ".");
          // we break out of the for loop in order to recalculate the
          // hypotheticalInstructedStudents, as we have hired a new staff member, if it's still
          // below 90% of the number of students in the university, we continue hiring staff,
          // otherwise we break out of the mainLoop
          break;
        }
        // if we've reached the end of the availableStaff, break out of the mainLoop, as this means
        // we've gone through all the availableStaff and we did not hire anyone due to not having
        // enough budget for even the cheapest staff
        if (i++ == availableStaff.size() - 1) {
          break mainLoop;
        }
      }
      hypotheticalInstructedStudents = calculateHypotheticalInstructedStudents();
    }
    return availableStaff;
  }

  /**
   * Calculates the number of students that would be instructed if all current staff instructed as
   * many students as they can until reaching the minimum stamina. Calculates the number of students
   * each staff member can instruct maximally using the formula: (stamina - minimum stamina) / 20 *
   * (20 + skill).
   *
   * @return the number of students that would be instructed if all current staff instructed as many
   *          students as they can until reaching the minimum stamina
   */
  public int calculateHypotheticalInstructedStudents() {
    Iterator<Staff> staffIterator = getStaff();
    int hypotheticalInstructedStudents = 0;
    while (staffIterator.hasNext()) {
      Staff currentStaff = staffIterator.next();
      hypotheticalInstructedStudents +=
          (int) Math.ceil((currentStaff.getStamina() - MINIMUM_STAMINA) / 20) * (20
              + currentStaff.getSkill());
    }
    return hypotheticalInstructedStudents;
  }

  /**
   * Instructs the given number of students. Returns the number of students that were not
   * instructed. Calculates the number of students each staff member can instruct maximally using
   * the formula: (stamina - minimum stamina) / 20 * (20 + skill).
   *
   * @param numberOfStudents the number of students that have to be instructed
   * @return the number of students that were not instructed
   */
  public int instructStudents(int numberOfStudents) {
    Iterator<Staff> staffIterator = getStaff();
    int uninstructedStudents = numberOfStudents;

    // iterates through all staff members
    while (staffIterator.hasNext()) {
      Staff currentStaff = staffIterator.next();

      // checks if there are more students to instruct than a staff member can instruct until
      // reaching their minimum stamina, if so, instructs the maximum number of students possible
      // and decreases the number of uninstructed students by that amount
      if (uninstructedStudents
          - ((int) Math.ceil((currentStaff.getStamina() - MINIMUM_STAMINA) / 20) * (20
          + currentStaff.getSkill())) > 0) {
        int studentsToInstruct =
            (int) Math.ceil((currentStaff.getStamina() - MINIMUM_STAMINA) / 20) * (20
                + currentStaff.getSkill());
        uninstructedStudents -= studentsToInstruct;
        currentStaff.instruct(studentsToInstruct);
        System.out.println(
            currentStaff.getName() + " instructed " + studentsToInstruct + " students.");
        // if there are less students to instruct than a staff member can instruct until reaching
        // their minimum stamina, instructs the remaining students and decreases the number of
        // uninstructed students by that amount
      } else {
        System.out.println(
            currentStaff.getName() + " instructed " + uninstructedStudents + " students.");
        uninstructedStudents -= uninstructedStudents;
        currentStaff.instruct(uninstructedStudents);
      }
    }
    return uninstructedStudents;
  }

  /**
   * Updates the staff roster, removing staff members that have retired by reaching 30 years
   * experience or left due to exhaustion.
   */
  public void updateStaffRoster() {
    Iterator<Staff> staff = getStaff();
    while (staff.hasNext()) {
      Staff currentStaff = staff.next();
      if (currentStaff.getYearsOfTeaching() > 30) {
        staff.remove();
        System.out.println(currentStaff.getName() + " retired. Thank you for your service.");
        // the chance of a staff member leaving is (100 - stamina), so generates a random number
        // between 0.0 and 1.0 using Math.random() and multiplies it by 100 to get a percentage,
        // if the percentage is greater than the staff member's stamina, they leave (as then it
        // lies within 100 - stamina)
      } else if ((int) (Math.round(Math.random()) * 100) > currentStaff.getStamina()) {
        staff.remove();
        System.out.println(currentStaff.getName() + " left..");
      }
    }
  }
}
