public class Task {
    private String taskName;
    private Boolean status;

    public Task(){
        this("Default Item", false);
    }

    public Task(String taskName, Boolean status) {
        this.taskName = taskName;
        this.status = status;
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