package storageappdbwriter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Values")
public class Value
{
    public String name;
    public long value;

    public Value()
    {
        
    }

    public Value(String name)
    {
        this.name = name;
    }

    @DynamoDBHashKey(attributeName = "name")
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    
    @DynamoDBHashKey(attributeName = "value")
    public long getValue()
    {
        return value;
    }

    public void setValue(long value)
    {
        this.value = value;
    }
}