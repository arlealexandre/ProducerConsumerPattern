public class Message {
    String message;

    Message (String mes) {
        this.message = mes;
    }

    Message () {
        this.message = "Default message";
    }

    public String toString() {
        return "'"+message+"'";
    }
}
