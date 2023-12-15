package facilities.buildings;

/**
 * A building that houses students. Has a base capacity of 6 students, a maximum level of 4, and a
 * base building cost of 100.
 */
public class Hall extends AbstractBuilding {

  /**
   * Constructor for Hall. Sets baseBuildCost to 100, capacity to 6, and maximumLevel to 4.
   *
   * @param name the name of the hall
   */
  public Hall(String name) {
    super(name, 4, 6, 100);
  }
}
