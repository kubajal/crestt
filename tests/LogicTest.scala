package tests
import com.fasterxml.jackson.databind.ObjectMapper
import logic.{Node, NodeFactory, Row}
import org.scalatest.{FlatSpec, PrivateMethodTester}

class NodeFactoryTest extends FlatSpec {

  val parser = new NodeFactory()

  "parse function" should "return Nil on no rows" in {

    val data = List()
    val result = parser.parse(0, data)
    assert(result == None)
  }

  "parse function" should "parse one parent and one child" in {

    val data = Row("a", 0, 1) :: Row("aa", 1, 2) :: Nil
    val root = parser.parse(0, data).get
    assert(root.id == 1)
    assert(root.name == "a")
    assert(root.nodes.size == 1)
    assert(root.nodes(0).id == 2)
    assert(root.nodes(0).nodes.size == 0)
  }

  "parse function" should "parse two parents" in {

    val data = Row("a", 0, 1) :: Row("b", 0, 2) :: Nil
    val result = parser.findRoots(0, Nil, data)
    val root1 = result(0)
    val root2 = result(1)
    assert(root2.id == 1)
    assert(root2.nodes.size == 0)
    assert(root2.name == "a")
    assert(root1.id == 2)
    assert(root1.nodes.size == 0)
    assert(root1.name == "b")
  }

  "parse function" should "prase parent, child, parent" in {

    val data = Row("a", 0, 1) :: Row("aa", 1, 2) :: Row("b", 0, 3) :: Nil
    val result = parser.findRoots(0, Nil, data)
    assert(result.size == 2)
    val root1 = result(0)
    val root2 = result(1)
    assert(root2.id == 1)
    assert(root2.name == "a")
    assert(root2.nodes.size == 1)
    assert(root2.nodes(0).id == 2)
    assert(root2.nodes(0).nodes.size == 0)
    assert(root1.id == 3)
    assert(root1.name == "b")
    assert(root1.nodes.size == 0)
  }

  "parse function" should "parse parent, child, child, parent" in {

    val data = Row("a", 0, 1) :: Row("aa", 1, 2) :: Row("ab", 1, 3) :: Row("b", 0, 4) :: Nil
    val result = parser.findRoots(0, Nil, data)
    assert(result.size == 2)
    val root2 = result(0)
    val root1 = result(1)
    assert(root1.id == 1)
    assert(root1.nodes.size == 2)
    assert(root2.id == 4)
    assert(root2.nodes.size == 0)
  }

  "parse function" should "parse 3 levels of rows" in {

    val data = Row("a", 0, 1) :: Row("aa", 1, 2) :: Row("aaa", 2, 3) :: Row("ab", 1, 4) :: Row("aba", 2, 5) :: Nil
    val result = parser.findRoots(0, Nil, data)
    assert(result.size == 1)
    val root = result(0)
    assert(root.id == 1)
    assert(root.name == "a")
    assert(root.nodes.size == 2)
    assert(root.nodes(0).id == 4)
    assert(root.nodes(0).nodes.size == 1)
    assert(root.nodes(0).nodes(0).id == 5)
    assert(root.nodes(0).nodes(0).nodes.size == 0)
    assert(root.nodes(1).nodes.size == 1)
    assert(root.nodes(1).id == 2)
    assert(root.nodes(1).nodes.size == 1)
    assert(root.nodes(1).nodes(0).id == 3)
    assert(root.nodes(1).nodes(0).nodes.size == 0)
  }

  "parse function" should "parse 4 levels of rows" in {

    val data = Row("a", 0, 1) :: Row("aa", 1, 2) :: Row("aaa", 2, 3) :: Row("aaaa", 3, 4) :: Row("b", 0, 5) :: Nil
    val result = parser.findRoots(0, Nil, data)
    assert(result.size == 2)
    val level0 = result.last
    assert(level0.nodes.size == 1)
    val level1 = level0.nodes.head
    assert(level1.nodes.size == 1)
    val level2 = level1.nodes.head
    assert(level2.nodes.size == 1)
    val level3 = level2.nodes.head
    assert(level3.nodes.size == 0)
    assert(level3.name == "aaaa")
    val b = result.head
    assert(b.nodes.isEmpty)
    assert(b.id == 5)
    assert(b.name == "b")
  }

  "Node.toString" should "have JSON format" in {

    val data = Row("a", 0, 1) :: Row("aa", 1, 2) :: Nil
    val result = parser.findRoots(0, Nil, data)(0)
    assert(result.toString == """{"id":1,"name":"a","nodes":[{"id":2,"name":"aa","nodes":[]}]}""" )
  }
}
