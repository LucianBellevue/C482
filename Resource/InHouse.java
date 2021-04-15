package Resource;

/**
 * In house resource data for parts.
 *
 * @author Diego Medina
 */
public class InHouse extends Part {

    /**
     * the part machine ID.
     */
    private int machineId;

    /**
     * Constructor for instance InHouse as object.
     * @param id part ID.
     * @param name part name.
     * @param price part price.
     * @param stock inventory level of part.
     * @param min minimum inventory of part.
     * @param max maximum inventory of part.
     * @param machineId machine ID of part.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * gets the machine ID.
     * @return the machine ID.
     */
    public int getMachineId() {return machineId;}

    /**
     * sets the machine ID.
     * @param machineId the machine ID.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
