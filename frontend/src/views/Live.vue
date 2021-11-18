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
      // location : "computer",
    }
  },
  props: {
    location: {
      type: String,
    }
  },

  created(){
    if(this.$store.getters.getUser === null){
      this.$router.push('/login');
    } else if(this.$store.getters.getSchoolId === null) {
      this.$router.push({
        name: 'UnityGame',
      })
    }
  }
}
</script>

<style scoped>
#computer{
  display: flex;
  width: 99vw;
  height: 98vh;
  margin: 0%;
  padding: 0;
  align-items: center;
  justify-content: center;
}
</style>