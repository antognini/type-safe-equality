package equality.java_nio

import equality.Eq

export EqInstances.given

object EqInstances:
  given java_nio_ByteBuffer: Eq[java.nio.ByteBuffer] = Eq
  given java_nio_ShortBuffer: Eq[java.nio.ShortBuffer] = Eq
  given java_nio_IntBuffer: Eq[java.nio.IntBuffer] = Eq
  given java_nio_LongBuffer: Eq[java.nio.LongBuffer] = Eq
  given java_nio_FloatBuffer: Eq[java.nio.FloatBuffer] = Eq
  given java_nio_DoubleBuffer: Eq[java.nio.DoubleBuffer] = Eq
