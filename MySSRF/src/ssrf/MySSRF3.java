package ssrf;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.io.PrintWriter;


public class MySSRF3 extends HttpServlet{
	
	//urlConnection
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    {
        try {
            // ImageIO ssrf vul
        	System.out.println("ImageIO test");
            String url = request.getParameter("url");
            URL u = new URL(url);
            BufferedImage img = ImageIO.read(u); // send request
            System.out.println("Height:" + img.getHeight());
            
            PrintWriter myout = response.getWriter();
            myout.println("ImageIO test");
            myout.println("url:" + url );
            myout.println("Height:" + img.getHeight());  
            myout.println("Width:" + img.getWidth()); 
            myout.flush();
            


        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("fail");
            
            PrintWriter myout = response.getWriter();
            myout.println("something is wrong"); 
            myout.flush();
           
        }
    }
	}
}
