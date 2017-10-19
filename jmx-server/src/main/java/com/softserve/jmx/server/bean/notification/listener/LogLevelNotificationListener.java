package com.softserve.jmx.server.bean.notification.listener;

import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import javax.management.NotificationListener;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class LogLevelNotificationListener implements NotificationListener {

	@Override
	public void handleNotification(Notification notification, Object handback) {
		AttributeChangeNotification attributeNotification = (AttributeChangeNotification) notification;
		log.info(String.format("*** Handling '%s' attribute notification ***",
				attributeNotification.getAttributeName()));
		log.info("Message: " + notification.getMessage());
		log.info("Old Value: " + notification.getMessage());
		log.info("New Value: " + notification.getMessage());
		log.info("Seq: " + notification.getSequenceNumber());
		log.info("*********************************");
	}

}
