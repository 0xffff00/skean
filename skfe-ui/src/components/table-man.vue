<template>
    <div>
        <h1>{{model.name}}管理</h1>
        <div class="pane criteria-pane">
            <slot name="criteria-pane"></slot>
        </div>
        <div class="pane control-pane">
            <slot name="control-pane">

                <ButtonGroup shape="circle">
                    <Button type="ghost" icon="ios-plus-empty" @click="data.editor.item=null,ui.editing=true"></Button>
                    <Button type="error" icon="trash-a" disabled></Button>
                </ButtonGroup>

                <span class="rt">
          <Button type="primary" shape="circle" icon="ios-search" @click="readItems()"></Button>
        </span>
            </slot>
        </div>
        <div class="pane result-pane">
            <slot name="result-pane">

                <Table border :loading="ui.loading" :columns="computedModel.columns" :data="data.result.items"></Table>
                <div class="pager rt">
                    <Page :page-size-opts="[3,5,10, 20 ,30, 50, 100]"
                          :page-size="querier.pageSize" :current.sync="querier.pageNum" :total="data.result.totalCount"
                          size="small" show-total show-elevator show-sizer
                          @on-page-size-change="pageSizeChanged"></Page>
                </div>
            </slot>
        </div>


        <slot name="editorDialog">
            <Modal v-model="ui.editing" :loading="ui.saving" :title="editorTitle" :ok-text="editorOkText"
                   @on-ok="saveItem()" @on-cancel="">
                <Form :model="data.editor.item" :label-width="80">
                    <FormItem v-for="col in computedModel.columns" v-if="!col.hideInEditor" :label="col.title">
                        {{col.hideInEditor}}
                        <template v-if="col.key && col.editable">
                            <template v-if="col.sk2template==='state'">
                                <RadioGroup v-model="data.editor.item[col.key]" type="button">
                                    <Radio label="A" color="red">活动的</Radio>
                                    <Radio label="S">停止的</Radio>
                                </RadioGroup>
                            </template>
                            <template v-else>
                                <Input v-model="data.editor.item[col.key]" :placeholder="'请输入'+col.key"></Input>
                            </template>
                        </template>
                        <template v-else>
                            {{data.editor.item[col.key]}}
                        </template>

                    </FormItem>
                </Form>
            </Modal>


        </slot>

    </div>
</template>

