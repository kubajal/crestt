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

      case Nil => {
        None
      }

      // there are more lines to process
      case first :: xs =>
        if(first.level != level)
          throw new Exception("Invalid indentation in the given rows")
        else
          Some(Node(first.id, first.value, for(Some(child) <- findRoots(level + 1, Nil, xs)) yield child ))
    }
  }

  /**
    * Return those root Nodes that are on the given level.
    * @param list List of Rows to be processed
    * @param level level on which to discover roots
    * @return List of Nodes that are on zero level
    */

  @tailrec
  final def findRoots(level: Int, accumulator: List[Option[Node]], list: List[ParsedRow]): List[Option[Node]] = {
    list match {
      case Nil => accumulator
      case first :: tail =>

          // a tree root found, process its children
          if(first.level == level) {
            val newNode = parse(level, list)
              findRoots(level, newNode :: accumulator, tail)
          }

          // look further for a tree root
          else if (first.level > level)
            findRoots(level, accumulator, tail)

          // all rows on this level have been processed, return all roots on this level
          else // (first.level < second.level)
            accumulator
    }
  }

  def parse(list: List[ParsedRow]): List[Node] = {

    for
      {
        root <- findRoots(0, Nil, list)
        if(root.isDefined)
      }
      yield root.get
  }
}
