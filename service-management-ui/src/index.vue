<template>
  <div>
    <div class="header">
      <Menu mode="horizontal" theme="light" active-name="1">
        <MenuItem name="1">
            <Icon type="md-construct" />
            <router-link to="/task-management"><span>任务管理</span></router-link>
        </MenuItem>
        <MenuItem name="2">
            <Icon type="ios-paper" />
            <router-link to="/service-catalog"><span>服务目录管理</span></router-link>
        </MenuItem>
    </Menu>
    </div>
    <div class="content-container">
      <router-view :key="$route.name"></router-view>
    </div>
  </div>
</template>
<script>
export default {
  components: {},
  data () {
    return {
    }
  },
  methods: {
  },
  created () {
  },
  watch: {
  }
}
</script>
<style lang="scss" scoped>
a {
    height: 60px
}
.header {
  width: 100%;
  display: block;
}
.content-container {
  padding: 5px 30px;
}</style>
