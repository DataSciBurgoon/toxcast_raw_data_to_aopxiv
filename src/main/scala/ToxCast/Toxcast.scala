package ToxCast

import scala.collection.mutable.Map

/**
 * @author burgoonl
 */
class Toxcast() {
  
  var chemical_name = ""
  var casrn = ""
  var gene_name = "" 
  var assay_name = ""
  var assay_format_type = ""
  var assay_source_name = ""
  var organism = ""
  
  var concentration_response = Map[Double, Double]()
  
}