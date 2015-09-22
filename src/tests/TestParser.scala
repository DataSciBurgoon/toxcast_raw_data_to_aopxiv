package tests

import org.junit.Test
import org.junit.Assert._
import ToxCast.ToxcastParser


/**
 * @author burgoonl
 */
class TestParser {
  @Test def testParseFirstChem(){
    val file_path = "toxcast_raw_example.txt"
    val toxcast_parser = new ToxcastParser(file_path)
    val toxcast_data = toxcast_parser.parse()
    assertEquals(toxcast_data(0).chemical_name, "Corticosterone")
  }
}