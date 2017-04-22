/**
 * 
 */
package com.jeecmstheme.jautopost;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author Administrator
 * 
 */
public class ConcurrentUtil {
	public static boolean bindHibernateSessionToThread(
			SessionFactory sessionFactory) {
		if (TransactionSynchronizationManager.hasResource(sessionFactory)) {
			// Do not modify the Session: just set the participate flag.
			return true;
		} else {
			Session session = sessionFactory.openSession();
			session.setFlushMode(FlushMode.MANUAL);
			SessionHolder sessionHolder = new SessionHolder(session);
			TransactionSynchronizationManager.bindResource(sessionFactory,
					sessionHolder);
		}
		return false;
	}

	public static void closeHibernateSessionFromThread(boolean participate,
			Object sessionFactory) {

		if (!participate) {
			SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager
					.unbindResource(sessionFactory);
			SessionFactoryUtils.closeSession(sessionHolder.getSession());
		}
	}
}
