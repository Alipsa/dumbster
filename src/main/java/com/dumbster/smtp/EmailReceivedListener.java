package com.dumbster.smtp;

public interface EmailReceivedListener {

  void messageArrived(SmtpMessage msg);
}
