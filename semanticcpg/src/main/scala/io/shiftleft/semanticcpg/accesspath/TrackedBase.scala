package io.shiftleft.semanticcpg.accesspath

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, nodes}
import io.shiftleft.semanticcpg.language.nodemethods.TrackingPointMethodsBase
import io.shiftleft.semanticcpg.utils.MemberAccess
import scala.jdk.CollectionConverters._

trait TrackedBase
case class TrackedNamedVariable(name: String) extends TrackedBase
case class TrackedReturnValue(call: nodes.CallRepr) extends TrackedBase {
  override def toString: String = {
    s"TrackedReturnValue(${call.code})"
  }
}
case class TrackedLiteral(literal: nodes.Literal) extends TrackedBase {
  override def toString: String = {
    s"TrackedLiteral(${literal.code})"
  }
}
case class TrackedMethodOrTypeRef(methodOrTypeRef: nodes.StoredNode with nodes.HasCode) extends TrackedBase {
  override def toString: String = {
    s"TrackedMethodOrTypeRef(${methodOrTypeRef.code})"
  }
}

object TrackedUnknown extends TrackedBase {
  override def toString: String = {
    "TrackedUnknown"
  }
}
object TrackedFormalReturn extends TrackedBase {
  override def toString: String = {
    "TrackedFormalReturn"
  }
}
