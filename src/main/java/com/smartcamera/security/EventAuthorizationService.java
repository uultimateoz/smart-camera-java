
import com.smarthome.camera.model.User;
import com.smarthome.camera.model.Role;
import com.smarthome.camera.repository.EventRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service("eventAuthorization")
public class EventAuthorizationService {

    private final EventRepository events;
    private final CurrentUserService current;

    public EventAuthorizationService(
            EventRepository events,
            CurrentUserService current) {
        this.events = events;
        this.current = current;
    }

    public boolean isOwner(
            Integer eventId,
            Authentication authentication) {

        if (eventId == null || authentication == null
                || !authentication.isAuthenticated()) {
            return false;
        }

        User user = current.requireCurrentUser(authentication);

        return user.getRole() == Role.RESIDENT
                && events.existsByEventIdAndOwnerUserId(
                        eventId, user.getUserId());
    }
}