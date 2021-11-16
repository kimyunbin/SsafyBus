<template>
  <div class="imgurl">
    <div class="head">
      <h2 style="font-weight:bold;">{{name}}</h2>
      <h2>에게 <span>무</span>엇이든 <span>물</span>어보는 게시판</h2>
    </div>
    <!-- {{this.qboard}} -->
    <div class="board" v-if="this.qboard.length > 0">
      <div class="container">
        <QBoardDetail
          v-for="(QBoardDetail,key) in qboard"
          :key = key
          :QBoardDetail = QBoardDetail
          />
      </div>
      <div>
        <!-- <button v-if="this.user_info.userId == this.qboard_info" @click="bclick()">질문하기</button> -->
        <button @click="bclick()">질문하기</button>
        <button @click="goBack">돌아가기</button>

        <div class="modal-overlay" v-bind:class="{active: is_active}">
          <div class="modal" v-bind:class="{active: is_active}">
              <a class="close-modal" @click="xclick()">
                <svg viewBox="0 0 20 20">
                  <path fill="#000000" d="M15.898,4.045c-0.271-0.272-0.713-0.272-0.986,0l-4.71,4.711L5.493,4.045c-0.272-0.272-0.714-0.272-0.986,0s-0.272,0.714,0,0.986l4.709,4.711l-4.71,4.711c-0.272,0.271-0.272,0.713,0,0.986c0.136,0.136,0.314,0.203,0.492,0.203c0.179,0,0.357-0.067,0.493-0.203l4.711-4.711l4.71,4.711c0.137,0.136,0.314,0.203,0.494,0.203c0.178,0,0.355-0.067,0.492-0.203c0.273-0.273,0.273-0.715,0-0.986l-4.711-4.711l4.711-4.711C16.172,4.759,16.172,4.317,15.898,4.045z"></path>
                </svg>
              </a><!-- close modal -->

            <div class="modal-content">
            <h3>질문하기</h3>
            <form>
                <input type="textarea" v-model="content" @keyup.enter="submitClick()">
                <button type="button" @click="submitClick()">완료</button>
            </form>
            </div><!-- content -->
            
          </div>
        </div>
      </div>

    </div>
    <div v-else>
      <h2>아직 질문이 없어요</h2>      
      <button @click="bclick2()">질문하기</button>
      <button @click="goBack">돌아가기</button>
      <div class="modal" v-bind:class="{active: is_active}">
        <a class="close-modal" @click="xclick()">
          <svg viewBox="0 0 20 20">
            <path fill="#000000" d="M15.898,4.045c-0.271-0.272-0.713-0.272-0.986,0l-4.71,4.711L5.493,4.045c-0.272-0.272-0.714-0.272-0.986,0s-0.272,0.714,0,0.986l4.709,4.711l-4.71,4.711c-0.272,0.271-0.272,0.713,0,0.986c0.136,0.136,0.314,0.203,0.492,0.203c0.179,0,0.357-0.067,0.493-0.203l4.711-4.711l4.71,4.711c0.137,0.136,0.314,0.203,0.494,0.203c0.178,0,0.355-0.067,0.492-0.203c0.273-0.273,0.273-0.715,0-0.986l-4.711-4.711l4.711-4.711C16.172,4.759,16.172,4.317,15.898,4.045z"></path>
          </svg>
        </a><!-- close modal -->

      <div class="modal-content">
      <h3>질문하기</h3>
      <form>
          <input type="textarea" v-model="content" @keyup.enter="submitClick()">
          <button type="button" @click="submitClick()">완료</button>
      </form>
      </div><!-- content -->
      
    </div>
    </div>

  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
const userStore = 'userStore'
const boardStore = 'boardStore'
import QBoardDetail from '../components/QBoardDetail.vue'
export default {
  components: { QBoardDetail },
  name: "QBoard",
  data() {
    return  {
      content: '',
      is_active: false,
      qboard:'',
      name: '',
      user_id: '',
    }
  },
  created() {
    this.user_id = this.qboard_info.userId
    this.getQBoard(this.user_id)
    .then(()=>{
      this.qboard = this.qboard_info.question
      this.name = this.qboard_info.nickname
    })
  },
  computed: {
    ...mapGetters(userStore, ['user_info']),
    ...mapGetters(boardStore, ['qboard_info']),

  },
  methods: {
    ...mapActions(boardStore, ['writeQBoard', 'getQBoard']),
    bclick() {
      this.is_active = true
    },
    bclick2() {
    this.is_active = true
    },
    xclick() {
      this.is_active = false
    },
    submitClick(){
        var content = {
          'content' : this.content
        }
        const user_id = this.user_id
        console.log(user_id)
        const value = {
          'user_id' : user_id,
          'content': JSON.stringify(content),
        }
      this.writeQBoard(value)
      .then(()=>{
        alert('답변이 언젠가 올꺼에요!')
        this.$router.go()
      })
    },
    goBack(){
      this.$router.go(-1);
    }
  },
  mounted() {
    this.qboard = this.qboard_info.question
  }
}
</script>

