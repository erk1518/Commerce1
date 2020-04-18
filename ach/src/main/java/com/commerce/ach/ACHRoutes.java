package com.commerce.ach;
//alternative URI; Camel uses it’s own non-standard file URI format: file:// + <directory path> + ? + fileName= + <filename> + & + <other optional key=value params>

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import java.io.File;

public abstract class ACHRoutes extends RouteBuilder {

    public static void run() throws Exception {
        System.out.println("ACHRoutes started");
        moveTXTtoREAD();        //files are moved from uiFolder to readFolder;
        readTXTtoPOJO();        //.txt files are read from readFolder to POJO; consumed copies in dir:readFolder/.camel
    }

    private static void moveTXTtoREAD() throws Exception {
        System.out.println("moveTXTtoREAD started");
        CamelContext ctx2 = new DefaultCamelContext();          // establish the Camel Context
        ctx2.addRoutes(new ACHRoutes() {                        // add the route from().to()
            @Override
            public void configure() {                           // override the default camel routeBuilder
                from("file:C://Users/grega/SrProj/uiFolder" +   // from.(uiFolder)
                        "?antInclude=**/*.txt" +                // only move .txt files
                        "&delete=true")                        // delete consumer file
//                        "&delay=1000"                        // poll at millisecond intervals (1000 = 1 second)
//                        "&concurrentConsumers=2" +
//                        "&preMove=staging" +                    // move the input file into a staging folder before the Route starts the processing
//                        "&move=.completed")                     // when it’s done, move it to a .completed folder
                .to("file:C://Users/grega/SrProj/readFolder");  // .to(readFolder)
            }
        });
        try {
//            System.out.println("moveTXTtoREAD running");
            ctx2.start();    //initiate Camel
//            Thread.sleep(60 * 999);
//            ctx2.stop();
//            System.out.println("moveTXTtoREAD ended");
        }
        catch (Exception e) { e.printStackTrace(); }
    }


    private static void readTXTtoPOJO() throws Exception {
        System.out.println("readTXTtoPOJO started");
        CamelContext ctx1 = new DefaultCamelContext();
        ctx1.addRoutes(new ACHRoutes() {
            @Override
            public void configure() {
                from("file:C://Users/grega/SrProj/readFolder/" +
                        "?antInclude=**/*.txt")                        // only read .txt files
//                        "&delay=1000")                                 // poll at millisecond intervals (1000 = 1 second)
//                        "&preMove=staging" +                            // move the input file into a staging folder before the Route starts the processing
//                        "&move=.completed")                             // when it’s done, move it to a .completed folder
                        .convertBodyTo(File.class, "UTF-8")   // output should be File type
                        .process(new TEXTtoPOJO());                     // send output to processor
            }
        });
        try {
//            System.out.println("readTXTtoPOJO running");
            ctx1.start();
//            Thread.sleep(6000); // Maybe sleep a little here
//            ctx1.stop();
//            System.out.println("readTXTtoPOJO ended");
        }
        catch (Exception e) { e.printStackTrace(); }
    }


    private static void readFILEtoPOJO() throws Exception {
        System.out.println("readTXTtoPOJO started");
        CamelContext ctx3 = new DefaultCamelContext();
        ctx3.addRoutes(new ACHRoutes() {
            @Override
            public void configure() {
                from("file:C://Users/grega/SrProj/readFolder/" +
                        "?antInclude=**/*.txt" +                        // only read .txt files
                        "&concurrentConsumers=2" +
                        "&delay=1000")                                 // poll at millisecond intervals (1000 = 1 second)
//                        "&preMove=staging" +                            // move the input file into a staging folder before the Route starts the processing
//                        "&move=.completed")                             // when it’s done, move it to a .completed folder
                        .convertBodyTo(String.class, "UTF-8")   // output should be String type
                        .process(new FILEtoPOJO());                     // send output to processor
            }
        });

        try {
//            System.out.println("readTXTtoPOJO running");
            ctx3.start();
//            Thread.sleep(4000); // Maybe sleep a little here
//            ctx3.stop();
//            System.out.println("readTXTtoPOJO ended");
        }
        catch (Exception e) { e.printStackTrace(); }
    }


