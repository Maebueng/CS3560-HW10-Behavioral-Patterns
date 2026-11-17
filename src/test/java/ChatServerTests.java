import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
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

    // assertTrue that message was sent
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

    // assertTrue that blocked list contains target
    @Test
    public void blockUsersTest() {}

    // assertTrue that user can't send message to other user that has them blocked
    @Test
    public void blockedUserSendMessageTest() {}
}
