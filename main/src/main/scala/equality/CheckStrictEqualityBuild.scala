package equality

import scala.annotation.implicitNotFound
import scala.util.NotGiven

private val msg = "the sources need to be compiled with -language:strictEquality"

def checkStrictEqualityBuild()(using @implicitNotFound(msg) ev: NotGiven[CanEqual[Any, Any]]): Boolean = true

private val enforce = checkStrictEqualityBuild()