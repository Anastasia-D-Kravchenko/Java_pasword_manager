public class Entry {
    private final String source; // = title
    private final String username;
    private String password;
    private final String url;
    private final String notes;

    public Entry(String source, String username, String password, String url, String notes) {
        this.source = source;
        this.username = username;
        this.password = password;
        this.url = url;
        this.notes = notes;
    }

    public String getSource() {
        return source;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public String getNotes() {
        return notes;
    }

    @Override
    public String toString() {
        return "Source: " + source + (username.isEmpty() ? "" : ", Username: " + username) + ", Password: " + password + ", URL: " + url + ", Notes: " + notes;
    }
}