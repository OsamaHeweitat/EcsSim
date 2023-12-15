package facilities.buildings;

/**
 * A facility that has a capacity, affecting number of students in the university, and is capable of
 * being built and upgraded.
 *
 * @see AbstractBuilding
 */
public interface Building {

  /**
   * Returns the level of the building.
   *
   * @return the level of the building
   */
  int getLevel();

  /**
   * Increases the level of the building.
   */
  void increaseLevel();

  /**
   * Returns the cost of upgrading the building.
   *
   * @return the cost of upgrading the building
   */
  int getUpgradeCost();

  /**
   * Returns the capacity of the building.
   *
   * @return the capacity of the building
   */
  int getCapacity();

  /**
   * Returns the cost of building the building.
   *
   * @return the cost of building the building
   */
  int getBuildCost();

  /**
   * Returns whether the building is upgradable.
   *
   * @return whether the building is upgradable
   */
  boolean isUpgradable();
}
