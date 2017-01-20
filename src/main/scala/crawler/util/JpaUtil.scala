package crawler.util

import javax.persistence.{EntityManager, EntityManagerFactory}

object jpaSelectOne {
  def apply[T] (f: EntityManager => java.util.List[T])(implicit emf: EntityManagerFactory): Option[T] = {
    val em = emf.createEntityManager()
    val r = f(em)
    import scala.collection.JavaConverters._
    em.close()
    val result = r.asScala
    result.size match {
      case 1 => Some(result.head)
      case _ => None
    }
  }
}

object jpaSelect {
  def apply[T] (f: EntityManager => java.util.List[T])(implicit emf: EntityManagerFactory): Seq[T] = {
    val em = emf.createEntityManager()
    val result = f(em)
    import scala.collection.JavaConverters._
    em.close()
    result.asScala
  }
}

object jpaDML {
  def apply (f: EntityManager => Unit)(implicit emf: EntityManagerFactory): Unit = {
    val em = emf.createEntityManager()
    em.getTransaction.begin()
    f(em)
    em.getTransaction.commit()
    em.close()
  }
}

object jpaInsertOrUpdate {
  def apply[T] (o: T)(implicit emf: EntityManagerFactory) : Unit = {
    val em = emf.createEntityManager()
    em.getTransaction.begin()
    em.persist(o)
    em.getTransaction.commit()
    em.close()
  }
}
