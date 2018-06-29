/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storageappdbwriter;

import static java.lang.System.out;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author spencersharp
 */
public class TestRunner
{
    public static void main(String[] args) throws SQLException
    {
        RDSHandler rds = new RDSHandler();
        /*
        User user = new User();
        user.setId(0);
        user.setType("Admin");
        user.setFirstName("Shawn");
        user.setLastName("Sharp");
        user.setUsername("Admin");
        user.setPassword("test");
        user.setIsActive(true);
        ZoneId first = ZoneId.of("GMT");
        ZoneId second = ZoneId.of("America/Chicago");
        LocalDateTime newDateTime = LocalDateTime.now().atZone(second)
                                       .withZoneSameInstant(first)
                                       .toLocalDateTime();
        user.setDateCreated(newDateTime);
        user.setDateUpdated(newDateTime);
        
        rds.addUser(user);
        */
        String parseIt = "2018-06-29 11:50";
        LocalDateTime localDateTime = rds.getTimeToWriteFromString(parseIt);
        
        
        ArrayList<User> users = rds.getActiveUsers();
        users.get(0).dateCreated = localDateTime;
        rds.overwriteAllUsers(users);
        
    }
}
