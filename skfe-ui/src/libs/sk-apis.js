const CB_NO_OP = (d) => {}
const DEFAULT_HEADERS = {
  'Accept': 'application/json, text/plain, */*',
  'Content-Type': 'application/json'
}
/**
 * respond synchronisely for REST API in Skean 2.x
 *
 * resp (asynchronised) -> resp2(synchronised)
 * resp2 is like
 * {ok,statusCode,totalAffected,totalCount,data}
 * @param callback
 */
const respond = callback => resp => {
  const statusCode = resp.status
  const totalAffected = parseInt(resp.headers.get('X-Total-Affected')) || null
  const totalCount = parseInt(resp.headers.get('X-Total-Count')) || null
  const ok = resp.ok
  resp.text().then(text => {
    let resp2 = {ok, statusCode}
    if (totalAffected) resp2.totalAffected = totalAffected
    if (totalCount) resp2.totalCount = totalCount
    let data = text
    try {
      data = JSON.parse(text)
    } catch (err) {
    }
    resp2.data = data
    callback(resp2)
  })
}

export { respond, CB_NO_OP, DEFAULT_HEADERS }
