package university;

import facilities.Facility;
import facilities.buildings.AbstractBuilding;
import facilities.buildings.Building;
import facilities.buildings.Hall;
import facilities.buildings.Lab;
import facilities.buildings.Theatre;
import facilities.recreational.AbstractRecreational;
import facilities.recreational.Cafeteria;
import facilities.recreational.Gym;
import facilities.recreational.Recreational;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * The University class, which contains the estate and human resource of the university. It's
 * responsible for managing the university, including building and upgrading buildings, hiring
 * staff, instructing students, and paying maintenance costs and staff salaries.
 *
 * @see Estate
 * @see HumanResource
 */
public class University {

  private float budget;
  private Estate estate;
  private int reputation = 0;
  private HumanResource humanResource;

  // constants used for calculating building scores
  private static final float REPUTATION_MODIFIER = 0.325f;
  private static final float CAPACITY_MODIFIER = 15f;
  private static final float PRICE_MODIFIER = 0.1875f;
  private static final float RANKING_MODIFIER = 3.25f;
  private static final float COSTS_MODIFIER_BUILDINGS = 2.0f;
  private static final float COSTS_MODIFIER_RECREATIONAL = 1.15f;

  // used to give buildings unique names
  private int counter = 1;
  private static final String[] POSSIBLE_HALL_NAMES = {"Glen Eyre", "Mayflower", "Highfield",
      "Wessex Lane"};
  private static final String[] POSSIBLE_THEATRE_NAMES = {"Nuffield", "Turner Sims",
      "John Hansard"};
  private static final String[] POSSIBLE_LAB_NAMES = {"Zepler", "Mountbatten", "Eustice"};

  /**
   * Constructs a new university with the given funding. Constructs a new estate and human resource
   * for the university.
   *
   * @param funding the funding of the university
   */
  public University(int funding) {
    this.budget = funding;
    estate = new Estate();
    humanResource = new HumanResource();
  }

  /**
   * Builds a new facility with the given type and name. Returns the facility if successful,
   * otherwise returns null.
   *
   * @param type the type of the facility
   * @param name the name of the facility
   * @return the facility if successful, otherwise returns null
   * @see Estate#addFacility(String, String)
   */
  public Facility build(String type, String name) {
    Facility facility = estate.addFacility(type, name);
    // ensures building the facility succeeded as addState returns the facility if successful,
    // otherwise returns null
    if (facility != null) {
      // reduce its cost from the budget, type cast it to a building to get its build cost. Then
      // increase the reputation
      if (facility instanceof Building) {
        budget -= ((facilities.buildings.Building) facility).getBuildCost();
      } else if (facility instanceof Recreational) {
        budget -= ((facilities.recreational.Recreational) facility).getBuildCost();
      }
      reputation += 100;
      System.out.println("Built " + facility.getClass().getSimpleName() + " "
          + facility.getName() + ".");
      return facility;
    }
    return null;
  }

  /**
   * Upgrades the given building. Throws an exception if the building is not found in the university
   * or if the building is already at maximum level.
   *
   * @param building the building to upgrade
   * @throws Exception if the building is not found in the university or if the building is already
   *                   at maximum level
   */
  public void upgrade(Facility building) throws Exception {
    Facility[] facilities = estate.getFacilities();
    // iterates through every facility in the estate, matching its name to the building
    // to-be-upgraded. type casts required as the estate returns facilities, not buildings, and
    // buildings need to be type cast as a facility to get its name
    for (Facility facility : facilities) {
      if (facility.getName().equals(((Facility) building).getName())) {
        if (facility instanceof Building) {
          if (((AbstractBuilding) building).isUpgradable()) {
            budget -= ((Building) building).getUpgradeCost();
            ((Building) building).increaseLevel();
            reputation += 50;
            System.out.println("Upgraded " + building.getClass().getSimpleName() + " "
                + ((AbstractBuilding) building).getName() + ".");
            return;
          }
        } else if (facility instanceof Recreational) {
          if (((Recreational) building).isUpgradable()) {
            budget -= ((Recreational) building).getUpgradeCost();
            ((Recreational) building).increaseLevel();
            reputation += 50;
            System.out.println("Upgraded " + building.getClass().getSimpleName() + " "
                + ((AbstractRecreational) building).getName() + ".");
            return;
          }
        }
        throw new Exception("Building is already at maximum level.");
      }
    }
    throw new Exception("Building not found in university.");
  }

  /**
   * Returns the budget of the university.
   *
   * @return the budget of the university
   */
  public float getBudget() {
    return budget;
  }

