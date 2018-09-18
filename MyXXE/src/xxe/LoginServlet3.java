package xxe;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;



//https://dom4j.github.io/

@WebServlet("/doLoginServlet3")
public class LoginServlet3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String USERNAME = "admin";//账号
	private static final String PASSWORD = "admin";//密码
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		       
              
        String result="";
		try {
			//JDOM2 Read XML		
			SAXBuilder builder = new SAXBuilder();				      
		    Document document = builder.build(request.getInputStream());
			 
		    String username = getValueByTagName3(document,"username");
			String password = getValueByTagName3(document,"password");
			 
			if(username.equals(USERNAME) && password.equals(PASSWORD)){
				result = String.format("<result><code>%d</code><msg>%s</msg></result>",1,username);
			}else{
				result = String.format("<result><code>%d</code><msg>%s</msg></result>",0,username);
			}
			 
			 
			
		} catch (JDOMException  e) {
			System.out.println(e.getMessage());
		} 
		response.setContentType("text/xml;charset=UTF-8");
		response.getWriter().append(result);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	/**
	 * 
	 * @param doc 文档
	 * @param tagName 标签名
 	 * @return 标签值
	 */
	public static String getValueByTagName3(Document document, String tagName){  
		
		if(document == null || tagName.equals(null)){  
            return "";  
        }  
		    
          Element root = document.getRootElement();
          List<Element> myList = root.getChildren();

          for (int i = 0; i < myList.size(); i++) {
        	  if(((Element) myList.get(i)).getName().equals(tagName)){
                  System.out.println("value:" + ((Element) myList.get(i)).getValue());               
                  System.out.println("**********");
                  return ((Element) myList.get(i)).getValue();
        	  }
              }
	
        return "";
    }	
	
}
