<template>
  <div id="computer">
    <web-cam :location="location"/>
  </div>  
</template>

<script>
import WebCam from '@/components/Openvidu/WebCam.vue';
export default {
  name : "Computer",
  components : {
      WebCam,
  },
  data() {
    return {
      location : "computer",
    }
  },
  created(){
    if(this.$store.getters.getUser === null){
      this.$router.push('/login');
    } else if(this.$store.getters.getSchoolId === null) {
      this.$router.push({
        name: 'Unity',
      })
    }
  }
}
</script>

<style scoped>
#computer{
  display: flex;
  height : 90vh;
  overflow: hidden;
  flex-direction: column;
}
</style>