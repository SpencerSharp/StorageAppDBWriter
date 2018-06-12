package storageappdbwriter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.math.BigDecimal;
import java.sql.Time;

@DynamoDBTable(tableName = "Facilities")
public class Facility
{
    private long id;
    private String name;
    private long companyId;

    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String state;
    private String zip;
    private String country;

    private String website;
    private BigDecimal setupFee;
    private BigDecimal percentFull;

    private boolean hasRetailStore;
    private boolean hasInsurance;
    private boolean hasOnlineBillPay;
    private boolean hasWineStorage;
    private boolean hasKiosk;
    private boolean hasOnsiteManagement;
    private boolean hasCameras;
    private boolean hasVehicleParking;
    private boolean hasCutLocks;
    private boolean hasOnsiteShipping;
    private boolean hasAutopay;
    private boolean hasOnsiteCarts;
    private boolean hasParabolicMirrors;
    private boolean hasMotionLights;
    private boolean hasElectronicLease;
    private boolean hasPaperlessBilling;

    private Time mondayOpen;
    private Time mondayClose;
    private Time tuesdayOpen;
    private Time tuesdayClose;
    private Time wednesdayOpen;
    private Time wednesdayClose;
    private Time thursdayOpen;
    private Time thursdayClose;
    private Time fridayOpen;
    private Time fridayClose;
    private Time saturdayOpen;
    private Time saturdayClose;
    private Time sundayOpen;
    private Time sundayClose;

    private String rating;

    private String promotions;

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

    @DynamoDBAttribute(attributeName = "companyId")
    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    @DynamoDBAttribute(attributeName = "streetAddress1")
    public String getStreetAddress1() {
        return streetAddress1;
    }

    public void setStreetAddress1(String streetAddress1) {
        this.streetAddress1 = streetAddress1;
    }

    @DynamoDBAttribute(attributeName = "streetAddress2")
    public String getStreetAddress2() {
        return streetAddress2;
    }

    public void setStreetAddress2(String streetAddress2) {
        this.streetAddress2 = streetAddress2;
    }

