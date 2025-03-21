package notification;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.sns.Topic;

public abstract class NotificationService {
    private final Topic notificationTopic;
    public NotificationService(Stack stack, NotificationProperties properties){
        notificationTopic = Topic.Builder.create(stack, properties.getNotificationId())
                .topicName(properties.getTopic())
                .build();
    }

    public String getTopicArn(){ return notificationTopic.getTopicArn(); }
    public abstract void subscribe(String subscriber);
    protected Topic getNotificationTopic(){ return notificationTopic; }
}
