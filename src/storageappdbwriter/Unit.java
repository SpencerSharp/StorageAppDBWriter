package storageappdbwriter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.math.BigDecimal;

@DynamoDBTable(tableName = "Units")
public class Unit
{
    private long id;
    private String name;
    private String type;

    private BigDecimal width;
    private BigDecimal depth;
    private BigDecimal height;

    private int floor;

    private BigDecimal doorHeight;
    private BigDecimal doorWidth;

    public Unit()
    {

    }

    @DynamoDBHashKey(attributeName = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @DynamoDBAttribute(attributeName = "width")
    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    @DynamoDBAttribute(attributeName = "depth")
    public BigDecimal getDepth() {
        return depth;
    }

    public void setDepth(BigDecimal depth) {
        this.depth = depth;
    }

    @DynamoDBAttribute(attributeName = "height")
    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    @DynamoDBAttribute(attributeName = "floor")
    public int getFloor(){
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    @DynamoDBAttribute(attributeName = "doorHeight")
    public BigDecimal getDoorHeight() {
        return doorHeight;
    }

    public void setDoorHeight(BigDecimal doorHeight) {
        this.doorHeight = doorHeight;
    }

    @DynamoDBAttribute(attributeName = "doorWidth")
    public BigDecimal getDoorWidth() {
        return doorWidth;
    }

    public void setDoorWidth(BigDecimal doorWidth) {
        this.doorWidth = doorWidth;
    }
    
    public boolean equalsUnit(Unit other)
    {
        if(other.type.equals(type))
        {
            if(other.floor == floor)
            {
                if(other.name.equals(name))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isEqualToJavaLocalGrailsUnit(JavaLocalGrailsUnit other)
    {
        if(other.type.equals(type))
        {
            if(other.floor == floor)
            {
                if(other.name.equals(name))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public String toString()
    {
        return "ID: " + id + " NAME: " + name + " TYPE: " + type + " FLOOR: " + floor;
    }
}