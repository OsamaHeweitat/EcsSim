package university;

/**
 * A staff member who teaches students. Implements comparable to allow for sorting by skill.
 *
 * @see HumanResource
 */
public class Staff implements Comparable<Staff> {

  private String name;
  private int skill;
  private int yearsOfTeaching;
  private int stamina;

  /**
   * Constructs a new staff member with the given name and skill.
   *
   * @param name  the name of the staff member
   * @param skill the skill of the staff member
   */
  public Staff(String name, int skill) {
    this.name = name;
    this.skill = skill;
    this.yearsOfTeaching = 0;
    this.stamina = 100;
  }

  /**
   * Instructs the given number of students. Returns the reputation gained from the instruction with
   * the formula reputation gained = (100 * skill) / (100 + number of students to instruct).
   *
   * @param numberOfStudents the number of students to instruct
   * @return the reputation gained from the instruction
   */
  public int instruct(int numberOfStudents) {
    int reputationGained = (100 * skill) / (100 + numberOfStudents);
    increaseSkill(1);
    decreaseStamina((int) Math.ceil(numberOfStudents / (20 + skill)) * 20);
    return reputationGained;
  }

  /**
   * Replenishes the stamina of this staff member by 20. If the stamina is greater than 100, sets
   * the stamina to 100.
   */
  public void replenishStamina() {
    if (stamina + 20 <= 100) {
      stamina += 20;
    } else {
      stamina = 100;
    }
  }

  /**
   * Increases the skill of this staff member by the given amount. If the skill is greater than 100,
   * sets the skill to 100.
   *
   * @param amount the amount to increase the skill by
   */
  public void increaseSkill(int amount) {
    if (skill + amount <= 100) {
      skill += amount;
    } else {
      skill = 100;
    }
  }

  /**
   * Decreases the stamina of this staff member by the given amount. If the stamina is less than 0,
   * sets the stamina to 0.
   *
   * @param amount the amount to decrease the stamina by
   */
  public void decreaseStamina(int amount) {
    if (stamina - amount >= 0) {
      stamina -= amount;
    } else {
      stamina = 0;
    }
  }

  /**
   * Increases the years of teaching of this staff member by 1.
   */
  public void increaseYearsOfTeaching() {
    yearsOfTeaching++;
  }

  /**
   * Compares the skill of this staff member to the skill of the given staff member. Returns 1 if
   * this staff member's skill is greater than the given staff member's skill, -1 if this staff
   * member's skill is less than the given staff member's skill, and 0 if this staff member's skill
   * is equal to the given staff member's skill.
   *
   * @return 1 if this staff member's skill is greater than the given staff member's skill, -1 if
   *          this staff member's skill is less than the given staff member's skill, and 0 if this
   *          staff member's skill is equal to the given staff member's skill
   */
  public int compareTo(Staff staff) {
    if (staff.getSkill() > skill) {
      return 1;
    } else if (staff.getSkill() < skill) {
      return -1;
    } else {
      return 0;
    }
  }

  /**
   * Returns the skill of this staff member.
   *
   * @return the skill of this staff member
   */
  public int getSkill() {
    return skill;
  }

  /**
   * Returns the name of this staff member.
   *
   * @return the name of this staff member
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the stamina of this staff member.
   *
   * @return the stamina of this staff member
   */
  public int getStamina() {
    return stamina;
  }

  /**
   * Returns the years of teaching of this staff member.
   *
   * @return the years of teaching of this staff member
   */
  public int getYearsOfTeaching() {
    return yearsOfTeaching;
  }

  /**
   * Returns the maximum possible starting salary of this staff member.
   *
   * @return the maximum possible starting salary of this staff member
   */
  public float getStartingSalaryMax() {
    return (float) (skill * ((10.5) / 100));
  }
}
