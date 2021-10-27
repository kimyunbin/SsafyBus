<template>
  <div class="container">
    <article class="date-carousel">
      <input type="button" class="date-carousel-prev" value="&lt;">
      <input type="date" class="date-carousel-input">
      <input type="button" class="date-carousel-next" value="&gt;">
    </article>
    <hr>
    <div class ="write">
      modal
    </div>
    <div class = "list">
      반복문으로?
    </div>
  </div>
</template>

<script>
export default {
  name: 'VisitorBook',
  data() {
    return {

    }
  },
  computed: {

  },
  methods: {
    calender() {
        var util = {
          qs(sel, ctx){ 
            return (ctx || document).querySelector(sel);
          },
          qsa(sel, ctx){
            return Array.from((ctx || document).querySelectorAll(sel));
          }
        };

        class DateCarousel {
          constructor(el) {
            this.element = el;
            this.prevButton = util.qs(".date-carousel-prev", el);
            this.input = util.qs(".date-carousel-input",el);
            this.nextButton = util.qs(".date-carousel-next",el);
            this.input.valueAsDate = new Date();
            this.prevButton.addEventListener("click",this.prev.bind(this));
            this.nextButton.addEventListener("click",this.next.bind(this));
          }
          
          prev(){
            this.input.stepDown();
          }
          
          next() {
            this.input.stepUp();
          }
        }

        util.qsa('.date-carousel').forEach(function(el){ new DateCarousel(el) });
    }
  },
  
  created() {
    // 방명록 조회
  },
  
}
</script>

<style scoped>
.date-carousel {
  background-color: black;
  box-sizing: border-box;
  display: flex;
  height: 0.5in;
  margin: auto;
  position: relative;
  width:3in;
}

.date-carousel-next,
.date-carousel-prev,
.date-carousel-input {
  box-sizing: border-box;
  position:relative;
  border: none;
}

.date-carousel-next,
.date-carousel-prev {
  background-color: transparent;
  color: white;
  cursor: pointer;
  font-size: larger;
  font-weight: bold;
  width: 0.5in;
}

.date-carousel-input {
  color: white;
  background-color: transparent;
  text-align: center;
  width: 2in;
  font-size: 1.5rem;
}

/* Disable Native UI */

.date-carousel-input::-webkit-inner-spin-button,
.date-carousel-input::-webkit-input-placeholder{
    display: none;
    -webkit-appearance: none;
}
</style>