package com.kafkastorm.example.jetty;

import com.kafkastorm.example.subscriber.ImageGenerator;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by anton.l on 12/20/2015.
 */
public class ImageProvider extends AbstractHandler {

	static int i = 0;

	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) {
		ImageGenerator.getWordsAndWriteToFile();
	}

	public static void main(String[] args) throws Exception {

		Server server = new Server(8181);

		ResourceHandler resource_handler = new ResourceHandler();
		resource_handler.setDirectoriesListed(true);

		resource_handler.setResourceBase("src\\main\\webapp\\");

		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] { new ImageProvider(), resource_handler });
		server.setHandler(handlers);
		server.start();
		server.join();
	}

}