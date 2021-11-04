import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "../views/Login.vue";
import Gate from "../views/Gate.vue";
import Locker from "../views/Locker.vue";
import QBoard from "../views/QBoard.vue";
import Help from "../views/Help.vue";
import HelpWrite from "../views/HelpWrite.vue";
import HelpDetail from "../views/HelpDetail.vue";
import Share from "../views/Share.vue";

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
    path: "/locker",
    name: "Locker",
    component: Locker,
  },
  {
    path: "/qboard",
    name: "QBoard",
    component: QBoard,
  },
  {
    path: "/help",
    name: "Help",
    component: Help,
  },
  {
    path: "/helpwrite/:help_pk?",
    name: "HelpWrite",
    component: HelpWrite,
  },
  {
    path: "/helpdetail/:help_pk",
    name: "HelpDetail",
    component: HelpDetail,
  },
  {
    path: "/share",
    name: "Share",
    component: Share,
  },
]

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
