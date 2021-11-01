<template>
  <div class="writing">
    <p>🖐</p>
    
    <h3>{{visitorDetail.user.nickname}}</h3>

    <div if v-if="visitorDetail.content">
      <p class="content">: {{visitorDetail.content}}</p>
    </div>

    <p class="date">{{visitorDetail.createdAt | timeFor}}</p>
    
  </div>
</template>

<script>

export default {
  name: 'VisitorBookDetail',
  props: {
    visitorDetail:{
      type: Object,
      required:true
    },

  },
  data() {
    return {
      createTime:'',
    }
  },

  computed: {

  },
  created() {

  },

  methods: {

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
.writing {
  display: flex;
  margin-top: 10px;
}
p, h3 {
  margin-right: 20px;
}
.content {
  font-size: 18px;
}
.date {
  color: gray;
}
</style>