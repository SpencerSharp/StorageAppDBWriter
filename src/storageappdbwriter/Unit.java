package storageappdbwriter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.math.BigDecimal;
import java.util.Objects;

@DynamoDBTable(tableName = "Units")
public class Unit implements Comparable<Unit>
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
    
    public int compareTo(Unit other)
    {
        int c = width.compareTo(other.width);
        if(c < 0)
        {
            return -1;
        }
        else if(c > 0)
        {
            return 1;
        }
        
        c = depth.compareTo(other.depth);
        if(c < 0)
        {
            return -1;
        }
        else if(c > 0)
        {
            return 1;
        }
        
        c = type.compareTo(other.type);
        if(c < 0)
        {
            return 1;
        }
        else if(c > 0)
        {
            return -1;
        }
        
        if(floor > other.floor)
        {
            return -1;
        }
        else if(floor < other.floor)
        {
            return 1;
        }
        return 0;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + Objects.hashCode(this.type);
        hash = 89 * hash + this.floor;
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj == null)
        {
            return false;
        }
        if(getClass() != obj.getClass())
        {
            return false;
        }
        final Unit other = (Unit) obj;
        if(!Objects.equals(this.name, other.name))
        {
            return false;
        }
        if(!Objects.equals(this.type, other.type))
        {
            return false;
        }
        if(this.floor != other.floor)
        {
            return false;
        }
        return true;
    }
}