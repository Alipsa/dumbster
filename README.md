# dumbster
> The Dumbster is a very simple fake SMTP server designed for unit and system testing applications that
> send email messages. It responds to all standard SMTP commands but does not deliver messages to the user.
> The messages are stored within the Dumbster for later extraction and verification.

**This repository is a fork of [https://github.com/kirviq/dumbster](https://github.com/kirviq/dumbster).**

Differences:
1. Added a possibility to attach a listener on messages arriving
2. Add support for multipart messages (patch created by [koke24](https://github.com/koke24))
3. Remove lombok
4. Make SmtpMessage Json serializable and deserializable

Aside from that, the actual smtp logic is completely unchanged.

### Usage
Add maven dependency:
```xml
<dependency>
    <groupId>se.alipsa</groupId>
    <artifactId>dumbster</artifactId>
    <version>1.7.2</version>
    <scope>test</scope>
</dependency>
```
Start testing:
```java
class SomeTest {
    public void runTest() {
        try (SimpleSmtpServer dumbster = SimpleSmtpServer.start(SimpleSmtpServer.AUTO_SMTP_PORT)) {
        
            sendMessage(dumbster.getPort(), "sender@here.com", "Test", "Test Body", "receiver@there.com");
            
            List<SmtpMessage> emails = dumbster.getReceivedEmails();
            assertThat(emails, hasSize(1));
            SmtpMessage email = emails.get(0);
            assertThat(email.getHeaderValue("Subject"), is("Test"));
            assertThat(email.getBody(), is("Test Body"));
            assertThat(email.getHeaderValue("To"), is("receiver@there.com"));
        }
    }
}
```
See more examples in the included [unit tests](https://github.com/alipsa/dumbster/blob/master/src/test/java/com/dumbster/smtp/SimpleSmtpServerTest.java).
