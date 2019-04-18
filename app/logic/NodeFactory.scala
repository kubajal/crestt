package logic

class NodeFactory {

  /**
    * Helper function to parse a single tree of a zero-level Node
    * @param list
    * @return
    */

  protected def parse(list: List[Row]): List[Node] = {
    list match {

      case Nil => Nil

      // last value of in the file, it doesnt have any children
      case first :: Nil => List(new Node(first.id, Nil))

      // there are more lines to process
      case first :: second :: xs => {

        // if the following line is tabbed then consume all children of first
        if (first.level < second.level)
          Node(first.id, parse(second :: xs)) :: Nil

        // if the following line on the same level then first doesnt have children
        // and return list of syblings
        else if(first.level == second.level)
          Node(first.id, Nil) :: parse(second :: xs)

        // if the following lines level is lower then end the recursion
        else
          Node(first.id, Nil) :: Nil
      }
    }
  }

  /**
    * Return those Nodes that are on zero level.
    * @param list List of Rows to be processed
    * @return List of Nodes that are on zero level
    */

  def findRoots(list: List[Row]): List[Node] = {

    list match {
      case Nil => Nil
      case first :: tail =>
          // we found a tree root
          if(first.level == 0)
            parse(list) ++ findRoots(tail)

          // look further for a tree root
          else
            findRoots(tail)
    }
  }
}
