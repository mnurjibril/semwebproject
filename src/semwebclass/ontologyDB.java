/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package semwebclass;

import java.util.*;
import com.hp.hpl.jena.db.*;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.*;


/**
 *
 * @author tarex
 */
public class ontologyDB {
    
    /* Read Ontology from file system and store it into database*/
    public static OntModel createDBModelFromFile(IDBConnection con, String name, String filePath) {
    	ModelMaker maker = ModelFactory.createModelRDBMaker(con);
    	Model base = maker.createModel(name);
    	OntModel newmodel = ModelFactory.createOntologyModel( getModelSpec(maker), base );
		newmodel.read(filePath);
    return newmodel; 	
    }
    
    /* Get the Ontology from DataBase  */
    
    public static OntModel getModelFromDB(IDBConnection con, String name) {
    	ModelMaker maker = ModelFactory.createModelRDBMaker(con);
    	Model base = maker.getModel(name);
    	OntModel newmodel = ModelFactory.createOntologyModel( getModelSpec(maker), base);
    	return newmodel;	
    }
    
    public static OntModelSpec getModelSpec(ModelMaker maker) {
    	OntModelSpec spec = new OntModelSpec(OntModelSpec.OWL_MEM);
    	spec.setImportModelMaker(maker);
    	return spec;
    }
    
    //List of classes in the model or Ontology
    public static void listClasses(IDBConnection con, String name){
        OntModel model = getModelFromDB(con, name);
       String[] content = new String[100];
        int k=0;
        for(Iterator i = model.listClasses(); i.hasNext();){
            OntClass c = (OntClass) i.next();
           // System.out.println(c.getLocalName());
           content[k]= c.getLocalName();
           k++;
        }
        k=0;
        while(k<100){
        if(content[k]==null){
            break;
        }
        System.out.println(content[k]);
        k++;
    }
        
        
    }
    
    //list of instances of a class
    public static void getInstance(IDBConnection con, String name, String className){
        OntModel model = getModelFromDB(con, name);
        String prefix = "http://www.semanticweb.org/orange/ontologies/2018/11/conman#";
        OntClass movs = model.getOntClass(prefix + className);
        
        //print out the name of the selected class
        System.out.println(movs.getLocalName());
        
        //get instances of the selected class
        for(Iterator it = movs.listInstances(); it.hasNext();){
            Individual ind = (Individual) it.next();
            System.out.println(ind.getLocalName());
        }
    }
    
    //list of properties of a class
    public static void getPropertiesClass(IDBConnection con, String name, String className){
        OntModel model = getModelFromDB(con, name);
        String prefix = "http://www.semanticweb.org/orange/ontologies/2018/11/conman#";
        OntClass movs = model.getOntClass(prefix + className);
        
        //get the properties of the instances of the selected class
        for(Iterator it = movs.listDeclaredProperties(); it.hasNext();){
            OntProperty prop = (OntProperty) it.next();
            //print out the property name and its values
            System.out.println(prop.getLocalName());
        }
    }
    
    public static void getPropertiesInstance(IDBConnection con, String name, String className){
        OntModel model = getModelFromDB(con, name);
        String prefix = "http://www.semanticweb.org/orange/ontologies/2018/11/conman#";
        OntClass movs = model.getOntClass(prefix + className);
        
        //get all instances one by one for the class
        Iterator it = movs.listInstances();
        while(it.hasNext()){
            Individual ind = (Individual) it.next();
            
            //get properties of the individuals one by one
            for(Iterator indit = movs.listDeclaredProperties(); indit.hasNext();){
                OntProperty ontp = (OntProperty) indit.next();
                
                for(Iterator init = ind.listProperties(ontp); init.hasNext();){
                    String valueName = init.next().toString();
                    System.out.println(valueName);
                }
            }
        }
    }
}
