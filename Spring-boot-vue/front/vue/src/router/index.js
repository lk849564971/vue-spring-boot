import Vue from "vue";
import VueRouter from "vue-router";

import Login from "@/components/Login";
import Register from "@/components/Register";
import Home from "@/components/Home";
import MyOrder from "@/components/MyOrder";
import MyCommodity from "@/components/MyCommodity";
import CreateOrder from "@/components/CreateOrder";

Vue.use(VueRouter);

const router = new VueRouter({
  mode: "history",
  routes: [
    {
      path: "/",
      name: "Login",
      component: Login
    },
    {
      path: "/register",
      name: "Register",
      component: Register
    },
    {
      path: "/home",
      name: "Home",
      component: Home
    },
    {
      path: "/Myorder",
      name: "MyOrder",
      component: MyOrder
    },
    {
      path: "/Mycommodity",
      name: "MyCommodity",
      component: MyCommodity
    },
    {
      path: "/createorder",
      name: "CreateOrder",
      component: CreateOrder
    }
  ]
});

export default router;
