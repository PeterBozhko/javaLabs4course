package com.company;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import serialize.XmlSerializer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Car Lada2101 = new CarWithKpp("Lada 2101", 63,4);

        Document document = new XmlSerializer().serialize(Lada2101);

        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter(System.out, format);
        writer.write(document);
    }
}
