<template>
  <div class="imgurl">
    <img style="margin:auto" src="https://i.ibb.co/5swYQnb/ssafy.png" alt="ssafy" border="0" />
    <div class="cont">
      <div class="form sign-in">
        <h2>신기한 싸피버스</h2>
        <br>
        <label>
          <span>ID</span>
          <input type="text" v-model='login_info.id'/>
        </label>
        <label>
          <span>Password</span>
          <input type="password" v-model='login_info.password'/>
        </label>
        <br>
        <button type="button" class="submit" @click="loginClick()">LOGIN</button>
      </div>
      <div class="sub-cont">
        <div class="img">
          <div class="img__text m--up">
            <h2>처음이신가요?</h2>
            <p style="margin-top:20px">회원가입 후 싸피버스에 같이 참여해보세요!</p>
          </div>
          <div class="img__text m--in">
            <h2>계정이 있나요?</h2>
            <p style="margin-top:20px">이미 계정이 있으시다면 버튼을 눌러 로그인 창으로 가주세요!</p>
          </div>
          <div class="img__btn" @click="change()">
            <span class="m--up">Sign Up</span>
            <span class="m--in">Sign In</span>
          </div>
        </div>
        <div class="form sign-up">
          <!-- <h2>신기한 싸피버스에 오신걸 환영합니다!</h2> -->
          <!-- <br> -->
          <label>
            <span>ID</span>
            <button class="img__btn" style="background-color:black" @click="idCheckClick()">중복체크</button>
            <input type="text" v-model='signup_info.userId'/>
          </label>
          <label>
            <span>이름</span>
            <input type="text"  v-model='signup_info.nickname'/>
          </label>
          <label>
            <span>기수</span>
            <input type="number" v-model='signup_info.generation'/>
          </label>
          <label>
            <span>반</span>
            <input type="number" v-model='signup_info.classNum'/>
          </label>
          <label>
            <span>직위</span>
            <!-- <input type="text" v-model='signup_info.position'/> -->
            <select name="position" v-model='signup_info.position'>
              <option value="">--선택--</option>
              <option value="STUDENT">학생</option>
              <option value="TEACHER">교사</option>
              <option value="CCONSULTANT">컨설턴트</option>
            </select>
          </label>                    
          <label>
            <span>비밀번호</span>
            <input type="password" v-model='signup_info.password'/>
          </label>
            <label>
            <span>비밀번호확인</span>
            <input type="password" v-model='signup_info.password_confirmation'/>
          </label>
          <button type="button" class="submit" @click="signUpClick()">Sign Up</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
const userStore = 'userStore'

export default {
  name:"Login",
  data() {
    return {
      signup_info: {
        userId: '',
        nickname: '',
        generation: '',
        classNum: '',
        position: '',
        password: '',
        password_confirmation: '',
      },
      login_info: {
        id: '',
        password: '',
      },
    }
  },
  methods: {
    ...mapActions(userStore, ['signUp', 'login', 'idCheck']),
    signUpClick() {
      if (this.signup_info.password == this.signup_info.password_confirmation) {
        delete this.signup_info['password_confirmation']
        // console.log(this.signup_info)
        this.signUp(this.signup_info)
        .then(()=>{
          alert('회원가입이 완료되었습니다')
        })
        // this.$router.push({name:"Login"})
        this.$router.go()

      }
      else if (this.signup_info.password != this.signup_info.password_confirmation) {
        alert('비밀번호 확인과 다릅니다')
      }
    },
    loginClick() {
      this.login(this.login_info)
      .then(()=>{
        // alert('로그인 되었습니다')
        this.$router.push({name:"Gate"})
      })
      .catch(()=>{
        alert('아이디 혹은 비밀번호가 틀렸습니다')
      })
      
    },

    change() {
      document.querySelector('.cont').classList.toggle('s--signup');  
    },
    idCheckClick() {
      var value = {
        'userId' : this.signup_info.userId
      }
      console.log(value)
      if (value.userId == "") {
        alert('아이디를 입력해주세요')
      }
      
      else {
        this.idCheck(value)
        .then(()=>{
          alert('사용가능한 아이디입니다')
        })
        .catch((e)=>{
          console.log(e.response)
          alert('존재하는 아이디입니다')
        })

      }
    },
    
  },
}

</script>

<style lang="scss" scoped>
*, *:before, *:after {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}
body, html{
  width: 100%;
  height: 100vh;
  margin: 0;
  padding: 0;
}

.imgurl {
  width: 100vw;
  height: 100vh;
  // background-image: url("../assets/login.png");
  // background-size: cover;
  // background-position: center;
}


input, button {
  border: none;
  outline: none;
  background: none;
  font-family: 'Open Sans', Helvetica, Arial, sans-serif;
}

$contW: 900px;
$imgW: 260px;
$formW: $contW - $imgW; // 이미지 제외하고 쓰려고
$switchAT: 1.1s; // 변환 시 걸리는 시간
$inputW: 260px;
$btnH: 36px;

// $diffRatio: ($contW - $imgW) / $contW;

@mixin signUpActive { // 
  .cont.s--signup & {
    @content;
  }
}
.cont {
  overflow: hidden; // 딱 맞게 자르려고
  position: relative;
  width: $contW;
  height: 700px;
  margin: 0 auto 100px;
  background: #fff;
}