<style lang="scss" scoped>
body, html{
  width: 100%;
  height: 100vh;
  margin: 0;
  padding: 0;
}
.imgurl {
  // width: 100vw;
  // height: 100vh;
  // background-image: url("../assets/qboard.png");
  // background-size: cover;
  // background-position: center;
  // padding-top: 60px;
}

.head {
  display: flex;
  justify-content: center;
  margin-top: 60px;
}

.goBack{
  position: absolute;
  left: 0;
}

.board {
 border: 4px solid #FFE651;
 border-radius: 10px;
 margin-top: 50px;
 padding-bottom: 30px;
 background-color: white;
 max-width: 1200px;
 margin-left: auto;
 margin-right: auto;
}

.container {
  max-width: 100vw;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  grid-gap: 35px;
  margin: 0 auto;
  padding: 40px 0;
}
/**
 * Variables
 */
$button-bg: #17B0E7;
$speed: 0.6s;
$delay: ($speed * .5);
$easing: cubic-bezier(.55,0,.1,1);


/**
 * Base styles
 */


h1, h2, h3, h4, h5, h6 {
  font-weight: normal;
}

h1 {
  font-size: 1.875rem;
  font-weight: 300;
  margin: 60px 0 30px 0;
  color: black;
}

h3 {
  font-size: 16px;
  font-weight: 500;
  margin: 10px 0 30px 0;
  color: black;
}

button {
  background-color: $button-bg;
  position: relative;
  color: #fff;
  border: none;
  padding: 1.25em 2em;
  font-size: 0.75em;
  letter-spacing: 1px;
  text-transform: uppercase;
  cursor: pointer;
  box-shadow: 0 10px 20px rgba(0,0,0,.1);
  transition: background 0.25s $easing;
  border-radius: 10px;
  margin: 0px 20px;
  
  &:hover {
    background: darken($button-bg, 3%);
  }
  
  &:focus {
    outline: none;
  }
}


/**
 * Overlay
 * -- only show for tablet and up
 */
@media only screen and (min-width: 40em) {
  
  .modal-overlay {
    display: flex;
    align-items: center;
    justify-content: center;
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: 5;
    background-color: rgba(#000, 0.6);
    opacity: 0;
    visibility: hidden;
    backface-visibility: hidden;
    transition: opacity $speed $easing, visibility $speed $easing;
    
    &.active {
      opacity: 1;
      visibility: visible;
    }
  }
} // media query

h2{
  font-weight: 700;
}

h2 span{
  color: #17B0E7;
}


/**
 * Modal
 */
.modal {
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  margin: 0 auto;
  background-color: #fff;
  width: 600px;
  max-width: 75rem;
  min-height: 20rem;
  padding: 1rem;
  border-radius: 5px;
  opacity: 0;
  overflow-y: auto;
  visibility: hidden;
  box-shadow: 0 2px 10px rgba(#000, 0.1);
  backface-visibility: hidden;
  transform: scale(1.2);
  transition: all $speed $easing;
  
  .close-modal {
    position: absolute;
    cursor: pointer;
    top: 5px;
    right: 15px;
    opacity: 0;
    backface-visibility: hidden;
    transition: opacity $speed $easing, transform $speed $easing;
    transition-delay: $delay;
    
    svg {
      width: 1.75em;
      height: 1.75em;
    }
  } // close modal
  
  .modal-content {
    opacity: 0;
    backface-visibility: hidden;
    transition: opacity $speed $easing;
    transition-delay: $delay;
  } // content
  
  &.active {
    visibility: visible;
    opacity: 1;
    transform: scale(1);
    
    .modal-content {
      opacity: 1;
    }
    
    .close-modal {
      transform: translateY(10px);
      opacity: 1;
    }
  }
}



/**
 * Mobile styling
 */
@media only screen and (max-width: 39.9375em) {
  
} // media query


a {
  text-decoration: none;
  color: rgb(2, 0, 145);
}

form button {
  display: block;
  width: 200px;
  margin: auto;
  border-radius: 5px;
  border: none;
  outline: none;
  padding: 1rem;
  font-size: 1.1rem;
}

form input {
  display: block;
  width: 300px;
  margin: 1.3rem 0;
  border-radius: 5px;
  border: none;
  outline: none;
  padding: 1rem;
  font-size: 1.1rem;
  box-shadow: inset 2px 2px 5px #babecc, inset -5px -5px 10px 
  #ffffff73;
}

form button {
  background-color: rgb(8, 99, 235);
  color: #fff;
}

</style>