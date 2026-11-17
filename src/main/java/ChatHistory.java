import java.util.List;
import java.util.ArrayList;

public class ChatHistory {
    private List<Message> messages = new ArrayList<>();

    public void addNewMessage(Message message) {
        messages.add(message);
    }

    public Message getLastSentMessage() {
        if (messages.isEmpty()) {
            return null;
        } else {
            return messages.get(messages.size() - 1);
        }
    }
}
