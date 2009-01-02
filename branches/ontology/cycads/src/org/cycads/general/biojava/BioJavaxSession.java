/*
 * Created on 15/10/2008
 */
package org.cycads.general.biojava;

import org.biojavax.RichObjectFactory;
import org.cycads.general.CacheCleanerListener;
import org.cycads.general.ParametersDefault;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class BioJavaxSession
{
	public static SessionFactory	sessionFactory	= new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	public static Session			session;
	public static Transaction		transaction;

	public static void init() {
		session = sessionFactory.openSession();
		RichObjectFactory.connectToBioSQL(session);
		RichObjectFactory.setDefaultNamespaceName(ParametersDefault.getNameSpaceDefault());
		transaction = session.beginTransaction();
	}

	public static void clearCache() {
		session.flush();
		transaction.commit();
		session.clear();
		RichObjectFactory.clearLRUCache();
		transaction = session.beginTransaction();
	}

	public static void finish() {
		session.flush();
		transaction.commit();
		session.close();
	}

	public static void finishWithRollback() {
		session.flush();
		transaction.rollback();
		session.close();
	}

	public static CacheCleanerListener getCacheCleanerListener() {
		return new CacheCleanerListener() {

			public void clearCache() {
				BioJavaxSession.clearCache();
			}
		};
	}

	public static Query createQuery(String query) {
		return session.createQuery(query);
	}

	public static SQLQuery createSqlQuery(String query) {
		return session.createSQLQuery(query);
	}

	public static void saveOrUpdate(String entityName, Object o) {
		session.saveOrUpdate(entityName, o);
	}
}
