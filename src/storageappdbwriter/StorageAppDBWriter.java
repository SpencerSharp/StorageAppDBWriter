package storageappdbwriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.System.out;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class StorageAppDBWriter
{
    public static boolean isDelimiterLine(String line)
    {
        String[] delimiters = {"-","~","."};
        for(String s : delimiters)
        {
            if(line.equals(s))
            {
                return true;
            }
        }
        return false;
    }
    
    public static BigDecimal buildBigDecimal(String string)
    {
        if(string.length()==0)
        {
            return null;
        }
        //out.println(string);
        return new BigDecimal(string);
    }
    
    public static Time buildTime(String string)
    {
        if(string.length()==0)
        {
            return null;
        }
        return new Time(Long.parseLong(string));
    }
    
    public static void main(String args[]) throws IOException, InterruptedException, SQLException
    {
        RDSHandler rds = new RDSHandler();
        //rds.resetTables();
        //System.exit(1);
        Calendar start = Calendar.getInstance();

        
        String dataFile = "DataFiles/Scraper_Input.txt";
        
        

        ArrayList<Company> companies = new ArrayList<Company>();
        HashMap<Company,Long> companyToCorrectId = new HashMap<Company,Long>();
        long maxCompanyId = rds.getMaxCompanyId();
        out.println("MAX COMPANY ID: " + maxCompanyId);
        ArrayList<CompanyToFacility> companiesToFacilities = new ArrayList<CompanyToFacility>();
        long maxCompanyToFacilityId = rds.getMaxCompanyToFacilityId();
        ArrayList<Facility> facilities = new ArrayList<Facility>();
        long maxFacilityId = rds.getMaxFacilityId();
        HashMap<Facility,Long> facilityToCorrectId = new HashMap<Facility,Long>();
        ArrayList<FacilityToUnit> facilitiesToUnits = new ArrayList<FacilityToUnit>();
        long maxFacilityToUnitId = rds.getMaxFacilityToUnitId();
        ArrayList<Unit> units = new ArrayList<Unit>();
        long maxUnitId = rds.getMaxUnitId();
        HashMap<Unit,Long> unitToCorrectId = new HashMap<Unit,Long>();
        
        Company company = null;
        Facility facility = null;
        LocalDateTime writingDate = TimeFormatter.getCurrentLocalDateTimeToWrite();
        //Timestamp writetime = rds.getSqlDateFromDate(writingDate);
        WriteTime writeTime = new WriteTime();
        writeTime.setTime(writingDate);
        
        for(int i = 0; i < 2; i++)
        {
            //out.println(companies);
            BufferedReader f = new BufferedReader(new FileReader(dataFile));
            String line;
            while((line=f.readLine())!=null)
            {
                //out.println("LINE: " + line);
                if(line.equals("-"))
                {
                    //Company time
                    company = new Company();
                    if(companies.size() == 0)
                    {
                        company.setId(0);
                    }
                    else
                    {
                        company.setId(companies.get(companies.size()-1).getId()+1);
                    }
                    company.setName(f.readLine());
                    company.setWebsite(f.readLine());
                    boolean found = false;
                    for(Company temp : companies)
                    {
                        if(temp.getName().equals(company.getName()))
                        {
                            company = temp;
                            found = true;
                        }
                    }
                    if(!found)
                    {
                        if(i==0)
                            companies.add(company);
                    }

                }
                else if(line.equals("~"))
                {
                    //Facility time
                    facility = new Facility();
                    if(facilities.size()==0)
                    {
                        facility.setId(0);
                    }
                    else
                    {
                        facility.setId(facilities.get(facilities.size()-1).getId()+1);
                    }
                    facility.setName(f.readLine());
                    facility.setSourceURL(f.readLine());
                    facility.setCompanyId(company.getId());
                    facility.setStreetAddress1(f.readLine());
                    facility.setStreetAddress2(f.readLine());
                    facility.setCity(f.readLine());
                    facility.setState(f.readLine());
                    facility.setZip(f.readLine());
                    facility.setCountry(f.readLine());
                    facility.setWebsite(f.readLine());
                    facility.setSetupFee(buildBigDecimal(f.readLine()));
                    facility.setPercentFull(buildBigDecimal(f.readLine()));
                    facility.setHasRetailStore(Boolean.parseBoolean(f.readLine()));
                    facility.setHasRetailStore(Boolean.parseBoolean(f.readLine()));
                    facility.setHasOnlineBillPay(Boolean.parseBoolean(f.readLine()));
                    facility.setHasWineStorage(Boolean.parseBoolean(f.readLine()));
                    facility.setHasKiosk(Boolean.parseBoolean(f.readLine()));
                    facility.setHasOnsiteManagement(Boolean.parseBoolean(f.readLine()));
                    facility.setHasCameras(Boolean.parseBoolean(f.readLine()));
                    facility.setHasVehicleParking(Boolean.parseBoolean(f.readLine()));
                    facility.setHasCutLocks(Boolean.parseBoolean(f.readLine()));
                    facility.setHasOnsiteShipping(Boolean.parseBoolean(f.readLine()));
                    facility.setHasAutopay(Boolean.parseBoolean(f.readLine()));
                    facility.setHasOnsiteCarts(Boolean.parseBoolean(f.readLine()));
                    facility.setHasParabolicMirrors(Boolean.parseBoolean(f.readLine()));
                    facility.setHasMotionLights(Boolean.parseBoolean(f.readLine()));
                    facility.setHasElectronicLease(Boolean.parseBoolean(f.readLine()));
                    facility.setHasPaperlessBilling(Boolean.parseBoolean(f.readLine()));
                    facility.setMondayOpen(buildTime(f.readLine()));
                    facility.setMondayClose(buildTime(f.readLine()));
                    facility.setTuesdayOpen(buildTime(f.readLine()));
                    facility.setTuesdayClose(buildTime(f.readLine()));
                    facility.setWednesdayOpen(buildTime(f.readLine()));
                    facility.setWednesdayClose(buildTime(f.readLine()));
                    facility.setThursdayOpen(buildTime(f.readLine()));
                    facility.setThursdayClose(buildTime(f.readLine()));
                    facility.setFridayOpen(buildTime(f.readLine()));
                    facility.setFridayClose(buildTime(f.readLine()));
                    facility.setSaturdayOpen(buildTime(f.readLine()));
                    facility.setSaturdayClose(buildTime(f.readLine()));
                    facility.setSundayOpen(buildTime(f.readLine()));
                    facility.setSundayClose(buildTime(f.readLine()));
                    facility.setRating(f.readLine());
                    facility.setPromotions(f.readLine());
                    
                    for(Facility temp : facilities)
                    {
                        if(temp.equals(facility))
                        {
                            temp.setCompanyId(company.getId());
                        }
                    }
                    
                    //out.println(facility);
                    if(i==0)
                        facilities.add(facility);

                    if(i==1)
                    {
                        CompanyToFacility companyToFacility = new CompanyToFacility();
                        if(companiesToFacilities.size()==0)
                        {
                            companyToFacility.setId(0);
                        }
                        else
                        {
                            companyToFacility.setId(companiesToFacilities.get(companiesToFacilities.size()-1).getId()+1);
                        }
                        companyToFacility.setCompanyId(companyToCorrectId.get(company));
                        companyToFacility.setFacilityId(facilityToCorrectId.get(facility));
                        
                        companiesToFacilities.add(companyToFacility);
                    }
                }
                else //Units time
                {
                    Unit unit = null;

                    if(unit==null)
                    {
                        unit = new Unit();
                        if(units.size()==0)
                        {
                            unit.setId(0);
                        }
                        else
                        {
                            unit.setId(units.get(units.size()-1).getId()+1);
                        }
                        unit.setName(f.readLine());
                        unit.setName(unit.getName().replace(" ", ""));
                        unit.setType(f.readLine());
                        unit.setWidth(buildBigDecimal(f.readLine()));
                        unit.setDepth(buildBigDecimal(f.readLine()));
                        unit.setHeight(buildBigDecimal(f.readLine()));
                        unit.setFloor(Integer.parseInt(f.readLine()));
                        unit.setDoorHeight(buildBigDecimal(f.readLine()));
                        unit.setDoorWidth(buildBigDecimal(f.readLine()));

                        boolean found = false;
                        //TODO: Replace this loop with something else
                        //IDEA: Sort the list, everything that has compareTo == 0 adjacent merge into one
                        for(Unit temp : units)
                        {
                            if(temp.equalsUnit(unit))
                            {
                                unit = temp;
                                found = true;
                                break;
                            }
                        }
                        
                        if(i==1 && unit.getId() == 11)
                        {
                            out.println("11 " + facility);
                        }
                        
                        //if(unit.getType().equals("Non-Climate") && unit.getName().equals("5'x10'") && unit.getFloor() == 1)
                        //{
                        //    out.println("DUPLICATE: " + facility);
                        //}

                        if(!found)
                        {
                            if(i==0)
                                units.add(unit);
                        }
                    }
                    
                    if(i==1)
                    {
                        FacilityToUnit facilityToUnit = new FacilityToUnit();
                        if(facilitiesToUnits.size()==0)
                        {
                            facilityToUnit.setId(0);
                        }
                        else
                        {
                            facilityToUnit.setId(facilitiesToUnits.get(facilitiesToUnits.size()-1).getId()+1);
                        }
                        facilityToUnit.setRateAmount(new BigDecimal(f.readLine()));
                        facilityToUnit.setRateType(f.readLine());
                        facilityToUnit.setUnitId(unitToCorrectId.get(unit));
                        facilityToUnit.setFacilityId(facilityToCorrectId.get(facility));
                        facilityToUnit.setDateCreated(writingDate);
                        facilityToUnit.setRateType("standard");
                        if(i==1 && unit.getId() == 11)
                        {
                            out.println("THIS IT: " +facilityToUnit);
                        }
                        facilitiesToUnits.add(facilityToUnit);
                    }
                    else
                    {
                        f.readLine();
                        f.readLine();
                    }
                }
            }
            if(i==0)
            {
                Collections.sort(companies);
                Collections.sort(facilities);
                Collections.sort(units);
                out.println(units);
                
                ArrayList<Company> dbCompanies = rds.getAllCompanies();
                Collections.sort(dbCompanies);
                ArrayList<Facility> dbFacilities = rds.getAllFacilities();
                Collections.sort(dbFacilities);
                ArrayList<Unit> dbUnits = rds.getAllUnits();
                Collections.sort(dbUnits);
                
                ArrayList<Company> companiesToWrite = new ArrayList<Company>();
                
                int index1 = 0;
                int index2 = 0;
                long index = 0;
                
                while(index1 < companies.size() && index2 < dbCompanies.size())
                {
                    int compare = companies.get(index1).compareTo(dbCompanies.get(index2));
                    if(compare > 0) //This is a new company
                    {
                        companyToCorrectId.put(dbCompanies.get(index2), index);
                        dbCompanies.get(index2).setId(index++);
                        companiesToWrite.add(dbCompanies.get(index2++));
                    }
                    else if(compare == 0) //The companies match
                    {
                        companyToCorrectId.put(companies.get(index1), index);
                        companies.get(index1).setId(index++);
                        companiesToWrite.add(companies.get(index1++));
                        index2++;
                    }
                    else //Keep looking in dbCompanies
                    {
                        companyToCorrectId.put(companies.get(index1),index);
                        
                        companies.get(index1).setId(index++);
                
                        companiesToWrite.add(companies.get(index1++));
                    }
                }
                for(int j = index1; j < companies.size(); j++)
                {
                    companyToCorrectId.put(companies.get(j), index);
                    companies.get(j).setId(index++);
                    companiesToWrite.add(companies.get(j));
                }
                for(int j = index2; j < dbCompanies.size(); j++)
                {
                    companyToCorrectId.put(dbCompanies.get(j), index);
                    dbCompanies.get(j).setId(index++);
                    companiesToWrite.add(dbCompanies.get(j));
                }
                
                index1 = 0;
                index2 = 0;
                index = 0;
                
                ArrayList<Facility> facilitiesToWrite = new ArrayList<Facility>();
                
                while(index1 < facilities.size() && index2 < dbFacilities.size())
                {
                    int compare = facilities.get(index1).compareTo(facilities.get(index2));
                    if(compare > 0) //This is a new company
                    {
                        facilityToCorrectId.put(dbFacilities.get(index2), index);
                        dbFacilities.get(index2).setId(index++);
                        facilitiesToWrite.add(dbFacilities.get(index2++));
                    }
                    else if(compare == 0) //The companies match
                    {
                        facilityToCorrectId.put(facilities.get(index1), index);
                        facilities.get(index1).setId(index++);
                        facilitiesToWrite.add(facilities.get(index1++));
                        index2++;
                    }
                    else //Keep looking in dbCompanies
                    {
                        facilityToCorrectId.put(facilities.get(index1),index);
                        
                        facilities.get(index1).setId(index++);
                
                        facilitiesToWrite.add(facilities.get(index1++));
                    }
                }
                for(int j = index1; j < facilities.size(); j++)
                {
                    facilityToCorrectId.put(facilities.get(j), index);
                    facilities.get(j).setId(index++);
                    facilitiesToWrite.add(facilities.get(j));
                }
                for(int j = index2; j < dbFacilities.size(); j++)
                {
                    facilityToCorrectId.put(dbFacilities.get(j), index);
                    dbFacilities.get(j).setId(index++);
                    facilitiesToWrite.add(dbFacilities.get(j));
                }
                
                index1 = 0;
                index2 = 0;
                index = 0;
                
                ArrayList<Unit> unitsToWrite = new ArrayList<Unit>();
                
                while(index1 < units.size() && index2 < dbUnits.size())
                {
                    int compare = units.get(index1).compareTo(dbUnits.get(index2));
                    if(compare > 0) //This is a new company
                    {
                        unitToCorrectId.put(dbUnits.get(index2), index);
                        dbUnits.get(index2).setId(index++);
                        unitsToWrite.add(dbUnits.get(index2++));
                    }
                    else if(compare == 0) //The companies match
                    {
                        unitToCorrectId.put(units.get(index1), index);
                        units.get(index1).setId(index++);
                        unitsToWrite.add(units.get(index1++));
                        index2++;
                    }
                    else //Keep looking in dbCompanies
                    {
                        unitToCorrectId.put(units.get(index1),index);
                        
                        units.get(index1).setId(index++);
                
                        unitsToWrite.add(units.get(index1++));
                    }
                }
                for(int j = index1; j < units.size(); j++)
                {
                    unitToCorrectId.put(units.get(j), index);
                    units.get(j).setId(index++);
                    unitsToWrite.add(units.get(j));
                }
                for(int j = index2; j < dbUnits.size(); j++)
                {
                    unitToCorrectId.put(dbUnits.get(j), index);
                    dbUnits.get(j).setId(index++);
                    unitsToWrite.add(dbUnits.get(j));
                }
                out.println("MAP OF FOUND: " + companyToCorrectId);
            }
        }
        
        Collections.sort(companiesToFacilities);
        
        ArrayList<CompanyToFacility> dbCompaniesToFacilities = rds.getAllCompanyToFacilities();
        Collections.sort(dbCompaniesToFacilities);
        
        ArrayList<CompanyToFacility> companiesToFacilitiesToWrite = new ArrayList<CompanyToFacility>();
        
        int index1 = 0;
        int index2 = 0;
        int index = 0;
                
        while(index1 < companiesToFacilities.size() && index2 < dbCompaniesToFacilities.size())
        {
            int compare = companiesToFacilities.get(index1).compareTo(dbCompaniesToFacilities.get(index2));
            if(compare > 0) //This is a new company
            {
                dbCompaniesToFacilities.get(index2).setId(index++);
                companiesToFacilitiesToWrite.add(dbCompaniesToFacilities.get(index2++));
            }
            else if(compare == 0) //The companies match
            {
                companiesToFacilities.get(index1).setId(index++);
                companiesToFacilitiesToWrite.add(companiesToFacilities.get(index1++));
                index2++;
            }
            else //Keep looking in dbCompanies
            {
                companiesToFacilities.get(index1).setId(index++);

                companiesToFacilitiesToWrite.add(companiesToFacilities.get(index1++));
            }
        }
        for(int j = index1; j < companiesToFacilities.size(); j++)
        {
            companiesToFacilities.get(j).setId(index++);
            companiesToFacilitiesToWrite.add(companiesToFacilities.get(j));
        }
        for(int j = index2; j < dbCompaniesToFacilities.size(); j++)
        {
            dbCompaniesToFacilities.get(j).setId(index++);
            companiesToFacilitiesToWrite.add(dbCompaniesToFacilities.get(j));
        }
        
        
        
        
        
        
        
        
        
        
        
        Collections.sort(facilitiesToUnits);
        ArrayList<FacilityToUnit> dbFacilitiesToUnits = rds.getAllFacilityToUnits();
        Collections.sort(dbFacilitiesToUnits);
        ArrayList<FacilityToUnit> facilityToUnitsToWrite = new ArrayList<FacilityToUnit>();
        ArrayList<FacilityToUnitHistory> facilityToUnitsToBackup = rds.getAllFacilityToUnitHistories();
        
        index1 = 0;
        index2 = 0;
        index = 0;
                
        while(index1 < facilitiesToUnits.size() && index2 < dbFacilitiesToUnits.size())
        {
            int compare = facilitiesToUnits.get(index1).compareTo(dbFacilitiesToUnits.get(index2));
            if(compare > 0) //This is a new company
            {
                dbFacilitiesToUnits.get(index2).setId(index++);
                facilityToUnitsToWrite.add(dbFacilitiesToUnits.get(index2++));
            }
            else if(compare == 0) //The companies match
            {
                facilitiesToUnits.get(index1).setId(index++);
                facilityToUnitsToWrite.add(facilitiesToUnits.get(index1++));
                facilityToUnitsToBackup.add(dbFacilitiesToUnits.get(index2++).getAsFacilityToUnitHistory());
            }
            else //Keep looking in dbCompanies
            {
                facilitiesToUnits.get(index1).setId(index++);

                facilityToUnitsToWrite.add(facilitiesToUnits.get(index1++));
            }
        }
        for(int j = index1; j < facilitiesToUnits.size(); j++)
        {
            facilitiesToUnits.get(j).setId(index++);
            facilityToUnitsToWrite.add(facilitiesToUnits.get(j));
        }
        for(int j = index2; j < dbFacilitiesToUnits.size(); j++)
        {
            dbFacilitiesToUnits.get(j).setId(index++);
            facilityToUnitsToWrite.add(dbFacilitiesToUnits.get(j));
        }
        Collections.sort(facilityToUnitsToBackup);
        index = 0;
        for(FacilityToUnitHistory facilityToUnitHistory : facilityToUnitsToBackup)
        {
            facilityToUnitHistory.setId(index++);
        }
        out.println("BACKING UP: " + facilityToUnitsToBackup.size());

        //NOW ALL THE LISTS ARE SET UP WITH CORRECT IDS!!!
//        ArrayList<FacilityToUnitHistory> onesToAdd = new ArrayList<FacilityToUnitHistory>();
//        for(int in = 0; in < onesToReplace.size(); in++)
//        {
//            onesToReplace.get(in).setId(++maxFacilityToUnitHistoryId);
//            //if(onesToReplace.get(in).getFacilityId() == 6)
//                onesToAdd.add(new FacilityToUnitHistory().createFromFacilityToUnit(onesToReplace.get(in)));
//        }
        
        //rds.batchSaveFacilityToUnitsHistory(onesToAdd);
        
        rds.overwriteAllCompanies(companies);
        rds.overwriteAllCompanyToFacilities(companiesToFacilities);
        rds.overwriteAllFacilities(facilities);
        rds.overwriteAllFacilityToUnits(facilitiesToUnits);
        rds.overwriteAllFacilityToUnitHistories(facilityToUnitsToBackup);
        rds.overwriteAllUnits(units);
        
        writeTime.setId(rds.getMaxWriteTimeId()+1);
        rds.addWriteTime(writeTime);
        
        /*
        //Get all the companies
        
        long id = 0;
        while((line=f.readLine())!=null)
        {
            Company c = new Company(line);
            c.setId(id++);
            c.setName(line);
            out.println(c);
            companies.add(c);
        }
        
        //Setup all the facilities
        Row facilityNamesRow = storageInfoSheet.getRow(0);
        
        int facilitiesIndex = 4;
        
        id = 0;
        for(int i = 0; i < 2; i++)
        {
            int curIndex = facilitiesIndex + (i*2);
            Cell c = facilityNamesRow.getCell(curIndex);
            String val = c.getStringCellValue();
            Facility facility = new Facility();
            facility.setName(val);
            facility.setId(id++);
            out.println("FACILITY"+facility);
            facilities.add(facility);
        }
        
        Facility extraSpace = new Facility();
        extraSpace.setName("ExtraSpace Storage");
        extraSpace.setId(id++);
        out.println("FACILITY"+extraSpace);
        facilities.add(extraSpace);
        
        facilitiesIndex = 28;
        for(int i = 0; i < 14; i++)
        {
            int curIndex = facilitiesIndex + (i*2);
            Cell c = facilityNamesRow.getCell(curIndex);
            String val = c.getStringCellValue();
            Facility facility = new Facility();
            facility.setName(val);
            facility.setId(id++);
            out.println("FACILITY" + facility);
            facilities.add(facility);
        }
        
        //Setup all the CompanyToFacility objects
        id = 0;
        for(int i = 0; i < facilities.size(); i++)
        {
            for(int j = 0; j < companies.size(); j++)
            {
                if(facilities.get(i).getName().contains(companies.get(j).getName()))
                {   
                    out.println("FOUND: " + facilities.get(i).getName());
                    CompanyToFacility ctf = new CompanyToFacility();
                    ctf.setCompanyId(companies.get(j).getId());
                    ctf.setFacilityId(facilities.get(i).getId());
                    ctf.setId(id++);
                    companiesToFacilities.add(ctf);
                    facilities.get(i).setCompanyId(companies.get(j).getId());
                    j = companies.size();
                }
                else if(facilities.get(i).getName().contains("Car Wash"))
                {
                    out.println("FOUND: " + facilities.get(i).getName());
                    CompanyToFacility ctf = new CompanyToFacility();
                    ctf.setCompanyId(6);
                    ctf.setFacilityId(facilities.get(i).getId());
                    ctf.setId(id++);
                    companiesToFacilities.add(ctf);
                    facilities.get(i).setCompanyId(6);
                    j = companies.size();
                }
                else if(facilities.get(i).getName().contains("Extra Space Self Storage"))
                {
                    out.println("FOUND: " + facilities.get(i).getName());
                    CompanyToFacility ctf = new CompanyToFacility();
                    ctf.setCompanyId(2);
                    ctf.setFacilityId(facilities.get(i).getId());
                    ctf.setId(id++);
                    companiesToFacilities.add(ctf);
                    facilities.get(i).setCompanyId(2);
                    j = companies.size();
                }
            }
        }
        
        //Setup all units
        id = 0;
        for(int row = 31; row < 92; row++)
        {
            Row r = storageInfoSheet.getRow(row);
            String unitName = r.getCell(0).getStringCellValue();
            out.println(unitName);
            BigDecimal width = new BigDecimal(unitName.substring(0, unitName.indexOf("'")));
            String depthName = unitName.substring(unitName.indexOf("'")+1);
            BigDecimal depth = new BigDecimal(depthName.substring(1, depthName.indexOf("'")));
            String type = r.getCell(2).getStringCellValue();
            
            Unit u = new Unit();
            u.setId(id++);
            u.setName(unitName);
            u.setType(type);
            u.setWidth(width);
            u.setDepth(depth);
            if(row >= 60)
                u.setFloor((int)Double.parseDouble(r.getCell(3).toString()));
            else
                u.setFloor(1);
            out.println(u);
            units.add(u);
        }
        
        //Setup all FacilityToUnit objects
        id = 0;
        String rateType = "standard";
        for(int row = 31; row < 92; row++)
        {
            Row r = storageInfoSheet.getRow(row);
            long unitId = units.get(row-31).getId();
            
            for(int i = 0; i < facilities.size(); i++)
            {
                Cell c;
                long facilityId = facilities.get(i).getId();
                int cellToGet;
                
                boolean isExtraSpace = false;
                if(i < 2)
                {
                    cellToGet = 4 + i*2;
                }
                else if(i > 2)
                {
                    cellToGet = 28 + (i-3)*2;
                }
                else
                {
                    isExtraSpace = true;
                    cellToGet = 8;
                    if(!(c=r.getCell(cellToGet)).toString().equals(""))
                    {
                        FacilityToUnit ftu = new FacilityToUnit();
                        ftu.setId(id++);
                        ftu.setFacilityId(facilityId);
                        ftu.setUnitId(unitId);
                        
                        BigDecimal rateAmount = new BigDecimal(c.toString());
                        
                        ftu.setRateAmount(rateAmount);
                        ftu.setRateType(rateType);
                        
                        ftu.setDateCreated(new Date());
                        
                        facilitiesToUnitsHistory.add(new FacilityToUnitHistory().createFromFacilityToUnit(ftu));
                        facilitiesToUnits.add(ftu);
                    }
                    cellToGet = 10;
                    rateType = "web";
                    if(!(c=r.getCell(cellToGet)).toString().equals(""))
                    {
                        FacilityToUnit ftu = new FacilityToUnit();
                        ftu.setId(id++);
                        ftu.setFacilityId(facilityId);
                        ftu.setUnitId(unitId);
                        
                        BigDecimal rateAmount = new BigDecimal(c.toString());
                        
                        ftu.setRateAmount(rateAmount);
                        ftu.setRateType(rateType);
                        
                        ftu.setDateCreated(new Date());
                        
                        facilitiesToUnitsHistory.add(new FacilityToUnitHistory().createFromFacilityToUnit(ftu));
                        facilitiesToUnits.add(ftu);
                    }
                    rateType = "standard";
                }
                if(!isExtraSpace)
                {
                    if(!(c=r.getCell(cellToGet)).toString().equals(""))
                    {
                        FacilityToUnit ftu = new FacilityToUnit();
                        ftu.setId(id++);
                        ftu.setFacilityId(facilityId);
                        ftu.setUnitId(unitId);
                        
                        BigDecimal rateAmount = new BigDecimal(c.toString());
                        
                        ftu.setRateAmount(rateAmount);
                        ftu.setRateType(rateType);
                        
                        ftu.setDateCreated(new Date());
                        
                        facilitiesToUnitsHistory.add(new FacilityToUnitHistory().createFromFacilityToUnit(ftu));
                        facilitiesToUnits.add(ftu);
                    }
                }
            }
        }
        
        long waitTime = 350;
        
        //Save all the objects to the AWS database
        RDSHandler rds = new RDSHandler();
        rds.resetTables();
        rds.incrementVersion();
        
        
        AllCompaniesObject o = new AllCompaniesObject();
        o.setId(0);
        String info = "";
        
        for(Company c : companies)
        {
            info += c;
        }
        
        
        /*for(Company c : companies)
        {
            dh.addCompany(c);
            out.println("Company " + c + " Added");
        }*/
        
        
        
        /*for(CompanyToFacility ctf : companiesToFacilities)
        {
            dh.addCompanyToFacility(ctf);
            out.println("CompanyToFacility " + ctf + " Added");
        }*/
        
        /*for(Facility facility : facilities)
        {
            dh.addFacility(facility);
            out.println("Facility " + facility + " Added");
        }*/
        
        
        
        //rds.batchSaveFacilityToUnitsHistory(facilitiesToUnitsHistory);
        /*for(FacilityToUnit ftu : facilitiesToUnits)
        {
            dh.addFacilityToUnit(ftu);
            out.println("FacilityToUnit " + ftu + " Added");
        }*/
        
        
        /*for(Unit u : units)
        {
            dh.addUnit(u);
            out.println("Unit " + u + " Added");
        }*/
        //dh.deleteFacilityToUnits();
        
        /*
        rds.batchSaveCompanies(companies);
        rds.batchSaveCompanyToFacilities(companiesToFacilities);
                rds.batchSaveFacilities(facilities);
        rds.batchSaveFacilityToUnits(facilitiesToUnits);
        rds.batchSaveUnits(units);    
        FacilityToUnitHistory example = new FacilityToUnitHistory();
        example.setId(1);
        example.setUnitId(0);
        example.setFacilityId(4);
        example.setDateCreated(new Date(1500000000000l));
        example.setRateAmount(new BigDecimal("300.0"));
        example.setRateType("standard");
        rds.addFacilityToUnitHistory(example);
        
        FacilityToUnitHistory example2 = new FacilityToUnitHistory();
        example2.setId(2);
        example2.setUnitId(0);
        example2.setFacilityId(4);
        example2.setDateCreated(new Date(1300000000000l));
        example2.setRateAmount(new BigDecimal("20.0"));
        example2.setRateType("standard");
        rds.addFacilityToUnitHistory(example2);
        */
        User user = new User();
        user.setId(0);
        user.setType("Admin");
        user.setFirstName("Shawn");
        user.setLastName("Sharp");
        user.setUsername("Admin");
        user.setPassword("test");
        user.setIsActive(true);
        user.setDateCreated(writingDate);
        user.setDateUpdated(writingDate);
        
        User user2 = new User();
        user2.setId(1);
        user2.setType("Standard");
        user2.setFirstName("Spencer");
        user2.setLastName("S");
        user2.setUsername("spencers");
        user2.setPassword("testPass");
        user2.setIsActive(true);
        user2.setDateCreated(writingDate);
        user2.setDateUpdated(writingDate);
        
        ArrayList<User> users = new ArrayList<User>();
        users.add(user); 
       users.add(user2);
        
        UserPreferences userPreferences = new UserPreferences();
        userPreferences.setUserId(0);
        userPreferences.setLandingPage("Unit Table");
        userPreferences.setLandingFacilityId(4);
        //rds.addUserPreferences(userPreferences);
        
        //rds.batchSaveUsers(users);
        
    }
}