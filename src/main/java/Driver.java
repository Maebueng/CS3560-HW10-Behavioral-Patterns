import java.util.List;

public class Driver {
    public static void main(String[] args) {
        ChatServer schoolServer = new ChatServer();
        ChatHistory schoolServerHistory = new ChatHistory();
        User steve = new User("ILoveDonuts", schoolServer);
        User nashala = new User("BlueRover", schoolServer);
        User anya = new User("BookEnjoyer", schoolServer);

        schoolServer.registerUser(steve);
        schoolServer.registerUser(nashala);
        schoolServer.registerUser(anya);

        steve.sendMessage(List.of(nashala, anya), "Did you guys receive the pdf I sent?");
        nashala.sendMessage(List.of(steve, anya), "Yes, the one named 'presentation.pdf'?");
        anya.sendMessage(List.of(steve, nashala), "No, haven't been able to check my emails all day,"
         + "but will do later tonight!");

        anya.sendMessage(List.of(nashala), "Can you send me the notes for today later?");
        anya.undoLastMessage();
        anya.sendMessage(List.of(nashala), "Sorry, nevermind, Steve sent them to me already!");

        schoolServer.blockMessagesFromSpecificUser(nashala, anya);

        anya.sendMessage(List.of(nashala), "Can you see this?");

    } // end main
} // end Driver
