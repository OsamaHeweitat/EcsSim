package facilities.recreational;

/**
 * A recreational facility where students can go to eat. Has a base profit of 1 coin, a maximum 
 * level of 2, and a base building cost of 500.
 */
public class Gym extends AbstractRecreational {

  /**
   * Constructor for Gym. Sets baseBuildCost to 650, profit to 4, and maximumLevel to 2.
   *
   * @param name the name of the gym
   */
  public Gym(String name) {
    super(name, 650, 4, 2);
  }
}
