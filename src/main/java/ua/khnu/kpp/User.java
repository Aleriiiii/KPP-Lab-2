package ua.khnu.kpp;

import java.util.Objects;

public final class User {
    private final long id;
    private final String name;

    public User(long id, String name) {
        if (id <= 0 || name == null || name.isBlank()) {
            throw new IllegalArgumentException("Invalid user parameters");
        }
        this.id = id;
        this.name = name;
    }

    public long id() { return id; }
    public String name() { return name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}