package facilities.buildings;

/**
 * A building where students do practical work. Has a base capacity of 5 students, a maximum level
 * of 5, and a base building cost of 300.
 */
public class Lab extends AbstractBuilding {

  /**
   * Constructor for Lab. Sets baseBuildCost to 300, capacity to 5, and maximumLevel to 5.
   *
   * @param name the name of the lab
   */
  public Lab(String name) {
    super(name, 5, 5, 300);
  }
}
