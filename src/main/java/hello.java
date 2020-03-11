import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.PasswordAuthentication;

public class hello {

    public static void main(String [] args) throws ParserConfigurationException, XPathExpressionException, IOException, SAXException {

        String user = "", passwd = "";

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.flush();
            System.out.println("Insert username: ");
            user = reader.readLine();
            System.out.println("Insert password: ");
            passwd = reader.readLine();
            System.out.flush();
        } catch (Exception e) {
            System.out.println(e);
        }

        char[] pass = passwd.toCharArray();

        doLogin(user, pass);
    }


    public static void doLogin(String userName, char[] password)
            throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true);
        DocumentBuilder builder = domFactory.newDocumentBuilder();
        Document doc = builder.parse("db.xml");
        String pwd = password.toString();

        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        XPathExpression expr = xpath.compile("//users/user[username/text()='" +
                userName + "' and password/text()='" + pwd + "' ]");
        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;

        // Print first names to the console
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i).getChildNodes().item(1).getChildNodes().item(0);
            System.out.println( "Authenticated: " + ((Node) node).getNodeValue());
        }
    }

}
