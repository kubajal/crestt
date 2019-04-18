package logic

import scala.annotation.tailrec

class NodeFactory {

  /**
    * Helper function to parse a single tree of a zero-level Node
    * @param list
    * @return
    */

  def parse(level: Int, list: List[Row]): Option[Node] = {
    list match {

      case Nil => None

      // last value of in the file, it doesnt have any children
      case first :: Nil => Some(Node(first.id, first.value, Nil))

      // there are more lines to process
      case first :: xs =>
        Some(Node(first.id, first.value, findRoots(level + 1, Nil, xs)))

    }
  }

  /**
    * Return those Nodes that are on zero level.
    * @param list List of Rows to be processed
    * @param level
    * @return List of Nodes that are on zero level
    */

  @tailrec
  final def findRoots(level: Int, accumulator: List[Node], list: List[Row]): List[Node] = {
    list match {
      case Nil => accumulator
      case first :: tail =>

          // we found a tree root
          if(first.level == level) {
            val newNode = parse(level, list)
            if(newNode.isDefined)
              findRoots(level, newNode.get :: accumulator, tail)
            else
              findRoots(level, accumulator, tail)
          }

          // look further for a tree root
          else if (first.level > level)
            findRoots(level, accumulator, tail)
          else
            accumulator
    }
  }
}
