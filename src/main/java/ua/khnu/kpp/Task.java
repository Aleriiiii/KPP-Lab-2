package ua.khnu.kpp;

import java.time.LocalDate;

public final class Task {
    private final long id;
    private final String title;
    private final Project project;
    private final LocalDate created;
    private final LocalDate dueDate;
    private final CompletionPolicy policy;

    private Status status;
    private User assignee;

    public Task(long id, String title, Project project, LocalDate created, LocalDate dueDate, CompletionPolicy policy) {
        if (id <= 0 || title == null || title.isBlank() || project == null || created == null || dueDate == null || policy == null) {
            throw new IllegalArgumentException("Invalid task parameters");
        }
        if (dueDate.isBefore(created)) {
            throw new IllegalArgumentException("Due date cannot be before creation date");
        }
        this.id = id;
        this.title = title;
        this.project = project;
        this.created = created;
        this.dueDate = dueDate;
        this.policy = policy;
        this.status = Status.NEW;
        this.assignee = null;
    }

    public long id() { return id; }
    public String title() { return title; }
    public Project project() { return project; }
    public LocalDate created() { return created; }
    public LocalDate due() { return dueDate; }
    public CompletionPolicy policy() { return policy; }
    public Status status() { return status; }
    public User assignee() { return assignee; }

    public void assign(User user) {
        if (status != Status.NEW) {
            throw new IllegalStateException("Cannot assign task in current status");
        }
        if (user == null) {
            throw new IllegalArgumentException("Assignee cannot be null");
        }
        this.assignee = user;
    }

    public void start() {
        if (status != Status.NEW) {
            throw new IllegalStateException("Task must be NEW to start");
        }
        if (assignee == null) {
            throw new IllegalStateException("Cannot start task without an assignee");
        }
        this.status = Status.ACTIVE;
    }

    public void complete(LocalDate date, int units, int defects, boolean approved) {
        if (status != Status.ACTIVE) {
            throw new IllegalStateException("Task must be ACTIVE to complete");
        }
        if (date == null || date.isBefore(created) || units < 0 || defects < 0) {
            throw new IllegalArgumentException("Invalid completion arguments");
        }
        boolean allowed = policy.allows(units, defects, approved);
        if (!allowed) {
            throw new IllegalStateException("Completion policy denied completion");
        }
        this.status = Status.DONE;
    }
}