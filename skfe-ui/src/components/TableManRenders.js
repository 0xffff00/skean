import _ from 'lodash'

/*
 *
 * r, short for render, is like (h, params) => h('xxx',{},[h_son1,h_son2])
 */

const rBtn = (vue, actionName, actionFunc, style = null) => (h, params) => {
  style = style || {
    btnProps: {
      shape: 'circle',
      size: 'small'
    },
    btnText: actionName,
    btnTooltip: actionName
  }
  return h('Button', {
    props: style.btnProps,
    on: {
      click: () => actionFunc(params.row)
    },
    style: style.btnStyle
  }, [style.btnText])
}
const rBtnWithConfirm = (vue, actionName, actionFunc, style = null) => (h, params) => {
  style = style || {
    btnProps: {
      shape: 'circle',
      size: 'small'
    },
    btnText: actionName,
    btnTooltip: actionName
  }
  return h('Button', {
    props: style.btnProps,
    on: {
      click: () => {
        vue.$Modal.confirm({
          // title: `即将${actionName},确认继续吗?`,
          content: `即将${actionName},确认继续吗?`,
          onOk: () => {
            actionFunc(params.row)
          },
          onCancel: () => {}
        })
      }
    },
    style: style.btnStyle
  }, [style.btnText])
}

const rBtnWithPopConfirm = (vue, actionName, actionFunc, style = null) => {
  style = style || {
    btnProps: {
      shape: 'circle'
    },
    btnText: actionName
  }
  return (h, params) => wrapToTree([
    {
      type: 'Poptip',
      props: {
        confirm: true,
        title: `即将${actionName},确认继续吗?`
      },
      on: {
        'on-ok': () => {
          actionFunc(params.row)
        },
        'on-cancel': () => {}
      }
    },
    {
      type: 'Button',
      props: style.btnProps,
      style: style.btnStyle
    },
    style.btnText
  ])(h, params)
}

const rState = vue => (key) => (h, params) => {
  const stateMap = {
    'A': {text: '活动', color: 'green'},
    'S': {text: '停止', color: 'red'}
  }
  let val = params.row[key]
  if (!val) {
    return null
  }
  let v = stateMap[val] || {text: val, color: undefined}
  return h(
    'Tag', {
      props: {color: v.color}
    },
    v.text
  )
}

const rRadio = vue => (valMap) => (h, params) => {
  let radiosArr = valMap.keys().map(k => {
    let v = valMap[k]
    return h('Radio', {props: {label: v}}, k)
  })
  return h(
    'RadioGroup', {
      props: {
        type: 'button'
      }
    },
    radiosArr
  )
}

/**
 * <pre>
 * flattenDomNode ([
 *   {type:'Button',props:{},style:{}},
 *   {type:'div',props:{},style:{}},
 *   'aaaaa'
 * ])
 * ===
 * (h,params) => h('Button',{props:{},style:{}},
 * [
 *    h('div',{props:{},style:{}},
 *    [
 *       'aaaaa'
 *    ])
 * ])
 *
 * </pre>
 * @param flattenDomNodes
 */
function wrapToTree (flattenDomNodes) {
  return function (h, params) {
    let n = flattenDomNodes.length
    if (n <= 0) {
      return null
    }
    let node0 = flattenDomNodes[0]
    if (typeof node0 === 'string') {
      return node0
    }
    if (n === 1) {
      return h(node0.type, {props: node0.props, on: node0.on, style: node0.style}, [])
    }
    let childH = wrapToTree(flattenDomNodes.slice(1))(h, params)
    return h(node0.type, {props: node0.props, on: node0.on, style: node0.style}, [childH])
  }
}

function resolveToRenders (sk2actions, vue, h, params) {
  return sk2actions.map(sk2action => resolveToRender(sk2action, vue, h, params))
}

function resolveToRender (sk2action, vue, h, params) {
  let renderType = sk2action.renderType
  let actionName = sk2action.actionName
  let actionFunc = sk2action.actionFunc
  let style = sk2action.style
  let r = rBtnWithConfirm
  if (renderType === 'rBtn') {
    r = rBtn
  }
  return r(vue, actionName, actionFunc, style)(h, params)
}

export default {
  rBtn,
  rBtnWithConfirm,
  rRadio,
  rState,
  resolveToRenders
}
