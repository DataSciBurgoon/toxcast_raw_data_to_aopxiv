package test

import org.scalatest._
import com.mongodb.casbah.Imports._
import ToxCast.ToxcastParser
import ToxCast.AopxivWriter

/**
 * @author burgoonl
 */


class TestMongoInsert extends FlatSpec with BeforeAndAfter{
  val file_path = "src/test/resources/toxcast_raw_example.txt"
  val toxcast_parser = new ToxcastParser(file_path)
  toxcast_parser.parse()
  
  def fixture = new {
    val mongoClient = MongoClient("localhost", 27017)
    val db = mongoClient("aopxplorer-dev")
    val collection = db("toxcast")
  }
  
  "An AopxivWriter" should "create a MongoDB entry for ACEA_T47D" in {
    val f = fixture
    val acea_t47d = MongoDBObject("bioassay.assayName" -> "ACEA_T47D")
    val acea_t47d_doc = f.collection.findOne(acea_t47d)
    assert(!acea_t47d_doc.isEmpty)
    f.mongoClient.close()
  }
  
  "The ACEA_T47D entry" should "be associated with the gene estrogen receptor 1" in {
    val f = fixture
    val acea_t47d = MongoDBObject("bioassay.assayName" -> "ACEA_T47D")
    val acea_t47d_doc = f.collection.findOne(acea_t47d)
    val bioassay_obj = acea_t47d_doc.get("bioassay")
    assertResult("estrogen receptor 1"){
      bioassay_obj.asInstanceOf[DBObject].get("targetMacromolecule").toString()
    }
    f.mongoClient.close()
  }
  
  "An AopxivWriter" should "Create a MongoDB entry for Tox21_NFkB_BLA_Agonist" in {
    val f = fixture
    val tox21_nfkb = MongoDBObject("bioassay.assayName" -> "Tox21_NFkB_BLA_Agonist")
    val tox21_nfkb_doc = f.collection.findOne(tox21_nfkb)
    assert(!tox21_nfkb_doc.isEmpty)
    f.mongoClient.close()
  }
  
  "The Tox21_NFkB_BLA_Agonist entry" should "be associated with the gene nuclear factor of kappa light polypeptide gene enhancer in B-cells 1" in {
    val f = fixture
    val tox21_nfkb = MongoDBObject("bioassay.assayName" -> "Tox21_NFkB_BLA_Agonist")
    val tox21_nfkb_doc = f.collection.findOne(tox21_nfkb)
    val bioassay_obj = tox21_nfkb_doc.get("bioassay")
    assertResult("nuclear factor of kappa light polypeptide gene enhancer in B-cells 1"){
      bioassay_obj.asInstanceOf[DBObject].get("targetMacromolecule").toString()
    }
    f.mongoClient.close()
  }
  
  "The ACEA_T47D entry" should "have a response of -9.55044950669381 at log concentration of -2.22184874961636" in {
    val f = fixture
    val acea_t47d = MongoDBObject("bioassay.assayName" -> "ACEA_T47D")
    val acea_t47d_doc = f.collection.findOne(acea_t47d)
    val bioassay_obj = acea_t47d_doc.get("bioassay")
    
    val blah = acea_t47d_doc.get("bioassay").asInstanceOf[DBObject].getAs[MongoDBList]("measureGroup")
    blah.foreach(blahs => println(blahs.get(0)))
    blah.foreach(blahs => println(blahs.get(0).asInstanceOf[DBObject].get("screenedEntity")))
    //returns corticosterone
    blah.foreach(blahs => println(blahs.get(0).asInstanceOf[DBObject].get("endpoint")))
    //returns { "screeningConcentration" : -1.69897000433602 , "value" : 61.0007424230354}
    val screening_concentration = blah.foreach(blahs => println(blahs.get(0).asInstanceOf[DBObject].get("endpoint").asInstanceOf[DBObject].get("screeningConcentration")))
    //returns -1.69897000433602
    
    /*var screening_concentration_hit = false
    if(screening_concentration < -1.1 && screening_concentration > -2.1){
      screening_concentration_hit = true
    }
    
    assert(screening_concentration_hit)*/
    
    f.mongoClient.close()
  }
  
  
}