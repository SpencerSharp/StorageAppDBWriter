package storageappdbwriter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.math.BigDecimal;
import java.util.Date;

@DynamoDBTable(tableName = "FacilitiesUnits")
public class FacilityToUnitHistory
{
    private long id;
    private long facilityId;
    private long unitId;
    private Date dateCreated;
    private BigDecimal rateAmount;
    private String rateType;

    public FacilityToUnitHistory()
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

    @DynamoDBAttribute(attributeName = "dateCreated")
    public Date getDateCreated()
    {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated)
    {
        this.dateCreated = dateCreated;
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
    public BigDecimal getRateAmount()
    {
        return rateAmount;
    }

    public void setRateAmount(BigDecimal rateAmount)
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

    @DynamoDBIgnore
    public FacilityToUnitHistory createFromFacilityToUnit(FacilityToUnit other)
    {
        this.id = other.getId();
        this.facilityId = other.getFacilityId();
        this.unitId = other.getUnitId();
        this.dateCreated = other.getDateCreated();
        this.rateAmount = other.getRateAmount();
        this.rateType = other.getRateType();

        return this;
    }

    public String toString()
    {
        return id + " " + facilityId + " " + unitId + " Cost: " + rateAmount + " Type: " + rateType;
    }
}
