package task;

class Task {
    private String priority;
    private int timeMark;

    public Task(String priority, int timeMark) {
        this.priority = priority;
        this.timeMark = timeMark;
    }

    public String getPriority() {
        return priority;
    }

    public int getTimeMark() {
        return timeMark;
    }
}
