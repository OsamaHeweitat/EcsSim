package facilities.recreational;

import facilities.Facility;

/**
 * An abstract recreational facility that produces a profit, affecting the yearly amount of coins
 * gained, and is capable of being built and upgraded. It implements the Recreational interface.
 *
 * @see Cafeteria
 * @see Gym
 */
public class AbstractRecreational extends Facility implements Recreational {

  private int baseBuildCost;
  private int upgradeCost;
  private int profit;
  private int level;
  private int maximumLevel;

  /**
   * Constructs a recreational facility with the given base build cost, upgrade cost, and profit.
   *
   * @param name          the name of the recreational facility
   * @param baseBuildCost the base build cost of the recreational facility
   * @param profit        the profit of the recreational facility
   * @param maximumLevel  the maximum level of the recreational facility
   */
  public AbstractRecreational(String name, int baseBuildCost, int profit, int maximumLevel) {
    super(name);
    this.baseBuildCost = baseBuildCost;
    this.profit = profit;
    this.level = 1;
    this.maximumLevel = maximumLevel;
    this.upgradeCost = calculateUpgradeCost();
  }

  /**
   * Calculates the cost of upgrading the recreational facility to the next level. Uses the formula:
   * lastLevelCost (or baseBuildCost if upgrading to level 2) * (1.5).
   *
   * @return the cost of upgrading the recreational facility
   */
  public int calculateUpgradeCost() {
    if (level == maximumLevel) {
      return -1;
    }
    if (level == 1) {
      return (int) Math.round(baseBuildCost * 1.5);
    } else {
      return (int) Math.round(upgradeCost * 1.5);
    }
  }

  /**
   * Returns the cost of building the recreational facility.
   *
   * @return the cost of building the recreational facility
   */
  public int getBuildCost() {
    return baseBuildCost;
  }

  /**
   * Returns the cost of upgrading the recreational facility.
   *
   * @return the cost of upgrading the recreational facility
   */
  public int getUpgradeCost() {
    return upgradeCost;
  }

  /**
   * Returns the profit of the recreational facility.
   *
   * @return the profit of the recreational facility
   */
  public int getProfit() {
    return profit;
  }

  /**
   * Returns the level of the recreational facility.
   *
   * @return the level of the recreational facility
   */
  public int getLevel() {
    return level;
  }

  /**
   * Increases the level of the recreational facility by 1. Also increases the profit by 2.
   */
  public void increaseLevel() {
    if (level < maximumLevel) {
      level++;
      upgradeCost = calculateUpgradeCost();
      increaseProfit(2);
    }
  }

  /**
   * Increases the profit of the recreational facility by the given amount.
   *
   * @param amount the amount to increase the profit by
   */
  public void increaseProfit(int amount) {
    profit += amount;
  }

  /**
   * Returns whether the recreational facility is upgradable.
   *
   * @return whether the recreational facility is upgradable
   */
  public boolean isUpgradable() {
    return level < maximumLevel;
  }
}
