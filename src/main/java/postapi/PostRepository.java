package postapi;

import java.io.IOException;
import java.util.List;

public interface PostRepository {
    List<Post> findAll() throws IOException, InterruptedException;
}
