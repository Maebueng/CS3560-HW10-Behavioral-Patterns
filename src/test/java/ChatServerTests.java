import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.List;

public class ChatServerTests {

    @Test
    public void registeredUsersTest() {
        ChatServer mainServer = new ChatServer();
        ChatHistory messageLog = new ChatHistory();
        User user1 = new User("TheLegend27", mainServer);
        User user2 = new User("McLovin", mainServer);

        mainServer.registerUser(user1);
        mainServer.registerUser(user2);

        assertNotNull(mainServer);
    }

    @Test
    public void sendMessageTest() {
        ChatServer mainServer = new ChatServer();
        ChatHistory messageLog = new ChatHistory();
        User user1 = new User("TheLegend27", mainServer);
        User user2 = new User("McLovin", mainServer);

        mainServer.registerUser(user1);
        mainServer.registerUser(user2);

        user1.sendMessage(List.of(user2), "Hello, World!");

        assertNotNull(user1.getChatHistory().getLastSentMessage());
        assertEquals("Hello, World!", user1.getChatHistory().getLastSentMessage().getContent());
    }

    @Test
    public void blockUsersTest() {
        ChatServer mainServer = new ChatServer();
        ChatHistory messageLog = new ChatHistory();
        User user1 = new User("TheLegend27", mainServer);
        User user2 = new User("McLovin", mainServer);

        mainServer.registerUser(user1);
        mainServer.registerUser(user2);

        mainServer.blockMessagesFromSpecificUser(user1, user2);

        assertTrue(mainServer.isBlocked(user1, user2));


    }

    @Test
    public void blockedUserSendMessageTest() {
        ChatServer mainServer = new ChatServer();
        ChatHistory messageLog = new ChatHistory();
        User user1 = new User("TheLegend27", mainServer);
        User user2 = new User("McLovin", mainServer);

        mainServer.registerUser(user1);
        mainServer.registerUser(user2);

        mainServer.blockMessagesFromSpecificUser(user1, user2);

        user2.sendMessage(List.of(user1), "Can you see me?");

        assertNull(user1.getChatHistory().getLastSentMessage());
    }

    @Test
    public void mementoUndoHistoryTest() {
        ChatServer mainServer = new ChatServer();
        ChatHistory messageLog = new ChatHistory();
        User user1 = new User("TheLegend27", mainServer);
        User user2 = new User("McLovin", mainServer);

        mainServer.registerUser(user1);
        mainServer.registerUser(user2);

        String messageContent = "Message test";

        user1.sendMessage(List.of(user2), messageContent);

        Message lastMessage = user1.getChatHistory().getLastSentMessage();

        assertNotNull(lastMessage, "Chat history should contain the sent message");
        assertEquals(messageContent, lastMessage.getContent(), "Saved memento content should match");

        user1.undoLastMessage();

        assertNull(user1.getChatHistory().getLastSentMessage(), "Chat history should be empty after undo");
    }

    @Test
    public void testCustomIteratorByUser() {
        ChatServer mainServer = new ChatServer();
        User user1 = new User("TheLegend27", mainServer);
        User user2 = new User("McLovin", mainServer);
        User user3 = new User("Sidekick", mainServer);

        mainServer.registerUser(user1);
        mainServer.registerUser(user2);
        mainServer.registerUser(user3);

        // user1 sends messages to different users
        user1.sendMessage(List.of(user2), "Message to McLovin");
        user1.sendMessage(List.of(user3), "Message to Sidekick");
        user2.sendMessage(List.of(user1), "Reply from McLovin");

        // Iterate through user1's history filtered specifically by user2
        Iterator<Message> iterator = user1.iterator(user2);

        boolean foundUser2Message = false;
        int messageCount = 0;

        while (iterator.hasNext()) {
            Message msg = iterator.next();
            messageCount++;
            if (msg.getContent().equals("Message to McLovin") || msg.getContent().equals("Reply from McLovin")) {
                foundUser2Message = true;
            }
        }

        assertTrue(foundUser2Message, "Iterator should find messages involving user2");
        assertTrue(messageCount > 0, "Iterator should return messages");
    }
}
