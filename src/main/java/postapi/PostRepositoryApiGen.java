package postapi;

import repository.ApiRepository;

import java.io.IOException;
import java.net.URI;
import java.util.List;

public class PostRepositoryApiGen implements PostRepository{
    private ApiRepository<Post> posts = new ApiRepository<>(Post.class);
    private static final String uri = "https://jsonplaceholder.typicode.com/posts";

    @Override
    public List<Post> findAll() throws IOException, InterruptedException {
        return posts.getList(URI.create(uri));
    }

    public Post findById(int id) throws IOException, InterruptedException {
        return posts.getObject(URI.create(uri+"/"+id));
    }
}
