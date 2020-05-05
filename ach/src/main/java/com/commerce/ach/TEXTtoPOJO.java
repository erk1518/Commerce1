package com.commerce.ach;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import java.io.File;

public class TEXTtoPOJO implements Processor {

    public void process(Exchange exchange) throws Exception {
        System.out.println("TEXTtoPOJO process started");
        File newFile = exchange.getIn().getBody(File.class);
        ACH4 ach = new ACH4(newFile);
        exchange.getIn().setBody(ach, ACH4.class);
        System.out.print(ach.toString());

        System.out.println("TEXTtoPOJO process ended");
    }

}
