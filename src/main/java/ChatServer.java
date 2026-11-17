import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatServer {

    private Map<String, User> registeredUsers = new HashMap<>();
    private Map<User, List<User>> blockedUsers = new HashMap<>();

    public void registerUser(User user) {
        registeredUsers.put(user.getUsername(), user);
        System.out.println(user.getUsername() + " has joined the server");
    }

    public void unregisterUser(User user) {
        registeredUsers.remove(user.getUsername());
        System.out.println(user.getUsername() + " has left the server");
    }

    public void routeMessage(User sender, Message message) {
        for (User recipient : message.getRecipients()) {
            if (isBlocked(recipient, sender)) {
                System.out.println("[Server]: Message blocked, " + recipient.getUsername() +
                        " has blocked " + sender.getUsername());
            } else {
                recipient.receiveMessage(message);
            }
        }
    }

    public void blockMessagesFromSpecificUser(User owner, User userToBlock) {
        blockedUsers.putIfAbsent(owner, new ArrayList<>());
        blockedUsers.get(owner).add(userToBlock);
        System.out.println(owner.getUsername() + " blocked " + userToBlock.getUsername());
    }

    public boolean isBlocked(User owner, User target) {
        // Accesses map of Owner to check if target is in blocked list
        List<User> blockedList = blockedUsers.get(owner);

        // checks to see if list is populated AND has target, if so return the blockedList
        return blockedList != null && blockedList.contains(target);
    }
}
