package facilities.recreational;

/**
 * A recreational facility where students can go to eat. Has a base profit of 1 coin, a maximum 
 * level of 2, and a base building cost of 500.
 */
public class Cafeteria extends AbstractRecreational {

  /**
   * Constructor for Cafeteria. Sets baseBuildCost to 500, profit to 1, and maximumLevel to 2.
   *
   * @param name the name of the cafeteria
   */
  public Cafeteria(String name) {
    super(name, 500, 1, 2);
  }
}
