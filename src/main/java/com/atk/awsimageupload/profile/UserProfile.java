package com.atk.awsimageupload.profile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class UserProfile {

    @NotNull(message = "Id can not be null")
    private final UUID userProfileId;

    @NotNull
    @NotBlank
    private final String userName;

    private String userProfileLinkImage;

    public UserProfile(UUID userProfileId, String userName, String userProfileLinkImage) {
        this.userProfileId = userProfileId;
        this.userName = userName;
        this.userProfileLinkImage = userProfileLinkImage;
    }

    public void setUserProfileLinkImage(String userProfileLinkImage){
        this.userProfileLinkImage = userProfileLinkImage;
    }

    public UUID getUserProfileId() {
        return userProfileId;
    }

    public String getUserName() {
        return userName;
    }

    public Optional<String> getUserProfileLinkImage() {
        return Optional.ofNullable(userProfileLinkImage);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return Objects.equals(userProfileId, that.userProfileId) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(userProfileLinkImage, that.userProfileLinkImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userProfileId, userName, userProfileLinkImage);
    }
}
