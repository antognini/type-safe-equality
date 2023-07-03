package equality

import util.NotGiven
import annotation.implicitNotFound

private val msg = "the sources need to be compiled with -language:strictEquality"

def checkStrictEqualityBuild()(using @implicitNotFound(msg) ev: NotGiven[CanEqual[Any, Any]]): Boolean = true

private val enforce = checkStrictEqualityBuild()