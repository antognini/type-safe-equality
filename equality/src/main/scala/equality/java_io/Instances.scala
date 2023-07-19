package equality.java_io

import equality.core.{Eq, EqRef}

export Instances.given

object Instances:
  given java_io_File: Eq[java.io.File] = Eq.assumed
  given java_io_FileDescriptor: Eq[java.io.FileDescriptor] = Eq.assumed
  given java_io_InputStream: EqRef[java.io.InputStream] = EqRef.assumed
  given java_io_OutputStream: EqRef[java.io.OutputStream] = EqRef.assumed
  given java_io_Reader: EqRef[java.io.Reader] = EqRef.assumed
  given java_io_Writer: EqRef[java.io.Writer] = EqRef.assumed

