package com.commerce.ach.validation;
//alternative URI; Camel uses it’s own non-standard file URI format: file:// + <directory path> + ? + fileName= + <filename> + & + <other optional key=value params>

import com.commerce.ach.camel.TEXTtoPOJO;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import java.io.File;

public abstract class ACHRoutes extends RouteBuilder {

    public static void run() throws Exception {
        System.out.println("ACHRoutes started");
        readTXTtoPOJO();        //.txt files are read from readFolder to POJO; consumed copies in dir:readFolder/.camel
    }

    private static void readTXTtoPOJO() throws Exception {
        System.out.println("readTXTtoPOJO started");
        CamelContext ctx1 = new DefaultCamelContext();
        ctx1.addRoutes(new ACHRoutes() {
            @Override
            public void configure() {
                from("file:upload-dir" +
                        "?antInclude=**/*.txt")                        //only read .txt files
                        .convertBodyTo(File.class, "UTF-8")   // output should be File type
                        .process(new TEXTtoPOJO());                     // send output to processor
            }
        });
        try {
            ctx1.start();
        }
        catch (Exception e) { e.printStackTrace(); }
    }

}
