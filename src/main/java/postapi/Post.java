package postapi;
import java.time.LocalDateTime;

public class Post {
    private int id;
    private int userId;
    private String title;
    private String body;
    private LocalDateTime readed;

    public Post() {
        readed = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public LocalDateTime getReaded() {
        return readed;
    }
}
