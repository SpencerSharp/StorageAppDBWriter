/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storageappdbwriter;

/**
 *
 * @author spencersharp
 */
public class UserPreferences
{
    long userId;
    String landingPage;
    long landingFacilityId;
    
    public void UserPreferences()
    {
        
    }
    
    public long getUserId()
    {
        return userId;
    }
    
    public void setUserId(long userId)
    {
        this.userId = userId;
    }
    
    public String getLandingPage()
    {
        return landingPage;
    }
    
    public void setLandingPage(String landingPage)
    {
        this.landingPage = landingPage;
    }
    
    public long getLandingFacilityId()
    {
        return landingFacilityId;
    }
    
    public void setLandingFacilityId(long landingFacilityId)
    {
        this.landingFacilityId = landingFacilityId;
    }
}