package facilities.recreational;

/**
 * A facility that produces a profit, affecting the yearly amount of coins gained, and is capable of
 * being built and upgraded.
 */
public interface Recreational {

  /**
   * Returns the level of the recreational facility.
   *
   * @return the level of the recreational facility
   */
  int getLevel();

  /**
   * Increases the level of the recreational facility.
   */
  void increaseLevel();

  /**
   * Returns the cost of upgrading the recreational facility.
   *
   * @return the cost of upgrading the recreational facility
   */
  int getUpgradeCost();

  /**
   * Returns the cost of building the recreational facility.
   *
   * @return the cost of building the recreational facility
   */
  int getBuildCost();

  /**
   * Returns the profit of the recreational facility.
   *
   * @return the profit of the recreational facility
   */
  int getProfit();

  /**
   * Returns whether the recreational facility is upgradable.
   *
   * @return whether the recreational facility is upgradable
   */
  boolean isUpgradable();
}