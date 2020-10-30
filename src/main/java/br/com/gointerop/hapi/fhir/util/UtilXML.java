package br.com.gointerop.hapi.fhir.util;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class UtilXML {
	public static String getContentByTag(Document document, String tag) {
		NodeList nodeList = document.getElementsByTagName(tag);

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				return node.getTextContent();
			}
		}

		return null;
	}

	public static String getAttributeByTag(Document document, String tag, String attribute) {
		NodeList nodeList = document.getElementsByTagName(tag);

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				NamedNodeMap nodeAttributes = node.getAttributes();

				for (int j = 0; j < nodeAttributes.getLength(); j++) {
					Node nodeAttribute = nodeAttributes.item(j);

					if (nodeAttribute.getNodeName() == attribute)
						return nodeAttribute.getNodeValue();
				}
			}
		}

		return null;
	}

	public static Node getNodeByTag(Document document, String tag) {
		NodeList nodeList = document.getElementsByTagName(tag);

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				return node;
			}
		}

		return null;
	}
}
