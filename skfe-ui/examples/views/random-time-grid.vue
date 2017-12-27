<template>
    <div>
        <div class="random-time-grid">
            <TimeGrid :countByDate="demo.countsByDate">
            </TimeGrid>
        </div>
        <div class="random-time-grid" style="margin-left: 200px;">
            <TimeGrid :countByDate="demo.countsByDate" :cssDateTdHeight="10" :cssDateTdPadding="0">
            </TimeGrid>
        </div>
        <div class="random-time-grid"style="margin-left: 400px;">
            <TimeGrid :countByDate="demo.countsByDate" :cssDateTdHeight="20" >
            </TimeGrid>
        </div>
    </div>
</template>

<script>
    import TimeGrid from '../../src/components/time-grid.vue'
    import moment from 'moment'

    export default {
        data: () => ({
            demo: {
                countsByDate: []
            }
        }),
        created() {
            for (let i = 0; i < 100; i++) {
                let d = moment().add(i - 100, 'd').format('YYYY-MM-DD')
                this.demo.countsByDate.push({date: d, cnt: i - 5})
            }
            for (let i = 0; i < 800; i++) {
                let d = moment().add(i, 'd').format('YYYY-MM-DD')
                let p = Math.random()
                let v = 0
                if (p > 0.9) {
                    v = (Math.random() * 70).toFixed() + 20
                } else if (p > 0.3) {
                    v = (Math.random() * 16).toFixed()
                }
                this.demo.countsByDate.push({date: d, cnt: v})
            }
        },
        components: {TimeGrid}
    }
</script>
<style scoped>
    .random-time-grid {
        float: left;
    }
</style>