  /**
   * Returns the reputation of the university.
   *
   * @return the reputation of the university
   */
  public int getReputation() {
    return reputation;
  }

  /**
   * Returns the number of students in the university. Uses the getNumberOfStudents method in the
   * estate. Please see it for explanation.
   *
   * @return the number of students in the university
   * @see Estate#getNumberOfStudents()
   */
  public int getNumberOfStudents() {
    return estate.getNumberOfStudents();
  }

  /**
   * Collects money from students. Adds 10 coins per student to the budget. Prints the amount
   * collected and the number of students.
   */
  public void collectStudentMoney() {
    increaseBudget(getNumberOfStudents() * 10);
    System.out.println(
        "Collected " + getNumberOfStudents() * 10 + " coins from " + getNumberOfStudents()
            + " students.");
  }

  /**
   * Collects profits from recreational facilities. Uses the collectProfits method in the estate.
   * Please see it for explanation.
   *
   * @see Estate#collectProfits()
   */
  public void collectRecreationalProfits() {
    increaseBudget(estate.collectProfits());
  }

  /**
   * Increases the budget by the given amount.
   *
   * @param amount the amount to increase the budget by
   */
  public void increaseBudget(float amount) {
    budget += amount;
  }

  /**
   * Hires staff from the given list of available staff. Returns the list of available staff after
   * hiring. Uses the hireStaff method in the human resource. Please see it for explanation.
   *
   * @param availableStaff the available staff in the staff market
   * @return the available staff in the staff market after hiring staff
   * @see HumanResource#hireStaff(ArrayList, float, float, int)
   */
  public ArrayList<Staff> hireStaff(ArrayList<Staff> availableStaff) {
    return humanResource.hireStaff(availableStaff, budget, getAllCosts(), getNumberOfStudents());
  }

  /**
   * Instructs students. Returns the number of uninstructed students. Uses the instructStudents
   * method in the human resource. Please see it for explanation.
   *
   * @return the number of uninstructed students
   * @see HumanResource#instructStudents(int)
   */
  public int instructStudents() {
    return humanResource.instructStudents(getNumberOfStudents());
  }

  /**
   * Pays maintenance costs. Reduces the budget by the maintenance costs of the estate. Prints the
   * amount paid.
   */
  public void payMaintenanceCosts() {
    float total = estate.getMaintenanceCost();
    budget -= total;
    System.out.println("Paid " + total + " coins in maintenance costs.");
  }

  /**
   * Pays staff salaries. Reduces the budget by the staff salaries. Prints the amount paid.
   */
  public void payStaffSalaries() {
    budget -= humanResource.getSalary();
    System.out.println("Paid " + humanResource.getSalary() + " coins in staff salaries.");
  }

  /**
   * Returns the total costs of the university. Uses the getMaintenanceCost and getSalary methods in
   * the estate and human resource respectively.
   *
   * @return the total costs of the university
   * @see Estate#getMaintenanceCost()
   * @see HumanResource#getSalary()
   */
  public float getAllCosts() {
    return estate.getMaintenanceCost() + humanResource.getSalary();
  }

  /**
   * Increases the years of teaching of all staff members by 1. Uses the getStaff method in the
   * human resource to iterate over all staff.
   */
  public void increaseStaffExperience() {
    Iterator<Staff> staff = humanResource.getStaff();
    while (staff.hasNext()) {
      staff.next().increaseYearsOfTeaching();
    }
  }

  /**
   * Replenishes the stamina of all staff members by 20. Uses the getStaff method in the human
   * resource to iterate over all staff.
   */
  public void replenishAllStamina() {
    Iterator<Staff> staff = humanResource.getStaff();
    while (staff.hasNext()) {
      staff.next().replenishStamina();
    }
  }

  /**
   * Decreases the reputation by the number of uninstructed students given.
   *
   * @param uninstructedStudents the number of uninstructed students
   */
  public void decreaseReputationByUninstructedStudents(int uninstructedStudents) {
    int initialReputation = reputation; // initial reputation before decreasing
    decreaseReputation(uninstructedStudents);
    // checks if reputation after decreasing is smaller than initial, meaning there were indeed
    // uninstructed students
    if (reputation < initialReputation) {
      System.out.println(
          "Lost " + uninstructedStudents + " reputation due to uninstructed students.");
    }
  }

  /**
   * Decreases the reputation by the given amount. If the reputation is less than 0, sets the
   * reputation to 0.
   *
   * @param amount the amount to decrease the reputation by
   */
  public void decreaseReputation(int amount) {
    if (reputation - amount >= 0) {
      reputation -= amount;
    } else {
      reputation = 0;
    }
  }

