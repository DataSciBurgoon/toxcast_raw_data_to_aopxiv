package runner

import ToxCast.ToxcastParser
import ToxCast.AopxivWriter

/**
 * @author burgoonl
 */
object runner {
  def main(args: Array[String]){
    //val file_path = "src/test/resources/toxcast_raw_example.txt"
    val file_path = args(0)
    val toxcast_parser = new ToxcastParser(file_path)
    val toxcast_data = toxcast_parser.parse()
    println("done with parsing")
    
    val aop_xiv_writer = new AopxivWriter(toxcast_data)
    println("just before sending stuff to mongo")
    aop_xiv_writer.send_to_mongo()
  }
}