.form {
  position: relative;
  width: $formW;
  height: 100%;
  transition: transform $switchAT ease-in-out; // 변환 ( 초, 천천히변환)
  padding: 10px 30px 0;
}

.sub-cont {
  overflow: hidden;
  position: absolute;
  left: $formW;
  top: 0;
  width: $contW;
  height: 100%;
  padding-left: $imgW;
  background: #fff;
  transition: transform $switchAT ease-in-out;
  @include signUpActive {
    transform: translate3d($formW * -1,0,0);
  }
}
button {
  display: block;
  margin: 0 auto;
  width: $inputW;
  height: $btnH;
  border-radius: 30px;
  color: #fff;
  font-size: 15px;
  cursor: pointer;
}

.img {
  overflow: hidden;
  z-index: 2;
  position: absolute;
  left: 0;
  top: 0;
  width: $imgW;
  height: 100%;
  padding-top: 360px;

  &:before {
    content: '';
    position: absolute;
    right: 0;
    top: 0;
    width: $contW;
    height: 100%;
    background-image: url('https://i.ibb.co/fnFfMVT/1.png');
    background-size: cover;
    transition: transform $switchAT ease-in-out;
  }

  &:after {
    content: '';
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background: rgba(0,0,0,0.35);
  }

  @include signUpActive {
    &:before {
      transform: translate3d($formW,0,0);
    }
  }

  &__text {
    z-index: 2;
    position: absolute;
    left: 0;
    top: 50px;
    width: 100%;
    padding: 0 20px;
    text-align: center;
    color: #fff;
    transition: transform $switchAT ease-in-out;

    h2 {
      margin-bottom: 10px;
      font-weight: normal;
    }

    p {
      font-size: 14px;
      line-height: 1.5;
    }

    &.m--up {

      @include signUpActive {
        transform: translateX($imgW*2);
      }
    }

    &.m--in {
      transform: translateX($imgW * -2);

      @include signUpActive {
        transform: translateX(0);
      }
    }
  }

  &__btn {
    overflow: hidden;
    z-index: 2;
    position: relative;
    width: 100px;
    height: $btnH;
    margin: 0 auto;
    background: transparent;
    color: #fff;
    text-transform: uppercase;
    font-size: 15px;
    cursor: pointer;
    
    &:after {
      content: '';
      z-index: 2;
      position: absolute;
      left: 0;
      top: 0;
      width: 100%;
      height: 100%;
      border: 2px solid #fff;
      border-radius: 30px;
    }

    span {
      position: absolute;
      left: 0;
      top: 0;
      display: flex;
      justify-content: center;
      align-items: center;
      width: 100%;
      height: 100%;
      transition: transform $switchAT;
      
      &.m--in {
        transform: translateY($btnH*-2);
        
        @include signUpActive {
          transform: translateY(0);
        }
      }
      
      &.m--up {
        @include signUpActive {
          transform: translateY($btnH*2);
        }
      }
    }
  }
}

h2 {
  width: 100%;
  font-size: 26px;
  text-align: center;
}

label {
  display: block;
  width: $inputW;
  margin: 25px auto 0;
  text-align: center;

  span {
    font-size: 12px;
    color: #978d8d;
    text-transform: uppercase;
  }
}

input {
  display: block;
  width: 100%;
  margin-top: 5px;
  padding-bottom: 5px;
  font-size: 16px;
  border-bottom: 1px solid rgba(0,0,0,0.4);
  text-align: center;
}

.forgot-pass {
  margin-top: 15px;
  text-align: center;
  font-size: 12px;
  color: #cfcfcf;
}

.submit {
  margin-top: 40px;
  margin-bottom: 20px;
  background: #17B0E7;
  text-transform: uppercase;
}

.fb-btn {
  border: 2px solid #d3dae9;
  color: darken(#d3dae9, 20%);

  span {
    font-weight: bold;
    color: darken(#768cb6, 20%);
  }
}

.sign-in {
  transition-timing-function: ease-out;

  @include signUpActive {
    transition-timing-function: ease-in-out;
    transition-duration: $switchAT;
    transform: translate3d($formW,0,0);
  }
  label {
    margin-top: 50px;
  }
}

.sign-up {
  transform: translate3d($contW * -1,0,0);

  @include signUpActive {
    transform: translate3d(0,0,0);
  }
}

.icon-link {
  position: absolute;
  left: 5px;
  bottom: 5px;
  width: 32px;

  img {
    width: 100%;
    vertical-align: top;
  }

  &--twitter {
    left: auto;
    right: 5px;
  }
}

select {
  -moz-appearance: none;
  -webkit-appearance: none;
  appearance: none;
  font-size: 12px;
  font-weight: 400;
  line-height: 1.5;
  color: #444;
  background-color: #fff;
  padding: 6px 14px;
  // padding: .6em 1.4em .5em .8em;
  margin-left: 10px;
  border: 1px solid #aaa;
  border-radius: .5em;
  box-shadow: 0 1px 0 1px rgba(0,0,0,.04);
}
select:hover {
  border-color: #17B0E7;
}
select:focus {
  border-color: #aaa;
  box-shadow: 0 0 1px 3px rgba(59, 153, 252, .7);
  box-shadow: 0 0 0 3px -moz-mac-focusring;
  color: #222;
  outline: none;
}

select:disabled {
  opacity: 0.5;
}
</style>