package facilities.buildings;

import facilities.Facility;

/**
 * An abstract building that has a capacity, affecting number of students in the university, and is
 * capable of being built and upgraded. It implements the Building interface.
 *
 * @see Hall
 * @see Lab
 * @see Theatre
 */
public abstract class AbstractBuilding extends Facility implements Building {

  private int level = 1; // default level is 1
  private int maximumLevel;
  private int baseCapacity;
  private int capacity;
  private int baseBuildingCost;
  private int upgradeCost;

  /**
   * Constructs a building with the given name, maximum level, base capacity, and base building
   * cost.
   *
   * @param name             the name of the building
   * @param maximumLevel     the maximum level of the building
   * @param baseCapacity     the base capacity of the building
   * @param baseBuildingCost the base building cost of the building
   */
  public AbstractBuilding(String name, int maximumLevel, int baseCapacity, int baseBuildingCost) {
    super(name);
    this.maximumLevel = maximumLevel;
    this.baseCapacity = baseCapacity;
    this.baseBuildingCost = baseBuildingCost;
    this.upgradeCost = calculateUpgradeCost();
    this.capacity = calculateCapacity();
  }

  /**
   * Calculates the cost of upgrading the building. Uses the formula: baseBuildingCost * (level +
   * 1).
   *
   * @return the cost of upgrading the building
   */
  public int calculateUpgradeCost() {
    if (level == maximumLevel) {
      return -1;
    }
    return baseBuildingCost * (level + 1);
  }

  /**
   * Calculates the capacity of the building. Uses the formula: baseCapacity * 2^(level - 1).
   *
   * @return the capacity of the building
   */
  public int calculateCapacity() {
    return (int) Math.round(baseCapacity * Math.pow(2, level - 1));
  }

  /**
   * Returns the level of the building.
   *
   * @return the level of the building
   */
  public int getLevel() {
    return level;
  }

  /**
   * Increases the level of the building by 1.
   */
  public void increaseLevel() {
    if (level < maximumLevel) {
      level++;
      upgradeCost = calculateUpgradeCost();
      capacity = calculateCapacity();
    }
  }

  /**
   * Returns whether the building is upgradable.
   *
   * @return whether the building is upgradable
   */
  public boolean isUpgradable() {
    return level < maximumLevel;
  }

  /**
   * Returns the cost of upgrading the building.
   *
   * @return the cost of upgrading the building
   */
  public int getUpgradeCost() {
    return upgradeCost;
  }

  /**
   * Returns the capacity of the building.
   *
   * @return the capacity of the building
   */
  public int getCapacity() {
    return capacity;
  }

  /**
   * Returns the cost of building the building.
   *
   * @return the cost of building the building
   */
  public int getBuildCost() {
    return baseBuildingCost;
  }
}
