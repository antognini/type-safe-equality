package equality.java_io

import equality.Eq

export EqInstances.given

object EqInstances:
  given java_io_File: Eq[java.io.File] = Eq.assumed
  given java_io_FileDescriptor: Eq[java.io.FileDescriptor] = Eq.assumed
  given java_io_InputStream: Eq[java.io.InputStream] = Eq.assumed
  given java_io_OutputStream: Eq[java.io.OutputStream] = Eq.assumed
  given java_io_Reader: Eq[java.io.Reader] = Eq.assumed
  given java_io_Writer: Eq[java.io.Writer] = Eq.assumed
  