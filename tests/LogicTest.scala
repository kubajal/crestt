package tests
import logic.{Node, NodeFactory, Row}
import org.scalatest.{FlatSpec, PrivateMethodTester}

class NodeFactoryTest extends FlatSpec with PrivateMethodTester{

  val parse = PrivateMethod[List[Node]]('parse)

  "parse function" should "return Nil on no rows" in {

    val data = Nil
    val parser = new NodeFactory()
    val result = parser.invokePrivate(parse(data))
    assert(result == Nil)
  }

  "parse function" should "parse one parent and one child" in {

    val data = Row("a", 0, 1) :: Row("aa", 1, 2) :: Nil
    val parser = new NodeFactory()
    val root = parser.invokePrivate(parse(data))(0)
    assert(root.id == 1)
    assert(root.children.size == 1)
    assert(root.children(0).id == 2)
    assert(root.children(0).children.size == 0)
  }

  "parse function" should "parse two parents" in {

    val data = Row("a", 0, 1) :: Row("b", 0, 2) :: Nil
    val parser = new NodeFactory()
    val result = parser.invokePrivate(parse(data))
    val root1 = result(0)
    val root2 = result(1)
    assert(root1.id == 1)
    assert(root1.children.size == 0)
    assert(root2.id == 2)
    assert(root2.children.size == 0)
  }

  "parse function" should "prase parent, child, parent" in {

    val data = Row("a", 0, 1) :: Row("aa", 1, 2) :: Row("b", 0, 3) :: Nil
    val parser = new NodeFactory()
    val result = parser.findRoots(data)
    assert(result.size == 2)
    val root1 = result(0)
    val root2 = result(1)
    assert(root1.id == 1)
    assert(root1.children.size == 1)
    assert(root1.children(0).id == 2)
    assert(root1.children(0).children.size == 0)
    assert(root2.id == 3)
    assert(root2.children.size == 0)
  }

  "parse function" should "parse parent, child, child, parent" in {

    val data = Row("a", 0, 1) :: Row("aa", 1, 2) :: Row("ab", 1, 3) :: Row("b", 0, 4) :: Nil
    val parser = new NodeFactory()
    val result = parser.findRoots(data)
    assert(result.size == 2)
    val root1 = result(0)
    val root2 = result(1)
    assert(root1.id == 1)
    assert(root1.children.size == 2)
    assert(root2.id == 4)
    assert(root2.children.size == 0)
  }

  "parse function" should "parse 3 level of rows" in {

    val data = Row("a", 0, 1) :: Row("aa", 1, 2) :: Row("aaa", 2, 3) :: Row("ab", 1, 4) :: Row("aba", 2, 5) :: Nil
    val parser = new NodeFactory()
    val result = parser.findRoots(data)
    assert(result.size == 1)
    val root = result(0)
    assert(root.id == 1)
    assert(root.children.size == 2)
    assert(root.children(0).id == 2)
    assert(root.children(0).children.size == 1)
    assert(root.children(0).children(0).id == 3)
    assert(root.children(0).children(0).children.size == 0)
    assert(root.children(1).children.size == 1)
    assert(root.children(1).id == 4)
    assert(root.children(1).children.size == 1)
    assert(root.children(1).children(0).id == 5)
    assert(root.children(1).children(0).children.size == 0)
  }
}
