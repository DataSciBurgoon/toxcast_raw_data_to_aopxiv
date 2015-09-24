package runner

import ToxCast.ToxcastParser
import ToxCast.AopxivWriter

/**
 * @author burgoonl
 */
object runner {
  def main(args: Array[String]){
    val file_path = "src/test/resources/toxcast_raw_example.txt"
    val toxcast_parser = new ToxcastParser(file_path)
    val toxcast_data = toxcast_parser.parse()
    
    val aop_xiv_writer = new AopxivWriter(toxcast_data)
    aop_xiv_writer.send_to_mongo()
  }
}