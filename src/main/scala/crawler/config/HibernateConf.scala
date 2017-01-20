package crawler.config

import javax.persistence.Persistence

import org.hibernate.cfg.Configuration

object HibernateConf {

  def getEntityManagerFactory() = {
    val entityManagerFactory = Persistence.createEntityManagerFactory("defaultPersistenceUnit", getHibernateConfigByCode().getProperties)
    entityManagerFactory
  }

  def getHibernateConfigByCode(): Configuration = {
     val configuration: Configuration  = new Configuration()
     configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
     configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver")
     configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:test")
     configuration.setProperty("hibernate.connection.username", "sa")
//     configuration.setProperty("hibernate.connection.autocommit", "true")
//hibernate.show_sql
//hibernate.generate_statistics
//hibernate.use_sql_comments
    configuration.setProperty("hibernate.generate_statistics", "false")
    configuration.setProperty("hibernate.use_sql_comments", "false")
    configuration.setProperty("hibernate.connection.autocommit", "false")
     configuration.setProperty("hibernate.show_sql", "true")
     configuration.setProperty("hibernate.format_sql", "true")
     configuration.setProperty("hibernate.current_session_context_class", "thread")
     configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop")
     configuration
  }
}
