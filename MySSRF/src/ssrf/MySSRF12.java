package ssrf;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.io.PrintWriter;


public class MySSRF12 extends HttpServlet{
	
//https://github.com/JoyChou93/java-sec-code/blob/master/src/main/java/org/joychou/controller/SSRF.java
	//urlConnection
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    {
        try {
        	
        	 //HttpURLConnection ssrf sec
        	System.out.println("HttpURLConnection test sec");
            String url = request.getParameter("url");
            System.out.println("url" + ":" + url);
           
            
            if (!SSRFHostCheck(url)) {
            	System.out.println("warning!!! illegal url:" + url);
                return;
            }    
      
            URL u = new URL(url);
            
            URLConnection urlConnection = u.openConnection();  
            HttpURLConnection httpUrl = (HttpURLConnection)urlConnection;   //HttpURLConnection
            
            httpUrl.setInstanceFollowRedirects(false); //禁止30x跳转
            
            BufferedReader in = new BufferedReader(new InputStreamReader(httpUrl.getInputStream())); //send request
            String inputLine;
            StringBuffer html = new StringBuffer(); 
            
            

            while ((inputLine = in.readLine()) != null) {
                html.append(inputLine);
            }        
            System.out.println("html:" + html.toString());
            in.close();
            
        
            PrintWriter myout = response.getWriter();
            myout.println("HttpURLConnection test sec");
            myout.println("url:" + url );
            myout.println("len:"  + html.toString().length());  
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
	
	
	public static Boolean SSRFHostCheck(String url) {
	    try {
	    	URL u = new URL(url);
	    	// 限制为http和https协议
            if (!u.getProtocol().startsWith("http") && !u.getProtocol().startsWith("https")) {
           	 String uProtocol = u.getProtocol();
           	 System.out.println("illegal Protocol:" + uProtocol);
           	 return  false;
           }
           
          // 获取域名或IP，并转为小写
           String host = u.getHost().toLowerCase();
           System.out.println("Host:" + host);   
           String hostwhitelist = "192.168.199.209";     //白名单    
           //String rootDomain = InternetDomainName.from(host).topPrivateDomain().toString();                
          if (host.equals(hostwhitelist)) {
       	   System.out.println("ok_host:" + host);
       	   return true;
           } else {
           	System.out.println("illegal host:" + host);
           	return false;
          }

	    } catch (Exception e) {
	        return false;
	    }
	}
	
	
}
