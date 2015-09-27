package ToxCast

import scala.io.Source
//import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.ListBuffer
import ToxCast.Toxcast

/**
 * @author burgoonl
 */

/*
 * To fix the memory issue, I need to:
 * 
 * 1) Bring the AopxivWriter stuff here to directly send data to MongoDB
 * 2) Not hold on to the Toxcast object -- just delete it once it's done (or overwrite it)
 * 
 * This will make the code less complex. AopxivWriter also needs to be rewritten.
 */
class ToxcastParser(val file_path: String) {
  //def parse(): ArrayBuffer[Toxcast]= {
  def parse() {
    var toxcast_concentration = ListBuffer.empty[Double]
    var toxcast_value = ListBuffer.empty[Double]
    var current_assay = ""
    var current_chemical = ""
    var i = 1
    val toxcast = new Toxcast()
    for(line <- Source.fromFile(file_path).getLines()){
      
      if(i > 1){
        val split_data = line.split("\t")
        if(current_assay == split_data(4) && current_chemical == split_data(0)){
          if(split_data(8) != "NULL" && split_data(9) != "NULL"){
            toxcast.concentration_response(split_data(8).toDouble) = split_data(9).toDouble
          }
          
        }
        else if(i == 2){
          toxcast.chemical_name = split_data(0)
          toxcast.casrn = split_data(2)
          toxcast.gene_name = split_data(3)
          toxcast.assay_name = split_data(4)
          toxcast.assay_format_type = split_data(5)
          toxcast.assay_source_name = split_data(6)
          toxcast.organism = split_data(7)
          if(split_data(8) != "NULL" && split_data(9) != "NULL"){
            toxcast.concentration_response(split_data(8).toDouble) = split_data(9).toDouble
          }
          
          current_assay = split_data(4)
          current_chemical = split_data(0)
          
          i += 1
        }
        else{
          println(toxcast.chemical_name)
          val aopxivwriter = new AopxivWriter(toxcast)
          aopxivwriter.send_to_mongo()
          
          toxcast.chemical_name = split_data(0)
          toxcast.casrn = split_data(2)
          toxcast.gene_name = split_data(3)
          toxcast.assay_name = split_data(4)
          toxcast.assay_format_type = split_data(5)
          toxcast.assay_source_name = split_data(6)
          toxcast.organism = split_data(7)
          toxcast.concentration_response.clear()
          if(split_data(8) != "NULL" && split_data(9) != "NULL"){
            toxcast.concentration_response(split_data(8).toDouble) = split_data(9).toDouble
          }
          
          current_assay = split_data(4)
          current_chemical = split_data(0)
          
          i += 1
        }
        
      }
      else{
        i += 1
      }
      
    }
    
    val aopxivwriter = new AopxivWriter(toxcast)
    aopxivwriter.send_to_mongo()
  }
}