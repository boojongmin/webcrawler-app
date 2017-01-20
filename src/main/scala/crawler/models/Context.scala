package crawler.models

class Context(_url: String, _depth: Int, _context: Context) {

  def this(_url: String) {
    this(_url, 0, null)
  }

  var url: String = _url
  var deplth: Int = _depth
  var context: Context = _context

}
