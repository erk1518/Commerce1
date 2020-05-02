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

//        send ACH object to back-end validation
//        runValidation(ach);

        System.out.println("TEXTtoPOJO process ended");
    }


    /*
    //take value from ach.txt and create an ACH Object.
    public void process(Exchange exchange) throws Exception {
        System.out.println("TEXTtoPOJO process started");
        File newFile = exchange.getIn().getBody(File.class);
        ACH ach = new ACH(newFile);
        exchange.getIn().setBody(ach, ACH.class);
        System.out.print(ach.toString());

//        send ACH object to back-end validation
//        ACHValidate(ach).run();

        System.out.println("TEXTtoPOJO process ended");
    }

     */
}
