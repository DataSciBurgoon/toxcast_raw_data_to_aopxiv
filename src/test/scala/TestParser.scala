package test

import org.junit.Test
import org.junit.Assert._
import ToxCast.ToxcastParser


/**
 * @author burgoonl
 */
class TestParser {
  @Test def testParseFirstDataRow{
    val file_path = "toxcast_raw_example.txt"
    val toxcast_parser = new ToxcastParser(file_path)
    val toxcast_data = toxcast_parser.parse()
    
    assertEquals(toxcast_data(0).chemical_name, "Corticosterone")
    assertEquals(toxcast_data(0).casrn, "50-22-6")
    assertEquals(toxcast_data(0).gene_name, "estrogen receptor 1")
    assertEquals(toxcast_data(0).assay_name, "ACEA_T47D")
    assertEquals(toxcast_data(0).assay_format_type, "cell-based")
    assertEquals(toxcast_data(0).assay_source_name, "ACEA")
    assertEquals(toxcast_data(0).organism, "human")
    assertEquals(toxcast_data(0).log_concentration, -2.2218, 0.01)
    assertEquals(toxcast_data(0).response_value, 5.85, 0.01)
  }
  
  @Test def testParseLastDataRow{
    val file_path = "toxcast_raw_example.txt"
    val toxcast_parser = new ToxcastParser(file_path)
    val toxcast_data = toxcast_parser.parse()
    
    assertEquals(toxcast_data(18).chemical_name, "Sodium chlorate")
    assertEquals(toxcast_data(18).casrn, "7775-09-9")
    assertEquals(toxcast_data(18).gene_name, "nuclear factor of kappa light polypeptide gene enhancer in B-cells 1")
    assertEquals(toxcast_data(18).assay_name, "Tox21_NFkB_BLA_Agonist")
    assertEquals(toxcast_data(18).assay_format_type, "cell-based")
    assertEquals(toxcast_data(18).assay_source_name, "Tox21")
    assertEquals(toxcast_data(18).organism, "human")
    assertEquals(toxcast_data(18).log_concentration, -1.699, 0.01)
    assertEquals(toxcast_data(18).response_value, -0.245, 0.01)
  }
}