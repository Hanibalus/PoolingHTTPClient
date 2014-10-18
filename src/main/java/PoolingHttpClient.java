import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class PoolingHttpClient {

    public static void main(String[] argv) throws InterruptedException {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();

        IdleConnectionMonitorThread idleConnectionMonitorThread = new IdleConnectionMonitorThread(cm);
        idleConnectionMonitorThread.start();

        String[] urisToGet = {
                "http://www.google.com/",
                "http://www.yahoo.com/",
                "http://www.orange.com/",
                "http://www.eugenpopa.ro/test",
                "http://www.gsp.ro/"

        };

        clientThread[] threads = new clientThread[urisToGet.length];
        for (int i = 0; i < threads.length; i++) {
            HttpGet httpget = new HttpGet(urisToGet[i]);
            threads[i] = new clientThread(httpClient, httpget);
        }

        for (int j = 0; j < threads.length; j++) {
            threads[j].start();
        }

        for (int j = 0; j < threads.length; j++) {
            threads[j].join();
        }

        idleConnectionMonitorThread.shutdown();
    }

}
