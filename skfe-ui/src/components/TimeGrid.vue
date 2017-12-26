<template>
  <div class="time-grid" :style="cssStyleOfTimeGrid">
    <table class="month-bar" :style="cssStyleOfMonthBar">
      <tr v-for="month in involvedMonths">
        <td :style="cssOfMonthBlock(month)">
          <span v-if="month.endsWith('01')" class="year" :style="cssStyleOfYear">{{month.slice(0, 4)}}</span>
          <span v-else class="month" :style="cssStyleOfMonth">{{month.slice(5, 7)}}</span>
        </td>
      </tr>
    </table>
    <table class="week-bar" :style="cssOfWeekBar">
      <tr v-for="y1 in involvedWeeksCount">
        <td v-for="x1 in 7" :class="cssClassOfDateTd(x1-1,y1-1)" :style="cssStyleOfDateTd(x1-1,y1-1)">
          <a :style="cssOfDateBlock(x1 - 1, y1 - 1)"
             :title="getItem(x1 - 1, y1 - 1).date+'('+getItem(x1 - 1, y1 - 1).cnt+')'"
             :href="hrefOfDate(x1 - 1, y1 - 1)"
             @click="clickDateBlock(x1 - 1, y1 - 1)">
          </a>
        </td>
      </tr>
    </table>
  </div>
</template>
<script>
  import _ from 'lodash'
  import moment from 'moment'

  /**
   * code conventions:
   * x: which day of week , 0 - Monday, 7 - Sunday
   * y: which week of TimeGrid
   * idx: the index of vue.data
   *
   */
  export default {
    name: 'TimeGrid',
    data () {
      return {
        aaa: 1
      }
    },
    props: {
      countByDate: {type: Array},
      cssDateTdHeight: {type: Number, default: 17},
      cssDateTdPadding: {type: Number, default: 1}
    },
    computed: {
      offsetInFirstWeek () {
        let a0 = this.countByDate[0]
        if (!a0 || !a0.date) return 0
        return (moment(a0.date).day() + 6) % 7  // default format: 'YYYY-MM-DD'
      },
      involvedWeeksCount () {
        return Math.ceil((this.offsetInFirstWeek + this.countByDate.length) / 7)
      },
      countMapByMonth () {
        return _.mapValues(_.groupBy(this.countByDate, x => x.date.slice(0, 7)), x => x.length)
      },
      countMapByYear () {
        return _.mapValues(_.groupBy(this.countByDate, x => x.date.slice(0, 4)), x => x.length)
      },
      involvedMonths () {
        return _.keys(this.countMapByMonth)
      },
      involvedMonthsCount () {
        return this.involvedMonths.length
      },
      xOf1stDateInEachMonth () { // y -> x of 1st date in month
        let res = new Array(this.involvedWeeksCount).fill(-1)
        for (let k = 0; k < this.countByDate.length; k++) {
          let d = this.countByDate[k]
          if (d.date.endsWith('01')) {
            let co = this.coord(k)
            res[co.y] = co.x
          }
        }
        return res
      },
      xOf1stDateInEachYear () { // y -> x of 1st date in year
        let res = new Array(this.involvedWeeksCount).fill(-1)
        for (let k = 0; k < this.countByDate.length; k++) {
          let d = this.countByDate[k]
          if (d.date.endsWith('01-01')) {
            let co = this.coord(k)
            res[co.y] = co.x
          }
        }
        return res
      },
      monthBarStyle () {
        let map = this.countMapByMonth
        let dtdh = this.cssDateTdHeight
        let accCnt = 0
        let heightTotal = 0
        let h = 0
        let heightByMonthMap = {}
        Object.keys(map).forEach(month => {
          accCnt += map[month]
          heightByMonthMap[month] = h = Math.round(accCnt / 7 * dtdh - heightTotal)
          heightTotal += h
        })
        return {
          heightByMonthMap, // month -> month block's height without fractal
          heightTotal,  // height of bar
          width: dtdh * 7,
          monthFontSize: Math.round(dtdh * 2.5),
          yearFontSize: Math.round(dtdh * 2)
        }
      },
      cssOfWeekBar () {
        return ``
      },
      cssStyleOfMonthBar () {
        let w = this.monthBarStyle.width
        return `width:${w}px;`
      },
      cssStyleOfTimeGrid () {
        let w = this.monthBarStyle.width + 8
        return `width:${w}px;`
      },
      cssStyleOfYear () {
        return `font-size:${this.monthBarStyle.yearFontSize}pt`
      },
      cssStyleOfMonth () {
        return `font-size:${this.monthBarStyle.monthFontSize}pt`
      }
    },
    mounted () {

    },

    methods: {
      /**
       * 76 -> {x：6,y:10}
       */
      coord (idx) {
        let k = this.offsetInFirstWeek + idx
        return {x: k % 7, y: parseInt(k / 7)}
      },
      /**
       * （6，10) -> 76
       */
      idx (x, y) {
        let idx = x + y * 7 - this.offsetInFirstWeek
        if (idx < 0 || idx >= this.countByDate.length) {
          return -1
        }
        return idx
      },
      getItem (x, y) {
        let idx = this.idx(x, y)
        return idx < 0 ? {} : this.countByDate[idx]
      },
      cssStyleOfDateTd (x, y) {
        let dtdh = this.cssDateTdHeight
        let dtdp = this.cssDateTdPadding
        return `
        width: ${dtdh}px;
        height: ${dtdh}px;
        padding: ${dtdp}px;
        background: rgba(255, 255, 255, 0.8);
        `
      },
      cssClassOfDateTd (x, y) {
        let res = []
        let e = this.xOf1stDateInEachMonth[y]
        if (e !== -1) {
          res = (x >= e) ? ['mo', 'mo-up'] : ['mo', 'mo-dn']
          if (x === e && e !== 0) res.push('mo-lt')
        }
        let e1 = this.xOf1stDateInEachYear[y]
        if (e1 !== -1) {
          res = (x >= e1) ? ['yr', 'yr-up'] : ['yr', 'yr-dn']
          if (x === e1 && e1 !== 0) res.push('yr-lt')
        }
        return res
      },
      cssOfDateBlock (x, y) {
        let v = this.getItem(x, y).cnt
        let c = J_1_9_GREEN(v)
        return `background: rgba(${c.r},${c.g},${c.b},0.7);`
      },
      cssOfMonthBlock (month) {
        let h = this.monthBarStyle.heightByMonthMap[month]
        let monNo = parseInt(month.slice(5, 7))
        let bgc = monNo % 2 ? '#e2e' : '#e4a'
        return `height:${h}px;color:${bgc}`
      },
      hrefOfDate (x, y) {
//        let idx = this.idx(x, y)
//        let v = null
//        for (let i = idx; i < idx + 50 && i < this.countByDateObjArr.length; i++) {
//          if ((v = this.countByDateObjArr[i]).cnt) break
//        }
        let v = this.getItem(x, y)
        if (v && v.cnt) return `#img-${v.date}`
        return `#img-${v.date && v.date.slice(0, 4)}`
      },
      clickDateBlock (x, y) {
        let v = this.getItem(x, y)
        this.$emit('clickDate', v)
      }
    }
  }

  /**
   * mark C=J(x), so J is like a 'J' curve
   * x is number
   * C is color object, like {r: 12, g: 233, b: 48}
   * [1,p] is linear
   * [p,q] is exponential, log2 curve
   * @param C0 color when x=0
   * @param C1 color when x=1
   * @param Cp color when x=p
   * @param Cq color when x=q
   * @param p {number} [1,p] is linear
   * @param q {number} [p,q] is exponential
   */
  const J = (C0, C1, Cp, Cq, p, q) => x => {
    let k = 0
    // bound guarantee
    if (x < 0) x = 0
    if (x > q) x = q
    if (x === 0) return C0
    if (x <= p) {
      k = (x - 1) / (p - 1)
      return midRgb(C1, Cp, k)
    } else {
      k = (Math.log2(x - 1) - Math.log2(p - 1)) / (Math.log2(q - 1) - Math.log2(p - 1))
      return midRgb(Cp, Cq, k)
    }
  }
  const J_1_9_GREEN = J(
    {r: 233, g: 233, b: 233},
    {r: 32, g: 233, b: 82},
    {r: 45, g: 128, b: 45},
    {r: 48, g: 64, b: 0},
    9, 65
  )

  const midNum = (x, y, k) => x + (y - x) * k
  const midInt = (x, y, k) => Math.floor(x + (y - x) * k)
  const midRgb = (x, y, k) => ({r: midInt(x.r, y.r, k), g: midInt(x.g, y.g, k), b: midInt(x.b, y.b, k)})
  let x = 2
  x = 3
  x = 6
  x = 3
  x = 6
  x = 3
  x = 6

