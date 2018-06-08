/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storageappdbwriter;

import java.io.IOException;
import static java.lang.System.out;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author spencersharp
 */
public class IdTest
{
    public static void main(String[] args) throws IOException, ParseException
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date = new Date(0);
        Date minDate = new Date(Integer.MAX_VALUE);
        out.println(date.compareTo(minDate));
        /*
        DynamoHandler dh = new DynamoHandler();
        dh.incrementVersion();
        
        Company test = new Company();
        test.setId(0);
        
        ArrayList<Company> list = new ArrayList<Company>();
        list.add(test);
        
        dh.mapper.batchDelete(list);*/
    }
}
