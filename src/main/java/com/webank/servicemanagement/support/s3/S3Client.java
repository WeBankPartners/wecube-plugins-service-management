package com.webank.servicemanagement.support.s3;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.webank.servicemanagement.commons.AppProperties.ServiceManagementProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

public class S3Client {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    AmazonS3 s3Client;
    private String s3DefaultBucket;

    public S3Client(@Autowired ServiceManagementProperties serviceManagementProperties) {
        this.s3DefaultBucket = serviceManagementProperties.getS3DefaultBucket();
        String endpoint = serviceManagementProperties.getS3Endpoint();
        String accessKey = serviceManagementProperties.getS3AccessKey();
        String secretKey = serviceManagementProperties.getS3SecretKey();
        AwsClientBuilder.EndpointConfiguration endpointConfig = new AwsClientBuilder.EndpointConfiguration(endpoint,
                Regions.DEFAULT_REGION.name());

        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setSignerOverride("AWSS3V4SignerType");

        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);

        this.s3Client = AmazonS3ClientBuilder.standard().withEndpointConfiguration(endpointConfig)
                .withPathStyleAccessEnabled(true).withClientConfiguration(clientConfiguration)
                .withCredentials(awsCredentialsProvider).build();
    }

    public AmazonS3 getS3Client() {
        return this.s3Client;
    }

    public String getDefaultBucket() {
        return s3DefaultBucket;
    }

    public boolean fileExists(String bucketName, String fileName) {
        ObjectListing objects = s3Client.listObjects(bucketName);

        for (S3ObjectSummary ob : objects.getObjectSummaries()) {
            if (ob.getKey() == fileName) {
                return true;
            }
        }
        return false;
    }

    public String uploadFile(String s3KeyName, File file) throws Exception {
        String bucketName = getDefaultBucket();
        if (!(s3Client.doesBucketExist(bucketName))) {
            s3Client.createBucket(new CreateBucketRequest(bucketName));
        }

        if (fileExists(bucketName, s3KeyName)) {
            throw new Exception(String.format("File[%s] already exists", s3KeyName));
        }

        log.info("uploaded File  [{}] to S3 bucket[{}]", s3KeyName, bucketName);
        s3Client.putObject(
                new PutObjectRequest(bucketName, s3KeyName, file).withCannedAcl(CannedAccessControlList.Private));

        Date expiration = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(expiration);
        calendar.add(Calendar.HOUR, 24);

        GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(bucketName, s3KeyName)
                .withExpiration(calendar.getTime());
        String url = s3Client.generatePresignedUrl(urlRequest).toString();
        return url;
    }

    public S3ObjectInputStream downFile(String key) {
        String bucketName = getDefaultBucket();

        GetObjectRequest request = new GetObjectRequest(bucketName, key);
        S3Object object = s3Client.getObject(request);
        S3ObjectInputStream inputStream = object.getObjectContent();
        log.info("downloaded file [{}] from s3 , url {} , ", key, inputStream.getHttpRequest().getURI());
        return inputStream;
    }

    public void downFile(String key, String localPath) {
        String bucketName = getDefaultBucket();

        GetObjectRequest request = new GetObjectRequest(bucketName, key);
        s3Client.getObject(request, new File(localPath));
        log.info("downloaded file [{}] from s3 to local path[{}]", key, localPath);
    }

    public S3Client() {
        super();
    }

}
