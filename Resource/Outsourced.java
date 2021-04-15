package Resource;

/**
 * Resource data for outsourced parts.
 *
 * @author Diego Medina
 */
public class Outsourced extends Part{

    /**
     * The part's company name
     */
    private String companyName;

    /**
     * gets the company name of part.
     * @return the company name.
     */
    public String getCompanyName() {return companyName;}

    /**
     * sets the company name of part.
     * @param companyName the company name.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Constructor for the instance of Outsourced parts.
     * @param id part ID.
     * @param name part name.
     * @param price part price.
     * @param stock part inventory level.
     * @param min minimum inventory level of part.
     * @param max maximum inventory level of part.
     * @param companyName the name of the company for outsourced part.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
}
