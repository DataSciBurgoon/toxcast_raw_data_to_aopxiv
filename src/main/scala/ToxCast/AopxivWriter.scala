package ToxCast

import com.mongodb.casbah.Imports._
//import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.ListBuffer
import com.mongodb.casbah.WriteConcern

/**
 * @author burgoonl
 */
class AopxivWriter(val toxcast: Toxcast) {
  def send_to_mongo(){
    
    /*
     * Set-up the MongoDB session
     * Grab the aopxplorer-dev database
     * Connect to the toxcast collection 
     */
    val mongoClient = MongoClient("localhost", 27017)
    val db = mongoClient("aopxplorer-dev")
    val collection = db("toxcast")
    val wc: com.mongodb.WriteConcern = WriteConcern.Journaled
    
    var current_assay = ""
    val bioassay = MongoDBObject("assayName" -> toxcast.assay_name,
                      "assayManufacturer" -> toxcast.assay_source_name,
                      "assayFormat" -> toxcast.assay_format_type,
                      "targetMacromoleculeSpecies" -> toxcast.organism,
                      "targetMacromolecule" -> toxcast.gene_name)
                      
    var measure_group = List[MongoDBObject]()
    for((concentration, response) <- toxcast.concentration_response){
      
      var endpoint_value = MongoDBObject("value" -> response)
      var screening_concentration = MongoDBObject("screeningConcentration" -> concentration, "value" -> response)
      var measure_group_element = MongoDBObject("screenedEntity" -> toxcast.chemical_name, "endpoint" -> screening_concentration)
      measure_group ::= measure_group_element
      //measure_group ::= MongoDBObject("screenedEntity" -> toxcast.chemical_name, "endpoint" -> {"screeningConcentration" -> {"has_concentration" -> concentration}}, "value" -> response)
    }
    
    bioassay += ("measureGroup" -> measure_group)
    collection.insert(MongoDBObject("bioassay" -> bioassay), wc)
    //collection.find()
    mongoClient.close()
  }
}