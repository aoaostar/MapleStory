package provider.WzXML;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import provider.MapleData;
import provider.MapleDataEntity;
import tools.FileoutputUtil;

public class XMLDomMapleData implements MapleData, Serializable
{
    private Node node;
    private File imageDataDir;
    
    private XMLDomMapleData(final Node node) {
        this.node = node;
    }
    
    public XMLDomMapleData(final FileInputStream fis, final File imageDataDir) {
        try {
            final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            final Document document = documentBuilder.parse(fis);
            this.node = document.getFirstChild();
        }
        catch (Exception ex2) {
            throw new RuntimeException(ex2);
        }
        this.imageDataDir = imageDataDir;
    }
    
    @Override
    public MapleData getChildByPath(final String path) {
        final String[] segments = path.split("/");
        if (segments[0].equals("..")) {
            return ((MapleData)this.getParent()).getChildByPath(path.substring(path.indexOf("/") + 1));
        }
        Node myNode = this.node;
        for (int x = 0; x < segments.length; ++x) {
            final NodeList childNodes = myNode.getChildNodes();
            boolean foundChild = false;
            for (int i = 0; i < childNodes.getLength(); ++i) {
                try {
                    final Node childNode = childNodes.item(i);
                    if (childNode != null && childNode.getNodeType() == 1 && childNode.getAttributes().getNamedItem("name").getNodeValue().equals(segments[x])) {
                        myNode = childNode;
                        foundChild = true;
                        break;
                    }
                }
                catch (NullPointerException e) {
                    FileoutputUtil.outputFileError(FileoutputUtil.PacketEx_Log, e);
                }
            }
            if (!foundChild) {
                return null;
            }
        }
        final XMLDomMapleData ret = new XMLDomMapleData(myNode);
        ret.imageDataDir = new File(this.imageDataDir, this.getName() + "/" + path).getParentFile();
        return ret;
    }
    
    @Override
    public List<MapleData> getChildren() {
        final List<MapleData> ret = new ArrayList<MapleData>();
        final NodeList childNodes = this.node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); ++i) {
            final Node childNode = childNodes.item(i);
            if (childNode != null && childNode.getNodeType() == 1) {
                final XMLDomMapleData child = new XMLDomMapleData(childNode);
                child.imageDataDir = new File(this.imageDataDir, this.getName());
                ret.add(child);
            }
        }
        return ret;
    }
    
    @Override
    public Object getData() {
        final NamedNodeMap attributes = this.node.getAttributes();
        final MapleDataType type = this.getType();
        switch (type) {
            case DOUBLE: {
                return Double.parseDouble(attributes.getNamedItem("value").getNodeValue());
            }
            case FLOAT: {
                return Float.parseFloat(attributes.getNamedItem("value").getNodeValue());
            }
            case INT: {
                return Integer.parseInt(attributes.getNamedItem("value").getNodeValue());
            }
            case SHORT: {
                return Short.parseShort(attributes.getNamedItem("value").getNodeValue());
            }
            case STRING:
            case UOL: {
                return attributes.getNamedItem("value").getNodeValue();
            }
            case VECTOR: {
                return new Point(Integer.parseInt(attributes.getNamedItem("x").getNodeValue()), Integer.parseInt(attributes.getNamedItem("y").getNodeValue()));
            }
            case CANVAS: {
                return new FileStoredPngMapleCanvas(Integer.parseInt(attributes.getNamedItem("width").getNodeValue()), Integer.parseInt(attributes.getNamedItem("height").getNodeValue()), new File(this.imageDataDir, this.getName() + ".png"));
            }
            default: {
                return null;
            }
        }
    }
    
    @Override
    public MapleDataType getType() {
        final String nodeName2;
        final String nodeName = nodeName2 = this.node.getNodeName();
        switch (nodeName2) {
            case "imgdir": {
                return MapleDataType.PROPERTY;
            }
            case "canvas": {
                return MapleDataType.CANVAS;
            }
            case "convex": {
                return MapleDataType.CONVEX;
            }
            case "sound": {
                return MapleDataType.SOUND;
            }
            case "uol": {
                return MapleDataType.UOL;
            }
            case "double": {
                return MapleDataType.DOUBLE;
            }
            case "float": {
                return MapleDataType.FLOAT;
            }
            case "int": {
                return MapleDataType.INT;
            }
            case "short": {
                return MapleDataType.SHORT;
            }
            case "string": {
                return MapleDataType.STRING;
            }
            case "vector": {
                return MapleDataType.VECTOR;
            }
            case "null": {
                return MapleDataType.IMG_0x00;
            }
            default: {
                return null;
            }
        }
    }
    
    @Override
    public MapleDataEntity getParent() {
        final Node parentNode = this.node.getParentNode();
        if (parentNode.getNodeType() == 9) {
            return null;
        }
        final XMLDomMapleData parentData = new XMLDomMapleData(parentNode);
        parentData.imageDataDir = this.imageDataDir.getParentFile();
        return parentData;
    }
    
    @Override
    public String getName() {
        return this.node.getAttributes().getNamedItem("name").getNodeValue();
    }
    
    @Override
    public Iterator<MapleData> iterator() {
        return this.getChildren().iterator();
    }
}