<script>
    /**
     * offer a common rich table module.
     * implemented by wrapping a iView Table.
     * it has functions:
     *  - management operations such as CRUD
     *  - pagination, sorting by column
     *  - query by criteria
     */
    import _ from 'lodash'
    import Renders from './table-man-renders'
    import MsgBox from '../libs/sk-ivu-msg-box'

    export default {
        name: 'table-man',
        data() {
            return {}
        },
        computed: {
            computedModel() {
                const self = this
                const columnDefault = {
                    title: '',
                    required: false,
                    editable: true,
                    sortable: false,
                    type: 'string',
                    render: (item, column, items) => column ? item[column.name] : 'NA11'
                }
                let columns = this.model.columns.map(x => {
                    let y = {}
                    y.key = x.key
                    y.title = x.title || columnDefault.title
                    y.render = x.render
                    y.required = x.required || columnDefault.required
                    y.editable = x.editable || columnDefault.editable
                    y.sortable = x.sortable || columnDefault.sortable
                    y.sk2template = x.sk2template
                    if (y.sk2template === 'action') {
                        let sk2actions = x.sk2actions
                        y.hideInEditor = true
                        if (sk2actions && sk2actions.length) {
                            // replace constant templates
                            let _x1 = sk2actions.findIndex(x => x === 'edit')
                            if (_x1 >= 0) {
                                sk2actions[_x1] = {
                                    actionName: '编辑',
                                    actionFunc: self.openEditorDialog,
                                    renderType: 'rBtn'
                                }
                            }
                            let _x2 = sk2actions.findIndex(x => x === 'delete')
                            if (_x2 >= 0) {
                                sk2actions[_x2] = {actionName: '删除', actionFunc: self.deleteItem}
                            }
                            y.render = (h, params) => {
                                let renders = Renders.resolveToRenders(sk2actions, self, h, params)
                                return h('ButtonGroup', {}, renders)
                            }
                        }
                    }
                    if (y.sk2template === 'state') {
                        y.render = Renders.rState(self)(y.key)
                    }
                    return y
                })
                return {columns}
            },
            translatedQuerierParams() {
                let q = this.querier
                let rp = {}
                if (q.pageSize) {
                    rp.p = q.pageNum
                    rp.l = q.pageSize
                }
                if (q.orderBy) {
                    rp.o = q.orderBy
                }
                q.criteria.forEach(e => {
                    let tail = e.operator ? '_' + e.operator : ''
                    rp[e.name + tail] = e.value
                })
                return rp
            },
            editorTitle() {
                return this.model.name + ' - ' + (this.isEditingForCreate ? '新增' : '修改')
            },
            editorOkText() {
                return this.ui.saving ? '保存中...' : '保存'
            },
            isEditingForCreate() {
                return this.data.editor.itemOld === null
            }
        },
        props: {
            'model.name': {type: String, default: '条目'},
            'model.api': {type: Object},
            data: {type: Object},
            querier: {type: Object},
            ui: {type: Object, default: {loadTick: 0}},
            model: {type: Object}

        },
        created() {
            this.readItems()
        },
        methods: {
            readItems() {
                let self = this
                self.ui.loading = true
                self.model.api.httpGetSome(self.translatedQuerierParams)(resp2 => {
                    console.log(resp2)
                    // self.$message({type: 'success', message: '加载成功 x ' + self.ui.loadTick})
                    if (resp2.ok) {
                        self.data.result.items = resp2.data
                        self.data.result.totalCount = resp2.totalCount
                    } else {
                        MsgBox.open(self, '加载')
                    }
                    self.ui.loading = false
                })
            },
            deleteItem(item) {
                const self = this
                self.ui.deleting = true
                self.model.api.httpDelete(item)(MsgBox.open(self, '删除'))
            },
            saveItem() {
                const self = this
                this.ui.saving = true
                const isNew = self.data.editor.itemOld === null
                if (isNew) {
                    self.model.api.httpPost(self.data.editor.item)(MsgBox.open(self, '创建'))
                } else {
                    const changes = changesOfItem(self.data.editor.itemOld, self.data.editor.item, self.model.columns)
                    self.model.api.httpPatch(self.data.editor.itemOld, changes)(MsgBox.open(self, '更新'))
                }
            },

            initEditor() {
                let item = this.data.editor.item
                this.data.editor.itemOld = _.cloneDeep(item)
                if (item === null) {
                    this.data.editor.item = _.cloneDeep(this.data.editor.itemDefault)
                }
            },
            openEditorDialog(item) {
                this.data.editor.item = _.cloneDeep(item)
                this.ui.editing = true
            },
            pageSizeChanged(pageSize) {
                this.querier.pageSize = pageSize
            }
        },
        watch: {
            'ui.loadTick': function (v) {
                this.readItems()
            },
            'querier.pageNum': function (v) {
                this.readItems()
            },
            'querier.pageSize': function (v) {  // iview Page does not support pageSize.sync
                this.readItems()
            },
            'ui.editTick': function () {
                this.openEditorDialog()
            },
            'ui.editing': function (v) {
                if (v === true) {
                    this.initEditor()
                }
            },
            'ui.editor.title': function () {
                let n = this.itemName
                return (this.ui.currItem) ? '新增' + n : '修改' + n
            }
//      'data.deleter.item': function (v) {
//        if (v === null) {
//          return
//        }
//        this.openDeleteConfirmDialog()
//      }
        }
    }

    function changesOfItem(oldItem, newItem, itemCols) {
        let keysPicked = Object.keys(newItem)
            .filter(k => itemCols.find(col => col.name === k && col.editable) !== null)
            .filter(k => oldItem[k] !== newItem[k])
        console.log('changesOfItem: ', newItem, oldItem, itemCols, keysPicked, _.pick(newItem, keysPicked))
        return _.pick(newItem, keysPicked)
    }

</script>

<style scoped>
    .pane {
        margin: 5px auto;
    }

    .rt {
        float: right;
    }

    .pager {
        margin: 5px auto;
    }

</style>
