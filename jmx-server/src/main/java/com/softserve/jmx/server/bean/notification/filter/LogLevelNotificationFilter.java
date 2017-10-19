package com.softserve.jmx.server.bean.notification.filter;

import static com.softserve.jmx.server.bean.NotificationWebServerMXBean.LOG_LEVEL_ATTR;
import static javax.management.AttributeChangeNotification.ATTRIBUTE_CHANGE;

import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import javax.management.NotificationFilter;

public class LogLevelNotificationFilter implements NotificationFilter {

	private static final long serialVersionUID = 7973658914674289799L;

	@Override
	public boolean isNotificationEnabled(Notification notification) {
		String type = notification.getType();
		if ((type == null) || (type.equals(ATTRIBUTE_CHANGE) == false)
				|| (!(notification instanceof AttributeChangeNotification))) {
			return false;
		}
		AttributeChangeNotification attributeNotification = (AttributeChangeNotification) notification;
		if (!attributeNotification.getAttributeName().equals(LOG_LEVEL_ATTR)) {
			return false;
		}

		String oldValue = (String) attributeNotification.getOldValue();
		String newValue = (String) attributeNotification.getNewValue();
		return !oldValue.equals(newValue);
	}

}
