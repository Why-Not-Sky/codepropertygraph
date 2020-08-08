package io.shiftleft.dataflowengineoss.passes

import io.shiftleft.dataflowengineoss.language.DataFlowCodeToCpgSuite
import io.shiftleft.semanticcpg.language._
import io.shiftleft.dataflowengineoss.language._
import io.shiftleft.codepropertygraph.generated.nodes

class ReachingDefPassTests extends DataFlowCodeToCpgSuite {
  override val code =
    """
      | int foo(int x) {
      | sink(x);
      | }
      |""".stripMargin

  "Test0 : should find flow from parameter to argument" in {
    val src = cpg.parameter.name("x")
    val snk = cpg.call("sink").argument(1)
    val flows = snk.reachableByFlows(src).l
    flows.size shouldBe 1
    flows.head.elements match {
      case List(first : nodes.MethodParameterIn, arg : nodes.Identifier) =>
        first.name shouldBe "x"
        arg.code shouldBe "x"
      case _ => fail
    }

    cpg.parameter.name("x").ddgNext.l match {
      case List(x : nodes.Identifier) => x.code shouldBe "x"
      case _ => fail
    }
  }
}

class ReachingDefPassTests1 extends DataFlowCodeToCpgSuite {

  override val code =
    """
      | int foo(int x) {
      | x = 10;
      | sink(x);
      | }
      |""".stripMargin

  "Test 1.1: should return no flow when variable is overwritten" in {
    val src = cpg.parameter.name("x").l
    src.size shouldBe 1
    src.start.ddgNext.l.size shouldBe 0
    val snk = cpg.call("sink").argument(1).l
    snk.size shouldBe 1
    snk.start.reachableByFlows(src.start).l.size shouldBe 0
  }

  "Test 1.2: should return a flow from assignment's `x` to sink" in {
    val src = cpg.assignment.target.l
    src.size shouldBe 1
    val snk = cpg.call("sink").argument(1).l
    println(snk.start.reachableByFlows(src.start).l)
  }

}