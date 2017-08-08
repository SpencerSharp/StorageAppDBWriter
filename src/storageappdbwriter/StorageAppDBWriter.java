package storageappdbwriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class StorageAppDBWriter
{
    public static void main(String[] args) throws IOException
    {
        String fName = "161201 Self Storage Market Rent Comparison r12"; //CHANGE FILENAME HERE
        
        
        File myFile = new File("DataFiles/"+fName + ".xlsx");
        InputStream inp = new FileInputStream(myFile);
        XSSFWorkbook wb = new XSSFWorkbook(inp);

        //Access the match result sheet
        Sheet storageInfoSheet = wb.getSheetAt(0);
        
        ArrayList<Company> companies;
        ArrayList<CompanyToFacility> companiesToFacilities;
        ArrayList<Facility> facilities;
        ArrayList<FacilityToUnit> facilitiesToUnits;
        ArrayList<Unit> units;
        
        //Get all the companies
        File companiesFile = new File("DataFile/Companies.txt");
        BufferedReader f = new BufferedReader(new FileReader(companiesFile));
        
        
    }
}
