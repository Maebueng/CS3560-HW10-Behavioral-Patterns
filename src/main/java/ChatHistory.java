import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ChatHistory implements IterableByUser {
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

    public void removeLastMessage() {
        if (!messages.isEmpty()) {
            messages.remove(messages.size() - 1);
        }
    }

    public List<Message> filterMessageFromUser(User targetUser) {
        System.out.println("--- Showing all messages from: " + targetUser.getUsername() + "---");
        return messages.stream().filter(msg ->
                    (msg.getSender().equals(targetUser)) ||
                    (msg.getRecipients() != null && msg.getRecipients().contains(targetUser))
                ).collect(Collectors.toList());
    }

    @Override
    public Iterator<Message> iterator(User userToSearchWith) {
        return new SearchMessagesByUser(messages, userToSearchWith);
    }

}
