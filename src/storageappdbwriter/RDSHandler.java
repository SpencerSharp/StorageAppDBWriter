package storageappdbwriter;

import javax.xml.transform.Result;
import java.io.File;
import java.io.IOError;
import java.io.IOException;
import static java.lang.System.out;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by spencersharp on 6/11/18.
 */
public class RDSHandler
{
    Connection connection;

    private static Long getLong(ResultSet resultSet, String name) throws SQLException
    {
        if(resultSet.isBeforeFirst())
        {
            return resultSet.getLong(name);
        }
        return null;
    }









    public java.util.Date getDateFromSqlDate(java.sql.Date date)
    {
        java.util.Date javaDate = null;
        if (date != null) {
            javaDate = new Date(date.getTime());
        }
        return javaDate;
    }

    public java.sql.Date getSqlDateFromDate(java.util.Date date)
    {
        if(date == null)
            return null;
        java.sql.Date result = new java.sql.Date(date.getTime());
        return result;
    }

    public RDSHandler() {

        String username = "";
        String password = "";
        try {
            Scanner sc = new Scanner(new File("DataFiles/rdslogin.txt"));
            username = sc.nextLine();
            password = sc.nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("----MySQL JDBC Connection -------");

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found!");
            e.printStackTrace();
            return;
        }

        System.out.println("MySQL JDBC Driver found!");
        connection = null;

        try {
            connection = DriverManager.
                    getConnection("jdbc:mysql://" + "nitroxtech.c48qi7cc3kyh.us-west-1.rds.amazonaws.com" + ":" + "3306" + "/" + "StorageDBWebAppMainDatabase", username, password);
        } catch (SQLException e) {
            System.out.println("Connection Failed!:\n" + e.getMessage());
        }

        if (connection != null) {
            System.out.println("SUCCESS!");
        } else {
            System.out.println("Failed to make connection!");
        }

        //deleteTables(connection, "Companies CompaniesFacilities Facilities FacilitiesUnitsHistory FacilitiesUnits Units Version");
        /*
        createCompaniesTable(connection);
        createCompaniesToFacilitiesTable(connection);
        createFacilitiesTable(connection);
        createFacilityToUnitsTable(connection);
        createFacilityToUnitsHistoryTable(connection);
        createUnitsTable(connection);
        createValuesTable(connection);
        createVersionTable(connection);
        */

    }

    public void resetTables()
    {
        deleteTables(connection, "Companies CompaniesFacilities Facilities FacilitiesUnitsHistory FacilitiesUnits Units Version");

        createCompaniesTable(connection);
        createCompaniesToFacilitiesTable(connection);
        createFacilitiesTable(connection);
        createFacilityToUnitsTable(connection);
        createFacilityToUnitsHistoryTable(connection);
        createUnitsTable(connection);
        //createValuesTable(connection);
        createVersionTable(connection);
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

















    private static void createVersionTable(Connection conn) {
        Statement statement = null;
        try {
            statement = conn.createStatement();
            String sql =  "CREATE TABLE " + "Version" + "(" +
                    "id" + " BIGINT PRIMARY KEY, "
                    + "version" + " BIGINT" +")";
            out.println(sql);
            statement.executeUpdate(sql);
            System.out.println("Created Version table");
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException se2) {
            }// nothing we can do
        }//end try
    }

