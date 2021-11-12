<template>
  <div id="app">
    <router-view/>
    <Unity v-if="showUnity"/>
  </div>
</template>

<script>
import Unity from '@/components/Unity';
// $store.state.userStore.user_info!==null && 
export default {
  name: 'App',
  components: {
    Unity,
  },
  computed: {
    showUnity: function() {
      if(this.$route.name === 'Login'){
        return false
      }else{
        return true
      }
    }
  },
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  /* margin-top: 60px; */
}
</style>
