import { CB_NO_OP, respond, DEFAULT_HEADERS } from './SkeanApis'
import SkeanUrls from './sk-urls'

const stringify = obj => obj ? JSON.stringify(obj) : null
export default class RestApi {
  /**
   * <pre>
   *  a RestAPI of user's addresses
   *  templated url: "http:localhost:8080/my-app/users/{uid}/addresses/{addressCode}"
   *  concete url:   "http:localhost:8080/my-app/users/20032/addresses/mars-olp03-32-1021"
   *    parts:        |<------ CTX ------------>|
   *                  |<----------------  pluralUrl ----------------->|<--singularPart-->|
   *                  |<------------------------ singularUrl --------------------------->|
   *
   * const repoRestApi = new Api(CTX + '/users/', '?name={name}&team={team}')   // uri q var style
   * const repoRestApi = new Api(CTX + '/users/', 'name={name};team={team}')    // matrix var style
   * const repoRestApi = new Api(CTX + '/users/', '{id}')                       // uri path var style
   * const repoRestApi = new Api(CTX + '/users/', params => CTX+'/users/'+params.id)  // set a func
   * </pre>
   * @param pluralUrl {string}
   * @param singularPart {function|string}, build a url made by primary key columns.
   */
  constructor (pluralUrl, singularPart = '') {
    this.pluralUrl = pluralUrl
    if (typeof singularPart === 'string') {
      this.singularUrlBuilder = (params) => {
        let u = pluralUrl + (singularPart || '')
        if (params) {
          Object.keys(params).forEach(key => {
            u = u.replace('{' + key + '}', params[key])
          })
        }
        return u
      }
    } else if (typeof singularPart === 'function') {
      this.singularUrlBuilder = singularPart
    } else {
      throw new Error('Api has illegal singularPartTemplate')
    }
  }

  getPluralUrlWithWithQParams (params) {
    return SkeanUrls.appendQParams(this.pluralUrl, params)
  }

  /**
   * get a list of part and count of all by http GET request
   * @param params
   */
  httpGetSome (params) {
    const finalUrl = this.getPluralUrlWithWithQParams(params)
    return callback => fetch(finalUrl, {method: 'GET'}).then(respond(callback))
  }

  httpGet (params) {
    const finalUrl = this.singularUrlBuilder(params)
    return callback => fetch(finalUrl, {method: 'GET'}).then(respond(callback))
  }

  httpPost (params) {
    const finalUrl = this.pluralUrl
    return callback =>
      fetch(finalUrl, {
        method: 'POST',
        headers: DEFAULT_HEADERS,
        body: stringify(params)
      })
        .then(respond(callback))
  }

  httpDelete (params) {
    const finalUrl = this.singularUrlBuilder(params)
    return callback => fetch(finalUrl, {method: 'DELETE'}).then(respond(callback))
  }

  httpDeleteSome (params) {
    const finalUrl = this.getPluralUrlWithWithQParams(params)
    return callback => fetch(finalUrl, {method: 'DELETE'}).then(respond(callback))
  }

  /**
   *
   * @param oldParams body in PUT request
   * @param newParams uri-q-params in PUT request
   */
  httpPut (oldParams, newParams) {
    const finalUrl = this.singularUrlBuilder(oldParams)
    return callback =>
      fetch(finalUrl, {
        method: 'PUT',
        headers: DEFAULT_HEADERS,
        body: stringify(newParams)
      })
        .then(respond(callback))
  }

  httpPatch (oldParams, newParams) {
    const finalUrl = this.singularUrlBuilder(oldParams)
    return callback =>
      fetch(finalUrl, {
        method: 'PATCH',
        headers: DEFAULT_HEADERS,
        body: stringify(newParams)
      })
        .then(respond(callback))
  }
}
