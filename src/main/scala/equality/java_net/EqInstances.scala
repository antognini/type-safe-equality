package equality.java_net

import equality.Eq

export EqInstances.given

object EqInstances:
  given java_net_URI: Eq[java.net.URI] = Eq
  given java_net_URL: Eq[java.net.URL] = Eq
  given java_net_HttpCookie: Eq[java.net.HttpCookie] = Eq
  given java_net_Inet6Address: Eq[java.net.Inet6Address] = Eq
  given java_net_Inet4Address: Eq[java.net.Inet4Address] = Eq
  given java_net_ProtocolFamily: Eq[java.net.ProtocolFamily] = Eq
  given java_net_NetworkInterface: Eq[java.net.NetworkInterface] = Eq
  given java_net_SocketOption: Eq[java.net.SocketOption[?]] = Eq
  given java_net_SocketOptions: Eq[java.net.SocketOptions] = Eq
