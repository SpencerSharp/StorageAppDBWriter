package storageappdbwriter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Version")
public class Version
{
    public long version;

    public Version()
    {
        
    }

    public Version(long v)
    {
        version = v;
    }

    @DynamoDBHashKey(attributeName = "id")
    public long getVersion()
    {
        return version;
    }

    public void setVersion(long v)
    {
        version = v;
    }

    @DynamoDBIgnore
    public void increment()
    {
        version++;
    }
}