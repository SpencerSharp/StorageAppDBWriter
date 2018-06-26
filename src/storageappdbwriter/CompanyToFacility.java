package storageappdbwriter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "CompaniesFacilities")
public class CompanyToFacility implements Comparable<CompanyToFacility>
{
    private long id;
    private long companyId;
    private long facilityId;

    public CompanyToFacility()
    {

    }

    @DynamoDBHashKey(attributeName = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "companyId")
    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    @DynamoDBAttribute(attributeName = "facilityId")
    public long getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(long facilityId) {
        this.facilityId = facilityId;
    }

    public String toString()
    {
        return id + " " + companyId + " " + facilityId;
    }
    
    public int compareTo(CompanyToFacility other)
    {
        if(companyId > other.companyId)
        {
            return 1;
        }
        else if(companyId < other.companyId)
        {
            return -1;
        }
        else
        {
            if(facilityId > other.facilityId)
            {
                return 1;
            }
            else if(facilityId < other.facilityId)
            {
                return -1;
            }
            else
            {
                return 0;
            }
        }
    }
}