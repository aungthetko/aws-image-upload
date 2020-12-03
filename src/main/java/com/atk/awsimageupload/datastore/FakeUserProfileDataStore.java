package com.atk.awsimageupload.datastore;

import com.atk.awsimageupload.profile.UserProfile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FakeUserProfileDataStore {

    private static final List<UserProfile> USER_PROFILES = new ArrayList<>();

    static{
        USER_PROFILES.add(new UserProfile(UUID.fromString("56e0f1fa-1b6a-4506-b8de-b2cd53ead173"),
                "junior",
                null));
        USER_PROFILES.add(new UserProfile(UUID.fromString("37bf1f5b-5dab-4943-81b3-6329f3bddb77"),
                "senior",
                null));
    }

    public List<UserProfile> getUserProfiles(){
        return USER_PROFILES;
    }
}
