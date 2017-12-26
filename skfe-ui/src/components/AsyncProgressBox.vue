<template>
  <Modal v-model="ui.visible" :title="ui.title"
         @on-cancel="ui.visible=false" :closable="true" :mask-closable="false">
    <Progress :percent="progressPercent" status="active"></Progress>

    <b>{{status.current}}/{{status.totalSteps}}</b>
    <p class="curr-msg">{{status.currentMessage}}</p>
    <p class="curr-msg">
      <b v-if="getResult['OK']" class="msg-sr-ok">成功: {{getResult['OK']}}; </b>
      <b v-if="getResult['SKIPPED']" class="msg-sr-skipped">跳过: {{getResult['SKIPPED']}}; </b>
      <b v-if="getResult['FAILED']" class="msg-sr-failed">失败: {{getResult['FAILED']}}; </b>

    </p>
    <div slot="footer">
    </div>
  </Modal>
</template>
<script>
  import MsgBox from '../libs/MsgBox'

  export default {
    name: 'AsyncProgressBox',
    data () {
      return {
        status: {
          category: null,
          current: 0,
          totalSteps: 0,
          currentMessage: null,
          results: null
        },
        ui: {
          visible: false,
          title: ''
        },
        jobId: 0,
        failCnt: 0
      }
    },
    computed: {
      progressPercent: function () {
        return Math.floor(this.status.current / this.status.totalSteps * 100)
      },
      getResult: function () {
        let res = this.status.results
        return res || {}
      }

    },
    props: {
      name: {type: String},
      startTick: {type: Number},
      startHeartbeatTick: {type: Number, default: 0},
      /**
       * apis.start: a PUT
       * apis.status: a GET must respond Skean JobStatus
       * apis.statusAll: a GET must respond Skean JobStatus
       */
      apis: {type: Object},
      params: {type: Object},
      heartbeatInterval: {type: Number, default: 500},
      heartbeatTimeout: {type: Number, default: 60 * 15 * 1000},  // 15min
      heartbeatMaxFailTrials: {type: Number, default: 3}
    },
    created () {
    },
    methods: {
      start () {
        const self = this
        if (self.status.category === 'RUNNING') {
          self.$Notice.warning({title: '无法同时运行多个任务!'})
          return
        }
        self.apis.start(self.params)(resp2 => {})
        self.startHeartbeat(self.heartbeatInterval, self.heartbeatTimeout)
      },
      startHeartbeat (interval, timeout) {
        const self = this
        self.status.current = 0
        self.ui.title = `[进行中] ${self.name}`
        self.ui.visible = true
        self.failCnt = 0
        self.jobId = setInterval(self.refreshStatus, interval)
        console.info(`starting Heartbeat Job[id=${self.jobId}]...`)
        setTimeout(() => {
          self.stopHeartbeat()
        }, timeout)
      },
      stopHeartbeat () {
        const self = this
        console.info(`stopping Heartbeat Job[id=${self.jobId}]...`)
        clearInterval(self.jobId)
      },
      refreshStatus () {
        const self = this
        self.apis.status(resp2 => {
          if (resp2.data && resp2.data.current) self.status = resp2.data
          if (!resp2.ok) {
            MsgBox.open(self, '获取任务进度')(resp2)
            self.failCnt++
            if (self.failCnt > self.heartbeatMaxFailTrials) {
              console.info(`fail heartbeat trails reached max value: ${self.heartbeatMaxFailTrials}`)
              self.stopHeartbeat()
            }
          }
//          if (resp2.data && resp2.data.results){
//          }
          if (!resp2.data || resp2.data.category !== 'RUNNING') {
            self.stopHeartbeat()
            self.ui.title = `[${resp2.data.category}] ${self.name}`
            self.status.current = self.status.totalSteps
          }
        })
      }
    },
    watch: {
      startTick: function (v) {
        this.start()
      },
      startHeartbeatTick: function (v) {
        this.startHeartbeat(this.heartbeatInterval, this.heartbeatTimeout)
      }
    }
  }

</script>
<style scoped>
  .curr-msg {
    height: 4em;
  }

  .msg-sr-ok {
    color: darkgreen;
  }

  .msg-sr-skipped {
    color: darkgoldenrod;
  }

  .msg-sr-failed {
    color: darkred;
  }
</style>
