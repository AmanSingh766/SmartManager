package com.scm.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    private String userId;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String about;

    private String phoneNumber;

    private String profilePic;

    private boolean emailVerified;
    private boolean phoneVerified;
    private boolean enabled;

    @Enumerated(EnumType.STRING)  // Use enum instead of String
    private Providers provider;

    private String providerUserId;

    private String emailToken;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleList;

    // -------------------- Getters and Setters --------------------

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getAbout() { return about; }
    public void setAbout(String about) { this.about = about; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getProfilePic() { return profilePic; }
    public void setProfilePic(String profilePic) { this.profilePic = profilePic; }

    public boolean isEmailVerified() { return emailVerified; }
    public void setEmailVerified(boolean emailVerified) { this.emailVerified = emailVerified; }

    public boolean isPhoneVerified() { return phoneVerified; }
    public void setPhoneVerified(boolean phoneVerified) { this.phoneVerified = phoneVerified; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public Providers getProvider() { return provider; }
    public void setProvider(Providers provider) { this.provider = provider; }

    public String getProviderUserId() { return providerUserId; }
    public void setProviderUserId(String providerUserId) { this.providerUserId = providerUserId; }

    public String getEmailToken() { return emailToken; }
    public void setEmailToken(String emailToken) { this.emailToken = emailToken; }

    public List<String> getRoleList() { return roleList; }
    public void setRoleList(List<String> roleList) { this.roleList = roleList; }
}
