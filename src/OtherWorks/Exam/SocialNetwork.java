package OtherWorks.Exam;

public class SocialNetwork {
    public static void main(String[] args) {
        Informative[] informatives = new Informative[]{
                new User(1, "John", 34),
                new DefaultMessage("Hello world!", new User(2, "Dan", 24))
        };

        for (Informative i : informatives) {
            System.out.println(i.getInfo());
        }

        User user = new User(3, "Dave", 41);
        user.sendMessage("New message");

        Message message = new DefaultMessage("Hi", user);
        message.edit("Bye");
        user.sendMessage(message);

        Group group = new Group(5, new User[] {user});
        System.out.println(group.getMembersCount());
        System.out.println(group.isGroupFull());


    }
}

interface Sendable {
    void send();
}

interface Informative {
    String getInfo();
}

interface Editable {
    void edit(String text);
}

interface Saveable {
    void save();
}

abstract class Message implements Sendable, Editable ,Informative {
    String text;
    int length;
    Informative sender;

    public Message(String text, Informative sender) {
        this.text = text;
        this.length = text.length();
        this.sender = sender;
    }

    @Override
    public abstract void send();

    @Override
    public String getInfo() {
        return "Message text: " + text +
                "\nSender: " + sender.getInfo();
    }

    @Override
    public void edit(String text) {
        this.text = text;
        length = text.length();
    }
}

class WarningMessage extends Message{
    public WarningMessage(String text, User sender) {
        super(text, sender);
    }

    @Override
    public void send() {
        // sending logic
    }

    @Override
    public String getInfo() {
        return super.getInfo() + "\nType: warning message";
    }
}

class UneditableMessage extends Message {
    public UneditableMessage(String text, User sender) {
        super(text, sender);
    }

    @Override
    public void send() {
        // sending logic
    }

    @Override
    public String getInfo() {
        return super.getInfo() + "\nType: uneditable message";
    }

    @Override
    public void edit(String text) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}

class DefaultMessage extends Message {
    public DefaultMessage(String text, User sender) {
        super(text, sender);
    }

    @Override
    public void send() {
        // logic to send messages
    }

    @Override
    public String getInfo() {
        return super.getInfo() + "\nType: default message";
    }

    @Override
    public void edit(String text) {
        this.text = text;
        length = text.length();
    }
}


class User implements Informative {
    private final int id;
    private String name;
    private int age;

    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    void sendMessage(String text) {
        Message message = new DefaultMessage(text, this);
        message.send();
    }

    void sendMessage(Message message) {
        message.send();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String getInfo() {
        return "ID: " + id +
                "\nName: " + name +
                "\nAge: " + age;
    }
}

//

class Group {
    int id;
    User[] users = new User[256];
    int freeSpot = 0;

    Group(int id) {
        this.id = id;
    }

    Group(int id, User[] users) {
        this(id);
        for (int i = 0; i < users.length; i++) {
            this.users[i] = users[i];
        }
        freeSpot = users.length;
    }

    void addNewMember(User user) {
        if (freeSpot == -1) return;
        users[freeSpot] = user;
    }

    int getMembersCount() {
        return freeSpot;
    }

    boolean isGroupFull() {
        return freeSpot == -1;
    }
}

class Chat implements Saveable {
    Message[] messages = new Message[4096];
    int freeSpot = 0;

    void addMessage(Message message) {
        if (isFull()) {
            save();
            clean();
        }
        messages[freeSpot++] = message;
        if (freeSpot == message.length) freeSpot = -1;
    }

    boolean isFull() {
        return freeSpot == -1;
    }

    void clean() {
        messages = new Message[4096];
        freeSpot = 0;
    }

    @Override
    public void save() {
        // saving chat history in archive before cleaning
    }
}

class Post implements Informative {
    String text;
    Object image;
    Informative sender;
    boolean isEdited;

    public Post(String text, Object image, Informative sender) {
        this.text = text;
        this.image = image;
        this.sender = sender;
    }

    void editText(String newText) {
        text = newText;
        isEdited = true;
    }

    void editImage(Object newImage) {
        image = newImage;
        isEdited = true;
    }

    int getTextLength() {
        return text.length();
    }

    @Override
    public String getInfo() {
        return "Text: " + text + "\nSender: " + sender.getInfo() + "\nIs edited: " + isEdited;
    }
}

