package crawler.models

import java.time.{Duration, LocalDateTime, Period}
import javax.persistence._

@Entity
@Table(name="host")
@Access(value=AccessType.FIELD)
class Host(_a1: String, _a2: String, _a3: Boolean, _a4: LocalDateTime) extends Trait{

  def this() {
    this(null, null, false, null)
  }

  def this(_a1: String, _a2: String, _a3: Boolean) {
    this(_a1, _a2, _a3, LocalDateTime.now())
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Int = _
  var name: String = _a1
  var url: String = _a2
  var status: Boolean = _a3
  var nextDate: LocalDateTime = _a4
  var interval: Duration = Duration.ofMinutes(10)

  override def toString = s"Host($id, $name, $url, $status, $nextDate, $createdAt, $updatedAt)"
}
