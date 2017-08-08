package storageappdbwriter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "FacilitiesUnits")
public class FacilityToUnit
{
    private long id;
    private long facilityId;
    private long unitId;
    private double rateAmount;
    private String rateType;
    
    public FacilityToUnit()
    {
        
    }
    
    @DynamoDBHashKey(attributeName = "id")
    public long getId()
    {
        return id;
    }
    
    public void setId(long id)
    {
        this.id = id;
    }
    
    @DynamoDBAttribute(attributeName = "facilityId")
    public long getFacilityId()
    {
        return facilityId;
    }
    
    public void setFacilityId(long facilityId)
    {
        this.facilityId = facilityId;
    }
    
    @DynamoDBAttribute(attributeName = "unitId")
    public long getUnitId()
    {
        return unitId;
    }
    
    public void setUnitId(long unitId)
    {
        this.unitId = unitId;
    }
    
    @DynamoDBAttribute(attributeName = "rateAmount")
    public double getRateAmount()
    {
        return rateAmount;
    }
    
    public void setRateAmount(long rateAmount)
    {
        this.rateAmount = rateAmount;
    }

    @DynamoDBAttribute(attributeName = "rateType")
    public String getRateType() {
        return rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }
}
