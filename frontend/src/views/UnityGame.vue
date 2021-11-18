<template>
  <div class="unity-page">
    <div class="logout" @click="goLogin">로그아웃</div>
  </div>
</template>
<script>
export default {
  methods: {
    goLogin() {
      this.$router.push({name:"Login"})
      this.$router.go()
    }
  }
}
</script>
<style lang="scss" scoped>
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}
  .logout{
    position: absolute;
    z-index: 10000;
    // text-align: right;
    // margin-right: 20px;
    background: white;
    padding: 6px 10px 6px 10px;
    margin-top: 5px;
    font-size: 12px;
    right: 10px;
    border-radius: 5px;
    font-weight: 600;
    cursor: pointer;
  }
</style>