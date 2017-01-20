package crawler.models

import java.time.LocalDateTime
import java.util.Date
import javax.persistence.{Column, PreUpdate}

import org.hibernate.annotations.{CreationTimestamp, UpdateTimestamp}

trait Trait {
  @Column
  @CreationTimestamp
  var createdAt: LocalDateTime = _

  @Column
  @UpdateTimestamp
  var updatedAt: LocalDateTime = _

}
