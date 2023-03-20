public class Todo extends Task {
    protected boolean toDo;

    public Todo(String taskName, boolean toDo) {
        super(taskName);
        this.toDo = toDo;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
