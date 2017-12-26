<template>
    <div>

        <div>aa2a</div>
        <router-view></router-view>
    </div>
</template>
<script>
  import conf from './libs/config'

  export default {
    data () {
      return {}
    },
    mounted () {

    },
    created () {
      console.log('config=', conf)
    },
    beforeDestroy () {

    },
    methods: {}
  }
</script>