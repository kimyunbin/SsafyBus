<template>
  <div class="text-center">
    <v-pagination
      v-model="page"
      :length="total_page"
      color = #17B0E7
      @input="change"    
    ></v-pagination>
  </div>
</template>

<script>
export default {
  props: {
    pagnationInfo:{
      type: Object,
      required:true
    }
  },
  data () {
    return {
      page: this.pagnationInfo.page+1,
      total_page: this.pagnationInfo.total,
    }
  },
  methods: {
    change(value) {
      console.log(value)
      this.page = value
      this.$emit('update', value);
    },
  }
}
</script>
<style lang="scss" scoped>
.v-pagination__item--active {
  color: #17B0E7 !important
}

</style>