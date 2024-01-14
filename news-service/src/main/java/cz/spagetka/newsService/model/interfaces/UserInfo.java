package cz.spagetka.newsService.model.interfaces;

import cz.spagetka.newsService.model.enums.ERole;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserInfo extends UserDetails {
    String getUserId();
    String getEmail();
    ERole getRole();
}
