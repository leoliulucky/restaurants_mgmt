package com.benxiaopao.thrift;

import com.benxiaopao.thrift.service.TSRestaurantService;
import lombok.Getter;
import lombok.Setter;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * Thrift客户端
 *
 * Created by liupoyang
 * 2019-04-21
 */
public class ThriftClient {
    private TSRestaurantService.Client client;
    private TBinaryProtocol protocol;
    private TSocket transport;
    @Setter
    @Getter
    private String host;
    @Setter
    @Getter
    private int port;

    public void init() {
        transport = new TSocket(host, port);
        protocol = new TBinaryProtocol(transport);
        client = new TSRestaurantService.Client(protocol);
    }

    public TSRestaurantService.Client getThriftService() {
        return client;
    }

    public void open() throws TTransportException {
        transport.open();
    }

    public void close() {
        transport.close();
    }
}
