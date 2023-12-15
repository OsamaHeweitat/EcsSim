package university;

import facilities.Facility;
import facilities.buildings.Building;
import facilities.buildings.Hall;
import facilities.buildings.Lab;
import facilities.buildings.Theatre;
import facilities.recreational.Cafeteria;
import facilities.recreational.Gym;
import facilities.recreational.Recreational;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The estate of the university, containing all the facilities.
 *
 * @see Facility
 */
public class Estate {

  private ArrayList<Facility> facilities;
  private static final String[] RECREATIONAL_TYPES = {"Cafeteria", "Gym"};

  /**
   * Constructs a new estate with no facilities.
   */
  public Estate() {
    facilities = new ArrayList<Facility>();
  }

  /**
   * Returns the facilities in the estate as an array of facilities.
   *
   * @return the facilities in the estate as an array of facilities
   */
  public Facility[] getFacilities() {
    return facilities.toArray(new Facility[facilities.size()]);
  }

  /**
   * Returns the upgradable buildings in the estate as an array of buildings.
   *
   * @return the upgradable buildings in the estate as an array of buildings
   */
  public Building[] getUpgradableBuildings() {
    ArrayList<Building> buildings = new ArrayList<Building>();

    // iterates through all facilities
    for (Facility facility : facilities) {
      // if the facility is a building and is upgradable, adds it to the array of buildings
      if (facility instanceof Building && ((Building) facility).isUpgradable()) {
        buildings.add((Building) facility);
      }
    }
    return buildings.toArray(new Building[buildings.size()]);
  }

  /**
   * Returns the upgradable recreational facilities in the estate as an array of recreational
   * facilities.
   *
   * @return the upgradable recreational facilities in the estate as an array of recreational
   *          facilities
   */
  public Recreational[] getUpgradableRecreational() {
    ArrayList<Recreational> recreational = new ArrayList<Recreational>();

    // iterates through all facilities
    for (Facility facility : facilities) {
      // if the facility is recreational and is upgradable, adds it to the array of recreational 
      // facilities
      if (facility instanceof Recreational && ((Recreational) facility).isUpgradable()) {
        recreational.add((Recreational) facility);
      }
    }
    return recreational.toArray(new Recreational[recreational.size()]);
  }

  /**
   * Adds a facility to the estate with the given type and name. Returns the facility if successful,
   * otherwise returns null.
   *
   * @param type the type of the facility
   * @param name the name of the facility
   * @return the facility if successful, otherwise returns null
   */
  public Facility addFacility(String type, String name) {
    // adds new building depending on the type parameter, if the type is invalid, null is returned
    if (type.equals("Hall")) {
      facilities.add(new Hall(name));
    } else if (type.equals("Theatre")) {
      facilities.add(new Theatre(name));
    } else if (type.equals("Lab")) {
      facilities.add(new Lab(name));
    } else if (type.equals("Cafeteria")) {
      facilities.add(new Cafeteria(name));
    } else if (type.equals("Gym")) {
      facilities.add(new Gym(name));
    } else {
      return null;
    }
    return facilities.get(facilities.size() - 1);
  }

  /**
   * Gets the total maintenance cost of all facilities in the estate. The maintenance cost is
   * calulated as 10% of the capacity of each building.
   *
   * @return the total maintenance cost of all facilities in the estate
   */
  public float getMaintenanceCost() {
    float total = 0;
    for (Facility facility : facilities) {
      // after ensuring the facility is a building, type cast facility to building in order to get
      // the capacity and add the maintenance cost to the total. Maintenance cost is 10% of the
      // capacity of each building.
      if (facility instanceof facilities.buildings.Building) {
        total += ((facilities.buildings.Building) facility).getCapacity() * 0.1;
      }
    }
    return total;
  }

