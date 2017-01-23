package crawler.models

import java.time.LocalDateTime
import java.util.Date
import javax.persistence._

import org.hibernate.annotations.{CreationTimestamp, UpdateTimestamp}

trait Trait {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Int = _

  @Column
  @CreationTimestamp
  var createdAt: LocalDateTime = _

  @Column
  @UpdateTimestamp
  var updatedAt: LocalDateTime = _

}
