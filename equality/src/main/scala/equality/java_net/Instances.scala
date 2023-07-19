package equality.java_net

import equality.core.{Eq, EqRef}

export Instances.given

object Instances:
  given java_net_HttpCookie: Eq[java.net.HttpCookie] = Eq.assumed
  given java_net_IDN: EqRef[java.net.IDN] = EqRef.assumed
  given java_net_Inet4Address: Eq[java.net.Inet4Address] = Eq.assumed
  given java_net_Inet6Address: Eq[java.net.Inet6Address] = Eq.assumed
  given java_net_MulticastSocket: EqRef[java.net.MulticastSocket] = EqRef.assumed
  given java_net_NetworkInterface: Eq[java.net.NetworkInterface] = Eq.assumed
  given java_net_ProtocolFamily: Eq[java.net.ProtocolFamily] = Eq.assumed
  given java_net_ServerSocket: EqRef[java.net.ServerSocket] = EqRef.assumed
  given java_net_Socket: EqRef[java.net.Socket] = EqRef.assumed
  given java_net_SocketOption[A: Eq]: Eq[java.net.SocketOption[A]] = Eq.assumed
  given java_net_SocketOptions: Eq[java.net.SocketOptions] = Eq.assumed
  given java_net_URI: Eq[java.net.URI] = Eq.assumed
  given java_net_URL: Eq[java.net.URL] = Eq.assumed
