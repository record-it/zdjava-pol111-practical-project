package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import postapi.Post;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ApiRepository<T> {
    private HttpClient client = HttpClient.newHttpClient();
    private ObjectMapper mapper = new ObjectMapper();
    public List<T> getList(URI uri) throws IOException, InterruptedException {
        final HttpResponse<String> response = getStringHttpResponse(uri);
        final List<T> result = mapper.readValue(response.body(), new TypeReference<List<T>>() {});
        return result;
    }

    public T getObject(URI uri) throws IOException, InterruptedException {
        final HttpResponse<String> response = getStringHttpResponse(uri);
        final T result = mapper.readValue(response.body(), new TypeReference<T>() {});
        return result;
    }

    private HttpResponse<String> getStringHttpResponse(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

}
