import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nbpapi.RateTable;
import nbpapi.Table;
import nbpapi.URIGenerator;
import postapi.Post;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class PostApp {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/posts"))
                .GET()
                .build();
        final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        final List<Post> posts = mapper.readValue(response.body(), new TypeReference<List<Post>>() {
        });
        for(Post post: posts){
            System.out.println(post.getId() +" " + post.getReaded());
        }
    }
}
