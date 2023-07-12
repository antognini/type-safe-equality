package examples

import scala.annotation.nowarn

@nowarn
@main def eqEqApply: Unit =

  case class Email( address:String) derives Eq

  enum MailPolicy derives Eq:
    case SendOnly, SendAndReceive

  case class Person(
                     name: String,
                     contact: Email,
                   ) derives Eq:

    // To be documented in README.md
    // Only compiles because enum MailPolicy derives Eq
    def mailPolicy: MailPolicy = Eq:

      if contact.address.endsWith("abc.com")
      then MailPolicy.SendAndReceive
      else MailPolicy.SendOnly

  end Person
