/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storageappdbwriter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author spencersharp
 */
public class WriteTime
{
    long id;
    LocalDateTime time;
    
    public long getId()
    {
        return id;
    }
    
    public void setId(long id)
    {
        this.id = id;
    }
    
    public LocalDateTime getTime()
    {
        return time;
    }
    
    public void setTime(LocalDateTime date)
    {
        this.time = date;
    }
}
