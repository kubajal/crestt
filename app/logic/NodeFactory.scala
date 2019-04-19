package logic

import scala.annotation.tailrec

class NodeFactory {

  /**
    * Helper function to parse a subtree.
    * @param list remaining rows to process
    * @return
    */

  final def parse(level: Int, list: List[ParsedRow]): Option[Node] = {
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
    * Return those root Nodes that are on the given level.
    * @param list List of Rows to be processed
    * @param level level on which to discover roots
    * @return List of Nodes that are on zero level
    */

  @tailrec
  final def findRoots(level: Int, accumulator: List[Node], list: List[ParsedRow]): List[Node] = {
    list match {
      case Nil => accumulator
      case first :: tail =>

          // a tree root found, process its children
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

          // all rows on this level have been processed, return all roots on this level
          else // (first.level < second.level)
            accumulator
    }
  }
}
