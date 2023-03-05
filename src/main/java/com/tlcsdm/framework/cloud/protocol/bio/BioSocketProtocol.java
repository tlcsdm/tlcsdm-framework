package com.tlcsdm.framework.cloud.protocol.bio;

import com.tlcsdm.framework.cloud.Invoker;
import com.tlcsdm.framework.cloud.Protocol;
import com.tlcsdm.framework.cloud.URL;
import com.tlcsdm.framework.cloud.register.LocalRegister;
import com.tlcsdm.framework.cloud.register.RemoteMapRegister;

public class BioSocketProtocol implements Protocol {
    private BioSocketServer server;

    @Override
    public void export(URL url) {
        LocalRegister.register(url.getInterfaceName(), url.getVersion(), url.getImplClass());
        doExport(url);
    }

    @Override
    public void export(URL url, Object instance) {
        LocalRegister.register(url.getInterfaceName(), url.getVersion(), instance);
        doExport(url);
    }

    @Override
    public Invoker refer(URL url) {
        return new BioSocketInvoker(url);
    }

    public void doExport(URL url) {
        RemoteMapRegister.register(url);
        if (server == null) {
            server = new BioSocketServer();
            new Thread(() -> server.start(url.getHostname(), url.getPort())).start();
        }
    }

}
