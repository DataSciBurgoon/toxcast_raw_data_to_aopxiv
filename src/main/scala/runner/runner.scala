package runner

import ToxCast.ToxcastParser
import ToxCast.AopxivWriter
import com.mongodb.casbah.Imports._

/**
 * @author burgoonl
 */
object runner {
  def main(args: Array[String]){
    //val file_path = "src/test/resources/toxcast_raw_example.txt"
    val file_path = args(0)
    val mongoClient = MongoClient("localhost", 27017)
    val db = mongoClient("aopxplorer-dev")
    if(db.collectionNames.contains("toxcast")){
      val collection = db("toxcast")
      collection.drop()
    }
    mongoClient.close()
    
    
    val toxcast_parser = new ToxcastParser(file_path)
    val toxcast_data = toxcast_parser.parse()
    
  }
}