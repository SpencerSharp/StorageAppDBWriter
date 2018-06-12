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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class StorageAppDBWriter
{
    public static void main(String[] args) throws IOException, InterruptedException, SQLException
    {
        String fName = "161201 Self Storage Market Rent Comparison r12"; //CHANGE FILENAME HERE
        
        File myFile = new File("DataFiles/"+fName + ".xlsx");
        InputStream inp = new FileInputStream(myFile);
        XSSFWorkbook wb = new XSSFWorkbook(inp);

        //Access the match result sheet
        Sheet storageInfoSheet = wb.getSheetAt(0);
        
        ArrayList<Company> companies = new ArrayList<Company>();
        ArrayList<CompanyToFacility> companiesToFacilities = new ArrayList<CompanyToFacility>();
        ArrayList<Facility> facilities = new ArrayList<Facility>();
        ArrayList<FacilityToUnit> facilitiesToUnits = new ArrayList<FacilityToUnit>();
        ArrayList<FacilityToUnitHistory> facilitiesToUnitsHistory = new ArrayList<FacilityToUnitHistory>();
        ArrayList<Unit> units = new ArrayList<Unit>();
        
        //Get all the companies
        File companiesFile = new File("DataFiles/Companies.txt");
        BufferedReader f = new BufferedReader(new FileReader(companiesFile));
        
        String line;
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
        
        rds.batchSaveCompanies(companies);
        /*for(Company c : companies)
        {
            dh.addCompany(c);
            out.println("Company " + c + " Added");
        }*/
        
        rds.batchSaveCompanyToFacilities(companiesToFacilities);
        /*for(CompanyToFacility ctf : companiesToFacilities)
        {
            dh.addCompanyToFacility(ctf);
            out.println("CompanyToFacility " + ctf + " Added");
        }*/
        
        rds.batchSaveFacilities(facilities);
        /*for(Facility facility : facilities)
        {
            dh.addFacility(facility);
            out.println("Facility " + facility + " Added");
        }*/
        
        rds.batchSaveFacilityToUnits(facilitiesToUnits);
        
        //rds.batchSaveFacilityToUnitsHistory(facilitiesToUnitsHistory);
        /*for(FacilityToUnit ftu : facilitiesToUnits)
        {
            dh.addFacilityToUnit(ftu);
            out.println("FacilityToUnit " + ftu + " Added");
        }*/
        
        rds.batchSaveUnits(units);
        /*for(Unit u : units)
        {
            dh.addUnit(u);
            out.println("Unit " + u + " Added");
        }*/
        //dh.deleteFacilityToUnits();
        
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
    }
}