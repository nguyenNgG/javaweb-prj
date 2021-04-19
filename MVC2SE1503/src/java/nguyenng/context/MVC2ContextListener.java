/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nguyenng.context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author bchao
 */
@WebListener
public class MVC2ContextListener implements ServletContextListener {
    /*
        contextInitialized is invoked when app is deployed,
        before app starts
    */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //ServletContext receive signal about changes to servletcontext
        ServletContext ctx = sce.getServletContext();
        //initialize url map
        Map<String, String> list = null;
        //filename from web.xml, inside <context-param> tag
        String filename = ctx.getInitParameter("UrlMappingFile").trim();
        FileReader fr = null; //read file from drive
        BufferedReader bf = null; 
        //get character stream from filereader and put into buffer memory (RAM)
        // to read
        try {
            fr = new FileReader(filename);
            bf = new BufferedReader(fr);
            //read from buffer when buffer is not empty
            while (bf.ready()) {
                String line = bf.readLine();
                String mapEntry[] = line.split("=");
                if (mapEntry.length == 2) {
                    String key = mapEntry[0].trim();
                    String value = mapEntry[1].trim();
                    if (list == null) {
                        list = new HashMap<String, String>();
                    } // end if map not existed
                    //add entry to map
                    list.put(key, value);
                } // end if valid entry
            } //end reading from file
            //save in contextscope
            ctx.setAttribute("URL_MAPPING", list);
        } 
        catch (FileNotFoundException ex) {
            System.out.println("MVC2ContextListener _ FileNotFoundException: " + ex.getCause());
        }
        catch (IOException ex) {
            System.out.println("MVC2ContextListener _ IOException: " + ex.getCause());
        } finally {
            try {
                if (bf != null) {
                    bf.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ex) {
                System.out.println("MVC2ContextListener _ IOException: " + ex.getCause());
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //remove attribute before shutting down context
        ServletContext servletContext = sce.getServletContext();
        servletContext.removeAttribute("URL_MAPPING");
    }

}
