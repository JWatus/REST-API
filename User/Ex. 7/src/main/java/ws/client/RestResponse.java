package ws.client;


import java.util.List;
public class RestResponse {
    List<State> result;
    List<String> messages;
    public List<State> getResult() {
        return result;
    }
    public void setResult(List<State> result) {
        this.result = result;
    }
    public List<String> getMessages() {
        return messages;
    }
    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}