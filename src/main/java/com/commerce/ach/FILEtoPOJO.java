package com.commerce.ach;
// // http://preparationforinterview.com/preparationforinterview/camel-object-to-json-springdsl
// https://www.javainuse.com/camel/camel-marshal-unmarshal-example
// the Camel Processor class where we will be modifying the unmarshalled object.

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class FILEtoPOJO implements Processor {

    //take value from ach.txt and create an ACH Object.
    public void process(Exchange exchange) throws Exception {
        System.out.println("FILEtoPOJO process started");
        String input = exchange.getIn().getBody(String.class);
//        ACH ach = new ACH(input);
//        exchange.getIn().setBody(ach, ACH.class);
//        System.out.print(ach.toString());

//        send ACH object to back-end validation class...???

        System.out.println("FILEtoPOJO process ended");
    }
}
