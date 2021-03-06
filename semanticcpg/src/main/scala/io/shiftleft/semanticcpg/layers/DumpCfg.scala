package io.shiftleft.semanticcpg.layers

import better.files.File
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal._

case class CfgDumpOptions(var outDir: String) extends LayerCreatorOptions {}

object DumpCfg {

  val overlayName = "dumpCfg"

  val description = "Dump control flow graph to out/"

  def defaultOpts: CfgDumpOptions = CfgDumpOptions("out")
}

class DumpCfg(options: CfgDumpOptions) extends LayerCreator {
  override val overlayName: String = DumpCfg.overlayName
  override val description: String = DumpCfg.description

  override def create(context: LayerCreatorContext, serializeInverse: Boolean): Unit = {
    val cpg = context.cpg
    cpg.method.zipWithIndex.foreach {
      case (method, i) =>
        val str = method.start.dotCfg.head
        (File(options.outDir) / s"${i}-cfg.dot").write(str)
    }
  }

  override def probe(cpg: Cpg): Boolean = false
}