</script>
<style scoped>

  ::-webkit-scrollbar {
    width: 8px;
    height: 0;
  }

  ::-webkit-scrollbar-track-piece {
    background: #ccc
  }

  ::-webkit-scrollbar-thumb {
    background: #aaa
  }

  .time-grid {
    position: fixed;
    overflow: scroll;
    height: 90%;
    width: 100%;
  }

  table.month-bar {
    position: absolute;
    z-index: 4;
    width: 30px;
    border-spacing: 0;
  }

  table.month-bar td {
    vertical-align: top;
  }

  table.month-bar td .year {
    position: absolute;
    color: #100;
    font-weight: 900;
    width: 100%;
    text-align: center;
  }

  table.month-bar td .month {
    position: absolute;
    font-weight: 500;
    color: #111;
    width: 100%;
    text-align: center;
  }

  table.week-bar {
    position: absolute;
    z-index: 5;
    border-spacing: 0;
  }

  /*table.week-bar td {*/
  /*width: 17px;*/
  /*height: 17px;*/
  /*!*margin: 1px;*!*/
  /*padding: 1px;*/
  /*color: red;*/
  /*background: rgba(255, 255, 255, 0.8);*/
  /*}*/

  table.week-bar td a {
    display: block;
    width: 100%;
    height: 100%;

  }

  table.week-bar td.mo {
    border-color: rgba(144, 140, 50, 0.3);
    border-style: dashed;
    border-width: 0;
  }

  table.week-bar td.mo-dn {
    border-bottom-width: 1px;
  }

  table.week-bar td.mo-up {
    border-top-width: 1px;
  }

  table.week-bar td.mo-lt {
    border-left-width: 1px;
  }

  table.week-bar td.yr {
    border-color: rgba(56, 43, 50, 0.8);
    border-style: solid;
    border-width: 0;
  }

  table.week-bar td.yr-dn {
    border-bottom-width: 1px;
  }

  table.week-bar td.yr-up {
    border-top-width: 1px;
  }

  table.week-bar td.yr-lt {
    border-left-width: 1px;
  }


</style>
