package luminia.backend.services;
import luminia.backend.configurations.S3Configuration;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.net.URI;
import java.net.URL;
import java.nio.file.Path;

@Service
public class S3Service {
    private final S3Configuration config;
    private final S3Client s3Client;

    public S3Service(S3Configuration config) {
        this.config = config;
        this.s3Client = S3Client.builder()
                .endpointOverride(URI.create(config.getEndpoint()))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.builder()
                        .accessKeyId(config.getAccessKey())
                        .secretAccessKey(config.getSecretKey())
                        .build()))
                .region(config.getRegion() != null ? Region.of(config.getRegion()) : Region.EU_CENTRAL_1)
                .forcePathStyle(true)
                .build();
    }

    public void upload(String objectId, String contentType, byte[] bytes) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(config.getBucket())
                .key(objectId)
                .contentType(contentType)
                .build();
        s3Client.putObject(request, RequestBody.fromBytes(bytes));
    }

    public URL getUrl(String objectId) {
        return s3Client.utilities().getUrl(GetUrlRequest.builder().bucket(config.getBucket()).key(objectId).build());
    }
}
