/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.interfazhibernate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.NotSerializableException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author raulr
 */
public class InterfazHibernate {

    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException, NotSerializableException, SAXException, ParserConfigurationException {
        Ventana1 v = new Ventana1();
        
        v.setVisible(true);
    }
}
