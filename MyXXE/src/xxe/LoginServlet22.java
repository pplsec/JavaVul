package xxe;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;


//https://dom4j.github.io/

@WebServlet("/doLoginServlet22")
public class LoginServlet22 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String USERNAME = "admin";//账号
	private static final String PASSWORD = "admin";//密码
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
        String result="";
		try {
			//DOM4J Read XML
			SAXReader saxReader = new SAXReader();
					
			/*以下为修复代码*/ 
		    //https://www.owasp.org/index.php/XML_External_Entity_(XXE)_Prevention_Cheat_Sheet#Java
		
			//禁用DTDs (doctypes),几乎可以防御所有xml实体攻击
			saxReader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true); //首选
			
		    //如果不能禁用DTDs,可以使用下两项，必须两项同时存在
			saxReader.setFeature("http://xml.org/sax/features/external-general-entities", false);		//防止外部实体POC 
			saxReader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);   //防止参数实体POC
		    /*以上为修复代码*/	
			
			Document document = saxReader.read(request.getInputStream());
			 
		    String username = getValueByTagName2(document,"username");
			String password = getValueByTagName2(document,"password");
			 
			if(username.equals(USERNAME) && password.equals(PASSWORD)){
				result = String.format("<result><code>%d</code><msg>%s</msg></result>",1,username);
			}else{
				result = String.format("<result><code>%d</code><msg>%s</msg></result>",0,username);
			}
			 
			 
			
		} catch (DocumentException  e) {
			System.out.println(e.getMessage());
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	public static String getValueByTagName2(Document document, String tagName){  
		
		if(document == null || tagName.equals(null)){  
            return "";  
        }  
		
		  Element root = document.getRootElement();
          
          for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
              Element myuser = (Element) it.next();
              
              if(myuser.getName().equals(tagName)){
              System.out.println(myuser.getName() + ":" + myuser.getText());
              System.out.println("**********");
              return myuser.getText();
          }
          }
	
        return "";
    }
	
	
}
