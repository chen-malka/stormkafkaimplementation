package com.kafkastorm.example.jetty;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.IOException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 * Created by anton.l on 12/20/2015.
 */
public class ImageProvider extends AbstractHandler {

    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException
    {


        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

//        byte[] imageBytes = ...
//        response.setHeader("Content-Type", "image/jpg");// or png or gif, etc
//        response.setHeader("Content-Length", imageBytes.lenght);
//        response.getOutputStream().write(imageBytes);
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        server.setHandler(new ImageProvider());

        server.start();
        server.join();
    }


}