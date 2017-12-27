<template>
    <div>
        <Menu active-name="1" mode="horizontal" theme="dark">
            <router-link to="/time-grid">
                <MenuItem name="1">时间格子</MenuItem>
            </router-link>

            <MenuGroup title="others" mode="horizontal">
                <MenuItem name="3">用户留存1</MenuItem>
                <MenuItem name="4">流失用户</MenuItem>
            </MenuGroup>
        </Menu>
        <div>
            <router-view></router-view>
        </div>
    </div>
</template>
<script>
    import RandomTimeGrid from './views/random-time-grid.vue'

    export default {
        data: () => ({}),
        created() {

        },
        components: {RandomTimeGrid}
    }
</script>