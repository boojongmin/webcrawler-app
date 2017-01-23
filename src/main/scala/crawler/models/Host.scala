package crawler.models

import java.time.{Duration, LocalDateTime, Period}
import javax.persistence._

@Entity
@Table(name="HOST")
@Access(value=AccessType.FIELD)
class Host(_name: String, _urls: java.util.List[Url], _status: Boolean, _nextDate: LocalDateTime) extends Trait{

  def this() {
    this(null, null, false, null)
  }

  def this(_name: String, _urls: java.util.List[Url]) {
    this(_name, _urls, false, LocalDateTime.now())
  }

  def this(_name: String, _status: Boolean ) {
    this(_name, new java.util.ArrayList[Url](), _status, LocalDateTime.now())
  }

  var name: String = _name

  @OneToMany(cascade = Array(CascadeType.ALL), orphanRemoval = true, fetch = FetchType.EAGER)
  var urls: java.util.List[Url] = _urls
  var status: Boolean = _status
  var nextDate: LocalDateTime = _nextDate
  var interval: Duration = Duration.ofMinutes(10)

  override def toString = s"Host(name=$name, urls=$urls, status=$status, nextDate=$nextDate, interval=$interval)"
}
