package com.kafkastorm.example.jetty;

import com.kafkastorm.example.subscriber.ImageGenerator;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.resource.Resource;

/**
 * Created by anton.l on 12/20/2015.
 */
public class ImageProvider extends AbstractHandler {

    static int i = 0;

    public synchronized void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response){
        ImageGenerator.getWordsAndWriteToFile();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("src\\main\\webapp\\wordcloud_circle.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(ImageGenerator.getWordsAndWriteToFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) throws Exception {


        Server server = new Server(8080);


        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);

        resource_handler.setResourceBase("src\\main\\webapp\\");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { new ImageProvider(), resource_handler});
        server.setHandler(handlers);
        server.start();
        server.join();
    }


}