  /**
   * Gets the minimum number of students the estate can accomodate. This is the minimum of the total
   * capacity of all halls, theatres, and labs.
   *
   * @return the minimum number of students the estate can accomodate
   */
  public int getNumberOfStudents() {
    int hallTotal = 0;
    int theatreTotal = 0;
    int labTotal = 0;

    // iterates through every building, adding the capacity to the respective total
    for (Facility facility : facilities) {
      if (facility instanceof facilities.buildings.Hall) {
        hallTotal += ((facilities.buildings.Building) facility).getCapacity();
      } else if (facility instanceof facilities.buildings.Theatre) {
        theatreTotal += ((facilities.buildings.Building) facility).getCapacity();
      } else if (facility instanceof facilities.buildings.Lab) {
        labTotal += ((facilities.buildings.Building) facility).getCapacity();
      }
    }

    // returns the minimum of the three totals (the bottleneck) using nested if statements
    if (hallTotal < theatreTotal) {
      if (hallTotal < labTotal) {
        return hallTotal;
      } else {
        return labTotal;
      }
    } else {
      if (theatreTotal < labTotal) {
        return theatreTotal;
      } else {
        return labTotal;
      }
    }
  }

  /**
   * Gets an ascending ranking of the bottleneck buildings in the estate. The bottleneck buildings
   * are the buildings that are the most limiting in terms of capacity. The ranking is in order of
   * least bottleneck to most bottleneck.
   *
   * @return an ascending ranking of the bottleneck buildings in the estate
   */
  public String[] getBottleneckRankings() {
    int hallTotal = 0;
    int theatreTotal = 0;
    int labTotal = 0;

    // iterates through every building, adding the capacity to the respective total
    for (Facility facility : facilities) {
      if (facility instanceof facilities.buildings.Hall) {
        hallTotal += ((facilities.buildings.Building) facility).getCapacity();
      } else if (facility instanceof facilities.buildings.Theatre) {
        theatreTotal += ((facilities.buildings.Building) facility).getCapacity();
      } else if (facility instanceof facilities.buildings.Lab) {
        labTotal += ((facilities.buildings.Building) facility).getCapacity();
      }
    }

    // sorts the totals in ascending order, then reverses the array to get the totals in descending
    // order
    int[] totals = {hallTotal, theatreTotal, labTotal};
    int[] reversedTotals = new int[totals.length];
    Arrays.sort(totals);
    for (int i = 0; i < totals.length; i++) {
      reversedTotals[i] = totals[totals.length - 1 - i];
    }
    totals = reversedTotals;

    // iterates through the totals, adding the building type to the rankings array in the matching
    // position of the total if the total matches the total of the building type and the building
    // type is not already in the rankings array
    String[] rankings = {"", "", ""};
    for (int i = 0; i < 3; i++) {
      if (totals[i] == hallTotal && !Arrays.asList(rankings).contains("Hall")) {
        rankings[i] = "Hall";
      } else if (totals[i] == theatreTotal && !Arrays.asList(rankings).contains("Theatre")) {
        rankings[i] = "Theatre";
      } else if (totals[i] == labTotal) {
        rankings[i] = "Lab";
      }
    }
    return rankings;
  }

  /**
   * Gets an array of the unbuilt recreational facilities types on this estate.
   *
   * @return an array of the unbuilt recreational facilities types on this estate
   */
  public String[] getUnbuiltRecreationalTypes() {
    ArrayList<String> recreationalTypes = new ArrayList<String>(Arrays.asList(RECREATIONAL_TYPES));
    for (Facility facility : getFacilities()) {
      if (recreationalTypes.contains(facility.getClass().getSimpleName())) {
        recreationalTypes.remove(facility.getClass().getSimpleName());
      }
    }
    return recreationalTypes.toArray(new String[recreationalTypes.size()]);
  }

  /**
   * Gets the total profit of all recreational facilities in the estate.
   *
   * @return the total profit of all recreational facilities in the estate
   */
  public int collectProfits() {
    int total = 0;
    for (Facility facility : facilities) {
      if (facility instanceof Recreational) {
        total += ((Recreational) facility).getProfit() * getNumberOfStudents();
        System.out.println(
            "Collected " + ((Recreational) facility).getProfit() * getNumberOfStudents()
                + " coins from "
                + facility.getName()
                + " " + facility.getClass().getSimpleName() + ".");
      }
    }
    return total;
  }
}
