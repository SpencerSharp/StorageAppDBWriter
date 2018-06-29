/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storageappdbwriter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author spencersharp
 */
public final class TimeFormatter
{
    private TimeFormatter()
    {
        
    }
    
    private static LocalDateTime convertToUTC(LocalDateTime localDateTime)
    {
        ZoneId first = ZoneId.of("GMT");
        ZoneId second = ZoneId.systemDefault();
        LocalDateTime newDateTime = localDateTime.atZone(second)
                                       .withZoneSameInstant(first)
                                       .toLocalDateTime();
        return newDateTime;
    }
    
    private static LocalDateTime convertToLocalTime(LocalDateTime localDateTime)
    {
        ZoneId first = ZoneId.of("GMT");
        ZoneId second = ZoneId.systemDefault();
        LocalDateTime newDateTime = localDateTime.atZone(first)
                                       .withZoneSameInstant(second)
                                       .toLocalDateTime();
        return newDateTime;
    }
    
    public static LocalDateTime getTimeToWriteFromString(String time)
    {
        String format = "yyyy-MM-dd HH:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
        return convertToUTC(dateTime);
    }
    
    public static String showLocalTimeFromLocalDateTime(LocalDateTime localDateTime)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        localDateTime = convertToLocalTime(localDateTime);
        String formattedDateTime = localDateTime.format(formatter);
        return formattedDateTime;
    }

    public static LocalDateTime getCurrentLocalDateTimeToWrite()
    {
        return convertToUTC(LocalDateTime.now());
    }

    public static LocalDateTime getDateFromTimestamp(Timestamp date)
    {
        LocalDateTime javaDate = null;
        if (date != null) {
            javaDate = date.toLocalDateTime();
        }
        return convertToUTC(javaDate);
    }

    public static Timestamp getTimestampFromDate(LocalDateTime date)
    {
        if(date == null)
            return null;
        Timestamp result = Timestamp.valueOf(date);
        return result;
    }
}
