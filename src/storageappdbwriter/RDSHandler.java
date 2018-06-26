package storageappdbwriter;

import java.io.File;
import java.io.IOException;
import static java.lang.System.out;

import java.sql.*;
import java.util.Date;
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









    public java.util.Date getDateFromSqlDate(Timestamp date)
    {
        Date javaDate = null;
        if (date != null) {
            javaDate = new Date(date.getTime());
        }
        return javaDate;
    }

    public Timestamp getSqlDateFromDate(Date date)
    {
        if(date == null)
            return null;
        Timestamp result = new Timestamp(date.getTime());
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
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found!");
            e.printStackTrace();
            return;
        }

        System.out.println("MySQL JDBC Driver found!");
        connection = null;

        try {
            connection = DriverManager.
                    getConnection("jdbc:mysql://" + "nitroxtech.c48qi7cc3kyh.us-west-1.rds.amazonaws.com" + ":" + "3306" + "/" + "StorageDBWebAppMainDatabase?useLegacyDatetimeCode=false", username, password);
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
        createUsersTable(connection);


        createValuesTable(connection);
        createVersionTable(connection);
        */
    }

    public void resetTables()
    {
        deleteTables(connection, "Companies CompaniesFacilities Facilities FacilitiesUnitsHistory FacilitiesUnits Units Users UserPreferences WriteTimes Version");

        createCompaniesTable(connection);
        createCompaniesToFacilitiesTable(connection);
        createFacilitiesTable(connection);
        createFacilityToUnitsTable(connection);
        createFacilityToUnitsHistoryTable(connection);
        createUnitsTable(connection);
        createUsersTable(connection);
        createUserPreferencesTable(connection);
        createWriteTimesTable(connection);
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

    private static void createFacilitiesTable(Connection conn) {
        Statement statement = null;
        try {
            statement = conn.createStatement();
            String sql =  "CREATE TABLE " + "Facilities" + "(" +
                    "id" + " BIGINT PRIMARY KEY," +
                    "sourceURL" + " TEXT," +
                    "name" + " TEXT," +
                    "companyId" + " BIGINT," +
                    "streetAddress1" + " TEXT," +
                    "streetAddress2" + " TEXT," +
                    "city" + " TEXT," +
                    "state" + " TEXT," +
                    "zip" + " TEXT," +
                    "country" + " TEXT," +
                    "website" + " TEXT," +
                    "setupFee" + " DECIMAL(10,2)," +
                    "percentFull" + " DECIMAL(10,2)," +
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
                    "mondayOpen" + " TIME," +
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

    private static void createFacilityToUnitsTable(Connection conn) {
        Statement statement = null;
        try {
            statement = conn.createStatement();
            String sql =  "CREATE TABLE " + "FacilitiesUnits" + "(" +
                    "id" + " BIGINT PRIMARY KEY," +
                    "facilityId" + " BIGINT," +
                    "unitId" + " BIGINT," +
                    "dateCreated" + " TIMESTAMP," +
                    "rateAmount" + " DECIMAL(10,2)," +
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
                    "dateCreated" + " TIMESTAMP," +
                    "rateAmount" + " DECIMAL(10,2)," +
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
                    "width" + " DECIMAL(10,2)," +
                    "depth" + " DECIMAL(10,2)," +
                    "height" + " DECIMAL(10,2)," +
                    "floor" + " INT," +
                    "doorHeight" + " DECIMAL(10,2)," +
                    "doorWidth" + " DECIMAL(10,2)" + ")";
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

    private static void createUsersTable(Connection conn)
    {
        Statement statement = null;

        try {
            statement = conn.createStatement();
            String sql =  "CREATE TABLE " + "Users(" +
                    "id" + " BIGINT PRIMARY KEY," +
                    "type" + " TEXT," +
                    "firstName" + " TEXT," +
                    "lastName" + " TEXT," +
                    "username" + " TEXT," +
                    "password" + " TEXT," +
                    "isActive" + " BIT," +
                    "dateCreated" + " TIMESTAMP," +
                    "dateUpdated" + " TIMESTAMP)";
            statement.executeUpdate(sql);
            System.out.println("Created Users table");
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

    private static void createUserPreferencesTable(Connection conn)
    {
        Statement statement = null;

        try {
            statement = conn.createStatement();
            String sql =  "CREATE TABLE " + "UserPreferences(" +
                    "userId" + " BIGINT PRIMARY KEY," +
                    "landingPage" + " TEXT," +
                    "landingFacilityId" + " BIGINT)";
            statement.executeUpdate(sql);
            System.out.println("Created UserPreferences table");
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
    
    private static void createWriteTimesTable(Connection conn)
    {
        Statement statement = null;

        try {
            statement = conn.createStatement();
            String sql =  "CREATE TABLE " + "WriteTimes(" +
                    "id" + " BIGINT PRIMARY KEY," +
                    "time" + " TIMESTAMP)";
            statement.executeUpdate(sql);
            System.out.println("Created WriteTimes table");
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
        //System.out.println("QUERY: " + query);
        Statement statement = connection.createStatement();
        if(query.contains("INSERT") || query.contains("DELETE") || query.contains("UPDATE") || query.contains("TRUNCATE"))
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
        facilityToUnit.setDateCreated(getDateFromSqlDate(resultSet.getTimestamp("dateCreated")));
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
        facilityToUnitHistory.setDateCreated(getDateFromSqlDate(resultSet.getTimestamp("dateCreated")));
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

    private User createUserFromResultSet(ResultSet resultSet) throws SQLException
    {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setType(resultSet.getString("type"));
        user.setFirstName(resultSet.getString("firstName"));
        user.setLastName(resultSet.getString("lastName"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setIsActive(resultSet.getBoolean("isActive"));
        user.setDateCreated(getDateFromSqlDate(resultSet.getTimestamp("dateCreated")));
        user.setDateUpdated(getDateFromSqlDate(resultSet.getTimestamp("dateUpdated")));
        return user;
    }

    private UserPreferences createUserPreferencesFromResultSet(ResultSet resultSet) throws SQLException
    {
        UserPreferences userPreferences = new UserPreferences();
        userPreferences.setUserId(resultSet.getLong("userId"));
        userPreferences.setLandingPage(resultSet.getString("landingPage"));
        userPreferences.setLandingFacilityId(resultSet.getLong("landingFacilityId"));
        return userPreferences;
    }












    private String buildValuesOfCompanyInsertQuery(Company company)
    {
        String result = "(" + company.getId() + ", ";
        result += "'" + company.getName() + "', ";
        result += "'" + company.getWebsite() + "')";

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
        result += "'" + facility.getSourceURL() + "', ";
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

    private String buildValuesOfFacilityToUnitInsertQuery(FacilityToUnit facilityToUnit) throws SQLException
    {
        String result = "(" + facilityToUnit.getId() + ", ";
        result += "" + facilityToUnit.getFacilityId() + ", ";
        result += "" + facilityToUnit.getUnitId() + ", ";
        Timestamp date = getSqlDateFromDate(facilityToUnit.getDateCreated());
        //PreparedStatement preparedStatement = connection.prepareStatement("?");
        //preparedStatement.setTimestamp(1,date);
        //out.println("AHA: " +preparedStatement.toString());
        //result += preparedStatement.toString();
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

    private String buildFacilityToUnitInsertQuery(FacilityToUnit facilityToUnit) throws SQLException
    {
        String query = "INSERT INTO FacilitiesUnits VALUES" + buildValuesOfFacilityToUnitInsertQuery(facilityToUnit);
        return query;
    }

    private String buildValuesOfFacilityToUnitHistoryInsertQuery(FacilityToUnitHistory facilityToUnitHistory)
    {
        String result = "(" + facilityToUnitHistory.getId() + ", ";
        result += "" + facilityToUnitHistory.getFacilityId() + ", ";
        result += "" + facilityToUnitHistory.getUnitId() + ", ";
        Timestamp date = getSqlDateFromDate(facilityToUnitHistory.getDateCreated());
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

    private String buildValuesOfUserInsertQuery(User user)
    {
        String result = "(";
        result += user.getId() + ",";
        result += "'" + user.getType() + "',";
        result += "'" + user.getFirstName() + "',";
        result += "'" + user.getLastName() + "',";
        result += "'" + user.getUsername() + "',";
        result += "'" + user.getPassword() + "',";
        result += "" + user.isActive() + ",";
        Timestamp date = getSqlDateFromDate(user.getDateCreated());
        if(date == null)
        {
            result += "null, ";
        }
        else
        {
            result += "'" + date + "',";
        }
        date = getSqlDateFromDate(user.getDateUpdated());
        if(date == null)
        {
            result += "null)";
        }
        else
        {
            result += "'" + date + "')";
        }
        return result;
    }

    private String buildUserInsertQuery(User user)
    {
        String query = "INSERT INTO Users VALUES" + buildValuesOfUserInsertQuery(user);
        return query;
    }

    private String buildValuesOfUserPreferencesInsertQuery(UserPreferences userPreferences)
    {
        String result = "(";
        result += userPreferences.getUserId() + ",";
        result += "'" + userPreferences.getLandingPage() + "',";
        result += userPreferences.getLandingFacilityId() + ")";
        return result;
    }

    private String buildUserPreferencesInsertQuery(UserPreferences userPreferences)
    {
        String query = "INSERT INTO UserPreferences VALUES" + buildValuesOfUserPreferencesInsertQuery(userPreferences);
        return query;
    }
    
    private String buildValuesOfWriteTimeInsertQuery(WriteTime writeTime)
    {
        String result = "(";
        result += writeTime.getId() + ",";
        Date date = getSqlDateFromDate(writeTime.getTime());
        if(date == null)
        {
            result += "null)";
        }
        else
        {
            result += "'" + date + "')";
        }
        return result;
    }
    
    private String buildWriteTimeInsertQuery(WriteTime writeTime)
    {
        String query = "INSERT INTO WriteTimes VALUES" + buildValuesOfWriteTimeInsertQuery(writeTime);
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

    public void addUser(User user) throws SQLException
    {
        String query = buildUserInsertQuery(user);
        executeQuery(query);
    }

    public void addUserPreferences(UserPreferences userPreferences) throws SQLException
    {
        String query = buildUserPreferencesInsertQuery(userPreferences);
        executeQuery(query);
    }
    
    public void addWriteTime(WriteTime writeTime) throws SQLException
    {
        String query = buildWriteTimeInsertQuery(writeTime);
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
        ArrayList<Unit> units = new ArrayList<Unit>();
        if(results.size() > 0)
        {
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

            while(resultSet.next())
            {
                units.add(createUnitFromResultSet(resultSet));
            }
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
    
    public ArrayList<FacilityToUnit> getFacilityToUnitsFromFacilityIds(ArrayList<Long> facilityIds) throws SQLException
    {
        if(facilityIds.size() == 0)
        {
            return new ArrayList<FacilityToUnit>();
        }
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
        return facilityToUnits;
    }

    public ArrayList<JavaLocalGrailsUnit> getUnitsFromFacilityIds(ArrayList<Long> facilityIds) throws SQLException
    {
        ArrayList<FacilityToUnit> facilityToUnits = getFacilityToUnitsFromFacilityIds(facilityIds);

        //Search units for unitIds of the FacilityToUnit objects
        String query = "SELECT * FROM Units WHERE id IN (";
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
        ResultSet resultSet = executeQuery(query);
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

    public String getUserTypeFromLogin(String username, String password) throws SQLException
    {
        String query = "SELECT * FROM Users WHERE username='" + username + "' AND password='" + password + "'";
        ResultSet resultSet = executeQuery(query);
        if(resultSet.next())
        {
            return createUserFromResultSet(resultSet).getType();
        }
        return "failed";
    }

    

    public ArrayList<User> getActiveUsers() throws SQLException
    {
        ArrayList<User> users = new ArrayList<User>();
        String query = "SELECT * FROM Users WHERE isActive = 1";
        ResultSet resultSet = executeQuery(query);
        while(resultSet.next())
        {
            users.add(createUserFromResultSet(resultSet));
        }
        return users;
    }

    public void makeUserWithIdInactive(long id) throws SQLException
    {
        String query = "UPDATE Users SET isActive = FALSE WHERE id = " + id;
        executeQuery(query);
    }

    public void makeAllUsersInactive() throws SQLException
    {
        String query = "UPDATE Users SET isActive = FALSE";
        executeQuery(query);
    }

    public User getUserByUsername(String username) throws SQLException
    {
        String query = "SELECT * FROM Users WHERE username='" + username + "'";
        ResultSet resultSet = executeQuery(query);
        resultSet.next();
        return createUserFromResultSet(resultSet);
    }

    public UserPreferences getPreferencesOfUser(User user) throws SQLException
    {
        String query = "SELECT * FROM UserPreferences WHERE userId=" + user.getId();
        ResultSet resultSet = executeQuery(query);
        resultSet.next();
        return createUserPreferencesFromResultSet(resultSet);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    public ArrayList<Company> getAllCompanies() throws SQLException
    {
        ArrayList<Company> companies = new ArrayList<Company>();
        String query = "SELECT * FROM Companies";
        ResultSet resultSet = executeQuery(query);
        while(resultSet.next())
        {
            companies.add(createCompanyFromResultSet(resultSet));
        }
        return companies;
    }
    
    public ArrayList<CompanyToFacility> getAllCompanyToFacilities() throws SQLException
    {
        ArrayList<CompanyToFacility> companyToFacilities = new ArrayList<CompanyToFacility>();
        String query = "SELECT * FROM CompaniesFacilities";
        ResultSet resultSet = executeQuery(query);
        while(resultSet.next())
        {
            companyToFacilities.add(createCompanyToFacilityFromResultSet(resultSet));
        }
        return companyToFacilities;
    }
    
    public ArrayList<Facility> getAllFacilities() throws SQLException
    {
        ArrayList<Facility> facilities = new ArrayList<Facility>();
        String query = "SELECT * FROM Facilities";
        ResultSet resultSet = executeQuery(query);
        while(resultSet.next())
        {
            facilities.add(createFacilityFromResultSet(resultSet));
        }
        return facilities;
    }
    
    public ArrayList<FacilityToUnit> getAllFacilityToUnits() throws SQLException
    {
        ArrayList<FacilityToUnit> facilityToUnits = new ArrayList<FacilityToUnit>();
        String query = "SELECT * FROM FacilitiesUnits";
        ResultSet resultSet = executeQuery(query);
        while(resultSet.next())
        {
            facilityToUnits.add(createFacilityToUnitFromResultSet(resultSet));
        }
        return facilityToUnits;
    }
    
    public ArrayList<Unit> getAllUnits() throws SQLException
    {
        ArrayList<Unit> units = new ArrayList<Unit>();
        String query = "SELECT * FROM Units";
        ResultSet resultSet = executeQuery(query);
        while(resultSet.next())
        {
            units.add(createUnitFromResultSet(resultSet));
        }
        return units;
    }
    
    public ArrayList<User> getAllUsers() throws SQLException
    {
        ArrayList<User> users = new ArrayList<User>();
        String query = "SELECT * FROM Users";
        ResultSet resultSet = executeQuery(query);
        while(resultSet.next())
        {
            users.add(createUserFromResultSet(resultSet));
        }
        return users;
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    public void forceAddCompanies(ArrayList<Company> companies) throws SQLException
    {
        String query = "INSERT INTO Companies (id, name, website)\n";
        query += "VALUES\n";
        for(int i = 0; i < companies.size(); i++)
        {
            query += buildValuesOfCompanyInsertQuery(companies.get(i));
            if(i < companies.size()-1)
            {
                query += ",";
            }
            query += "\n";
        }
        query += "ON DUPLICATE KEY UPDATE\n";
        query += "id = VALUES(id),\n";
        query += "name = VALUES(name),\n";
        query += "website = VALUES(website)";
        executeQuery(query);
    }
    
    public void forceAddCompanyToFacilities(ArrayList<CompanyToFacility> companyToFacilities) throws SQLException
    {
        String query = "INSERT INTO CompaniesFacilities (id, companyId, facilityId)\n";
        query += "VALUES\n";
        for(int i = 0; i < companyToFacilities.size(); i++)
        {
            query += buildValuesOfCompanyToFacilityInsertQuery(companyToFacilities.get(i));
            if(i < companyToFacilities.size()-1)
            {
                query += ",";
            }
            query += "\n";
        }
        query += "ON DUPLICATE KEY UPDATE\n";
        query += "id = VALUES(id),\n";
        query += "companyId = VALUES(companyId),\n";
        query += "facilityId = VALUES(facilityId)";
        executeQuery(query);
    }
    
    public void forceAddFacilities(ArrayList<Facility> facilities) throws SQLException
    {
        String query = "INSERT INTO Facilities (id, sourceURL, name, companyId, "
                + "streetAddress1, streetAddress2, city, state, zip, country, "
                + "website, setupFee, percentFull, hasRetailStore, hasInsurance, "
                + "hasOnlineBillPay, hasWineStorage, hasKiosk, hasOnsiteManagement, "
                + "hasCameras, hasVehicleParking, hasCutLocks, hasOnsiteShipping, "
                + "hasAutopay, hasOnsiteCarts, hasParabolicMirrors, hasMotionLights, "
                + "hasElectronicLease, hasPaperlessBilling, mondayOpen, mondayClose, "
                + "tuesdayOpen, tuesdayClose, wednesdayOpen, wednesdayClose, thursdayOpen, "
                + "thursdayClose, fridayOpen, fridayClose, saturdayOpen, saturdayClose, "
                + "sundayOpen, sundayClose, rating, promotions)\n";
        query += "VALUES\n";
        for(int i = 0; i < facilities.size(); i++)
        {
            query += buildValuesOfFacilityInsertQuery(facilities.get(i));
            if(i < facilities.size()-1)
            {
                query += ",";
            }
            query += "\n";
        }
        query += "ON DUPLICATE KEY UPDATE\n";
        query += "id = VALUES(id),\n";
        query += "sourceURL = VALUES(sourceURL),\n";
        query += "name = VALUES(name),\n";
        query += "companyId = VALUES(companyId),\n";
        query += "streetAddress1 = VALUES(streetAddress1),\n";
        query += "streetAddress2 = VALUES(streetAddress2),\n";
        query += "city = VALUES(city),\n";
        query += "state = VALUES(state),\n";
        query += "zip = VALUES(zip),\n";
        query += "country = VALUES(country),\n";
        query += "website = VALUES(website),\n";
        query += "setupFee = VALUES(setupFee),\n";
        query += "percentFull = VALUES(percentFull),\n";
        query += "hasRetailStore = VALUES(hasRetailStore),\n";
        query += "hasInsurance = VALUES(hasInsurance),\n";
        query += "hasOnlineBillPay = VALUES(hasOnlineBillPay),\n";
        query += "hasWineStorage = VALUES(hasWineStorage),\n";
        query += "hasKiosk = VALUES(hasKiosk),\n";
        query += "hasOnsiteManagement = VALUES(hasOnsiteManagement),\n";
        query += "hasCameras = VALUES(hasCameras),\n";
        query += "hasVehicleParking = VALUES(hasVehicleParking),\n";
        query += "hasCutLocks = VALUES(hasCutLocks),\n";
        query += "hasOnsiteShipping = VALUES(hasOnsiteShipping),\n";
        query += "hasAutopay = VALUES(hasAutopay),\n";
        query += "hasOnsiteCarts = VALUES(hasOnsiteCarts),\n";
        query += "hasParabolicMirrors = VALUES(hasParabolicMirrors),\n";
        query += "hasMotionLights = VALUES(hasMotionLights),\n";
        query += "hasElectronicLease = VALUES(hasElectronicLease),\n";
        query += "hasPaperlessBilling = VALUES(hasPaperlessBilling),\n";
        query += "mondayOpen = VALUES(mondayOpen),\n";
        query += "mondayClose = VALUES(mondayClose),\n";
        query += "tuesdayOpen = VALUES(tuesdayOpen),\n";
        query += "tuesdayClose = VALUES(tuesdayClose),\n";
        query += "wednesdayOpen = VALUES(wednesdayOpen),\n";
        query += "wednesdayClose = VALUES(wednesdayClose),\n";
        query += "thursdayOpen = VALUES(thursdayOpen),\n";
        query += "thursdayClose = VALUES(thursdayClose),\n";
        query += "fridayOpen = VALUES(fridayOpen),\n";
        query += "fridayClose = VALUES(fridayClose),\n";
        query += "saturdayOpen = VALUES(saturdayOpen),\n";
        query += "saturdayClose = VALUES(saturdayClose),\n";
        query += "sundayOpen = VALUES(sundayOpen),\n";
        query += "sundayClose = VALUES(sundayClose),\n";
        query += "rating = VALUES(rating),\n";
        query += "promotions = VALUES(promotions)";
        
        executeQuery(query);
    }
    
    public void forceAddFacilityToUnits(ArrayList<FacilityToUnit> facilityToUnits) throws SQLException
    {
        String query = "INSERT INTO FacilitiesUnits (id, facilityId, unitId, dateCreated, rateAmount, rateType)\n";
        query += "VALUES\n";
        for(int i = 0; i < facilityToUnits.size(); i++)
        {
            query += buildValuesOfFacilityToUnitInsertQuery(facilityToUnits.get(i));
            if(i < facilityToUnits.size()-1)
            {
                query += ",";
            }
            query += "\n";
        }
        query += "ON DUPLICATE KEY UPDATE\n";
        query += "id = VALUES(id),\n";
        query += "facilityId = VALUES(facilityId),\n";
        query += "unitId = VALUES(unitId),\n";
        query += "dateCreated = VALUES(dateCreated),\n";
        query += "rateAmount = VALUES(rateAmount),\n";
        query += "rateType = VALUES(rateType)";
        executeQuery(query);
    }
    
    public void forceAddUnits(ArrayList<Unit> units) throws SQLException
    {
        String query = "INSERT INTO Units (id, name, type, width, depth, height, floor, doorHeight, doorWidth)\n";
        query += "VALUES\n";
        for(int i = 0; i < units.size(); i++)
        {
            query += buildValuesOfUnitInsertQuery(units.get(i));
            if(i < units.size()-1)
            {
                query += ",";
            }
            query += "\n";
        }
        query += "ON DUPLICATE KEY UPDATE\n";
        query += "id = VALUES(id),\n";
        query += "name = VALUES(name),\n";
        query += "type = VALUES(type),\n";
        query += "width = VALUES(width),\n";
        query += "depth = VALUES(depth),\n";
        query += "height = VALUES(height),\n";
        query += "floor = VALUES(floor),\n";
        query += "doorHeight = VALUES(doorHeight),\n";
        query += "doorWidth = VALUES(doorWidth)";
        executeQuery(query);
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
        //out.println(query);
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
        //out.println(query);
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
                //out.println(facilityToUnit.getId());
                if(facilityToUnit.getFacilityId() == 4)
                {
//                    out.println("FOUND ONE");
//                    out.println(facilityToUnit);
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

    public void batchSaveUsers(ArrayList<User> users) throws SQLException
    {
        if(users.size() > 0)
        {
            String query = "INSERT Users VALUES";
            for(int i = 0; i < users.size(); i++)
            {
                User user = users.get(i);
                query += buildValuesOfUserInsertQuery(user);
                if(i != users.size() - 1)
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
        if(companies.size() > 0)
        {
            String query = "DELETE FROM Companies VALUES(";
            for(int i = 0; i < companies.size(); i++)
            {
                Company unit = companies.get(i);
                query += companies.get(i).getId();
                if(i != companies.size() - 1)
                {
                    query += ",";
                }
                else
                {
                    query += ")";
                }
            }
            executeQuery(query);
        }
    }

    public void batchDeleteCompanyToFacilities(ArrayList<CompanyToFacility> companyToFacilities) throws SQLException {
        if(companyToFacilities.size() > 0)
        {
            String query = "DELETE FROM CompaniesFacilities WHERE id IN VALUES(";
            for(int i = 0; i < companyToFacilities.size(); i++)
            {
                CompanyToFacility unit = companyToFacilities.get(i);
                query += companyToFacilities.get(i).getId();
                if(i != companyToFacilities.size() - 1)
                {
                    query += ",";
                }
                else
                {
                    query += ")";
                }
            }
            executeQuery(query);
        }
    }

    public void batchDeleteFacilities(ArrayList<Facility> facilities) throws SQLException {
        if(facilities.size() > 0)
        {
            String query = "DELETE FROM Facilities VALUES(";
            for(int i = 0; i < facilities.size(); i++)
            {
                query += facilities.get(i).getId();
                if(i != facilities.size() - 1)
                {
                    query += ",";
                }
                else
                {
                    query += ")";
                }
            }
            executeQuery(query);
        }
    }

    public void batchDeleteFacilityToUnits(ArrayList<FacilityToUnit> facilityToUnits) throws SQLException {
        if(facilityToUnits.size() > 0)
        {
            String query = "DELETE FROM FacilitiesUnits WHERE id IN (";
            for(int i = 0; i < facilityToUnits.size(); i++)
            {
                query += facilityToUnits.get(i).getId();
                if(i != facilityToUnits.size() - 1)
                {
                    query += ",";
                }
                else
                {
                    query += ")";
                }
            }
            executeQuery(query);
        }
    }

    public void batchDeleteFacilityToUnitsHistory(ArrayList<FacilityToUnitHistory> facilityToUnitHistories) throws SQLException {
        if(facilityToUnitHistories.size() > 0)
        {
            String query = "DELETE FROM FacilitiesUnitsHistory VALUES(";
            for(int i = 0; i < facilityToUnitHistories.size(); i++)
            {
                query += facilityToUnitHistories.get(i).getId();
                if(i != facilityToUnitHistories.size() - 1)
                {
                    query += ",";
                }
                else
                {
                    query += ")";
                }
            }
            executeQuery(query);
        }
    }

    public void batchDeleteUnits(ArrayList<Unit> units) throws SQLException {
        if(units.size() > 0)
        {
            String query = "DELETE FROM Units WHERE id IN (";
            for(int i = 0; i < units.size(); i++)
            {
                query += units.get(i).getId();
                if(i != units.size() - 1)
                {
                    query += ",";
                }
                else
                {
                    query += ")";
                }
            }
            executeQuery(query);
        }
    }

    public void batchDeleteUsersWithIds(ArrayList<Long> ids) throws SQLException {
        if(ids.size() > 0)
        {
            String query = "DELETE FROM Users WHERE id IN (";
            for(int i = 0; i < ids.size(); i++)
            {
                query += ids.get(i);
                if(i != ids.size() - 1)
                {
                    query += ",";
                }
                else
                {
                    query += ")";
                }
            }
            executeQuery(query);
        }
    }

    public void batchDeleteUsers(ArrayList<User> users) throws SQLException {
        if(users.size() > 0)
        {
            String query = "DELETE FROM Users WHERE id IN (";
            for(int i = 0; i < users.size(); i++)
            {
                query += users.get(i).getId();
                if(i != users.size() - 1)
                {
                    query += ",";
                }
                else
                {
                    query += ")";
                }
            }
            executeQuery(query);
        }
    }

    /**
     * Set the users to be updated to the info in users. The rest of the users (mentioned in removedIds) will have isActive
     * set to 0.
     * @param users
     * @param removedIds
     * @throws SQLException
     */
    public void updateUsers(ArrayList<User> users, ArrayList<Long> removedIds) throws SQLException
    {
        /*
        String query = "UPDATE Users\n";
        //Type update
        query += "SET type = CASE id\n";
        String end = "WHERE id IN (";
        for(User user : users)
        {
            query += "WHEN " + user.getId() + " THEN '" + user.getType() + "'\n";
            end += user.getId() + ", ";
        }
        query += "END,\n";
        end = end.substring(0,end.length()-2);
        query += end + ")";
        //Firstname update
        query += "SET type = CASE id\n";
        String end = "WHERE id IN (";
        for(User user : users)
        {
            query += "WHEN " + user.getId() + " THEN '" + user.getType() + "'\n";
            end += user.getId() + ", ";
        }
        query += "END,\n";
        end = end.substring(0,end.length()-2);
        query += end + ")";
        //Lastname update
        query += "SET type = CASE id\n";
        String end = "WHERE id IN (";
        for(User user : users)
        {
            query += "WHEN " + user.getId() + " THEN '" + user.getType() + "'\n";
            end += user.getId() + ", ";
        }
        query += "END,\n";
        end = end.substring(0,end.length()-2);
        query += end + ")";
        //Username update
        query += "SET type = CASE id\n";
        String end = "WHERE id IN (";
        for(User user : users)
        {
            query += "WHEN " + user.getId() + " THEN '" + user.getType() + "'\n";
            end += user.getId() + ", ";
        }
        query += "END,\n";
        end = end.substring(0,end.length()-2);
        query += end + ")";
        //Password update
        query += "SET type = CASE id\n";
        String end = "WHERE id IN (";
        for(User user : users)
        {
            query += "WHEN " + user.getId() + " THEN '" + user.getType() + "'\n";
            end += user.getId() + ", ";
        }
        query += "END,\n";
        end = end.substring(0,end.length()-2);
        query += end + ")";
        //IsActive update
        query += "SET type = CASE id\n";
        String end = "WHERE id IN (";
        for(User user : users)
        {
            query += "WHEN " + user.getId() + " THEN '" + user.getType() + "'\n";
            end += user.getId() + ", ";
        }
        query += "END,\n";
        end = end.substring(0,end.length()-2);
        query += end + ")";

        //for(User user : users)

        query += " SET username = (case";
        for(User user : users)
        {
            query += " when id=" + user.getId() + " then '" + user.getUsername() + "'";
        }
        query += " end),";


        executeQuery(query);*/
    }




















    /*
     *      Get max commands
     */
    public long getMaxCompanyId() throws SQLException
    {
        ResultSet resultSet = executeQuery("SELECT IFNULL(max(id),-1) FROM Companies");
        resultSet.next();
        Long result = resultSet.getLong(1);
        return result;
    }
    
    public long getMaxCompanyToFacilityId() throws SQLException
    {
        ResultSet resultSet = executeQuery("SELECT IFNULL(max(id),-1) FROM CompaniesFacilities");
        resultSet.next();
        Long result = resultSet.getLong(1);
        if(result == null)
            return -1;
        return result;
    }
    
    public long getMaxFacilityId() throws SQLException
    {
        ResultSet resultSet = executeQuery("SELECT IFNULL(max(id),-1) FROM Facilities");
        resultSet.next();
        Long result = resultSet.getLong(1);
        if(result == null)
            return -1;
        return result;
    }

    public long getMaxFacilityToUnitId() throws SQLException
    {
        ResultSet resultSet = executeQuery("SELECT IFNULL(max(id),-1) FROM FacilitiesUnits");
        resultSet.next();
        Long result = resultSet.getLong(1);
        if(result == null)
            return -1;
        return result;
    }

    public long getMaxFacilityToUnitHistoryId() throws SQLException
    {
        ResultSet resultSet = executeQuery("SELECT IFNULL(max(id),-1) FROM FacilitiesUnitsHistory");
        resultSet.next();
        Long result = resultSet.getLong(1);
        if(result == null)
            return -1;
        return result;
    }

    public long getMaxUnitId() throws SQLException
    {
        ResultSet resultSet = executeQuery("SELECT IFNULL(max(id),-1) FROM Units");
        resultSet.next();
        Long result = resultSet.getLong(1);
        if(result == null)
            return -1;
        return result;
    }

    public long getMaxUserId() throws SQLException
    {
        ResultSet resultSet = executeQuery("SELECT IFNULL(max(id),-1) FROM Users");
        resultSet.next();
        Long result = resultSet.getLong(1);
        if(result == null)
            return -1;
        return result;
    }
    
    public long getMaxWriteTimeId() throws SQLException
    {
        ResultSet resultSet = executeQuery("SELECT max(id) FROM WriteTimes");
        resultSet.next();
        Long result = resultSet.getLong(1);
        if(result == null)
            return -1;
        return result;
    }

    public void emptyUsersTable() throws SQLException
    {
        executeQuery("TRUNCATE Users");
    }
}