<template>
  <div>
    <h1>오늘의 방명록을 남겨주세요!✍</h1>
    <button @click="bclick()">방명록 남기기</button>
    <!-- {{list[0].user.nickname}} -->
    <!-- modal -->
    <div class="modal-overlay" v-bind:class="{active: is_active}">
      <div class="modal" v-bind:class="{active: is_active}">
          <a class="close-modal" @click="xclick()">
            <svg viewBox="0 0 20 20">
              <path fill="#000000" d="M15.898,4.045c-0.271-0.272-0.713-0.272-0.986,0l-4.71,4.711L5.493,4.045c-0.272-0.272-0.714-0.272-0.986,0s-0.272,0.714,0,0.986l4.709,4.711l-4.71,4.711c-0.272,0.271-0.272,0.713,0,0.986c0.136,0.136,0.314,0.203,0.492,0.203c0.179,0,0.357-0.067,0.493-0.203l4.711-4.711l4.71,4.711c0.137,0.136,0.314,0.203,0.494,0.203c0.178,0,0.355-0.067,0.492-0.203c0.273-0.273,0.273-0.715,0-0.986l-4.711-4.711l4.711-4.711C16.172,4.759,16.172,4.317,15.898,4.045z"></path>
            </svg>
          </a><!-- close modal -->

        <div class="modal-content">
         <h3>출석체크!</h3>
         <form>
            <input type="textarea" v-model="content" @keyup.enter="submitClick()">
            <button type="button" @click="submitClick()">완료</button>
         </form>
        </div><!-- content -->
        
      </div><!-- modal -->
    </div><!-- overlay -->

  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
const boardStore = 'boardStore'
const userStore = 'userStore'
export default {
  name: "WriteModal",
  props: {
    list:{
      type: Array,
      required:true
      },
  },
  data() {
    return {
      is_active: false, 
      content: '',
    }
  },
  computed: {
  ...mapGetters(userStore, ['user_info']),

  },
  methods: {
  ...mapActions(boardStore, ['writeVisitorBook']),

    bclick() {
      this.is_active = true
    },
    xclick() {
      this.is_active = false
    },
    submitClick() {
      var arr = []
      if (this.list.length > 0) {
        
        for (let i = 0; i < this.list.length; i++) {
          const userId = this.list[i].user.userId;
          arr.push(userId)
        }
      }
      console.log(arr,'arr')
      console.log(this.user_info.userId,'nick')
      if (arr.includes(this.user_info.userId) ) {
        alert('이미 출석체크 했습니다!')
      }
      else {
        var value = {
          'content' : this.content
        }
        this.writeVisitorBook(JSON.stringify(value))
        .then(()=>{
          alert('출석체크가 완료 되었습니다.')
        })
        .then(()=>{
          this.$router.go()
        })
      }
    }
  },
  mounted() {

  },
}
</script>

<style lang="scss" scoped>
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
  border-radius: 10px;
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
.head { 
  width: 100%;
  height: 32px;
  padding: 12px 30px;
  overflow: hidden;
  background: #e2525c;
}
</style>>

