package ToxCast

import scala.io.Source
//import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.ListBuffer
import ToxCast.Toxcast

/**
 * @author burgoonl
 */

/*
 * I'm not positive, but I think the GC issues were coming up b/c of ArrayBuffer.
 * Specifically, ArrayBuffer requires contiguous blocks of memory. This works fine
 * so long as you don't have a lot of data. But if you don't have enough contiguous
 * blocks of memory, ArrayBuffer chokes.
 * 
 * Thus, I loved over to ListBuffer, which doesn't require contiguous blocks of
 * memory.
 * 
 * TODO: I need to re-work this. I think I need to collapse the data down immediately rather
 * than wait until I'm in the AopxivWriter. I think I need to collapse down the Toxcast
 * object so that it doesn't have all the stuff repeating all the time. That should drastically
 * cut down on overall memory usage. That means the doubles are going to have to be placed in
 * a map of some sort most likely, but that's better than the current appraoch.
 */
class ToxcastParser(val file_path: String) {
  //def parse(): ArrayBuffer[Toxcast]= {
  def parse(): ListBuffer[Toxcast]= {
    var toxcast_data = ListBuffer.empty[Toxcast]
    var i = 1
    for(line <- Source.fromFile(file_path).getLines()){
      if(i > 1){
        println("here!" + i)
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
        i += 1
      }
      else{
        i += 1
      }
      
    }
    
    return(toxcast_data)
  }
}