package equality.java_net

import equality.Eq

export EqInstances.given

object EqInstances:
  given java_net_URI: Eq[java.net.URI] = Eq.assumed
  given java_net_URL: Eq[java.net.URL] = Eq.assumed
  given java_net_HttpCookie: Eq[java.net.HttpCookie] = Eq.assumed
  given java_net_Inet6Address: Eq[java.net.Inet6Address] = Eq.assumed
  given java_net_Inet4Address: Eq[java.net.Inet4Address] = Eq.assumed
  given java_net_ProtocolFamily: Eq[java.net.ProtocolFamily] = Eq.assumed
  given java_net_NetworkInterface: Eq[java.net.NetworkInterface] = Eq.assumed
  given java_net_SocketOptions: Eq[java.net.SocketOptions] = Eq.assumed
