package storageappdbwriter;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DynamoHandler
{
    DynamoDBMapper mapper;
    
    public DynamoHandler() throws IOException
    {
        Scanner sc = new Scanner(new File("DataFiles/credentials.txt"));
        String accessKey = sc.nextLine();
        String secretKey = sc.nextLine();
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AWSStaticCredentialsProvider cred = new AWSStaticCredentialsProvider(credentials);
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withCredentials(cred).withRegion(Regions.US_WEST_1).build();//Client(credentials).withEndpoint("dynamodb.us-west-2.amazonaws.com");
        mapper = new DynamoDBMapper(client);
    }
    
    public void deleteFacilityToUnits()
    {
        String filterExpression = "id <> :val1";
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withN("3"));
        
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression(filterExpression).withExpressionAttributeValues(eav);
        
        List result = mapper.scan(FacilityToUnit.class, scanExpression);
        mapper.batchDelete(result);
        
        Value value = new Value("maxFacilityToUnitId");
        value.setValue(3l);
        mapper.save(value);
    }
    
    public void clearAllTablesExceptVersion()
    {
        String filterExpression = "id <> :val1";
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withN("-1"));
        
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression(filterExpression).withExpressionAttributeValues(eav);
        
        List result = mapper.scan(Company.class, scanExpression);
        mapper.batchDelete(result);
        
        result = mapper.scan(CompanyToFacility.class, scanExpression);
        mapper.batchDelete(result);
        
        result = mapper.scan(Facility.class, scanExpression);
        mapper.batchDelete(result);
        
        result = mapper.scan(FacilityToUnitRecent.class, scanExpression);
        mapper.batchDelete(result);
        
        result = mapper.scan(FacilityToUnit.class, scanExpression);
        mapper.batchDelete(result);
        
        result = mapper.scan(Unit.class, scanExpression);
        mapper.batchDelete(result);
        
        result = mapper.scan(Value.class, scanExpression);
        mapper.batchDelete(result);
    }
    
    public void batchSaveCompanies(ArrayList<Company> companyList)
    {
        System.out.println(mapper.batchSave(companyList));
    }
    
    public class FacilityToUnitUpdater
    {
        
    }
            
    
    //Version code
    public void incrementVersion()
    {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        PaginatedScanList<Version> scanVersions = mapper.scan(Version.class,scanExpression);
        long maxVersion = 0;
        for(Version v : scanVersions)
        {
            if(v.getVersion()>maxVersion)
                maxVersion = v.getVersion();
            mapper.delete(v);
        }
        Version version = new Version(maxVersion+1);
        mapper.save(version);
    }

    public long getVersion()
    {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        PaginatedScanList<Version> scanVersions = mapper.scan(Version.class,scanExpression);
        long maxVersion = 0;
        for(Version v : scanVersions)
        {
            if(v.getVersion()>maxVersion)
                maxVersion = v.getVersion();
        }
        return maxVersion;
    }
    
    public void addCompany(Company c)
    {
        mapper.save(c);
    }
    
    public void addCompanyToFacility(CompanyToFacility ctf)
    {
        mapper.save(ctf);
    }
    
    public void addFacility(Facility f)
    {
        mapper.save(f);
    }
    
    public void addFacilityToUnit(FacilityToUnit ftu)
    {
        mapper.save(ftu);
    }
    
    public void addUnit(Unit u)
    {
        mapper.save(u);
    }
}
