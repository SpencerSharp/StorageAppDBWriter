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
import java.io.File;
import java.io.IOException;
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
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withCredentials(cred).withRegion(Regions.US_EAST_2).build();//Client(credentials).withEndpoint("dynamodb.us-west-2.amazonaws.com");
        mapper = new DynamoDBMapper(client);
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
