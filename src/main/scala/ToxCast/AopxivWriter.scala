package ToxCast

import com.mongodb.casbah.Imports._
//import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.ListBuffer

/**
 * @author burgoonl
 */
class AopxivWriter(val toxcast_data: ListBuffer[Toxcast]) {
  def send_to_mongo(){
    
    /*
     * Set-up the MongoDB session
     * Grab the aopxplorer-dev database
     * Connect to the toxcast collection 
     */
    val mongoClient = MongoClient("localhost", 27017)
    val db = mongoClient("aopxplorer-dev")
    if(db.collectionNames.contains("toxcast")){
      val collection = db("toxcast")
      collection.drop()
    }
    val collection = db("toxcast")
    
    /*
     * Process the data
     * Insert it into MongoDB
     */
    var current_assay = ""
    val bioassay = MongoDBObject()
    var measure_group = List[MongoDBObject]()
    
    
    for(toxcast_datum <- toxcast_data){
      if(current_assay == toxcast_datum.assay_name){
        measure_group ::= MongoDBObject("screenedEntity" -> toxcast_datum.chemical_name, "endpoint" -> ("screeningConcentration" -> ("has_concentration" -> toxcast_datum.log_concentration),
                                        "value" -> toxcast_datum.response_value))
        
      }
      else if(current_assay == ""){
        bioassay += ("assayName" -> toxcast_datum.assay_name,
                      "assayManufacturer" -> toxcast_datum.assay_source_name,
                      "assayFormat" -> toxcast_datum.assay_format_type,
                      "targetMacromoleculeSpecies" -> toxcast_datum.organism,
                      "targetMacromolecule" -> toxcast_datum.gene_name)
        
        measure_group ::= MongoDBObject("screenedEntity" -> toxcast_datum.chemical_name, "endpoint" -> ("screeningConcentration" -> ("has_concentration" -> toxcast_datum.log_concentration),
                                        "value" -> toxcast_datum.response_value))
          
          
         /* List(MongoDBObject("screenedEntity" -> toxcast_datum.chemical_name),
                          MongoDBObject("endpoint" -> MongoDBObject("screeningConcentration" -> MongoDBObject("has_concentration" -> toxcast_datum.log_concentration),
                                        "value" -> toxcast_datum.response_value))))*/
        current_assay = toxcast_datum.assay_name
      }
      else{
        bioassay += ("measureGroup" -> measure_group)
        collection.insert(MongoDBObject("bioassay" -> bioassay))
        bioassay.clear()
        measure_group = List[MongoDBObject]()
        
        bioassay += ("assayName" -> toxcast_datum.assay_name,
                      "assayManufacturer" -> toxcast_datum.assay_source_name,
                      "assayFormat" -> toxcast_datum.assay_format_type,
                      "targetMacromoleculeSpecies" -> toxcast_datum.organism,
                      "targetMacromolecule" -> toxcast_datum.gene_name)
        
        measure_group ::= MongoDBObject("screenedEntity" -> toxcast_datum.chemical_name, "endpoint" -> ("screeningConcentration" -> ("has_concentration" -> toxcast_datum.log_concentration),
                                        "value" -> toxcast_datum.response_value))
        current_assay = toxcast_datum.assay_name
        
      }
      
    }
    
      bioassay += ("measureGroup" -> measure_group)
      collection.insert(MongoDBObject("bioassay" -> bioassay))
  }
}