    @DynamoDBAttribute(attributeName = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @DynamoDBAttribute(attributeName = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @DynamoDBAttribute(attributeName = "zip")
    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @DynamoDBAttribute(attributeName = "country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @DynamoDBAttribute(attributeName = "website")
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @DynamoDBAttribute(attributeName = "setupFee")
    public BigDecimal getSetupFee() {
        return setupFee;
    }

    public void setSetupFee(BigDecimal setupFee) {
        this.setupFee = setupFee;
    }

    @DynamoDBAttribute(attributeName = "percentFull")
    public BigDecimal getPercentFull() {
        return percentFull;
    }

    public void setPercentFull(BigDecimal percentFull) {
        this.percentFull = percentFull;
    }

    @DynamoDBAttribute(attributeName = "hasRetailStore")
    public boolean hasRetailStore() {
        return hasRetailStore;
    }

    public void setHasRetailStore(boolean hasRetailStore) {
        this.hasRetailStore = hasRetailStore;
    }

    @DynamoDBAttribute(attributeName = "hasInsurance")
    public boolean hasInsurance() {
        return hasInsurance;
    }

    public void setHasInsurance(boolean hasInsurance) {
        this.hasInsurance = hasInsurance;
    }

    @DynamoDBAttribute(attributeName = "hasOnlineBillPay")
    public boolean hasOnlineBillPay() {
        return hasOnlineBillPay;
    }

    public void setHasOnlineBillPay(boolean hasOnlineBillPay) {
        this.hasOnlineBillPay = hasOnlineBillPay;
    }

    @DynamoDBAttribute(attributeName = "hasWineStorage")
    public boolean hasWineStorage() {
        return hasWineStorage;
    }

    public void setHasWineStorage(boolean hasWineStorage) {
        this.hasWineStorage = hasWineStorage;
    }

    @DynamoDBAttribute(attributeName = "hasKiosk")
    public boolean hasKiosk() {
        return hasKiosk;
    }

    public void setHasKiosk(boolean hasKiosk) {
        this.hasKiosk = hasKiosk;
    }

    @DynamoDBAttribute(attributeName = "hasOnsiteManagement")
    public boolean hasOnsiteManagement() {
        return hasOnsiteManagement;
    }

    public void setHasOnsiteManagement(boolean hasOnsiteManagement) {
        this.hasOnsiteManagement = hasOnsiteManagement;
    }

    @DynamoDBAttribute(attributeName = "hasCameras")
    public boolean hasCameras() {
        return hasCameras;
    }

    public void setHasCameras(boolean hasCameras) {
        this.hasCameras = hasCameras;
    }

    @DynamoDBAttribute(attributeName = "hasVehicleParking")
    public boolean hasVehicleParking() {
        return hasVehicleParking;
    }

    public void setHasVehicleParking(boolean hasVehicleParking) {
        this.hasVehicleParking = hasVehicleParking;
    }

    @DynamoDBAttribute(attributeName = "hasCutLocks")
    public boolean hasCutLocks() {
        return hasCutLocks;
    }

    public void setHasCutLocks(boolean hasCutLocks) {
        this.hasCutLocks = hasCutLocks;
    }

    @DynamoDBAttribute(attributeName = "hasOnsiteShipping")
    public boolean hasOnsiteShipping() {
        return hasOnsiteShipping;
    }

    public void setHasOnsiteShipping(boolean hasOnsiteShipping) {
        this.hasOnsiteShipping = hasOnsiteShipping;
    }

    @DynamoDBAttribute(attributeName = "hasAutopay")
    public boolean hasAutopay() {
        return hasAutopay;
    }

    public void setHasAutopay(boolean hasAutopay) {
        this.hasAutopay = hasAutopay;
    }

    @DynamoDBAttribute(attributeName = "hasOnsiteCarts")
    public boolean hasOnsiteCarts() {
        return hasOnsiteCarts;
    }

    public void setHasOnsiteCarts(boolean hasOnsiteCarts) {
        this.hasOnsiteCarts = hasOnsiteCarts;
    }

    @DynamoDBAttribute(attributeName = "hasParabolicMirrors")
    public boolean hasParabolicMirrors() {
        return hasParabolicMirrors;
    }

    public void setHasParabolicMirrors(boolean hasParabolicMirrors) {
        this.hasParabolicMirrors = hasParabolicMirrors;
    }

    @DynamoDBAttribute(attributeName = "hasMotionLights")
    public boolean hasMotionLights() {
        return hasMotionLights;
    }

    public void setHasMotionLights(boolean hasMotionLights) {
        this.hasMotionLights = hasMotionLights;
    }

    @DynamoDBAttribute(attributeName = "hasElectronicLease")
    public boolean hasElectronicLease() {
        return hasElectronicLease;
    }

    public void setHasElectronicLease(boolean hasElectronicLease) {
        this.hasElectronicLease = hasElectronicLease;
    }

    @DynamoDBAttribute(attributeName = "hasPaperlessBilling")
    public boolean hasPaperlessBilling() {
        return hasPaperlessBilling;
    }

    public void setHasPaperlessBilling(boolean hasPaperlessBilling) {
        this.hasPaperlessBilling = hasPaperlessBilling;
    }

    @DynamoDBAttribute(attributeName = "mondayOpen")
    public Time getMondayOpen() {
        return mondayOpen;
    }

    public void setMondayOpen(Time mondayOpen) {
        this.mondayOpen = mondayOpen;
    }

    @DynamoDBAttribute(attributeName = "mondayClose")
    public Time getMondayClose() {
        return mondayClose;
    }

    public void setMondayClose(Time mondayClose) {
        this.mondayClose = mondayClose;
    }

    @DynamoDBAttribute(attributeName = "tuesdayOpen")
    public Time getTuesdayOpen() {
        return tuesdayOpen;
    }

    public void setTuesdayOpen(Time tuesdayOpen) {
        this.tuesdayOpen = tuesdayOpen;
    }

    @DynamoDBAttribute(attributeName = "tuesdayClose")
    public Time getTuesdayClose() {
        return tuesdayClose;
    }

    public void setTuesdayClose(Time tuesdayClose) {
        this.tuesdayClose = tuesdayClose;
    }

    @DynamoDBAttribute(attributeName = "wednesdayOpen")
    public Time getWednesdayOpen() {
        return wednesdayOpen;
    }

    public void setWednesdayOpen(Time wednesdayOpen) {
        this.wednesdayOpen = wednesdayOpen;
    }

    @DynamoDBAttribute(attributeName = "wednesdayClose")
    public Time getWednesdayClose() {
        return wednesdayClose;
    }

    public void setWednesdayClose(Time wednesdayClose) {
        this.wednesdayClose = wednesdayClose;
    }

    @DynamoDBAttribute(attributeName = "thursdayOpen")
    public Time getThursdayOpen() {
        return thursdayOpen;
    }

    public void setThursdayOpen(Time thursdayOpen) {
        this.thursdayOpen = thursdayOpen;
    }

    @DynamoDBAttribute(attributeName = "thursdayClose")
    public Time getThursdayClose() {
        return thursdayClose;
    }

    public void setThursdayClose(Time thursdayClose) {
        this.thursdayClose = thursdayClose;
    }

    @DynamoDBAttribute(attributeName = "fridayOpen")
    public Time getFridayOpen() {
        return fridayOpen;
    }

    public void setFridayOpen(Time fridayOpen) {
        this.fridayOpen = fridayOpen;
    }

    @DynamoDBAttribute(attributeName = "fridayClose")
    public Time getFridayClose() {
        return fridayClose;
    }

    public void setFridayClose(Time fridayClose) {
        this.fridayClose = fridayClose;
    }

    @DynamoDBAttribute(attributeName = "saturdayOpen")
    public Time getSaturdayOpen() {
        return saturdayOpen;
    }

    public void setSaturdayOpen(Time saturdayOpen) {
        this.saturdayOpen = saturdayOpen;
    }

    @DynamoDBAttribute(attributeName = "saturdayClose")
    public Time getSaturdayClose() {
        return saturdayClose;
    }

    public void setSaturdayClose(Time saturdayClose) {
        this.saturdayClose = saturdayClose;
    }

    @DynamoDBAttribute(attributeName = "sundayOpen")
    public Time getSundayOpen() {
        return sundayOpen;
    }

    public void setSundayOpen(Time sundayOpen) {
        this.sundayOpen = sundayOpen;
    }

    @DynamoDBAttribute(attributeName = "sundayClose")
    public Time getSundayClose() {
        return sundayClose;
    }

    public void setSundayClose(Time sundayClose) {
        this.sundayClose = sundayClose;
    }

    @DynamoDBAttribute(attributeName = "rating")
    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @DynamoDBAttribute(attributeName = "promotions")
    public String getPromotions() {
        return promotions;
    }

    public void setPromotions(String promotions) {
        this.promotions = promotions;
    }

    public String toString()
    {
        return id + " " + name;
    }
}