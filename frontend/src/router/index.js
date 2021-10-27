import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "../views/Login.vue";
import Gate from "../views/Gate.vue";
import Reserve from "../views/Reserve.vue";
import StudyRoom from "../views/StudyRoom.vue";

Vue.use(VueRouter)

const routes = [
  {
    path: "/",
    name: "Login",
    component: Login,
  },
  {
    path: "/gate",
    name: "Gate",
    component: Gate,
  },
  {
    path: "/reserve",
    name: "Reserve",
    component: Reserve,
  },
  {
    path: "/studyroom",
    name: "StudyRoom",
    component: StudyRoom,
  },
]

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
