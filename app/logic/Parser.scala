package logic

class Parser {

  def parse(list: List[Row]): List[Node] = {
    list match {
      case Nil => Nil
      case first :: Nil => List(new Node(first.id, Nil))
      case first :: second :: xs =>
        if (first.level < second.level)
          Node(first.id, parse(second :: xs)) :: Nil
        else if(first.level == second.level)
          Node(first.id, Nil) :: parse(second :: xs)
        else
          Node(first.id, Nil) :: Nil
    }
  }

  def findRoots(list: List[Row]): List[Node] = {

    list match {
      case Nil => Nil
      case first :: tail =>
          if(first.level == 0)
            parse(list) ++ findRoots(tail)
          else
            findRoots(tail)
    }
  }
}
