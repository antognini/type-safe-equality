package equality.java_nio

import equality.Eq

export EqInstances.given
export equality.scala_.EqInstances.scala_CanEqual

object EqInstances:
  given java_nio_ByteBuffer: Eq[java.nio.ByteBuffer] = Eq.assumed
  given java_nio_ShortBuffer: Eq[java.nio.ShortBuffer] = Eq.assumed
  given java_nio_IntBuffer: Eq[java.nio.IntBuffer] = Eq.assumed
  given java_nio_LongBuffer: Eq[java.nio.LongBuffer] = Eq.assumed
  given java_nio_FloatBuffer: Eq[java.nio.FloatBuffer] = Eq.assumed
  given java_nio_DoubleBuffer: Eq[java.nio.DoubleBuffer] = Eq.assumed
