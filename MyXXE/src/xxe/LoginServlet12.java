package xxe;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;


@WebServlet("/doLoginServlet12")
public class LoginServlet12 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String USERNAME = "admin";//账号
	private static final String PASSWORD = "admin";//密码
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
        String result="";
		try {
			//DOM Read XML
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();     
	        
			/*以下为修复代码*/ 
		    //https://www.owasp.org/index.php/XML_External_Entity_(XXE)_Prevention_Cheat_Sheet#Java
		
			//禁用DTDs (doctypes),几乎可以防御所有xml实体攻击
		    dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true); //首选
			
		    //如果不能禁用DTDs,可以使用下两项，必须两项同时存在
		    dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);		//防止外部实体POC 
		    dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);   //防止参数实体POC
		    /*以上为修复代码*/	
		    
		    DocumentBuilder db = dbf.newDocumentBuilder();	    
			Document doc = db.parse(request.getInputStream());
			
			String username = getValueByTagName(doc,"username");
			String password = getValueByTagName(doc,"password");
			if(username.equals(USERNAME) && password.equals(PASSWORD)){
				result = String.format("<result><code>%d</code><msg>%s</msg></result>",1,username);
			}else{
				result = String.format("<result><code>%d</code><msg>%s</msg></result>",0,username);
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			result = String.format("<result><code>%d</code><msg>%s</msg></result>",3,e.getMessage());
		} catch (SAXException e) {
			e.printStackTrace();
			result = String.format("<result><code>%d</code><msg>%s</msg></result>",3,e.getMessage());
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
	public static String getValueByTagName(Document doc, String tagName){  
        if(doc == null || tagName.equals(null)){  
            return "";  
        }  
        NodeList pl = doc.getElementsByTagName(tagName);  
        if(pl != null && pl.getLength() > 0){  
            return pl.item(0).getTextContent();  
        } 
        return "";
    }
}
