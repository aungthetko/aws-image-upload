package com.atk.awsimageupload.profile;

import com.atk.awsimageupload.bucket.BucketName;
import com.atk.awsimageupload.filestore.FileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
public class UserProfileService {

    private final UserProfileDataAccessService userProfileDataAccessService;
    private final FileStore fileStore;

    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileDataAccessService, FileStore fileStore) {
        this.userProfileDataAccessService = userProfileDataAccessService;
        this.fileStore = fileStore;
    }

    public List<UserProfile> getUserProfiles(){
        return userProfileDataAccessService.getUserProfile();
    }

    public void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {
        // Check if the image is not empty
        isFileEmpty(file);

        // Check the file is image
        isImage(file);

        // User exists in memory database
        UserProfile user = getUserProfileOrThrow(userProfileId);

        // Grab some metadata from file if any
        Map<String, String> metadata = getMetadata(file);

        // Store the image in s3 and update the database
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUserName());
        String fileName = String.format("%s", file.getOriginalFilename());
        try{
            fileStore.save(path, fileName, Optional.of(metadata), file.getInputStream());
            user.setUserProfileLinkImage(fileName);
        }catch (IOException e){
            throw new IllegalStateException(e);
        }
    }

    private Map<String, String> getMetadata(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }

    private UserProfile getUserProfileOrThrow(UUID userProfileId) {
        UserProfile user = userProfileDataAccessService.getUserProfile()
                .stream()
                .filter(userProfile -> userProfile.getUserProfileId().equals(userProfileId))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
        return user;
    }

    private void isImage(MultipartFile file) {
        if(!Arrays.asList(IMAGE_JPEG.getMimeType(),
                IMAGE_PNG.getMimeType(),
                IMAGE_GIF.getMimeType()).contains(file.getContentType())){
            throw new IllegalStateException("Your file must be an image only");
        }
    }

    private void isFileEmpty(MultipartFile file) {
        if(file.isEmpty()){
            throw new IllegalStateException("Can not upload empty image [" + file.getSize() + "]");
        }
    }

    public byte[] downloadUserProfileImage(UUID userProfileId) {
        UserProfile user = getUserProfileOrThrow(userProfileId);
        String path = String.format("%s/%s",
                BucketName.PROFILE_IMAGE.getBucketName(),
                user.getUserName());

        return user.getUserProfileLinkImage()
                .map(key -> fileStore.download(path, key))
                .orElse(new byte[0]);
    }
}
