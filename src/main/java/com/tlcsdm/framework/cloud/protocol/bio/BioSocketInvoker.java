package com.tlcsdm.framework.cloud.protocol.bio;

import com.tlcsdm.framework.cloud.Invocation;
import com.tlcsdm.framework.cloud.Invoker;
import com.tlcsdm.framework.cloud.URL;

public class BioSocketInvoker implements Invoker {

    private URL url;

    public BioSocketInvoker(URL url) {
        this.url = url;
    }

    @Override
    public Object invoke(Invocation invocation) {
        BioSocketClient client = new BioSocketClient();
        return client.send(url.getHostname(), url.getPort(), invocation);
    }

}
