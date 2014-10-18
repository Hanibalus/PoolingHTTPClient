import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

public class clientThread extends  Thread{

    private final CloseableHttpClient httpClient;
    private final HttpContext context;
    private final HttpGet httpget;

    public clientThread(CloseableHttpClient httpClient, HttpGet httpget) {
        this.httpClient = httpClient;
        this.context = HttpClientContext.create();
        this.httpget = httpget;
    }

    @Override
    public void run() {
        try {
            CloseableHttpResponse response = httpClient.execute(httpget, context);
            try {
                System.out.println(httpget.getURI() + " status " + response.getStatusLine().getStatusCode());
                HttpEntity entity = response.getEntity();

            } finally {
                response.close();
            }
        } catch (ClientProtocolException ex) {
            System.out.println("ClientProtocolException");
        } catch (IOException ex) {
            System.out.println("IOException" );
            ex.printStackTrace();
        }
    }

}
