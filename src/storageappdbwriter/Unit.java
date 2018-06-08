package storageappdbwriter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Units")
public class Unit
{
    private long id;
    private String name;
    
    private String type;
    private double width;
    private double depth;
    private double height;
    
    private int floor;
    
    private double doorHeight;
    private double doorWidth;
    
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
    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    @DynamoDBAttribute(attributeName = "depth")
    public double getDepth() {
        return depth;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    @DynamoDBAttribute(attributeName = "height")
    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
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
    public double getDoorHeight() {
        return doorHeight;
    }

    public void setDoorHeight(double doorHeight) {
        this.doorHeight = doorHeight;
    }

    @DynamoDBAttribute(attributeName = "doorWidth")
    public double getDoorWidth() {
        return doorWidth;
    }

    public void setDoorWidth(double doorWidth) {
        this.doorWidth = doorWidth;
    }
    
    public String toString()
    {
        return id + " " + name + " " + type + " " + floor;
    }
}