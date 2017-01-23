package crawler.models

import javax.persistence._

@Entity
@Table(name="URL")
class Url(_url: String) extends Trait{

  def this() = {
    this(null)
  }

//  @ManyToOne
//  @JoinColumn(nullable = false, updatable = true, insertable = true)
  @Column
  var hostId: Int = _
  @Column
  var url: String =  _url


  override def toString = s"Url(hostId=$hostId, url=$url)"
}
