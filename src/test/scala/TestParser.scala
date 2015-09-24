package test

import org.scalatest._
import ToxCast.ToxcastParser


/**
 * @author burgoonl

 */

class TestParser extends FlatSpec with ShouldMatchers{
  
  "A ToxcastParser" should "create a ToxCast object with all of the pertinent data" in {
    val file_path = "src/test/resources/toxcast_raw_example.txt"
    val toxcast_parser = new ToxcastParser(file_path)
    val toxcast_data = toxcast_parser.parse()
    
    assertResult("Corticosterone"){
      toxcast_data(0).chemical_name
    }
    
    assertResult("50-22-6"){
      toxcast_data(0).casrn
    }
    
    assertResult("estrogen receptor 1"){
      toxcast_data(0).gene_name
    }
    
    assertResult("ACEA_T47D"){
      toxcast_data(0).assay_name
    }
    
    
    assertResult("cell-based"){
      toxcast_data(0).assay_format_type
    }
    
    assertResult("ACEA"){
      toxcast_data(0).assay_source_name
    }
    
    assertResult("human"){
      toxcast_data(0).organism
    }
    
    
    toxcast_data(0).log_concentration should be ((-2.2218) +- 0.01)
    toxcast_data(0).response_value should be (5.85 +- 0.01)
    
    assertResult("Sodium chlorate"){
      toxcast_data(18).chemical_name
    }
    
    assertResult("7775-09-9"){
      toxcast_data(18).casrn
    }
    
    assertResult("nuclear factor of kappa light polypeptide gene enhancer in B-cells 1"){
      toxcast_data(18).gene_name
    }
    
    assertResult("Tox21_NFkB_BLA_Agonist"){
      toxcast_data(18).assay_name
    }
    
    
    assertResult("cell-based"){
      toxcast_data(18).assay_format_type
    }
    
    assertResult("Tox21"){
      toxcast_data(18).assay_source_name
    }
    
    assertResult("human"){
      toxcast_data(18).organism
    }
    
    
    toxcast_data(18).log_concentration should be (-1.699 +- 0.01)
    toxcast_data(18).response_value should be (-0.245 +- 0.01)
    
  }
  
}