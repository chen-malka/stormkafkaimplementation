package com.kafkastorm.example.jetty;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.IOException;

import com.kafkastorm.example.subscriber.KafkaTopology;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 * Created by anton.l on 12/20/2015.
 */
public class ImageProvider extends AbstractHandler {

    static int i = 0;

    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException
    {


        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);


        byte[] imageBytes = KafkaTopology.getWordsAndWriteToFile();
        response.setHeader("Content-Type", "image/jpg");// or png or gif, etc
        response.setHeader("Content-Length", "" + imageBytes.length);
        response.getOutputStream().write(imageBytes);
//        response.setContentType("text/html;charset=utf-8");
//        response.setStatus(HttpServletResponse.SC_OK);
//        baseRequest.setHandled(true);
//        response.getWriter().println("<h1>Hello World " + i + "</h1>");
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        server.setHandler(new ImageProvider());

        server.start();
        server.join();
    }


}