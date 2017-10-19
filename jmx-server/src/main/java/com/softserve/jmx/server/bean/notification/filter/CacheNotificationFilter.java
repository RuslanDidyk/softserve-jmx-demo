package com.softserve.jmx.server.bean.notification.filter;

import static com.softserve.jmx.server.bean.NotificationWebServerMXBean.CACHE_ATTR;
import static javax.management.AttributeChangeNotification.ATTRIBUTE_CHANGE;

import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import javax.management.NotificationFilter;

public class CacheNotificationFilter implements NotificationFilter {

	private static final long serialVersionUID = -2695056248975780292L;

	@Override
	public boolean isNotificationEnabled(Notification notification) {
		String type = notification.getType();
		if ((type == null) || (type.equals(ATTRIBUTE_CHANGE) == false)
				|| (!(notification instanceof AttributeChangeNotification))) {
			return false;
		}
		AttributeChangeNotification attributeNotification = (AttributeChangeNotification) notification;
		if (!attributeNotification.getAttributeName().equals(CACHE_ATTR)) {
			return false;
		}

		Integer oldValue = (Integer) attributeNotification.getOldValue();
		Integer newValue = (Integer) attributeNotification.getNewValue();
		// Send notification in case new cache twice bigger. Can be any other condition.
		return (newValue / 2) > oldValue;
	}

}
