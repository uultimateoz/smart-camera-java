
import com.smarthome.camera.model.Event;
import java.time.LocalDateTime;

public record EventResponse(
        Integer eventId,
        String cameraId,
        LocalDateTime captureTime,
        String location,
        String mediaUrl) {

    public static EventResponse from(Event e) {
        return new EventResponse(
                e.getEventId(),
                e.getCameraId(),
                e.getCaptureTime(),
                e.getLocation(),
                e.getMediaUrl());
    }
}