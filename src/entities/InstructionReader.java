package entities;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class InstructionReader {

    public static final String INSTR_30_4 = "30_3";
    public static final String INSTR_20000_4 = "20000_4";
    public static final String INSTR_20000_20 = "20000_20";

    public InstructionReader(){}

    public List<Instruction> readInstructions(String amount) throws ParserConfigurationException, IOException, SAXException {
        List<Instruction> instructions = new LinkedList<>();
        String filename = "Instructions_" + amount + ".xml";
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD,"");
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA,"");
        DocumentBuilder builder = dbf.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(filename));
        doc.getDocumentElement().normalize();

        NodeList list = doc.getElementsByTagName("instruction");
        for(int i = 0; i< list.getLength(); i++){
            Instruction instruction = new Instruction();
            Node node = list.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                instruction.setProcessID(Integer.parseInt(element.getElementsByTagName("processID").item(0).getTextContent()));
                instruction.setOperation(element.getElementsByTagName("operation").item(0).getTextContent());
                instruction.setVirtualAdress(Integer.parseInt(element.getElementsByTagName("adress").item(0).getTextContent()));

                instructions.add(instruction);
            }
        }
        return instructions;

    }
}
