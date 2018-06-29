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
import java.util.Collections;

/**
 *
 * @author spencersharp
 */
public class TestRunner
{
    public static void main(String[] args) throws SQLException
    {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime localDateTime2 = LocalDateTime.now().minusHours(5);
        LocalDateTime localDateTime3 = LocalDateTime.now().minusHours(10);
        ArrayList<LocalDateTime> dates = new ArrayList<LocalDateTime>();
        dates.add(localDateTime);
        dates.add(localDateTime2);
        dates.add(localDateTime3);
        Collections.sort(dates);
        for(LocalDateTime date : dates)
        {
            out.println(TimeFormatter.showLocalTimeFromLocalDateTime(date));
        }
        
    }
}
