package xxe;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom2.input.sax.SAXHandler;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


@WebServlet("/doLoginServlet42")
public class LoginServlet42 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		 
		//https://blog.csdn.net/u011024652/article/details/51516220		
		String result="";
		try {
			//SAX Read XML
			SAXParserFactory factory  = SAXParserFactory.newInstance(); 
					
			/*以下为修复代码*/ 
		    //https://www.owasp.org/index.php/XML_External_Entity_(XXE)_Prevention_Cheat_Sheet#Java
		
			//禁用DTDs (doctypes),几乎可以防御所有xml实体攻击
			factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true); //首选
			
		    //如果不能禁用DTDs,可以使用下两项，必须两项同时存在
			factory.setFeature("http://xml.org/sax/features/external-general-entities", false);		//防止外部实体POC 
			factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);   //防止参数实体POC
		    /*以上为修复代码*/	
			
			
			SAXParser saxparser = factory.newSAXParser();  
			SAXHandler handler = new SAXHandler();  	
			saxparser.parse(request.getInputStream(), handler); 
			
			//为简单，没有提取子元素中的数据，只要调用parse()解析xml就已经触发xxe漏洞了
            //没有回显  blind xxe
			 result = String.format("<result><code>%d</code><msg>%s</msg></result>",0,1);
	
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


}
