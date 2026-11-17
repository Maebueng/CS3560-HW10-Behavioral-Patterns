import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class SearchMessagesByUser implements Iterator<Message> {
    private List<Message> messages;
    private User targetUser;
    private int currentIndex = 0;

    public SearchMessagesByUser(List<Message> messages, User targetUser) {
        this.messages = messages;
        this.targetUser = targetUser;
        findNextValidIndex();
    }

    private void findNextValidIndex() {
        while (currentIndex < messages.size()) {
            Message msg = messages.get(currentIndex);
            boolean involvesUser = msg.getSender().equals(targetUser) ||
                    (msg.getRecipients() != null && msg.getRecipients().contains(targetUser));
            if (involvesUser) {
                break;
            }
            currentIndex++;
        }
    }

    @Override
    public boolean hasNext() {
        return currentIndex < messages.size();
    }

    @Override
    public Message next() {
        if(!hasNext()) {
            throw new NoSuchElementException();
        }
        Message nextMessage = messages.get(currentIndex);
        currentIndex++;
        findNextValidIndex();
        return nextMessage;
    }
}
