import axois from 'axios'

/**
 * == how axios works ==
 * axios.post(url,data).then(r1=>{}).catch(r2=>{})
 * r2 may be undefined if axios's timeout is set
 * r1          may be like : {data:{},headers:{..},request:aXHR,status:200}
 * r2.response may be like : {data:{},headers:{..},request:aXHR,status:500}
 * r2.response.data may be like : {debugInfo:{exception:'xx',message:'xx'},error:'xx',message:'xx',status:404}
 *
 * == what SkeanRestApi can do ==
 * SkeanRestApi can unify okResp & errResp to one 'resp2' object like :
 *        {
 *        data:{},headers:{..},request:aXHR,status:20x/40x/50x,
 *        ok:true/false,
 *        totalCount:42,
 *        totalAffected:1
 *        }
 */
class SkeanRestApi {

    constructor(pluralUrl, singularPart = '') {
        this.pluralUrl = pluralUrl
        this.$axois = axois.create({baseURL: pluralUrl, validateStatus: st => true})
        if (typeof singularPart === 'string') {
            this.singularUrlBuilder = (params) => {
                if (!singularPart) return ''
                let u = singularPart
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

    /**
     * get a list of part and count of all by http GET request
     * @param params
     * @return {function}
     */
    gettingSome(params) {
        return callback =>
            this.$axois.get(toQVarStr(params)).then(respond(callback))
    }

    /**
     * get single item
     * @param params
     * @return {function}
     */
    getting(params) {
        return callback =>
            this.$axois.get(this.singularUrlBuilder(params)).then(respond(callback))
    }

    deleting(params) {
        return callback =>
            this.$axois.delete(this.singularUrlBuilder(params)).then(respond(callback))
    }

    deletingSome(params) {
        return callback =>
            this.$axois.delete(toQVarStr(params)).then(respond(callback))
    }

    posting(params) {
        return callback =>
            this.$axois.post(null, params).then(respond(callback))
    }

    /**
     *
     * @param oldParams body in PUT request
     * @param newParams uri-q-params in PUT request
     */
    putting(oldParams, newParams) {
        return callback =>
            this.$axois.put(this.singularUrlBuilder(params), newParams).then(respond(callback))
    }

    /**
     *
     * @param oldParams body in PATCH request
     * @param newParams uri-q-params in PATCH request
     */
    patching(oldParams, newParams) {
        return callback =>
            this.$axois.patch(this.singularUrlBuilder(params), newParams).then(respond(callback))
    }
}


const isOk = st => st >= 200 && st < 300

/**
 * resp -> resp2
 * insert 'ok','total*' to 'resp', and become 'resp2' for REST API in Skean 2.x
 * resp2 is like
 * {ok,statusCode,totalAffected,totalCount,data}
 * @param callback
 */
const respond = callback => resp => {
    let resp2 = {ok: 0, data: null}
    if (resp) {
        resp2.status = resp.status
        resp2.ok = isOk(resp.status)
        const totalAffected = parseInt(resp.headers.get('X-Total-Affected')) || null
        const totalCount = parseInt(resp.headers.get('X-Total-Count')) || null
        if (totalAffected) resp2.totalAffected = totalAffected
        if (totalCount) resp2.totalCount = totalCount
    }
    callback(resp2)
}


/**
 * {a:1,b:'xx'} => ?a=1&b=xx
 * @param params
 */
function toQVarStr(params) {
    if (!params) return ''
    const keys = Object.keys(params)
    if (!keys.length) return ''
    return '?' + keys.map(k => k + '=' + params[k]).join('&')
}

/**
 * <pre>
 *  a RestAPI of user's addresses
 *  templated url: "http:localhost:8080/my-app/users/{uid}/addresses/{addressCode}"
 *  concete url:   "http:localhost:8080/my-app/users/20032/addresses/mars-olp03-32-1021"
 *    parts:        |<------ CTX ------------>|
 *                  |<----------------  pluralUrl ----------------->|<--singularPart-->|
 *                  |<------------------------ singularUrl --------------------------->|
 *
 * const repoRestApi = RestApi.of(CTX + '/users/', '?name={name}&team={team}')   // q var style
 * const repoRestApi = RestApi.of(CTX + '/users/', 'name={name};team={team}')    // matrix var style
 * const repoRestApi = RestApi.of(CTX + '/users/', '{id}')                       // path var style
 * const repoRestApi = RestApi.of(CTX + '/users/', params => 'UID-'+params.id)   // set a func
 * </pre>
 * @param pluralUrl {string}
 * @param singularPart {function|string}, build a url made by primary key columns.
 */
const of = (pluralUrl, singularPart = '') => new SkeanRestApi(pluralUrl, singularPart)
export default {of}