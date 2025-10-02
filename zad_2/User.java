package SOLUTIONS.src.zad_2;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import java.util.Objects;

public class User implements Serializable {
    private String userName;
    private String email;
    private String platform;
    private Set<String> days;

    public User(String userName, String email, String platform, Set<String> days) {
        this.userName = userName;
        this.email = email;
        this.platform = platform;
        this.days = new HashSet<>(days);
    }

    public String getUserName() { return userName; }
    public String getEmail() { return email; }
    public String getPlatform() { return platform; }
    public Set<String> getDays() { return new HashSet<>(days); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, email);
    }

    @Override
    public String toString() {
        return String.format("User: %s, Email: %s, Platform: %s, Days: %s",
                userName, email, platform, days);
    }
}