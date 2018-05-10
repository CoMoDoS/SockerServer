package Start;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable {
    private static final long serialVersionUID = -5399605122490343339L;

    private String A;
    private String B;
    private List<Message> messageList;

    public Message(String firstNumber, String string ){
        this.A = firstNumber;
        this.B = string;
    }

    public String getA() {
        return A;
    }

    public String getB() {
        return B;
    }

    public void addList(Message m)
    {
        messageList.add(m);
    }

    public List<Message> getMessageList()
    {
        return this.messageList;
    }
}