package com.senior.cyber.frmk.metamodel;

import org.apache.commons.io.FilenameUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class XmlUtility {

    public static void process(File inputLiquibaseFolder, File outputLiquibaseFolder) throws IOException, SAXException, ParserConfigurationException, TransformerException {
        String notNullKey = "notnull_key_";
        String foreignKey = "foreign_key_";
        String uniqueIndex = "unique_index_";
        String index = "index_";
        String primaryKey = "primary_key_";

        int notNullKeyRotation = 1;
        int primaryKeyRotation = 1;
        int uniqueIndexRotation = 1;
        int indexRotation = 1;
        int foreignKeyRotation = 1;

        Map<Double, String> files = new TreeMap<>();
        int numberSize = 0;

        File[] temps = inputLiquibaseFolder.listFiles();

        if (temps != null) {
            for (File temp : temps) {
                int i = temp.getName().indexOf("__");
                if (i > -1) {
                    numberSize = Math.max(temp.getName().substring(1, i).length(), numberSize);
                }
            }
        }
        if (temps != null) {
            for (File temp : temps) {
                int i = temp.getName().indexOf("__");
                if (i > -1) {
                    double ver = Double.parseDouble(temp.getName().substring(1, i).replace('_', '.'));
                    files.put(ver, temp.getName());
                }
            }
        }


        for (String file : files.values()) {
            System.out.println(file);
            int changesets = 1;
            DocumentBuilderFactory docFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new File(inputLiquibaseFolder, file));
            Element databaseChangeLog = doc.getDocumentElement();
            for (int i = 0; i < databaseChangeLog.getChildNodes().getLength(); i++) {
                if (databaseChangeLog.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element changeSet = (Element) databaseChangeLog.getChildNodes().item(i);
                    if ("changeSet".equals(changeSet.getNodeName())) {
                        if (changeSet.getAttribute("id") != null && !"".equals(changeSet.getAttribute("id"))) {
                            changesets++;
                        } else {
                            changeSet.setAttribute("id", FilenameUtils.getBaseName(file) + "-" + (changesets++));
                        }
                        for (int j = 0; j < changeSet.getChildNodes().getLength(); j++) {
                            if (changeSet.getChildNodes().item(j).getNodeType() == Node.ELEMENT_NODE) {
                                Element element = (Element) changeSet.getChildNodes().item(j);
                                if ("addNotNullConstraint".equals(element.getNodeName())) {
                                    if (element.getAttribute("constraintName") != null && !"".equals(element.getAttribute("constraintName"))) {
                                        notNullKeyRotation++;
                                    } else {
                                        element.setAttribute("constraintName", notNullKey + notNullKeyRotation++);
                                    }
                                } else if ("addForeignKeyConstraint".equals(element.getNodeName())) {
                                    if (element.getAttribute("constraintName") != null && !"".equals(element.getAttribute("constraintName"))) {
                                        foreignKeyRotation++;
                                    } else {
                                        element.setAttribute("constraintName", foreignKey + foreignKeyRotation++);
                                    }
                                } else if ("addUniqueConstraint".equals(element.getNodeName())) {
                                    if (element.getAttribute("constraintName") != null && !"".equals(element.getAttribute("constraintName"))) {
                                        uniqueIndexRotation++;
                                    } else {
                                        element.setAttribute("constraintName", uniqueIndex + uniqueIndexRotation++);
                                    }
                                } else if ("createIndex".equals(element.getNodeName())) {
                                    if (element.getAttribute("constraintName") != null && !"".equals(element.getAttribute("constraintName"))) {
                                        indexRotation++;
                                    } else {
                                        element.setAttribute("indexName", index + indexRotation++);
                                    }
                                } else if ("createTable".equals(element.getNodeName()) || "addColumn".equals(element.getNodeName())) {
                                    for (int k = 0; k < element.getChildNodes().getLength(); k++) {
                                        if (element.getChildNodes().item(k).getNodeType() == Node.ELEMENT_NODE) {
                                            Element column = (Element) element.getChildNodes().item(k);
                                            if ("column".equals(column.getNodeName())) {
                                                if (column.hasAttribute("type")) {
                                                    String type = column.getAttribute("type");
                                                    if (type.startsWith("VARCHAR") && type.contains("(") && type.contains(")")) {
                                                        int size = Integer.parseInt(type.substring(type.indexOf("(") + 1, type.lastIndexOf(")")));
                                                        if (size > 4000) {
                                                            column.setAttribute("type", "VARCHAR(4000)");
                                                        }
                                                    }
                                                }
                                                for (int m = 0; m < column.getChildNodes().getLength(); m++) {
                                                    if (column.getChildNodes().item(m).getNodeType() == Node.ELEMENT_NODE) {
                                                        Element constraints = (Element) column.getChildNodes().item(m);
                                                        if ("constraints".equals(constraints.getNodeName())) {
                                                            if (constraints.hasAttribute("nullable")) {
                                                                if (constraints.getAttribute("notNullConstraintName") != null && !"".equals(constraints.getAttribute("notNullConstraintName"))) {
                                                                    notNullKeyRotation++;
                                                                } else {
                                                                    constraints.setAttribute("notNullConstraintName", notNullKey + notNullKeyRotation++);
                                                                }
                                                            }
                                                            if (constraints.hasAttribute("primaryKey")) {
                                                                if (constraints.getAttribute("primaryKeyName") != null && !"".equals(constraints.getAttribute("primaryKeyName"))) {
                                                                    primaryKeyRotation++;
                                                                } else {
                                                                    constraints.setAttribute("primaryKeyName", primaryKey + primaryKeyRotation++);
                                                                }
                                                            }
                                                            if (constraints.hasAttribute("unique")) {
                                                                if (constraints.getAttribute("uniqueConstraintName") != null && !"".equals(constraints.getAttribute("uniqueConstraintName"))) {
                                                                    uniqueIndexRotation++;
                                                                } else {
                                                                    constraints.setAttribute("uniqueConstraintName", uniqueIndex + uniqueIndexRotation++);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(outputLiquibaseFolder, file));
            transformer.transform(source, result);
        }
    }

}