  /**
   * Updates the staff roster, removing staff members that have retired by reaching 30 years
   * experience or left due to exhaustion. Uses the updateStaffRoster method in the human resource,
   * so please look there for the code.
   *
   * @see HumanResource#updateStaffRoster()
   */
  public void updateStaffRoster() {
    humanResource.updateStaffRoster();
  }

  /**
   * Builds and upgrades buildings. If there are no students (meaning it's the very beginning of the
   * simulation), builds a hall, lab, and theatre. Otherwise, it will check if all recreational
   * facility types are built, if not it will attempt to build them as long as budget - buildCost >
   * allCosts * COSTS_MODIFIER_RECREATIONAL. It will then check if there are any upgradable
   * recreational facilities left and attempt to upgrade them with the same criteria (albeit with
   * buildCost replaced with upgradeCost). Priority is given to recreational facilities as the
   * earlier they are built and ugpraded, the better. It will then calculate a score for upgrading
   * each building and for building a new building of each type, and then sort them by score. Please
   * see the calculateScore method for explanation of score calculation. Then, iterates through the
   * sorted buildings, upgrading them if they are upgradable and the budget after upgrading is
   * greater than the budget limit, or building them if they are new buildings and the budget is
   * greater than the budget limit - 300. The budget limit is calculated by multiplying the total
   * costs of the university (to ensure that the budget won't become negative at the end of the year
   * by paying costs) by the COSTS_MODIFIER_BUILDINGS constant and adding 450.
   *
   * @see #calculateScore(Building, String, String[])
   * @see #sortMapByValue(LinkedHashMap)
   */
  public void buildAndUpgrade() {
    // if there are no students (very beginning of the simulation), build a hall, lab, and theatre
    if (getNumberOfStudents() == 0) {
      build("Hall", getRandomName("Hall") + " (B" + counter++ + ")");
      build("Lab", getRandomName("Lab") + " (B" + counter++ + ")");
      build("Theatre", getRandomName("Theatre") + " (B" + counter++ + ")");
      return;
    }

    // build unbuilt recreational facilities if possible (if budget - buildCost >= total costs + 
    // build cost of the recreational facility * COSTS_MODIFIER_RECREATIONAL)
    String[] unbuiltRecreationalTypes = estate.getUnbuiltRecreationalTypes();
    if (unbuiltRecreationalTypes.length != 0) {
      for (String type : unbuiltRecreationalTypes) {
        if ((type.equals("Cafeteria") && budget
            >= getAllCosts() + new Cafeteria("").getBuildCost() * COSTS_MODIFIER_RECREATIONAL) || (
            type.equals("Gym") && budget
                >= getAllCosts() + new Gym("").getBuildCost() * COSTS_MODIFIER_RECREATIONAL)) {
          build(type, getRandomName(type));
        }
      }
    }

    Recreational[] upgradeableRecreational = estate.getUpgradableRecreational();
    for (Recreational recreational : upgradeableRecreational) {
      if (budget - recreational.getUpgradeCost() >= getAllCosts() * COSTS_MODIFIER_RECREATIONAL) {
        try {
          upgrade((Facility) recreational);
        } catch (Exception e) {
          System.out.println("Error: " + e);
          e.printStackTrace();
        }
      }

    }

    ArrayList<Building> upgradeableBuildings = new ArrayList<Building>(
        Arrays.asList(estate.getUpgradableBuildings()));
    // using a linked hash map to preserve the order of the buildings as regular hash maps don't
    // preserve order, and the order is important for the algorithm
    LinkedHashMap<Building, Float> buildingScores = new LinkedHashMap<Building, Float>();
    String[] bottleneckRankings = estate.getBottleneckRankings();
    // calculate the score for upgrading each already built building and add them to the hash map
    for (Building building : upgradeableBuildings) {
      float score = calculateScore(building, "upgrade", bottleneckRankings);
      buildingScores.put(building, score);
    }
    // calculate the score for building a new building of each type and add them to the hash map
    buildingScores.put(new Hall(getRandomName("Hall")),
        calculateScore(new Hall(""), "build", bottleneckRankings));
    buildingScores.put(new Lab(getRandomName("Lab")),
        calculateScore(new Lab(""), "build", bottleneckRankings));
    buildingScores.put(new Theatre(getRandomName("Theatre")),
        calculateScore(new Theatre(""), "build", bottleneckRankings));
    // sort the hash map by value, so the buildings with the highest scores are first
    buildingScores = sortMapByValue(buildingScores);

    // calculate the budget limit and if we are already within or below it, return
    float budgetLimit = getAllCosts() * COSTS_MODIFIER_BUILDINGS + 450;
    if (budget <= budgetLimit) {
      return;
    }

    // iterate through the sorted buildings, upgrading them if they are already built upgradable
    // buildings and the budget after upgrading is greater than the budget limit, or building them
    // if they are new buildings and the budget is greater than the budget limit - 300
    for (Building building : buildingScores.keySet()) {
      if (upgradeableBuildings.contains(building)
          && budget - building.getUpgradeCost() >= budgetLimit) {
        try {
          upgrade((Facility) building);
        } catch (Exception e) {
          System.out.println("Error: " + e);
          e.printStackTrace();
        }
      } else if (!upgradeableBuildings.contains(building)
          && budget - building.getBuildCost() >= budgetLimit - 300) {
        // type casting necessary to get the building name
        build(building.getClass().getSimpleName(),
            ((Facility) building).getName() + " (B" + counter++ + ")");
      }
    }
  }

