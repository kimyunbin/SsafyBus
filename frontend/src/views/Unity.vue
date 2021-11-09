<template>
  <unity
    src = "./unity/Build/unity.json"
    unityLoader = "./unity/Build/UnityLoader.js"
    height = "600"
    width = "1000"
    >
  </unity>
</template>

<script>
import Unity from 'vue-unity-webgl'

export default {
    components : {
      Unity,
    },
    data() {
        return {
        }
    },
    created(){
    },
}
</script>

<style scoped>
</style>