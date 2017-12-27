/**
 * appendQParams('xxx?a=2&b=3',{c=4}) === 'xxx?a=2&b=3&c=4'
 * @param urlStr
 * @param params
 * @return {string}
 */
const appendQParams = function (urlStr, params) {
  let url = new URL(urlStr)
  if (params) {
    Object.keys(params).forEach(key => url.searchParams.append(key, params[key]))
  }
  return url.href
}

const getCurrentUrlPathname = function () {
  // TODO maybe not right
  return window.location.pathname
}
export default {appendQParams, getCurrentUrlPathname}
