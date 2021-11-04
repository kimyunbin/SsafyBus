<template>
  <div class="comment-box">
    <div class="head">
      <p class="username">{{commentList.user.nickname}}</p>
      <p class="date">{{commentList.createdAt | timeFor}}</p>
    </div>
      <p class="content">{{commentList.content}}</p>
      <hr>
  </div>
</template>

<script>
import { mapActions, mapGetters} from 'vuex'
const boardStore = 'boardStore'

export default {
  name: "CommentDetail",
  props: {
    commentList:{
      type: Object,
      required:true
    },
  },
  data() {
    return {

    }
  },
  created() {
    // this.help_item = this.help_one_info()
  },
  methods: {
    ...mapActions(boardStore, ['getHelpList', 'getHelpItem','writeComment']),
    ...mapGetters(boardStore, ['help_info','help_one_info']),
   
  },
  filters : {
    timeFor : function(created_at){
      const today = new Date();
      const timeValue = new Date(created_at);

      const betweenTime = Math.floor((today.getTime() - timeValue.getTime()) / 1000 / 60);
      if (betweenTime < 1) return '방금전';
      if (betweenTime < 60) {
          return `${betweenTime}분전`;
      }

      const betweenTimeHour = Math.floor(betweenTime / 60);
      if (betweenTimeHour < 24) {
          return `${betweenTimeHour}시간전`;
      }

      const betweenTimeDay = Math.floor(betweenTime / 60 / 24);
      if (betweenTimeDay < 365) {
          return `${betweenTimeDay}일전`;
      }

      return `${Math.floor(betweenTimeDay / 365)}년전`;
    }
  }
}
</script>

<style scoped>
.comment-box {
  margin-bottom: 30px;
}
.head {
  display: flex;
  /* justify-content: center; */
}
.date {
  color:gray;
  margin-left: 10px;
}
.content {
  text-align:left;
}
</style>>