    private static void createCompaniesToFacilitiesTable(Connection conn) {
        Statement statement = null;
        try {
            statement = conn.createStatement();
            String sql =  "CREATE TABLE " + "CompaniesFacilities" + "(" +
                    "id" + " BIGINT PRIMARY KEY," +
                    "companyId" + " BIGINT," +
                    "facilityId" + " BIGINT" +")";
            out.println(sql);
            statement.executeUpdate(sql);
            System.out.println("Created CompaniesFacilities table");
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException se2) {
            }// nothing we can do
        }//end try
    }
    private static void createValuesTable(Connection conn) {
        Statement statement = null;
        try {
            statement = conn.createStatement();
            String sql =  "CREATE TABLE " + "MValues" + "(" +
                    "name" + " TEXT," +
                    "value" + " BIGINT" +")";
            statement.executeUpdate(sql);
            System.out.println("Created Values table");
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException se2) {
            }// nothing we can do
        }//end try
    }

    private static void createCompaniesTable(Connection conn) {
        Statement statement = null;

        try {
            statement = conn.createStatement();
            String sql =  "CREATE TABLE " + "Companies" + "(" + "id" +
                    " BIGINT PRIMARY KEY," + "name" + " TEXT, " + "website" +
                    " TEXT" + ")";
            statement.executeUpdate(sql);
            System.out.println("Created Companies table");
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException se2) {
            }// nothing we can do
        }//end try
    }

    private static void createFacilityToUnitsTable(Connection conn) {
        Statement statement = null;
        try {
            statement = conn.createStatement();
            String sql =  "CREATE TABLE " + "FacilitiesUnits" + "(" +
                    "id" + " BIGINT PRIMARY KEY," +
                    "facilityId" + " BIGINT," +
                    "unitId" + " BIGINT," +
                    "dateCreated" + " DATE," +
                    "rateAmount" + " DECIMAL," +
                    "rateType" + " TEXT" + ")";
            statement.executeUpdate(sql);
            System.out.println("Created FacilitiesUnits table");
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException se2) {
            }// nothing we can do
        }//end try
    }

    private static void createFacilityToUnitsHistoryTable(Connection conn) {
        Statement statement = null;
        try {
            statement = conn.createStatement();
            String sql =  "CREATE TABLE " + "FacilitiesUnitsHistory" + "(" +
                    "id" + " BIGINT PRIMARY KEY," +
                    "facilityId" + " BIGINT," +
                    "unitId" + " BIGINT," +
                    "dateCreated" + " DATE," +
                    "rateAmount" + " DECIMAL," +
                    "rateType" + " TEXT" + ")";
            statement.executeUpdate(sql);
            System.out.println("Created FacilitiesUnitsHistory table");
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException se2) {
            }// nothing we can do
        }//end try
    }

    private static void createUnitsTable(Connection conn) {
        Statement statement = null;

        try {
            statement = conn.createStatement();
            String sql =  "CREATE TABLE " + "Units" + "(" +
                    "id" + " BIGINT PRIMARY KEY," +
                    "name" + " TEXT," +
                    "type" + " TEXT," +
                    "width" + " DECIMAL," +
                    "depth" + " DECIMAL," +
                    "height" + " DECIMAL," +
                    "floor" + " INT," +
                    "doorHeight" + " DECIMAL," +
                    "doorWidth" + " DECIMAL" + ")";
            statement.executeUpdate(sql);
            System.out.println("Created Units table");
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException se2) {
            }// nothing we can do
        }//end try
    }

    private static void createFacilitiesTable(Connection conn) {
        Statement statement = null;
        try {
            statement = conn.createStatement();
            String sql =  "CREATE TABLE " + "Facilities" + "(" +
                    "id" + " BIGINT PRIMARY KEY," +
                    "name" + " TEXT," +
                    "companyId" + " BIGINT," +
                    "streetAddress1" + " TEXT," +
                    "streetAddress2" + " TEXT," +
                    "city" + " TEXT," +
                    "state" + " TEXT," +
                    "zip" + " TEXT," +
                    "country" + " TEXT," +
                    "website" + " TEXT," +
                    "setupFee" + " DECIMAL," +
                    "percentFull" + " DECIMAL," +
                    "hasRetailStore" + " BIT," +
                    "hasInsurance" + " BIT," +
                    "hasOnlineBillPay" + " BIT," +
                    "hasWineStorage" + " BIT," +
                    "hasKiosk" + " BIT," +
                    "hasOnsiteManagement" + " BIT," +
                    "hasCameras" + " BIT," +
                    "hasVehicleParking" + " BIT," +
                    "hasCutLocks" + " BIT," +
                    "hasOnsiteShipping" + " BIT," +
                    "hasAutopay" + " BIT," +
                    "hasOnsiteCarts" + " BIT," +
                    "hasParabolicMirrors" + " BIT," +
                    "hasMotionLights" + " BIT," +
                    "hasElectronicLease" + " BIT," +
                    "hasPaperlessBilling" + " BIT," +
                    "mondayOpen" + " DATE," +
                    "mondayClose" + " DATE," +
                    "tuesdayOpen" + " DATE," +
                    "tuesdayClose" + " DATE," +
                    "wednesdayOpen" + " DATE," +
                    "wednesdayClose" + " DATE," +
                    "thursdayOpen" + " DATE," +
                    "thursdayClose" + " DATE," +
                    "fridayOpen" + " DATE," +
                    "fridayClose" + " DATE," +
                    "saturdayOpen" + " DATE," +
                    "saturdayClose" + " DATE," +
                    "sundayOpen" + " DATE," +
                    "sundayClose" + " DATE," +
                    "rating" + " TEXT," +
                    "promotions" + " TEXT" + ")";
            statement.executeUpdate(sql);
            System.out.println("Created Facilities table");
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException se2) {
            }// nothing we can do
        }//end try
    }











    private static void deleteTables(Connection conn, String tbls) {
        Statement statement = null;
        String[] tables = tbls.split(" ");

        try {
            statement = conn.createStatement();
            String sql = "";
            for(int i = 0; i < tables.length; i++)
            {
                if(i == 0)
                    sql += "DROP TABLE IF EXISTS";
                else
                    sql += ",";
                sql += " " + tables[i];
            }
            statement.executeUpdate(sql);
            System.out.println("Deleted table(s) " + tables.toString());
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException se2) {
            }// nothing we can do
        }//end try
    }















    public ResultSet executeQuery(String query) throws SQLException
    {
        System.out.println("QUERY: " + query);
        Statement statement = connection.createStatement();
        if(query.contains("INSERT") || query.contains("DELETE"))
        {
            statement.executeUpdate(query);
            return null;
        }
        ResultSet resultSet = statement.executeQuery(query);
        return resultSet;
    }









    /*
     *      Methods that create from result sets
     */

    private Company createCompanyFromResultSet(ResultSet resultSet) throws SQLException
    {
        Company company = new Company();
        company.setId(resultSet.getLong("id"));
        company.setName(resultSet.getString("name"));
        return company;
    }

    private CompanyToFacility createCompanyToFacilityFromResultSet(ResultSet resultSet) throws SQLException
    {
        CompanyToFacility companyToFacility = new CompanyToFacility();
        companyToFacility.setId(resultSet.getLong("id"));
        companyToFacility.setCompanyId(resultSet.getLong("companyId"));
        companyToFacility.setFacilityId(resultSet.getLong("facilityId"));
        return companyToFacility;
    }

    private Facility createFacilityFromResultSet(ResultSet resultSet) throws SQLException
    {
        Facility facility = new Facility();
        facility.setId(resultSet.getLong("id"));
        facility.setName(resultSet.getString("name"));
        facility.setCompanyId(resultSet.getLong("companyId"));
        facility.setStreetAddress1(resultSet.getString("streetAddress1"));
        facility.setStreetAddress2(resultSet.getString("streetAddress2"));
        facility.setCity(resultSet.getString("city"));
        facility.setState(resultSet.getString("state"));
        facility.setZip(resultSet.getString("zip"));
        facility.setCountry(resultSet.getString("country"));
        facility.setWebsite(resultSet.getString("website"));
        facility.setSetupFee(resultSet.getBigDecimal("setupFee"));
        facility.setPercentFull(resultSet.getBigDecimal("percentFull"));
        facility.setHasRetailStore(resultSet.getBoolean("hasRetailStore"));
        facility.setHasInsurance(resultSet.getBoolean("hasInsurance"));
        facility.setHasOnlineBillPay(resultSet.getBoolean("hasOnlineBillPay"));
        facility.setHasWineStorage(resultSet.getBoolean("hasWineStorage"));
        facility.setHasKiosk(resultSet.getBoolean("hasKiosk"));
        facility.setHasOnsiteManagement(resultSet.getBoolean("hasOnsiteManagement"));
        facility.setHasCameras(resultSet.getBoolean("hasCameras"));
        facility.setHasVehicleParking(resultSet.getBoolean("hasVehicleParking"));
        facility.setHasCutLocks(resultSet.getBoolean("hasCutLocks"));
        facility.setHasOnsiteShipping(resultSet.getBoolean("hasOnsiteShipping"));
        facility.setHasAutopay(resultSet.getBoolean("hasAutopay"));
        facility.setHasOnsiteCarts(resultSet.getBoolean("hasOnsiteCarts"));
        facility.setHasParabolicMirrors(resultSet.getBoolean("hasParabolicMirrors"));
        facility.setHasMotionLights(resultSet.getBoolean("hasMotionLights"));
        facility.setHasElectronicLease(resultSet.getBoolean("hasElectronicLease"));
        facility.setHasPaperlessBilling(resultSet.getBoolean("hasPaperlessBilling"));
        facility.setMondayOpen(resultSet.getTime("mondayOpen"));
        facility.setMondayClose(resultSet.getTime("mondayClose"));
        facility.setTuesdayOpen(resultSet.getTime("tuesdayOpen"));
        facility.setTuesdayClose(resultSet.getTime("tuesdayClose"));
        facility.setWednesdayOpen(resultSet.getTime("wednesdayOpen"));
        facility.setWednesdayClose(resultSet.getTime("wednesdayClose"));
        facility.setThursdayOpen(resultSet.getTime("thursdayOpen"));
        facility.setThursdayClose(resultSet.getTime("thursdayClose"));
        facility.setFridayOpen(resultSet.getTime("fridayOpen"));
        facility.setFridayClose(resultSet.getTime("fridayClose"));
        facility.setSaturdayOpen(resultSet.getTime("saturdayOpen"));
        facility.setSaturdayClose(resultSet.getTime("saturdayClose"));
        facility.setSundayOpen(resultSet.getTime("sundayOpen"));
        facility.setSundayClose(resultSet.getTime("sundayClose"));
        facility.setRating(resultSet.getString("rating"));
        facility.setPromotions(resultSet.getString("promotions"));
        return facility;
    }

    private FacilityToUnit createFacilityToUnitFromResultSet(ResultSet resultSet) throws SQLException
    {
        FacilityToUnit facilityToUnit = new FacilityToUnit();
        facilityToUnit.setId(resultSet.getLong("id"));
        facilityToUnit.setFacilityId(resultSet.getLong("facilityId"));
        facilityToUnit.setUnitId(resultSet.getLong("unitId"));
        facilityToUnit.setDateCreated(getDateFromSqlDate(resultSet.getDate("dateCreated")));
        facilityToUnit.setRateAmount(resultSet.getBigDecimal("rateAmount"));
        facilityToUnit.setRateType(resultSet.getString("rateType"));
        return facilityToUnit;
    }

    private FacilityToUnitHistory createFacilityToUnitHistoryFromResultSet(ResultSet resultSet) throws SQLException
    {
        FacilityToUnitHistory facilityToUnitHistory = new FacilityToUnitHistory();
        facilityToUnitHistory.setId(resultSet.getLong("id"));
        facilityToUnitHistory.setFacilityId(resultSet.getLong("facilityId"));
        facilityToUnitHistory.setUnitId(resultSet.getLong("unitId"));
        facilityToUnitHistory.setDateCreated(getDateFromSqlDate(resultSet.getDate("dateCreated")));
        facilityToUnitHistory.setRateAmount(resultSet.getBigDecimal("rateAmount"));
        facilityToUnitHistory.setRateType(resultSet.getString("rateType"));
        return facilityToUnitHistory;
    }

    private Unit createUnitFromResultSet(ResultSet resultSet) throws SQLException
    {
        Unit unit = new Unit();
        unit.setId(resultSet.getLong("id"));
        unit.setName(resultSet.getString("name"));
        unit.setType(resultSet.getString("type"));
        unit.setWidth(resultSet.getBigDecimal("width"));
        unit.setDepth(resultSet.getBigDecimal("depth"));
        unit.setHeight(resultSet.getBigDecimal("height"));
        unit.setFloor(resultSet.getInt("floor"));
        unit.setDoorHeight(resultSet.getBigDecimal("doorHeight"));
        unit.setDoorWidth(resultSet.getBigDecimal("doorWidth"));
        return unit;
    }





    private String buildValuesOfCompanyInsertQuery(Company company)
    {
        String result = "(" + company.getId() + ", ";
        result += "'" + company.getName() + "', ";
        result += "" + company.getWebsite() + ")";

        return result;
    }

    private String buildCompanyInsertQuery(Company company)
    {
        String query = "INSERT INTO Companies VALUES" + buildValuesOfCompanyInsertQuery(company);
        return query;
    }

    private String buildValuesOfCompanyToFacilityInsertQuery(CompanyToFacility companyToFacility)
    {
        String result = "(" + companyToFacility.getId() + ", ";
        result += companyToFacility.getCompanyId() + ", ";
        result += companyToFacility.getFacilityId() + ")";

        return result;
    }

    private String buildCompanyToFacilityInsertQuery(CompanyToFacility companyToFacility)
    {
        String query = "INSERT INTO CompaniesFacilities VALUES" + buildValuesOfCompanyToFacilityInsertQuery(companyToFacility);
        return query;
    }

    private String buildValuesOfFacilityInsertQuery(Facility facility)
    {
        String result = "(" + facility.getId() + ", ";
        result += "'" + facility.getName() + "', ";
        result += "'" + facility.getCompanyId() + "', ";
        result += "'" + facility.getStreetAddress1() + "', ";
        result += "'" + facility.getStreetAddress2() + "', ";
        result += "'" + facility.getCity() + "', ";
        result += "'" + facility.getState() + "', ";
        result += "'" + facility.getZip() + "', ";
        result += "'" + facility.getCountry() + "', ";
        result += "'" + facility.getWebsite() + "', ";
        result += "" + facility.getSetupFee() + ", ";
        result += "" + facility.getPercentFull() + ", ";
        result += "" + facility.hasRetailStore() + ", ";
        result += "" + facility.hasInsurance() + ", ";
        result += "" + facility.hasOnlineBillPay() + ", ";
        result += "" + facility.hasWineStorage() + ", ";
        result += "" + facility.hasKiosk() + ", ";
        result += "" + facility.hasOnsiteManagement() + ", ";
        result += "" + facility.hasCameras() + ", ";
        result += "" + facility.hasVehicleParking() + ", ";
        result += "" + facility.hasCutLocks() + ", ";
        result += "" + facility.hasOnsiteShipping() + ", ";
        result += "" + facility.hasAutopay() + ", ";
        result += "" + facility.hasOnsiteCarts() + ", ";
        result += "" + facility.hasParabolicMirrors() + ", ";
        result += "" + facility.hasMotionLights() + ", ";
        result += "" + facility.hasElectronicLease() + ", ";
        result += "" + facility.hasPaperlessBilling() + ", ";
        result += "" + facility.getMondayOpen() + ", ";
        result += "" + facility.getMondayClose() + ", ";
        result += "" + facility.getTuesdayOpen() + ", ";
        result += "" + facility.getTuesdayClose() + ", ";
        result += "" + facility.getWednesdayOpen() + ", ";
        result += "" + facility.getWednesdayClose() + ", ";
        result += "" + facility.getThursdayOpen() + ", ";
        result += "" + facility.getThursdayClose() + ", ";
        result += "" + facility.getFridayOpen() + ", ";
        result += "" + facility.getFridayClose() + ", ";
        result += "" + facility.getSaturdayOpen() + ", ";
        result += "" + facility.getSaturdayClose() + ", ";
        result += "" + facility.getSundayOpen() + ", ";
        result += "" + facility.getSundayClose() + ", ";
        result += "'" + facility.getRating() + "', ";
        result += "'" + facility.getPromotions() + "')";

        return result;
    }

    private String buildFacilityInsertQuery(Facility facility)
    {
        String query = "INSERT INTO Facilities VALUES" + buildValuesOfFacilityInsertQuery(facility);
        return query;
    }

    private String buildValuesOfFacilityToUnitInsertQuery(FacilityToUnit facilityToUnit)
    {
        String result = "(" + facilityToUnit.getId() + ", ";
        result += "" + facilityToUnit.getFacilityId() + ", ";
        result += "" + facilityToUnit.getUnitId() + ", ";
        java.sql.Date date = getSqlDateFromDate(facilityToUnit.getDateCreated());
        if(date == null)
        {
            result += "null, ";
        }
        else
        {
            result += "'" + date + "', ";
        }
        result += "" + facilityToUnit.getRateAmount() + ", ";
        result += "'" + facilityToUnit.getRateType() + "')";

        return result;
    }

    private String buildFacilityToUnitInsertQuery(FacilityToUnit facilityToUnit)
    {
        String query = "INSERT INTO FacilitiesUnits VALUES" + buildValuesOfFacilityToUnitInsertQuery(facilityToUnit);
        return query;
    }

    private String buildValuesOfFacilityToUnitHistoryInsertQuery(FacilityToUnitHistory facilityToUnitHistory)
    {
        String result = "(" + facilityToUnitHistory.getId() + ", ";
        result += "" + facilityToUnitHistory.getFacilityId() + ", ";
        result += "" + facilityToUnitHistory.getUnitId() + ", ";
        java.sql.Date date = getSqlDateFromDate(facilityToUnitHistory.getDateCreated());
        if(date == null)
        {
            result += "null, ";
        }
        else
        {
            result += "'" + date + "', ";
        }
        result += "" + facilityToUnitHistory.getRateAmount() + ", ";
        result += "'" + facilityToUnitHistory.getRateType() + "')";

        return result;
    }

    private String buildFacilityToUnitHistoryInsertQuery(FacilityToUnitHistory facilityToUnitHistory)
    {
        String query = "INSERT INTO FacilitiesUnitsHistory VALUES" + buildValuesOfFacilityToUnitHistoryInsertQuery(facilityToUnitHistory);
        return query;
    }

    private String buildValuesOfUnitInsertQuery(Unit unit)
    {
        String result = "(" + unit.getId() + ", ";
        int indexOfQuote = unit.getName().indexOf("'");
        String name = unit.getName().substring(0, indexOfQuote) + "'" + unit.getName().substring(indexOfQuote);
        result += "'" + name + "'', ";
        result += "'" + unit.getType() + "', ";
        result += "" + unit.getWidth() + ", ";
        result += "" + unit.getDepth() + ", ";
        result += "" + unit.getHeight() + ", ";
        result += "" + unit.getFloor() + ", ";
        result += "" + unit.getDoorHeight() + ", ";
        result += "" + unit.getDoorWidth() + ") ";

        return result;

    }

    private String buildUnitInsertQuery(Unit unit)
    {
        String query = "INSERT INTO Units VALUES" + buildValuesOfUnitInsertQuery(unit);
        return query;
    }













    /*
     *      Version commands
     */

    public void incrementVersion() throws SQLException {
        String query = "SELECT * FROM Version";
        ResultSet resultSet = executeQuery(query);
        resultSet.next();
        Long val = getLong(resultSet, "version");
        if(val == null)
            val = new Long("1");

        query = "INSERT INTO Version VALUES(1, " + ++val + ")";
        executeQuery(query);
    }

    public long getVersion() throws SQLException
    {
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM Version";
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        long val = resultSet.getLong("val");

        return val;
    }












    /*
     *      Simple adds
     */

    public void addCompany(Company c) throws SQLException
    {
        String query = buildCompanyInsertQuery(c);
        executeQuery(query);
    }

    public void addCompanyToFacility(CompanyToFacility companyToFacility) throws SQLException
    {
        String query = buildCompanyToFacilityInsertQuery(companyToFacility);
        executeQuery(query);
    }

    public void addFacility(Facility f) throws SQLException
    {
        String query = buildFacilityInsertQuery(f);
        executeQuery(query);

    }

    public void addFacilityToUnit(FacilityToUnit facilityToUnit) throws SQLException
    {
        String query = buildFacilityToUnitInsertQuery(facilityToUnit);
        executeQuery(query);
    }

    public void addFacilityToUnitHistory(FacilityToUnitHistory facilityToUnitHistory) throws SQLException
    {
        String query = buildFacilityToUnitHistoryInsertQuery(facilityToUnitHistory);
        executeQuery(query);
    }

    public void addUnit(Unit u) throws SQLException
    {
        String query = buildUnitInsertQuery(u);
        executeQuery(query);
    }






    public Company getCompanyFromId(long id) throws SQLException
    {
        String query = "SELECT * FROM Companies WHERE id=" + id;
        ResultSet resultSet = executeQuery(query);
        resultSet.next();
        return createCompanyFromResultSet(resultSet);
    }

    public ArrayList<Facility> getFacilitiesFromCompanyId(long companyId) throws SQLException
    {
        String query = "SELECT * FROM Facilities WHERE companyId=" + companyId;
        ResultSet resultSet = executeQuery(query);
        ArrayList<Facility> result = new ArrayList<Facility>();
        while(resultSet.next())
        {
            result.add(createFacilityFromResultSet(resultSet));
        }
        return result;
    }

    public Company getCompanyFromName(String name) throws SQLException
    {
        String query = "SELECT * FROM Companies WHERE name='" + name + "'";
        ResultSet resultSet = executeQuery(query);
        resultSet.next();
        return createCompanyFromResultSet(resultSet);
    }

    public Facility getFacilityFromFacilityName(String name) throws SQLException
    {
        String query = "SELECT * FROM Facilities WHERE name='" + name + "'";
        ResultSet resultSet = executeQuery(query);
        resultSet.next();
        return createFacilityFromResultSet(resultSet);
    }

    public Company getCompanyFromFacilityId(long facilityId) throws SQLException
    {
        String query = "SELECT * FROM Facilities WHERE id=" + facilityId;
        ResultSet resultSet = executeQuery(query);
        resultSet.next();
        Facility facility = createFacilityFromResultSet(resultSet);
        long companyId = facility.getCompanyId();
        query = "SELECT * FROM Companies WHERE id=" + companyId;
        resultSet = executeQuery(query);
        resultSet.next();
        return createCompanyFromResultSet(resultSet);
    }

    public ArrayList<Unit> getUnitsFromFacilityName(String name) throws SQLException
    {
        String query = "SELECT * FROM Facilities WHERE name='" + name + "'";
        ResultSet resultSet = executeQuery(query);
        resultSet.next();
        Facility facility = createFacilityFromResultSet(resultSet);
        long facilityId = facility.getId();

        query = "SELECT * FROM FacilitiesUnits WHERE facilityId=" + facilityId;
        resultSet = executeQuery(query);
        ArrayList<FacilityToUnit> results = new ArrayList<FacilityToUnit>();
        while(resultSet.next())
        {
            results.add(createFacilityToUnitFromResultSet(resultSet));
        }

        query = "SELECT * FROM Units WHERE id IN (";
        for(int i = 0; i < results.size(); i++)
        {
            query += results.get(i).getUnitId();
            if(i != results.size() - 1)
            {
                query += ", ";
            }
            else
            {
                query += ")";
            }
        }
        resultSet = executeQuery(query);
        ArrayList<Unit> units = new ArrayList<Unit>();
        while(resultSet.next())
        {
            units.add(createUnitFromResultSet(resultSet));
        }

        return units;
    }

    public ArrayList<FacilityToUnit> getFacilityToUnitsFromFacilityId(long facilityId) throws SQLException
    {
        String query = "SELECT * FROM FacilitiesUnits WHERE facilityId=" + facilityId;
        ResultSet resultSet = executeQuery(query);

        ArrayList<FacilityToUnit> facilityToUnits = new ArrayList<FacilityToUnit>();
        while(resultSet.next())
        {
            facilityToUnits.add(createFacilityToUnitFromResultSet(resultSet));
        }
        return facilityToUnits;
    }

    public ArrayList<FacilityToUnitHistory> getFacilityToUnitHistoryFromFacilityId(long facilityId) throws SQLException
    {
        String query = "SELECT * FROM FacilitiesUnitsHistory WHERE facilityId=" + facilityId;
        ResultSet resultSet = executeQuery(query);

        ArrayList<FacilityToUnitHistory> facilityToUnitsHistory = new ArrayList<FacilityToUnitHistory>();
        while(resultSet.next())
        {
            facilityToUnitsHistory.add(createFacilityToUnitHistoryFromResultSet(resultSet));
        }
        return facilityToUnitsHistory;
    }

    public ArrayList<Company> getCompaniesFromCompanyIds(ArrayList<Long> companyIds) throws SQLException
    {
        String query = "SELECT * FROM Companies WHERE id IN (";
        for(int i = 0; i < companyIds.size(); i++)
        {
            query += companyIds.get(i);
            if(i != companyIds.size() - 1)
            {
                query += ", ";
            }
            else
            {
                query += ")";
            }
        }
        ResultSet resultSet = executeQuery(query);

        ArrayList<Company> companies = new ArrayList<Company>();
        while(resultSet.next())
        {
            companies.add(createCompanyFromResultSet(resultSet));
        }

        return companies;
    }

    public ArrayList<JavaLocalGrailsUnit> getUnitsFromFacilityIds(ArrayList<Long> facilityIds) throws SQLException
    {
        //Search FacilitiesUnits for facilityIds
        String query = "SELECT * FROM FacilitiesUnits WHERE facilityId IN (";
        for(int i = 0; i < facilityIds.size(); i++)
        {
            query += facilityIds.get(i);
            if(i != facilityIds.size() - 1)
            {
                query += ", ";
            }
            else
            {
                query += ")";
            }
        }
        ResultSet resultSet = executeQuery(query);
        ArrayList<FacilityToUnit> facilityToUnits = new ArrayList<FacilityToUnit>();
        while(resultSet.next())
        {
            facilityToUnits.add(createFacilityToUnitFromResultSet(resultSet));
        }

        //Search units for unitIds of the FacilityToUnit objects
        query = "SELECT * FROM Units WHERE id IN (";
        for(int i = 0; i < facilityToUnits.size(); i++)
        {
            query += facilityToUnits.get(i).getUnitId();
            if(i != facilityToUnits.size() - 1)
            {
                query += ", ";
            }
            else
            {
                query += ")";
            }
        }
        resultSet = executeQuery(query);
        ArrayList<Unit> units = new ArrayList<Unit>();
        while(resultSet.next())
        {
            units.add(createUnitFromResultSet(resultSet));
        }

        //If unitIds match, you put them into a JavaLocalGrailsUnit
        ArrayList<JavaLocalGrailsUnit> result = new ArrayList<JavaLocalGrailsUnit>();
        for(FacilityToUnit facilityToUnit : facilityToUnits)
        {
            for(Unit unit: units)
            {
                if(unit.getId() == facilityToUnit.getUnitId())
                {
                    result.add(new JavaLocalGrailsUnit(unit.getId(), unit.getName(), unit.getWidth(), unit.getDepth(),
                            unit.getHeight(), unit.getType(), unit.getFloor(), facilityToUnit.getRateAmount(),
                            facilityToUnit.getFacilityId(), facilityToUnit.getRateType(), facilityToUnit.getDateCreated()));
                }
            }
        }
        return result;
    }

    //Gets all the Units that match in name, floor, type
    public ArrayList<Unit> getUnitsWithInfo(ArrayList<JavaLocalGrailsUnit> list) throws SQLException
    {
        String query = "SELECT * FROM Units WHERE ";
        for(int i = 0; i < list.size(); i++)
        {
            JavaLocalGrailsUnit javaLocalGrailsUnit = list.get(i);
            int indexOfQuote = javaLocalGrailsUnit.name.indexOf("'");
            String name = javaLocalGrailsUnit.name.substring(0, indexOfQuote) + "'" + javaLocalGrailsUnit.name.substring(indexOfQuote);
            query += "name='" + name + "'' AND floor=" + javaLocalGrailsUnit.floor + " AND type='" + javaLocalGrailsUnit.type + "'";
            if(i != list.size() - 1)
            {
                query += " OR ";
            }
        }
        ArrayList<Unit> units = new ArrayList<Unit>();
        ResultSet resultSet = executeQuery(query);
        while(resultSet.next())
        {
            units.add(createUnitFromResultSet(resultSet));
        }
        return units;
    }

    public ArrayList<FacilityToUnitHistory> getFacilityToUnitsHistoryWithInfo(ArrayList<JavaLocalGrailsUnit> list) throws SQLException
    {
        String query = "SELECT * FROM FacilitiesUnitsHistory WHERE ";
        for(int i = 0; i < list.size(); i++)
        {
            JavaLocalGrailsUnit javaLocalGrailsUnit = list.get(i);
            query += "facilityId=" + javaLocalGrailsUnit.facilityId + " AND unitId=" + javaLocalGrailsUnit.id + " AND rateType='" + javaLocalGrailsUnit.rateType + "'";
            if(i != list.size() - 1)
            {
                query += " OR ";
            }
        }
        ArrayList<FacilityToUnitHistory> facilityToUnitHistorys = new ArrayList<FacilityToUnitHistory>();
        ResultSet resultSet = executeQuery(query);
        while(resultSet.next())
        {
            facilityToUnitHistorys.add(createFacilityToUnitHistoryFromResultSet(resultSet));
        }
        return facilityToUnitHistorys;
    }

    public Facility getFacilityFromId(long facilityId) throws SQLException
    {
        String query = "SELECT * FROM Facilities WHERE id=" + facilityId;
        ResultSet resultSet = executeQuery(query);
        resultSet.next();
        return createFacilityFromResultSet(resultSet);
    }

    public ArrayList<FacilityToUnit> getFacilityToUnitsFromFacilityIdAndIdsToExclude(long facilityId, ArrayList<Long> idsToExclude) throws SQLException
    {
        String query = "SELECT * FROM FacilitiesUnits WHERE facilityId=" + facilityId;
        for(Long idToExclude: idsToExclude)
        {
            query += " AND unitId !=" + idToExclude;
        }
        ArrayList<FacilityToUnit> facilityToUnits = new ArrayList<FacilityToUnit>();
        ResultSet resultSet = executeQuery(query);
        while(resultSet.next())
        {
            facilityToUnits.add(createFacilityToUnitFromResultSet(resultSet));
        }
        return facilityToUnits;
    }

    public FacilityToUnit getFacilityToUnitByFacilityIdAndUnitId(long facilityId, long unitId) throws SQLException
    {
        String query = "SELECT * FROM FacilitiesUnits WHERE facilityId=" + facilityId + " AND unitId=" + unitId;
        ResultSet resultSet = executeQuery(query);
        resultSet.next();
        return createFacilityToUnitFromResultSet(resultSet);
    }

    public ArrayList<FacilityToUnitHistory> getFacilityToUnitsHistoryFromFacilityIdAndUnitId(long facilityId, long unitId) throws SQLException
    {
        String query = "SELECT * FROM FacilitiesUnitsHistory WHERE facilityId=" + facilityId + " AND unitId=" + unitId;
        ResultSet resultSet = executeQuery(query);
        ArrayList<FacilityToUnitHistory> facilityToUnitHistorys = new ArrayList<FacilityToUnitHistory>();
        while(resultSet.next())
        {
            facilityToUnitHistorys.add(createFacilityToUnitHistoryFromResultSet(resultSet));
        }
        return facilityToUnitHistorys;
    }

    public ArrayList<Facility> getFacilitiesFromFacilityIds(ArrayList<Long> facilityIds) throws SQLException
    {
        String query = "SELECT * FROM Facilities WHERE id IN (";
        for(int i = 0; i < facilityIds.size(); i++)
        {
            query += facilityIds.get(i);
            if(i != facilityIds.size() - 1)
            {
                query += ", ";
            }
            else
            {
                query += ")";
            }
        }
        ResultSet resultSet = executeQuery(query);
        ArrayList<Facility> facilities = new ArrayList<Facility>();
        while(resultSet.next())
        {
            facilities.add(createFacilityFromResultSet(resultSet));
        }
        return facilities;
    }










    /*
     *      Batch save commands.
     */
    public void batchSaveCompanies(ArrayList<Company> companies) throws SQLException
    {
        String query = "INSERT INTO Companies VALUES";
        for(int i = 0; i < companies.size(); i++)
        {
            Company company = companies.get(i);
            query += buildValuesOfCompanyInsertQuery(company);
            if(i != companies.size() - 1)
            {
                query += ", ";
            }
            else
            {

            }
        }
        out.println(query);
        executeQuery(query);
    }

    public void batchSaveCompanyToFacilities(ArrayList<CompanyToFacility> companyToFacilities) throws SQLException
    {
        String query = "INSERT INTO CompaniesFacilities VALUES";
        for(int i = 0; i < companyToFacilities.size(); i++)
        {
            CompanyToFacility companyToFacility = companyToFacilities.get(i);
            query += buildValuesOfCompanyToFacilityInsertQuery(companyToFacility);
            if(i != companyToFacilities.size() - 1)
            {
                query += ",";
            }
            else
            {

            }
        }
        executeQuery(query);
    }

    public void batchSaveFacilities(ArrayList<Facility> facilities) throws SQLException
    {
        String query = "INSERT INTO Facilities VALUES";
        for(int i = 0; i < facilities.size(); i++)
        {
            Facility facility = facilities.get(i);
            query += buildValuesOfFacilityInsertQuery(facility);
            if(i != facilities.size() - 1)
            {
                query += ",";
            }
            else
            {

            }
        }
        out.println(query);
        executeQuery(query);
    }

    public void batchSaveFacilityToUnits(ArrayList<FacilityToUnit> facilityToUnits) throws SQLException
    {
        if(facilityToUnits.size() > 0)
        {
            String query = "INSERT INTO FacilitiesUnits VALUES";
            for(int i = 0; i < facilityToUnits.size(); i++)
            {
                FacilityToUnit facilityToUnit = facilityToUnits.get(i);
                out.println(facilityToUnit.getId());
                if(facilityToUnit.getFacilityId() == 4)
                {
                    out.println("FOUND ONE");
                    out.println(facilityToUnit);
                }


                query += buildValuesOfFacilityToUnitInsertQuery(facilityToUnit);
                if(i != facilityToUnits.size() - 1)
                {
                    query += ",";
                }
                else
                {

                }
            }
            executeQuery(query);
        }
    }

    public void batchSaveFacilityToUnitsHistory(ArrayList<FacilityToUnitHistory> facilityToUnitHistories) throws SQLException
    {
        if(facilityToUnitHistories.size() > 0)
        {
            String query = "INSERT INTO FacilitiesUnitsHistory VALUES";
            for(int i = 0; i < facilityToUnitHistories.size(); i++)
            {
                FacilityToUnitHistory facilityToUnitHistory = facilityToUnitHistories.get(i);
                query += buildValuesOfFacilityToUnitHistoryInsertQuery(facilityToUnitHistory);
                if(i != facilityToUnitHistories.size() - 1)
                {
                    query += ",";
                }
                else
                {

                }
            }
            executeQuery(query);
        }
    }

    public void batchSaveUnits(ArrayList<Unit> units) throws SQLException
    {
        if(units.size() > 0)
        {
            String query = "INSERT INTO Units VALUES";
            for(int i = 0; i < units.size(); i++)
            {
                Unit unit = units.get(i);
                query += buildValuesOfUnitInsertQuery(unit);
                if(i != units.size() - 1)
                {
                    query += ",";
                }
                else
                {
                }
            }
            executeQuery(query);
        }
    }

