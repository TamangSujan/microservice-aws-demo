package notification;

public class NotificationProperties {
    private final String notificationId;
    private final String topic;
    public NotificationProperties(String notificationId, String topic){
        this.notificationId = notificationId;
        this.topic = topic;
    }

    public String getNotificationId(){ return notificationId; }
    public String getTopic(){ return topic; }
}
