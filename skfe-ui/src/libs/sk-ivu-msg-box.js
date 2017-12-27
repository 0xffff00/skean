/**
 * resp2 -> errInfo
 * @param resp2
 * @param actionName
 * @returns {{title: string, body: string}}
 */
function toErrInfo (resp2, actionName = '操作') {
  // console.log(resp2, actionName)
  let st0 = resp2.status || (resp2.data ? resp2.data.status : '')
  let st1 = st0 ? '[' + st0 + ']' : ''
  let err1 = resp2.data && resp2.data.error || ''
  let bd1 = resp2.data ? (resp2.data.message || '') + (resp2.data.debugInfo || '') : null
  return {
    title: `${actionName}失败: ${st1} ${err1}`,
    body: bd1
  }
}

const opening = (vue, actionName = null) => (resp2) => {
  if (resp2.ok) {
    let title = (actionName || '操作') + '成功'
    let body = ''
    if (resp2.totalAffected) {
      body = resp2.totalAffected + '个条目' + (actionName ? '已' + actionName : '受影响')
    }
    vue.$Notice.success({title: title, desc: body})
  } else {
    let errInfo = toErrInfo(resp2, actionName)
    vue.$Notice.error({title: errInfo.title, desc: errInfo.body, duration: 0})
  }
}

function openConfirmDialog (actionDesc, nextAction) {
  this.vue.$confirm('即将' + actionDesc + ', 是否继续?', '警告', {
    confirmButtonText: '是',
    cancelButtonText: '否',
    type: 'warning'
  }).then(nextAction)
    .catch(() => {
      this.vue.$message({type: 'info', message: '已取消' + actionDesc})
    })
}

export default {
  open: opening
}
