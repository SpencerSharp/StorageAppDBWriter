package storageappdbwriter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import java.util.Objects;

@DynamoDBTable(tableName = "Companies")
public class Company implements Comparable<Company>
{
    private long id;
    private String name;
    private String website;

    public Company()
    {

    }

    public Company(String name)
    {
        setName(name);
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

    @DynamoDBAttribute(attributeName = "website")
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String toString()
    {
        return id + " " + name;
    }

    public String fullDelimitedToString()
    {
        return id+"*"+name+"*"+website;
    }
    
    @Override
    public int compareTo(Company other)
    {
        return name.compareTo(other.name);
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.name);
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
        final Company other = (Company) obj;
        if(!Objects.equals(this.name, other.name))
        {
            return false;
        }
        return true;
    }
}