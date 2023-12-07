package obj1;

public class Message {

    private String message; // Un message est une chaine de caractère

    public Message(String mes) {
        this.message = mes;
    }

    public Message() {
        this.message = "Default message";
    }

    public String toString() {
        return "'" + message + "'";
    }
}
