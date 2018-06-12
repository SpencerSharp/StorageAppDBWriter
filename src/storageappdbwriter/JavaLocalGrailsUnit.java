package storageappdbwriter;

import java.math.BigDecimal;
import java.util.Date;

public class JavaLocalGrailsUnit {
    public long id;
    public String name;
    public BigDecimal width;
    public BigDecimal depth;
    public BigDecimal height;
    public String type;
    public int floor;
    public BigDecimal price;
    public long facilityId;
    public Date dateCreated;

    public String rateType;

    public JavaLocalGrailsUnit()
    {

    }

    public JavaLocalGrailsUnit(long id, String name, BigDecimal width, BigDecimal depth, BigDecimal height, String type, int floor, BigDecimal price, long facilityId, String rateType, Date dateCreated)
    {
        this.id = id;
        this.name = name;
        this.width = width;
        this.depth = depth;
        this.height = height;
        this.type = type;
        this.floor = floor;
        this.price = price;
        this.facilityId = facilityId;
        this.rateType = rateType;
        this.dateCreated = dateCreated;
    }

    public String toString()
    {
        return "ID: " + id + " NAME: " + name + " WIDTH: " + width + " DEPTH: " + depth + " HEIGHT: " + height + " TYPE: " + type + " FLOOR: " + floor + " PRICE: " + price + " FACILITYID: " + facilityId + " RATETYPE: " + rateType;
    }
}