  /**
   * Returns a random name for the given type of building.
   *
   * @param type the type of building
   * @return a random name for the given type of building
   */
  public String getRandomName(String type) {
    String[] possibleNames = null;
    switch (type) {
      case "Hall":
        possibleNames = POSSIBLE_HALL_NAMES;
        break;
      case "Theatre":
        possibleNames = POSSIBLE_THEATRE_NAMES;
        break;
      case "Lab":
        possibleNames = POSSIBLE_LAB_NAMES;
        break;
      case "Cafeteria":
        return "Exquisiette";
      case "Gym":
        return "Jubilee";
      default:
        return "";
    }
    return possibleNames[(int) Math.round(Math.random() * (possibleNames.length - 1))];
  }

  /**
   * Calculates the score for upgrading or building the given building. The score is calculated
   * using the formula (reputationGained * REPUTATION_MODIFIER + capacity * CAPACITY_MODIFIER) *
   * (bottleNeckRanking)^RANKING_MODIFIER / (upgradeCost * PRICE_MODIFIER), where the
   * bottleNeckRanking is their index in the bottle neck ranking array + 1. The reputation is 50 if
   * upgrading and 100 if building. The capacity is the capacity of the building. The rank is the
   * index of the building's class name in the bottleneck rankings array, plus 1. The upgrade cost
   * is the upgrade cost of the building. The price modifier is 0.1875. The ranking modifier is
   * 3.25. The reputation modifier is 0.325. The capacity modifier is 15.
   *
   * @param building           the building to calculate the score for
   * @param reputationType     the type of reputation, either "upgrade" or "build"
   * @param bottleneckRankings the bottleneck rankings of the estate
   * @return the score for upgrading or building the given building
   */
  public float calculateScore(Building building, String reputationType,
      String[] bottleneckRankings) {
    float score = 0;
    float reputationGained = 0;

    // set the reputation gained depending on whether the building is being upgraded or built
    if (reputationType.equals("upgrade")) {
      reputationGained = 50;
    } else if (reputationType.equals("build")) {
      reputationGained = 100;
    }

    // formula explained in method description
    score = (float) (
        ((reputationGained * REPUTATION_MODIFIER) + (building.getCapacity() * CAPACITY_MODIFIER))
            * Math.pow(
            (Arrays.asList(bottleneckRankings).indexOf(building.getClass().getSimpleName()) + 1),
            RANKING_MODIFIER) / (building.getUpgradeCost() * PRICE_MODIFIER));
    return score;
  }

  /**
   * Sorts the given linked hash map by value, from highest to lowest. Returns the sorted map.
   *
   * @param map the map to sort
   * @return the sorted map
   */
  public LinkedHashMap<Building, Float> sortMapByValue(LinkedHashMap<Building, Float> map) {
    // seperates the keyset and values, sorting the values
    ArrayList<Building> buildings = new ArrayList<Building>(map.keySet());
    ArrayList<Float> scores = new ArrayList<Float>(map.values());
    Collections.sort(scores);
    Collections.reverse(scores);
    LinkedHashMap<Building, Float> sortedMap = new LinkedHashMap<Building, Float>();
    // iterates through the sorted values, adding the corresponding building to the sorted map
    // along with its value
    for (Float score : scores) {
      for (Building building : buildings) {
        if (map.get(building) == score) {
          sortedMap.put(building, score);
          buildings.remove(building);
          break;
        }
      }
    }
    return sortedMap;
  }
}
