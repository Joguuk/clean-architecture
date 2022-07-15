package com.jo.clean.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jo.clean.kakaobiz.domain.BizForm;
import com.jo.clean.kakaobiz.domain.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

@Slf4j
public class MultiHttpClientConnThread extends Thread {
    private final CloseableHttpClient client;
    private final HttpGet get;

    private PoolingHttpClientConnectionManager connManager;
    private int leasedConn;

    private Data data;

    public MultiHttpClientConnThread(final CloseableHttpClient client, final HttpGet get, final PoolingHttpClientConnectionManager connManager) {
        this.client = client;
        this.get = get;
        this.connManager = connManager;
        leasedConn = 0;
    }

    MultiHttpClientConnThread(final CloseableHttpClient client, final HttpGet get) {
        this.client = client;
        this.get = get;
    }

    // API

    final int getLeasedConn() {
        return leasedConn;
    }

    //

    @Override
    public final void run() {
        try {
            log.debug("Thread Running: " + getName());
            log.debug("Thread Running: " + getName());

            if (connManager != null) {
                log.info("Before - Leased Connections = " + connManager.getTotalStats().getLeased());
                log.info("Before - Available Connections = " + connManager.getTotalStats().getAvailable());
            }

            final HttpResponse response = client.execute(get);

            if (connManager != null) {
                leasedConn = connManager.getTotalStats().getLeased();
                log.info("After - Leased Connections = " + connManager.getTotalStats().getLeased());
                log.info("After - Available Connections = " + connManager.getTotalStats().getAvailable());
            }

            HttpEntity entity = response.getEntity();
//            log.info("entity => {}", EntityUtils.toString(entity));
            ObjectMapper objectMapper = new ObjectMapper();
            BizForm result = objectMapper.readValue(EntityUtils.toString(entity), BizForm.class);
            log.info("====================");
            log.info("result => {}", result);
            log.info("====================");

            EntityUtils.consume(response.getEntity());
        } catch (final IOException ex) {
            log.error("", ex);
        }
    }
}
