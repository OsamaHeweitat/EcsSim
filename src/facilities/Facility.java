package facilities;

/**
 * A building or other place or set of equipment that is used for a particular purpose within the
 * university.
 *
 * @see Building
 */
public class Facility {

  private String name;

  /**
   * Constructs a new facility with the given name.
   *
   * @param name the name of the facility
   */
  public Facility(String name) {
    this.name = name;
  }

  /**
   * Returns the name of the facility.
   *
   * @return the name of the facility
   */
  public String getName() {
    return name;
  }
}
