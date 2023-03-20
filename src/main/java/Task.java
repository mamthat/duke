public class Task {
    protected String taskName;
    protected Boolean status;

    public Task(){
        this("Default Task");
    }

    public Task(String taskName) {
        this.taskName = taskName;
        this.status = false;
    }

    public String getTaskName() {
        return taskName;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setItemName(String taskName) {
        this.taskName = taskName;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}