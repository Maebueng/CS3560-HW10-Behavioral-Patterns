import java.util.List;

public class User {
    private String username;
    private ChatServer chatServer;
    private ChatHistory chatHistory;

    public User(String username, ChatServer chatServer) {
        this.username = username;
        this.chatServer = chatServer;
        this.chatHistory = new ChatHistory();
    }

    public String getUsername() {
        return username;
    }

    public ChatHistory getChatHistory() {
        return chatHistory;
    }

    public void sendMessage(List<User> recipients, String content) {
        Message message = new Message(this, recipients, content);
        MessageMemento memento = new MessageMemento(message.getContent(), message.getTimestamp());
        chatHistory.addNewMessage(message);
        chatServer.routeMessage(this, message);
    }

    public void receiveMessage(Message message) {
        System.out.println("[" + username + " received from " + message.getSender().getUsername()
                + "]: " + message.getContent());
    }

    public void undoLastMessage() {
        Message lastMessage = chatHistory.getLastSentMessage();
        if (lastMessage != null) {
            System.out.println(username + " deleted their message: " + lastMessage.getContent());
        } else {
            System.out.println(username + " has nothing to delete");
        }
    }
}
