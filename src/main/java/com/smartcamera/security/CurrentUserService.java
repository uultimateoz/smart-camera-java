
import com.smarthome.camera.model.User;
import com.smarthome.camera.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    private final UserRepository users;

    public CurrentUserService(UserRepository users) {
        this.users = users;
    }

    public User requireCurrentUser(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()
                || "anonymousUser".equals(auth.getPrincipal())) {
            throw new AccessDeniedException("Authentication required");
        }

        return users.findByEmail(auth.getName())
                .orElseThrow(() ->
                        new AccessDeniedException("Access denied"));
    }
}