    private static void readTXTtoMSG() throws Exception {
        System.out.println("readTXTtoMSG started");
        CamelContext ctx1 = new DefaultCamelContext();
        ctx1.addRoutes(new ACHRoutes() {
            @Override
            public void configure() {
                from("file:C://Users/grega/SrProj/readFolder/" +
                        "?antInclude=**/*.txt" +                        // only read .txt files
                        "&concurrentConsumers=2" +
                        "&delay=1000" +                                 // poll at millisecond intervals (1000 = 1 second)
                        "&preMove=staging" +                            // move the input file into a staging folder before the Route starts the processing
                        "&move=.completed")                             // when it’s done, move it to a .completed folder
                        .split(body().tokenize("\n"))                           // new as of 3-28-20, try file stream line-by-line
                        .streaming()                                            // new as of 3-28-20, try file stream line-by-line
                        .process( msg -> {                                       // new as of 3-28-20, try file stream line-by-line
                            String line = msg.getIn().getBody(String.class);    // new as of 3-28-20, try file stream line-by-line
//                            LOG.info("Processing line: " + line);             // new as of 3-28-20, try file stream line-by-line
                            System.out.println("Processing line: " + line);     // new as of 3-28-20, try file stream line-by-line
                        });
            }
        });
        try {
//            System.out.println("readTXTtoMSG running");
            ctx1.start();
//            Thread.sleep(4000); // Maybe sleep a little here
//            ctx1.stop();
//            System.out.println("readTXTtoMSG ended");
        }
        catch (Exception e) { e.printStackTrace(); }
    }


        // https://www.javainuse.com/camel/camel-marshal-unmarshal-example
    private static void readTXTtoJSON() throws Exception {
        System.out.println("readTXTtoJSON started");
        CamelContext ctx6 = new DefaultCamelContext();
        ctx6.addRoutes(new ACHRoutes() {
            @Override
            public void configure() {
//                from("file:C://Users/grega/SrProj/readFolder/?antInclude=**/*.txt")
//                        .convertBodyTo(JacksonDataFormat, "UTF-8")
//                        .process(new ACHProcessor());
            }
        });
        try {
            System.out.println("readTXTtoJSON running");
            ctx6.start();
//            Thread.sleep(4000); // Maybe sleep a little here
//            ctx6.stop();
//            System.out.println("readTXTtoJSON ended");
        }
        catch (Exception e) { e.printStackTrace(); }
    }


    //https://fabian-kostadinov.github.io/2016/01/10/reading-from-and-writing-to-files-in-apache-camel/
    //If the files are in plain text format then the RouteBuilder's 'from' method is the best choice
    private static void readFromFile() throws Exception {
        System.out.println("ReadFromFiles started");
        CamelContext ctx3 = new DefaultCamelContext();
        ctx3.addRoutes(new ACHRoutes() {
            @Override
            public void configure() {
                from("file:C://Users/grega/SrProj/readFolder/" +
                        "?fileName=MyFile.txt" +
                        "&charset=utf-8")
                        .to("file:C://Users/grega/SrProj/backendFolder/" +
                                "?fileName=MyFile.txt" +
                                "&charset=utf-8");
            }
        });
        try {
            System.out.println("ReadFromFiles running");
            ctx3.start();
//            Thread.sleep(4000); // Maybe sleep a little here
//            ctx3.stop();
//            System.out.println("ReadFromFiles ended");
        }
        catch (Exception e) { e.printStackTrace(); }
    }


    private static void readToString() throws Exception {
        System.out.println("ReadToString started");
//        String readCharset = "&charset=utf-8";      //add a charset parameter to specify the file encoding to be used
        CamelContext ctx4 = new DefaultCamelContext();
        ctx4.addRoutes(new ACHRoutes() {
            @Override
            public void configure() {
                from("file:C://Users/grega/SrProj/readFolder/")
                        .convertBodyTo(String.class, "UTF-8")
                        .to("file:C://Users/grega/SrProj/backendFolder/");      //for single file, append this text: '?fileName=MyFile.txt'
            }
        });
        try {
            System.out.println("ReadToString running");
            ctx4.start();
//            Thread.sleep(4000); // Maybe sleep a little here
//            ctx4.stop();
//            System.out.println("ReadToString ended");
        }
        catch (Exception e) { e.printStackTrace(); }
    }


    private static void copyFiles() throws Exception {
        System.out.println("CopyFiles started");
        String copyFromFolder = "file:C://Users/grega/SrProj/uiFolder";
        String copyToFolder = "file:C://Users/grega/SrProj/readFolder";
        String noopCopyFunct = "?noop=true";
        CamelContext ctx1 = new DefaultCamelContext();      // establish the Camel Context
        ctx1.addRoutes(new ACHRoutes() {        // add the route
            @Override
            public void configure() {
                from(copyFromFolder + noopCopyFunct).to(copyToFolder);   //copies files
            }
        });
        try {
            System.out.println("CopyFiles running");
            ctx1.start();        //initiate Camel
//            Thread.sleep(5 * 60 * 1000);
//            ctx1.stop();
//            System.out.println("CopyFiles ended");
        } catch (Exception e) { e.printStackTrace(); }
    }

}
