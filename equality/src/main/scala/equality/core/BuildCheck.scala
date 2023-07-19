package equality.core

import scala.annotation.implicitNotFound
import scala.util.NotGiven

private val message = "the sources need to be compiled with -language:strictEquality"

private val _ = checkStrictEqualityBuild()

def checkStrictEqualityBuild()(using @implicitNotFound(message) ev: NotGiven[CanEqual[Any, Any]]): Boolean = true
