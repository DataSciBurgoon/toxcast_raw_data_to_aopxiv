package ToxCast

/**
 * @author burgoonl
 */
class Toxcast(val chemical_name: String, 
              val casrn: String, 
              val gene_name: String, 
              val assay_name: String,
              val assay_format_type: String,
              val assay_source_name: String,
              val organism: String,
              val log_concentration: Double, 
              val response_value: Double) {
  
}