/*    public void batchSaveValues(ArrayList<Value> values) throws SQLException
    {
        String query = "INSERT INTO Values VALUES";
        for(int i = 0; i < values.size(); i++)
        {
            Value value = values.get(i);
            query += buildValuesOfValue(value);
            if(i != values.size() - 1)
            {
                query += ",";
            }
            else
            {
                query += ")";
            }
        }
        executeQuery(query);
    }*/




    /*
     *      Batch delete commands
     */

    public void batchDeleteCompanies(ArrayList<Company> companies) throws SQLException {
        for(int i = 0; i < companies.size(); i++) {
            String query = "DELETE FROM Companies WHERE id = " + companies.get(i).getId() + ";";
            executeQuery(query);
        }
    }

    public void batchDeleteCompanyToFacilities(ArrayList<CompanyToFacility> companyToFacilities) throws SQLException {
        for(int i = 0; i < companyToFacilities.size(); i++) {
            String query = "DELETE FROM CompaniesFacilities WHERE id = " + companyToFacilities.get(i).getId() + ";";
            executeQuery(query);
        }
    }

    public void batchDeleteFacilities(ArrayList<Facility> facilities) throws SQLException {
        for(int i = 0; i < facilities.size(); i++) {
            String query = "DELETE FROM Facilities WHERE id = " + facilities.get(i).getId() + ";";
            executeQuery(query);
        }

    }

    public void batchDeleteFacilityToUnits(ArrayList<FacilityToUnit> facilityToUnits) throws SQLException {
        for(int i = 0; i < facilityToUnits.size(); i++) {
            String query = "DELETE FROM FacilitiesUnits WHERE id = " + facilityToUnits.get(0).getId() + ";";
            executeQuery(query);
        }
    }

    public void batchDeleteFacilityToUnitsHistory(ArrayList<FacilityToUnitHistory> facilityToUnitHistories) throws SQLException {
        for(int i = 0; i < facilityToUnitHistories.size(); i++) {
            String query = "DELETE FROM FacilityUnitsHistory WHERE id = " + facilityToUnitHistories.get(0).getId() + ";";
            executeQuery(query);
        }
    }

    public void batchDeleteUnits(ArrayList<Unit> units) throws SQLException {
        for(int i = 0; i < units.size(); i++) {
            String query = "DELETE FROM Units WHERE id = " + units.get(0).getId() + ";";
            executeQuery(query);
        }
    }

















    /*
     *      Get max commands
     */

    public long getMaxFacilityToUnitId() throws SQLException
    {
        ResultSet rs = executeQuery("SELECT max(id) FROM FacilitiesUnits");
        rs.next();
        return rs.getLong(1);
    }

    public long getMaxUnitId() throws SQLException
    {
        ResultSet rs = executeQuery("SELECT max(id) FROM Units");
        rs.next();
        return rs.getLong(1);
    }

    public long getMaxFacilityToUnitHistoryId() throws SQLException
    {
        ResultSet rs = executeQuery("SELECT max(id) FROM FacilitiesUnitsHistory");
        rs.next();
        return rs.getLong(1);
    }
}