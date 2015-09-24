package test

import org.scalatest._
import com.mongodb.casbah.Imports._
import ToxCast.ToxcastParser
import ToxCast.AopxivWriter

/**
 * @author burgoonl
 */
class TestMongoInsert extends FlatSpec with ShouldMatchers{
  
  def fixture = new {
    val file_path = "src/test/resources/toxcast_raw_example.txt"
    val toxcast_parser = new ToxcastParser(file_path)
    val toxcast_data = toxcast_parser.parse()
    
    val aop_xiv_writer = new AopxivWriter(toxcast_data)
    aop_xiv_writer.send_to_mongo()
    
    val mongoClient = MongoClient("localhost", 27017)
    val db = mongoClient("aopxplorer-dev")
    val collection = db("toxcast")
  }
  
  "An AopxivWriter" should "create a MongoDB entry for ACEA_T47D" in {
    val f = fixture
    val acea_t47d = MongoDBObject("bioassay.assayName" -> "ACEA_T47D")
    val acea_t47d_doc = f.collection.findOne(acea_t47d)
    assert(!acea_t47d_doc.isEmpty)
  }
  
  "The ACEA_T47D entry" should "be associated with the gene estrogen receptor 1" in {
    val f = fixture
    val acea_t47d = MongoDBObject("bioassay.assayName" -> "ACEA_T47D")
    val acea_t47d_doc = f.collection.findOne(acea_t47d)
    val bioassay_obj = acea_t47d_doc.get("bioassay")
    assertResult("estrogen receptor 1"){
      bioassay_obj.asInstanceOf[DBObject].get("targetMacromolecule").toString()
    }
  }
  
  "An AopxivWriter" should "Create a MongoDB entry for Tox21_NFkB_BLA_Agonist" in {
    val f = fixture
    val tox21_nfkb = MongoDBObject("bioassay.assayName" -> "Tox21_NFkB_BLA_Agonist")
    val tox21_nfkb_doc = f.collection.findOne(tox21_nfkb)
    assert(!tox21_nfkb_doc.isEmpty)
  }
  
  "The Tox21_NFkB_BLA_Agonist entry" should "be associated with the gene nuclear factor of kappa light polypeptide gene enhancer in B-cells 1" in {
    val f = fixture
    val tox21_nfkb = MongoDBObject("bioassay.assayName" -> "Tox21_NFkB_BLA_Agonist")
    val tox21_nfkb_doc = f.collection.findOne(tox21_nfkb)
    val bioassay_obj = tox21_nfkb_doc.get("bioassay")
    assertResult("nuclear factor of kappa light polypeptide gene enhancer in B-cells 1"){
      bioassay_obj.asInstanceOf[DBObject].get("targetMacromolecule").toString()
    }
  }
}