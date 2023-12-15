package facilities.buildings;

/**
 * A building where students attend lectures. Has a base capacity of 10 students, a maximum level of
 * 6, and a base building cost of 200.
 */
public class Theatre extends AbstractBuilding {

  /**
   * Constructor for Theatre. Sets baseBuildCost to 200, capacity to 10, and maximumLevel to 6.
   *
   * @param name the name of the theatre
   */
  public Theatre(String name) {
    super(name, 6, 10, 200);
  }
}
