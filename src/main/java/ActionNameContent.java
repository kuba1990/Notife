public class ActionNameContent implements QueueNotify {
    private String action;
    private String name;
    private String content;

    public ActionNameContent(String action, String name, String content) {
        this.action = action;
        this.name = name;
        this.content = content;
    }

    @Override
    public String getAction() {
        return action;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getContent() {
        return content;
    }

}
