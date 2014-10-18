import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HTTPClientTest {

    public static void  main(String[] argv) throws IOException {
        System.out.println("Start");

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(20);
        HttpHost google = new HttpHost("http://www.google.com/", 80);
        cm.setMaxPerRoute(new HttpRoute(google), 50);

        //CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpClient httpclient = HttpClients.custom()
                 .setConnectionManager(cm)
                .build();
        HttpClientContext context = HttpClientContext.create();
        HttpGet httpget = new HttpGet("http://www.google.com/");

        CloseableHttpResponse response = httpclient.execute(httpget,context);
        try {
            HttpHost target = context.getTargetHost();

            System.out.println("HttpHost " + target);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                long len = entity.getContentLength();
                //if (len != -1 && len < 2048) {
                    System.out.println(EntityUtils.toString(entity));
                //} else {
                    // Stream content out
                //}
            }
        } finally {
            response.close();


        }

        httpclient.close();

        System.out.println("End");
    }

}
