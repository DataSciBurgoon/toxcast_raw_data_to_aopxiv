package ToxCast

import scala.io.Source
import scala.collection.mutable.ArrayBuffer
import ToxCast.Toxcast

/**
 * @author burgoonl
 */
class ToxcastParser(val file_path: String) {
  def parse(): ArrayBuffer[Toxcast]= {
    var toxcast_data = ArrayBuffer.empty[Toxcast]
    var i = 1
    for(line <- Source.fromFile(file_path).getLines()){
      if(i > 1){
        val split_data = line.split("\t")
        toxcast_data += new Toxcast(split_data(0), 
                                    split_data(2), 
                                    split_data(3), 
                                    split_data(4), 
                                    split_data(5), 
                                    split_data(6), 
                                    split_data(7), 
                                    split_data(8).toDouble, 
                                    split_data(9).toDouble)
      }
      else{
        i += 1
      }
      
    }
    return(toxcast_data)
  }
}