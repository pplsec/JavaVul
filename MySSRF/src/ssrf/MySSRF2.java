package ssrf;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.io.PrintWriter;


public class MySSRF2 extends HttpServlet{
	
	//urlConnection
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    {
        try {
        	//urlConnection ssrf vul
        	System.out.println("urlConnection test");
            String url = request.getParameter("url");
            System.out.println("url:" + url);
            URL u = new URL(url);
            URLConnection urlConnection = u.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream())); //send request
            String inputLine;
            StringBuffer html = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                html.append(inputLine);
            }        
            System.out.println("html:" + html.toString());
            in.close();
            
        
            PrintWriter myout = response.getWriter();
            myout.println("urlConnection test");
            myout.println("url:" + url );
            myout.println("len:" + html.toString().length());  
            myout.println("text:" + html.toString());
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
