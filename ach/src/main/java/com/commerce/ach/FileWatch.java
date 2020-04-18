package com.commerce.ach;
// https://camel.apache.org/components/latest/file-watch-component.html
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class FileWatch extends RouteBuilder {

    public static void run() throws Exception {
        System.out.println("FileWatch started");
        watchUIFolder();
        watchReaderFolder();
    }

    public static void watchUIFolder() throws Exception {
        //System.out.println("FileWatch started - UIFolder");
        CamelContext cc1 = new DefaultCamelContext();
        cc1.addRoutes(new FileWatch() {
            @Override
            public void configure() {
                //System.out.println("FileWatch configure() started");
                from("file-watch:C://Users/grega/SrProj/uiFolder" +
                        "?events=CREATE,DELETE,MODIFY")     // .log these events at occurrence
                        .log("File event: ${header.CamelFileEventType}"
                                + " occurred on file ${header.CamelFileName}"
                                + " at " + getTimeStamp());
            }
        });
        try{
            System.out.println("FileWatch running - UIFolder");
            cc1.start();
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    public static void watchReaderFolder() throws Exception {
        //System.out.println("FileWatch started - ReaderFolder");
        CamelContext cc2 = new DefaultCamelContext();
        cc2.addRoutes(new FileWatch() {
            @Override
            public void configure() {
                //System.out.println("FileWatch configure() started");
                from("file-watch:C://Users/grega/SrProj/readFolder" +
                        "?events=CREATE,DELETE,MODIFY")                       // .log these events at occurrence
                        .log("File event: ${header.CamelFileEventType}"
                                + " occurred on file ${header.CamelFileName}"
                                + " at " + getTimeStamp());
            }
        });
        try {
            System.out.println("FileWatch running - ReaderFolder");
            cc2.start();
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    private static String getTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }
}
